package com.ule.webgum.core;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-08 10:21
 * @Creator: Liy
 * @Version: 1.0
 */

public class SystemWebView extends WebView{

	private Context context;
	private PluginManager pluginManager;

	public SystemWebView(Context context) {
		super(context);
		this.context = context;
	}

	public SystemWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}


	public void init(PluginManager pm) {
		this.pluginManager = pm;
	}


	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

}
