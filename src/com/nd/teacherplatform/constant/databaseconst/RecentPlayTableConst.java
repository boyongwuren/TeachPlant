package com.nd.teacherplatform.constant.databaseconst;

/**
 * 最近播放表
 * @author zmp
 *
 */
public class RecentPlayTableConst
{
     public static final String TABLE_NAME = "recentPlayTable";
	 
      /**
 	  * 键值ID
 	  */
 	 public static final String TABLE_ID = "id";
 	 
 	 /**
 	  * 视频ID
 	  */
 	 public static final String VIDEO_ID = "videoId";

    //应该还有一个 最近播放的时间的字段。从videoinfo 那里挪过来

    /***
     * 创建【最近播放】的表的sql语句
     */
    public static final String CREATE_RECENTPLAY_TABLE = "CREATE TABLE "
            + RecentPlayTableConst.TABLE_NAME + "( "
            + RecentPlayTableConst.TABLE_ID + " INTEGER PRIMARY KEY, "
            + RecentPlayTableConst.VIDEO_ID + " INTEGER" +
            ")";


    /**
     * 删除【最近播放】的表的sql语句
     */
    public static final String DELETE_RECENTPLAY_TABLE = "DROP TABLE IF EXISTS "
            + RecentPlayTableConst.TABLE_NAME;
}
