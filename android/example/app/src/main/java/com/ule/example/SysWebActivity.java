package com.ule.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ule.webgum.core.PluginManager;
import com.ule.webgum.core.SystemWebView;

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
		webView = findViewById(R.id.acty_sys_wv);

		webView.init(new PluginManager());

		String url = "file:///android_asset/webgum/edit.html";
		webView.loadUrl(url);


	}


	public void onSearch(View view){
		String url = ((EditText)findViewById(R.id.acty_sys_ed)).getText().toString();
		webView.loadUrl(url);
	}


}
