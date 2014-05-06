package com.nd.teacherplatform.constant.databaseconst;

/**
 * ��Ƶ��Ϣ��� ��صĳ���
 * @author Administrator
 *
 */
public class VideoInfoTableConst 
{
	
	/**
	 * ����Ƶ��Ϣ���ı�ı�����
	 */
	 public static final String VIDEOINFO_TABLE = "videoInfo";
	 
	 /**
	  * ������ű���ͷ���б���
	  */
	 public static final String VIDEOINFO__ID = "id";
	 public static final String VIDEOINFO__VIDEONAME = "videoName";
	 public static final String VIDEOINFO__VIDEOSETID = "videoSetId";//��Ƶ��
	 public static final String VIDEOINFO__SUBJECTID = "subjectId";//ѧ��
	 public static final String VIDEOINFO__REQUIREID = "requireId";//���޼�
	 public static final String VIDEOINFO__CELLID = "cellId"; //��ԪID
	 public static final String VIDEOINFO__AUTHOR = "author";
	 public static final String VIDEOINFO__TEACHERNAME = "teacherName";
	 public static final String VIDEOINFO__TOTALTIME = "totalTime";
	 public static final String VIDEOINFO__VIDEOSIZE = "videoSize";
	 public static final String VIDEOINFO__PREVIEWPICBIG = "preViewPicBig";
	 public static final String VIDEOINFO__PREVIEWPICNOM = "preViewPicNom";
	 public static final String VIDEOINFO__VIDEO_URL = "videoUrl";
	 public static final String VIDEOINFO__VIDEO_FORMAT = "videoFormat";
	 public static final String VIDEOINFO__PLAYTIME = "playTime";//�Ѿ����ŵ�ʱ��
	 public static final String VIDEOINFO__LASTPLAYTIME = "lastPlayTime";//�ϴβ��ŵ�����
	 
	 
	 /***
	  * ��������Ƶ��Ϣ���ı��sql���
	  */
	 public static final String CREATE_VIDEOINFO_TABLE = "CREATE TABLE " + VideoInfoTableConst.VIDEOINFO_TABLE + "( "+ VideoInfoTableConst.VIDEOINFO__ID+" INTEGER PRIMARY KEY, " +
	 																										 ""+ VideoInfoTableConst.VIDEOINFO__PREVIEWPICNOM+" TEXT," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__PREVIEWPICBIG+" TEXT," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__VIDEONAME+" TEXT," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__SUBJECTID+" INTEGER," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__VIDEOSETID+" TEXT, " +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__REQUIREID+" INTEGER," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__CELLID+" INTEGER," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__TEACHERNAME+" TEXT, " +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__AUTHOR+" TEXT,  " +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__TOTALTIME+" INTEGER ," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__VIDEOSIZE+" INTEGER ," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__PLAYTIME+" INTEGER," +
	 																										 ""+VideoInfoTableConst.VIDEOINFO__LASTPLAYTIME+" TEXT, "+
	 																										    VideoInfoTableConst.VIDEOINFO__VIDEO_URL+" TEXT)" ;
	 
	 /**
	  * ɾ������Ƶ��Ϣ���ı��sql���
	  */
	 public static final String DELETE_VIDEOINFO_TABLE = "DROP TABLE IF EXISTS " + VideoInfoTableConst.VIDEOINFO_TABLE;
	 
	 
	 
	 
	 
	 
	
}
