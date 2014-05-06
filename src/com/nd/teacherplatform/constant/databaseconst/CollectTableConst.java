package com.nd.teacherplatform.constant.databaseconst;

/**
 * �ղر�
 * @author zmp
 *
 */
public class CollectTableConst
{
    /**
     * �ղر�����
     */
	public static final String COLLECT_TABLE_NAME = "collectTableName";
 
	/**
	 * ��ֵID
	 */
	public static final String TABLE_ID = "tableId";

	/**
	 * �ղص���Ƶ�ɣ�
	 */
	public static final String COLLECT_VIDEO_ID = "collectVideoId";

	/**
	 * �û���ţɣ�
	 */
	public static final String USER_ID = "userID";

	/**
	 *  �ղ�ʱ��
	 */
	public static final String COLLECT_DATE = "collectDate";
	
	
	 /***
	  * �������ղر��ı��sql���
	  */
	 public static final String CREATE_COLLECT_TABLE = "CREATE TABLE " + CollectTableConst.COLLECT_TABLE_NAME + "( "
	                                                                             +CollectTableConst.TABLE_ID+" INTEGER PRIMARY KEY, " 
			 																	 +CollectTableConst.COLLECT_VIDEO_ID+" INTEGER,  "
			 																	 +CollectTableConst.USER_ID+" INTEGER,  "
	                                                                             +CollectTableConst.COLLECT_DATE+" TEXT)" ;
	 
	 
	 /**
	  * ɾ�����ղر��ı��sql���
	  */
	 public static final String DELETE_COLLECT_TABLE = "DROP TABLE IF EXISTS " + CollectTableConst.COLLECT_TABLE_NAME;
	 

}
