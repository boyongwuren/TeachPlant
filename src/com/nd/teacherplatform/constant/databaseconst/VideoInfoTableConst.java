package com.nd.teacherplatform.constant.databaseconst;

/**
 * 视频信息表的 相关的常量
 * @author Administrator
 *
 */
public class VideoInfoTableConst 
{
	
	/**
	 * 【视频信息】的表的表名称
	 */
	 public static final String VIDEOINFO_TABLE = "videoInfo";
	 
	 /**
	  * 最近播放表里头的列标题
	  */
	 public static final String VIDEOINFO__ID = "id";
	 public static final String VIDEOINFO__VIDEONAME = "videoName";
	 public static final String VIDEOINFO__VIDEOSETID = "videoSetId";//视频集
	 public static final String VIDEOINFO__SUBJECTID = "subjectId";//学科
	 public static final String VIDEOINFO__REQUIREID = "requireId";//必修几
	 public static final String VIDEOINFO__CELLID = "cellId"; //单元ID
	 public static final String VIDEOINFO__AUTHOR = "author";
	 public static final String VIDEOINFO__TEACHERNAME = "teacherName";
	 public static final String VIDEOINFO__TOTALTIME = "totalTime";
	 public static final String VIDEOINFO__VIDEOSIZE = "videoSize";
	 public static final String VIDEOINFO__PREVIEWPICBIG = "preViewPicBig";
	 public static final String VIDEOINFO__PREVIEWPICNOM = "preViewPicNom";
	 public static final String VIDEOINFO__VIDEO_URL = "videoUrl";
	 public static final String VIDEOINFO__VIDEO_FORMAT = "videoFormat";
	 public static final String VIDEOINFO__PLAYTIME = "playTime";//已经播放的时间
	 public static final String VIDEOINFO__LASTPLAYTIME = "lastPlayTime";//上次播放的日期
	 
	 
	 /***
	  * 创建【视频信息】的表的sql语句
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
	  * 删除【视频信息】的表的sql语句
	  */
	 public static final String DELETE_VIDEOINFO_TABLE = "DROP TABLE IF EXISTS " + VideoInfoTableConst.VIDEOINFO_TABLE;
	 
	 
	 
	 
	 
	 
	
}
