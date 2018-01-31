package com.ule.example.plugins;

import com.ule.webgum.core.CallbackContext;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.SystemWebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 14:38
 * @Creator: Liy
 * @Version: 1.0
 */

public class CameraPlugin extends IWebgumPlugin {



	public CameraPlugin(SystemWebView webView, String pluginName, String pluginVersion){
		super(webView, pluginName, pluginVersion);
	}




	@Override
	public void execute(String action, String args, CallbackContext callback) {

	}
}
