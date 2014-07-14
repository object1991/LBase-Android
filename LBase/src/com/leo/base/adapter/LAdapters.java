package com.leo.base.adapter;

import java.util.List;

import com.leo.base.application.LApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;

/**
 * <h1>来源：</h1> 无 <h1>用途：</h1> LAdapters 类为方便所有 Adapter 保存数据所创建 <h1>说明：</h1>
 * LAdapters 类共有 5 种对象 <li>{@linkplain android.content.Context Context} 上下文对象</li>
 * <li>{@linkplain java.util.List List<T>} 数据集合</li> <li>{@link Boolean} 是否使用
 * {@linkplain com.nostra13.universalimageloader.core.ImageLoader ImageLoader}
 * 异步加载图片标记</li> <li>
 * {@linkplain com.nostra13.universalimageloader.core.ImageLoader ImageLoader}
 * 是异步加载图片对象， 该对象由 {@linkplain com.leo.base.application.LApplication
 * LApplication} 配置而来。<br/>
 * {@linkplain com.nostra13.universalimageloader.core.ImageLoader ImageLoader}
 * 来源于：<a href="https://github.com/nostra13/Android-Universal-Image-Loader">
 * GitHub 网站 nostra13 的开源力作</a></li> <li>
 * {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
 * DisplayImageOptions} 是
 * {@linkplain com.nostra13.universalimageloader.core.ImageLoader ImageLoader}
 * 的加载配置文件， 该对象由 {@linkplain com.leo.base.application.LApplication LApplication}
 * 配置而来</li>
 * 
 * @author Chen Lei
 * @version 1.1.5
 * @param <T>
 *            传入此参数类型，以保证返回的数据为使用者需要的类型
 */
public final class LAdapters<T> {

	private Context mContext;

	private List<T> mList;

	private boolean mUseImage = false;

	private ImageLoader mImageLoader;

	private DisplayImageOptions mOption;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文对象
	 */
	public LAdapters(Context context) {
		this.mContext = context;
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文对象
	 * @param list
	 *            数据集合
	 */
	public LAdapters(Context context, List<T> list) {
		this.mContext = context;
		this.mList = list;
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文对象
	 * @param list
	 *            数据集合
	 * @param useImage
	 *            是否使用
	 *            {@linkplain com.nostra13.universalimageloader.core.ImageLoader
	 *            ImageLoader} 异步加载图片
	 */
	public LAdapters(Context context, List<T> list, boolean useImage) {
		this.mContext = context;
		this.mList = list;
		this.mUseImage = useImage;
	}

	/**
	 * 
	 * @return 返回上下文对象
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * 
	 * @param list
	 *            设置数据集合
	 */
	public void setList(List<T> list) {
		this.mList = list;
	}

	/**
	 * 
	 * @return 返回数据集合
	 */
	public List<T> getList() {
		return mList;
	}

	/**
	 * 
	 * @param useImage
	 *            设置是否使用
	 *            {@linkplain com.nostra13.universalimageloader.core.ImageLoader
	 *            ImageLoader} 异步加载图片
	 */
	public void setUseImage(boolean useImage) {
		this.mUseImage = useImage;
	}

	/**
	 * 
	 * @return 获取是否使用图片加载
	 */
	public boolean getUseImage() {
		return mUseImage;
	}

	/**
	 * 
	 * 
	 * @param imageLoader
	 *            自定义
	 *            {@linkplain com.nostra13.universalimageloader.core.ImageLoader
	 *            ImageLoader} 对象
	 */
	public void setImageLoader(ImageLoader imageLoader) {
		this.mImageLoader = imageLoader;
	}

	/**
	 * 
	 * 
	 * @return 获取
	 *         {@linkplain com.nostra13.universalimageloader.core.ImageLoader
	 *         ImageLoader} 实例
	 * @throws IllegalArgumentException
	 *             如果 {@linkplain LAdapters#setUseImage(boolean)} 为
	 *             false，或构造函数中未定义，或未设置此属性（默认为 false），则会抛出此异常
	 */
	public ImageLoader getImageLoader() {
		if (mUseImage) {
			if (mImageLoader == null) {
				mImageLoader = LApplication.getInstance().getImageLoader();
			}
			return mImageLoader;
		} else {
			throw new IllegalArgumentException(
					"你必须要setUseImage(true)之后才可以使用此方法");
		}
	}

	/**
	 * 
	 * @param imageOptions
	 *            设置
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            DisplayImageOptions} 对象
	 */
	public void setImageOptions(DisplayImageOptions imageOptions) {
		this.mOption = imageOptions;
	}

	/**
	 * 获取一个DisplayImageOptions实例
	 * 
	 * @return 获取
	 *         {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *         DisplayImageOptions}实例
	 * @throws IllegalArgumentException
	 *             如果 {@linkplain LAdapters#setUseImage(boolean)} 为
	 *             false，或构造函数中未定义，或未设置此属性（默认为 false），则会抛出此异常
	 */
	public DisplayImageOptions getImageOptions() {
		if (mUseImage) {
			if (mOption == null) {
				mOption = LApplication.getInstance().getImageOptions();
			}
			return mOption;
		} else {
			throw new IllegalArgumentException(
					"你必须要setHasImage(true)之后才可以使用此方法");
		}
	}

	/**
	 * 
	 * @return 获取数据集合总数量
	 */
	public int getCount() {
		int count = 0;
		if (mList != null)
			count = mList.size();
		return count;
	}

	/**
	 * 
	 * 
	 * @param position
	 *            传入所需要的下标
	 * @return 获取该下标的数据对象，为 T 类型
	 */
	public Object getItem(int position) {
		Object o = null;
		if (mList != null && position < mList.size())
			o = mList.get(position);
		return o;
	}

	/**
	 * 
	 * @param position
	 *            获取一个下标
	 * @return
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 清除数据集合所有数据，但该数据集合并不为空
	 */
	public void destroyList() {
		if (mList != null)
			mList.clear();
	}

	/**
	 * 在数据集合头部添加一条数据
	 * 
	 * @param data
	 *            传入一条T类型的数据
	 */
	public void addDataFirst(T data) {
		mList.add(0, data);
	}

	/**
	 * 在数据集合尾部添加一条数据
	 * 
	 * @param data
	 *            传入一条T类型的数据
	 */
	public void addDataLast(T data) {
		mList.add(data);
	}

	/**
	 * 在数据集合头部添加一些数据
	 * 
	 * @param datas
	 *            传入一个 T 类型的数据集合
	 */
	public void addDatasToFirst(List<T> datas) {
		for (T t : datas) {
			mList.add(0, t);
		}
	}

	/**
	 * 在数据集合尾部添加一些数据
	 * 
	 * @param datas
	 *            传入一个 T 类型的数据集合
	 */
	public void addDatasToLast(List<T> datas) {
		for (T t : datas) {
			mList.add(t);
		}
	}

	/**
	 * 删除一条数据
	 * 
	 * @param position
	 *            传入一条T类型的数据
	 */
	public void delData(T data) {
		mList.remove(data);
	}

	/**
	 * 删除一条数据
	 * 
	 * @param position
	 *            传入一个 int 类型的下标
	 */
	public void delData(int position) {
		mList.remove(position);
	}

}
