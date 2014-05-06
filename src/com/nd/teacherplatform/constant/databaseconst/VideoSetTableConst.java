package com.nd.teacherplatform.constant.databaseconst;


/**
 * ��Ƶ���ı�ĳ���
 * @author zmp
 *
 */
public class VideoSetTableConst
{
	 /**
	  * ��Ƶ���������
	  */
	 public static final String VIDEO_SET_TABLENAME = "videoSetTable";
	 
	 /**
	  * ��Ƶ�� id
	  */
	 public static final String VIDEOSET_ID = "videoSetId";
	 
	 /**
	  * ��Ƶ�� ����
	  */
	 public static final String VIDEOSET_NAME = "videoSetName";
	 
	 /**
	  * ��Ƶ�� Ԥ��ͼƬ��ַ
	  */
	 public static final String VIDEOSET_PREVIEWPIC = "videoSetPreVidwPicURL";
	 
	 /**
	  * ��Ƶ�� �ܹ���Ƶ����
	  */
	 public static final String VIDEOSET_TOTALNUM = "videoSetTotalNum";
	 
	 /**
	  * ��Ƶ�� �Ѿ����ص���Ƶ����
	  */
	 public static final String VIDEOSET_HASDOWNLOAD = "videoSetHasDownLoad";
	 
	 
	 /***
	  * ��������Ƶ�����ı��sql���
	  */
	 public static final String CREATE_VIDEOSET_TABLE = "CREATE TABLE " + VideoSetTableConst.VIDEO_SET_TABLENAME + "( "
	                                                                             +VideoSetTableConst.VIDEOSET_ID+" INTEGER PRIMARY KEY, " 
			 																	 +VideoSetTableConst.VIDEOSET_NAME+" TEXT,  "
			 																	 +VideoSetTableConst.VIDEOSET_TOTALNUM+" INTEGER,  "
			 																	 +VideoSetTableConst.VIDEOSET_HASDOWNLOAD+" INTEGER,  "
	                                                                             +VideoSetTableConst.VIDEOSET_PREVIEWPIC+" TEXT)" ;
	 
	 
	 /**
	  * ɾ������Ƶ�����ı��sql���
	  */
	 public static final String DELETE_VIDEOSET_TABLE = "DROP TABLE IF EXISTS " + VideoSetTableConst.VIDEO_SET_TABLENAME;
	 
}
