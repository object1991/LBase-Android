package com.leo.base.db;

import com.leo.base.exception.LException;
import com.leo.base.util.L;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * 本地数据库管理类
 * 
 * @author Chen Lei
 * 
 */
public final class LDBHelper extends SQLiteOpenHelper {

	private SQLiteDatabase myDataBase;

	private String[] mTableCreateText;

	private String[] mTableDropText;

	private static LDBHelper mDataProvider;

	private LDBHelper(Context context, String databaseName, int databaseVersion) {
		super(context, databaseName, null, databaseVersion);
	}

	/**
	 * 创建一个DataProvider对象并返回
	 * 
	 * @param context
	 *            ：上下文对象
	 * @param databaseName
	 *            ：数据库名称
	 * @param databaseVersion
	 *            ：数据库版本
	 * @return
	 */
	public static synchronized LDBHelper Instance(Context context,
			String databaseName, int databaseVersion) {
		if (mDataProvider == null) {
			mDataProvider = new LDBHelper(context, databaseName,
					databaseVersion);
		}
		return mDataProvider;
	}

	/**
	 * 写入创建表语句
	 * 
	 * @param str
	 */
	public void setTableCreateText(String[] tables) {
		this.mTableCreateText = tables;
	}

	/**
	 * 写入删除表语句
	 * 
	 * @param str
	 */
	public void setTableDropText(String[] tables) {
		this.mTableDropText = tables;
	}

	/**
	 * 打开数据库
	 * 
	 * @throws SQLException
	 */
	public void openDataBase() throws SQLException {
		this.myDataBase = this.getWritableDatabase();
	}

	/**
	 * 关闭数据库
	 * 
	 * @throws SQLException
	 */
	public void closeDataBase() throws SQLException {
		if (this.myDataBase != null && this.myDataBase.isOpen())
			myDataBase.close();
	}

	/**
	 * 传入SQL语句 返回查询结果
	 */
	public android.database.sqlite.SQLiteCursor Query(String SQL) {
		if (myDataBase == null || !myDataBase.isOpen()) {
			openDataBase();
		}
		SQLiteCursor cursor = (android.database.sqlite.SQLiteCursor) myDataBase
				.rawQuery(SQL, null);
		return cursor;
	}

	/**
	 * 执行SQL语句
	 */
	public void Execute(String SQL) {
		if (myDataBase == null || !myDataBase.isOpen()) {
			openDataBase();
		}
		myDataBase.compileStatement(SQL);
		myDataBase.execSQL(SQL);
	}

	/**
	 * 更新
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int Update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		if (myDataBase == null || !myDataBase.isOpen()) {
			openDataBase();
		}
		int num = myDataBase.update(table, values, whereClause, whereArgs);
		return num;
	}

	/**
	 * 插入功能，如果使用此方法，建议在SDK2.2版本(8)或以上
	 */
	public long Insert(String table, String nullColumnHack, ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		long num = db.insert(table, nullColumnHack, values);
		return num;
	}

	/**
	 * 执行SQL
	 */
	public void Execute(String SQL, Object[] bindArgs) {
		if (myDataBase == null || !myDataBase.isOpen()) {
			openDataBase();
		}
		myDataBase.execSQL(SQL, bindArgs);
	}

	public SQLiteStatement CompileStatement(String SQL) {
		if (myDataBase == null || !myDataBase.isOpen()) {
			openDataBase();
		}
		return myDataBase.compileStatement(SQL);
	}

	/**
	 * 查询
	 */
	public android.database.sqlite.SQLiteCursor getCursor(String TableName,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {

		if (myDataBase == null || !myDataBase.isOpen()) {
			openDataBase();
		}
		return (android.database.sqlite.SQLiteCursor) myDataBase.query(
				TableName, columns, selection, selectionArgs, groupBy, having,
				orderBy);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (mTableCreateText == null || mTableCreateText.length <= 0)
			return;
		String[] sql = mTableCreateText;
		db.beginTransaction();
		try {
			for (String s : sql) {
				if (s.trim().length() > 0) {
					db.execSQL(s);
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			L.e(LException.getStackMsg(e));
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (mTableDropText == null || mTableDropText.length <= 0)
			return;
		String[] sql = mTableDropText;
		db.beginTransaction();
		try {
			for (String s : sql) {
				if (s.trim().length() > 0) {
					db.execSQL(s);
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			L.e(LException.getStackMsg(e));
		} finally {
			db.endTransaction();
		}
		onCreate(db);
	}

}
