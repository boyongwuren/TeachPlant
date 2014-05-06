package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.handler.VideoOnClickListener;
import com.nd.teacherplatform.util.DataFormatUtil;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 各个学科的最近播放的适配器
 * @author zmp
 *
 */
public class SubjectRecentPlayAdapter extends BaseAdapter {

	private ArrayList<VideoInfoVo> recentPlayVos;
	
	public SubjectRecentPlayAdapter(ArrayList<VideoInfoVo> recentPlayVos) 
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
			convertView = LayoutInflater.from(SingleToolClass.curContext).inflate(R.layout.subject_recent_play_item, null);
		    convertView.setOnClickListener(new VideoOnClickListener());
		}
		
		VideoInfoVo vo = recentPlayVos.get(position);
		
		ImageView previewPic = (ImageView)convertView.findViewById(R.id.previewPic);
		
		TextView videoSetName = (TextView)convertView.findViewById(R.id.videoSetName);
		
		TextView videoType = (TextView)convertView.findViewById(R.id.videoType);

		TextView videoName = (TextView)convertView.findViewById(R.id.videoName);

		TextView totalTime = (TextView)convertView.findViewById(R.id.totalTime);

		TextView playTime = (TextView)convertView.findViewById(R.id.playTime);
		
		ProgressBar timeProgressBar = (ProgressBar)convertView.findViewById(R.id.timeProgressBar);
		
		if(vo != null)
		{
			ImageDownloader imageDownloader = new ImageDownloader(SingleToolClass.curContext);
			imageDownloader.download(vo.preViewUrlCom, previewPic, R.drawable.error_net_big_pic, R.drawable.error_net_big_pic, true);
			
			videoSetName.setText(vo.videoSetName);
			
			videoType.setText(vo.videoRequireTypeName);
			
			videoName.setText("<<"+vo.videoName+">>");
			
			totalTime.setText(DataFormatUtil.second2Format(vo.totalTime));
			
			playTime.setText(DataFormatUtil.second2Format(vo.playTime));
			
			timeProgressBar.setMax((int)vo.totalTime);
			timeProgressBar.setProgress((int)vo.playTime);
		}
		
		convertView.setTag(vo);
		
		return convertView;
	}

}
