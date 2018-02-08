package com.ule.webgum.sysweb;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import com.ule.webgum.annotation.JSArgumentType;
import com.ule.webgum.core.JSResult;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 11:01
 * @Creator: Liy
 * @Version: 1.0
 */

public class JsResponse {

	public long id;
	protected String pluginName;
	protected String methodName;
	private WebView webView;


	public JsResponse(long id, String pluginName, String methodName, WebView webView) {
		this.id = id;
		this.pluginName = pluginName;
		this.methodName = methodName;
		this.webView = webView;
	}

	/**
	 * 发送正常的数据
	 * @param param
	 * @param result
	 */
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

	/**
	 * 发送错误信息
	 * @param result
	 */
	public synchronized void sendErr(final JSResult result){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				webView.loadUrl("javascript:wg.onNativeError('"+ result.toJsonString() +"')");
			}
		});
	}

}
