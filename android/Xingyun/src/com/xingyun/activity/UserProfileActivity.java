package com.xingyun.activity;

import com.xingyun.persistence.UserManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class UserProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!UserManager.isLogin()) {
			View view = UserProfileActivityGroup.group
					.getLocalActivityManager()
					.startActivity(
							"activity_login",
							new Intent(UserProfileActivity.this,
									LoginActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView();
			UserProfileActivityGroup.group.setContentView(view);
			return;
		}

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

		TableRow trLogout = (TableRow) findViewById(R.id.tr_logout);
		trLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserManager.setLogin(false);
				View view = UserProfileActivityGroup.group
						.getLocalActivityManager()
						.startActivity(
								"activity_login",
								new Intent(UserProfileActivity.this,
										LoginActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView();
				UserProfileActivityGroup.group.setContentView(view);
			}

		});

		TextView txtWelcome = (TextView) this.findViewById(R.id.txt_welcome);
		txtWelcome.setText("欢迎您，" + UserManager.getName());
	}
}