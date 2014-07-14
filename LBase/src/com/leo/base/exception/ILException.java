package com.leo.base.exception;

/**
 * 此类暂未正式使用
 * @author Chen Lei
 *
 */
public interface ILException {

	/**
	 * 处理状态
	 * 
	 * @author leo
	 * 
	 */
	public enum LExcState {

		/**
		 * 异常处理成功
		 */
		Success,

		/**
		 * 异常处理失败
		 */
		Error,

		/**
		 * 异常未处理
		 */
		None
	}

	/**
	 * 捕获异常并处理方法
	 * 
	 * @param e
	 */
	void captureException(Exception e);

	/**
	 * 异常处理后通知完成
	 * 
	 * @param state
	 *            ：完成状态
	 */
	void handleAccomplish(LExcState state);
}
