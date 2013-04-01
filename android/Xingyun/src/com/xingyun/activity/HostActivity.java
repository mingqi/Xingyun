package com.xingyun.activity;

import com.xingyun.persistence.UserManager;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;

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
								R.string.resturant_events),
						getResources().getDrawable(R.drawable.gift))
				.setContent(new Intent(this, EventsActivity.class)));

		tabHost.addTab(tabHost
				.newTabSpec(
						(String) this.getResources().getText(
								R.string.book_order))
				.setIndicator(
						(String) this.getResources().getText(
								R.string.book_order),
						getResources().getDrawable(R.drawable.fork_knife))
				.setContent(new Intent(this, ResturantInfoActivity.class)));

		if (UserManager.isLogin()) {
			tabHost.addTab(tabHost
					.newTabSpec(
							(String) this.getResources().getText(
									R.string.profile))
					.setIndicator(
							(String) this.getResources().getText(
									R.string.profile),
							getResources().getDrawable(R.drawable.man))
					.setContent(new Intent(this, UserProfileActivity.class)));

		} else {

			tabHost.addTab(tabHost
					.newTabSpec(
							(String) this.getResources().getText(
									R.string.profile))
					.setIndicator(
							(String) this.getResources().getText(
									R.string.profile),
							getResources().getDrawable(R.drawable.man))
					.setContent(
							new Intent(this, UserProfileActivityGroup.class)));

		}

		TabWidget tabWidget = tabHost.getTabWidget();
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			tabWidget.getChildAt(i).getLayoutParams().height = 80;
		}
	}
}