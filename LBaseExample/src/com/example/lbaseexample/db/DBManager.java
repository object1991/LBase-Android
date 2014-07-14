package com.example.lbaseexample.db;

import java.util.ArrayList;
import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;

import com.example.lbaseexample.common.MApplication;
import com.example.lbaseexample.entity.ListEntity;
import com.leo.base.db.LDBHelper;
import com.leo.base.db.LDBManager;
import com.leo.base.util.L;

public class DBManager extends LDBManager {

	private static final String KEY = DBManager.class.getSimpleName();

	private LDBHelper db;

	private static DBManager instance;

	private DBManager() {
		db = MApplication.get().getDBHelper();
	}

	public synchronized static DBManager get() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	public void addListEntitys(List<ListEntity> list) {
		for (ListEntity entity : list) {
			String SQL = insertSql("ListEntityTable", new String[] { "ID",
					"URL", "CONTENT" },
					new String[] { String.valueOf(entity.id), entity.url,
							entity.content });
			execute(SQL, "ID 为 " + entity.id + "的数据插入失败");
		}
	}

	public List<ListEntity> getListEntitys() {
		List<ListEntity> res = new ArrayList<ListEntity>();
		SQLiteCursor cursor = null;
		try {
			cursor = db.getCursor("ListEntityTable", null, null, null, null,
					null, null);
			while (cursor.moveToNext()) {
				ListEntity entity = new ListEntity();
				entity.id = cursor.getInt(cursor.getColumnIndex("ID"));
				entity.url = cursor.getString(cursor.getColumnIndex("URL"));
				entity.content = cursor.getString(cursor
						.getColumnIndex("CONTENT"));
				res.add(entity);
			}
		} catch (Exception e) {
			res = null;
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return res;
	}

	public boolean delListEntitys() {
		String SQL = deleteSql("ListEntityTable");
		return execute(SQL, "清空表内所有数据失败");
	}

	public boolean execute(String SQL, String error) {
		boolean res = true;
		try {
			db.Execute(SQL);
		} catch (SQLException e) {
			res = false;
			if (null != error && !"".equals(error)) {
				L.e(KEY, error);
			}
			e.printStackTrace();
		}
		return res;
	}

}
