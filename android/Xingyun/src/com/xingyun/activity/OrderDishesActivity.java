package com.xingyun.activity;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.SimpleAdapter;

public class OrderDishesActivity extends Activity {

	private ListView listView;
	private SimpleAdapter adapter;
	private ArrayList<HashMap<String, Object>> dishList;
	private View footerView;

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

		// /////////////show dishes/////////////////////////////

		// todo: construct dish arraylist from a list of dish object.

		listView.addFooterView(footerView);

		dishList = new ArrayList<HashMap<String, Object>>();

		ArrayList<Dish> dishes = WSUtility.getDishes(DishType.ALL);
		for (int i = 0; i < dishes.size(); i++) {
			Dish dish = dishes.get(i);
			HashMap<String, Object> mapping = new HashMap<String, Object>();
			mapping.put("name", dish.getName());
			mapping.put("price", dish.getPrice() + "");
			dishList.add(mapping);
		}

		loadData();

		Log.d("dish size", "" + dishList.size());

		final SimpleAdapter adapter = new SimpleAdapter(this, dishList,
				R.layout.dishlistitem,
				new String[] { "image", "name", "price" }, new int[] {
						R.id.dishImage, R.id.dishName, R.id.dishPrice });

		// listView.setAdapter(adapter);

		listView.setAdapter(new DishListAdapter(this, dishes, listView));

		// listView.setOnScrollListener(listener);
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				Log.d("===last visible position",
						"" + arg0.getLastVisiblePosition());
				Log.d("===view count", "" + arg0.getCount());

				if (arg0.getLastVisiblePosition() == arg0.getCount() - 1) {
					loadData();
					adapter.notifyDataSetChanged();
				}
			}

		});
	}

	private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				loadData();
				adapter.notifyDataSetChanged();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}
	};

	/**
	 * 构造List列表数据
	 */
	private void loadData() {
		if (dishList.size() < 40) {
			for (int i = 0; i < 10; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("image", R.drawable.ic_launcher);
				map.put("name", "t");
				map.put("price", "p" + i);
				dishList.add(map);
			}
		} else {
			listView.removeFooterView(footerView);
		}
	}

}
