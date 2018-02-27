package com.ule.webgum.sysweb;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ule.webgum.core.IWebgumChromeClient;
import com.ule.webgum.core.IWebgumViewClient;
import com.ule.webgum.core.JSWebgumImpl;
import com.ule.webgum.core.IWebgumView;
import com.ule.webgum.PluginManager;
import com.ule.webgum.Webgum;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-08 10:21
 * @Creator: Liy
 * @Version: 1.0
 */

public class SystemWebView extends WebView implements IWebgumView {

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


	public void init(){
		this.pluginManager = Webgum.createManager();
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

		setWebgumViewClient(new SystemWebViewClient());
		setWebgumChromeClient(new SystemWebChromeClient());

		SystemWebSettingTool.settingWebView(context,this);

		addJavascriptInterface(new JSWebgumImpl(this), "wg_android_native");
	}



	@Override
	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

	@Override
	public void setWebgumViewClient(IWebgumViewClient webViewClient) {
		setWebViewClient((WebViewClient) webViewClient);
	}

	@Override
	public void setWebgumChromeClient(IWebgumChromeClient webChromeClient) {
		setWebChromeClient((WebChromeClient) webChromeClient);
	}

}
