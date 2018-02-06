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

final public class JSResult<V> {


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



	private Map<String, Object> map;

	public JSResult(boolean status,String code, String msg) {
		this.map = new HashMap<>();
		this.map.put("status",status);
		this.map.put("code",code);
		this.map.put("msg",msg);
	}

	public JSResult(boolean status, String code){
		this(status,code,codeMap.get(code));
	}

	public JSResult(String code){
		this("0000".equals(code), code);
	}

	public JSResult(){
		this("0000");
	}

	public void put(String key, V value){
		this.map.put(key, value);
	}

	public V get(String key){
		return (V) this.map.get(key);
	}

	public String toJsonString(){
		return new GsonBuilder().create().toJson(this.map);
	}

	@Override
	public String toString() {
		return this.toJsonString();
	}
}
