package com.ule.webgum.crosswalkweb;

import android.text.TextUtils;
import android.util.Log;

import com.ule.webgum.Webgum;

import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-27 15:11
 * @Creator: Liy
 * @Version: 1.0
 */

public class WgCrosswalkChromeClient extends XWalkUIClient{


    private String preLoadJs;

    public WgCrosswalkChromeClient(XWalkView view) {
        super(view);
    }


    @Override
    public void onPageLoadStarted(XWalkView view, String url) {
        super.onPageLoadStarted(view, url);
    }

    @Override
    public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
//        if(TextUtils.isEmpty(preLoadJs)){
//            preLoadJs = Webgum.getPreLoadJs();
//        }
//        Log.e("WgCrosswalkWebView","注入成功");
//        view.evaluateJavascript(preLoadJs,null);
        super.onPageLoadStopped(view, url, status);
    }
}
