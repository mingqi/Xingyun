package com.xingyun.persistence;

import android.content.Context;

import com.xingyun.utility.DBManager;

public class UserManager {

	public static String getContactName(Context context) {

		DBManager manager = new DBManager(context);
		String contactName = manager.getContactName();
		manager.closeDB();
		return contactName;
	}

	public static void setContactName(Context context, String contactName) {
		DBManager manager = new DBManager(context);
		manager.setContactName(contactName);
		manager.closeDB();
	}

	public static String getContactPhone(Context context) {
		DBManager manager = new DBManager(context);
		String contactPhone = manager.getUserTelephone();
		manager.closeDB();
		return contactPhone;
	}

	public static void setContactPhone(Context context, String contactPhone) {
		DBManager manager = new DBManager(context);
		manager.setUserTelephone(contactPhone);
		manager.closeDB();
	}

	public static int getId(Context context) {
		DBManager manager = new DBManager(context);
		int id = manager.getUserId();
		manager.closeDB();
		return id;
	}

	public static void setId(Context context, int id) {
		DBManager manager = new DBManager(context);
		manager.setUserId(id);
		manager.closeDB();
	}

}
