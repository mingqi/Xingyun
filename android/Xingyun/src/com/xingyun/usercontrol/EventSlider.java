package com.xingyun.usercontrol;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EventSlider extends FrameLayout {

	// private String title;
	// private String description;
	// private int drawableId;

	private static RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.FILL_PARENT,
			RelativeLayout.LayoutParams.FILL_PARENT);

	public EventSlider(Context context, String title, String description,
			int drawableId) {
		super(context);
		// this.title = title;
		// this.description = description;
		// this.drawableId = drawableId;
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(drawableId);
		imageView.setLayoutParams(params);

		this.addView(imageView, params);
	}
}