package com.leo.base.exception;

import com.nostra13.universalimageloader.utils.L;

/**
 * 如网络请求时，需要后台登录，则抛出此异常<br/>
 * throw new LLoginException();
 * 
 * @author Chen Lei
 * @version 1.3.1
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
