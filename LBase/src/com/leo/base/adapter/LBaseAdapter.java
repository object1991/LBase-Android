package com.leo.base.adapter;

import java.util.List;

import com.leo.base.cache.LCache;
import com.leo.base.entity.LMessage;
import com.leo.base.handler.ILHandlerCallback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * <h1>来源：</h1> LBaseAdapter 继承自 {@linkplain android.widget.BaseAdapter
 * BaseAdapter} <h1>用途：</h1> 所有需要继承 {@linkplain android.widget.BaseAdapter
 * BaseAdapter} 类的 Adapter，只需继承此类即可 <h1>说明：</h1> 属性 <li>
 * {@linkplain com.leo.base.cache.LCache LCache}：用作缓存View</li> <li>
 * {@linkplain com.leo.base.adapter.LAdapters LAdapters}：用作获取共有数据</li> <li>
 * {@linkplain android.view.LayoutInflater LayoutInflater} 对象</li><br/>
 * 方法 <li>此类实现了 {@linkplain android.widget.BaseAdapter BaseAdapter} 所有的抽象方法， 并将
 * {@linkplain android.widget.BaseAdapter#getView(int, View, ViewGroup)
 * BaseAdapter.getView(int, View, ViewGroup)} 方法继承抽象，以便继承此类的 Adapter 构造自己的 Item。
 * <br/>
 * 继承此类后，使用者只需实现
 * {@linkplain android.widget.BaseAdapter#getView(int, View, ViewGroup)
 * BaseAdapter.getView(int, View, ViewGroup)} 方法便可，无需考虑其它<br/>
 * 此类还提供了多个操作缓存的方法</li> <li>当你使用了
 * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.entity.LReqEntity)
 * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
 * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此类的
 * {@linkplain com.leo.base.adapter.LBaseAdapter#resultHandler(com.leo.base.entity.LMessage)
 * LBaseAdapter.resultHandler(LMessage)} 方法，并将解析的结果
 * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回</li>
 * 
 * @author Chen Lei
 * @version 1.3.1
 * @param <T>
 *            传入此参数类型，以保证返回的数据为使用者需要的类型
 */
public abstract class LBaseAdapter<T> extends BaseAdapter implements ILHandlerCallback {

	/**
	 * 缓存View对象
	 */
	private LCache<View> cacheView = new LCache<View>();

	private LAdapters<T> mAdapter;

	private LayoutInflater mInflater;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            传入上下文对象
	 */
	public LBaseAdapter(Context context) {
		initAdapter(context, null, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            传入上下文对象
	 * @param list
	 *            数据集合
	 */
	public LBaseAdapter(Context context, List<T> list) {
		initAdapter(context, list, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            传入上下文对象
	 * @param list
	 *            数据集合
	 * @param hasImage
	 *            是否使用
	 *            {@linkplain com.nostra13.universalimageloader.core.ImageLoader
	 *            ImageLoader} 异步加载图片
	 */
	public LBaseAdapter(Context context, List<T> list, boolean useImage) {
		initAdapter(context, list, useImage);
	}

	/**
	 * 初始化 {@linkplain com.leo.base.adapter.LAdapters.LAdapters LAdapters} 实例
	 * 
	 * @param context
	 * @param list
	 * @param useImage
	 */
	private void initAdapter(Context context, List<T> list, boolean useImage) {
		mAdapter = new LAdapters<T>(context, list, useImage);
	}

	/**
	 * 
	 * @return 获取 {@linkplain com.leo.base.adapter.LAdapters.LAdapters
	 *         LAdapters} 实例
	 */
	public LAdapters<T> getAdapter() {
		return mAdapter;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (mAdapter != null) {
			count = mAdapter.getCount();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		Object o = null;
		if (mAdapter != null) {
			o = mAdapter.getItem(position);
		}
		return o;
	}

	@Override
	public long getItemId(int position) {
		long p = 0;
		if (mAdapter != null) {
			p = mAdapter.getItemId(position);
		}
		return p;
	}

	/**
	 * 抽象的getView<br/>
	 * 系统会自动调用getView方法来获取List数据的每一条显示内容<br/>
	 * 需要手动实现此方法
	 */
	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

	/**
	 * 获取一个LayoutInflater实例
	 * 
	 * @return
	 */
	public LayoutInflater getInflater() {
		if (mInflater == null) {
			mInflater = (LayoutInflater) this.getAdapter().getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return mInflater;
	}

	/**
	 * 清除缓存数据，并不刷新UI
	 */
	public void destroyConvertView() {
		if (cacheView != null)
			cacheView.clearAll();
	}

	/**
	 * 清除内存数据，并不刷新UI
	 */
	public void Destroy() {
		destroyConvertView();
		cacheView = null;
		if (mAdapter != null) {
			mAdapter.destroyList();
			mAdapter.setList(null);
		}
	}

	/**
	 * 添加缓存view
	 * 
	 * @param key
	 * @param value
	 */
	public void addConvertView(String key, View value) {
		if (!cacheView.isHaveCache(key)) {
			cacheView.put(key, value);
		}
	}

	/**
	 * 删除缓存view
	 * 
	 * @param key
	 */
	public void delConvertView(String key) {
		if (cacheView.isHaveCache(key)) {
			cacheView.remove(key);
		}
	}

	/**
	 * 查询缓存view
	 * 
	 * @param key
	 * @return
	 */
	public View getConvertView(String key) {
		View v = null;
		if (cacheView.isHaveCache(key)) {
			v = cacheView.get(key);
		}
		return v;
	}

	/**
	 * 清除所有缓存并刷新UI
	 */
	@Override
	public void notifyDataSetChanged() {
		destroyConvertView();
		super.notifyDataSetChanged();
	}

	/**
	 * 清除指定KEY的缓存并刷新UI
	 * 
	 * @param key
	 *            ：需要重新生成UI的KEY
	 */
	public void notifyDataSetChanged(String key) {
		delConvertView(key);
		super.notifyDataSetChanged();
	}

	/**
	 * 当你使用了
	 * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.net.LReqEntity)
	 * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
	 * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此方法，并将解析的结果
	 * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回
	 * 
	 * @param msg
	 */
	public void onResultHandler(LMessage msg, int requestId) {
		// ... 写入你需要的代码
	}

}
