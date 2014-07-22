package com.example.lbaseexample.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lbaseexample.R;
import com.example.lbaseexample.handler.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.entity.LReqEntity;

public class Fragment1 extends LFragment {

	private TextView textview;

	private FragmentHandler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new FragmentHandler(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup mViewGroup = (ViewGroup) inflater.inflate(
				R.layout.fragment_view1, container, false);
		textview = (TextView) mViewGroup.findViewById(R.id.jsonstring);
		return mViewGroup;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		load();
	}

	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		super.onResultHandler(msg, requestId);
		if (msg != null) {
			// textview.setText(msg.getStr());
			textview.setText("百度代码获取成功\n该页面未使用网络请求缓存!");
		}
	}

	public void load() {
		textview.setText("正在获取百度网页代码...");
		String url = "http://www.baidu.com";
		handler.request(new LReqEntity(url), 0);
	}

}
