package com.ule.webgum.core;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 11:02
 * @Creator: Liy
 * @Version: 1.0
 */

final public class Webgum {

	private static boolean ready = false;

	private static String preLoadJs;

	public final static void load(Context context){
		loadJsFile(context);
	}

	private final static void loadJsFile(Context context){
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = context.getAssets().open("wg_android.js");
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			sb.append(new String(buffer));
			is.close();
			preLoadJs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isReady() {
		return ready;
	}

	public static String getPreLoadJs(){
		return preLoadJs;
	}
}
