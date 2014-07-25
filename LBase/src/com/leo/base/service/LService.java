package com.leo.base.service;

import com.leo.base.entity.LMessage;
import com.leo.base.handler.ILHandlerCallback;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 
 * @author Chen Lei
 * @version 1.3.9
 *
 */
public class LService extends Service implements ILHandlerCallback {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		// ... 写入你需要的代码
	}

}
