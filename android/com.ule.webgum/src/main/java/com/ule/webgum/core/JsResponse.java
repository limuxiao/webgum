package com.ule.webgum.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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
	protected String pluginName;
	protected String methodName;
	private IWebgumView webgumView;


	public JsResponse(long id, String pluginName, String methodName, IWebgumView webgumView) {
		this.id = id;
		this.pluginName = pluginName;
		this.methodName = methodName;
		this.webgumView = webgumView;
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

				webgumView.loadUrl("javascript:wg.onNativeCallback('"+ s +"')");
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
				webgumView.loadUrl("javascript:wg.onNativeError('"+ result.toJsonString() +"')");
			}
		});
	}

}
