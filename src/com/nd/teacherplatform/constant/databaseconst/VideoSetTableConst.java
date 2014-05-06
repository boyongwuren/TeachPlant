package com.nd.teacherplatform.constant.databaseconst;


/**
 * 视频集的表的常量
 * @author zmp
 *
 */
public class VideoSetTableConst
{
	 /**
	  * 视频集表的名称
	  */
	 public static final String VIDEO_SET_TABLENAME = "videoSetTable";
	 
	 /**
	  * 视频集 id
	  */
	 public static final String VIDEOSET_ID = "videoSetId";
	 
	 /**
	  * 视频集 名称
	  */
	 public static final String VIDEOSET_NAME = "videoSetName";
	 
	 /**
	  * 视频集 预览图片地址
	  */
	 public static final String VIDEOSET_PREVIEWPIC = "videoSetPreVidwPicURL";
	 
	 /**
	  * 视频集 总共视频节数
	  */
	 public static final String VIDEOSET_TOTALNUM = "videoSetTotalNum";
	 
	 /**
	  * 视频集 已经下载的视频数量
	  */
	 public static final String VIDEOSET_HASDOWNLOAD = "videoSetHasDownLoad";
	 
	 
	 /***
	  * 创建【视频集】的表的sql语句
	  */
	 public static final String CREATE_VIDEOSET_TABLE = "CREATE TABLE " + VideoSetTableConst.VIDEO_SET_TABLENAME + "( "
	                                                                             +VideoSetTableConst.VIDEOSET_ID+" INTEGER PRIMARY KEY, " 
			 																	 +VideoSetTableConst.VIDEOSET_NAME+" TEXT,  "
			 																	 +VideoSetTableConst.VIDEOSET_TOTALNUM+" INTEGER,  "
			 																	 +VideoSetTableConst.VIDEOSET_HASDOWNLOAD+" INTEGER,  "
	                                                                             +VideoSetTableConst.VIDEOSET_PREVIEWPIC+" TEXT)" ;
	 
	 
	 /**
	  * 删除【视频集】的表的sql语句
	  */
	 public static final String DELETE_VIDEOSET_TABLE = "DROP TABLE IF EXISTS " + VideoSetTableConst.VIDEO_SET_TABLENAME;
	 
}
