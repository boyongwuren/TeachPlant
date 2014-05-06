package com.nd.teacherplatform.vo.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nd.teacherplatform.constant.databaseconst.VideoInfoTableConst;
import com.nd.teacherplatform.vo.VideoInfoVo;

/**
 * 处理视频信息表的操作
 * 
 * @author zmp
 * 
 */
public class HandlerVideoInfoTable
{

	synchronized  public static void insertDataToVideoInfoTable(VideoInfoVo vo)
    {
		System.out.println("insertDataToVideoInfoTable videoUrl = "+ vo.videoURL);
        HandlerVideoInfoTable.insertDataToVideoInfoTable(vo.id, vo.preViewUrlCom, vo.preViewUrlBig, vo.videoName, vo.sujectID, vo.getVideoSetID(), vo.getVideoRequireType(), vo.teacherName, vo.authorName, vo.totalTime, vo.playTime, vo.lastPlayTime, vo.videoURL, vo.cellID,vo.videoSize);
    }

	/**
	 * @param id
	 * @param preViewPicNom
	 * @param preViewPicBig
	 * @param videoName
	 * @param subjectId
	 * @param videoSetID
	 * @param videoType
	 * @param teacherName
	 * @param authorName
	 * @param totalTime
	 * @param playTime
	 * @param lastPlayTime
	 * @param videoUrl
	 * @param cellID
	 */
	synchronized public static void insertDataToVideoInfoTable(int id, String preViewPicNom, String preViewPicBig, String videoName, int subjectId, int videoSetID, int videoType, String teacherName, String authorName, long totalTime, long playTime, String lastPlayTime, String videoUrl, int cellID,int videoSize)
	{
		System.out.println("insertDataToVideoInfoTable videoUrl = "+videoUrl);
		ContentValues cv = new ContentValues();
		cv.put(VideoInfoTableConst.VIDEOINFO__ID, id);
		cv.put(VideoInfoTableConst.VIDEOINFO__PREVIEWPICNOM, preViewPicNom);
		cv.put(VideoInfoTableConst.VIDEOINFO__PREVIEWPICBIG, preViewPicBig);
		cv.put(VideoInfoTableConst.VIDEOINFO__VIDEONAME, videoName);
		cv.put(VideoInfoTableConst.VIDEOINFO__SUBJECTID, subjectId);
		cv.put(VideoInfoTableConst.VIDEOINFO__VIDEOSETID, videoSetID);
		cv.put(VideoInfoTableConst.VIDEOINFO__REQUIREID, videoType);
		cv.put(VideoInfoTableConst.VIDEOINFO__TEACHERNAME, teacherName);
		cv.put(VideoInfoTableConst.VIDEOINFO__AUTHOR, authorName);
		cv.put(VideoInfoTableConst.VIDEOINFO__TOTALTIME, totalTime);
		if(playTime>0)
		{
			cv.put(VideoInfoTableConst.VIDEOINFO__PLAYTIME, playTime);
		}
		cv.put(VideoInfoTableConst.VIDEOINFO__LASTPLAYTIME, lastPlayTime);
		cv.put(VideoInfoTableConst.VIDEOINFO__CELLID, cellID);
		if("".equals(videoUrl) == false)
		{
			//视频地址有的情况 才更新
			cv.put(VideoInfoTableConst.VIDEOINFO__VIDEO_URL, videoUrl);
		}
		
		if(videoSize>0)
		{
			cv.put(VideoInfoTableConst.VIDEOINFO__VIDEOSIZE, videoSize);
		}

		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();

		if (sqLiteDatabase != null) {
			String sql = "select id from " + VideoInfoTableConst.VIDEOINFO_TABLE + " where id = ?";
			Cursor c = sqLiteDatabase.rawQuery(sql, new String[] { String.valueOf(id) });
			if (c != null) {
				try {
					try {
						if (c.moveToFirst()) {
							cv.remove(VideoInfoTableConst.VIDEOINFO__ID);
							sqLiteDatabase.update(VideoInfoTableConst.VIDEOINFO_TABLE, cv, "id=?", new String[] { String.valueOf(id) });

						} else {
							sqLiteDatabase.insert(VideoInfoTableConst.VIDEOINFO_TABLE, null, cv);
						}
					} finally {
						c.close();
					}
				} catch (Exception e) {
					// Log.e(TAG, "insertDataToVideoInfoTable", e);
				} finally {
				}
			}
		}
	}




	/**
	 * 获取视频信息
	 * 
	 * @param videoId
	 *            视频的Id
	 * @return videoinfovo
	 */
	synchronized public static VideoInfoVo getVideoInfoVo(int videoId)
	{
		SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from " + VideoInfoTableConst.VIDEOINFO_TABLE + " where " + VideoInfoTableConst.VIDEOINFO__ID + "=?", new String[] { videoId + "" });

		if (cursor.moveToNext()) {
			return parseData(cursor);
		}

		return null;
	}

	/**
	 * 数据表 对应到 数据结构
	 * 
	 * @param cursor
	 * @return
	 */
	synchronized private static VideoInfoVo parseData(Cursor cursor)
	{
		int videoId = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__ID));
		String preViewPicNom = cursor.getString(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__PREVIEWPICNOM));
		String preViewPicBig = cursor.getString(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__PREVIEWPICBIG));
		String videoName = cursor.getString(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__VIDEONAME));
		int subjectId = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__SUBJECTID));
		int videoSetId = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__VIDEOSETID));
		String teacherName = cursor.getString(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__TEACHERNAME));
		String authorName = cursor.getString(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__AUTHOR));
		int videoType = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__REQUIREID));
		int cellID = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__CELLID));
		int videoSize = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__VIDEOSIZE));
		long playTime = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__PLAYTIME));// 已经播放的时间
		long totalTime = cursor.getInt(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__TOTALTIME));
		String lastPlayTime = cursor.getString(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__LASTPLAYTIME));
		String videoUrl = cursor.getString(cursor.getColumnIndex(VideoInfoTableConst.VIDEOINFO__VIDEO_URL));

		VideoInfoVo vo = new VideoInfoVo();
		vo.preViewUrlCom = preViewPicNom;
		vo.preViewUrlBig = preViewPicBig;
		vo.videoName = videoName;
		vo.teacherName = teacherName;
		vo.setVideoRequireType(videoType);
		vo.playTime = playTime;
		vo.totalTime = totalTime;
		vo.lastPlayTime = lastPlayTime;
		vo.sujectID = subjectId;
		vo.setVideoSetID(videoSetId);
		vo.authorName = authorName;
		vo.videoURL = videoUrl;
		vo.id = videoId;
		vo.cellID = cellID;
		vo.videoSize = videoSize;

		System.out.println("表视频信息 = "+vo.id+" "+vo.videoName+" "+vo.videoURL);
		return vo;
	}

    /**
     * 更新 视频表
     * @param  videoId 视频id
     * @param cv 更新的字段和数字
     * */
	synchronized  public static void updataTable(int videoId,ContentValues cv)
    {
		System.out.println("更新vo数据"+cv.toString());
        SQLiteDatabase sqLiteDatabase = SqliteOpenHelperClass.getInstance().getReadableDatabase();
        String [] args = {videoId+""};
        sqLiteDatabase.update(VideoInfoTableConst.VIDEOINFO_TABLE,cv, VideoInfoTableConst.VIDEOINFO__ID+" = ?",args);
    }




}
