package com.ule.webgum.replys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JSRequest.Parameter;
import com.ule.webgum.core.JSResult;
import com.ule.webgum.core.JsResponse;


/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-03-05 14:12
 * @Creator: Liy
 * @Version: 1.0
 */

public class ContactReply implements IReply {


    private int id = IDFactory.generateID();


    private JsResponse response;
    private JSRequest request;

    public ContactReply(JSRequest request, JsResponse response) {
        this.response = response;
        this.request = request;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void reply(Intent intent) {
        Uri uri = intent.getData();
        String[] contacts = getPhoneContacts(uri, this.request.webgumView.getContext());
        JSResult<String> result = new JSResult<>();
        result.put("name",contacts[0]);
        result.put("telno",contacts[1]);
        Parameter parameter = request.getParam(0);
        response.send(parameter, result);
    }


    public static String[] getPhoneContacts(Uri uri, Context context) {
        String[] str = new String[2];
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return str;
        }
        if (cursor.moveToFirst()) {
            //   获得联系人记录的ID
            String contactId = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));
            //  获得联系人的名字
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            str[0] = name;
            String phoneNumber = "未找到联系人号码";
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.
                    Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.
                    Phone.CONTACT_ID + "=" + "?", new String[]{contactId}, null);
            if (phoneCursor.moveToFirst()) {
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                str[1] = phoneNumber;
            }
            //  关闭查询手机号码的cursor
            phoneCursor.close();

        }
        //  关闭查询联系人信息的cursor
        cursor.close();
        return str;
    }
}
