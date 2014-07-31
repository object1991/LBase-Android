package com.example.lbaseexample.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.lbaseexample.R;
import com.leo.base.activity.LActivity;
import com.leo.base.widget.LWebView;
import com.leo.base.widget.T;
import com.leo.base.widget.webview.LWebViewClient;

public class WebViewActivity extends LActivity {

	private LWebView mWebView;

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_webview);
		mWebView = (LWebView) findViewById(R.id.webview_webview);
		init();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init() {
		// 支持JavaScript
		mWebView.getSettings().setJavaScriptEnabled(true);
		// 支持缩放
		mWebView.getSettings().setBuiltInZoomControls(false);
		// 支持保存数据
		mWebView.getSettings().setSaveFormData(false);

		// 清除缓存
		mWebView.clearCache(true);
		// 清除历史记录
		mWebView.clearHistory();
		// LWebView会自动为您的请求填充session，使用者无需自主填充。
		// 如果需要设置WebViewClient回调，请在此处设置为LWebViewClient，不影响使用
		mWebView.setWebViewClient(new LWebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				T.ss("加载完成...");
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				T.ss("加载开始...");
			}

		});
		// 联网载入
		mWebView.loadUrl("http://www.youku.com");
	}

}
