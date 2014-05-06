package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.Constants;
import com.nd.teacherplatform.handler.VideoOnClickListener;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.ReCommendVideoVo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 推荐视频的适配器
 * @author zmp 
 *
 */
public class RecommendVideoAdapter extends BaseAdapter 
{

	private ArrayList<ReCommendVideoVo> reCommendVideoVos;
	
	public RecommendVideoAdapter(ArrayList<ReCommendVideoVo> reCommendVideoVos)
	{
		this.reCommendVideoVos = reCommendVideoVos;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return reCommendVideoVos.size();
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
		 if(convertView == null)
		 {
			 convertView = LayoutInflater.from(SingleToolClass.curContext).inflate(R.layout.recommend_video_item, null);
		     convertView.setOnClickListener(new VideoOnClickListener());
		 }
		 
		 ImageView preViewImg = (ImageView)convertView.findViewById(R.id.preViewImg);
		 
		 TextView videoName = (TextView)convertView.findViewById(R.id.videName);
		 
		 TextView videoType = (TextView)convertView.findViewById(R.id.videoType);
		 
		 TextView teacherName = (TextView)convertView.findViewById(R.id.tearchName);
		 
		 ReCommendVideoVo vo = reCommendVideoVos.get(position);
		 
		 if(vo != null)
		 {
			 ImageDownloader imageDownloader = new ImageDownloader(SingleToolClass.curContext);
			 imageDownloader.download(vo.preViewUrlCom, preViewImg, R.drawable.error_net_pic, R.drawable.error_net_pic, true);
			 
			 videoName.setText(vo.videoName);//视频名字

             videoType.setText(vo.videoRequireTypeName);//视频类型
             
             teacherName.setText(Constants.TEACHER+vo.teacherName);//视频讲师
		 }
		 
		 convertView.setTag(vo);
		 
		return convertView;
	}

}
