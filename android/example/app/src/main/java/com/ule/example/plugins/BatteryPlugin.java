package com.ule.example.plugins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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

	private BatteryBroadcastReceiver receiver;

	public BatteryPlugin(String pluginName, String pluginVersion) {
		super(pluginName, pluginVersion);
	}


	@JSMethod()
	public void getPower(JSRequest request, JsResponse response){

	}


	class BatteryBroadcastReceiver extends BroadcastReceiver{

		private JSRequest request;
		private JsResponse response;

		public BatteryBroadcastReceiver(JSRequest request, JsResponse response) {
			this.request = request;
			this.response = response;
		}

		@Override
		public void onReceive(Context context, Intent intent) {

		}
	}
}
