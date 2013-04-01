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

		Intent i = this.getIntent();
		String nextStep;
		try {
			nextStep = i.getStringExtra("nextstep").toString();
		} catch (Exception ex) {
			nextStep = "";
		}
		Intent intentToShow = new Intent(UserProfileActivityGroup.this,
				LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentToShow.putExtra("nextstep", nextStep);
		View view = getLocalActivityManager().startActivity("activity_login",
				intentToShow).getDecorView();
		setContentView(view);
	}

}