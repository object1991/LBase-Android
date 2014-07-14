package com.leo.base.net;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.L;

/**
 * 网络请求工具类<br/>
 * 抽象类<br/>
 * 继承此类需重写{@link #doLogin}方法<br/>
 * 请求有三种状态{@link ReqState.PENDING}、{@link ReqState.RUNNING}、
 * {@link ReqState.FINISHED}<br/>
 * 在{@linkplain ReqState.RUNNING}（线程开始执行）状态下，不可以再打开相同的请求<br/>
 * 请求共有三个回调方法{@link onNetException}、{@link onNetResult}、 {@link onHandleUI}<br/>
 * 
 * @param {@linkplain onNetException}：
 *        <p>
 *        此回调方法有一个{@link LReqResultState}对象的参数，用于接收操作异常。 在
 *        {@link LReqResultState.NETWORK_EXC}网络连接失败、<br/>
 *        {@link LReqResultState.PARSE_EXC}数据解析失败、<br/>
 *        {@link LReqResultState.LOGIN_ERROR}用户登录失败、<br/>
 *        {@link LReqResultState.LOGIN_NONE}无登录帐号<br/>
 *        和{@link LReqResultState.OTHER}其它异常时，<br/>
 *        程序会将结果返回给此回调方法处理。<br/>
 *        此方法运行在UI线程中
 *        </p>
 * @param {@link onNetResult}：
 *        <p>
 *        此回调方法有一个{@link String}类型的参数<br/>
 *        此参数用于传递网络请求结果，用户可以使用此参数做任何操作。<br/>
 *        此方法需要用户返回一个Object类型的对象，用于在{@link onHandleUI}中传递到UI线程中<br/>
 *        此方法运行在子线程中，不可操作UI
 *        </p>
 * @param {@link onHandleUI}：
 *        <p>
 *        此回调方法有一个Object类型的参数，此参数从子线程中传递而来。<br/>
 *        用户可以在此方法中更新UI等操作
 *        </p>
 * 
 * @author Chen Lei
 * @version 1.1.5
 * 
 */
public abstract class LNetwork implements ILNetwork {

	/**
	 * 线程状态
	 * 
	 * @author object
	 * 
	 */
	private static enum ReqState {

		/**
		 * 线程状态为等待执行
		 */
		PENDING,

		/**
		 * 线程状态为正在执行
		 */
		RUNNING,

		/**
		 * 线程状态为执行结束
		 */
		FINISHED;
	}

	/**
	 * 网络请求参数对象
	 */
	private LReqEntity mReqEntity;

	/**
	 * 请求ID
	 */
	private int mRequestId;

	/**
	 * 构造函数
	 * 
	 * @param entity
	 *            请求参数实体
	 */
	public LNetwork(LReqEntity entity) {
		this(entity, 0);
	}

	/**
	 * 构造函数
	 * 
	 * @param entity
	 *            请求参数实体
	 * @param requestId
	 *            请求ID，用于区分不同请求
	 */
	public LNetwork(LReqEntity entity, int requestId) {
		this.mReqEntity = entity;
		this.mRequestId = requestId;
		mThreadState = ReqState.PENDING;
	}

	/**
	 * 请求处理接口
	 */
	private ILNetworkCallback mCallback;

	/**
	 * 设置请求处理回调方法
	 * 
	 * @param callback
	 */
	public void setILNetworkCallback(ILNetworkCallback callback) {
		this.mCallback = callback;
	}

	/**
	 * 返回结果对象
	 */
	private LMessage mMessage;

	/**
	 * 线程状态
	 */
	private static ReqState mThreadState;

	/**
	 * 开始执行网络请求
	 */
	@Override
	public void start() {
		if (mThreadState == ReqState.RUNNING) {
			L.i("当前线程正在执行");
			return;
		}
		if (this.mReqEntity == null) {
			throw new NullPointerException("参数对象不能为空");
		}
		mThreadState = ReqState.RUNNING;
		new NetworkTask().execute();
	}

	private final class NetworkTask extends
			AsyncTask<Void, Void, LReqResultState> {

		@Override
		protected LReqResultState doInBackground(Void... params) {
			if (mReqEntity == null) {
				throw new NullPointerException("参数对象不能为空");
			}
			String url = mReqEntity.getUrl();
			if (TextUtils.isEmpty(url)) {
				throw new NullPointerException("请求地址参数不能为空");
			}
			Map<String, String> param = mReqEntity.getParams();
			LReqEncode encoding = mReqEntity.getReqEncode();
			LReqMothed mothed = mReqEntity.getReqMode();
			List<LFileEntity> files = mReqEntity.getFileParams();
			String resStr = null;
			try {
				if (files != null && files.size() > 0) {
					resStr = LCaller.doUploadFile(url, param, files, encoding);
				} else {
					resStr = LCaller.doConn(url, param,
							mReqEntity.getUseCache(), mothed, encoding);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return LReqResultState.NETWORK_EXC;
			}
			if (mCallback != null) {
				try {
					mMessage = mCallback.onNetResult(resStr, mRequestId);
				} catch (LLoginException e) {
					mMessage = null;
					// ... 登录处理
					LoginState loginState = doLogin();
					if (loginState == LoginState.SUCCESS) {
						return LReqResultState.LOGIN_SUCCESS;
					} else if (loginState == LoginState.ERROR) {
						return LReqResultState.LOGIN_ERROR;
					} else if (loginState == LoginState.NONE) {
						return LReqResultState.LOGIN_NONE;
					} else {
						return LReqResultState.LOGIN_EXC;
					}
				} catch (JSONException e) {
					mMessage = null;
					e.printStackTrace();
					return LReqResultState.PARSE_EXC;
				} catch (Exception e) {
					mMessage = null;
					e.printStackTrace();
					return LReqResultState.OTHER;
				}
			}
			return LReqResultState.SUCCESS;
		}

		@Override
		protected void onPostExecute(LReqResultState result) {
			super.onPostExecute(result);
			if (mCallback != null) {
				switch (result) {
				case SUCCESS:
					mCallback.onHandleUI(mMessage, mRequestId);
					break;
				case NETWORK_EXC:
					mCallback.onNetException(LReqResultState.NETWORK_EXC,
							mRequestId);
					break;
				case PARSE_EXC:
					mCallback.onNetException(LReqResultState.PARSE_EXC,
							mRequestId);
					break;
				case LOGIN_SUCCESS:
					L.i("用户自动登录成功");
					mThreadState = ReqState.FINISHED;
					start();
					break;
				case LOGIN_ERROR:
					mCallback.onNetException(LReqResultState.LOGIN_ERROR,
							mRequestId);
					break;
				case LOGIN_NONE:
					mCallback.onNetException(LReqResultState.LOGIN_NONE,
							mRequestId);
					break;
				case LOGIN_EXC:
					throw new RuntimeException("用户未登录");
				case OTHER:
					mCallback.onNetException(LReqResultState.OTHER, mRequestId);
					break;
				default:
					throw new IllegalArgumentException("返回结果参数错误");
				}
				mThreadState = ReqState.FINISHED;
			}
		}

		@Override
		protected void onCancelled() {
			mReqEntity = null;
			mMessage = null;
			mThreadState = ReqState.FINISHED;
		}

	}

	/**
	 * 此方法用于执行用户登录操作<br/>
	 * 在请求网络时，由于SESSION可能为空，导致无法获取数据<br/>
	 * 这时便需要在后台做登录操作
	 * 
	 * @return {@link LoginState}结果对象
	 */
	@Override
	public abstract LoginState doLogin();

}
