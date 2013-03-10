package com.xingyun.activity;

import com.xingyun.entity.Event;
import com.xingyun.excontrol.RoundedRectTextView;
import com.xingyun.utility.WSUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EventDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventdetail);

		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		int index = bundle.getInt("event_index");

		Log.d("=-=-=-=", index + "");

		Event evt = WSUtility.getEventDetailInfo(index);

		TextView txtTitle = (TextView) findViewById(R.id.txt_title);
		txtTitle.setText(evt.getTitle());

		RoundedRectTextView rtvDescription = (RoundedRectTextView) findViewById(R.id.rtv_description);
		rtvDescription.setText(evt.getDescription());

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});

		Button btnOrderDishes = (Button) findViewById(R.id.btn_orderdishes);
		btnOrderDishes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(EventDetailActivity.this,
						OrderDishesActivity.class);
				startActivity(intent);
			}

		});
	}
}
