package com.ule.webgum.crosswalkweb;

import android.content.Context;
import android.util.AttributeSet;

import com.ule.webgum.PluginManager;
import com.ule.webgum.Webgum;
import com.ule.webgum.core.IWebgumView;
import com.ule.webgum.core.JSWebgumImpl;

import org.xwalk.core.XWalkView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-27 15:05
 * @Creator: Liy
 * @Version: 1.0
 */

public class WgCrosswalkWebView extends XWalkView implements IWebgumView{


    private Context context;
    private PluginManager pluginManager;


    public WgCrosswalkWebView(Context context) {
        super(context);
        this.context = context;
    }

    public WgCrosswalkWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void init() {
        this.pluginManager = Webgum.createManager();
        setResourceClient(new WgCrosswalkViewCilent(this));
        setUIClient(new WgCrosswalkChromeClient(this));
        addJavascriptInterface(new JSWebgumImpl(this), "wg_android_native");
    }

    @Override
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public void loadUrl(String url) {
        load(url,null);
    }
}