package com.ule.webgum.plugins;

import android.text.TextUtils;
import android.util.Log;

import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.tools.NetCheckTool;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-07 17:12
 * @Creator: Liy
 * @Version: 1.0
 */

public class NetWorkPlugin extends IWebgumPlugin {

    public NetWorkPlugin(String pluginName, String pluginVersion) {
        super(pluginName, pluginVersion);
    }


    /**
     * 获取网络类型
     * @param request
     * @return
     */
    @JSMethod
    public String getNetType(JSRequest request){
        String netType = "";
        int type = NetCheckTool.getNetType(request.webgumView.getContext());
        Log.e("NetWorkPlugin","netType: " + type);
        switch (type){
            case NetCheckTool.NET_TYPE_WIFI:
                netType = "wifi";
                break;
            case NetCheckTool.NET_TYPE_2G:
                netType = "2G";
                break;
            case NetCheckTool.NET_TYPE_3G:
                netType = "3G";
                break;
            case NetCheckTool.NET_TYPE_4G:
                netType = "4G";
                break;
        }
        if(TextUtils.isEmpty(netType)){
            JSResult result = new JSResult(false,"9999","获取网络类型失败");
            return result.toJsonString();
        }else {
            JSResult<String> result = new JSResult();
            result.put("netType",netType);
            return result.toJsonString();
        }

    }







}
