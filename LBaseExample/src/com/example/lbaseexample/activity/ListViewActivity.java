package com.example.lbaseexample.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lbaseexample.R;
import com.example.lbaseexample.adapter.ListViewAdapter;
import com.example.lbaseexample.db.DBManager;
import com.example.lbaseexample.entity.ListEntity;
import com.example.lbaseexample.handler.ListViewHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEncode;
import com.leo.base.net.LReqEntity;
import com.leo.base.net.LReqMothed;
import com.leo.base.util.T;

public class ListViewActivity extends LActivity {

	private ListView mListView;
	private ListViewAdapter adapter;

	private ListViewHandler handler;

	private ProgressDialog progress;

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_listview);
		mListView = (ListView) findViewById(R.id.listview);
		handler = new ListViewHandler(this);
		initData();
	}

	/**
	 * 加载数据
	 */
	private void initData() {
		List<ListEntity> data = DBManager.get().getListEntitys();
		if (data != null && data.size() > 0) {
			setData(data);
		} else {
			sendRequest();
			progress = new ProgressDialog(this);
			progress.setMessage("正在加载数据...");
			progress.show();
		}
	}

	/**
	 * 请求网络，当请求成功后，框架会调用resultHandler方法，
	 */
	private void sendRequest() {
		// ... 网络请求地址，此URL来自于网络
		String url = "http://www.duitang.com/album/1733789/masn/p/2/24/";	
		// ... 网络请求参数，默认为空
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", "value");	
		// ... 网络请求编码方式，默认为UTF-8
		LReqEncode encode = LReqEncode.UTF8;
		// ... 网络请求方式，默认为Get，当请求参数不为空时，默认为Post
		LReqMothed mothed = LReqMothed.GET;
		
		LReqEntity entity = new LReqEntity();
		entity.setUrl(url);
		entity.setReqMode(mothed);
		handler.startLoadingData(entity, 1);
	}

	private void setData(List<ListEntity> data) {
		if (adapter == null) {
			adapter = new ListViewAdapter(this, data);
			mListView.setAdapter(adapter);
		} else {
			adapter.getAdapter().setList(data);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		super.onResultHandler(msg, requestId);
		// ... 如果请求过程中发现异常，MHandler 会将清空，
		// ... 如需修改请自主到MHandler中修改
		if (msg != null) {
			if (msg.getWhat() == 1) {
				T.ss("获取数据成功");
				List<ListEntity> data = msg.getList();
				setData(data);
			} else {
				T.ss("获取数据失败");
			}
		}
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
		}
	}

}
