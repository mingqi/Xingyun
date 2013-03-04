package com.xingyun.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResturantInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_resturantinfo);

		ListView list = (ListView) findViewById(R.id.lv_actions);

		ArrayList<HashMap<String, String>> actionList = new ArrayList<HashMap<String, String>>();

		// 预订 菜品 地图 电话
		HashMap<String, String> mOrder = new HashMap<String, String>();
		mOrder.put("title", "预订");
		HashMap<String, String> mFood = new HashMap<String, String>();
		mFood.put("title", "菜品");
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("title", "地图");
		HashMap<String, String> mTel = new HashMap<String, String>();
		mTel.put("title", "电话");
		actionList.add(mOrder);
		actionList.add(mFood);
		actionList.add(mMap);
		actionList.add(mTel);

		SimpleAdapter adapter = new SimpleAdapter(this, actionList,
				R.layout.actionlistitem,// ListItem layout
				new String[] { "title" }, new int[] { R.id.itemTitle });

		list.setAdapter(adapter);
	}

}
