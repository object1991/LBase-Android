package com.example.lbaseexample.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lbaseexample.common.MHandler;
import com.example.lbaseexample.db.DBManager;
import com.example.lbaseexample.entity.ListEntity;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.L;
import com.leo.base.widget.T;

public class ListViewHandler extends MHandler {

	public ListViewHandler(LActivity activity) {
		super(activity);
	}

	@Override
	public LMessage onParse(String strs, int requestId) throws JSONException,
			LLoginException {
		LMessage msg = parseJson(strs);
		if (msg.getWhat() == 1) {
			List<ListEntity> list = msg.getList();
			// ... 清空本地数据库所有数据
			DBManager.get().delListEntitys();
			// ... 清加所有数据到本地数据库
			DBManager.get().addListEntitys(list);
		}
		return msg;
	}

	@Override
	protected void onNetWorkExc(int requestId) {
		T.ss("网络请求发现异常");
	}

	@Override
	protected void onParseExc(int requestId) {
		T.ss("数据解析发现异常");
	}

	@Override
	protected void onLoginError(int requestId) {
		T.ss("自动登录错误异常");
	}

	@Override
	protected void onLoginNone(int requestId) {
		T.ss("用户并未存有登录帐号异常");
	}

	@Override
	protected void onOtherExc(int requestId) {
		T.ss("其它异常");
	}

	@Override
	protected void onStop(int requestId) {
		L.i("线程停止");
	}

	private LMessage parseJson(String json) throws JSONException,
			LLoginException {
		LMessage msg = new LMessage();
		JSONObject newsObject = new JSONObject(json);
		/*
		 * if (newsObject.optInt("is_login", 0) == 0) { // ... 例： // ...
		 * 如果此次请求需要用户登录，而此处后台告诉前台， // ... 当前用户并未登录，可以在此处向上抛出未登录异常 throw new
		 * LLoginException(); // ... 框架捕获到此异常之后，会自动调用 MNetwork.doLogin()方法自动登录，
		 * // ... 登录成功后，自动重新请求 }
		 */
		msg.setWhat(1);
		JSONObject jsonObject = newsObject.getJSONObject("data");
		JSONArray blogsJson = jsonObject.getJSONArray("blogs");
		List<ListEntity> list = new ArrayList<ListEntity>();
		for (int i = 0; i < blogsJson.length(); i++) {
			JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
			ListEntity entity = new ListEntity();
			entity.id = newsInfoLeftObject.optInt("albid");
			entity.url = newsInfoLeftObject.optString("isrc");
			entity.content = newsInfoLeftObject.optString("msg");
			list.add(entity);
		}
		msg.setList(list);
		return msg;
	}

}
