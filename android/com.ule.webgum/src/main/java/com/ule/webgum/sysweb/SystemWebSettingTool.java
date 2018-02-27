package com.ule.webgum.sysweb;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ule.webgum.core.JSWebgumImpl;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-08 10:50
 * @Creator: Liy
 * @Version: 1.0
 */

final public class SystemWebSettingTool {

	@SuppressWarnings("deprecation")
	public static void settingWebView(Context context, WebView swv){
		// initWebSetting
		WebSettings webSettings = swv.getSettings();

		webSettings.setAllowContentAccess(true);
		webSettings.setAllowFileAccess(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			webSettings.setAllowFileAccessFromFileURLs(true);
			webSettings.setAllowUniversalAccessFromFileURLs(true);
		}

		webSettings.setAppCacheEnabled(false);
		webSettings.setAppCachePath(context.getCacheDir().toString());
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setDatabaseEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setGeolocationDatabasePath(context.getFilesDir().toString());

		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);

		webSettings.setDefaultTextEncodingName("UTF-8");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			webSettings.setLoadsImagesAutomatically(true);
		} else {
			webSettings.setLoadsImagesAutomatically(false);
		}

		webSettings.setLoadWithOverviewMode(true);
		webSettings.setTextZoom(100);
		webSettings.setUseWideViewPort(true);
		webSettings.setBlockNetworkImage(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setSupportMultipleWindows(false);
		webSettings.setSaveFormData(true);

		StringBuilder sb = new StringBuilder(webSettings.getUserAgentString());
		sb.append(" Webgum/" + JSWebgumImpl.WEBGUM_VERSION + " (" + JSWebgumImpl.WEBGUM_OS + ")");
		webSettings.setUserAgentString(sb.toString());
		Log.e("SystemWebSettingTool",sb.toString());
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			try {
				webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
			} catch (Exception e) {
			}
		}
	}

}
