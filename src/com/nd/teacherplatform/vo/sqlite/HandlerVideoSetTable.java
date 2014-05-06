package com.nd.teacherplatform.vo.sqlite;

import com.nd.teacherplatform.constant.databaseconst.VideoSetTableConst;
import com.nd.teacherplatform.vo.VideoSetVo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 视频集 的表的操作集合
 * @author zmp
 *
 */
public class HandlerVideoSetTable
{

	 /**
	  * 通过视频集 id 得到视频集名称
	 * @param videoSetId 视频集id
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
	 * 通过视频集 id 得到视频集信息
	 * @param videoSetId 视频集id
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
	 * 插入 视频集 信息
	 * @param videoSetName 视频集 名称
	 * @param hasDownLoadNum 已经下载的数量
	 * @param videoSetId     视频集ID
	 * @param preViewPicUrl  视频集 图片地址
	 * @param videoSetTotalNum  视频集总共有几个视频
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
			 //已经收藏过了
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
