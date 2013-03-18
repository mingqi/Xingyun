package com.xingyun.entity;

public class Event {
	private String imageUrl;
	private String title;
	private String description;
	public Event(String imageUrl, String title, String description) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
		this.description = description;
	}
	public Event() {
		// TODO Auto-generated constructor stub
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	 
}
