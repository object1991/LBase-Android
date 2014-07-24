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

public class Fragment2 extends LFragment {

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
		return mViewGroup;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		textview = (TextView) getView().findViewById(R.id.jsonstring);
	}

	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		super.onResultHandler(msg, requestId);
		if (msg != null) {
			// textview.setText(msg.getStr());
			textview.setText("搜搜代码获取成功\n该页面使用了网络请求缓存!");
		}
	}

	public void load() {
		textview.setText("正在获取搜搜网页代码...");
		String url = "http://www.soso.com/";
		LReqEntity entity = new LReqEntity();
		entity.setUrl(url);
		entity.setUseCache(true);
		handler.request(entity, 0);
	}
}
