package com.xingyun.activity;

import java.util.ArrayList;

import com.xingyun.adapter.DishListAdapter;
import com.xingyun.entity.Dish;
import com.xingyun.entity.DishType;
import com.xingyun.utility.WSUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;

public class OrderDishesActivity extends Activity {

	private ListView listView;
	private DishListAdapter adapter;
	private View footerView;
	private ArrayList<Dish> dishes;

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

		Button btnSkip = (Button) findViewById(R.id.btn_skip);
		btnSkip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(OrderDishesActivity.this,
						OrderDetailActivity.class);
				startActivity(intent);
			}

		});

		listView.addFooterView(footerView);

		dishes = WSUtility.getDishes(DishType.ALL);
		adapter = new DishListAdapter(this, dishes, listView);
		listView.setAdapter(adapter);

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
	}

	private void loadData() {
		if (dishes.size() < 100) {
			for (int i = 0; i < 10; i++) {
				dishes.add(new Dish("dish" + i, i,
						"http://www.baidu.com/img/shouye_b5486898c692066bd2cbaeda86d74448.gif"));
			}
		} else {
			listView.removeFooterView(footerView);
		}
	}
}
