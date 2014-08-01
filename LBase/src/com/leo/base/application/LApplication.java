package com.leo.base.application;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.WindowManager;

import com.leo.base.activity.LActivity;
import com.leo.base.db.LDBHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * <h1>来源：</h1> LApplication 继承自 {@linkplain android.app.Application
 * Application} <h1>用途：</h1> 存放着基本的全局属性 <h1>说明：</h1> <li>
 * {@linkplain android.content.Context Context} 对象</li> <li>
 * {@linkplain android.support.v4.app.FragmentManager FragmentManager} 对象</li>
 * <li>{@linkplain com.leo.base.db.LDBHelper LDBHelper} 对象，对本地数据库操作属性</li> <li>
 * String 数据库名称，使用本地数据库，需设置此项</li> <li>int 数据库版本号，使用本地数据库，需设置此项</li> <li>
 * String[] 数据库创建表语句集合</li> <li>String[] 数据库删除表语句集合</li> <li>String 应用名称</li>
 * <li>String 应用后台服务器主路径</li> <li>int 全局默认缓存数量，主要应用于
 * {@linkplain com.leo.base.cache.LCache}</li> <li>boolean 是否销毁所有Activity</li>
 * <li>{@linkplain java.io.File File} 图片默认缓存路径</li> <li>
 * {@linkplain com.nostra13.universalimageloader.core.ImageLoader ImageLoader}
 * 图片异步加载对象<br/>
 * {@linkplain com.nostra13.universalimageloader.core.ImageLoader ImageLoader}
 * 来源于：<a href="https://github.com/nostra13/Android-Universal-Image-Loader">
 * GitHub 网站 nostra13 的开源力作</a></li> <li>
 * {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
 * DisplayImageOptions} 是
 * {@linkplain com.nostra13.universalimageloader.core.ImageLoader ImageLoader}
 * 的加载配置文件</li> <li>int 异步加载图片默认图片资源ID</li> <li>
 * {@linkplain android.graphics.drawable.Drawable Drawable} 异步加载图片默认图片对象</li>
 * <li>String 服务器后台 SESSION 的 KEY 值，配置于
 * {@linkplain com.leo.base.application.LConfig LStaticVariables}</li> <li>
 * String 服务器后台 SESSION 的 VALUE 值，该值在网络请求时将自动填充</li> <li>int 屏幕宽度和高度，单位（px）</li>
 * 
 * @author Chen Lei
 * @version 1.3.1
 * 
 */
public class LApplication extends Application {

	/**
	 * 全局上下文对象
	 */
	private Context mContext;

	/**
	 * 将所有FragmentActivity存放在链表中，便于管理
	 */
	private List<FragmentActivity> activityList;

	/**
	 * 全局FragmentManager实例
	 */
	private FragmentManager mFragmentManager;

	/**
	 * 全局LApplication唯一实例
	 */
	private static LApplication instance;

	/**
	 * 全局数据库对象
	 */
	private LDBHelper mDBHelper;

	/**
	 * 全局数据库名称
	 */
	private String appDBName;

	/**
	 * 全局数据库版本
	 */
	private int appDBVersion;

	/**
	 * 全局数据库创建表的语句数组
	 */
	private String[] appDBCreateTables;

	/**
	 * 全局数据库删除表的语句数组
	 */
	private String[] appDBDropTables;

	/**
	 * 全局的是否打开DEBUG模式<br />
	 * 默认为true
	 */
	private boolean openDebugMode = true;

	/**
	 * 全局APP标识名称
	 */
	private String appName;

	/**
	 * 全局APP服务器主路径
	 */
	private String appServiceUrl;

	/**
	 * 全局数据缓存数量<br />
	 * 默认为20
	 */
	private int cacheCount = 20;

	/**
	 * 是否允许销毁所有Activity<br />
	 * 默认为false
	 */
	private boolean destroyActivitys = false;

	/**
	 * 全局缓存文件存储文件对象
	 */
	private File cacheFile;

	/**
	 * 全局ImageLoader实例
	 */
	private ImageLoader imageLoader;

	/**
	 * 全局DisplayImageOptions实例
	 */
	private DisplayImageOptions mOption;

	/**
	 * 全局默认图片ID，默认值为-1
	 */
	private int mDefaultImageId = -1;

	/**
	 * 全局Session的Key值
	 */
	private String SessionKey = LConfig.SESSION_KEY;

	/**
	 * 全局Session的Value值
	 */
	private String SessionValue;

	/**
	 * 全局屏幕宽度
	 */
	private int mDiaplayWidth;

	/**
	 * 全局屏幕高度
	 */
	private int mDiaplayHeight;

	/**
	 * 
	 * @return 获取上下文对象
	 */
	public Context getContext() {
		if (mContext == null) {
			mContext = this;
		}
		return mContext;
	}

	/**
	 * 设置上下文对象
	 * 
	 * @param mContext
	 */
	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	/**
	 * 添加一个Activity到数组里
	 * 
	 * @param activity
	 */
	public void addActivity(FragmentActivity activity) {
		if (this.activityList == null) {
			activityList = new LinkedList<FragmentActivity>();
		}
		this.activityList.add(activity);
	}

	/**
	 * 删除一个Activity在数组里
	 * 
	 * @param activity
	 */
	public void delActivity(FragmentActivity activity) {
		if (activityList != null) {
			activityList.remove(activity);
		}
	}

	/**
	 * 设置一个 FragmentManager 对象
	 * 
	 * @param fm
	 */
	public void setFragmentManager(FragmentManager fm) {
		this.mFragmentManager = fm;
	}

	/**
	 * 
	 * @return 获取一个 FragmentManager 对象
	 */
	public FragmentManager getFragmentManager() {
		return this.mFragmentManager;
	}

	/**
	 * 获取一个LApplication的实例
	 * 
	 * @return
	 */
	public static synchronized LApplication getInstance() {
		if (instance == null) {
			instance = new LApplication();
		}
		return instance;
	}

	/**
	 * 设置一个LApplication的实例
	 * 
	 * @param app
	 */
	public static void setLApplication(LApplication app) {
		instance = app;
	}

	/**
	 * 获取一个DBHelper实例并打开数据库
	 * 
	 * @return
	 */
	public LDBHelper getDBHelper() {
		if (mDBHelper == null) {
			mDBHelper = LDBHelper.Instance(getContext(), appDBName,
					appDBVersion);
			mDBHelper.setTableCreateText(appDBCreateTables);
			mDBHelper.setTableDropText(appDBDropTables);
			mDBHelper.openDataBase();
		}
		return mDBHelper;
	}

	/**
	 * 关闭数据库连接
	 */
	public void closeDBHelper() {
		if (mDBHelper != null) {
			mDBHelper.closeDataBase();
		}
	}

	/**
	 * 设置DBHelper实例信息
	 * 
	 * @param dbName
	 *            ：数据库名称
	 * @param dbVersion
	 *            ：数据库版本
	 * @param dbCreateTables
	 *            ：数据库创建表语句集合
	 * @param dbDropTables
	 *            ：数据库删除表语句集合
	 */
	public void setDBInfo(String dbName, int dbVersion,
			String[] dbCreateTables, String[] dbDropTables) {
		this.appDBName = dbName;
		this.appDBVersion = dbVersion;
		this.appDBCreateTables = dbCreateTables;
		this.appDBDropTables = dbDropTables;
	}

	/**
	 * 获取是否启用Debug模式
	 * 
	 * @return
	 */
	public boolean getIsOpenDebugMode() {
		return openDebugMode;
	}

	/**
	 * 设置是否启用Debug模式
	 * 
	 * @param openDebugMode
	 */
	public void setOpenDebugMode(boolean openDebugMode) {
		this.openDebugMode = openDebugMode;
	}

	/**
	 * 获取应用标识名称
	 * 
	 * @return
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * 设置应用标识名称
	 * 
	 * @param appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 获取应用主服务器路径
	 * 
	 * @return
	 */
	public String getAppServiceUrl() {
		return appServiceUrl;
	}

	/**
	 * 设置应用主服务器路径
	 * 
	 * @param appServiceUrl
	 */
	public void setAppServiceUrl(String appServiceUrl) {
		this.appServiceUrl = appServiceUrl;
	}

	/**
	 * 获取应用内存缓存数量
	 * 
	 * @return
	 */
	public int getCacheCount() {
		return cacheCount;
	}

	/**
	 * 设置应用内存缓存数量
	 * 
	 * @param cacheCount
	 */
	public void setCacheCount(int cacheCount) {
		this.cacheCount = cacheCount;
	}

	/**
	 * 获取应用是否允许销毁所有Activity
	 * 
	 * @return
	 */
	public boolean getIsDestroyActivitys() {
		return destroyActivitys;
	}

	/**
	 * 设置应用是否允许销毁所有Activity
	 * 
	 * @param destroyActivitys
	 */
	public void setDestroyActivitys(boolean destroyActivitys) {
		this.destroyActivitys = destroyActivitys;
	}

	/**
	 * 获取应用文件缓存路径文件
	 * 
	 * @return
	 */
	public File getCacheFile() {
		if (cacheFile == null) {
			cacheFile = getCacheDir();
		}
		return cacheFile;
	}

	/**
	 * 设置应用文件缓存路径文件
	 * 
	 * @param cacheFile
	 */
	public void setCacheFile(File cacheFile) {
		this.cacheFile = cacheFile;
	}

	/**
	 * 设置ImageLoader对象
	 * 
	 * @param configuration
	 */
	public void setImageLoader(ImageLoaderConfiguration configuration) {
		if (configuration != null) {
			ImageLoader.getInstance().init(configuration);
			imageLoader = ImageLoader.getInstance();
		}
	}

	/**
	 * 获取ImageLoader对象
	 * 
	 * @return
	 */
	public ImageLoader getImageLoader() {
		if (imageLoader == null) {
			ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
					getContext())
					.memoryCacheExtraOptions(480, 800)
					.diskCacheExtraOptions(480, 800, null)
					.threadPoolSize(3)
					.threadPriority(Thread.NORM_PRIORITY - 1)
					.tasksProcessingOrder(QueueProcessingType.FIFO)
					// 调用此方法将不会生成每张图片的各个尺寸的缓存图片
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
					.memoryCacheSize(2 * 1024 * 1024)
					.memoryCacheSizePercentage(13)
					.diskCache(new UnlimitedDiscCache(getCacheDir()))
					.diskCacheSize(50 * 1024 * 1024)
					.diskCacheFileCount(500)
					.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
					.imageDownloader(new BaseImageDownloader(getContext()))
					.defaultDisplayImageOptions(getImageOptions())
					// .writeDebugLogs()
					.build();
			ImageLoader.getInstance().init(configuration);
			imageLoader = ImageLoader.getInstance();
		}
		return imageLoader;
	}

	/**
	 * 设置DisplayImageOptions对象
	 * 
	 * @param options
	 */
	public void setImageOptions(DisplayImageOptions options) {
		this.mOption = options;
	}

	/**
	 * 获取DisplayImageOptions对象
	 * 
	 * @return
	 */
	public DisplayImageOptions getImageOptions() {
		return getImageOptions(null);
	}

	/**
	 * 获取DisplayImageOptions对象
	 * 
	 * @param drawableId
	 * @return
	 */
	public DisplayImageOptions getImageOptions(int drawableId) {
		return getImageOptions(getResources().getDrawable(drawableId));
	}

	/**
	 * 获取DisplayImageOptions对象
	 * 
	 * @return
	 */
	public DisplayImageOptions getImageOptions(Drawable drawable) {
		if (mOption == null) {
			if (mDefaultImageId == -1) {
				mDefaultImageId = android.R.drawable.ic_delete;
			}
			mOption = new DisplayImageOptions.Builder()
					.showImageOnLoading(mDefaultImageId)
					.showImageForEmptyUri(mDefaultImageId)
					.showImageOnFail(mDefaultImageId)
					.resetViewBeforeLoading(false).delayBeforeLoading(100)
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
					.cacheInMemory(true).cacheOnDisk(true)
					.bitmapConfig(Bitmap.Config.ARGB_8888)
					.displayer(new SimpleBitmapDisplayer()).build();
		}
		return mOption;
	}

	/**
	 * 设置默认图片
	 * 
	 * @param defaultImageId
	 *            ：图片ID
	 */
	public void setDefaultImage(int defaultImageId) {
		this.mDefaultImageId = defaultImageId;
	}

	/**
	 * 设置Session的Key值
	 * 
	 * @param sessionKey
	 */
	public void setSessionKey(String sessionKey) {
		this.SessionKey = sessionKey;
	}

	/**
	 * 获取Session的Key值
	 * 
	 * @return
	 */
	public String getSessionKey() {
		return this.SessionKey;
	}

	/**
	 * 设置Session的Value值
	 * 
	 * @param sessionValue
	 */
	public void setSessionValue(String sessionValue) {
		this.SessionValue = sessionValue;
	}

	/**
	 * 获取Session的Value值
	 * 
	 * @return
	 */
	public String getSessionValue() {
		return this.SessionValue;
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	public int getDiaplayWidth() {
		if (mDiaplayWidth <= 0) {
			computeDiaplayWidthAndHeight();
		}
		return mDiaplayWidth;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @return
	 */
	public int getDiaplayHeight() {
		if (mDiaplayHeight <= 0) {
			computeDiaplayWidthAndHeight();
		}
		return mDiaplayHeight;
	}

	private void computeDiaplayWidthAndHeight() {
		WindowManager mWindowManager = ((LActivity) getContext())
				.getWindowManager();
		Display display = mWindowManager.getDefaultDisplay();
		mDiaplayWidth = display.getWidth();
		mDiaplayHeight = display.getHeight();
	}

	/**
	 * 完全退出应用<br />
	 * 需要设置destroyActivitys为true
	 */
	public void exitApp() {
		if (destroyActivitys) {
			for (FragmentActivity f : activityList) {
				f.finish();
			}
			System.exit(0);
		}
	}
}
