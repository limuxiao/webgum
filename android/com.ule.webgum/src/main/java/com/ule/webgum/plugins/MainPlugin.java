package com.ule.webgum.plugins;

import android.util.Log;

import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.IWebgumView;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.core.JsResponse;
import com.ule.webgum.tools.SystemTool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-05 09:44
 * @Creator: Liy
 * @Version: 1.0
 */

final public class MainPlugin extends MainPluginAbs{


	public static final String WEBGUM_VERSION = "1.0.0";
	public static final String WEBGUM_OS = "Android";
	public static final String PLUGIN_VERSION = "1.0.0";


	public MainPlugin() {
		super(PLUGIN_VERSION);
	}


	@Override
	public String getOsInfo(JSRequest request) {
		JSResult<String> jsResult = new JSResult<>();
		jsResult.put("osName", WEBGUM_OS);
		jsResult.put("osVersion", SystemTool.getSystemVersion());
		jsResult.put("brand", SystemTool.getBrand());
		jsResult.put("model", SystemTool.getModel());
		jsResult.put("wgVersion", WEBGUM_VERSION);
		return jsResult.toJsonString();
	}

	@Override
	public String getPlugins(JSRequest request) {

		Log.e("MainPlugin","getPlugins");

		JSResult result = new JSResult();

		List<String> list = new ArrayList<>();

		for(IWebgumPlugin plugin : request.webgumView.getPluginManager().pluginMap.values()){
			if(!"main".equals(plugin.getName())){
				list.add(plugin.getName());
			}

		}
		result.put("plugins",list);
		return result.toJsonString();
	}


	//这是一个扩展的主体方法
	public void testCall(JSRequest request, JsResponse response){
		JSRequest.Parameter parameter = request.getParam(0);
		JSResult result = new JSResult();
		result.put("name","testCall");
		response.send(parameter, result);
	}

}
