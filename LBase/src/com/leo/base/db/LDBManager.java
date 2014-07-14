package com.leo.base.db;

import android.text.TextUtils;

/**
 * 
 * @author Chen Lei
 *
 */
public class LDBManager {

	/**
	 * 生成插入语句
	 * 
	 * @param table
	 *            表名
	 * @param names
	 *            字段数组
	 * @param values
	 *            值数组
	 * @return
	 */
	protected String insertSql(String table, String[] names, String[] values) {
		if (TextUtils.isEmpty(table)) {
			throw new NullPointerException("表名不能为空");
		}
		if (names == null || names.length <= 0) {
			throw new NullPointerException("插入字段名不能为空");
		}
		if (values == null || values.length <= 0) {
			throw new NullPointerException("插入值不能为空");
		}
		if (names.length != values.length) {
			throw new IllegalArgumentException("参数不匹配错误");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(table).append(" (");
		for (String name : names) {
			sb.append(name).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(") VALUES (");
		for (String value : values) {
			sb.append("'").append(value).append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}

	protected String deleteSql(String table) {
		if (TextUtils.isEmpty(table)) {
			throw new NullPointerException("表名不能为空");
		}
		return "DELETE FROM " + table;
	}

	protected String deleteSql(String table, String where) {
		if (TextUtils.isEmpty(table)) {
			throw new NullPointerException("表名不能为空");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ").append(table);
		if (!TextUtils.isEmpty(where)) {
			sb.append(" WHERE ").append(where);
		}
		sb.append(";");
		return sb.toString();
	}

	protected String selectSql(String table) {
		if (TextUtils.isEmpty(table)) {
			throw new NullPointerException("表名不能为空");
		}
		return "SELETE * FROM " + table;
	}

	protected String selectSql(String table, String where) {
		if (TextUtils.isEmpty(table)) {
			throw new NullPointerException("表名不能为空");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SELETE * FROM ").append(table);
		if (!TextUtils.isEmpty(where)) {
			sb.append(" WHERE ").append(where);
		}
		sb.append(";");
		return sb.toString();
	}

	protected String selectSql(String table, String[] selectNames, String where) {
		if (TextUtils.isEmpty(table)) {
			throw new NullPointerException("表名不能为空");
		}
		if (selectNames == null || selectNames.length <= 0) {
			throw new NullPointerException("插入字段名不能为空");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SELETE ");
		for (String name : selectNames) {
			sb.append(name).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" FROM").append(table);
		if (!TextUtils.isEmpty(where)) {
			sb.append(" WHERE ").append(where);
		}
		sb.append(";");
		return sb.toString();
	}

}
