package com.example.lbaseexample.handler;

import org.json.JSONException;

import com.example.lbaseexample.common.MHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.L;
import com.leo.base.util.T;

public class FragmentHandler extends MHandler {

	public FragmentHandler(LFragment fragment) {
		super(fragment);
	}

	@Override
	public LMessage onParse(String strs, int requestId) throws JSONException,
			LLoginException {
		LMessage msg = new LMessage();
		msg.setStr(strs);
		return msg;
	}

	@Override
	protected void onNetWorkExc(int requestId) {
		T.ss("网络请求发现异常");
	}

	@Override
	protected void onParseExc(int requestId) {
		T.ss("数据解析发现异常");
	}

	@Override
	protected void onLoginError(int requestId) {
		T.ss("自动登录错误异常");
	}

	@Override
	protected void onLoginNone(int requestId) {
		T.ss("用户并未存有登录帐号异常");
	}

	@Override
	protected void onOtherExc(int requestId) {
		T.ss("其它异常");
	}

	@Override
	protected void onStop(int requestId) {
		L.i("线程停止");
	}

}
