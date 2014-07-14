package com.leo.base.util;

import com.leo.base.application.LApplication;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LMobileInfo {

	/**
	 * 获取IMEI码
	 * @return
	 */
	public static String getImei() {
		TelephonyManager mTm = (TelephonyManager) LApplication.getInstance()
				.getContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTm.getDeviceId();
	}

	/**
	 * 获取IMSI码
	 * @return
	 */
	public static String getImsi() {
		TelephonyManager mTm = (TelephonyManager) LApplication.getInstance()
				.getContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTm.getSubscriberId();
	}

	/**
	 * 获取手机型号
	 * @return
	 */
	public static String getMobileType() {
		return android.os.Build.MODEL; // 手机型号
	}
    
	/**
	 * 获取系统发布版本号
	 * @return
	 */
	
	public  static String getVersionRelease(){
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 终端设备编码
	 */
	public static final String TAG_END_FROM = "Android";

}
