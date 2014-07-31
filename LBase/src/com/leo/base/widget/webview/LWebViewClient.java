package com.leo.base.widget.webview;

import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leo.base.application.LApplication;
import com.leo.base.util.L;
import com.leo.base.util.LFormat;

public class LWebViewClient extends WebViewClient {

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		String cookie = CookieManager.getInstance().getCookie(url);
		if (!LFormat.isEmpty(cookie)) {
			String[] cookies = cookie.split(";");
			if (cookies != null && cookies.length > 0) {
				for (String str : cookies) {
					int index = str.indexOf(LApplication.getInstance()
							.getSessionKey());
					if (index != -1) {
						String sessionValue = str.substring(
								str.indexOf("=") + 1, str.length());
						LApplication.getInstance()
								.setSessionValue(sessionValue);
						L.e(sessionValue);
					}
				}
			}
		}
	}

}
