package com.xingyun.persistence;

public class UserManager {
	private static boolean isLogin;

	private static String name;
	private static String contactName;
	private static String contactPhone;
	private static int id;

	public static boolean isLogin() {
		return isLogin;
	}

	public static void setLogin(boolean isLogin) {
		UserManager.isLogin = isLogin;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		UserManager.name = name;
	}

	public static String getContactName() {
		return contactName;
	}

	public static void setContactName(String contactName) {
		UserManager.contactName = contactName;
	}

	public static String getContactPhone() {
		return contactPhone;
	}

	public static void setContactPhone(String contactPhone) {
		UserManager.contactPhone = contactPhone;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		UserManager.id = id;
	}

}
