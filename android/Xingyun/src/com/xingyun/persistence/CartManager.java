package com.xingyun.persistence;

import java.util.ArrayList;
import java.util.List;

import com.xingyun.entity.Dish;

import android.util.Log;

public class CartManager {
	private static List<CartDishModel> orderedDishes;

	public static List<CartDishModel> getOrderedDishes() {
		if (orderedDishes == null) {
			orderedDishes = new ArrayList<CartDishModel>();
		}
		return orderedDishes;
	}

	public static void addDishToCart(CartDishModel model) {
		if (getOrderedDishes().contains(model)) {
			CartDishModel thisModel = getModel(model);
			thisModel.setCount(thisModel.getCount() + model.getCount());
		} else {
			getOrderedDishes().add(model);
		}

		// ////print
		for (int i = 0; i < orderedDishes.size(); i++) {
			Log.e("=========", orderedDishes.get(i).getDish().getName()
					+ orderedDishes.get(i).getCount());
		}
	}

	public static void removeDishFromCart(CartDishModel model) {
		if (getOrderedDishes().contains(model)) {
			CartDishModel thisModel = getModel(model);
			thisModel.setCount(thisModel.getCount() - model.getCount());

			if (thisModel.getCount() == 0) {
				getOrderedDishes().remove(thisModel);
			}
		}

		// ////print
		for (int i = 0; i < orderedDishes.size(); i++) {
			Log.e("=========", orderedDishes.get(i).getDish().getName()
					+ orderedDishes.get(i).getCount());
		}
	}

	private static CartDishModel getModel(CartDishModel that) {
		for (int i = 0; i < orderedDishes.size(); i++) {
			if (orderedDishes.get(i).getDish().getMenuItemId() == that
					.getDish().getMenuItemId()) {
				return orderedDishes.get(i);
			}
		}
		return null;
	}

	public static ArrayList<Dish> getPureDishes() {
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		if (orderedDishes != null) {
			for (int i = 0; i < orderedDishes.size(); i++) {
				dishes.add(orderedDishes.get(i).getDish());
			}
		}
		return dishes;
	}

	public static int getCountByDishId(int dishId) {
		if (orderedDishes == null) {
			return 0;
		}

		for (int i = 0; i < orderedDishes.size(); i++) {
			if (orderedDishes.get(i).getDish().getMenuItemId() == dishId) {
				return orderedDishes.get(i).getCount();
			}
		}

		return 0;
	}

	public static int getTotalPrice() {
		int total = 0;
		if (orderedDishes == null) {
			return total;
		}
		for (int i = 0; i < orderedDishes.size(); i++) {
			total = orderedDishes.get(i).getCount()
					* Integer.parseInt(orderedDishes.get(i).getDish()
							.getPrice()) + total;
		}
		return total;
	}

	public static String getJsonStr() {
		/*
		 * [ { "menu_item_id": 1, "quantity" : 1 }, { "menu_item_id": 5,
		 * "quantity" : 3 } ]
		 */
		String str = "[";
		for (int i = 0; i < getOrderedDishes().size(); i++) {
			str = str + "{\"menu_item_id\":"
					+ getOrderedDishes().get(i).getDish().getMenuItemId()
					+ ",\"quantity\":" + getOrderedDishes().get(i).getCount()
					+ "},";
		}
		str = str.substring(0, str.length() - 1);
		str += "]";
		Log.e("json str", str);
		return str;
	}
	
	public static void clearCart() {
		orderedDishes = new ArrayList<CartDishModel>();
	}
}
