package com.nd.teacherplatform.view;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.util.Utility;
import com.nd.teacherplatform.vo.UnitVideosVo;
import com.nd.teacherplatform.vo.adapter.UnitVideoAdapter;

import android.content.Context;
import android.test.UiThreadTest;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 每一个视频集的
 * 每一个必修的
 * 每一个单元的信息
 * @author zmp
 *
 */
public class UnitVideoView extends LinearLayout 
{

	/**
	 * 单元的标题
	 */
	private TextView unitTitltTextView;
	
	/**
	 * 视频列表
	 */
	private ListView videoListView;
	
	/**
	 * 每一个单元的视频信息
	 */
	private UnitVideosVo unitVideosVo;
	
	
	public UnitVideoView(Context context) 
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.unit_video, this, true);
		unitTitltTextView = (TextView)findViewById(R.id.unitTitle);
		videoListView = (ListView)findViewById(R.id.videoListView);
	}

	public UnitVideoView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
	
	/**
	 * 设置单元的视频信息
	 * @param vo
	 */
	public void setUnitVideosVo(UnitVideosVo vo)
	{
		unitVideosVo = vo;
		
		unitTitltTextView.setText(vo.unitName);
		
		UnitVideoAdapter unitVideoAdapter = new UnitVideoAdapter(vo.videoInfoVos);
		videoListView.setAdapter(unitVideoAdapter);
		
		Utility.setListViewHeightBasedOnChildren(videoListView);
	}
	
	/**
	 * 获取单元的视频信息
	 * @return
	 */
	public UnitVideosVo getUnitVideosVo()
	{
		return unitVideosVo;
	}

}
