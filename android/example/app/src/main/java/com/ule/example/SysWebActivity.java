package com.ule.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ule.example.functions.common.IReply;
import com.ule.example.functions.common.ReplyManager;
import com.ule.webgum.sysweb.SystemWebView;
import com.ule.webgum.Webgum;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 09:45
 * @Creator: Liy
 * @Version: 1.0
 */

public class SysWebActivity extends Activity{


	SystemWebView webView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sys);

		//下面两步应该放在Application中操作
		Webgum.init(this);
		Webgum.addPlugins(PluginsConfig.loadPlugins(this));

		webView = findViewById(R.id.acty_sys_wv);

		webView.init();

		String url = "file:///android_asset/webgum/edit.html";
		webView.loadUrl(url);


	}


	public void onSearch(View view){
		String url = ((EditText)findViewById(R.id.acty_sys_ed)).getText().toString();
		webView.loadUrl(url);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IReply reply = ReplyManager.getReply(requestCode);
		if(reply != null){
			reply.reply(data);
			ReplyManager.removeReply(reply);
		}
	}



}
