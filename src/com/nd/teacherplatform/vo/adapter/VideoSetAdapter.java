package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.Constants;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.interfaces.VideoSetOnClickInterface;
import com.nd.teacherplatform.util.FileUtils;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.UnitVideosVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.VideoSetVo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 各个学科的 视频集 适配器
 * @author zmp
 *
 */
public class VideoSetAdapter extends BaseAdapter 
{

	private ArrayList<VideoSetVo> videoSetVos;
	
	private VideoSetOnClickInterface videoSetOnClickInterface;
	
	public VideoSetAdapter(ArrayList<VideoSetVo> videoSetVos,VideoSetOnClickInterface videoSetOnClickInterface) 
	{
		// TODO Auto-generated constructor stub
		this.videoSetVos = videoSetVos;
		this.videoSetOnClickInterface = videoSetOnClickInterface;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return videoSetVos.size();
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
			convertView = LayoutInflater.from(SingleToolClass.curContext).inflate(R.layout.subject_page_item, null);
			convertView.setOnClickListener(new VideoSetClickListener());
		}
		
		VideoSetVo vo = videoSetVos.get(position);
		
		ImageView preViewPic = (ImageView)convertView.findViewById(R.id.preViewPic);
		
		TextView videoSetName = (TextView)convertView.findViewById(R.id.videoSetName);
		
		TextView totalVideoNum = (TextView)convertView.findViewById(R.id.totalVideoNum);
		
		TextView hasDownNumTextView = (TextView)convertView.findViewById(R.id.hasDownNum);
		
		ProgressBar downNumProgressBar = (ProgressBar)convertView.findViewById(R.id.downNumProgressBar);
		
		if(vo != null)
		{
			ImageDownloader imageDownloader = new ImageDownloader(SingleToolClass.curContext);
			imageDownloader.download(vo.preViewPicUrl, preViewPic, R.drawable.error_net_pic, R.drawable.error_net_pic, true);
			
			videoSetName.setText(vo.videoSetName);
			
			totalVideoNum.setText(vo.totalVideoNum+Constants.TOTAL_VIDEO_NUM);
			
			hasDownNumTextView.setText(Constants.HAS_DOWN_LOAD_VIDEO+vo.hasDownNum+"/"+vo.totalVideoNum);
			
			downNumProgressBar.setMax(vo.totalVideoNum);
			downNumProgressBar.setProgress(vo.hasDownNum);
		}
		
		convertView.setTag(vo);
		
		return convertView;
	}
	
	/**
	 * 视频集被点击的事件
	 * @author zmp
	 *
	 */
	private class VideoSetClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			//点击了视频集 打开视频集的界面
			 VideoSetVo vo = (VideoSetVo)v.getTag();
			 videoSetOnClickInterface.videoSetOnClick(vo.id);

		}
		
	}
	

	

	

}
