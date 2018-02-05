package com.ule.webgum.core;

/**
 * @Title:	IWebgumView		webView 接口
 * @Desc:
 *
 * 			定义此接口的目的是为了以后的扩展，
 *
 * 				在安卓项目里可能已经存在了自定义的webview，如果想使用webgum，
 * 			无需在去写一个支持webgum的webview，只需要实现此接口即可。
 *
 * @CreateTime: 2018-01-31 17:15
 * @Creator: Liy
 * @Version: 1.0
 */

public interface IWebgumView {


	/**
	 * 初始化
	 */
	void init();



	/**
	 * 获取插件管理器
	 * @return
	 */
	PluginManager getPluginManager();


	/**
	 * 加载网页或js代码
	 * @param url
	 */
	void loadUrl(String url);



}