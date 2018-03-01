package com.ule.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ule.webgum.crosswalkweb.WgCrosswalkWebView;
import com.ule.webgum.replys.IReply;
import com.ule.webgum.replys.ReplyManager;
import com.ule.webgum.sysweb.SystemWebView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-27 15:34
 * @Creator: Liy
 * @Version: 1.0
 */

public class CrossWebActivity extends Activity{


    WgCrosswalkWebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross);


        webView = (WgCrosswalkWebView) findViewById(R.id.acty_cross_wv);

        webView.init();

        String url = "file:///android_asset/webgum/edit2.html";
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
