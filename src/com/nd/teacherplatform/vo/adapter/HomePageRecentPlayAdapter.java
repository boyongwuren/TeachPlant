package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.Constants;
import com.nd.teacherplatform.handler.VideoOnClickListener;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.view.RecentPlayItemView;
import com.nd.teacherplatform.vo.RecentPlayVo;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 首页的 最近播放的适配器
 * @author zmp
 *
 */
public class HomePageRecentPlayAdapter extends BaseAdapter 
{

	private ArrayList<VideoInfoVo> recentPlayVos;
	 
	public HomePageRecentPlayAdapter(ArrayList<VideoInfoVo> recentPlayVos) 
	{
		// TODO Auto-generated constructor stub
		this.recentPlayVos = recentPlayVos;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return recentPlayVos.size();
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		RecentPlayItemView recentPlayItemView;

		if(convertView == null)
		{
		    convertView = new RecentPlayItemView(SingleToolClass.curContext);
			convertView.setOnClickListener(new VideoOnClickListener());
		}
		
		 recentPlayItemView = (RecentPlayItemView) convertView;
		
		 
		 VideoInfoVo vo = recentPlayVos.get(position);
		 
		 if(vo != null)
		 {
			 recentPlayItemView.setRecentPalyVo(vo);
		 }
		 
		 convertView.setTag(vo);
		
		return convertView;
	}

}
