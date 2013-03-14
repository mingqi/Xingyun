package com.xingyun.activity;

import java.util.ArrayList;

import com.xingyun.adapter.DishListAdapter;
import com.xingyun.entity.Dish;
import com.xingyun.entity.DishType;
import com.xingyun.utility.WSUtility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OrderDishesActivity extends Activity {

	private ListView listView;
	private DishListAdapter adapter;
	private View footerView;
	private ArrayList<Dish> dishes;

	TextView txtAllDish;
	TextView txtHotDish;
	TextView txtColdDish;
	TextView txtOtherDish;
	int selectedTextView = Color.rgb(102, 0, 0);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdishes);

		listView = (ListView) findViewById(R.id.lv_dishes);
		footerView = (View) LayoutInflater.from(this).inflate(
				R.layout.loadinglistitem, null);

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});

		Button btnConfirmOrder = (Button) findViewById(R.id.btn_confirmorder);
		btnConfirmOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(OrderDishesActivity.this, ConfirmOrderActivity.class);
				startActivity(i);
			}

		});

		listView.addFooterView(footerView);

		dishes = WSUtility.getDishes(DishType.ALL);
		adapter = new DishListAdapter(this, dishes, listView, 0);
		listView.setAdapter(adapter);

		Log.e("====", "???/");

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.e("====", "???/!!");
				Intent i = new Intent();
				i.setClass(OrderDishesActivity.this, DishDetailActivity.class);
				startActivity(i);
			}

		});

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				if (arg0.getLastVisiblePosition() == arg0.getCount() - 1) {
					Log.d("time to load data!", arg0.getLastVisiblePosition()
							+ ":" + (arg0.getCount() - 1));
					loadData();
					adapter.notifyDataSetChanged();
				}
			}
		});

		txtAllDish = (TextView) findViewById(R.id.txt_alldish);
		txtHotDish = (TextView) findViewById(R.id.txt_hotdish);
		txtColdDish = (TextView) findViewById(R.id.txt_colddish);
		txtOtherDish = (TextView) findViewById(R.id.txt_otherdish);

		txtAllDish.setBackgroundColor(selectedTextView);

		txtAllDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtAllDish.setBackgroundColor(selectedTextView);
			}

		});

		txtHotDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtHotDish.setBackgroundColor(selectedTextView);
			}

		});

		txtColdDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtColdDish.setBackgroundColor(selectedTextView);
			}

		});

		txtOtherDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtOtherDish.setBackgroundColor(selectedTextView);
			}

		});

	}

	private void loadData() {
		if (dishes.size() < 100) {
			for (int i = 0; i < 10; i++) {
				dishes.add(new Dish("dish" + i, "价格: " + i,
						"http://www.baidu.com/img/shouye_b5486898c692066bd2cbaeda86d74448.gif"));
			}
		} else {
			listView.removeFooterView(footerView);
		}
	}

	private void resetTextViewColor() {
		txtAllDish.setBackgroundColor(Color.rgb(139, 34, 82));
		txtHotDish.setBackgroundColor(Color.rgb(139, 34, 82));
		txtColdDish.setBackgroundColor(Color.rgb(139, 34, 82));
		txtOtherDish.setBackgroundColor(Color.rgb(139, 34, 82));
	}
}
