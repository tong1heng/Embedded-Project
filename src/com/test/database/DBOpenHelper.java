package com.test.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBOpenHelper extends SQLiteOpenHelper {

	public static final String CREATE_USER="create table user(" +
            "id integer primary key autoincrement," +
            "name text," +
            "password text," +
            "phone text)";
	
	
	public static final String CREATE_OPP="create table opp("+
			"id integer primary key autoincrement,"+
			"type text,"+
			"time text)";
	
	private Context mContext;
	
	public DBOpenHelper(Context context) {
		super(context, "EmbeddedProject.db", null, 1);
		mContext=context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_OPP);
		Toast.makeText(mContext, "数据库建立！", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
