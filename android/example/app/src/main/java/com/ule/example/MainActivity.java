package com.ule.example;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ule.example.test.TestActivity;

import java.io.InputStream;


public class MainActivity extends Activity {


	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}


	public void toSystemWebView(View view){
		Intent intent = new Intent(this, SysWebActivity.class);
		startActivity(intent);
	}

	public void toCrossWebView(View view){
		Intent intent = new Intent(this, CrossWebActivity.class);
		startActivity(intent);
	}

	public void toTestPage(View view){
		Intent intent = new Intent(this, TestActivity.class);
		startActivity(intent);
	}



	/**
	 * 获取手机通讯录
	 * @param view
	 */
	public void toPhoneList(View view){
		ContentResolver resolver = this.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION,
				null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {



			}
		}

//		Intent intent = new Intent();
//		intent.setAction(Intent.ACTION_PICK);
//		intent.setData(Contacts.People.CONTENT_URI);
//		startActivity(intent);





	}


	/**
	 * 添加联系人
	 * 数据一个表一个表的添加，每次都调用insert方法
	 * */
	public void testAddContacts(View view){
        /* 往 raw_contacts 中添加数据，并获取添加的id号*/
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = getContentResolver();
		ContentValues values = new ContentValues();
		long contactId = ContentUris.parseId(resolver.insert(uri, values));

        /* 往 data 中添加数据（要根据前面获取的id号） */
		// 添加姓名
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("raw_contact_id", contactId);
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("data2", "王国维");
		resolver.insert(uri, values);

		// 添加电话
		values.clear();
		values.put("raw_contact_id", contactId);
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		values.put("data2", "2");
		values.put("data1", "15099144117");
		resolver.insert(uri, values);

		// 添加Email
		values.clear();
		values.put("raw_contact_id", contactId);
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		values.put("data2", "2");
		values.put("data1", "wangguowei@qq.com");
		resolver.insert(uri, values);
	}



}
