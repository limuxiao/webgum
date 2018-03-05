package com.ule.webgum.plugins;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

import com.ule.webgum.replys.AlbumReply;
import com.ule.webgum.replys.CameraReply;
import com.ule.webgum.replys.ReplyManager;
import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSRequest;
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

	/**
	 * 打开相册
	 * @param request
	 * @param response
	 */
	@JSMethod
	public void openAlbum(JSRequest request, JsResponse response){
		Intent openAlbumIntent = new Intent("android.intent.action.GET_CONTENT");
		openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		Activity acty = (Activity)request.webgumView.getContext();
		AlbumReply albumReply = new AlbumReply(acty,request,response);
		ReplyManager.addReply(albumReply);
		acty.startActivityForResult(openAlbumIntent, albumReply.getId());
	}

	/**
	 * 打开摄像头
	 * @param request
	 * @param response
	 */
	@JSMethod
	public void openCamera(JSRequest request, JsResponse response){
		Intent openCameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
		Activity acty = (Activity)request.webgumView.getContext();
		CameraReply cameraReply = new CameraReply(acty,request,response);
		ReplyManager.addReply(cameraReply);
		openCameraIntent.putExtra("output", cameraReply.getImgUri());
		acty.startActivityForResult(openCameraIntent, cameraReply.getId());
	}

}
