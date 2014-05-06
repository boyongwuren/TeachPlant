package com.nd.teacherplatform.vo.sqlite;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nd.teacherplatform.constant.databaseconst.CollectTableConst;
import com.nd.teacherplatform.vo.CollectVo;

/**
 * �����ղر�
 * @author zmp
 *
 */
public class HandlerCollectTable
{

     /**
      * �������ݵ��ղر�
     * @param userId �û�id
     * @param videoId ��Ƶid
     */
	synchronized public static void insertToCollectTable(int userId, int videoId)
     {
    	 String collectDate = new Date(System.currentTimeMillis()).toString();//��ǰʱ��
    	 
    	 ContentValues cv = new ContentValues();
    	 cv.put(CollectTableConst.USER_ID, userId);
    	 cv.put(CollectTableConst.COLLECT_VIDEO_ID, videoId);
    	 cv.put(CollectTableConst.COLLECT_DATE, collectDate);
    	 
    	 SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
    	 String sql = "select "+CollectTableConst.TABLE_ID+" from " + CollectTableConst.COLLECT_TABLE_NAME + " where "+CollectTableConst.COLLECT_VIDEO_ID+" = ?";
		 Cursor c = sqLiteDatabase.rawQuery(sql, new String[] { String.valueOf(videoId) });
		 if(c.moveToFirst())
		 {
			 //�Ѿ��ղع���
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
     * ��ȡ��Ӧѧ��������ղ�����
     * @param subjectId 0 ��ʾȫ��
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
     * ��ʼ������
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
     * ͨ����ƵIDɾ�� �ղؼ�¼
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
