package com.ule.webgum.replys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.ule.webgum.replys.IReply;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JsResponse;
import com.ule.webgum.tools.ImageTool;

import java.io.File;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2017-09-01 14:15
 * @Creator: Liy
 * @Version: 1.0
 */

public class CameraReply implements IReply {

    private Activity activity;

    private Uri imgUri;

    private JsResponse response;
    private JSRequest request;



    public CameraReply(Activity activity,JSRequest request, JsResponse response){
        this.activity = activity;
        this.request = request;
        this.response = response;
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "YZGHD");
        if (! imageStorageDir.exists()){
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imgUri = Uri.fromFile(file);
    }

    @Override
    public int getId() {
        return 0x0002;
    }

    public Uri getImgUri(){
        return imgUri;
    }

    @Override
    public void reply(final Intent data) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(getImgUri().getPath());
                JSRequest.Parameter param = request.getParam(0);
                try{
                    if (file.exists()) {
                        Bitmap bitmap = ImageTool.getBitmap(file.getPath());
                        bitmap = ImageTool.compressAndGenImage(bitmap,500);
                        String imageBase64 = ImageTool.bitmapToBase64(bitmap,50);
                        imageBase64 = imageBase64.replaceAll("\n","");
                        JSResult result = new JSResult();
                        result.put("imagePath", getImgUri().getPath());
                        result.put("imageBase64", imageBase64);
                        response.send(param, result);
                    } else {
                        response.send(param, new JSResult(false,"9990","用户取消"));
                    }
                }catch (Exception e){
                    response.send(param, new JSResult(false,"9999","处理异常"));
                }
            }
        }).start();



    }
}
