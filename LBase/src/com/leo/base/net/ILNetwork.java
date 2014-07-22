package com.leo.base.net;

import com.leo.base.entity.LReqEntity;

/**
 * 
 * @author Chen Lei
 * @version 1.3.5
 * 
 */
public interface ILNetwork {

	static final String TAG = ILNetwork.class.getSimpleName();

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
	void request(LReqEntity reqEntity, int requestId, ILNetworkCallback callback);

	/**
	 * 停止当前对象所有线程
	 */
	void stopAllThread();

	/**
	 * 根据请求ID停止相对应的请求线程
	 * 
	 * @param requestId
	 */
	void stopRequestThread(int requestId);
	
	/**
	 * 根据请求ID停止相对应的下载线程
	 * @param requestId
	 */
	void stopDownloadThread(int requestId);
	
	/**
	 * 执行下载文件请求
	 * @param url
	 * @param savePath
	 * @param saveName
	 * @param callback
	 */
	void download(String url, String savePath, String saveName, int id, ILNetworkCallback callback);

	/**
	 * 登录状态
	 * 
	 * @author leo
	 * 
	 */
	enum LLoginState {
		/**
		 * 登录成功
		 */
		SUCCESS,

		/**
		 * 登录失败
		 */
		ERROR,

		/**
		 * 没有登录帐号
		 */
		NONE;
	}

	/**
	 * 此方法用于执行用户登录操作<br/>
	 * 在请求网络时，由于SESSION可能为空，导致无法获取数据<br/>
	 * 这时便需要在后台做登录操作
	 * 
	 * @return {@link LoginState}结果对象
	 */
	LLoginState doLogin();

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
