package com.ule.webgum.core;

/**
 * @Title:		需要注入的js对象的抽象接口
 * @Desc:
 * @CreateTime: 2018-01-29 16:29
 * @Creator: Liy
 * @Version: 1.0
 */

public interface IJSWebgum {

	String WEBGUM_VERSION = "1.0.0";
	String WEBGUM_OS = "Android";

	/**
	 * 获取系统基础信息
	 * @return
	 */
	String getOsInfo();

	/**
	 * 获取插件列表
	 * @return
	 */
	String getPlugins();

	/**
	 * 根据插件名获取某一插件
	 * @param pluginName 插件名
	 * @return 插件对象
	 */
	Object getPlugin(String pluginName);

}
