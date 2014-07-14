package com.leo.base.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * 
 * @author Chen Lei
 * @version 1.1.5
 *
 */
public class LViewPagerChangeListener implements OnPageChangeListener {

	public enum State {
		NONE, SCROLLING, SCROLL_SUCCESS
	}

	private ILOnPageScrollStateChanged mILOnPageScrollStateChanged;

	private ILOnPageScrolled mILOnPageScrolled;

	private ILOnPageSelected mILOnPageSelected;

	@Override
	public void onPageScrollStateChanged(int state) {
		if (mILOnPageScrollStateChanged == null)
			return;
		switch (state) {
		case 0:
			mILOnPageScrollStateChanged.onPageScrollStateChanged(State.NONE);
			break;
		case 1:
			mILOnPageScrollStateChanged
					.onPageScrollStateChanged(State.SCROLLING);
			break;
		case 2:
			mILOnPageScrollStateChanged
					.onPageScrollStateChanged(State.SCROLL_SUCCESS);
			break;
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (mILOnPageScrolled == null)
			return;
		mILOnPageScrolled.onPageScrolled(position, positionOffset,
				positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		if (mILOnPageSelected == null)
			return;
		mILOnPageSelected.onPageSelected(position);
	}

	/**
	 * 设置ILOnPageScrollStateChanged实例
	 * 
	 * @param onPageScrollStateChanged
	 */
	public void setILOnPageScrollStateChanged(
			ILOnPageScrollStateChanged onPageScrollStateChanged) {
		this.mILOnPageScrollStateChanged = onPageScrollStateChanged;
	}

	/**
	 * 设置ILOnPageScrolled实例
	 * 
	 * @param onPageScrolled
	 */
	public void setILOnPageScrolled(ILOnPageScrolled onPageScrolled) {
		this.mILOnPageScrolled = onPageScrolled;
	}

	/**
	 * 设置ILOnPageSelected实例
	 * 
	 * @param onPageSelected
	 */
	public void setILOnPageSelected(ILOnPageSelected onPageSelected) {
		this.mILOnPageSelected = onPageSelected;
	}

	/**
	 * 滑动状态接口
	 * 
	 * @author leo
	 * 
	 */
	public interface ILOnPageScrollStateChanged {
		/**
		 * 滑动状态方法<br />
		 * 三种状态：<br />
		 * State.NONE表示未滑动<br />
		 * State.SCROLLING表示正在滑动<br />
		 * State. SCROLL_SUCCESS表示滑动完成
		 * 
		 * @param state
		 *            ：滑动状态，使用{@link com.leo.base.listener.LViewPagerChangeListener.State}
		 */
		void onPageScrollStateChanged(State state);
	}

	/**
	 * 滑动参数接口
	 * 
	 * @author leo
	 * 
	 */
	public interface ILOnPageScrolled {
		/**
		 * 滑动参数方法
		 * 
		 * @param position
		 *            ：当前页面标识
		 * @param positionOffset
		 *            ：当前页面偏移的百分比
		 * @param positionOffsetPixels
		 *            ：当前页面偏移的像素值
		 */
		void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels);
	}

	/**
	 * 跳转完成接口
	 * 
	 * @author leo
	 * 
	 */
	public interface ILOnPageSelected {
		/**
		 * 页面跳转完成接口
		 * 
		 * @param position
		 *            ：当前位置标识
		 */
		void onPageSelected(int position);
	}

}
