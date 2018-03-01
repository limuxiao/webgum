package com.ule.webgum.sysweb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ule.webgum.Webgum;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-31 17:54
 * @Creator: Liy
 * @Version: 1.0
 *
 * {@link }
 */

public class SystemWebViewClient extends WebViewClient{

	private String preLoadJs;

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		if(TextUtils.isEmpty(preLoadJs)){
			preLoadJs = Webgum.getPreLoadJs();
			Log.e("SystemWebViewClient","" + preLoadJs);
		}
		view.loadUrl("javascript:" + preLoadJs);
		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageCommitVisible(WebView view, String url) {
		super.onPageCommitVisible(view, url);
	}

	@Override
	public void onPageFinished(WebView view, final String url) {
		view.invalidate();
	}


	@Override
	public void onReceivedError(WebView view, final WebResourceRequest request, WebResourceError error) {
		super.onReceivedError(view, request, error);
	}

	@Override
	public void onReceivedHttpError(WebView view, final WebResourceRequest request, WebResourceResponse errorResponse) {
		super.onReceivedHttpError(view, request, errorResponse);
	}

	@Override
	public void onReceivedError(WebView view, int errorCode, String description, final String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
	}

	@Override
	public void onFormResubmission(WebView view, @NonNull final Message dontResend, final Message resend) {
		super.onFormResubmission(view,dontResend,resend);
	}

	@Override
	public void onReceivedSslError(WebView view, @NonNull final SslErrorHandler handler, SslError error) {
		Context holder = view.getContext();
		if (holder == null || !(holder instanceof Activity)) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(holder);
		builder.setCancelable(false);
		builder.setTitle("警告");
		builder.setMessage("当前站点的证书是不可信任的，需要继续浏览吗？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				handler.proceed();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				handler.cancel();
			}
		});

		AlertDialog dialog = builder.create();
		if (error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
			dialog.show();
		} else {
			handler.proceed();
		}
	}

}
