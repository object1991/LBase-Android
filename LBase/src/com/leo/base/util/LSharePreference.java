package com.leo.base.util;

import com.leo.base.application.LApplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LSharePreference {
	private static String SP_NAME = LApplication.getInstance().getAppName();

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	private static LSharePreference instance;

	public static synchronized LSharePreference getInstance(Context context) {
		if (instance == null) {
			instance = new LSharePreference(context);
		}
		return instance;
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	private LSharePreference(Context context) {
		init(context);
	}

	/**
	 * 初使化
	 */
	private void init(Context context) {
		if (context != null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
			editor = sp.edit();
		}
	}

	/**
	 * 添加String
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 获取String
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return getString(key, null);
	}

	/**
	 * 获取String
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	/**
	 * 添加Int
	 * 
	 * @param key
	 * @param value
	 */
	public void setInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 获取Int
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 获取Int
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	/**
	 * 添加float
	 * 
	 * @param key
	 * @param value
	 */
	public void setFloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	/**
	 * 获取float
	 * 
	 * @param key
	 * @return
	 */
	public float getFloat(String key) {
		return getFloat(key, 0.0f);
	}

	/**
	 * 获取float
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public float getFloat(String key, float defValue) {
		return sp.getFloat(key, defValue);
	}

	/**
	 * 添加boolean
	 * 
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获取boolean
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	/**
	 * 获取boolean
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void delContent(String key) {
		editor.remove(key);
		editor.commit();
	}
}
