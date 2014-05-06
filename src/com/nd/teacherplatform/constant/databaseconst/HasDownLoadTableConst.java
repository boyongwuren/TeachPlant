package com.nd.teacherplatform.constant.databaseconst;

/**
 * 已经下载的视频 表结构
 * @author zmp
 *
 */
public class HasDownLoadTableConst
{

	/**
	 * 已下载的表名称
	 */
	 public static final String HAS_DOWNLOAD_TABLE_NAME = "hasDownLoadTableName";
	 
	 /**
	  * 索引键
	  */
	 public static final String TABLE_ID = "tableId";
	 
	 /**
	  * 已经下载的视频的id
	  */
	 public static final String HAS_DOWN_VIDEO_ID = "hasDownVideoId";
	 
	 
	 /***
	  * 创建【已经下载】的表的sql语句
	  */
	 public static final String CREATE_HASDOWN_TABLE = "CREATE TABLE " + HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME + "( "
	                                                                             +HasDownLoadTableConst.TABLE_ID+" INTEGER PRIMARY KEY, " 
			 																	 +HasDownLoadTableConst.HAS_DOWN_VIDEO_ID+" INTEGER )";
			 																	 
	                                                                             
	 
	 /**
	  * 删除【收藏表】的表的sql语句
	  */
	 public static final String DELETE_HASDOWN_TABLE = "DROP TABLE IF EXISTS " + HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME;
	 

}
