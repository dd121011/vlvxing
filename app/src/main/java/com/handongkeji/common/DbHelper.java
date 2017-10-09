/**
] *  ClassName: Constants.java
 *  created on 2012-10-19
 *  Copyrights 2012-10-19 hjgang All rights reserved.
 *  site: http://www.hjgang.tk
 *  email: hjgang@yahoo.cn
 */
package com.handongkeji.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


import com.vlvxing.app.common.Constants;

import java.util.List;


/**
 * Android系统中操作SQLite数据库的帮助�?
 * 主要用于数据库版本的管理，并提供SQLiteDatabase实例
 */
public class DbHelper extends SQLiteOpenHelper {
	private static String db_name;
	
	static{
		db_name = Constants.DB_NAME;
	}
	
	public DbHelper(Context context){
		super(context, db_name, null, Constants.DB_VERSION);
	}

	public DbHelper(Context context, String name, CursorFactory factory,
					int version) {
		super(context, name, factory, version);
	}

	//在第�?��安装本应用程序时，会回调的方法，主要用于执行数据库表的创建和数据初始�?
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE sysMsg(msgId integer PRIMARY KEY autoincrement, msgContent varchar(500),msgTime varchar(10),msgStatus integer DEFAULT 0)");
		
		//注意这个SqliteDatabase实例不需要关�?
	}
	
	// sqlCreate用于接收创建数据库sql语句，sqlUpdate用于接收更新数据库sql语句
	String sqlCreate = "", sqlUpdate = "";

	// 判断是创建还是更新数据库sql命令语句
	public void SqlStr(String sql) {
		if (sql.contains("CREATE")) {
			sqlCreate = sql;
		} else {
			sqlUpdate = sql;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
	}

	// 添加
	public void Insert(String sql) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
	}

	public Cursor Query(String sql) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	// 搜索sql搜索sql语句，id搜索数据的id
	public Cursor Query(String sql, Integer id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, new String[] { id.toString() });
		return cursor;
	}

	// 搜索根据一个条件与sql查询
	public Cursor Query(String sql, String WhereStr) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, new String[] { WhereStr });
		return cursor;
	}

	// 编辑
	public void Update(String sql) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
	}

	// 删除
	public void Delete(String sql) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
	}

	// 得到返回数据的数量
	public int GetCount(String sql) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		int result = cursor.getInt(0);
		cursor.close();
		return result;
	}
	
	public float GetFCount(String sql) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		float result = cursor.getFloat(0);
		cursor.close();
		return result;
	}


	// 分页,分页的sql语句，offset第几行开始分页（第一行默认为0），maxResult最大跳多少行
	public Cursor GetScrollData(String sql, int offset, int maxResult) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(offset),
				String.valueOf(maxResult) });
		return cursor;
	}

	/**
	 * 执行批量Sql,增删改 启动数据库事务
	 * 
	 * @param
	 *            <String> lstr
	 * @return 返回执行是否成功
	 */
	public boolean execSQLList(List<String> lstr) {
		boolean flag = false;
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();// 开启事务
		try {
			for (String sql : lstr) {
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();// 提交
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			if (db.inTransaction()) {
				db.endTransaction();// 结束事务
			}
		}
		return flag;
	}
}
