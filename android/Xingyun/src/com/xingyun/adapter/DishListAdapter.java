package com.xingyun.adapter;

import java.util.List;

import com.xingyun.activity.ConfirmOrderActivity;
import com.xingyun.activity.R;
import com.xingyun.adapter.AsyncImageLoader.ImageCallback;
import com.xingyun.entity.Dish;
import com.xingyun.persistence.CartDishModel;
import com.xingyun.persistence.CartManager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DishListAdapter extends ArrayAdapter<Dish> {

	private ListView listView;
	private AsyncImageLoader asyncImageLoader;
	private int listViewType;

	private ConfirmOrderActivity confirmOrderActivity;

	public DishListAdapter(Activity activity, List<Dish> imageAndTexts,
			ListView listView, int listViewType) {
		super(activity, 0, imageAndTexts);

		if (activity instanceof ConfirmOrderActivity) {
			this.confirmOrderActivity = (ConfirmOrderActivity) activity;
		}

		this.listView = listView;
		this.listViewType = listViewType; // 待重构
		asyncImageLoader = new AsyncImageLoader();
	}

	View rowView;

	public View getView(int position, View convertView, ViewGroup parent) {
		final Activity activity = (Activity) getContext();

		// Inflate the views from XML
		rowView = convertView;
		DishListItemCache viewCache;
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();

			if (listViewType == 0) {
				rowView = inflater.inflate(R.layout.dishlistitem, null);
			} else if (listViewType == 1) {
				rowView = inflater.inflate(R.layout.dishlistitem1, null);
			}
			viewCache = new DishListItemCache(rowView);
			rowView.setTag(viewCache);
		} else {
			viewCache = (DishListItemCache) rowView.getTag();
		}
		final Dish dish = getItem(position);

		if (listViewType == 0) {
			// “点菜”按钮
			Button btnAddToCart = (Button) rowView
					.findViewById(R.id.btn_addtocart);
			btnAddToCart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					CartManager.addDishToCart(new CartDishModel(dish, 1));

					Toast toast = Toast.makeText(activity
							.getApplicationContext(), activity.getResources()
							.getString(R.string.add_to_cart_succeed),
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.BOTTOM, 0, 0);
					toast.show();
				}

			});
		}

		if (listViewType == 1) {
			Button btnAdd = (Button) rowView.findViewById(R.id.btn_add);
			Button btnRemove = (Button) rowView.findViewById(R.id.btn_remove);
			final TextView txtCount = (TextView) rowView
					.findViewById(R.id.txt_count);

			int count = CartManager.getCountByDishId(dish.getMenuItemId());
			txtCount.setText("" + count);

			btnAdd.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					txtCount.setText(""
							+ (Integer.parseInt(txtCount.getText().toString()) + 1));
					CartManager.addDishToCart(new CartDishModel(dish, 1));

					confirmOrderActivity.reloadPrice();
				}

			});

			btnRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					txtCount.setText(""
							+ (Integer.parseInt(txtCount.getText().toString()) - 1));
					CartManager.removeDishFromCart(new CartDishModel(dish, 1));

					if (CartManager.getCountByDishId(dish.getMenuItemId()) == 0) {
						// reload listview
						confirmOrderActivity.removeRow(dish);
					}

					confirmOrderActivity.reloadPrice();
				}

			});
		}

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

		TextView dishName = viewCache.getDishName();
		dishName.setText(dish.getName());

		TextView dishPrice = viewCache.getDishPrice();
		dishPrice.setText("价格: " + dish.getPrice() + "元");

		return rowView;
	}

}