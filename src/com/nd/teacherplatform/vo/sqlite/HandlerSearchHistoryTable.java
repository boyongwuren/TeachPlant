package com.nd.teacherplatform.vo.sqlite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nd.teacherplatform.constant.databaseconst.SearchHistoryTableConst;
import com.nd.teacherplatform.vo.SearchHistoryVo;

/**
 * 搜索历史表
 * 
 * @author shj
 * 
 */
public class HandlerSearchHistoryTable {
	private static final String TAG = "HandlerSearchHistoryTable"; // 调试标签

	synchronized public static ArrayList<SearchHistoryVo> getSearchHistoryVos(
			Date beginDate, int Count, int AccountID, String keyword) {
		SimpleDateFormat bigLongSdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SSSS", Locale.CHINA);
		ArrayList<SearchHistoryVo> searchHistoryVos = new ArrayList<SearchHistoryVo>();

		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance()
				.getReadableDatabase();
		if (null != sqLiteDatabase) {
			try {
				String sql = "select %s, %s, %s, %s from %s "
						+ " where %s = %d and %s like '%%%s%%' and %s < '%s' "
						+ " order by %s desc limit 0, %d ";
				sql = String.format(sql, SearchHistoryTableConst.TABLE_ID,
						SearchHistoryTableConst.USER_ID,
						SearchHistoryTableConst.SEARCH_KEYWORD,
						SearchHistoryTableConst.SEARCH_DATE,
						SearchHistoryTableConst.SEARCH_TABLE_NAME,
						SearchHistoryTableConst.USER_ID, AccountID,
						SearchHistoryTableConst.SEARCH_KEYWORD, keyword,
						SearchHistoryTableConst.SEARCH_DATE,
						bigLongSdf.format(beginDate),
						SearchHistoryTableConst.SEARCH_DATE, Count);
				Cursor c = sqLiteDatabase.rawQuery(sql, null);
				if (null != c) {
					try {
						SearchHistoryVo searchHistoryVo;
						int columnIDindex = c
								.getColumnIndex(SearchHistoryTableConst.TABLE_ID);
						int columnAccountIDindex = c
								.getColumnIndex(SearchHistoryTableConst.USER_ID);
						int columnKeywordindex = c
								.getColumnIndex(SearchHistoryTableConst.SEARCH_KEYWORD);
						int columnOperateDateindex = c
								.getColumnIndex(SearchHistoryTableConst.SEARCH_DATE);

						while (c.moveToNext()) {
							searchHistoryVo = new SearchHistoryVo();
							searchHistoryVo.setId(c.getInt(columnIDindex));
							searchHistoryVo.setUserID(c
									.getInt(columnAccountIDindex));
							searchHistoryVo.setKeyword(c
									.getString(columnKeywordindex));
							searchHistoryVo.setOperateDate(bigLongSdf.parse(c
									.getString(columnOperateDateindex)));

							searchHistoryVos.add(searchHistoryVo);
						}
					} finally {
						c.close();
					}
				}
			} catch (Exception e) {
				Log.e(TAG, "getSearchHistoryVos", e);
			} finally {
				if (sqLiteDatabase.isOpen())
					sqLiteDatabase.close();
			}
		}

		return searchHistoryVos;
	}

	synchronized public static boolean updateSearchHistoryVo(int id,
			String operateDate) {
		boolean lreturn = false;
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance()
				.getReadableDatabase();

		if (null != sqLiteDatabase) {
			try {
				String sql = "update %s set %s = ? where %s = ? ";
				sql = String.format(sql,
						SearchHistoryTableConst.SEARCH_TABLE_NAME,
						SearchHistoryTableConst.SEARCH_DATE,
						SearchHistoryTableConst.TABLE_ID);

				sqLiteDatabase.execSQL(sql,
						new String[] { operateDate, String.valueOf(id) });
				lreturn = true;
			} catch (Exception e) {
				Log.e(TAG, "updateSearchHistoryVo", e);
			} finally {
				if (sqLiteDatabase.isOpen())
					sqLiteDatabase.close();
			}
		}

		return lreturn;
	}

	synchronized public static boolean clearSearchHistory() {
		boolean lreturn = false;
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance()
				.getReadableDatabase();
		if (null != sqLiteDatabase) {
			sqLiteDatabase.delete(SearchHistoryTableConst.SEARCH_TABLE_NAME,
					SearchHistoryTableConst.USER_ID + "!=?",
					new String[] { "0" });
			lreturn = true;
		}
		return lreturn;
	}

	// 增加搜索记录（不存在插入，存在则更新）
	synchronized public static boolean AddSearchHistoryVo(
			SearchHistoryVo searchHistoryVo) {
		boolean lreturn = false;
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance()
				.getReadableDatabase();
		if (null != searchHistoryVo && null != sqLiteDatabase) {
			try {
				String sql = "select %s from %s where %s = ? and %s = ? ";
				sql = String.format(sql, SearchHistoryTableConst.TABLE_ID,
						SearchHistoryTableConst.SEARCH_TABLE_NAME,
						SearchHistoryTableConst.USER_ID,
						SearchHistoryTableConst.SEARCH_KEYWORD);
				Cursor c = sqLiteDatabase.rawQuery(
						sql,
						new String[] {
								String.valueOf(searchHistoryVo.getUserID()),
								searchHistoryVo.getKeyword() });
				if (c != null) {
					SimpleDateFormat bigLongSdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSSS", Locale.CHINA);
					String curDateTime = bigLongSdf.format(new Date(System
							.currentTimeMillis())); // 当前时间
					try {
						if (c.moveToFirst()) {
							int id = c
									.getInt(c
											.getColumnIndex(SearchHistoryTableConst.TABLE_ID));
							updateSearchHistoryVo(id, curDateTime);

						} else {
							sql = "insert into %s(%s, %s, %s) Values(?, ?, ?)";
							sql = String.format(sql,
									SearchHistoryTableConst.SEARCH_TABLE_NAME,
									SearchHistoryTableConst.USER_ID,
									SearchHistoryTableConst.SEARCH_KEYWORD,
									SearchHistoryTableConst.SEARCH_DATE);
							sqLiteDatabase.execSQL(
									sql,
									new String[] {
											String.valueOf(searchHistoryVo
													.getUserID()),
											searchHistoryVo.getKeyword(),
											curDateTime });
						}
						lreturn = true;
					} finally {
						c.close();
					}
				}
			} catch (Exception e) {
				Log.e(TAG, "AddSearchHistoryVo", e);
			} finally {
				if (sqLiteDatabase.isOpen())
					sqLiteDatabase.close();
			}
		}
		return lreturn;
	}
}
