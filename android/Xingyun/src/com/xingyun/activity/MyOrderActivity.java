package com.xingyun.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyOrderActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		ListView lvOrders = (ListView) findViewById(R.id.lv_orders);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < 3; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "就餐人：孙先生");
			map.put("time", "就餐时间：2013-04-02 12:00");
			map.put("status", "状态：预订成功");
			list.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.orderlistitem,
				new String[] { "name", "time", "status" }, new int[] {
						R.id.txtName, R.id.txtTime, R.id.txtStatus });
		lvOrders.setAdapter(adapter);

		lvOrders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ListView listView = (ListView) arg0;
				// HashMap<String, String> map = (HashMap<String, String>)
				// listView
				// .getItemAtPosition(arg2);
				Intent i = new Intent();
				i.setClass(MyOrderActivity.this, ViewOrderActivity.class);
				startActivity(i);
			}

		});
	}
}