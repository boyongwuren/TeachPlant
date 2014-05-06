package com.nd.teacherplatform.constant.databaseconst;

/**
 * 和下载有关的表的名称
 * @author zmp
 *
 */
public class LoadingTableConst
{
     /**
      * 下载的表名称
      */
	 public static final String DOWNLOAD_TABLE_NAME = "downLoadTableName";
	 
	 /**
	  * 键值ID
	  */
	 public static final String TABLE_ID = "id";
	 
	 /**
	  * 线程的ID
	  */
	 public static final String THREAD_ID = "threadId";

	 /**
	  * 下载文件的起始位置
	  */
	 public static final String START_POS = "startPos";

	 /**
	  * 下载文件的结束位置
	  */
	 public static final String END_POS = "endPos";

	 /**
	  * 已经下载完成的大小
	  */
	 public static final String COMPLETE_SIZE = "completeSize";

	 /**
	  * 文件的URL地址
	  */
	 public static final String FILE_URL = "fileUrl";
	 
	 /**
	  * 加载的视频的id
	  */
	 public static final String LOADING_VIDEO_ID = "loadingVideoId";
	 
	 /**
	  * 创建下载表的语句
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
	  * 删除【下载表】的表的sql语句
	  */
	 public static final String DELETE_DOWNLOAD_TABLE = "DROP TABLE IF EXISTS " + LoadingTableConst.DOWNLOAD_TABLE_NAME;

}
