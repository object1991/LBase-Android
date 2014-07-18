package com.leo.base.net;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.os.AsyncTask;
import android.util.SparseArray;

import com.leo.base.entity.LMessage;
import com.leo.base.entity.LReqEncode;
import com.leo.base.entity.LReqEntity;
import com.leo.base.entity.LReqFile;
import com.leo.base.entity.LReqMothed;
import com.leo.base.exception.LException;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.L;
import com.leo.base.util.LFormat;

/**
 * 
 * @author Chen Lei
 * @version 1.3.1
 * 
 */
public abstract class LNetwork implements ILNetwork {

	/**
	 * 线程状态
	 * 
	 * @author object
	 * 
	 */
	private static enum LReqState {

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
	 * 存放当前所有线程的集合
	 */
	private SparseArray<LNetworkTask> mThreads;

	public LNetwork() {
		mThreads = new SparseArray<LNetworkTask>();
	}

	@Override
	public void start(LReqEntity reqEntity, ILNetworkCallback callback) {
		start(reqEntity, 0, callback);
	}

	@Override
	public void start(LReqEntity reqEntity, int requestId,
			ILNetworkCallback callback) {
		if (reqEntity == null) {
			throw new NullPointerException(
					"The network requests the LReqEntity parameter cannot be empty!");
		}
		if (callback == null) {
			throw new NullPointerException(
					"This is an invalid request,because you did not realize the callback interface!");
		}
		LNetworkTask task = mThreads.get(requestId);
		if (task == null || task.getState() == LReqState.FINISHED) {
			task = new LNetworkTask(reqEntity, requestId, callback);
			task.execute();
			mThreads.put(requestId, task);
		} else {
			L.i(TAG, "requestId " + requestId + " thread is running!");
		}
	}

	@Override
	public void stopAllThread() {
		for (int index = 0, size = mThreads.size(); index < size; index++) {
			int key = mThreads.keyAt(index);
			stopThread(key);
		}
	}

	@Override
	public void stopThread(int requestId) {
		LNetworkTask task = mThreads.get(requestId);
		if (task != null) {
			task.stop();
		}
	}

	/**
	 * 异步请求模块
	 * 
	 * @author Chen Lei
	 * 
	 */
	protected final class LNetworkTask extends
			AsyncTask<Void, Void, LReqResultState> {

		/**
		 * 请求封装实体
		 */
		private LReqEntity mReqEntity;

		/**
		 * 请求ID
		 */
		private int mRequestId;

		/**
		 * 请求回调接口
		 */
		private ILNetworkCallback mCallback;

		/**
		 * 请求返回值
		 */
		private LMessage mMessage;

		/**
		 * 线程状态
		 */
		private LReqState mThreadState;

		/**
		 * 是否停止线程，默认false
		 */
		private boolean mIsStopThread;

		LNetworkTask(LReqEntity reqEntity, int reqId, ILNetworkCallback callback) {
			this.mReqEntity = reqEntity;
			this.mRequestId = reqId;
			this.mCallback = callback;
			mThreadState = LReqState.PENDING;
			mIsStopThread = false;
		}

		@Override
		protected LReqResultState doInBackground(Void... params) {
			mThreadState = LReqState.RUNNING;
			LReqEntity entity = mReqEntity;
			if (entity == null) {
				throw new NullPointerException(
						"The network requests the LReqEntity parameter cannot be empty!");
			}
			String netUrl = entity.getUrl();
			if (LFormat.isEmpty(netUrl)) {
				throw new NullPointerException(
						"The network requests the URL parameter cannot be empty!");
			}
			LReqEncode netEncode = entity.getReqEncode();
			LReqMothed netMothed = entity.getReqMode();
			Map<String, String> netParams = entity.getParams();
			List<LReqFile> netFiles = entity.getFileParams();
			boolean netUseCache = entity.getUseCache();
			if (mIsStopThread)
				return LReqResultState.STOP;
			String result = null;
			try {
				if (netFiles == null || netFiles.isEmpty()) {
					result = LCaller.doConn(netUrl, netParams, netUseCache,
							netMothed, netEncode);
				} else {
					result = LCaller.doUploadFile(netUrl, netParams, netFiles,
							netEncode);
				}
			} catch (Exception e) {
				L.e(TAG, LException.getStackMsg(e));
				return LReqResultState.NETWORK_EXC;
			}
			if (mCallback != null) {
				if (mIsStopThread)
					return LReqResultState.STOP;
				try {
					mMessage = mCallback.onParse(result, mRequestId);
				} catch (LLoginException e) {
					mMessage = null;
					// ... 登录处理
					LLoginState loginState = doLogin();
					if (loginState == LLoginState.SUCCESS) {
						return LReqResultState.LOGIN_SUCCESS;
					} else if (loginState == LLoginState.ERROR) {
						return LReqResultState.LOGIN_ERROR;
					} else if (loginState == LLoginState.NONE) {
						return LReqResultState.LOGIN_NONE;
					} else {
						return LReqResultState.LOGIN_EXC;
					}
				} catch (JSONException e) {
					mMessage = null;
					L.e(TAG, LException.getStackMsg(e));
					return LReqResultState.PARSE_EXC;
				} catch (Exception e) {
					mMessage = null;
					L.e(TAG, LException.getStackMsg(e));
					return LReqResultState.OTHER;
				}
			} else {
				throw new NullPointerException(
						"This is an invalid request,because you did not realize the callback interface!");
			}
			return LReqResultState.SUCCESS;
		}

		@Override
		protected void onPostExecute(LReqResultState result) {
			if (mIsStopThread)
				result = LReqResultState.STOP;
			super.onPostExecute(result);
			if (mCallback != null) {
				switch (result) {
				case SUCCESS:
					mCallback.onHandlerUI(mMessage, mRequestId);
					break;
				case NETWORK_EXC:
					mCallback.onException(LReqResultState.NETWORK_EXC,
							mRequestId);
					break;
				case PARSE_EXC:
					mCallback
							.onException(LReqResultState.PARSE_EXC, mRequestId);
					break;
				case LOGIN_SUCCESS:
					L.i(TAG, "用户自动登录成功");
					mThreadState = LReqState.FINISHED;
					start(mReqEntity, mRequestId, mCallback);
					break;
				case LOGIN_ERROR:
					mCallback.onException(LReqResultState.LOGIN_ERROR,
							mRequestId);
					break;
				case LOGIN_NONE:
					mCallback.onException(LReqResultState.LOGIN_NONE,
							mRequestId);
					break;
				case LOGIN_EXC:
					throw new RuntimeException("用户登录返回异常");
				case OTHER:
					mCallback.onException(LReqResultState.OTHER, mRequestId);
					break;
				case STOP:
					L.i(TAG, "线程ID为：" + mRequestId + "的线程已停止!");
					mCallback.onException(LReqResultState.STOP, mRequestId);
					break;
				default:
					throw new IllegalArgumentException("返回结果参数错误");
				}
				mThreadState = LReqState.FINISHED;
			}
		}

		public LReqState getState() {
			return mThreadState;
		}

		public void stop() {
			this.mIsStopThread = true;
		}

	}

	/**
	 * 登录状态
	 * 
	 * @author leo
	 * 
	 */
	protected enum LLoginState {
		/**
		 * 登录成功
		 */
		SUCCESS,

		/**
		 * 登录失败
		 */
		ERROR,

		/**
		 * 没有登录帐号
		 */
		NONE;
	}

	/**
	 * 此方法用于执行用户登录操作<br/>
	 * 在请求网络时，由于SESSION可能为空，导致无法获取数据<br/>
	 * 这时便需要在后台做登录操作
	 * 
	 * @return {@link LoginState}结果对象
	 */
	protected abstract LLoginState doLogin();

}
