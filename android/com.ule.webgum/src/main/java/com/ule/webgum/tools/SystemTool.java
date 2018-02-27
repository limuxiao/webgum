package com.ule.webgum.tools;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 16:59
 * @Creator: Liy
 * @Version: 1.0
 */

public class SystemTool {

	/**
	 * 获取手机操作系统版本
	 * @return
	 */
	public static String getSystemVersion(){
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机生产厂商
	 * @return
	 */
	public static String getBrand(){
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机型号
	 * @return
	 */
	public static String getModel(){
		return android.os.Build.MODEL;
	}

}
