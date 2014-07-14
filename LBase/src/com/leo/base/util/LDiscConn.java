package com.leo.base.util;

import java.util.List;

import com.leo.base.application.LApplication;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LDiscConn {

	private LDiscConn() {
	}

	/**
	 * 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
	 * 
	 * true已打开 false未打开
	 */
	public static boolean IsConnNet() {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) LApplication
				.getInstance().getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conManager != null) {
			NetworkInfo network = conManager.getActiveNetworkInfo();
			if (network != null) {
				bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
			}
		}
		return bisConnFlag;
	}

	/**
	 * 判断GPS是否打开
	 */
	public static boolean IsGPSEnabled() {
		LocationManager locationManager = (LocationManager) LApplication
				.getInstance().getContext()
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * 判断所用机型是否有GPS设备模块
	 */
	public static boolean HasGPSDevice() {
		LocationManager mgr = (LocationManager) LApplication.getInstance()
				.getContext().getSystemService(Context.LOCATION_SERVICE);
		if (mgr == null)
			return false;
		List<String> providers = mgr.getAllProviders();
		if (providers == null)
			return false;
		return providers.contains(LocationManager.GPS_PROVIDER);
	}
}
