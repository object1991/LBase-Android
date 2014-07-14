package com.leo.base.handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.leo.base.activity.LActivity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 * 
 */
public abstract class LHandler extends Handler {

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
			this.mOnLHandlerCallback = (OnLHandlerCallback) activity;
		} catch (ClassCastException e) {
			this.mOnLHandlerCallback = null;
		}
	}

	public LHandler(LFragment fragment) {
		this.mFragment = fragment;
		try {
			this.mOnLHandlerCallback = (OnLHandlerCallback) mFragment;
		} catch (ClassCastException e) {
			this.mOnLHandlerCallback = null;
		}
	}

	public <T> LHandler(LBaseAdapter<T> mBaseAdapter) {
		this.mBaseAdapter = mBaseAdapter;
		try {
			this.mOnLHandlerCallback = (OnLHandlerCallback) mBaseAdapter;
		} catch (ClassCastException e) {
			this.mOnLHandlerCallback = null;
		}
	}

	public void startLoadingData(LReqEntity entity) {
		startLoadingData(entity, 0);
	}

	/**
	 * 重写此方法，操作基本网络连接
	 * 
	 * @param entity
	 *            ：网络请求所需要的参数对象
	 * @param requestId
	 * 			  : 请求ID，方便区分不同请求
	 */
	public abstract void startLoadingData(LReqEntity entity, int requestId);

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

	private OnLHandlerCallback mOnLHandlerCallback;

	public void setOnLHandlerCallback(OnLHandlerCallback calback) {
		this.mOnLHandlerCallback = calback;
	}

	public OnLHandlerCallback getCallback() {
		return mOnLHandlerCallback;
	}

	public interface OnLHandlerCallback {
		void onResultHandler(LMessage msg, int requestId);
	}

}
