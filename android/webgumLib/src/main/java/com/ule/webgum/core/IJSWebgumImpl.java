package com.ule.webgum.core;

import com.ule.webgum.tools.SystemTool;

import java.util.HashMap;
import java.util.Map;

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

}
