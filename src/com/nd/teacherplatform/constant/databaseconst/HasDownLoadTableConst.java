package com.nd.teacherplatform.constant.databaseconst;

/**
 * �Ѿ����ص���Ƶ ��ṹ
 * @author zmp
 *
 */
public class HasDownLoadTableConst
{

	/**
	 * �����صı�����
	 */
	 public static final String HAS_DOWNLOAD_TABLE_NAME = "hasDownLoadTableName";
	 
	 /**
	  * ������
	  */
	 public static final String TABLE_ID = "tableId";
	 
	 /**
	  * �Ѿ����ص���Ƶ��id
	  */
	 public static final String HAS_DOWN_VIDEO_ID = "hasDownVideoId";
	 
	 
	 /***
	  * �������Ѿ����ء��ı��sql���
	  */
	 public static final String CREATE_HASDOWN_TABLE = "CREATE TABLE " + HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME + "( "
	                                                                             +HasDownLoadTableConst.TABLE_ID+" INTEGER PRIMARY KEY, " 
			 																	 +HasDownLoadTableConst.HAS_DOWN_VIDEO_ID+" INTEGER )";
			 																	 
	                                                                             
	 
	 /**
	  * ɾ�����ղر��ı��sql���
	  */
	 public static final String DELETE_HASDOWN_TABLE = "DROP TABLE IF EXISTS " + HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME;
	 

}
