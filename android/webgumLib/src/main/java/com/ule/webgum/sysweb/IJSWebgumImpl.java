package com.ule.webgum.sysweb;

import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSResult;
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

final public class IJSWebgumImpl extends IJSWebgumAbs {

	public IJSWebgumImpl(SystemWebView webView) {
		super(webView);
	}

	@Override
	public String getOsInfo() {
		JSResult<String> jsResult = new JSResult<>();
		jsResult.put("osName", WEBGUM_OS);
		jsResult.put("osVersion", SystemTool.getSystemVersion());
		jsResult.put("brand", SystemTool.getBrand());
		jsResult.put("model", SystemTool.getModel());
		jsResult.put("wgVersion", WEBGUM_VERSION);
		return jsResult.toJsonString();
	}

	@Override
	public String getPlugins() {
		JSResult result = new JSResult();

		List<String> list = new ArrayList<>();

		for(IWebgumPlugin plugin : this.webView.getPluginManager().pluginMap.values()){
			list.add(plugin.getName());
		}
		result.put("plugins",list);
		return result.toJsonString();
	}


	public void testCall(JSRequest request, JsResponse response){
		JSRequest.Parameter parameter = request.getParam(0);
		JSResult result = new JSResult();
		result.put("name","testCall");
		response.send(parameter, result);
	}

}
