package com.xingyun.utility;

import java.util.ArrayList;

import com.xingyun.entity.Dish;
import com.xingyun.entity.DishType;
import com.xingyun.entity.Event;

public class WSUtility {
	public static Event getEventDetailInfo(int index) {
		Event evt = new Event();

		evt.setImageUrl("");
		evt.setTitle(index + "小份菜 大味道");
		evt.setDescription(index
				+ "小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n小份菜 大味道 小份菜 大味道 小份菜 大味道 小份菜 大味道 \n\n小份菜 大味道 ");

		return evt;
	}

	public static ArrayList<Dish> getDishes(DishType type) {
		ArrayList<Dish> dishes = new ArrayList<Dish>();

		for (int i = 0; i < 10; i++) {
			Dish dish = new Dish("宫保鸡丁" + i, "价格: "+i+"元", "http://scby.cn/uploads/allimg/110116/1-110116154004.jpg");
			dishes.add(dish);
		}

		return dishes;
	}
}
