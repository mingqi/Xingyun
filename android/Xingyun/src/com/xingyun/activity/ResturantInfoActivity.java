package com.xingyun.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResturantInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_resturantinfo);

		final ListView list = (ListView) findViewById(R.id.lv_actions);

		ArrayList<HashMap<String, String>> actionList = new ArrayList<HashMap<String, String>>();

		// 预订 菜品 地图 电话
		HashMap<String, String> mOrder = new HashMap<String, String>();
		mOrder.put("title",
				this.getResources().getString(R.string.resturant_introduction));
		HashMap<String, String> mFood = new HashMap<String, String>();
		mFood.put("title", this.getResources().getString(R.string.all_dishes));
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("title", this.getResources().getString(R.string.map));
		HashMap<String, String> mTel = new HashMap<String, String>();
		mTel.put("title",
				this.getResources().getString(R.string.resturant_phone_number));
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
				switch (arg2) {
				case 0:
					Intent detailIntent = new Intent();
					detailIntent.setClass(ResturantInfoActivity.this,
							ResturantDetailActivity.class);
					startActivity(detailIntent);
					break;
				case 1:

					Intent dishesIntent = new Intent();
					dishesIntent.setClass(ResturantInfoActivity.this,
							OrderDishesActivity.class);
					startActivity(dishesIntent);
					break;
				case 2:
					// 地图
					Intent mapIntent = new Intent();
					mapIntent.setClass(ResturantInfoActivity.this,
							MapActivity.class);
					startActivity(mapIntent);
					break;
				case 3:
					// 电话
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:09962218222"));
					startActivity(callIntent);
					break;
				}
			}

		});

		Button btnOrderDishes = (Button) findViewById(R.id.btn_orderdishes);
		btnOrderDishes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ResturantInfoActivity.this,
						OrderDishesActivity.class);
				startActivity(intent);
			}

		});
	}

}
