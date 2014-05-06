package com.nd.teacherplatform.handler;


import java.io.File;

import android.content.ContentValues;
import com.nd.teacherplatform.constant.databaseconst.VideoInfoTableConst;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoInfoTable;
import org.json.JSONException;
import org.json.JSONObject;

import com.nd.teacherplatform.activity.HomeSubjectActivity;
import com.nd.teacherplatform.activity.VideoPlayerActivity;
import com.nd.teacherplatform.constant.FileConst;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.constant.VideoListConst;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.interfaces.LoadVideoUrlOverInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 视频被点击的处理
 * @author zmp
 *
 */
public class VideoOnClickListener implements OnClickListener 
{

	/**
	 * 点击的视频的信息
	 */
	private VideoInfoVo clickVideoVo;
	
	private LoadVideoUrlOverInterface loadVideoUrlOverInterface;
	
	public VideoOnClickListener()
	{
		
	}
	
	public VideoOnClickListener(LoadVideoUrlOverInterface loadVideoUrlOverInterface)
	{
		// TODO Auto-generated constructor stub
		this.loadVideoUrlOverInterface = loadVideoUrlOverInterface;
	}

	@Override
	public void onClick(View v) 
	{
		clickVideoVo = (VideoInfoVo)v.getTag();

		PlayVideoHelp.playVideo(clickVideoVo,loadVideoUrlOverInterface);
	    
	}
	

}
