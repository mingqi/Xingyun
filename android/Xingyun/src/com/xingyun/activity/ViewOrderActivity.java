package com.xingyun.activity;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.xingyun.entity.Dish;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ViewOrderActivity extends Activity {
	private String orderId;
	private ViewOrderActivity thisActivity;

	private RelativeLayout loadingPanel;

	private TableRow trViewDishes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vieworder);

		thisActivity = this;

		loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
		trViewDishes = (TableRow) findViewById(R.id.tr_viewdishes);

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
			loadingPanel.setVisibility(View.GONE);
			if (order == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.failed_to_get_data),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				try {
					final JSONObject obj = new JSONObject(order);

					Log.d(ViewOrderActivity.class.getName(), order);

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

					String reservedTime = obj.getString("reserved_time")
							.replace("T", " ").replace("+00:00", "");
					reservedTime = DateTimeUtility.utc2Local(reservedTime,
							"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");

					txtArrivalTime.setText("到店时间：" + reservedTime);

					TextView txtDishCount = (TextView) thisActivity
							.findViewById(R.id.txt_dishcount);
					try {
						txtDishCount.setText("共点菜"
								+ obj.getString("dishes_count") + "道");
					} catch (Exception ex) {
						txtDishCount.setText("共点菜0道");

					}
					TextView txtTotalPrice = (TextView) thisActivity
							.findViewById(R.id.txt_totalprice);
					try {
						txtTotalPrice.setText("一共"
								+ obj.getString("order_price") + "元");
					} catch (Exception ex) {
						txtTotalPrice.setText("一共0元");

					}
					if (obj.has("other_requirements")) {
						com.xingyun.excontrol.RoundedRectEditTextLayout txtRequirement = (com.xingyun.excontrol.RoundedRectEditTextLayout) thisActivity
								.findViewById(R.id.txt_requirements);
						txtRequirement.setText(obj
								.getString("other_requirements"));
					}

					trViewDishes.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							try {
								String dishes = obj.getString("dishes");
								Intent i = new Intent();
								i.setClass(ViewOrderActivity.this,
										ViewOrderDishesActivity.class);
								i.putExtra("dishes", dishes);
								startActivity(i);
							} catch (JSONException e) {
								Log.e(ViewOrderActivity.class.getName(),
										e.getMessage());
							}
						}

					});
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