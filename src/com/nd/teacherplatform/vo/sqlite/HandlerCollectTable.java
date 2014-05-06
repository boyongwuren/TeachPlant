package com.nd.teacherplatform.vo.sqlite;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nd.teacherplatform.constant.databaseconst.CollectTableConst;
import com.nd.teacherplatform.vo.CollectVo;

/**
 * 处理收藏表
 * @author zmp
 *
 */
public class HandlerCollectTable
{

     /**
      * 插入数据到收藏表
     * @param userId 用户id
     * @param videoId 视频id
     */
	synchronized public static void insertToCollectTable(int userId, int videoId)
     {
    	 String collectDate = new Date(System.currentTimeMillis()).toString();//当前时间
    	 
    	 ContentValues cv = new ContentValues();
    	 cv.put(CollectTableConst.USER_ID, userId);
    	 cv.put(CollectTableConst.COLLECT_VIDEO_ID, videoId);
    	 cv.put(CollectTableConst.COLLECT_DATE, collectDate);
    	 
    	 SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
    	 String sql = "select "+CollectTableConst.TABLE_ID+" from " + CollectTableConst.COLLECT_TABLE_NAME + " where "+CollectTableConst.COLLECT_VIDEO_ID+" = ?";
		 Cursor c = sqLiteDatabase.rawQuery(sql, new String[] { String.valueOf(videoId) });
		 if(c.moveToFirst())
		 {
			 //已经收藏过了
			 String [] args = {videoId+""};
			 sqLiteDatabase.update(CollectTableConst.COLLECT_TABLE_NAME, cv, CollectTableConst.COLLECT_VIDEO_ID+" = ?", args);
		 }else 
		 {
			sqLiteDatabase.insert(CollectTableConst.COLLECT_TABLE_NAME, null, cv);
		 }
		 
		 c.close();
		 sqLiteDatabase.close();
		 
		 initcVos();
     }
    
    
    
    private static ArrayList<CollectVo> cVos;
    
    /**
     * 获取对应学科下面的收藏内容
     * @param subjectId 0 标示全部
     * @return
     */
    synchronized public static ArrayList<CollectVo> getCollectData(int subjectId)
    {
    	if(cVos == null)
    	{
    		initcVos();
    	}
    	
    	if(subjectId == 0)
    	{
    		return cVos;
    	}else 
    	{
    		ArrayList<CollectVo> tempVos = new ArrayList<CollectVo>();
    		for (int i = 0; i < cVos.size(); i++)
    		{
				CollectVo vo = cVos.get(i);
				if(vo.vo.sujectID == subjectId)
				{
					tempVos.add(vo);
				}
			}
			return tempVos;
		}
    }
    
    
    /**
     * 初始化数据
     */
    synchronized private static void initcVos()
    {
    	
		 cVos = new ArrayList<CollectVo>();
		
    	 SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
    	 Cursor c = sqLiteDatabase.query(CollectTableConst.COLLECT_TABLE_NAME, null, null, null, null, null, null);
    	 
    	  while(c.moveToNext())
		  {
    		    CollectVo vo = new CollectVo();
		    	int userId = c.getInt(c.getColumnIndex(CollectTableConst.USER_ID));	  
		    	int collectVideoId = c.getInt(c.getColumnIndex(CollectTableConst.COLLECT_VIDEO_ID));	  
		    	String collectDate = c.getString(c.getColumnIndex(CollectTableConst.COLLECT_DATE));	 
		    	vo.userId = userId;
		    	vo.videoId = collectVideoId;
		    	vo.collectDate = collectDate;
		    	vo.vo = HandlerVideoInfoTable.getVideoInfoVo(collectVideoId);
		    	
		    	cVos.add(vo);
		  }
    }
    
    /**
     * 通过视频ID删除 收藏记录
     * @param videoID
     */
    synchronized public static void deleteCollectById(int videoID)
    {
    	String sql = "delete from "+CollectTableConst.COLLECT_TABLE_NAME +" where "+CollectTableConst.COLLECT_VIDEO_ID +" = " + videoID;
    	SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
    	sqLiteDatabase.execSQL(sql);
    	
    	initcVos();
    }


}
