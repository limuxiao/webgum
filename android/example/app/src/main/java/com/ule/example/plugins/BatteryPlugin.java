package com.ule.example.plugins;

import android.content.BroadcastReceiver;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.ule.webgum.core.CallbackContext;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.SystemWebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 10:39
 * @Creator: Liy
 * @Version: 1.0
 */

public class BatteryPlugin extends IWebgumPlugin {

	public static final String ACTION_ON_BATTERY_CHANGE = "onBatteryChange";

	private BroadcastReceiver receiver;

	public BatteryPlugin(SystemWebView webView, String pluginName, String pluginVersion) {
		super(webView, pluginName, pluginVersion);
	}

	@Override
	public void execute(String action, String args, CallbackContext callback) {
		switch (action){
			case ACTION_ON_BATTERY_CHANGE:
				callback.sendToJs("{\"power\":\"20%\"}");
				break;
			default:
				break;
		}
	}

	@JavascriptInterface
	public String getPower(){
		return "35%";
	}


	@JavascriptInterface
	public String getTest(final String object){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				Log.e("BatteryPlugin","object class" + object);
			}
		});

		return "hhhh";
	}


}
