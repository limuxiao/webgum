package com.ule.webgum.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * @Title:      NetCheckTool    --      网络监测
 * @Desc:
 * @CreateTime: 2017年07月12日 09:29
 * @Creator: Liy
 * @Version: 1.0
 */

final public class NetCheckTool {

    public static final int NET_TYPE_BREAK = -1;        //没有任何网络连接 WiFi、mobile都没有
    public static final int NET_TYPE_WIFI = 0;          //WiFi
    public static final int NET_TYPE_2G = 2;            //2G
    public static final int NET_TYPE_3G = 3;            //3G
    public static final int NET_TYPE_4G = 4;            //4G

    /**
     *  判断网络连接是否有效（此时可传输数据）。
     * @return boolean 不管wifi，还是mobile net，只要当前在连接状态（可有效传输数据）就返回true,反之false。
     */
    public static final boolean isConnected(Context context){
        NetworkInfo net = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return net != null && net.isConnected();
    }

    /**
     * 获取当前网络连接的连接类型
     * @return
     */
    public static final int getConnectType(){
        return 0;
    }


    /**
     * 获取当前网络类型 WiFi / mobile
     * @param context
     * @return
     */
    public static int getNetType(Context context) {
        if(isConnected(context)){
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if(tm == null) return NET_TYPE_BREAK;
            NetworkInfo netinfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            String name = netinfo.getTypeName();
            if(TextUtils.isEmpty(name)) return NET_TYPE_BREAK;
            if(name.toLowerCase().equals("wifi")){
                return NET_TYPE_WIFI;
            }else if(name.toLowerCase().equals("mobile")){
                int type = netinfo.getType();
                int telType = tm.getNetworkType();
                switch (telType){
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NET_TYPE_2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NET_TYPE_3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NET_TYPE_4G;
                    default:
                        return NET_TYPE_BREAK;
                }
            }else{
                return NET_TYPE_BREAK;
            }
        }else{
            return NET_TYPE_BREAK;
        }
    }
}
