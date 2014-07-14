package com.leo.base.exception;

import com.nostra13.universalimageloader.utils.L;

/**
 * 
 * @author Chen Lei
 *
 */
public class LLoginException extends LException {

	private static final long serialVersionUID = 1L;

	@Override
	public void captureException(Exception e) {
		L.w("用户未登录");
	}

	@Override
	public void handleAccomplish(LExcState state) {
		
	}

}
