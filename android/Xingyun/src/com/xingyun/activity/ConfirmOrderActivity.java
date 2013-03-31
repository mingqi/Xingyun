package com.xingyun.activity;

import java.util.ArrayList;

import com.xingyun.adapter.DishListAdapter;
import com.xingyun.entity.Dish;
import com.xingyun.entity.DishType;
import com.xingyun.utility.WSUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ConfirmOrderActivity extends Activity {
	private ListView listView;
	private DishListAdapter adapter;
//	private View footerView;
	private ArrayList<Dish> dishes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmorder);
		
		listView = (ListView) findViewById(R.id.lv_dishes);
//		footerView = (View) LayoutInflater.from(this).inflate(
//				R.layout.loadinglistitem, null);

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
				Intent i = new Intent();
				i.setClass(ConfirmOrderActivity.this, OrderInfoActivity.class);
				startActivity(i);
			}

		});

//		listView.addFooterView(footerView);
		
		dishes = WSUtility.getDishes(DishType.ALL);
		adapter = new DishListAdapter(this, dishes, listView, 1);
		listView.setAdapter(adapter);
	}
}