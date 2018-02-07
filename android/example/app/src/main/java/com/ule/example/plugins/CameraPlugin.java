package com.ule.example.plugins;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

import com.ule.example.functions.album.AlbumReply;
import com.ule.example.functions.common.ReplyManager;
import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.core.JsResponse;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 14:38
 * @Creator: Liy
 * @Version: 1.0
 */

public class CameraPlugin extends IWebgumPlugin {



	public CameraPlugin(String pluginName, String pluginVersion){
		super(pluginName, pluginVersion);
	}

	@JSMethod
	public void openAlbum(JSRequest request, JsResponse response){
		Intent openAlbumIntent = new Intent("android.intent.action.GET_CONTENT");
		openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		Activity acty = (Activity)request.webView.getContext();
		AlbumReply albumReply = new AlbumReply(acty,request,response);
		ReplyManager.addReply(albumReply);
		acty.startActivityForResult(openAlbumIntent, albumReply.getId());
	}


	@JSMethod
	public void test(JSRequest request, JsResponse response){
		JSRequest.Parameter param = request.getParam(0);
		JSResult result = new JSResult();
		result.put("test","22");
		response.send(param, result);
	}



}
