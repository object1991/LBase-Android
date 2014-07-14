package com.example.lbaseexample.common;

import org.json.JSONException;

import com.leo.base.activity.LActivity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.handler.LHandler;
import com.leo.base.net.ILNetwork.LReqResultState;
import com.leo.base.net.LNetwork;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;

public abstract class MHandler extends LHandler {

	public MHandler(LActivity activity) {
		super(activity);
	}

	public MHandler(LFragment fragment) {
		super(fragment);
	}

	public <T> MHandler(LBaseAdapter<T> baseAdapter) {
		super(baseAdapter);
	}

	@Override
	public void onException(LReqResultState state, int requestId) {
		switch (state) {
		case NETWORK_EXC:
			// ... 网络请求失败
			L.e("网络请求失败");
			onNetWorkExc();
			break;
		case PARSE_EXC:
			// ... 数据解析失败
			L.e("数据解析失败");
			onParseExc();
			break;
		case LOGIN_ERROR:
			// ... 自动登录错误
			L.i("自动登录错误");
			onLoginError();
			break;
		case LOGIN_NONE:
			// ... 没有登录帐号
			L.i("没有登录帐号");
			onLoginNone();
			break;
		case OTHER:
			// ... 其它异常
			L.e("其它异常");
			onOtherExc();
			break;
		}
		onHandleUI(null, requestId);
	}

	@Override
	public abstract LMessage onNetParse(String result, int requestId)
			throws JSONException, LLoginException;

	@Override
	public void startLoadingData(LReqEntity entity, int requestId) {
		LNetwork network = new MNetwork(entity, requestId);
		network.setILNetworkCallback(this);
		network.start();
	}

	/**
	 * 网络请求异常
	 */
	protected abstract void onNetWorkExc();

	/**
	 * 数据解析失败
	 */
	protected abstract void onParseExc();

	/**
	 * 自动登录错误
	 */
	protected abstract void onLoginError();

	/**
	 * 未登录用户
	 */
	protected abstract void onLoginNone();

	/**
	 * 其它异常
	 */
	protected abstract void onOtherExc();

}
