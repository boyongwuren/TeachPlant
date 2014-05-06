package com.nd.teacherplatform.constant;

/**
 * ���ݿ���صĳ���
 * @author Administrator
 *
 */
public class DataBaseConst 
{
	
	/**
	 * ���ݿ�����
	 */
	public static final String DATABASE_NAME = "TEACHER_PLATFORM_DATABASE.db";

	/**
	 * ���ݿ�汾
	 */
	public static final int DATABASE_VERSION = 1;

	/**
	 * ������ۿ����ı�ı�����
	 */
	 public static final String RECENT_PLAY_TABLE = "CurPlayTable";
	 
	 /**
	  * ������ű���ͷ���б���
	  */
	 public static final String RECENTPLAY__ID = "id";
	 public static final String RECENTPLAY__PREVIEWPICNOM = "preViewPicNom";
	 public static final String RECENTPLAY__PREVIEWPICBIG = "preViewPicBig";
	 public static final String RECENTPLAY__VIDEONAME = "videoName";
	 public static final String RECENTPLAY__SUBJECT = "subject";
	 public static final String RECENTPLAY__VIDEOSETNAME = "videoSetName";
	 public static final String RECENTPLAY__VIDEOTYPE = "videoType";
	 public static final String RECENTPLAY__TEACHERNAME = "teacherName";
	 public static final String RECENTPLAY__AUTHOR = "author";
	 public static final String RECENTPLAY__TOTALTIME = "totalTime";
	 public static final String RECENTPLAY__PLAYTIME = "playTime";
	 public static final String RECENTPLAY__LASTPLAYTIME = "lastPlayTime";
	 public static final String RECENTPLAY__VIDEO_URL = "videoUrl";
	 
	 /***
	  * ����������ۿ����ı��sql���
	  */
	 public static final String CREATE_CURPLAY_TABLE = "CREATE TABLE " + DataBaseConst.RECENT_PLAY_TABLE + "( "+ DataBaseConst.RECENTPLAY__ID+" INTEGER PRIMARY KEY, " +
	 																										 ""+ DataBaseConst.RECENTPLAY__PREVIEWPICNOM+" TEXT," +
	 																										 ""+DataBaseConst.RECENTPLAY__PREVIEWPICBIG+" TEXT," +
	 																										 ""+DataBaseConst.RECENTPLAY__VIDEONAME+" TEXT," +
	 																										 ""+DataBaseConst.RECENTPLAY__SUBJECT+" INTEGER," +
	 																										 ""+DataBaseConst.RECENTPLAY__VIDEOSETNAME+" TEXT, " +
	 																										 ""+DataBaseConst.RECENTPLAY__VIDEOTYPE+" INTEGER," +
	 																										 ""+DataBaseConst.RECENTPLAY__TEACHERNAME+" TEXT, " +
	 																										 ""+DataBaseConst.RECENTPLAY__AUTHOR+" TEXT,  " +
	 																										 ""+DataBaseConst.RECENTPLAY__TOTALTIME+" INTEGER ," +
	 																										 ""+DataBaseConst.RECENTPLAY__PLAYTIME+" INTEGER," +
	 																										 ""+DataBaseConst.RECENTPLAY__LASTPLAYTIME+" TEXT, "+
	 																										    DataBaseConst.RECENTPLAY__VIDEO_URL+" TEXT)" ;
	 
	 /**
	  * ɾ��������ۿ����ı��sql���
	  */
	 public static final String DELETE_MYCOLLECT_TABLE = "DROP TABLE IF EXISTS " + DataBaseConst.RECENT_PLAY_TABLE;
}
