package com.ule.webgum.core;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.ule.webgum.tools.SystemTool;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-09 14:28
 * @Creator: Liy
 * @Version: 1.0
 */

public class IJSWebgumImpl implements IJSWebgum {

	public static final String PLUGIN_MAIN = "__main__";

	private PluginManager pluginManager;
	private SystemWebView webView;

	public IJSWebgumImpl(SystemWebView webView) {
		this.webView = webView;
		this.pluginManager = this.webView.getPluginManager();
	}

	@Override
	public String getOsInfo() {
		Map<String, String> params = new HashMap<>();
		params.put("osName", WEBGUM_OS);
		params.put("osVersion", SystemTool.getSystemVersion());
		params.put("brand", SystemTool.getBrand());
		params.put("model", SystemTool.getModel());
		return JSResult.addCodeMsg(params);
	}

	@Override
	public String getPlugins() {
		Map<String, String> plugins = new HashMap<>();
		for (IWebgumPlugin plugin : this.pluginManager.pluginMap.values()) {
			plugins.put(plugin.getName(), plugin.getVersion());
		}
		return JSResult.addCodeMsg(plugins);
	}


	/**
	 * 处理即时返回的js调用
	 * @param req
	 * @return
	 */
	@JavascriptInterface
	public String onJsCallWithResult(String req){
		JSArgumentParser parser = JSArgumentParser.parse2(req);
		if(parser == null || TextUtils.isEmpty(parser.pluginName) || TextUtils.isEmpty(parser.methodName) || parser.id < 0)
			return new JSResult("9999").toJsonString();
		try{

			if(PLUGIN_MAIN.equals(parser.pluginName)){			//主对象
				return callMainWithResult(parser);

			}else{	//调用模块方法
				return callPluginWithResult(parser);
			}

		}catch (Exception e){
			return new JSResult("9991").toJsonString();
		}
	}


	/**
	 * 处理监听回调的js调用
	 * @param req
	 */
	@JavascriptInterface
	public void onJsCallWithListener(String req){
		Log.e("IJSWebgumImpl","onJsCallWithListener 收到参数：" + req);
		JSArgumentParser parser = JSArgumentParser.parse2(req);
		if(parser == null || TextUtils.isEmpty(parser.pluginName) || TextUtils.isEmpty(parser.methodName) || parser.id < 0)
			return;

		this.webView.getJsBridge().sendToJs(parser,"hhhh");

	}


	//调用本对象里的方法
	private String callMainWithResult(JSArgumentParser parser) throws Exception{
		return invokeWithResult(this, parser);
	}

	//调用插件中的方法
	private String callPluginWithResult(JSArgumentParser parser) throws Exception{
		IWebgumPlugin plugin = this.pluginManager.getPlugin(parser.pluginName);
		if(plugin == null)
			return new JSResult("1002").toJsonString();

		return invokeWithResult(plugin, parser);
	}

	//调用本对象里的方法
	private void callMainWithListener(JSArgumentParser parser){

	}

	//调用插件中的方法
	private void callPluginWithListener(JSArgumentParser parser){

	}


	//反射调用方法
	private String invokeWithResult(Object obj, JSArgumentParser parser) throws Exception{
		if(parser.params == null || parser.params.size() <= 0){				//没有参数，调用无参方法
			Method method = this.getClass().getMethod(parser.methodName);
			return String.valueOf(method.invoke(obj));
		}else{
			Method method = this.getClass().getMethod(parser.methodName, new Class<?>[]{JSArgumentParser.class});
			return String.valueOf(method.invoke(obj, parser));
		}
	}

}
