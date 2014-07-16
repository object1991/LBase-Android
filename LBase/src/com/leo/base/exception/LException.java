package com.leo.base.exception;

/**
 * 此类暂未正式使用
 * @author Chen Lei
 *
 */
import com.leo.base.application.LApplication;
import com.leo.base.util.L;

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
