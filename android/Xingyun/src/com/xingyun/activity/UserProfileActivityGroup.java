package com.xingyun.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressWarnings("deprecation")
public class UserProfileActivityGroup extends ActivityGroup {

	public static UserProfileActivityGroup group;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		group = this;

		View view = getLocalActivityManager().startActivity(
				"activity_login",
				new Intent(UserProfileActivityGroup.this, LoginActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView();
		setContentView(view);
	}

}