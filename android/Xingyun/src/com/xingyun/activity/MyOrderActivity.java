package com.xingyun.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xingyun.activity.EventsActivity.GetEventsTask;
import com.xingyun.entity.Event;
import com.xingyun.persistence.UserManager;
import com.xingyun.setting.Configuration;
import com.xingyun.utility.DateTimeUtility;
import com.xingyun.utility.StringUtility;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MyOrderActivity extends Activity {

	private List<Order> orderList;
	private MyOrderActivity activity;

	private String contactPhone;
	private RelativeLayout loadingPanel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);

		activity = this;
		Intent intent = this.getIntent();
		contactPhone = intent.getStringExtra("phonenumber");

		loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		GetOrdersTask task = new GetOrdersTask();
		task.execute();

	}

	private void fillOrderList() {
		ListView lvOrders = (ListView) findViewById(R.id.lv_orders);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < orderList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "就餐人：" + orderList.get(i).getContactName());
			map.put("time", "就餐时间：" + orderList.get(i).getReservedTime());

			String status = "";
			// status 1未处理/2预定成功/3取消
			if (orderList.get(i).getStatus() == 1) {
				status = "未处理";
			} else if (orderList.get(i).getStatus() == 2) {
				status = "预定成功";
			} else if (orderList.get(i).getStatus() == 3) {
				status = "取消";
			}

			map.put("status", "状态：" + status);
			map.put("id", orderList.get(i).getId() + "");
			list.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.orderlistitem,
				new String[] { "name", "time", "status" }, new int[] {
						R.id.txtName, R.id.txtTime, R.id.txtStatus });
		lvOrders.setAdapter(adapter);

		lvOrders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ListView listView = (ListView) arg0;
				HashMap<String, String> map = (HashMap<String, String>) listView
						.getItemAtPosition(arg2);
				Intent i = new Intent();
				i.putExtra("id", map.get("id").toString());
				i.setClass(MyOrderActivity.this, ViewOrderActivity.class);
				startActivity(i);
			}

		});
	}

	class GetOrdersTask extends AsyncTask<Object, Object, String> {
		String orders; // json格式的字符串

		@Override
		protected void onPostExecute(String result) {
			loadingPanel.setVisibility(View.GONE);
			orderList = new ArrayList<Order>();
			if (orders == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.failed_to_get_data),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				try {

					JSONArray jsonArray = new JSONArray(orders);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject order = (JSONObject) jsonArray.get(i);
						int id = order.getInt("order_id");
						String contactName = order.getString("contact_name");
						String reservedTime = order.getString("reserved_time")
								.replace("T", " ").replace("+00:00", "");
						reservedTime = DateTimeUtility.utc2Local(reservedTime,
								"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
						int status = order.getInt("order_status");

						Order o = new Order(id, contactName, reservedTime,
								status);
						orderList.add(o);
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

			fillOrderList();

			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(Object... params) {
			try {
				String path = Configuration.WS_GETORDERSBYPHONE + contactPhone;

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
				orders = StringUtility.inputstreamToString(is);
			} catch (Exception e) {

			}
			return orders;
		}

	}

	private class Order {
		private int id;
		private String contactName;
		private String reservedTime;
		private int status;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getReservedTime() {
			return reservedTime;
		}

		public void setReservedTime(String reservedTime) {
			this.reservedTime = reservedTime;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public Order(int id, String contactName, String reservedTime, int status) {
			super();
			this.id = id;
			this.contactName = contactName;
			this.reservedTime = reservedTime;
			this.status = status;
		}

	}
}