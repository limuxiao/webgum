package com.ule.webgum.core;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.ule.webgum.annotation.JSArgumentType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 17:50
 * @Creator: Liy
 * @Version: 1.0
 */

public final class JSArgumentParser {

	public long id;
	public String pluginName;
	public String methodName;
	public List<Parameter> params;


	/**
	 * 将js传过来的参数解析成java对象
	 * @param jsonStr
	 * @return
	 */
	public static JSArgumentParser parse(String jsonStr){

		try {
			return new GsonBuilder().create().fromJson(jsonStr, JSArgumentParser.class);
		}catch (Exception e){
			Log.e("JSArgumentParser",e.getMessage());
			return null;
		}

	}


	/**
	 * 将js传过来的参数解析成java对象
	 * @param jsonStr
	 * @return
	 */
	public static JSArgumentParser parse2(String jsonStr){

		try {
			JSArgumentParser parser = new JSArgumentParser();
			JSONObject jsn = new JSONObject(jsonStr);
			if(jsn.has("id")){
				parser.id = jsn.optLong("id");
			}
			if(jsn.has("pluginName")){
				parser.pluginName = jsn.optString("pluginName");
			}
			if(jsn.has("methodName")){
				parser.methodName = jsn.optString("methodName");
			}
			if(jsn.has("params")){
				JSONArray jsonArray = jsn.getJSONArray("params");
				List<Parameter> parameterList = new ArrayList<>();
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject item = jsonArray.getJSONObject(i);
						if (item == null) {
							continue;
						}
						Parameter parameter = new Parameter();
						if (item.has("name")) {
							parameter.name = item.optString("name");
						}
						if (item.has("type")) {
							parameter.setType(item.optInt("type"));
						}
						if (item.has("value")) {
							parameter.value = item.optString("value");
						}
						parameterList.add(parameter);
					}
				}
				parser.params = parameterList;
			}

			return parser;
		}catch (Exception e){
			Log.e("JSArgumentParser",e.getMessage());
			return null;
		}

	}

	/**
	 * 获取参数列表长度
	 * @return
	 */
	public int getParamSize(){
		return params == null ? 0 : params.size();
	}

	/**
	 * 根据位置获取参数
	 * @param index
	 * @return
	 */
	public Parameter getParam(int index){
		if (params == null) return null;
		return params.get(index);
	}


	/**
	 * 参数类
	 */
	public static class Parameter{
		@JSArgumentType.Type
		public int type;
		public String name;
		public Object value;


		public void setType(int type) {
			this.type = type;
		}
	}

}
