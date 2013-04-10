package com.xingyun.activity;

import com.xingyun.utility.DBManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QueryOrderActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queryorder);

		final EditText txtPhoneNumber = (EditText) findViewById(R.id.txt_phonenumber);

		DBManager manager = new DBManager(this);
		String userPhoneNumber = manager.getUserTelephone();
		manager.closeDB();
		
		txtPhoneNumber.setText(userPhoneNumber);
		
		Button btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String phoneNumber = txtPhoneNumber.getText().toString().trim();

				if (phoneNumber.equals("")) {
					Toast toast = Toast.makeText(
							getApplicationContext(),
							getResources().getString(
									R.string.plz_input_phone_number),
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					Intent i = new Intent();
					i.putExtra("phonenumber", phoneNumber);
					i.setClass(QueryOrderActivity.this, MyOrderActivity.class);
					startActivity(i);
				}
			}

		});
	}
}
