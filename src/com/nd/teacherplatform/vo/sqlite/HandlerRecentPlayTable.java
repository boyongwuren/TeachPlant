package com.nd.teacherplatform.vo.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nd.teacherplatform.constant.databaseconst.RecentPlayTableConst;
import com.nd.teacherplatform.vo.VideoInfoVo;

import java.util.ArrayList;

/**
 * ���� ������ŵı�
 * Created by zmp on 13-10-27.
 */
public class HandlerRecentPlayTable
{
    /**
     * �������ݵ�������ű�
     * */
	synchronized public static void insertDataToRecentPlayTable(int videoId)
    {
        ContentValues cv = new ContentValues();
        cv.put(RecentPlayTableConst.VIDEO_ID,videoId);

        SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+RecentPlayTableConst.TABLE_NAME+" where "+RecentPlayTableConst.VIDEO_ID +" = ?",new String[]{videoId+""});

        if(!cursor.moveToNext())
        {
            //û���������ݣ��Ų���
            sqLiteDatabase.insert(RecentPlayTableConst.TABLE_NAME, null, cv);
        }

    }


    private static boolean isfirst = false;

    /**
     * �õ���ҳ������ŵļ�¼
     *
     * @return
     */
    synchronized public static ArrayList<VideoInfoVo> getRecentPlayRecord()
    {
        if (isfirst)
        {
            isfirst = false;
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(1, "", "", "��Ƶ1", 1, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(11, "", "", "��Ƶ1", 1, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(12, "", "", "��Ƶ1", 1, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(13, "", "", "��Ƶ1", 1, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(14, "", "", "��Ƶ1", 1, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(15, "", "", "��Ƶ1", 1, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(22, "", "", "��Ƶ1", 1, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(23, "", "", "��Ƶ1", 1, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(24, "", "", "��Ƶ1", 1, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(25, "", "", "��Ƶ1", 1, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(26, "", "", "��Ƶ1", 1, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(3, "", "", "��Ƶ1", 1, 3, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(4, "", "", "��Ƶ1", 1, 3, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(5, "", "", "��Ƶ1", 1, 4, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(6, "", "", "��Ƶ1", 2, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(7, "", "", "��Ƶ1", 2, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(8, "", "", "��Ƶ1", 2, 3, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(9, "", "", "��Ƶ1", 2, 4, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(10, "", "", "��Ƶ1", 2, 5, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(11, "", "", "��Ƶ1", 3, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(12, "", "", "��Ƶ1", 3, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(13, "", "", "��Ƶ1", 3, 3, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(14, "", "", "��Ƶ1", 3, 4, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(15, "", "", "��Ƶ1", 3, 5, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(16, "", "", "��Ƶ1", 9, 1, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(17, "", "", "��Ƶ1", 9, 2, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(18, "", "", "��Ƶ1", 9, 3, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(19, "", "", "��Ƶ1", 9, 4, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//            HandlerVideoInfoTable.insertDataToVideoInfoTable(20, "", "", "��Ƶ1", 9, 5, 3, "��ʦ1", "����1", 240, 140, "xxx", "", 1);
//
//            HandlerVideoSetTable.insertVideoSetInfo("��Ƶ��1", 1, 1, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("��Ƶ��1", 2, 2, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("��Ƶ��1", 3, 3, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("��Ƶ��1", 4, 4, "", 10);
//            HandlerVideoSetTable.insertVideoSetInfo("��Ƶ��1", 5, 5, "", 10);
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
     * �õ���ѧ�ƶ�Ӧ�Ĳ��ż�¼
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
