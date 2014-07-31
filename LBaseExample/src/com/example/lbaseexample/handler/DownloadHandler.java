package com.example.lbaseexample.handler;

import org.json.JSONException;

import android.graphics.Bitmap;

import com.example.lbaseexample.activity.DownloadActivity;
import com.example.lbaseexample.common.MHandler;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.L;
import com.leo.base.util.LBitmap;
import com.leo.base.util.LFormat;
import com.leo.base.widget.T;

public class DownloadHandler extends MHandler {

	private DownloadActivity mActivity;

	public DownloadHandler(DownloadActivity activity) {
		super(activity);
		this.mActivity = activity;
	}

	@Override
	public LMessage onParse(String strs, int requestId) throws LLoginException,
			JSONException, Exception {
		LMessage msg = new LMessage();
		if (!LFormat.isEmpty(strs)) {
			Bitmap mBitmap = LBitmap.getResizedImage(strs, 100);
			if (mBitmap != null) {
				msg.setObj(mBitmap);
				msg.setStr(strs);
				return msg;
			}
		}
		return null;
	}

	@Override
	public void onProgress(int size, int current, int requestId) {
		super.onProgress(size, current, requestId);
		L.i("图片大小：" + size + "，当前大小：" + current);
		mActivity.getProgressBar().setMax(size);
		mActivity.getProgressBar().setProgress(current);
	}

	@Override
	protected void onNetWorkExc(int requestId) {
		T.ss("图片加载失败");
	}

	@Override
	protected void onParseExc(int requestId) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onLoginError(int requestId) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onLoginNone(int requestId) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onStop(int requestId) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onOtherExc(int requestId) {
		// TODO Auto-generated method stub

	}

}
