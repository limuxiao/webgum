package com.ule.webgum;

import android.content.Context;
import android.text.TextUtils;

import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JsResponse;
import com.ule.webgum.plugins.MainPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-01 11:02
 * @Creator: Liy
 * @Version: 1.0
 */

final public class Webgum {

	/**
	 * 插件集合
	 */
	private static final List<IWebgumPlugin> plugins = new ArrayList<>();


	private static boolean ready = false;

	private static String preLoadJs;

	/**
	 *
	 * @param context
	 */
	public final static void init(Context context){
		loadJsFile(context);
		plugins.add(new MainPlugin());
	}

	/**
	 * 读取注入js文件
	 * @param context
	 */
	private final static void loadJsFile(Context context){

		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = context.getAssets().open("wg_android.min.js");
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			sb.append(new String(buffer));
			is.close();
			preLoadJs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加单个插件
	 * @param plugin
	 */
	public final static void addPlugin(IWebgumPlugin plugin){
		plugins.add(plugin);
		LoadJsFromPlugin(plugin);
	}

	/**
	 * 添加插件列表
	 * @param plugins
	 */
	public final static void addPlugins(List<IWebgumPlugin> plugins){
		for(IWebgumPlugin plugin : plugins){
			addPlugin(plugin);
		}
	}

	private final static void LoadJsFromPlugin(IWebgumPlugin plugin){

		StringBuilder sb = new StringBuilder("WG_android.prototype." + plugin.getName() + "={");

		//动态注入js
		Method[] methods = plugin.getClass().getMethods();
		for(Method m : methods){

			if(checkMethod(m)){

				JSMethod jsMethod = m.getAnnotation(JSMethod.class);
				String name = TextUtils.isEmpty(jsMethod.name()) ? m.getName() : jsMethod.name();
				sb.append(name + ":function(){");
				String returnType = m.getReturnType().getSimpleName().toLowerCase();
				if(String.class.getSimpleName().toLowerCase().equals(returnType)){
					sb.append("return __bridgeWithResult('"+ plugin.getName() +"','"+ m.getName() +"',arguments)");
				}else if(Void.class.getSimpleName().toLowerCase().equals(returnType)){
					sb.append("__bridgeWithListener('"+ plugin.getName() +"','"+ m.getName() +"',arguments)");
				}
				sb.append("},");
			}

		}
		sb.append("};");
		preLoadJs += sb.toString();
	}

	private static boolean checkMethod(Method m){
		// 检查注解
		JSMethod jsMethod = m.getAnnotation(JSMethod.class);

		if (jsMethod == null)
			return false;

		// 检查返回值
		String returnType = m.getReturnType().getSimpleName().toLowerCase();
		int type = 0;
		if (String.class.getSimpleName().toLowerCase().equals(returnType)) type = 1;
		if (Void.class.getSimpleName().toLowerCase().equals(returnType)) type = 2;
		if(type == 0)
			return false;

		// 检查参数
		Class[] argTypes = m.getParameterTypes();
		if(argTypes.length == 0){
			return true;
		}else if(argTypes.length == 1){
			return JSRequest.class.getSimpleName().equals(argTypes[0].getSimpleName());
		}else if(argTypes.length == 2){
			return JSRequest.class.getSimpleName().equals(argTypes[0].getSimpleName())
					&& JsResponse.class.getSimpleName().equals(argTypes[1].getSimpleName());
		}else{
			return false;
		}
	}

	/**
	 * 创建插件管理器
	 * @return
	 */
	public static PluginManager createManager(){
		PluginManager pm = new PluginManager();
		for(IWebgumPlugin plugin : plugins){
			pm.addPlugin(plugin);
		}
		return pm;
	}

	public static boolean isReady() {
		return ready;
	}

	public static String getPreLoadJs(){
		return preLoadJs;
	}
}
