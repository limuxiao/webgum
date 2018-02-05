package com.ule.example.plugins;

import android.content.BroadcastReceiver;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSArgumentParser;
import com.ule.webgum.core.JSResult;

import java.util.HashMap;
import java.util.Map;

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

	public BatteryPlugin(String pluginName, String pluginVersion) {
		super(pluginName, pluginVersion);
	}


	@JSMethod()
	public String getPower(){
		Map<String,String> map = new HashMap<>();
		map.put("power","35%");
		return JSResult.addCodeMsg(map);
	}

	@JSMethod()
	public void getCallback(JSArgumentParser parser){
		Log.e("BatteryPlugin","getCallback");
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
