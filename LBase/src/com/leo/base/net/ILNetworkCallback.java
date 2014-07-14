package com.leo.base.net;

import org.json.JSONException;

import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.net.ILNetwork.LReqResultState;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 * 
 */
public interface ILNetworkCallback {

	/**
	 * 请求发现异常
	 * @param state：请求异常状态
	 * @param requestId：请求ID
	 */
	void onNetException(LReqResultState state, int requestId);

	/**
	 * 请求成功
	 * @param strs：网络请求结果
	 * @param requestId：网络请求ID
	 * @return：LMessage 对象，封装返回结果
	 * @throws JSONException：如解析过程中出现该异常，请向上抛出
	 * @throws LLoginException：如当前请求需要用户登录，并且用户在后台并未登录，请抛出此异常
	 * @throws Exception：其它异常
	 */
	LMessage onNetResult(String strs, int requestId) throws JSONException,
			LLoginException, Exception;

	/**
	 * 请求返回结果
	 * @param msg：返回结果封装
	 * @param requestId：请求ID
	 */
	void onHandleUI(LMessage msg, int requestId);

}
