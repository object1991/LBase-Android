package com.example.lbaseexample.common;

import org.json.JSONException;

import com.leo.base.activity.LActivity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.handler.LHandler;
import com.leo.base.net.ILNetwork.LReqResultState;

public abstract class MHandler extends LHandler {

	public MHandler(LActivity activity) {
		super(activity);
		init();
	}

	public MHandler(LFragment fragment) {
		super(fragment);
		init();
	}

	public <T> MHandler(LBaseAdapter<T> baseAdapter) {
		super(baseAdapter);
		init();
	}

	/**
	 * 初始化网络请求监听，非常重要
	 */
	private void init() {
		setILNetworkListener(new MNetwork());
	}

	@Override
	public void onException(LReqResultState state, int requestId) {
		switch (state) {
		case NETWORK_EXC:
			onNetWorkExc(requestId);
			break;
		case PARSE_EXC:
			onParseExc(requestId);
			break;
		case LOGIN_ERROR:
			onLoginError(requestId);
			break;
		case LOGIN_NONE:
			onLoginNone(requestId);
			break;
		case STOP:
			onStop(requestId);
			break;
		default:
			onOtherExc(requestId);
			break;
		}
	}

	@Override
	public abstract LMessage onParse(String strs, int requestId)
			throws LLoginException, JSONException, Exception;

	/**
	 * 网络请求异常
	 */
	protected abstract void onNetWorkExc(int requestId);

	/**
	 * 数据解析失败
	 */
	protected abstract void onParseExc(int requestId);

	/**
	 * 自动登录错误
	 */
	protected abstract void onLoginError(int requestId);

	/**
	 * 未登录用户
	 */
	protected abstract void onLoginNone(int requestId);
	
	/**
	 * 线程停止
	 */
	protected abstract void onStop(int requestId);

	/**
	 * 其它异常
	 */
	protected abstract void onOtherExc(int requestId);

}
