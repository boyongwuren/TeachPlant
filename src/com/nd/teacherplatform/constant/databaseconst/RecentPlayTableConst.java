package com.nd.teacherplatform.constant.databaseconst;

/**
 * ������ű�
 * @author zmp
 *
 */
public class RecentPlayTableConst
{
     public static final String TABLE_NAME = "recentPlayTable";
	 
      /**
 	  * ��ֵID
 	  */
 	 public static final String TABLE_ID = "id";
 	 
 	 /**
 	  * ��ƵID
 	  */
 	 public static final String VIDEO_ID = "videoId";

    //Ӧ�û���һ�� ������ŵ�ʱ����ֶΡ���videoinfo ����Ų����

    /***
     * ������������š��ı��sql���
     */
    public static final String CREATE_RECENTPLAY_TABLE = "CREATE TABLE "
            + RecentPlayTableConst.TABLE_NAME + "( "
            + RecentPlayTableConst.TABLE_ID + " INTEGER PRIMARY KEY, "
            + RecentPlayTableConst.VIDEO_ID + " INTEGER" +
            ")";


    /**
     * ɾ����������š��ı��sql���
     */
    public static final String DELETE_RECENTPLAY_TABLE = "DROP TABLE IF EXISTS "
            + RecentPlayTableConst.TABLE_NAME;
}
