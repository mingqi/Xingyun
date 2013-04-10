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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
	private ArrayList<Dish> dishList = new ArrayList<Dish>();;
	private TextView txtAllDish;
	private TextView txtHotDish;
	private TextView txtColdDish;
	private TextView txtOtherDish;
	private RelativeLayout loadingPanel;
	private int selectedTextViewColor;
	private int unSelectedTextViewColor;
	GetDishesTask dTask;
	private View footerView;
	private int pageIndex = 1;
	private int pageCount = Configuration.MAX_PAGE_COUNT;
	private boolean isLoading = false;

	private DishType selectedDishType = DishType.ALL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdishes);
		selectedTextViewColor = getResources().getColor(
				R.color.theme_color_dark);
		unSelectedTextViewColor = getResources().getColor(R.color.theme_color);
		loadingPanel = (RelativeLayout) this.findViewById(R.id.loadingPanel);

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
		dTask.execute(selectedDishType);

		footerView = (View) LayoutInflater.from(this).inflate(
				R.layout.footerview, null);

		txtAllDish = (TextView) findViewById(R.id.txt_alldish);
		txtHotDish = (TextView) findViewById(R.id.txt_hotdish);
		txtColdDish = (TextView) findViewById(R.id.txt_colddish);
		txtOtherDish = (TextView) findViewById(R.id.txt_otherdish);

		txtAllDish.setBackgroundColor(selectedTextViewColor);

		txtAllDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetState();
				txtAllDish.setBackgroundColor(selectedTextViewColor);
				clearList();
				dTask.cancel(true);
				selectedDishType = DishType.ALL;
				dTask = new GetDishesTask();
				dTask.execute(selectedDishType);
			}

		});

		txtHotDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetState();
				txtHotDish.setBackgroundColor(selectedTextViewColor);
				clearList();
				dTask.cancel(true);
				selectedDishType = DishType.HOT;
				dTask = new GetDishesTask();
				dTask.execute(selectedDishType);
			}

		});

		txtColdDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetState();
				txtColdDish.setBackgroundColor(selectedTextViewColor);
				clearList();
				dTask.cancel(true);
				selectedDishType = DishType.COLD;
				dTask = new GetDishesTask();
				dTask.execute(selectedDishType);
			}

		});

		txtOtherDish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetState();
				txtOtherDish.setBackgroundColor(selectedTextViewColor);
				clearList();
				dTask.cancel(true);
				selectedDishType = DishType.OTHER;
				dTask = new GetDishesTask();
				dTask.execute(selectedDishType);
			}

		});

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				if (!isLoading && pageIndex <= pageCount
						&& arg0.getLastVisiblePosition() == arg0.getCount() - 1) {
					// listView.addFooterView(footerView);

					Log.d("time to load data!", arg0.getLastVisiblePosition()
							+ ":" + (arg0.getCount() - 1));
					isLoading = true;
					dTask = new GetDishesTask();
					dTask.execute(selectedDishType);

				}

			}
		});
		listView.setVisibility(View.GONE);
		listView.addFooterView(footerView);
		fillDishesData();
	}

	private void clearList() {
		dishList = new ArrayList<Dish>();
		adapter = new DishListAdapter(this, dishList, listView, 0);
		listView.setAdapter(adapter);
		loadingPanel.setVisibility(View.VISIBLE);
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

	private void resetState() {
		txtAllDish.setBackgroundColor(unSelectedTextViewColor);
		txtHotDish.setBackgroundColor(unSelectedTextViewColor);
		txtColdDish.setBackgroundColor(unSelectedTextViewColor);
		txtOtherDish.setBackgroundColor(unSelectedTextViewColor);

		pageIndex = 1;
		pageCount = Configuration.MAX_PAGE_COUNT;
		dishList = new ArrayList<Dish>();
		listView.setVisibility(View.GONE);
		isLoading = false;
		try {
			listView.removeFooterView(footerView);
		} catch (Exception ex) {
			// nothing is wrong
		}
		listView.addFooterView(footerView);
	}

	class GetDishesTask extends AsyncTask<Object, Object, String> {
		String dishes; // json格式的字符串

		@Override
		protected void onPostExecute(String result) {
			isLoading = false;
			loadingPanel.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);

			if (dishes == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.failed_to_get_data),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				try {
					// {"items": [{"category": 2, "title": "鱼香肉丝", "price": 12,
					// "menu_item_id": 1, "image_uri": "1.jpg", "sorted_seq":
					// 1}, {"category": 2, "title": "麻辣鸡丝", "price": 123,
					// "menu_item_id": 2, "image_uri": "2.jpg", "sorted_seq":
					// 2}], "page_number": 1, "page_size": 2, "pages_count": 4}

					JSONObject jo = new JSONObject(dishes);
					String dishArrayStr = jo.getString("items");
					pageCount = jo.getInt("pages_count");

					JSONArray jsonArray = new JSONArray(dishArrayStr);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject dish = (JSONObject) jsonArray.get(i);
						int category = dish.getInt("category");
						String title = dish.getString("title");
						String price = dish.getString("price");
						int menuItemId = dish.getInt("menu_item_id");
						String imageUri = dish.getString("image_uri");

						if (imageUri != null) {
							imageUri = StringUtility.replaceLast(imageUri, ".",
									"_100x100.");
						}

						int sortedSeq = dish.getInt("sorted_seq");
						Dish dishObj = new Dish(category, menuItemId,
								sortedSeq, title, price, imageUri);
						dishList.add(dishObj);
					}
					Log.d(OrderDishesActivity.class.getName(), "list length:"
							+ dishList.size() + "");

					synchronized (dishList) {
						adapter.notifyDataSetChanged();
					}
					if (pageIndex >= pageCount) {
						listView.removeFooterView(footerView);
					}
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
					path = Configuration.WS_GETMENU + "?page=" + pageIndex++
							+ "&page_size="
							+ Configuration.LAZYLOAD_ITEM_DISPLAY_COUNT;
				} else {
					path = Configuration.WS_GETMENU + "?category="
							+ queryType.ordinal() + "&page=" + pageIndex++
							+ "&page_size="
							+ Configuration.LAZYLOAD_ITEM_DISPLAY_COUNT;
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
				Log.e(OrderDishesActivity.class.getName(), e.getMessage());
			}
			return dishes;
		}

	}

}
