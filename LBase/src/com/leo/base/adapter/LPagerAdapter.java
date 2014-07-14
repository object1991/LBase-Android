package com.leo.base.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * <h1>来源：</h1> LPagerAdapter 继承自
 * {@linkplain android.support.v4.view.PagerAdapter PagerAdapter}
 * 
 * @author Chen Lei
 * @version 1.1.5
 * @param <T>
 *            传入此参数类型，以保证返回的数据为使用者需要的类型
 */
public abstract class LPagerAdapter<T> extends PagerAdapter {

	private LAdapters<T> mAdapter;

	private SparseArray<View> mViewList;

	public LPagerAdapter(Context context) {
		initAdapters(context, null, false);
	}

	public LPagerAdapter(Context context, List<T> list) {
		initAdapters(context, list, false);
	}

	public LPagerAdapter(Context context, List<T> list, boolean useImage) {
		initAdapters(context, list, useImage);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            传入上下文对象
	 * @param list
	 *            传入T类型的{@link List}
	 * @param hasImage
	 *            传入是否需要图片下载
	 */
	private void initAdapters(Context context, List<T> list, boolean useImage) {
		mAdapter = new LAdapters<T>(context, list, useImage);
		this.mViewList = new SparseArray<View>();
	}

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
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (mViewList == null || mViewList.size() <= 0 || position < 0
				|| mViewList.size() <= position) {
			return;
		}
		View view = mViewList.get(position);
		if (view == null) {
			return;
		}
		container.removeView(view);
		mViewList.remove(position);
	}

	/**
	 * 添加页面样式
	 */
	@Override
	public abstract Object instantiateItem(ViewGroup container, int position);

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	/**
	 * 清除View
	 */
	public void clearView() {
		if (mViewList != null) {
			mViewList.clear();
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 销毁方法
	 */
	public void destroy() {
		clearView();
		mViewList = null;
		if (mAdapter != null) {
			mAdapter.destroyList();
			mAdapter.setList(null);
		}
	}

	public SparseArray<View> getViewList() {
		return mViewList;
	}

}
