package com.leo.base.entity;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public enum LReqMothed {
	
	/**
	 * 使用POST方式请求网络连接
	 */
	POST("POST"),

	/**
	 * 使用GET方式请求网络连接
	 */
	GET("GET");

	private String mMothed;

	private LReqMothed(String mothed) {
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
