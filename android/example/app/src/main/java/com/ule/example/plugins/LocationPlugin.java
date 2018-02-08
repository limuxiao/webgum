package com.ule.example.plugins;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.sysweb.JSRequest;
import com.ule.webgum.sysweb.JsResponse;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-08 11:04
 * @Creator: Liy
 * @Version: 1.0
 */

public class LocationPlugin extends IWebgumPlugin {


    private LocationClient mLocClient;


    public LocationPlugin(String pluginName, String pluginVersion) {
        super(pluginName, pluginVersion);
    }


    @JSMethod(name = "get")
    public void getLocal(JSRequest request, JsResponse response){

        Log.e("LocationPlugin","getLocal");

        // 定位初始化
        mLocClient = new LocationClient(request.webView.getContext());
        mLocClient.registerLocationListener(new MyLocationListenner());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    public class MyLocationListenner implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null)
                return;
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());

            Log.e("LocationPlugin","latitude:"+ latitude +", longitude:" + longitude);

            mLocClient.stop();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


}
