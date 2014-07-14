package com.example.lbaseexample.common;

import com.leo.base.net.LNetwork;
import com.leo.base.net.LReqEntity;

public class MNetwork extends LNetwork {

	public MNetwork(LReqEntity entity, int requestId) {
		super(entity, requestId);
	}

	@Override
	public LoginState doLogin() {
		// ... 你可以在这里操作登录
		// ... 当你请求服务器，服务器发现你的登录信息已失效，LBase会自动调用此方法，帮助你登录
		// ... 返回值有三种：
		// ... LoginState.SUCCESS 自动登录成功
		// ... LoginState.ERROR 自动登录失败
		// ... LoginState.NONE 未发现登录信息
		// ... 例如：
		/*
		 * String username =
		 * LSharePreference.getInstance(MApplication.get().getContext
		 * ()).getString("username"); String password =
		 * LSharePreference.getInstance
		 * (MApplication.get().getContext()).getString("password");
		 * if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
		 * return LoginState.NONE; } String url =
		 * MApplication.get().getAppServiceUrl() + "login"; Map<String, String>
		 * params = new HashMap<String, String>(); params.put("username",
		 * username); params.put("password", password); ReqEncode reqEncode =
		 * ReqEncode.UTF8; ReqMothed reqMothed = ReqMothed.POST; String result =
		 * null; try { result = LCaller.doConn(url, params, false, reqMothed,
		 * reqEncode); } catch (Exception e) { e.printStackTrace(); return
		 * LoginState.ERROR; } try { JSONObject jsonObject = new
		 * JSONObject(result); if (jsonObject.optInt("error") > 0) { // ... //
		 * 如果JSON返回错误信息大于0，则认为登录失败，否则登录成功 return LoginState.ERROR; } } catch
		 * (JSONException e) { e.printStackTrace(); return LoginState.ERROR; }
		 */
		return LoginState.SUCCESS;
	}

}
