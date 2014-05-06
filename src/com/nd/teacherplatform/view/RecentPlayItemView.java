package com.nd.teacherplatform.view;

import java.util.zip.DataFormatException;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.Constants;
import com.nd.teacherplatform.util.DataFormatUtil;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.RecentPlayVo;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 最近播放的ITEM
 * @author zmp
 *
 */
public class RecentPlayItemView extends LinearLayout 
{
    /**
     * 预览图
     */
	private ImageView preViewImg;
	
	/**
	 * 时间进度条
	 */
	private ProgressBar timeProgressBar;
	
	/**
	 * 当前播放的时间
	 */
	private TextView timeInfo;
	
	/**
	 * 视频总时间
	 */
	private TextView totalTime;
	
	/**
	 * 视频名称
	 */
	private TextView videName;
	
	/**
	 * 视频类型
	 */
	private TextView videoType;
	
	/**
	 * 教师名称
	 */
	private TextView tearchName;
	
	/**
	 * 是不是第一个 第一个的话 图片的大个的
	 */
	private boolean isFirst = false;
	
	/**
	 * 最近播放视频的数据结构
	 */
	private VideoInfoVo vo;

	private ImageDownloader imageDownloader;
	
	public RecentPlayItemView(Context context) 
	{
		super(context);
		init(context);
	}

	public RecentPlayItemView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) 
	{
		LayoutInflater.from(context).inflate(R.layout.recent_play_item, this,true);

		preViewImg = (ImageView) findViewById(R.id.preViewImg);
		timeProgressBar = (ProgressBar) findViewById(R.id.timeProgressBar);
		timeInfo = (TextView) findViewById(R.id.timeInfo);
		totalTime = (TextView) findViewById(R.id.totalTime);
		videName = (TextView) findViewById(R.id.videName);
		videoType = (TextView) findViewById(R.id.videoType);
		tearchName = (TextView) findViewById(R.id.tearchName);

		imageDownloader = new ImageDownloader(SingleToolClass.curContext);
	}

	
	/**
	 * 设置是否是第一个元素
	 * 第一个元素显示的是大图
	 * @param isFirst
	 */
	public void setIsFirst(boolean isFirst)
	{
		this.isFirst = isFirst;
	}
	
	/**
	 * 设置 最近播放的数据结构
	 * @param vo
	 */
	public void setRecentPalyVo(VideoInfoVo vo)
	{
		this.vo = vo;
		
		 if(isFirst)
		 {
			 //第一个元素 用大图
			 imageDownloader.download(vo.preViewUrlBig, preViewImg, R.drawable.error_net_big_pic, R.drawable.error_net_big_pic, true);
		 }else 
		 {
			 imageDownloader.download(vo.preViewUrlCom, preViewImg, R.drawable.error_net_pic, R.drawable.error_net_pic, true);
		 }
		 
		 timeInfo.setText(DataFormatUtil.second2Format(vo.playTime));
		 totalTime.setText(DataFormatUtil.second2Format(vo.totalTime));
		 
		 timeProgressBar.setMax((int)vo.totalTime);
		 timeProgressBar.setProgress((int)vo.playTime);
		 
		 videName.setText(vo.videoName);
		 
		 videoType.setText(vo.videoRequireTypeName);
		 
		 tearchName.setText(Constants.TEACHER+vo.teacherName);
	}
	
	/**
	 * 获取 最近播放的数据结构
	 * @return
	 */
	public VideoInfoVo getRecentPlayVo()
	{
		return this.vo;
	}
	

}
