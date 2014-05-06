package com.nd.teacherplatform.vo.sqlite;

import com.nd.teacherplatform.constant.databaseconst.VideoSetTableConst;
import com.nd.teacherplatform.vo.VideoSetVo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ƶ�� �ı�Ĳ�������
 * @author zmp
 *
 */
public class HandlerVideoSetTable
{

	 /**
	  * ͨ����Ƶ�� id �õ���Ƶ������
	 * @param videoSetId ��Ƶ��id
	 * @return
	 */
	synchronized public static String getVideoSetName(int videoSetId)
	 {
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from " + VideoSetTableConst.VIDEO_SET_TABLENAME + " where " + VideoSetTableConst.VIDEOSET_ID + " = ? ", new String[] { videoSetId + "" });
        if(cursor.moveToFirst())
        {
        	cursor.moveToPosition(0);
        	return cursor.getString(cursor.getColumnIndex(VideoSetTableConst.VIDEOSET_NAME));
        }
        
        cursor.close();
		
		 return "null";
	 }

	/**
	 * ͨ����Ƶ�� id �õ���Ƶ����Ϣ
	 * @param videoSetId ��Ƶ��id
	 * @return
	 */
	synchronized public static VideoSetVo getVideoSetVo(int videoSetId)
	{
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from " + VideoSetTableConst.VIDEO_SET_TABLENAME + " where " + VideoSetTableConst.VIDEOSET_ID + " = ? ", new String[] { videoSetId + "" });
		VideoSetVo vo = new VideoSetVo();
		while (cursor.moveToNext()) 
		{
			
			String videoSetName = cursor.getString(cursor.getColumnIndex(VideoSetTableConst.VIDEOSET_NAME));
			String preViewPic = cursor.getString(cursor.getColumnIndex(VideoSetTableConst.VIDEOSET_PREVIEWPIC));
			
			vo.id = videoSetId;
			vo.preViewPicUrl = preViewPic;
			vo.videoSetName = videoSetName;
		}
		
		cursor.close();
		sqLiteDatabase.close();
		
		return vo;
	}
	
	
	/**
	 * ���� ��Ƶ�� ��Ϣ
	 * @param videoSetName ��Ƶ�� ����
	 * @param hasDownLoadNum �Ѿ����ص�����
	 * @param videoSetId     ��Ƶ��ID
	 * @param preViewPicUrl  ��Ƶ�� ͼƬ��ַ
	 * @param videoSetTotalNum  ��Ƶ���ܹ��м�����Ƶ
	 */
	synchronized public static void insertVideoSetInfo(String videoSetName,int hasDownLoadNum,int videoSetId,String preViewPicUrl,int videoSetTotalNum)
	{
		ContentValues cv = new ContentValues();
		if("".equals(videoSetName) == false)
		{
			cv.put(VideoSetTableConst.VIDEOSET_NAME, videoSetName);
		}
		
		if(hasDownLoadNum != 0)
		{
			cv.put(VideoSetTableConst.VIDEOSET_HASDOWNLOAD, hasDownLoadNum);
		}
		
		cv.put(VideoSetTableConst.VIDEOSET_ID, videoSetId);
		
		if("".equals(preViewPicUrl) == false)
		{
			cv.put(VideoSetTableConst.VIDEOSET_PREVIEWPIC, preViewPicUrl);
		}
		
		if(videoSetTotalNum != 0)
		{
			cv.put(VideoSetTableConst.VIDEOSET_TOTALNUM, videoSetTotalNum);
		}
		 
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		
		 String sql = "select "+VideoSetTableConst.VIDEOSET_ID+" from " + VideoSetTableConst.VIDEO_SET_TABLENAME + " where "+VideoSetTableConst.VIDEOSET_ID+" = ?";
		 Cursor c = sqLiteDatabase.rawQuery(sql, new String[] { String.valueOf(videoSetId) });
		 if(c.moveToFirst())
		 {
			 //�Ѿ��ղع���
			 String [] args = {videoSetId+""};
			 sqLiteDatabase.update(VideoSetTableConst.VIDEO_SET_TABLENAME, cv, VideoSetTableConst.VIDEOSET_ID+" = ?", args);
		 }else 
		 {
			sqLiteDatabase.insert(VideoSetTableConst.VIDEO_SET_TABLENAME, null, cv);
		 }
	}
	
	synchronized public static void upData(int videoSetId,int hasDownLoad)
	{
		ContentValues cv = new ContentValues();
		cv.put(VideoSetTableConst.VIDEOSET_HASDOWNLOAD, hasDownLoad);
		
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();

		sqLiteDatabase.update(VideoSetTableConst.VIDEO_SET_TABLENAME, cv, VideoSetTableConst.VIDEOSET_ID+" = ?", new String[]{videoSetId+""});
	}

}
