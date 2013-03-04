package com.xingyun.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class HostActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost
				.newTabSpec(
						(String) this.getResources().getText(
								R.string.resturant_events))
				.setIndicator(
						(String) this.getResources().getText(
								R.string.resturant_events)
				// ,
				// getResources().getDrawable( //设置图标
				// android.R.drawable.btn_default)
				).setContent(new Intent(this, EventsActivity.class)));

		tabHost.addTab(tabHost
				.newTabSpec(
						(String) this.getResources().getText(
								R.string.book_order))
				.setIndicator(
						(String) this.getResources().getText(
								R.string.book_order))
				.setContent(new Intent(this, ResturantInfoActivity.class)));

		tabHost.addTab(tabHost
				.newTabSpec(
						(String) this.getResources().getText(R.string.profile))
				.setIndicator(
						(String) this.getResources().getText(R.string.profile))
				.setContent(new Intent(this, ResturantInfoActivity.class)));
	}
}