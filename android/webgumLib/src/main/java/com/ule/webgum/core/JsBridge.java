package com.ule.webgum.core;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 11:01
 * @Creator: Liy
 * @Version: 1.0
 */

public abstract class JsBridge {

	private WebView webView;

	public JsBridge(WebView webView) {
		this.webView = webView;
	}

	public synchronized void sendToJs(JSArgumentParser parser, String result){

		final String f_id = parser.id + "_" + parser.pluginName + "_" +  parser.methodName + "_0";
		final String f_result = result;

		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				webView.loadUrl("javascript:wg.onNativeCallback('" + "{\"id\":\""+ f_id +"\",\"result\":\""+ f_result +"\"}" + "')");
			}
		});

	}

}
