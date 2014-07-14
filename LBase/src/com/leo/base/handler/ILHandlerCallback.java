package com.leo.base.handler;

import com.leo.base.entity.LMessage;

public interface ILHandlerCallback {

	void onResultHandler(LMessage msg, int requestId);
	
	boolean isDestroy();
	
}
