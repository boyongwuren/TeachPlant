package com.nd.teacherplatform.constant.databaseconst;

/**
 * 搜索历史表
 * 
 * @author shj
 * 
 */
public class SearchHistoryTableConst {
	/**
	 * 搜索历史表名称
	 */
	public static final String SEARCH_TABLE_NAME = "searchHistoryTableName";

	/**
	 * 键值ID
	 */
	public static final String TABLE_ID = "tableId";

	/**
	 * 搜索关键词
	 */
	public static final String SEARCH_KEYWORD = "searchKeyword";

	/**
	 * 用户编号ＩＤ
	 */
	public static final String USER_ID = "userID";

	/**
	 * 搜索时间
	 */
	public static final String SEARCH_DATE = "searchDate";

	/***
	 * 创建【收藏表】的表的sql语句
	 */
	public static final String CREATE_SEARCHHISTORY_TABLE = "CREATE TABLE "
			+ SearchHistoryTableConst.SEARCH_TABLE_NAME + "( "
			+ SearchHistoryTableConst.TABLE_ID + " INTEGER PRIMARY KEY, "
			+ SearchHistoryTableConst.SEARCH_KEYWORD + " TEXT,  "
			+ SearchHistoryTableConst.USER_ID + " INTEGER,  "
			+ SearchHistoryTableConst.SEARCH_DATE + " TEXT)";

	/**
	 * 删除【收藏表】的表的sql语句
	 */
	public static final String DELETE_SEARCHHISTORY_TABLE = "DROP TABLE IF EXISTS "
			+ SearchHistoryTableConst.SEARCH_TABLE_NAME;

}
