package com.example.lbaseexample.common;

import android.content.res.Resources;

import com.example.lbaseexample.R;
import com.leo.base.application.LApplication;

public class MApplication extends LApplication {

	/**
	 * 实例
	 */
	private static MApplication instance;
	
	/**
	 * 本地数据库名称
	 */
	private static final String DB_NAME = "LBASE_EXAMPLE_DB_NAME";

	/**
	 * 本地数据库版本
	 */
	private static final int DB_VERSION = 2;

	/**
	 * 本地数据库创建表集合
	 */
	private static String[] DB_CREATE_TABLES;

	/**
	 * 本地数据库删除表集合
	 */
	private static String[] DB_DROP_TABLES;

	/**
	 * 本地缓存数量
	 */
	private static final int CACHE_COUNT = 30;

	@Override
	public void onCreate() {
		super.onCreate();
		Resources resources = getResources();
		// 设置实例
		setLApplication(this);
		// 设置数据库
		setDBInfo(DB_NAME, DB_VERSION, getDBCreateTables(), getDBDropTables());
		// 打开DEBUG模式
		setOpenDebugMode(true);
		// 设置APP名称
		setAppName(resources.getString(R.string.app_name));
		// 设置服务器路径
		setAppServiceUrl(resources.getString(R.string.app_service_url));
		// 设置缓存数量
		setCacheCount(CACHE_COUNT);
		// 设置默认图片
		setDefaultImage(R.drawable.ic_launcher);
	}

	/**
	 * 获取一个Application对象
	 * 
	 * @return
	 */
	public static synchronized MApplication get() {
		if (instance == null) {
			instance = (MApplication) getInstance();
		}
		return instance;
	}
	
	/**
	 * 获取创建数据库数组
	 * 
	 * @return
	 */
	private String[] getDBCreateTables() {
		if (DB_CREATE_TABLES == null) {
			DB_CREATE_TABLES = getResources().getStringArray(
					R.array.create_tables);
		}
		return DB_CREATE_TABLES;
	}

	/**
	 * 获取删除数据库数组
	 * 
	 * @return
	 */
	private String[] getDBDropTables() {
		if (DB_DROP_TABLES == null) {
			DB_DROP_TABLES = getResources().getStringArray(R.array.drop_tables);
		}
		return DB_DROP_TABLES;
	}

}
