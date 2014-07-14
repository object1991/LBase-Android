package com.example.lbaseexample.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.example.lbaseexample.R;
import com.example.lbaseexample.adapter.MyViewPagerAdapter;
import com.leo.base.activity.LActivity;
import com.leo.base.listener.LViewPagerChangeListener;
import com.leo.base.listener.LViewPagerChangeListener.ILOnPageSelected;

public class FragmentViewPagerActivity extends LActivity implements
		ActionBar.OnNavigationListener {

	private ViewPager mViewPager;
	private MyViewPagerAdapter adapter;

	private int index;

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_fragment_viewpager);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		adapter = new MyViewPagerAdapter(this, this.getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		LViewPagerChangeListener listener = new LViewPagerChangeListener();
		listener.setILOnPageSelected(new ILOnPageSelected() {

			@Override
			public void onPageSelected(int position) {
				index = position;
				if (mOnPagerIndexListener != null) {
					mOnPagerIndexListener.onPagerIndex(index);
				}
			}
		});
		mViewPager.setOnPageChangeListener(listener);
	}

	private OnPagerIndexListener mOnPagerIndexListener;

	public void setOnPagerIndexListener(OnPagerIndexListener listener) {
		this.mOnPagerIndexListener = listener;
	}

	public interface OnPagerIndexListener {
		void onPagerIndex(int index);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		this.index = itemPosition;
		this.mViewPager.setCurrentItem(itemPosition);
		return true;
	}

}
