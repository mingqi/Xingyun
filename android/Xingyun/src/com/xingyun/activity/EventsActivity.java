package com.xingyun.activity;

import java.util.ArrayList;
import java.util.List;

import com.viewpagerindicator.CirclePageIndicator;
import com.xingyun.usercontrol.EventSlider;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EventsActivity extends FragmentActivity {

	private ViewPager eventPager;

	private EventPagerAdapter eventPagerAdapter;

	private List<View> eventViews;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);

		// 5张示例图
		eventViews = new ArrayList<View>();
		int[] drawableIds = { R.drawable.s1, R.drawable.s2, R.drawable.s3,
				R.drawable.s4, R.drawable.s5 };
		for (int i = 0; i < drawableIds.length; i++) {
			EventSlider es = new EventSlider(this, "", "", drawableIds[i]);
			es.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("==========", "added in events activity");
				}
				
			});
			eventViews.add(es);
		}
		TextView tv = new TextView(this);
		tv.setText("dddddddddddddddddddddd");
		TextView tv2 = new TextView(this);
		tv2.setText("aaaaaaaaaaaaaaaaaaaaaaaa");
		eventViews.add(tv);
		eventViews.add(tv2);

		eventPagerAdapter = new EventPagerAdapter();

		eventPager = (ViewPager) findViewById(R.id.eventPager);
		eventPager.setAdapter(eventPagerAdapter);

		// Bitmap imbm = BitmapFactory.decodeFile(parent.listFiles()[count]
		// .getAbsolutePath());

		// Bind the title indicator to the adapter
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

}
