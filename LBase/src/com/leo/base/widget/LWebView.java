package com.leo.base.widget;

import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leo.base.application.LApplication;
import com.leo.base.widget.webview.LWebViewClient;

/**
 * 
 * LWebView 简单集成了Session控制<br/>
 * 如果需要使用{@linkplain android.webkit.WebView#setWebViewClient(WebViewClient)
 * setWebViewClient(WebViewClient)} 方法 ，请在实现
 * {@linkplain com.leo.base.widget.webview.LWebViewClient LWebViewClient}<br/>
 * 框架会在页面加载完成之后，自动填充session
 * 
 * @author Chen Lei
 * @version 1.4.1
 * 
 */
public class LWebView extends WebView {

	/**
	 * 上下文对象
	 */
	private Context mContext;

	/**
	 * 请求路径
	 */
	private String mUrl;

	/**
	 * 额外的标头
	 */
	private Map<String, String> mExtraHeaders;

	/**
	 * WebViewClient子类
	 */
	private LWebViewClient mWebViewClient;

	public LWebView(Context context) {
		super(context);
		init(context);
	}

	public LWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		this.setWebViewClient(getWebViewClient());
	}

	@Override
	public void loadUrl(String url) {
		mUrl = url;
		mExtraHeaders = null;
		initCookie();
		new RequestAsyncTask().execute();
	}

	/**
	 * 开始加载
	 */
	public void loadUrl(String url, Map<String, String> extraHeaders) {
		mUrl = url;
		mExtraHeaders = extraHeaders;
		initCookie();
		new RequestAsyncTask().execute();

	}

	@Override
	public void setWebViewClient(WebViewClient client) {
		mWebViewClient = (LWebViewClient) client;
		super.setWebViewClient(mWebViewClient);
	}

	/**
	 * 初始化CookieManager
	 */
	private void initCookie() {
		CookieSyncManager.createInstance(mContext);
		CookieManager.getInstance().setAcceptCookie(true);
		CookieManager.getInstance().removeSessionCookie();
	}

	/**
	 * 同步CookieManager
	 */
	private void syncCookie() {
		LApplication.getInstance().setSessionValue("123456789");
		CookieManager.getInstance().setCookie(
				mUrl,
				LApplication.getInstance().getSessionKey() + "="
						+ LApplication.getInstance().getSessionValue());
		CookieSyncManager.getInstance().sync();
	}

	/**
	 * 加载
	 */
	@SuppressLint("NewApi")
	private void myLoadUrl() {
		if (Build.VERSION.SDK_INT >= 8) {
			super.loadUrl(mUrl, mExtraHeaders);
		} else {
			super.loadUrl(mUrl);
		}
	}

	private class RequestAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			SystemClock.sleep(20);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			syncCookie();
			myLoadUrl();
		}
	}

	private WebViewClient getWebViewClient() {
		if (mWebViewClient == null) {
			mWebViewClient = new LWebViewClient();
		}
		return mWebViewClient;
	}

}
