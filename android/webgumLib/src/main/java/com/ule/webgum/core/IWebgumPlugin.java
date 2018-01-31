package com.ule.webgum.core;

import android.webkit.JavascriptInterface;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-09 16:42
 * @Creator: Liy
 * @Version: 1.0
 */

public abstract class IWebgumPlugin {

	private String pluginName;
	private String pluginVersion;
	private SystemWebView webView;

	public IWebgumPlugin(SystemWebView webView, String pluginName, String pluginVersion){
		this.webView = webView;
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
	}

	/**
	 * 	获取插件的基本信息
	 * @return
	 */
	@JavascriptInterface
	public String getBaseInfo(){
		Map<String, String> infos = new HashMap<>();
		infos.put("pluginName",this.pluginName);
		infos.put("pluginVersion",this.pluginVersion);
		return new GsonBuilder().create().toJson(infos);
	}

	/**
	 * 请求分发
	 * @param action
	 * @param args
	 * @param callback
	 */
	public abstract void execute(String action, String args, CallbackContext callback);


	public String getName() {
		return pluginName;
	}

	public String getVersion() {
		return pluginVersion;
	}

	public SystemWebView getWebView() {
		return webView;
	}
}
