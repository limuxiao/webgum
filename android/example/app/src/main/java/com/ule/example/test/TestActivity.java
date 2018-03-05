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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ule.example.R;
import com.ule.webgum.Webgum;
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
		webView = (WebView) findViewById(R.id.activity_test_wv);
		initWebView();


		String url = "file:///android_asset/webgum/edit2.html";
		webView.loadUrl(url);
	}

	void initWebView(){


		WebSettings webSettings = webView.getSettings();

		webSettings.setAllowContentAccess(true);
		webSettings.setAllowFileAccess(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			webSettings.setAllowFileAccessFromFileURLs(true);
			webSettings.setAllowUniversalAccessFromFileURLs(true);
		}

		webSettings.setAppCacheEnabled(false);
		webSettings.setAppCachePath(getApplicationContext().getCacheDir().toString());
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setDatabaseEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setGeolocationDatabasePath(getApplicationContext().getFilesDir().toString());

		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);

		webSettings.setDefaultTextEncodingName("UTF-8");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			webSettings.setLoadsImagesAutomatically(true);
		} else {
			webSettings.setLoadsImagesAutomatically(false);
		}

		webSettings.setLoadWithOverviewMode(true);
		webSettings.setTextZoom(100);
		webSettings.setUseWideViewPort(true);
		webSettings.setBlockNetworkImage(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setSupportMultipleWindows(false);
		webSettings.setSaveFormData(true);


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

		@RequiresApi(api = Build.VERSION_CODES.KITKAT)
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.e("TestActivity","onPageStarted");
			if(TextUtils.isEmpty(preLoadJs)){
				Log.e("TestActivity","load js");
				preLoadJs = buildJsInject();
			}
			view.evaluateJavascript("javascript:" + preLoadJs, null);
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
//				preLoadJs = Webgum.getPreLoadJs();
//			}
//			view.loadUrl("javascript:" + preLoadJs);
			super.onPageFinished(view, url);
		}
	};

	private WebChromeClient webChromeClient = new WebChromeClient() {

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
