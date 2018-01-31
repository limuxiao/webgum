package com.ule.example.plugins;

import com.ule.webgum.core.CallbackContext;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.SystemWebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 13:58
 * @Creator: Liy
 * @Version: 1.0
 */

public class DevicePlugin extends IWebgumPlugin {


	public DevicePlugin(SystemWebView webView, String pluginName, String pluginVersion) {
		super(webView, pluginName, pluginVersion);
	}

	@Override
	public void execute(String action, String args, CallbackContext callback) {

	}
}
