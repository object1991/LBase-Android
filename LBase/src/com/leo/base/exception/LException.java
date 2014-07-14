package com.leo.base.exception;

/**
 * 此类暂未正式使用
 * @author Chen Lei
 *
 */
import com.leo.base.application.LApplication;

public abstract class LException extends Exception implements ILException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void printException(Exception e) {
		if (LApplication.getInstance().getIsOpenDebugMode()) {
			e.printStackTrace();
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

}
