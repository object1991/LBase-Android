package com.leo.base.net;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public interface ILNetwork {
	
	static final String TAG = ILNetwork.class.getSimpleName();

	/**
	 * 开始执行网络请求
	 */
	void start();

	/**
	 * 此方法用于执行用户登录操作<br/>
	 * 在请求网络时，由于SESSION可能为空，导致无法获取数据<br/>
	 * 这时便需要在后台做登录操作
	 * 
	 * @return {@link LoginState}结果对象
	 */
	LoginState doLogin();

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
		OTHER;
	}

	/**
	 * 登录状态
	 * 
	 * @author leo
	 * 
	 */
	enum LoginState {
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

}
