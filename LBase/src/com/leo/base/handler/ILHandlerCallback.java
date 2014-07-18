package com.leo.base.handler;

import com.leo.base.entity.LMessage;

/**
 * 
 * @author Chen Lei
 * @version 1.3.1
 *
 */
public interface ILHandlerCallback {

	/**
	 * 将结果返回，运行在UI线程
	 * @param msg 请求结果处理后的实体封装
	 * @param requestId 请求ID
	 */
	void onResultHandler(LMessage msg, int requestId);
	
}
