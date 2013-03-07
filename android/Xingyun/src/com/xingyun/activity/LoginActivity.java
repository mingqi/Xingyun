package com.xingyun.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final ListView list = (ListView) findViewById(R.id.lv_form);

		ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();

		// 手机号 密码
		HashMap<String, String> map = new HashMap<String, String>();

		formList.add(map);
		SimpleAdapter adapter = new SimpleAdapter(this, formList,
				R.layout.loginform, new String[] {}, new int[] {});

		list.setAdapter(adapter);

		Button btnBack = (Button) findViewById(R.id.btn_cancel);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// finish();
			}

		});

		Button btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String tel = ((EditText) findViewById(R.id.login_tel))
						.getText().toString();
				String password = ((EditText) findViewById(R.id.login_password))
						.getText().toString();
				Log.d("tel:", tel);
				Log.d("password:", password);
			}
		});
	}
}
