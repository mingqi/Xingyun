package com.xingyun.setting;

public class Configuration {
	// public static final String WS_GETMENU =
	// "http://10.0.2.2:8000/xingyun/api/menus"; //模拟器调试ip
	public static final String WS_GETMENU = "http://192.168.1.102:8000/xingyun/api/menus";
	public static final String WS_GETEVENTS = "http://192.168.1.102:8000/xingyun/api/activities";
	public static final String WS_IMAGE_URI_PREFIX = "http://192.168.1.102:8000/static//menu/";
	public static final String WS_LOGIN = "http://192.168.1.102:8000/xingyun/api/customer/signin?name=__1__&password=__2__";
	public static final String WS_PLACEORDER = "http://192.168.1.102:8000/xingyun/api/orders";
	public static final String WS_SIGNUP = "http://192.168.1.102:8000/xingyun/api/customer/signup";
	public static final int WS_CONNTIMEOUT = 3000;
	public static final int WS_SOCKETTIMEOUT = 3000;
	// public static int LAZYLOAD_ITEM_DISPLAY_COUNT = 10;

}
