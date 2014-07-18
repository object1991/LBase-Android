package com.leo.base.exception;

import com.leo.base.application.LApplication;
import com.leo.base.util.L;

/**
 * 此类暂未正式使用
 * 
 * @author Chen Lei
 * @version 1.3.1
 * 
 */
public abstract class LException extends Exception implements ILException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void printException(Exception e) {
		if (LApplication.getInstance().getIsOpenDebugMode()) {
			L.e(LException.getStackMsg(e));
		}
	}

	public void printException(Exception e, boolean isCapture) {
		printException(e);
		if (isCapture) {
			captureException(e);
		}
	}

	@Override
	public abstract void captureException(Exception e);

	@Override
	public abstract void handleAccomplish(LExcState state);

	/**
	 * 获取基本异常信息
	 * @param e
	 * @return
	 */
	public static String getStackMsg(Throwable e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackArray = e.getStackTrace();
		for (int i = 0; i < stackArray.length; i++) {
			StackTraceElement element = stackArray[i];
			sb.append(element.toString() + "\n");
		}
		sb.append(e);
		return sb.toString();
	}

}
