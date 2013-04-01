package com.xingyun.usercontrol;

import com.xingyun.activity.R;
import com.xingyun.adapter.AsyncImageLoader;
import com.xingyun.adapter.AsyncImageLoader.ImageCallback;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EventSlider extends FrameLayout {

	// private String title;
	// private String description;
	// private int drawableId;

	private static RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.MATCH_PARENT,
			RelativeLayout.LayoutParams.MATCH_PARENT);

	public EventSlider(Context context, String title, String description,
			String imageUri) {
		super(context);
		// this.title = title;
		// this.description = description;
		// this.drawableId = drawableId;
		final ImageView imageView = new ImageView(context);

		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUri,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						if (imageView != null) {
							imageView.setImageDrawable(imageDrawable);
						}
					}
				});
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			imageView.setImageDrawable(cachedImage);
		}

		params.setMargins(0, 0, 0, 0);
		imageView.setLayoutParams(params);

		this.addView(imageView, params);
	}
}
