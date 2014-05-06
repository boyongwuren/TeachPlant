package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.Constants;
import com.nd.teacherplatform.handler.VideoOnClickListener;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.RecentPlayVo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 各学科最近播放的适配器
 * @author zmp
 *
 */
public class RecentPlayAdapter extends BaseAdapter 
{

	private ArrayList<RecentPlayVo> recentPlayVos;
	 
	public RecentPlayAdapter(ArrayList<RecentPlayVo> recentPlayVos) 
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
		// TODO Auto-generated method stub
		if(convertView == null)
		{
			convertView = LayoutInflater.from(SingleToolClass.curContext).inflate(R.layout.recent_play_item, null);
		    
			convertView.setOnClickListener(new VideoOnClickListener());
		}
		
		 ImageView preViewImg = (ImageView)convertView.findViewById(R.id.preViewImg);
		 
		 TextView timeInfo = (TextView)convertView.findViewById(R.id.timeInfo);
		 
		 ProgressBar timeProgressBar = (ProgressBar)convertView.findViewById(R.id.timeProgressBar);
		 
		 TextView videoName = (TextView)convertView.findViewById(R.id.videName);
		 
		 TextView videoType = (TextView)convertView.findViewById(R.id.videoType);
		 
		 TextView teacherName = (TextView)convertView.findViewById(R.id.tearchName);
		 
		 RecentPlayVo vo = recentPlayVos.get(position);
		 
		 if(vo != null)
		 {
			 ImageDownloader imageDownloader = new ImageDownloader(SingleToolClass.curContext);
			 if(position == 0)
			 {
				 //第一个元素 用大图
				 imageDownloader.download(vo.preViewUrlBig, preViewImg, R.drawable.error_net_big_pic, R.drawable.error_net_big_pic, true);
			 }else 
			 {
				 imageDownloader.download(vo.preViewUrlCom, preViewImg, R.drawable.error_net_pic, R.drawable.error_net_pic, true);
			 }
			 
			 timeInfo.setText(vo.playTime+"/"+vo.totalTime);
			 
			 timeProgressBar.setMax(100);
			 timeProgressBar.setProgress(20);
			 
			 videoName.setText(vo.videoName);
			 
			 videoType.setText(vo.videoRequireTypeName);
			 
			 teacherName.setText(Constants.TEACHER+vo.teacherName);
		 }
		 
		 convertView.setTag(vo);
		
		return convertView;
	}

}
