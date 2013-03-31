package com.xingyun.activity;

import com.xingyun.adapter.AsyncImageLoader;
import com.xingyun.adapter.AsyncImageLoader.ImageCallback;
import com.xingyun.entity.Dish;
import com.xingyun.persistence.CartDishModel;
import com.xingyun.persistence.CartManager;
import com.xingyun.utility.StringUtility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DishDetailActivity extends Activity {
	String name;
	String price;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dishdetail);

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		final Intent intent = this.getIntent();
		name = intent.getStringExtra("name");
		String imageUrl = StringUtility.replaceLast(
				intent.getStringExtra("imageUrl"), "100x100.", "400x400.");
		price = intent.getStringExtra("price");
		id = intent.getIntExtra("id", 0);

		TextView txtName = (TextView) this.findViewById(R.id.txtName);
		TextView txtPrice = (TextView) this.findViewById(R.id.txtPrice);
		final ImageView imgDish = (ImageView) this.findViewById(R.id.imgDish);

		txtName.setText(name);
		txtPrice.setText("价格：" + price + "元");

		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						if (imgDish != null) {
							imgDish.setImageDrawable(imageDrawable);
						}
					}
				});
		if (cachedImage == null) {
			imgDish.setImageResource(R.drawable.ic_launcher);
		} else {
			imgDish.setImageDrawable(cachedImage);
		}

		Button btnAddToCart = (Button) findViewById(R.id.btn_addtocart);
		btnAddToCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dish thisDish = new Dish();
				thisDish.setName(name);
				thisDish.setPrice(price);
				thisDish.setMenuItemId(id);
				thisDish.setImageUrl(intent.getStringExtra("imageUrl")
						.substring(
								intent.getStringExtra("imageUrl").lastIndexOf(
										"/"),
								intent.getStringExtra("imageUrl").length()));
				CartManager.addDishToCart(new CartDishModel(thisDish, 1));

				Toast toast = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.add_to_cart_succeed),
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
			}

		});
	}
}