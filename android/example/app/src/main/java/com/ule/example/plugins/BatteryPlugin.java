package com.ule.example.plugins;

import android.content.BroadcastReceiver;

import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.sysweb.JSRequest;
import com.ule.webgum.sysweb.JsResponse;

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
	public void getPower(JSRequest request, JsResponse response){

	}
}
