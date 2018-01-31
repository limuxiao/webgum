package com.ule.webgum.core;

import android.os.Handler;
import android.os.Looper;

import com.ule.webgum.core.SystemWebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-30 09:32
 * @Creator: Liy
 * @Version: 1.0
 */

public class CallbackContext {

	private String actionId;
	private SystemWebView webView;


	public CallbackContext(String actionId, SystemWebView webView) {
		this.actionId = actionId;
		this.webView = webView;
	}

	public void sendToJs(final String result){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				webView.loadUrl("javascript:" + joinJs(result));
			}
		});
	}

	private String joinJs(String result){
		StringBuilder sb = new StringBuilder("wg.callbackFromNative(");
		sb.append("'"+ this.actionId +"'");
		sb.append(",'"+ result + "'");
		sb.append(")");
		return sb.toString();
	}

}
