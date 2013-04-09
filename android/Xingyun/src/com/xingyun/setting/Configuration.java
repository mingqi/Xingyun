package com.xingyun.setting;

public class Configuration {
	public static final String WS_IP = "http://192.168.0.101:8000";
	
	public static final String WS_GETMENU = WS_IP + "/xingyun/api/menus";
	public static final String WS_GETEVENTS = WS_IP + "/xingyun/api/activities";
	public static final String WS_IMAGE_URI_PREFIX = WS_IP + "/static/menu/"; // static//menu
	public static final String WS_LOGIN = WS_IP
			+ "/xingyun/api/customer/signin?name=__1__&password=__2__";
	public static final String WS_PLACEORDER = WS_IP + "/xingyun/api/orders";
	public static final String WS_SIGNUP = WS_IP
			+ "/xingyun/api/customer/signup";
	public static final String WS_GETORDERS = WS_IP
			+ "/xingyun/api/orders?customerId=";
	public static final String WS_GETORDER = WS_IP + "/xingyun/api/order/";
	public static final int WS_CONNTIMEOUT = 3000;
	public static final int WS_SOCKETTIMEOUT = 3000;
	// public static int LAZYLOAD_ITEM_DISPLAY_COUNT = 10;

}
