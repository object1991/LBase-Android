package com.leo.base.handler;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.leo.base.activity.LActivity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.net.ILNetworkCallback;
import com.leo.base.net.LReqEntity;
import com.leo.base.net.ILNetwork.LReqResultState;
import com.leo.base.util.L;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 * 
 */
public abstract class LHandler extends Handler implements ILNetworkCallback {

	/**
	 * 默认进动画
	 */
	private static final int DEFAULT_ENTER_ANIM = -1;

	/**
	 * 默认出动画
	 */
	private static final int DEFAULT_EXIT_ANIM = -1;

	/**
	 * 默认返回进动画
	 */
	private static final int DEFAULT_BACK_ENTER_ANIM = -1;

	/**
	 * 默认返回出动画
	 */
	private static final int DEFAULT_BACK_EXIT_ANIM = -1;

	/**
	 * 当前Activity对象
	 */
	private LActivity mActivity;

	/**
	 * 当前Fragment对象
	 */
	private LFragment mFragment;

	/**
	 * 当前BaseAdapter对象
	 */
	private LBaseAdapter<?> mBaseAdapter;

	/**
	 * 构造函数
	 * 
	 * @param activity
	 */
	public LHandler(LActivity activity) {
		this.mActivity = activity;
		try {
			this.mILHandlerCallback = (ILHandlerCallback) activity;
		} catch (ClassCastException e) {
			this.mILHandlerCallback = null;
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param fragment
	 */
	public LHandler(LFragment fragment) {
		this.mFragment = fragment;
		try {
			this.mILHandlerCallback = (ILHandlerCallback) mFragment;
		} catch (ClassCastException e) {
			this.mILHandlerCallback = null;
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param <T>
	 * @param mBaseAdapter
	 */
	public <T> LHandler(LBaseAdapter<T> mBaseAdapter) {
		this.mBaseAdapter = mBaseAdapter;
		try {
			this.mILHandlerCallback = (ILHandlerCallback) mBaseAdapter;
		} catch (ClassCastException e) {
			this.mILHandlerCallback = null;
		}
	}

	/**
	 * 开始请求网络连接
	 * 
	 * @param entity
	 *            ：网络请求所需要的参数对象
	 */
	public void startLoadingData(LReqEntity entity) {
		startLoadingData(entity, 0);
	}

	/**
	 * 重写此方法，操作基本网络连接
	 * 
	 * @param entity
	 *            ：网络请求所需要的参数对象
	 * @param requestId
	 *            : 请求ID，方便区分不同请求
	 */
	public abstract void startLoadingData(LReqEntity entity, int requestId);

	/**
	 * 请求发现异常<br/>
	 * 如果请求view已经销毁，则返回
	 */
	@Override
	public void onNetException(LReqResultState state, int requestId) {
		if (isDestroyView()) {
			L.i("onNetException停止");
			return;
		}
		L.i("onNetException运行");
		onException(state, requestId);
	}

	/**
	 * 网络请求异常
	 * 
	 * @param state
	 *            ：异常状态
	 * @param requestId
	 *            ：请求ID
	 */
	public abstract void onException(LReqResultState state, int requestId);

	/**
	 * 请求结果返回<br/>
	 * 如果请求view已经销毁，则返回
	 */
	@Override
	public LMessage onNetResult(String strs, int requestId)
			throws JSONException, LLoginException, Exception {
		if (isDestroyView()) {
			L.i("onNetResult停止");
			return null;
		}
		L.i("onNetResult正常运行");
		return onNetParse(strs, requestId);
	}

	/**
	 * 网络请求结果返回，在此处解析返回结果
	 * 
	 * @param result
	 *            ：请求返回的值
	 * @param requestId
	 *            ：请求ID
	 * @return：将解析好的数据存放到LMessage中封装
	 * @throws JSONException
	 *             ：如解析错误，向上抛出此异常
	 * @throws LLoginException
	 *             ：如发现用户在后台未登录，向上抛出此异常
	 * @throws Exception
	 *             ：其它异常
	 */
	public abstract LMessage onNetParse(String result, int requestId)
			throws JSONException, LLoginException, Exception;

	/**
	 * 返回请求解析的结果<br/>
	 * 自动调用发出请求view的onResultHandler方法，使用者请自主复写此方法，以便处理<br/>
	 * 如果请求view已经销毁，则返回
	 */
	@Override
	public void onHandleUI(LMessage result, int requestId) {
		if (isDestroyView()) {
			L.i("onHandleUI停止");
			return;
		}
		L.i("onHandleUI正常运行");
		ILHandlerCallback callback = getCallback();
		if (callback != null) {
			callback.onResultHandler(result, requestId);
		}
	}

	/**
	 * 获取是否已经销毁当前view<br/>
	 * 如果当前view并未继承ILHandlerCallback，则返回false
	 * 
	 * @return
	 */
	public boolean isDestroyView() {
		ILHandlerCallback callback = getCallback();
		if (callback == null) {
			return false;
		}
		return callback.isDestroy();
	}

	/**
	 * 注销当前Activity
	 */
	public void finishActivity() {
		if (mActivity != null) {
			mActivity.finish();
			mActivity.overridePendingTransition(DEFAULT_BACK_ENTER_ANIM,
					DEFAULT_BACK_EXIT_ANIM);
		}
	}

	/**
	 * 跳转Activity
	 * 
	 * @param cls
	 *            ：需要跳转到的Activity
	 */
	public void jumpActivity(Class<?> cls) {
		jumpActivity(cls, null, false, DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
	}

	/**
	 * 跳转Activity
	 * 
	 * @param cls
	 *            ：需要跳转到的Activity
	 * @param isFinish
	 *            ：是否注销此Activity
	 */
	public void jumpActivity(Class<?> cls, boolean isFinish) {
		jumpActivity(cls, null, isFinish, DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
	}

	/**
	 * 跳转Activity
	 * 
	 * @param cls
	 *            ：需要跳转到的Activity
	 * @param data
	 *            ：需要传递的参数
	 */
	public void jumpActivity(Class<?> cls, Bundle data) {
		jumpActivity(cls, data, false, DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
	}

	/**
	 * 跳转Activity
	 * 
	 * @param cls
	 *            ：需要跳转到的Activity
	 * @param data
	 *            ：需要传递的参数
	 * @param isFinish
	 *            ：是否注销此Activity
	 */
	public void jumpActivity(Class<?> cls, Bundle data, boolean isFinish) {
		jumpActivity(cls, data, isFinish, DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
	}

	/**
	 * 跳转Activity
	 * 
	 * @param cls
	 *            ：需要跳转到的Activity
	 * @param data
	 *            ：需要传递的参数
	 * @param isFinish
	 *            ：是否注销此Activity
	 * @param enterAnim
	 *            ：进入动画
	 * @param exitAnim
	 *            ：送出动画
	 */
	public void jumpActivity(Class<?> cls, Bundle data, boolean isFinish,
			int enterAnim, int exitAnim) {
		LActivity context = null;
		if (mFragment != null) {
			context = (LActivity) mFragment.getActivity();
		} else if (mActivity != null) {
			context = mActivity;
		} else if (mBaseAdapter != null) {
			context = (LActivity) mBaseAdapter.getAdapter().getContext();
		}
		Intent intent = new Intent(context, cls);
		if (data != null) {
			intent.putExtras(data);
		}
		context.startActivity(intent);
		if (isFinish) {
			finishActivity();
		}
		// API LEVEL5.0
		context.overridePendingTransition(enterAnim, exitAnim);
	}

	private ILHandlerCallback mILHandlerCallback;

	public void setILHandlerCallback(ILHandlerCallback calback) {
		this.mILHandlerCallback = calback;
	}

	public ILHandlerCallback getCallback() {
		return mILHandlerCallback;
	}

}
