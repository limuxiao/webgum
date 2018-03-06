package com.ule.webgum.plugins;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSRequest.Parameter;
import com.ule.webgum.core.JsResponse;
import com.ule.webgum.replys.ContactReply;
import com.ule.webgum.replys.ReplyManager;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-03-05 10:59
 * @Creator: Liy
 * @Version: 1.0
 */

public class TelPlugin extends IWebgumPlugin {

    public TelPlugin(String pluginName, String pluginVersion) {
        super(pluginName, pluginVersion);
    }


    @JSMethod(name = "call")
    public void callTel(JSRequest request, JsResponse response) {
        Parameter parameter = request.getParam(0);
        Log.e("TelPlugin","callTel:" + parameter.value);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + parameter.value));
        Activity acty = (Activity) request.webgumView.getContext();
        if (ActivityCompat.checkSelfPermission(acty, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("TelPlugin","拨打电话的权限未被允许");
            return;
        }
        acty.startActivityForResult(intent, 0);
    }


    @JSMethod
    public void toPhoneList(JSRequest request, JsResponse response){
        Log.e("TelPlugin","toPhoneList");
        Activity acty = (Activity) request.webgumView.getContext();

        ContactReply reply = new ContactReply(request, response);
        ReplyManager.addReply(reply);

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        acty.startActivityForResult(intent, reply.getId());
    }

}
