package com.ule.webgum.core;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.GsonBuilder;
import com.ule.webgum.tools.SystemTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-09 14:28
 * @Creator: Liy
 * @Version: 1.0
 */

public class IJSWebgumImpl implements IJSWebgum{


	private PluginManager pluginManager;
	private SystemWebView webView;

	public IJSWebgumImpl(SystemWebView webView, PluginManager pluginManager){
		this.webView = webView;
		this.pluginManager = pluginManager;
	}

	@JavascriptInterface
	@Override
	public String getOsInfo(){
		Map<String, String> params = new HashMap<>();
		params.put("osName",WEBGUM_OS);
		params.put("osVersion", SystemTool.getSystemVersion());
		params.put("brand", SystemTool.getBrand());
		params.put("model",SystemTool.getModel());
		return new GsonBuilder().create().toJson(params);
	}

	@JavascriptInterface
	@Override
	public String getPlugins(){
		Map<String, String> plugins = new HashMap<>();
		for(IWebgumPlugin plugin: this.pluginManager.pluginMap.values()){
			plugins.put(plugin.getName(),plugin.getVersion());
		}
		return new GsonBuilder().create().toJson(plugins);
	}


	@JavascriptInterface
	@Override
	public IWebgumPlugin getPlugin(String pluginName){
		return this.pluginManager.getPlugin(pluginName);
	}


	@JavascriptInterface
	public String onJsCallWithResult(String req){
		Log.e("IJSWebgumImpl","onJsCallWithResult 收到参数：" + req);
		Map<String, String> params = new HashMap<>();
		params.put("success","true");
		return new GsonBuilder().create().toJson(params);
	}


	@JavascriptInterface
	public void onJsCallWithListener(String req){

	}




	@JavascriptInterface
	public void addListenerAndroid(String pluginName, String actionId, String param){
		Log.e("IJSWebgumImpl","收到参数：" + pluginName + "," + actionId + "," + param);
		IWebgumPlugin plugin = this.pluginManager.getPlugin(pluginName);
		CallbackContext callbackContext = new CallbackContext(actionId, this.webView);
		plugin.execute(actionId, param, callbackContext);
	}



}
