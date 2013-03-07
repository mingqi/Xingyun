package com.xingyun.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResturantInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_resturantinfo);

		final ListView list = (ListView) findViewById(R.id.lv_actions);

		ArrayList<HashMap<String, String>> actionList = new ArrayList<HashMap<String, String>>();

		// 预订 菜品 地图 电话
		HashMap<String, String> mOrder = new HashMap<String, String>();
		mOrder.put("title", "预订");
		HashMap<String, String> mFood = new HashMap<String, String>();
		mFood.put("title", "菜品");
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("title", "地图");
		HashMap<String, String> mTel = new HashMap<String, String>();
		mTel.put("title", "电话: 10086");
		actionList.add(mOrder);
		actionList.add(mFood);
		actionList.add(mMap);
		actionList.add(mTel);

		SimpleAdapter adapter = new SimpleAdapter(this, actionList,
				R.layout.actionlistitem,// ListItem layout
				new String[] { "title" }, new int[] { R.id.itemTitle });

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("list item index", arg2 + "");
				switch (arg2) {
				case 0:
					// 预订
					break;
				case 1:
					// 菜品
					break;
				case 2:
					// 地图
					Intent i = new Intent(getApplication(), MapActivity.class);
	                startActivity(i);
					break;
				case 3:
					// 电话
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:10086"));
					startActivity(callIntent);
					break;
				}
			}

		});
	}

}
