package com.xingyun.entity;

public class Event {
	/*
	 * [{"image_uri": "activity_1.jpg", "activity_id": 1, "sorted_seq": 1},
	 * {"image_uri": "activity_2.jpg", "activity_id": 2, "sorted_seq": 2},
	 * {"image_uri": "activity_3.jpg", "activity_id": 3, "sorted_seq": 3}]
	 */
	private String imageUrl;
	private String title;
	private String description;
	private int activityId;
	private int sortedSeq;

	public Event(String imageUrl, int activityId, int sortedSeq) {
		super();
		this.imageUrl = imageUrl;
		this.activityId = activityId;
		this.sortedSeq = sortedSeq;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getSortedSeq() {
		return sortedSeq;
	}

	public void setSortedSeq(int sortedSeq) {
		this.sortedSeq = sortedSeq;
	}

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
