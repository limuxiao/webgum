package com.ule.example.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ule.example.R;
import com.ule.webgum.sysweb.SystemWebSettingTool;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-31 08:23
 * @Creator: Liy
 * @Version: 1.0
 */

public class TestActivity extends Activity {

	WebView webView;

	String preLoadJs = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		webView = findViewById(R.id.activity_test_wv);
		initWebView();


		String url = "file:///android_asset/webgum/index.html";
		webView.loadUrl(url);
	}

	void initWebView(){
		SystemWebSettingTool.settingWebView(getApplicationContext(), webView);

		webView.setWebViewClient(webViewClient);
		webView.setWebChromeClient(webChromeClient);

	}



	private WebViewClient webViewClient = new WebViewClient() {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
			return super.shouldOverrideUrlLoading(view, request);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.e("TestActivity","onPageStarted");
			Log.e("TestActivity","url:" + url);
			if(TextUtils.isEmpty(preLoadJs)){
				preLoadJs = buildJsInject();
			}
			webView.loadUrl("javascript:" + preLoadJs);
			super.onPageStarted(view, url, favicon);

		}

		@Override
		public void onPageCommitVisible(WebView view, String url) {
			super.onPageCommitVisible(view, url);
		}

		@Override
		public void onPageFinished(WebView view, final String url) {
			Log.e("TestActivity","onPageFinished");
//			if(TextUtils.isEmpty(preLoadJs)){
//				preLoadJs = buildJsInject();
//			}
//			webView.loadUrl("javascript:" + preLoadJs);
			super.onPageFinished(view, url);
		}
	};

	private WebChromeClient webChromeClient = new WebChromeClient() {

		@Override
		public void onProgressChanged(WebView view, int progress) {
			super.onProgressChanged(view, progress);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			return super.onJsAlert(view, url, message, result);
		}

		@Override
		public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
	};


	public String buildJsInject(){
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = this.getAssets().open("webgum/wg_android.js");

			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			sb.append(new String(buffer));
			is.close();
//			Log.e("TestActivity","----1----:" + sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
