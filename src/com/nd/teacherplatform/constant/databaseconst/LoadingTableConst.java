package com.nd.teacherplatform.constant.databaseconst;

/**
 * �������йصı������
 * @author zmp
 *
 */
public class LoadingTableConst
{
     /**
      * ���صı�����
      */
	 public static final String DOWNLOAD_TABLE_NAME = "downLoadTableName";
	 
	 /**
	  * ��ֵID
	  */
	 public static final String TABLE_ID = "id";
	 
	 /**
	  * �̵߳�ID
	  */
	 public static final String THREAD_ID = "threadId";

	 /**
	  * �����ļ�����ʼλ��
	  */
	 public static final String START_POS = "startPos";

	 /**
	  * �����ļ��Ľ���λ��
	  */
	 public static final String END_POS = "endPos";

	 /**
	  * �Ѿ�������ɵĴ�С
	  */
	 public static final String COMPLETE_SIZE = "completeSize";

	 /**
	  * �ļ���URL��ַ
	  */
	 public static final String FILE_URL = "fileUrl";
	 
	 /**
	  * ���ص���Ƶ��id
	  */
	 public static final String LOADING_VIDEO_ID = "loadingVideoId";
	 
	 /**
	  * �������ر�����
	  */
	 public static final String CREATE_DOWNLOAD_TABLE = "create table "+LoadingTableConst.DOWNLOAD_TABLE_NAME +
			 													"( "+LoadingTableConst.TABLE_ID+" integer PRIMARY KEY AUTOINCREMENT , "+
			                                                         LoadingTableConst.THREAD_ID+" integer , "+
			                                                         LoadingTableConst.START_POS+" integer ,"+
			                                                         LoadingTableConst.END_POS+" integer ,"+
			                                                         LoadingTableConst.LOADING_VIDEO_ID+" integer ,"+
			                                                         LoadingTableConst.COMPLETE_SIZE+" integer , "+
			                                                         LoadingTableConst.FILE_URL+" char )";
	 
	 
	 /**
	  * ɾ�������ر��ı��sql���
	  */
	 public static final String DELETE_DOWNLOAD_TABLE = "DROP TABLE IF EXISTS " + LoadingTableConst.DOWNLOAD_TABLE_NAME;

}
