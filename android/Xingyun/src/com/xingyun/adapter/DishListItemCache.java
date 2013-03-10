package com.xingyun.adapter;

import com.xingyun.activity.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DishListItemCache {
	private View baseView;
	private TextView dishName;
	private TextView dishPrice;
	private ImageView dishImage;

	public DishListItemCache(View baseView) {
		this.baseView = baseView;
	}

	public TextView getDishName() {
		if (dishName == null) {
			dishName = (TextView) baseView.findViewById(R.id.dishName);
		}
		return dishName;
	}

	public TextView getDishPrice() {
		if (dishPrice == null) {
			dishPrice = (TextView) baseView.findViewById(R.id.dishPrice);
		}
		return dishPrice;
	}

	public ImageView getDishImage() {
		if (dishImage == null) {
			dishImage = (ImageView) baseView.findViewById(R.id.dishImage);
		}
		return dishImage;
	}

	// public TextView getTextView() {
	// if (textView == null) {
	// textView = (TextView) baseView.findViewById(R.id.text);
	// }
	// return textView;
	// }
	//
	// public ImageView getImageView() {
	// if (imageView == null) {
	// imageView = (ImageView) baseView.findViewById(R.id.image);
	// }
	// return imageView;
	// }
}
