package com.xingyun.activity;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.xingyun.adapter.DishListAdapter;
import com.xingyun.entity.Dish;
import com.xingyun.entity.DishType;
import com.xingyun.setting.Configuration;
import com.xingyun.utility.StringUtility;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * “菜单”页面
 * 
 * @author liang
 * 
 */
public class OrderDishesActivity extends Activity {

	private ListView listView;
	private DishListAdapter adapter;
	private ArrayList<Dish> dishList;
	private TextView txtAllDish;
	private TextView txtHotDish;
	private TextView txtColdDish;
	private TextView txtOtherDish;
	private int selectedTextViewColor = Color.rgb(102, 0, 0);
	private int unSelectedTextViewColor = Color.rgb(139, 34, 82);
	GetDishesTask dTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdishes);

		listView = (ListView) findViewById(R.id.lv_dishes);

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

		// 通过异步方法获取菜单
		dTask = new GetDishesTask();
		dTask.execute(DishType.ALL);

		txtAllDish = (TextView) findViewById(R.id.txt_alldish);
		txtHotDish = (TextView) findViewById(R.id.txt_hotdish);
		txtColdDish = (TextView) findViewById(R.id.txt_colddish);
		txtOtherDish = (TextView) findViewById(R.id.txt_otherdish);

		txtAllDish.setBackgroundColor(selectedTextViewColor);

		txtAllDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtAllDish.setBackgroundColor(selectedTextViewColor);
				dTask.cancel(true);
				dTask = new GetDishesTask();
				dTask.execute(DishType.ALL);
			}

		});

		txtHotDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtHotDish.setBackgroundColor(selectedTextViewColor);
				dTask.cancel(true);
				dTask = new GetDishesTask();
				dTask.execute(DishType.HOT);
			}

		});

		txtColdDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtColdDish.setBackgroundColor(selectedTextViewColor);
				dTask.cancel(true);
				dTask = new GetDishesTask();
				dTask.execute(DishType.COLD);
			}

		});

		txtOtherDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetTextViewColor();
				txtOtherDish.setBackgroundColor(selectedTextViewColor);
				dTask.cancel(true);
				dTask = new GetDishesTask();
				dTask.execute(DishType.OTHER);
			}

		});

	}

	private void fillDishesData() {
		adapter = new DishListAdapter(this, dishList, listView, 0);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) {
				Intent i = new Intent();
				i.setClass(OrderDishesActivity.this, DishDetailActivity.class);
				Dish selectedDish = dishList.get(index);
				i.putExtra("name", selectedDish.getName());
				i.putExtra("imageUrl", selectedDish.getImageUrl());
				i.putExtra("price", selectedDish.getPrice());
				i.putExtra("id", selectedDish.getMenuItemId());
				startActivity(i);
			}
		});
	}

	private void resetTextViewColor() {
		txtAllDish.setBackgroundColor(unSelectedTextViewColor);
		txtHotDish.setBackgroundColor(unSelectedTextViewColor);
		txtColdDish.setBackgroundColor(unSelectedTextViewColor);
		txtOtherDish.setBackgroundColor(unSelectedTextViewColor);
	}

	class GetDishesTask extends AsyncTask<Object, Object, String> {
		String dishes; // json格式的字符串

		@Override
		protected void onPostExecute(String result) {
			if (dishes == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.failed_to_get_data),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				try {
					dishList = new ArrayList<Dish>();
					JSONArray jsonArray = new JSONArray(dishes);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject dish = (JSONObject) jsonArray.get(i);
						int category = dish.getInt("category");
						String title = dish.getString("title");
						String price = dish.getString("price");
						int menuItemId = dish.getInt("menu_item_id");
						String imageUri = dish.getString("image_uri");

						if (imageUri != null) {
							imageUri = StringUtility.replaceLast(imageUri,
									".", "_100x100.");
						}

						int sortedSeq = dish.getInt("sorted_seq");
						Dish dishObj = new Dish(category, menuItemId,
								sortedSeq, title, price, imageUri);
						dishList.add(dishObj);
					}
					Log.e("list length", dishList.size() + "");
					fillDishesData();
				} catch (JSONException ex) {
					Toast toast = Toast.makeText(
							getApplicationContext(),
							getResources().getString(
									R.string.failed_to_get_data),
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				DishType queryType = (DishType) params[0];

				String path;
				if (queryType == DishType.ALL) {
					path = Configuration.WS_GETMENU;
				} else {
					path = Configuration.WS_GETMENU + "?category="
							+ queryType.ordinal();
				}

				HttpGet httpGet = new HttpGet(path);

				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters,
						Configuration.WS_CONNTIMEOUT);
				HttpConnectionParams.setSoTimeout(httpParameters,
						Configuration.WS_SOCKETTIMEOUT);
				DefaultHttpClient httpClient = new DefaultHttpClient(
						httpParameters);
				HttpResponse response = httpClient.execute(httpGet);
				InputStream is = response.getEntity().getContent();
				dishes = StringUtility.inputstreamToString(is);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return dishes;
		}

	}

}
