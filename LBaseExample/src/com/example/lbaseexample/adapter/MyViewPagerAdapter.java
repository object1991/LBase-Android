package com.example.lbaseexample.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.lbaseexample.activity.FragmentViewPagerActivity;
import com.example.lbaseexample.activity.FragmentViewPagerActivity.OnPagerIndexListener;
import com.example.lbaseexample.activity.fragment.Fragment1;
import com.example.lbaseexample.activity.fragment.Fragment2;
import com.leo.base.activity.fragment.LFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter implements
		OnPagerIndexListener {

	private List<LFragment> fragments;

	private Fragment1 f1;
	private Fragment2 f2;

	public MyViewPagerAdapter(FragmentViewPagerActivity activity,
			FragmentManager fm) {
		super(fm);
		activity.setOnPagerIndexListener(this);
		fragments = new ArrayList<LFragment>();
		f1 = new Fragment1();
		f2 = new Fragment2();
		fragments.add(f1);
		fragments.add(f2);
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public void onPagerIndex(int index) {
		if (index == 0) {
			f1.load();
		} else {
			f2.load();
		}
	}

}
