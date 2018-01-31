package com.ule.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ule.example.test.TestActivity;


public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}


	public void toJsBridge(View view){
		Intent intent = new Intent(this, TestActivity.class);
		startActivity(intent);
	}



}
