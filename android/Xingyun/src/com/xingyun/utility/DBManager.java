package com.xingyun.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	public int getUserId() {
		int id = 0;
		Cursor c = db.rawQuery("SELECT * FROM user", null);
		try {
			c.moveToNext();
			id = c.getInt(c.getColumnIndex("id"));
		} catch (Exception ex) {
		} finally {
			c.close();
		}
		return id;
	}

	public String getUserTelephone() {
		String telephone = "";
		Cursor c = db.rawQuery("SELECT * FROM user", null);

		try {
			c.moveToNext();
			telephone = c.getString(c.getColumnIndex("telephone"));
		} catch (Exception ex) {
		} finally {
			c.close();
		}
		return telephone;
	}

	public String getContactName() {
		String name = "";
		Cursor c = db.rawQuery("SELECT * FROM user", null);

		try {
			c.moveToNext();
			name = c.getString(c.getColumnIndex("name"));
		} catch (Exception ex) {
		} finally {
			c.close();
		}
		return name;
	}
	
	public void setUserId(int userId) {
		ContentValues cv = new ContentValues();
		cv.put("id", userId);
		db.update("user", cv, null, null);
	}

	public void setUserTelephone(String userTelephone) {
		ContentValues cv = new ContentValues();
		cv.put("telephone", userTelephone);
		db.update("user", cv, null, null);
	}

	public void setContactName(String contactName) {
		ContentValues cv = new ContentValues();
		cv.put("name", contactName);
		db.update("user", cv, null, null);
	}
	
	public void closeDB() {
		db.close();
	}
}
