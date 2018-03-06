package com.ule.webgum.replys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.ule.webgum.replys.IReply;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.core.JsResponse;
import com.ule.webgum.tools.AlbumTool;
import com.ule.webgum.tools.ImageTool;

import java.io.File;


/**
 * @Title:      打开相册后的应答
 * @Desc:
 * @CreateTime: 2017-08-31 10:55
 * @Creator: Liy
 * @Version: 1.0
 */

public class AlbumReply implements IReply {

    private Activity activity;

    private JsResponse response;
    private JSRequest request;

    public AlbumReply(Activity activity, JSRequest request, JsResponse response){
        this.activity = activity;
        this.request = request;
        this.response = response;
    }


    @Override
    public int getId() {
        return 0x0001;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void reply(Intent data) {

        if(data == null) return;
        Uri uri = data.getData();
        String path = AlbumTool.getPath(this.activity,uri);

        if(path == null){
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor actualimagecursor = this.activity.getContentResolver().query(uri,proj,null,null,null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            path = actualimagecursor.getString(actual_image_column_index);
        }

        File file = new File(path);

        JSRequest.Parameter param = request.getParam(0);

        try{
            if (file.exists()) {
                Bitmap bitmap = ImageTool.getBitmap(file.getPath());
                bitmap = ImageTool.compressAndGenImage(bitmap,500);
                String imageBase64 = ImageTool.bitmapToBase64(bitmap,100);
                imageBase64 = imageBase64.replaceAll("\n","");

                JSResult result = new JSResult();
                result.put("imagePath", path);
                result.put("imageBase64", "data:image/jpg;base64," + imageBase64);
                response.send(param, result);
            } else {
                response.send(param, new JSResult(false,"9990","用户取消"));
            }
        }catch (Exception e){
            response.send(param, new JSResult(false,"9999","处理异常"));
        }
    }


}
