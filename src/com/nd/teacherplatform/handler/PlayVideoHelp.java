package com.nd.teacherplatform.handler;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.nd.teacherplatform.activity.VideoPlayerActivity;
import com.nd.teacherplatform.constant.FileConst;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.constant.VideoListConst;
import com.nd.teacherplatform.constant.databaseconst.VideoInfoTableConst;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.interfaces.LoadVideoUrlOverInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoInfoTable;

/**
 * ������Ƶ
 * @author zmp
 *
 */
public class PlayVideoHelp
{

	private static VideoInfoVo clickVideoVo;
	
	private static LoadVideoUrlOverInterface loadVideoUrlOverInterface;
	
	public static void playVideo(VideoInfoVo vo,LoadVideoUrlOverInterface loadUrlOverInterface)
	{
		clickVideoVo = vo;
		loadVideoUrlOverInterface = loadUrlOverInterface;
		if("".equals(clickVideoVo.videoURL) == false)
		{
			//�Ѿ�����Ƶ��ַ��
			doPlayVideo(clickVideoVo.videoURL);
		}else 
		{
			//��û����Ƶ��ַ
			String paramString = "{\"op\":\"Video.getdownloadurl\",\"sid\":\"\", \"uid\":\"\",\"videoId\":"+clickVideoVo.id+",\"terminal\":1,\"source\":1,\"deviceId\":\""+SingleToolClass.getServerId()+"\",\"username\":\"\",\"module\":1}";
			LoadServerAPIHelp.loadServerApi(URLConst.GET_VIDEO_PLAY_URL, new LoadApiBackClass(), paramString);
		}
	}
	
	/**
	 * ���� ��Ƶ���ŵĵ�ַ ���
	 * @author zmp
	 *
	 */
	private static class LoadApiBackClass implements LoadApiBackInterface
	{

		@Override
		public void loadApiBackString(String jsonString)
		{
			String urlString = "";
			try 
			{
				JSONObject jsonObject = new JSONObject(jsonString);
				jsonObject = jsonObject.getJSONObject("data");
				urlString = jsonObject.getString("url");
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}

            //��ȡ������Ƶ��Ӧ�����ص�ַ�ˡ��������ݱ�
            ContentValues cv = new ContentValues();
            cv.put(VideoInfoTableConst.VIDEOINFO__VIDEO_URL,urlString);
            HandlerVideoInfoTable.updataTable(clickVideoVo.id,cv);
			
			doPlayVideo(urlString);
			 
		}
		
	}
	
	private static void doPlayVideo(String videoUrl)
	{
		clickVideoVo.videoURL = videoUrl;
		String localUrlString = Environment.getExternalStorageDirectory().getPath()+FileConst.VIDEO_PATH+File.separator+clickVideoVo.getSubjectName()+File.separator+clickVideoVo.videoSetName+File.separator+clickVideoVo.videoName+FileConst.FILE_FORMAT;
		File file = new File(localUrlString);
		if(file.exists())
		{
			clickVideoVo.videoURL = localUrlString;
		}
		
		
		if(loadVideoUrlOverInterface != null)
		{
			loadVideoUrlOverInterface.loadVideoUrlOver(clickVideoVo);
		}else 
		{
			Intent intent = new Intent(SingleToolClass.curContext,VideoPlayerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			Bundle bundle = new Bundle();
			bundle.putSerializable(VideoListConst.VIDEO_PLAY_KEY, clickVideoVo);
			intent.putExtras(bundle);
			SingleToolClass.curContext.startActivity(intent);
		}
	}


}
