package com.xingyun.adapter;

import java.util.List;

import com.xingyun.activity.R;
import com.xingyun.adapter.AsyncImageLoader.ImageCallback;
import com.xingyun.entity.Dish;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DishListAdapter extends ArrayAdapter<Dish> {

	private ListView listView;
	private AsyncImageLoader asyncImageLoader;

	public DishListAdapter(Activity activity, List<Dish> imageAndTexts,
			ListView listView) {
		super(activity, 0, imageAndTexts);
		this.listView = listView;
		asyncImageLoader = new AsyncImageLoader();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();

		// Inflate the views from XML
		View rowView = convertView;
		DishListItemCache viewCache;
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.dishlistitem, null);
			viewCache = new DishListItemCache(rowView);
			rowView.setTag(viewCache);
		} else {
			viewCache = (DishListItemCache) rowView.getTag();
		}
		Dish dish = getItem(position);

		// Load the image and set it on the ImageView
		String imageUrl = dish.getImageUrl();
		ImageView imageView = viewCache.getDishImage();
		imageView.setTag(imageUrl);
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						ImageView imageViewByTag = (ImageView) listView
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
						}
					}
				});
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			imageView.setImageDrawable(cachedImage);
		}
		// Set the text on the TextView
		TextView dishName = viewCache.getDishName();
		dishName.setText(dish.getName());

		TextView dishPrice = viewCache.getDishPrice();
		dishPrice.setText(dish.getPrice() + "");

		return rowView;
	}

}