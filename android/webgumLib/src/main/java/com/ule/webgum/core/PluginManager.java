package com.ule.webgum.core;

import java.util.LinkedHashMap;

/**
 * @Title:		--	插件管理类
 * @Desc:
 * @CreateTime: 2018-01-09 15:46
 * @Creator: Liy
 * @Version: 1.0
 */

final public class PluginManager {



	/**
	 * 插件集合
	 */
	protected final LinkedHashMap<String, IWebgumPlugin> pluginMap = new LinkedHashMap<>();


	public final PluginManager addPlugin(IWebgumPlugin plugin){
		this.pluginMap.put(plugin.getName(),plugin);
		return this;
	}


	public final IWebgumPlugin getPlugin(String pluginName){
		return pluginMap.get(pluginName);
	}

}
