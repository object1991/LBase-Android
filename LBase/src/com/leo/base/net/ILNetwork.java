package com.leo.base.net;

import com.leo.base.entity.LReqEntity;

/**
 * 
 * @author Chen Lei
 * @version 1.3.1
 * 
 */
public interface ILNetwork {

	static final String TAG = ILNetwork.class.getSimpleName();

	/**
	 * 开始执行网络请求
	 * 
	 * @param reqEntity
	 *            请求封装实体
	 * @param callback
	 *            执行回调接口
	 */
	void start(LReqEntity reqEntity, ILNetworkCallback callback);

	/**
	 * 开始执行网络请求
	 * 
	 * @param reqEntity
	 *            请求封装实体
	 * @param requestId
	 *            请求ID，用于相同实体之间区分不同请求<br/>
	 *            如果两次或多次请求的requestId相同， 框架需要检查之前此requestId的请求是否已经结束<br/>
	 *            如还未执行完成，则不再重复执行<br/>
	 *            如已完成，则继承执行
	 * @param callback
	 *            执行回调接口
	 */
	void start(LReqEntity reqEntity, int requestId, ILNetworkCallback callback);
	
	/**
	 * 停止当前对象所有线程
	 */
	void stopAllThread();
	
	/**
	 * 根据请求ID停止相对应的线程
	 * @param requestId
	 */
	void stopThread(int requestId);

	/**
	 * 请求返回结果状态
	 * 
	 * @author leo
	 * 
	 */
	enum LReqResultState {

		/**
		 * 网络请求返回结果成功
		 */
		SUCCESS,

		/**
		 * 网络请求返回结果失败
		 */
		NETWORK_EXC,

		/**
		 * 数据解析异常
		 */
		PARSE_EXC,

		/**
		 * 用户登录成功
		 */
		LOGIN_SUCCESS,

		/**
		 * 用户登录失败
		 */
		LOGIN_ERROR,

		/**
		 * 不存在登录帐号
		 */
		LOGIN_NONE,

		/**
		 * 登录未实现异常
		 */
		LOGIN_EXC,

		/**
		 * 其它异常
		 */
		OTHER,
		
		/**
		 * 线程停止
		 */
		STOP;
	}

}
