package com.ule.example;

import android.app.Application;

import com.ule.webgum.Webgum;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-07 17:24
 * @Creator: Liy
 * @Version: 1.0
 */

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initWebgum();
    }


    private void initWebgum(){
        //下面两步应该放在Application中操作
        Webgum.init(this);
        Webgum.addPlugins(PluginsConfig.loadPlugins(this));
    }

}
