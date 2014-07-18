package com.leo.base.handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.leo.base.activity.LActivity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.entity.LMessage;
import com.leo.base.entity.LReqEntity;
import com.leo.base.net.ILNetwork;
import com.leo.base.net.ILNetworkCallback;

/**
 * 
 * @author Chen Lei
 * @version 1.3.1
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
	 * 设置LNetwork监听
	 */
	private ILNetwork mNetworkListener;

	/**
	 * 返回结果回调接口，用于通知所对应的LActivity、LFragment、或LBaseAdapter
	 */
	private ILHandlerCallback mILHandlerCallback;

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
	 * 设置网络请求监听
	 * 
	 * @param network
	 */
	public void setILNetworkListener(ILNetwork network) {
		mNetworkListener = network;
	}

	/**
	 * 设置返回结果回调接口，用于通知所对应的LActivity、LFragment、或LBaseAdapter
	 * 
	 * @param calback
	 */
	public void setILHandlerCallback(ILHandlerCallback calback) {
		this.mILHandlerCallback = calback;
	}

	/**
	 * 获取返回结果回调接口
	 * 
	 * @return
	 */
	public ILHandlerCallback getCallback() {
		return mILHandlerCallback;
	}

	/**
	 * 开始请求网络连接
	 * 
	 * @param entity
	 *            ：网络请求所需要的参数对象
	 */
	public void start(LReqEntity entity) {
		start(entity, 0);
	}

	/**
	 * 重写此方法，操作基本网络连接
	 * 
	 * @param entity
	 *            ：网络请求所需要的参数对象
	 * @param requestId
	 *            : 请求ID，方便区分不同请求
	 */
	public void start(LReqEntity entity, int requestId) {
		if (mNetworkListener != null) {
			mNetworkListener.start(entity, requestId, this);
		} else {
			throw new NullPointerException(
					"The LNetwork is null, you must changed setLNetworkListener(LNetwork)");
		}
	}

	/**
	 * 停止当前实体所有线程
	 */
	public void stopAllThread() {
		if (mNetworkListener != null) {
			mNetworkListener.stopAllThread();
		} else {
			throw new NullPointerException(
					"The LNetwork is null, you must changed setLNetworkListener(LNetwork)");
		}
	}

	/**
	 * 停止当前实体requestId所对应的线程
	 * 
	 * @param requestId
	 */
	public void stopThread(int requestId) {
		if (mNetworkListener != null) {
			mNetworkListener.stopThread(requestId);
		} else {
			throw new NullPointerException(
					"The LNetwork is null, you must changed setLNetworkListener(LNetwork)");
		}
	}

	/**
	 * 返回请求解析的结果<br/>
	 * 自动调用发出请求view的onResultHandler方法，使用者请自主复写此方法，以便处理<br/>
	 */
	@Override
	public void onHandlerUI(LMessage result, int requestId) {
		ILHandlerCallback callback = getCallback();
		if (callback != null) {
			callback.onResultHandler(result, requestId);
		}
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

}
