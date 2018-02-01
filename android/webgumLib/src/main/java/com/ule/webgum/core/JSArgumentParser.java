package com.ule.webgum.core;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 17:50
 * @Creator: Liy
 * @Version: 1.0
 */

final class JSArgumentParser {

	private long id;
	private String pluginName;
	private String methodName;



	/**
	 * 将js传过来的参数解析成java对象
	 * @param jsonStr
	 * @return
	 */
	public static JSArgumentParser parse(String jsonStr){

		if(TextUtils.isEmpty(jsonStr) || (!jsonStr.startsWith("{") &&
				!jsonStr.startsWith("["))) {
			return null;
		}
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSArgumentParser parser = new JSArgumentParser();
			return parser;
		}catch (Exception e){
			return null;
		}



	}

}
