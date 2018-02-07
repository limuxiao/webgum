package com.ule.webgum.sysweb;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ule.webgum.core.IWebgumChromeClient;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 10:47
 * @Creator: Liy
 * @Version: 1.0
 *
 * {@link IWebgumChromeClient}
 */

public class SystemWebChromeClient extends WebChromeClient implements IWebgumChromeClient{

	@Override
	public void onProgressChanged(WebView view, int progress) {
		super.onProgressChanged(view, progress);
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
	}

	@Override
	public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
		return super.onJsAlert(view, url, message, result);
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
		return super.onJsPrompt(view, url, message, defaultValue, result);
	}
}
