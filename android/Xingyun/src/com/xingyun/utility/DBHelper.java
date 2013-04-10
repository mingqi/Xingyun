package com.xingyun.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "xy.db";
	private static final int DATABASE_VERSION = 2;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS user"
				+ "(id INTEGER, telephone VARCHAR, name VARCHAR)");

		db.execSQL("INSERT INTO user VALUES(?, ?, ?)", new Object[] { 0, "13800138000", "张三" });
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE user");
		onCreate(db);
	}

}