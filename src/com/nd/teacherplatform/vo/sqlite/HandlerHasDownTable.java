package com.nd.teacherplatform.vo.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.nd.teacherplatform.constant.SubjectTypeConst;
import com.nd.teacherplatform.constant.databaseconst.HasDownLoadTableConst;
import com.nd.teacherplatform.constant.databaseconst.VideoInfoTableConst;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.VideoSetVo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ���������ر�� �߼�
 * @author zmp
 *
 */
public class HandlerHasDownTable
{
	
	private static  Map<String, Map<String, ArrayList<VideoInfoVo>>> totalMap;
	
	
     /**
     * ��ȡ��������Ƶ���б�
     * @param subjectId 0 ��ʾ����
     */
	synchronized public static Map<String, ArrayList<VideoInfoVo>> getHasDownLoadInfo(int subjectId)
     {
    	if(totalMap == null)
    	{
    		initData();
    	}
    	
    	 return totalMap.get(subjectId+"");
    	 
     }
	
	/**
	 * �Ƿ��Ѿ����ع� �����Ƶ
	 * @param videoId
	 * @return
	 */
	synchronized public static boolean hasDownLoadVideo(int videoId)
	{
		String sql = "select "+HasDownLoadTableConst.HAS_DOWN_VIDEO_ID+" from " +HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME + " where "+HasDownLoadTableConst.HAS_DOWN_VIDEO_ID+" =?";
	    SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
	    Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{videoId+""});
	    if(cursor.moveToNext())
	    {
	    	cursor.close();
	    	return true;
	    }
	    
	    cursor.close();
	    return false;
	}
    
    
    /**
     * ���������ص�����
     * @param videoId
     */
	synchronized public static void insertDataToTable(int videoId)
    {
    	ContentValues cv = new ContentValues();
		cv.put(HasDownLoadTableConst.HAS_DOWN_VIDEO_ID, videoId);
		
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();

		String sqlString = "select * from "+HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME + " where "+ HasDownLoadTableConst.HAS_DOWN_VIDEO_ID+" =?";
		Cursor cursor = sqLiteDatabase.rawQuery(sqlString, new String[]{videoId+""});
		
		if(cursor.moveToNext())
		{
			
		}else 
		{
			sqLiteDatabase.insert(HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME, null, cv);
			
			VideoInfoVo vo = HandlerVideoInfoTable.getVideoInfoVo(videoId);
			VideoSetVo setVo = HandlerVideoSetTable.getVideoSetVo(vo.getVideoSetID());
			setVo.hasDownNum++;
			HandlerVideoSetTable.upData(setVo.id, setVo.hasDownNum);
			
			initData();
		}

    }
    
    
    /**
     * ��ʼ������
     */
	synchronized private static void initData()
    {
    	 SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
   	  
    	 Cursor cursor = sqLiteDatabase.query(HasDownLoadTableConst.HAS_DOWNLOAD_TABLE_NAME, null, null, null, null, null, null);
    
    	 Map<String, ArrayList<VideoInfoVo>> map;
    	 
    	 totalMap = new Hashtable<String, Map<String,ArrayList<VideoInfoVo>>>();
    	 
    	 map = new Hashtable<String, ArrayList<VideoInfoVo>>();
    	 totalMap.put(SubjectTypeConst.SUOYOU_ID+"", map);
    	 
    	 
    	
         while (cursor.moveToNext()) 
         {
			int videoId = cursor.getInt(cursor.getColumnIndex(HasDownLoadTableConst.HAS_DOWN_VIDEO_ID));
			VideoInfoVo vo = HandlerVideoInfoTable.getVideoInfoVo(videoId);
			
			
			//==================================================================
			 map = totalMap.get(SubjectTypeConst.SUOYOU_ID+"");

			if(map.containsKey(vo.getVideoSetID()+""))
			{
				//�Ѿ��������Ƶ����
				ArrayList<VideoInfoVo> vos = map.get(vo.getVideoSetID()+"");
				vos.add(vo);
			}else 
			{
				//��û�������Ƶ��
				ArrayList<VideoInfoVo> vos = new ArrayList<VideoInfoVo>();
				map.put(vo.getVideoSetID()+"", vos);
				vos.add(vo);
			}
			//==================================================================
			
			if(totalMap.containsKey(vo.sujectID+""))
			{
				//�����ѧ����
				map = totalMap.get(vo.sujectID+"");
			}else 
			{
				//û�����ѧ��
				 map = new Hashtable<String, ArrayList<VideoInfoVo>>();
				 totalMap.put(vo.sujectID+"", map);
			}
			
			if(map.containsKey(vo.getVideoSetID()+""))
			{
				//�Ѿ��������Ƶ����
				ArrayList<VideoInfoVo> vos = map.get(vo.getVideoSetID()+"");
				vos.add(vo);
			}else 
			{
				//��û�������Ƶ��
				ArrayList<VideoInfoVo> vos = new ArrayList<VideoInfoVo>();
				map.put(vo.getVideoSetID()+"", vos);
				vos.add(vo);
			}
			
		}
    }
}
