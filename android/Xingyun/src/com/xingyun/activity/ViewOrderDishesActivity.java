package com.xingyun.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xingyun.adapter.DishListAdapter;
import com.xingyun.entity.Dish;
import com.xingyun.persistence.CartManager;
import com.xingyun.utility.StringUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ViewOrderDishesActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vieworderdishes);

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});

		Intent intent = this.getIntent();
		String dishes = intent.getStringExtra("dishes");

		ListView listView = (ListView) findViewById(R.id.lv_dishes);

		try {
			ArrayList<Dish> dishList = new ArrayList<Dish>();
			JSONArray ja = new JSONArray(dishes);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = (JSONObject) ja.get(i);

				String title = jo.getString("title");
				int price = jo.getInt("price");
				String imageUri = StringUtility.replaceLast(
						jo.getString("image_uri"), ".", "_100x100.");
				int quantity = jo.getInt("quantity");

				Dish dish = new Dish();
				dish.setImageUrl(imageUri);
				dish.setName(title);
				dish.setPrice(price + "");
				dish.setQuantity(quantity);

				dishList.add(dish);
			}

			DishListAdapter adapter = new DishListAdapter(this, dishList,
					listView, 2);
			listView.setAdapter(adapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}