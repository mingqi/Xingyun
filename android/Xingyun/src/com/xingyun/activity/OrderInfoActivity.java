package com.xingyun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OrderInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderinfo);

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});

		Button btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}

		});
	}
}