package com.xingyun.setting;

public class Configuration {
	// public static final String WS_GETMENU =
	// "http://10.0.2.2:8000/xingyun/api/menus"; //模拟器调试ip
	public static final String WS_GETMENU = "http://192.168.0.102:8000/xingyun/api/menus";
	public static final String WS_IMAGE_URI_PREFIX = "http://192.168.0.102:8000/static//menu/";
	public static final int WS_CONNTIMEOUT = 1000;
	public static final int WS_SOCKETTIMEOUT = 1000;
	public static int LAZYLOAD_ITEM_DISPLAY_COUNT = 10;

}
