package com.nd.teacherplatform.view;

import java.util.ArrayList;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.DownLoadVideoSetVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.adapter.DownLoadListAdapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 下载界面的 二级界面
 * @author zmp
 *
 */
public class DownLoad2Frament extends LinearLayout
{

	private ImageView preViewImg;
	
	private TextView videoSetName;
	
	private ListView listView;
	
	private DownLoadVideoSetVo downLoadVideoSetVo;
	
	private ArrayList<VideoInfoVo> videoInfoVos;
	
	/**
	 * 是否在 已下载页面
	 */
	private boolean isHasDownPage = true;
	
	 
	
	public DownLoad2Frament(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	public DownLoad2Frament(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	public DownLoad2Frament(Context context)
	{
		super(context);
		 LayoutInflater.from(SingleToolClass.curContext).inflate(R.layout.download2page, this, true);
		 preViewImg = (ImageView)findViewById(R.id.preViewPic);
		 videoSetName = (TextView)findViewById(R.id.videoSetName);
		 listView = (ListView)findViewById(R.id.listView);
	}


	 


	/**
	 * 设置视频集 信息
	 * @param downLoadVideoSetVo
	 */
	public void setDownLoadVideoSetVo(DownLoadVideoSetVo downLoadVideoSetVo)
	{
		this.downLoadVideoSetVo = downLoadVideoSetVo;
		SingleToolClass.imageDownloader.download(downLoadVideoSetVo.preViewPicUrl, preViewImg, R.drawable.error_net_pic, R.drawable.error_net_pic, true);
	    videoSetName.setText(downLoadVideoSetVo.videoSetName);
	}


	/**
	 * 设置 视频 数组
	 * @param videoInfoVos
	 */
	public void setVideoInfoVos(ArrayList<VideoInfoVo> videoInfoVos)
	{
		this.videoInfoVos = videoInfoVos;
		listView.setAdapter(new DownLoadListAdapter(videoInfoVos,isHasDownPage));
	}

	/**
	 * 是否在 已下载页面
	 * @param isHasDownPage
	 */
	public void setHasDownPage(boolean isHasDownPage)
	{
		this.isHasDownPage = isHasDownPage;
	}
	


}
