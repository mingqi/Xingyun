package com.xingyun.persistence;

import com.xingyun.entity.Dish;

public class CartDishModel {
	private Dish dish;
	private int count = 1; // default set to 1

	public CartDishModel(Dish dish, int count) {
		super();
		this.dish = dish;
		this.count = count;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public boolean equals(Object obj) {
		CartDishModel that = (CartDishModel) obj;
		return this.dish.getMenuItemId() == that.getDish().getMenuItemId();
	}

}
