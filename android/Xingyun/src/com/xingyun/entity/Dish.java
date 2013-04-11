package com.xingyun.entity;

import com.xingyun.setting.Configuration;

public class Dish {
	// dish.getInt("category");
	// dish.getString("title");
	// dish.getString("price");
	// dish.getInt("menu_item_id");
	// dish.getString("image_uri");
	// dish.getInt("sorted_seq");

	private int category;
	private int menuItemId;
	private int sortedSeq;
	private String name;
	private String price;
	private String imageUrl;
	
	private int quantity;

	public Dish(String name, String price, String imageUrl) {
		super();
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public Dish(int category, int menuItemId, int sortedSeq, String name,
			String price, String imageUrl) {
		super();
		this.category = category;
		this.menuItemId = menuItemId;
		this.sortedSeq = sortedSeq;
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public Dish() {
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	public int getSortedSeq() {
		return sortedSeq;
	}

	public void setSortedSeq(int sortedSeq) {
		this.sortedSeq = sortedSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImageUrl() {
		return Configuration.WS_IMAGE_URI_PREFIX + imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
