package com.leo.base.util;

import com.leo.base.application.LApplication;

import android.content.Context;
import android.util.Log;


/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class L {
	
	private static Context mContext;
	
	/**
	 * 禁止实例化
	 */
	private L(){}
	
	private static final String TAG = LApplication.getInstance().getAppName();  
	
	/**
	 * 打印消息类传入的字符串
	 * @param msg
	 */
	public static void i(String msg) {
		i(TAG, msg);
	}

	/**
	 * 打印DEBUG类传入的字符串
	 * @param msg
	 */
	public static void d(String msg) {
		d(TAG, msg);
	}

	/**
	 * 打印ERROR类传入的字符串
	 * @param msg
	 */
	public static void e(String msg) {
		e(TAG, msg);
	}

	/**
	 * 打印详细类传入的字符串
	 * @param msg
	 */
	public static void v(String msg) {
		v(TAG, msg);
	}
	
	/**
	 * 打印消息类传入的字符串
	 * @param msg
	 */
	public static void i(int msg) {
		i(TAG, msg);
	}
	
	/**
	 * 打印DEBUG类传入的字符串
	 * @param msg
	 */
	public static void d(int msg) {
		d(TAG, msg);
	}
	
	/**
	 * 打印ERROR类传入的字符串
	 * @param msg
	 */
	public static void e(int msg) {
		e(TAG, msg);
	}
	
	/**
	 * 打印详细类传入的字符串
	 * @param msg
	 */
	public static void v(int msg) {
		v(TAG, msg);
	}
	
	/**
	 * 打印消息类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, int message) {
		String str = getContext().getResources().getString(message);
		i(tag, str);
	}
	
	/**
	 * 打印DEBUG类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, int message) {
		String str = getContext().getResources().getString(message);
		d(tag, str);
	}
	
	/**
	 * 打印ERROR类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, int message) {
		String str = getContext().getResources().getString(message);
		e(tag, str);
	}
	
	/**
	 * 打印详细类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, int message) {
		String str = getContext().getResources().getString(message);
		v(tag, str);
	}
	
	/**
	 * 打印消息类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (LApplication.getInstance().getIsOpenDebugMode())
			Log.i(tag, msg);
	}

	/**
	 * 打印DEBUG类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (LApplication.getInstance().getIsOpenDebugMode())
			Log.d(tag, msg);
	}

	/**
	 * 打印ERROR类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (LApplication.getInstance().getIsOpenDebugMode())
			Log.e(tag, msg);
	}

	/**
	 * 打印详细类传入的字符串
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (LApplication.getInstance().getIsOpenDebugMode())
			Log.v(tag, msg);
	}
	
	private static Context getContext() {
		if(mContext == null) 
			mContext = LApplication.getInstance().getContext();
		return mContext;
	}

}
