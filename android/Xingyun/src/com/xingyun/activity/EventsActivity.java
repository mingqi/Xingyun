package com.xingyun.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.viewpagerindicator.CirclePageIndicator;
import com.xingyun.activity.OrderDishesActivity.GetDishesTask;
import com.xingyun.entity.Dish;
import com.xingyun.entity.DishType;
import com.xingyun.entity.Event;
import com.xingyun.setting.Configuration;
import com.xingyun.usercontrol.EventSlider;
import com.xingyun.utility.StringUtility;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class EventsActivity extends FragmentActivity {

	private ViewPager eventPager;

	private EventPagerAdapter eventPagerAdapter;

	private List<View> eventViews;
	GetEventsTask eTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);

		// 把活动列表搞下来
		eTask = new GetEventsTask();
		eTask.execute();

	}

	private void fillEventData() {
		eventViews = new ArrayList<View>();
		for (int i = 0; i < eventList.size(); i++) {
			EventSlider es = new EventSlider(this, "", "", Configuration.WS_IMAGE_URI_PREFIX+ eventList.get(i)
					.getImageUrl());
			eventViews.add(es);
		}

		eventPagerAdapter = new EventPagerAdapter();

		eventPager = (ViewPager) findViewById(R.id.eventPager);
		eventPager.setAdapter(eventPagerAdapter);

		CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.circlePageIndicator);
		titleIndicator.setViewPager(eventPager);
	}

	private class EventPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return eventViews.size();

		}

		@Override
		public Object instantiateItem(View collection, int position) {
			((ViewPager) collection).addView(eventViews.get(position), 0);
			return eventViews.get(position);
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView(eventViews.get(position));
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}

	List<Event> eventList;

	class GetEventsTask extends AsyncTask<Object, Object, String> {
		String events; // json格式的字符串

		@Override
		protected void onPostExecute(String result) {
			if (events == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.failed_to_get_data),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				try {
					eventList = new ArrayList<Event>();
					JSONArray jsonArray = new JSONArray(events);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject event = (JSONObject) jsonArray.get(i);
						int id = event.getInt("activity_id");
						String imageUri = event.getString("image_uri");
						int sortedSeq = event.getInt("sorted_seq");
						Event eventObj = new Event(imageUri, id, sortedSeq);
						eventList.add(eventObj);
					}

					Log.e("event length", "" + eventList.size());
					fillEventData();
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
				String path = Configuration.WS_GETEVENTS;

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
				events = StringUtility.inputstreamToString(is);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return events;
		}

	}
}
