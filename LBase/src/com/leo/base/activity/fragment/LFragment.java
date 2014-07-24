package com.leo.base.activity.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.leo.base.activity.LActivity;
import com.leo.base.dialog.LProgress;
import com.leo.base.dialog.LProgress.OnKeyBackListener;
import com.leo.base.entity.LMessage;
import com.leo.base.handler.ILHandlerCallback;

/**
 * <h1>来源：</h1> LFragment 继承自 {@link android.support.v4.app.Fragment} <h1>用途：</h1>
 * 所有 Fragment 需继承此类 <h1>说明：</h1> <li>当你使用了
 * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.entity.LReqEntity)
 * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
 * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此类的
 * {@linkplain com.leo.base.activity.fragment.LFragment#resultHandler(com.leo.base.entity.LMessage)
 * LFragment.resultHandler(LMessage)} 方法，并将解析的结果
 * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回</li>
 * 
 * @author Chen Lei
 * @version 1.3.1
 * 
 */
public abstract class LFragment extends Fragment implements ILHandlerCallback, OnKeyBackListener {

	protected LActivity mActivity;

	protected View mView;

	/**
	 * 进度提示框
	 */
	private LProgress mProgressDialog;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (LActivity) activity;
	}

	@Override
	public void onPause() {
		super.onPause();
		dismissProgressDialog();
		mProgressDialog = null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 显示一个ProgressDialog，直接调用即可<br/>
	 * 此ProgressDialog会在onPause方法内自动销毁
	 * 
	 * @param text
	 *            提示文字
	 */
	protected void showProgressDialog(String text) {
		if (mProgressDialog == null) {
			mProgressDialog = new LProgress(this.getActivity());
			mProgressDialog.setOnKeyBackListener(this);
		}
		mProgressDialog.show(text);
	}

	/**
	 * 销毁一个ProgressDialog
	 */
	protected void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	public void onKeyBackListener() {
		// ... 如果ProgressDialog正在运行，使用者按下BACK键，销毁ProgressDialog的同时，还会调用此接口。
		// ... 使用者可以在这里finish()，也可以在这里停止线程
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

}
