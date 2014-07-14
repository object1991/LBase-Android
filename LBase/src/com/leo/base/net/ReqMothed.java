package com.leo.base.net;


/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public enum ReqMothed {
	
	/**
	 * 使用POST方式请求网络连接
	 */
	POST("POST"),

	/**
	 * 使用GET方式请求网络连接
	 */
	GET("GET");

	private String mMothed;

	private ReqMothed(String mothed) {
		this.mMothed = mothed;
	}

	/**
	 * 获取请求网络连接方式具体值
	 * 
	 * @return
	 */
	public String getMothed() {
		return this.mMothed;
	}
	
}
