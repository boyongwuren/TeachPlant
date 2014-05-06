package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.view.VideoInfoView;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 单元视频列表的 适配器
 * @author zmp
 *
 */
public class UnitVideoAdapter extends BaseAdapter
{

	private ArrayList<VideoInfoVo> videoInfoVos;
	
	public UnitVideoAdapter(ArrayList<VideoInfoVo> vos)
	{
         videoInfoVos = vos;
	}

	@Override
	public int getCount()
	{
		return videoInfoVos.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return position;
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		 VideoInfoView videoInfoView = null;
		 
		 if(convertView == null)
		 {
			 convertView = new VideoInfoView(SingleToolClass.curContext); 
		 }
		 
		 videoInfoView = (VideoInfoView)convertView;
		 
		 VideoInfoVo vo = videoInfoVos.get(position);
		 
		 videoInfoView.setVideoInfoVo(vo);
		 
		return videoInfoView;
	}

}
