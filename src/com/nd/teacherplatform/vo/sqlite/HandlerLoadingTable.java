package com.nd.teacherplatform.vo.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nd.teacherplatform.constant.SubjectTypeConst;
import com.nd.teacherplatform.constant.databaseconst.LoadingTableConst;
import com.nd.teacherplatform.download.LoadingInfo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.VideoSetVo;

import android.R.integer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 对下载表的操作
 * @author zmp
 *
 */
public class HandlerLoadingTable
{

	/**
	 * 查看数据库中是否有数据
	 */
	synchronized public static boolean isHasInfors(int videoId)
	{
		SQLiteDatabase database = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		Cursor cursor = null;
		
		String sql = "select *  from "+LoadingTableConst.DOWNLOAD_TABLE_NAME+" where "+LoadingTableConst.LOADING_VIDEO_ID+"=?";
		cursor = database.rawQuery(sql, new String[] { videoId+"" });

		if(cursor.moveToNext())
		{
			return true;
		}
		
		return false;

	}
	
	
	/**
	 * 保存 下载的具体信息
	 */
	synchronized public static void saveInfos(List<LoadingInfo> infos)
	{
		SQLiteDatabase database = SqliteOpenHelperClass.getInstance().getWritableDatabase();
		for (LoadingInfo info : infos) 
		{
			String sql = "insert into "+LoadingTableConst.DOWNLOAD_TABLE_NAME+"("+LoadingTableConst.THREAD_ID+" , "+LoadingTableConst.START_POS+" , "+LoadingTableConst.END_POS+","+LoadingTableConst.LOADING_VIDEO_ID+","+LoadingTableConst.COMPLETE_SIZE+","+LoadingTableConst.FILE_URL+") values (?,?,?,?,?,?)";
			Object[] bindArgs = { info.getThreadId(), info.getStartPos(), info.getEndPos(), info.getLoadingVideoId(),info.getCompeleteSize(), info.getUrl() };
			database.execSQL(sql, bindArgs);
		}
		
		init();
	}
	
	
	/**
	 * 得到下载具体信息
	 */
	synchronized public static List<LoadingInfo> getInfos(int videoId)
	{
		List<LoadingInfo> list = new ArrayList<LoadingInfo>();
		SQLiteDatabase database = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		
		
//		String sql = "select "+LoadingTableConst.THREAD_ID+", "+LoadingTableConst.START_POS+", "+LoadingTableConst.END_POS+","+LoadingTableConst.COMPLETE_SIZE+","+LoadingTableConst.FILE_URL+" from "+LoadingTableConst.DOWNLOAD_TABLE_NAME+" where "+LoadingTableConst.FILE_URL+"=?";
//		Cursor cursor = database.rawQuery(sql, new String[] { fileUrl });
		
		String sql = "select * from "+LoadingTableConst.DOWNLOAD_TABLE_NAME+" where "+LoadingTableConst.LOADING_VIDEO_ID+" = ?";
		
		Cursor cursor2 = database.rawQuery(sql, new String[]{videoId+""});
		
		
		while (cursor2.moveToNext()) 
		{
			int threadID = cursor2.getInt(cursor2.getColumnIndex(LoadingTableConst.THREAD_ID));
			int startPos = cursor2.getInt(cursor2.getColumnIndex(LoadingTableConst.START_POS));
			int endPos = cursor2.getInt(cursor2.getColumnIndex(LoadingTableConst.END_POS));
//			int videoId = cursor2.getInt(cursor2.getColumnIndex(LoadingTableConst.LOADING_VIDEO_ID));
			int completeSize = cursor2.getInt(cursor2.getColumnIndex(LoadingTableConst.COMPLETE_SIZE));
			String fileUrl = cursor2.getString(cursor2.getColumnIndex(LoadingTableConst.FILE_URL));
			LoadingInfo info = new LoadingInfo(threadID,startPos,endPos,videoId,completeSize,fileUrl);
			list.add(info);
		}
		cursor2.close();
		return list;
	}
	
	
	/**
	 * 更新数据库中的下载信息
	 */
	synchronized public static void updataInfos(int threadId, int compeleteSize, String urlstr)
	{
		SQLiteDatabase database = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		String sql = "update "+LoadingTableConst.DOWNLOAD_TABLE_NAME+" set "+LoadingTableConst.COMPLETE_SIZE+"=? where "+LoadingTableConst.THREAD_ID+"=? and "+LoadingTableConst.FILE_URL+"=?";
		Object[] bindArgs = { compeleteSize, threadId, urlstr };
		database.execSQL(sql, bindArgs);
	}
	
	/**
	 * 下载完成后删除数据库中的数据
	 */
	synchronized public static void delete(int videoId)
	{
		SQLiteDatabase database = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		database.delete(LoadingTableConst.DOWNLOAD_TABLE_NAME, LoadingTableConst.LOADING_VIDEO_ID+"=?", new String[] { videoId+"" });
		database.close();
		init();
	}
	
	/**
	 * 获取视频ID
	 * @return
	 */
	synchronized public static ArrayList<String> getVideoId()
	{
		String sql = "select distinct("+LoadingTableConst.LOADING_VIDEO_ID+") from "+LoadingTableConst.DOWNLOAD_TABLE_NAME;
		SQLiteDatabase database = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		Cursor cursor = database.rawQuery(sql, null);
		
		ArrayList<String> videoIds = new ArrayList<String>();
		while(cursor.moveToNext()) 
		{
			int videoId = cursor.getInt(0);	
			videoIds.add(videoId+"");
		}
		
		return videoIds;
	}
	
	
	private static Map<String, Map<String, ArrayList<VideoInfoVo>>> totalMap;
	
	/**
	 * 获取正在下载的信息
	 * @param subjectId 学科id
	 */
	public static Map<String, ArrayList<VideoInfoVo>> getLoadingVideos(int subjectId)
	{
		if(totalMap == null)
		{
			init();
		}
		
		return totalMap.get(subjectId+"");
	}
	
	synchronized private static void init()
	{
		String sql = "select distinct("+LoadingTableConst.LOADING_VIDEO_ID+") from "+LoadingTableConst.DOWNLOAD_TABLE_NAME;
		SQLiteDatabase database = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		Cursor cursor = database.rawQuery(sql, null);
		totalMap = new HashMap<String, Map<String,ArrayList<VideoInfoVo>>>();
		
		Map<String, ArrayList<VideoInfoVo>> suoYouMap = new HashMap<String, ArrayList<VideoInfoVo>>();
		totalMap.put(SubjectTypeConst.SUOYOU_ID+"", suoYouMap);
		
		
		Map<String, ArrayList<VideoInfoVo>> map ;
		
		while (cursor.moveToNext())
		{
			int videoId = cursor.getInt(0);
			VideoInfoVo videoInfoVo = HandlerVideoInfoTable.getVideoInfoVo(videoId);
			
			VideoSetVo videoSetVo = HandlerVideoSetTable.getVideoSetVo(videoInfoVo.getVideoSetID());
			
			ArrayList<VideoInfoVo> videoInfoVos;
			
			
			if(totalMap.containsKey(videoInfoVo.sujectID+""))
			{
				map = totalMap.get(videoInfoVo.sujectID+"");
			}else 
			{
				map = new HashMap<String, ArrayList<VideoInfoVo>>();
				totalMap.put(videoInfoVo.sujectID+"", map);
			}
			
			
			
 
			if(suoYouMap.containsKey(videoSetVo.id+""))
			{
                  videoInfoVos	= suoYouMap.get(videoSetVo.id+"");			
			}else 
			{
				 videoInfoVos = new ArrayList<VideoInfoVo>();
				 suoYouMap.put(videoSetVo.id+"",videoInfoVos);
			}
			videoInfoVos.add(videoInfoVo);
			
			
			if(map.containsKey(videoSetVo.id+""))
			{
				videoInfoVos = map.get(videoSetVo.id+"");
			}else 
			{
				videoInfoVos = new ArrayList<VideoInfoVo>();
				map.put(videoSetVo.id+"", videoInfoVos);
			}
			
			videoInfoVos.add(videoInfoVo);
		}
	}

}
