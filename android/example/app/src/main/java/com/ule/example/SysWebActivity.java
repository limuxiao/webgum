package com.ule.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ule.webgum.replys.IReply;
import com.ule.webgum.replys.ReplyManager;
import com.ule.webgum.sysweb.SystemWebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 09:45
 * @Creator: Liy
 * @Version: 1.0
 *
 *
 *  微信分享  音视频
	拨打电话
	通讯录
	相册/相机
	定位
	网络
 *
 *
 */

public class SysWebActivity extends Activity{


	SystemWebView webView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sys);


		webView = (SystemWebView) findViewById(R.id.acty_sys_wv);

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
