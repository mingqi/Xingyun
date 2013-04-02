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

import com.xingyun.persistence.UserManager;
import com.xingyun.setting.Configuration;
import com.xingyun.utility.StringUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewOrderActivity extends Activity {
	private String orderId;
	private ViewOrderActivity thisActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vieworder);

		thisActivity = this;

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		Intent intent = this.getIntent();
		orderId = intent.getStringExtra("id");

		GetOrderTask task = new GetOrderTask();
		task.execute();
	}

	class GetOrderTask extends AsyncTask<Object, Object, String> {
		String order; // json格式的字符串

		@Override
		protected void onPostExecute(String result) {
			if (order == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.failed_to_get_data),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				try {
					JSONObject obj = new JSONObject(order);

					Log.e("====", order);

					TextView txtContactName = (TextView) thisActivity
							.findViewById(R.id.txt_contactname);
					txtContactName.setText("顾客姓名："
							+ obj.getString("contact_name"));

					TextView txtTelephone = (TextView) thisActivity
							.findViewById(R.id.txt_telephone);
					txtTelephone.setText("联系方式："
							+ obj.getString("contact_phone"));

					TextView txtGuestNumber = (TextView) thisActivity
							.findViewById(R.id.txt_guestnumber);
					txtGuestNumber.setText("用餐人数："
							+ obj.getString("people_number") + "人");

					TextView txtIsVip = (TextView) thisActivity
							.findViewById(R.id.txt_isvip);
					if (obj.has("box_required")) {
						txtIsVip.setText("堂食/包厢："
								+ (obj.getString("box_required") == "true" ? "包厢"
										: "堂食"));
					} else {
						txtIsVip.setText("堂食/包厢：堂食");
					}

					TextView txtArrivalTime = (TextView) thisActivity
							.findViewById(R.id.txt_arrivaltime);
					txtArrivalTime.setText("到店时间："
							+ obj.getString("reserved_time").replace("T", "/")
									.replace("+00:00", ""));

					TextView txtDishCount = (TextView) thisActivity
							.findViewById(R.id.txt_dishcount);
					txtDishCount.setText("共点菜" + obj.getString("dishes_count")
							+ "道");

					TextView txtTotalPrice = (TextView) thisActivity
							.findViewById(R.id.txt_totalprice);
					txtTotalPrice.setText("一共" + obj.getString("order_price")
							+ "元");

					if (obj.has("other_requirements")) {
						com.xingyun.excontrol.RoundedRectEditTextLayout txtRequirement = (com.xingyun.excontrol.RoundedRectEditTextLayout) thisActivity
								.findViewById(R.id.txt_requirements);
						txtRequirement.setText(obj
								.getString("other_requirements"));
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
				String path = Configuration.WS_GETORDER + orderId;

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
				order = StringUtility.inputstreamToString(is);
			} catch (Exception e) {

			}
			return order;
		}

	}
}