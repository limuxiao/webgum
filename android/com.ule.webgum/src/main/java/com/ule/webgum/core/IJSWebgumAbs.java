package com.ule.webgum.core;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.ule.webgum.PluginManager;
import com.ule.webgum.core.IJSWebgum;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.IWebgumView;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.core.JsResponse;

import java.lang.reflect.Method;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-09 14:28
 * @Creator: Liy
 * @Version: 1.0
 */

public abstract class IJSWebgumAbs implements IJSWebgum {

	public static final String PLUGIN_MAIN = "__main__";

	protected PluginManager pluginManager;
	protected IWebgumView webgumView;

	public IJSWebgumAbs(IWebgumView webgumView) {
		this.webgumView = webgumView;
		this.pluginManager = this.webgumView.getPluginManager();
	}

	/**
	 * 处理即时返回的js调用
	 * @param req
	 * @return
	 */
	@JavascriptInterface
	public String onJsCallWithResult(String req){
		JSRequest request = JSRequest.parse(this.webgumView, req);
		if(request == null || TextUtils.isEmpty(request.pluginName) || TextUtils.isEmpty(request.methodName) || request.id < 0)
			return new JSResult("9999").toJsonString();
		try{

			if(PLUGIN_MAIN.equals(request.pluginName)){			//主对象
				return callMainWithResult(request);

			}else{	//调用模块方法
				return callPluginWithResult(request);
			}

		}catch (Exception e){
			e.printStackTrace();
			return new JSResult("9991").toJsonString();

		}
	}


	/**
	 * 处理监听回调的js调用
	 * @param req
	 */
	@JavascriptInterface
	public void onJsCallWithListener(String req){
		JSRequest request = JSRequest.parse(this.webgumView, req);
		JsResponse response = request.createResponse();
		if(request == null || TextUtils.isEmpty(request.pluginName) || TextUtils.isEmpty(request.methodName) || request.id < 0)
			return;
		try{
			if(PLUGIN_MAIN.equals(request.pluginName)){			//主对象
				callMainWithListener(request,response);
			}else{	//调用模块方法
				callPluginWithListener(request,response);
			}
		}catch (Exception e){
			e.printStackTrace();
		}


	}


	//调用本对象里的方法
	private String callMainWithResult(JSRequest request) throws Exception{
		return invokeWithResult(this, request);
	}

	//调用插件中的方法
	private String callPluginWithResult(JSRequest request) throws Exception{
		IWebgumPlugin plugin = this.pluginManager.getPlugin(request.pluginName);
		if(plugin == null)
			return new JSResult("1002").toJsonString();

		return invokeWithResult(plugin, request);
	}

	//调用本对象里的方法
	private void callMainWithListener(JSRequest request,JsResponse response) throws Exception{
		invokeWithListener(this, request, response);
	}

	//调用插件中的方法
	private void callPluginWithListener(JSRequest request,JsResponse response) throws Exception{
		IWebgumPlugin plugin = this.pluginManager.getPlugin(request.pluginName);
		if(plugin != null)
			invokeWithListener(plugin,request,response);
	}


	//反射调用方法
	private String invokeWithResult(Object obj, JSRequest request) throws Exception{
		if(request.params == null || request.params.size() <= 0){				//没有参数，调用无参方法
			Method method = obj.getClass().getMethod(request.methodName);
			return String.valueOf(method.invoke(obj));
		}else{
			Method method = obj.getClass().getMethod(request.methodName, new Class<?>[]{JSRequest.class});
			return String.valueOf(method.invoke(obj, request));
		}
	}

	//反射调用方法
	private void invokeWithListener(Object obj ,JSRequest request,JsResponse response) throws Exception{
		Method method = obj.getClass().getMethod(request.methodName, new Class<?>[]{JSRequest.class, JsResponse.class});
		method.invoke(obj,request,response);
	}

}
