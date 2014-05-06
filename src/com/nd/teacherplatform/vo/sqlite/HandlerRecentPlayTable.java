package com.nd.teacherplatform.vo.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nd.teacherplatform.constant.databaseconst.RecentPlayTableConst;
import com.nd.teacherplatform.vo.VideoInfoVo;

import java.util.ArrayList;

/**
 * 操作 最近播放的表
 * Created by zmp on 13-10-27.
 */
public class HandlerRecentPlayTable
{
    /**
     * 插入数据到最近播放表
     * */
	synchronized public static void insertDataToRecentPlayTable(int videoId)
    {
        ContentValues cv = new ContentValues();
        cv.put(RecentPlayTableConst.VIDEO_ID,videoId);

        SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+RecentPlayTableConst.TABLE_NAME+" where "+RecentPlayTableConst.VIDEO_ID +" = ?",new String[]{videoId+""});

        if(!cursor.moveToNext())
        {
            //没有这条数据，才插入
            sqLiteDatabase.insert(RecentPlayTableConst.TABLE_NAME, null, cv);
        }

    }


    private static boolean isfirst = false;

    /**
     * 得到主页最近播放的记录
     *
     * @return
     */
    synchronized public static ArrayList<VideoInfoVo> getRecentPlayRecord()
    {
        if (isfirst)
        {
            isfirst = false;
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(1, "", "", "视频1", 1, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(11, "", "", "视频1", 1, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(12, "", "", "视频1", 1, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(13, "", "", "视频1", 1, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(14, "", "", "视频1", 1, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(15, "", "", "视频1", 1, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(22, "", "", "视频1", 1, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(23, "", "", "视频1", 1, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(24, "", "", "视频1", 1, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(25, "", "", "视频1", 1, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(26, "", "", "视频1", 1, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(3, "", "", "视频1", 1, 3, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(4, "", "", "视频1", 1, 3, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(5, "", "", "视频1", 1, 4, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(6, "", "", "视频1", 2, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(7, "", "", "视频1", 2, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(8, "", "", "视频1", 2, 3, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(9, "", "", "视频1", 2, 4, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(10, "", "", "视频1", 2, 5, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(11, "", "", "视频1", 3, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(12, "", "", "视频1", 3, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(13, "", "", "视频1", 3, 3, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(14, "", "", "视频1", 3, 4, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(15, "", "", "视频1", 3, 5, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(16, "", "", "视频1", 9, 1, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(17, "", "", "视频1", 9, 2, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(18, "", "", "视频1", 9, 3, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(19, "", "", "视频1", 9, 4, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(20, "", "", "视频1", 9, 5, 3, "教师1", "作者1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoSetTable.insertVideoSetInfo("视频集1", 1, 1, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("视频集1", 2, 2, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("视频集1", 3, 3, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("视频集1", 4, 4, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("视频集1", 5, 5, "", 10);
//
//            HandlerHasDownTable.insertDataToTable(11);
//            HandlerHasDownTable.insertDataToTable(12);
//            HandlerHasDownTable.insertDataToTable(13);
//            HandlerHasDownTable.insertDataToTable(14);
//            HandlerHasDownTable.insertDataToTable(15);
//            HandlerHasDownTable.insertDataToTable(16);
//            HandlerHasDownTable.insertDataToTable(17);
//            HandlerHasDownTable.insertDataToTable(18);
//            HandlerHasDownTable.insertDataToTable(19);
//            HandlerHasDownTable.insertDataToTable(1);
//            HandlerHasDownTable.insertDataToTable(11);
//
//            HandlerCollectTable.insertToCollectTable(1, 1);
//            HandlerCollectTable.insertToCollectTable(1, 2);
//            HandlerCollectTable.insertToCollectTable(1, 3);
//            HandlerCollectTable.insertToCollectTable(1, 4);
//            HandlerCollectTable.insertToCollectTable(1, 5);
//            HandlerCollectTable.insertToCollectTable(1, 6);
//            HandlerCollectTable.insertToCollectTable(1, 7);
//            HandlerCollectTable.insertToCollectTable(1, 8);
//            HandlerCollectTable.insertToCollectTable(1, 9);
//            HandlerCollectTable.insertToCollectTable(1, 10);
//            
//            HandlerRecentPlayTable.insertDataToRecentPlayTable(1);
//            HandlerRecentPlayTable.insertDataToRecentPlayTable(11);
//            HandlerRecentPlayTable.insertDataToRecentPlayTable(12);
//            HandlerRecentPlayTable.insertDataToRecentPlayTable(13);
        }

        SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(RecentPlayTableConst.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<VideoInfoVo> rpvs = new ArrayList<VideoInfoVo>();

        cursor.moveToLast();
        cursor.moveToNext();
        while (cursor.moveToPrevious())
        {
            int videoId = cursor.getInt(cursor.getColumnIndex(RecentPlayTableConst.VIDEO_ID));
            VideoInfoVo vo = HandlerVideoInfoTable.getVideoInfoVo(videoId);
            rpvs.add(vo);
        }



        if (rpvs.size() > 4) {
            for (int i = rpvs.size() - 1; i > 3; i--) {
                rpvs.remove(i);
            }
        }

        return rpvs;
    }




    /**
     * 得到各学科对应的播放记录
     *
     * @return
     */
    synchronized public static ArrayList<VideoInfoVo> getSubjectRecentPlayRecord(int subjectID)
    {

        SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(RecentPlayTableConst.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<VideoInfoVo> vos = new ArrayList<VideoInfoVo>();

        cursor.moveToLast();
        cursor.moveToNext();
        while (cursor.moveToPrevious())
        {
            int videoId = cursor.getInt(cursor.getColumnIndex(RecentPlayTableConst.VIDEO_ID));
            VideoInfoVo vo = HandlerVideoInfoTable.getVideoInfoVo(videoId);

            if(vo.sujectID == subjectID)
            {
                vos.add(vo);
            }
        }
        
        if(vos.size()>20)
        {
        	for (int i = vos.size() - 1; i > 19; i--) {
                vos.remove(i);
            }
        }

        return vos;
    }



}
