package com.ule.webgum.core;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-09 16:57
 * @Creator: Liy
 * @Version: 1.0
 */

final public class PluginEntry {

	/**
	 * 插件名，更具象点说这个应该是插件id，前端会根据这个来区分插件，各插件严禁重复
	 */
	public final String pluginName;

	/**
	 * 插件全路径类名
	 */
	public final String pluginClass;

	/**
	 * 插件版本
	 */
	public final String pluginVersion;


	public PluginEntry(String pluginName, String pluginClass, String pluginVersion) {
		this.pluginName = pluginName;
		this.pluginClass = pluginClass;
		this.pluginVersion = pluginVersion;
	}
}
