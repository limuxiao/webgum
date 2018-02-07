package com.ule.webgum.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import com.ule.webgum.annotation.JSArgumentType;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 11:01
 * @Creator: Liy
 * @Version: 1.0
 */

public class JsResponse {

	public long id;
	public String pluginName;
	public String methodName;
	private WebView webView;


	public JsResponse(long id, String pluginName, String methodName, WebView webView) {
		this.id = id;
		this.pluginName = pluginName;
		this.methodName = methodName;
		this.webView = webView;
	}

	public synchronized void send(JSRequest.Parameter param, final JSResult result){

		if(param == null || param.type != JSArgumentType.TYPE_FUNCTION) return;

		String index = param.name.split("_")[1];

		final String f_id = this.id + "_" + this.pluginName + "_" +  this.methodName + "_" + index;

		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {

				String s = "{\"id\":\"" + f_id + "\",\"result\":"+ result.toJsonString() +"}";

				Log.e("JsResponse","" + s);

				webView.loadUrl("javascript:wg.onNativeCallback('"+ s +"')");
			}
		});

	}

}
