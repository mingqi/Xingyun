package com.xingyun.activity;

import java.util.ArrayList;

import com.xingyun.adapter.DishListAdapter;
import com.xingyun.entity.Dish;
import com.xingyun.persistence.CartManager;
import com.xingyun.persistence.UserManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ConfirmOrderActivity extends Activity {
	private ListView listView;
	private DishListAdapter adapter;
	private ArrayList<Dish> dishes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmorder);

		listView = (ListView) findViewById(R.id.lv_dishes);

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
				if (UserManager.isLogin()) {
					Intent i = new Intent();
					i.setClass(ConfirmOrderActivity.this,
							OrderInfoActivity.class);
					startActivity(i);
				} else {
					Intent i = new Intent();
					i.setClass(ConfirmOrderActivity.this,
							UserProfileActivityGroup.class);
					i.putExtra("nextstep", "order");
					startActivity(i);
				}
			}

		});

		dishes = CartManager.getPureDishes();
		adapter = new DishListAdapter(this, dishes, listView, 1);
		listView.setAdapter(adapter);

		this.reloadPrice();
	}

	public void reloadPrice() {
		String priceStr = "请确认您要点的菜品 一共" + CartManager.getTotalPrice() + "元";
		TextView txtTotalPrice = (TextView) this
				.findViewById(R.id.txt_totalprice);
		txtTotalPrice.setText(priceStr);
	}

	public void removeRow(Dish dish) {
		dishes.remove(dish);
		adapter.notifyDataSetChanged();
	}
}