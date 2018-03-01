package com.ule.webgum.crosswalkweb;

import android.util.Log;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-27 15:09
 * @Creator: Liy
 * @Version: 1.0
 */

public class WgCrosswalkViewCilent extends XWalkResourceClient{



    public WgCrosswalkViewCilent(XWalkView view) {
        super(view);
    }

    @Override
    public void onLoadStarted(XWalkView view, String url) {
        super.onLoadStarted(view, url);
    }

    @Override
    public void onLoadFinished(XWalkView view, String url) {
        super.onLoadFinished(view, url);
    }



}
