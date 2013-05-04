package com.xingyun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class NavigationHomeAcvitity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_home);
		
		findViewById(R.id.nav1ViewGroup).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(NavigationHomeAcvitity.this,
						EventsActivity.class);
				startActivity(intent);
			}
		});
		
		findViewById(R.id.nav2ViewGroup).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(NavigationHomeAcvitity.this,
						ResturantInfoActivity.class);
				startActivity(intent);
			}
		});
		
		findViewById(R.id.nav3ViewGroup).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(NavigationHomeAcvitity.this,
						SignupActivity.class);
				startActivity(intent);
			}
		});
	}

}
