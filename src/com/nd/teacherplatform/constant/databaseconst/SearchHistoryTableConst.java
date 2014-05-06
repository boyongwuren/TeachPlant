package com.nd.teacherplatform.constant.databaseconst;

/**
 * ������ʷ��
 * 
 * @author shj
 * 
 */
public class SearchHistoryTableConst {
	/**
	 * ������ʷ������
	 */
	public static final String SEARCH_TABLE_NAME = "searchHistoryTableName";

	/**
	 * ��ֵID
	 */
	public static final String TABLE_ID = "tableId";

	/**
	 * �����ؼ���
	 */
	public static final String SEARCH_KEYWORD = "searchKeyword";

	/**
	 * �û���ţɣ�
	 */
	public static final String USER_ID = "userID";

	/**
	 * ����ʱ��
	 */
	public static final String SEARCH_DATE = "searchDate";

	/***
	 * �������ղر��ı��sql���
	 */
	public static final String CREATE_SEARCHHISTORY_TABLE = "CREATE TABLE "
			+ SearchHistoryTableConst.SEARCH_TABLE_NAME + "( "
			+ SearchHistoryTableConst.TABLE_ID + " INTEGER PRIMARY KEY, "
			+ SearchHistoryTableConst.SEARCH_KEYWORD + " TEXT,  "
			+ SearchHistoryTableConst.USER_ID + " INTEGER,  "
			+ SearchHistoryTableConst.SEARCH_DATE + " TEXT)";

	/**
	 * ɾ�����ղر��ı��sql���
	 */
	public static final String DELETE_SEARCHHISTORY_TABLE = "DROP TABLE IF EXISTS "
			+ SearchHistoryTableConst.SEARCH_TABLE_NAME;

}
