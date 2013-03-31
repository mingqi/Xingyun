package com.xingyun.activity;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.xingyun.persistence.CartManager;
import com.xingyun.setting.Configuration;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class OrderInfoActivity extends Activity {

	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderinfo);

		activity = this;
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

				String name = ((EditText) activity.findViewById(R.id.txt_name))
						.getText().toString();
				String telephone = ((EditText) activity
						.findViewById(R.id.txt_telephone)).getText().toString();
				String guestNumber = ((EditText) activity
						.findViewById(R.id.txt_guestnumber)).getText()
						.toString();
				String isVip = ((CheckBox) activity.findViewById(R.id.cb_isvip))
						.isChecked() ? "true" : "false";
				String arrivalDateTime = ((EditText) activity
						.findViewById(R.id.txt_arrivaldate)).getText()
						.toString()
						+ ((EditText) activity
								.findViewById(R.id.txt_arrivaltime)).getText()
								.toString();
				String requirements = ((EditText) activity
						.findViewById(R.id.txt_requirements)).getText()
						.toString();

				// post data
				//HttpPost httpRequest = new HttpPost(Configuration.WS_PLACEORDER);
				HttpGet httpGet = new HttpGet(Configuration.WS_PLACEORDER);
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("customer_id", "1"));
//				params.add(new BasicNameValuePair("contact_name", name));
//				params.add(new BasicNameValuePair("contact_phone", telephone));
//				params.add(new BasicNameValuePair("people_number", guestNumber));
//				params.add(new BasicNameValuePair("box_required", isVip));
//				params.add(new BasicNameValuePair("order_price", CartManager.getTotalPrice()+""));
//				params.add(new BasicNameValuePair("dishes_count", CartManager.getOrderedDishes().size()+""));
//				params.add(new BasicNameValuePair("reserved_time", "2013-04-01")); //todo
//				params.add(new BasicNameValuePair("other_requirements", requirements));
//				params.add(new BasicNameValuePair("order_dishes", CartManager.getJsonStr()));

				HttpParams params = new BasicHttpParams();
				params.setParameter("customer_id", "1");
				params.setParameter("contact_name", name);
				params.setParameter("contact_phone", telephone);
				params.setParameter("people_number", guestNumber);
				params.setParameter("box_required", isVip);
				params.setParameter("order_price", CartManager.getTotalPrice()+"");
				params.setParameter("dishes_count", CartManager.getOrderedDishes().size()+"");
				params.setParameter("reserved_time", "2013-04-01");
				params.setParameter("other_requirements", requirements);
				params.setParameter("order_dishes", CartManager.getJsonStr());
				
				try { 
					httpGet.setParams(params);
//					httpGet.setEntity(new UrlEncodedFormEntity(params,
//							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpGet);
					// 201!
					Log.e("!!", httpResponse.getStatusLine().toString());
					if (httpResponse.getStatusLine().getStatusCode() == 201) {
						String strResult = EntityUtils.toString(httpResponse
								.getEntity());
					} else {
						String strResult = EntityUtils.toString(httpResponse
								.getEntity());
						Log.e("=======", strResult);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		TextView txtDishCount = (TextView) this
				.findViewById(R.id.txt_dishcount);
		txtDishCount.setText("共点菜" + CartManager.getOrderedDishes().size()
				+ "道");
		TextView txtTotalPrice = (TextView) this
				.findViewById(R.id.txt_totalprice);
		txtTotalPrice.setText("一共" + CartManager.getTotalPrice() + "元");

		final EditText txtArrivalDate = (EditText) this
				.findViewById(R.id.txt_arrivaldate);
		txtArrivalDate.setOnClickListener(new OnClickListener() {
			Calendar calendar = Calendar.getInstance();

			@Override
			public void onClick(View arg0) {
				DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						txtArrivalDate.setText(year + "-" + (monthOfYear + 1)
								+ "-" + dayOfMonth);
					}
				};
				Dialog dialog = new DatePickerDialog(activity, dateListener,
						calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));

				dialog.show();
			}

		});

		final EditText txtArrivalTime = (EditText) this
				.findViewById(R.id.txt_arrivaltime);
		txtArrivalTime.setOnClickListener(new OnClickListener() {
			Calendar calendar = Calendar.getInstance();

			@Override
			public void onClick(View arg0) {
				OnTimeSetListener timeListener = new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						txtArrivalTime.setText(hourOfDay + ":" + minute);
					}

				};
				TimePickerDialog dialog = new TimePickerDialog(activity,
						timeListener, calendar.get(Calendar.HOUR_OF_DAY),
						calendar.get(Calendar.MINUTE), true);
				dialog.show();
			}

		});
	}
}