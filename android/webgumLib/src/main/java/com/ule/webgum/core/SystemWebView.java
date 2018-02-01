package com.ule.webgum.core;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-08 10:21
 * @Creator: Liy
 * @Version: 1.0
 */

public class SystemWebView extends WebView implements IWebgumView{

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


	public void init(PluginManager pm){
		this.pluginManager = pm;
		// initWebView
//		setAlwaysDrawnWithCacheEnabled(true);
//		setAnimationCacheEnabled(true);
		setDrawingCacheBackgroundColor(0x00000000);
		setDrawingCacheEnabled(true);
		setWillNotCacheDrawing(false);
		setSaveEnabled(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			setBackground(null);
			getRootView().setBackground(null);
		}
		setBackgroundColor(Color.WHITE);

		setFocusable(true);
		setFocusableInTouchMode(true);
		setHorizontalScrollBarEnabled(false);
		setVerticalScrollBarEnabled(false);
		setScrollbarFadingEnabled(true);

		setWebViewClient(new SystemWebViewClient());
		setWebChromeClient(new SystemWebChromeClient());

		SystemWebSettingTool.settingWebView(context,this);

		addJavascriptInterface(new IJSWebgumImpl(this, this.pluginManager), "wg_android_native");
	}



	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

}
