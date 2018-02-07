package com.ule.webgum.core;

import android.webkit.WebView;

import com.ule.webgum.annotation.JSMethod;

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
	protected WebView webView;

	public IWebgumPlugin(String pluginName, String pluginVersion){
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
	}

	/**
	 * 	获取插件的基本信息
	 * @return
	 */
	@JSMethod()
	public String getBaseInfo(){
		JSResult result = new JSResult();
		result.put("pluginName",this.pluginName);
		result.put("pluginVersion",this.pluginVersion);
		return result.toJsonString();
	}


	public String getName() {
		return pluginName;
	}

	public String getVersion() {
		return pluginVersion;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}

	public WebView getWebView() {
		return webView;
	}
}
