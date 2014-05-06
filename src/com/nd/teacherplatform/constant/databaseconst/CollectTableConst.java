package com.nd.teacherplatform.constant.databaseconst;

/**
 * 收藏表
 * @author zmp
 *
 */
public class CollectTableConst
{
    /**
     * 收藏表名称
     */
	public static final String COLLECT_TABLE_NAME = "collectTableName";
 
	/**
	 * 键值ID
	 */
	public static final String TABLE_ID = "tableId";

	/**
	 * 收藏的视频ＩＤ
	 */
	public static final String COLLECT_VIDEO_ID = "collectVideoId";

	/**
	 * 用户编号ＩＤ
	 */
	public static final String USER_ID = "userID";

	/**
	 *  收藏时间
	 */
	public static final String COLLECT_DATE = "collectDate";
	
	
	 /***
	  * 创建【收藏表】的表的sql语句
	  */
	 public static final String CREATE_COLLECT_TABLE = "CREATE TABLE " + CollectTableConst.COLLECT_TABLE_NAME + "( "
	                                                                             +CollectTableConst.TABLE_ID+" INTEGER PRIMARY KEY, " 
			 																	 +CollectTableConst.COLLECT_VIDEO_ID+" INTEGER,  "
			 																	 +CollectTableConst.USER_ID+" INTEGER,  "
	                                                                             +CollectTableConst.COLLECT_DATE+" TEXT)" ;
	 
	 
	 /**
	  * 删除【收藏表】的表的sql语句
	  */
	 public static final String DELETE_COLLECT_TABLE = "DROP TABLE IF EXISTS " + CollectTableConst.COLLECT_TABLE_NAME;
	 

}
