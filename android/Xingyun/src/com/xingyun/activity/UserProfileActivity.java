package com.xingyun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;

public class UserProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userprofile);

		TableRow trMyOrder = (TableRow) findViewById(R.id.tr_myorder);
		trMyOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(UserProfileActivity.this, MyOrderActivity.class);
				startActivity(i);
			}

		});

		TableRow trPassword = (TableRow) findViewById(R.id.tr_password);
		trPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(UserProfileActivity.this,
						ModifyPasswordActivity.class);
				startActivity(i);
			}

		});
	}
}