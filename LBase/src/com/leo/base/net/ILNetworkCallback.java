package com.leo.base.net;

import org.json.JSONException;

import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.net.ILNetwork.LReqResultState;

/**
 * 
 * @author Chen Lei
 * @version 1.3.1
 * 
 */
public interface ILNetworkCallback {

	/**
	 * 请求发现异常，运行于UI线程
	 * 
	 * @param state
	 *            ：请求异常状态
	 * @param requestId
	 *            ：请求ID
	 */
	void onException(LReqResultState state, int requestId);

	/**
	 * 网络请求结果返回，在此处解析返回结果，运行于子线程，不可操作UI
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
	LMessage onParse(String strs, int requestId) throws LLoginException,
			JSONException, Exception;

	/**
	 * 请求返回结果，运行于UI线程
	 * 
	 * @param msg
	 *            ：返回结果封装
	 * @param requestId
	 *            ：请求ID
	 */
	void onHandlerUI(LMessage msg, int requestId);

}
