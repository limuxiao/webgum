package com.ule.webgum.plugins;

import android.util.Log;

import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.core.JsResponse;
import com.ule.webgum.tools.SystemTool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-03-05 16:44
 * @Creator: Liy
 * @Version: 1.0
 */

public abstract class MainPluginAbs extends IWebgumPlugin {


    public static final String PLUGIN_NAME = "main";

    public MainPluginAbs(String pluginVersion) {
        super(PLUGIN_NAME, pluginVersion);
    }


    public abstract String getOsInfo(JSRequest request);

    public abstract String getPlugins(JSRequest request);



}
