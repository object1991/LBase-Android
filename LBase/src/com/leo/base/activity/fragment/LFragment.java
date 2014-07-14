package com.leo.base.activity.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.handler.ILHandlerCallback;

/**
 * <h1>来源：</h1> 
 * LFragment 继承自 {@link android.support.v4.app.Fragment} 
 * <h1>用途：</h1>
 * 所有 Fragment 需继承此类 
 * <h1>说明：</h1> <li>当你使用了
 * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.net.LReqEntity)
 * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
 * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此类的
 * {@linkplain com.leo.base.activity.fragment.LFragment#resultHandler(com.leo.base.entity.LMessage)
 * LFragment.resultHandler(LMessage)} 方法，并将解析的结果
 * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回</li>
 * 
 * @author Chen Lei
 * @version 1.1.5
 * 
 */
public abstract class LFragment extends Fragment implements ILHandlerCallback {

	protected LActivity mActivity;
	protected View mView;

	/**
	 * 获取LFragment是否已经销毁
	 */
	private boolean isDestroy;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (LActivity) activity;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isDestroy = true;
	}

	/**
	 * 当你使用了
	 * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.net.LReqEntity)
	 * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
	 * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此方法，并将解析的结果
	 * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回
	 * 
	 * @param msg
	 */
	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		// ... 写入你需要的代码
	}

	/**
	 * 
	 * @return 获取LFragment是否已经销毁
	 */
	@Override
	public boolean isDestroy() {
		return isDestroy;
	}

}
