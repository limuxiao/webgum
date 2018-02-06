package com.ule.webgum.core;

import com.ule.webgum.tools.SystemTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
		params.put("wgVersion", WEBGUM_VERSION);
		return JSResult.addCodeMsg(params);
	}

	@Override
	public String getPlugins() {
		JSONObject jsn = new JSONObject();
		try {
			jsn.put("code","0000");
			jsn.put("msg","操作成功");
			JSONArray jarr = new JSONArray();
			for(IWebgumPlugin plugin : this.webView.getPluginManager().pluginMap.values()){
				jarr.put(plugin.getName());
			}
			jsn.put("plugins",jarr);
		} catch (JSONException e) {
			return new JSResult("1003").toJsonString();
		}
		return jsn.toString();
	}

}
