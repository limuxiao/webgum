package com.ule.webgum.core;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-02 11:07
 * @Creator: Liy
 * @Version: 1.0
 */

final public class JSResult {


	private static final Map<String,String> codeMap = new HashMap<>();

	static {
		codeMap.put("9991","方法解析异常");
		codeMap.put("9992","结果为空");
		codeMap.put("9999","参数异常");

		codeMap.put("0000","操作成功");

		codeMap.put("1001","模块列表为空");
		codeMap.put("1002","未找到对应模块");
		codeMap.put("1003","获取模块列表失败");
	}



	private boolean status;
	private String code;
	private String msg;

	public JSResult(boolean status,String code, String msg) {
		this.status = status;
		this.code = code;
		this.msg = msg;
	}

	public JSResult(boolean status, String code){
		this(status,code,codeMap.get(code));
	}

	public JSResult(String code){
		this("0000".equals(code), code);
	}

	public static String addCodeMsg(boolean status,String code,String msg,Map map){
		if(map == null)
			return new JSResult("9992").toJsonString();

		map.put("status",status);
		map.put("code",code);
		map.put("msg",msg);
		return new GsonBuilder().create().toJson(map);
	}

	public static String addCodeMsg(String code,String msg,Map map){
		return addCodeMsg("0000".equals(code),code,msg,map);
	}

	public static String addCodeMsg(String code,Map map){
		return addCodeMsg("0000".equals(code),code,codeMap.get(code),map);
	}

	public static String addCodeMsg(Map map){
		return addCodeMsg("0000",map);
	}

	public String toJsonString(){
		return new GsonBuilder().create().toJson(this);
	}

}
