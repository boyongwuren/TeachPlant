package com.nd.teacherplatform.view;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.handler.VideoOnClickListener;
import com.nd.teacherplatform.util.DataFormatUtil;
import com.nd.teacherplatform.util.FileUtils;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 视频集里面的<br>
 * 每一个必修里面的 <br>
 * 每一个单元的  <br>
 * 视频信息 显示的布局<br>
 * @author zmp
 *
 */
public class VideoInfoView extends LinearLayout 
{

	/**
	 * 预览图片
	 */
	private ImageView videoPreView;
	
	/**
	 * 视频名称
	 */
	private TextView videoNameTextView;
	
	/**
	 * 作者名字
	 */
	private TextView authorNameTextView;
	
	/**
	 * 讲师名字
	 */
	private TextView teacherNameTextView;
	
	/**
	 *  视频总时长
	 */
	private TextView totalTimeTextView;
	
	/**
	 * 下载速度
	 */
	private TextView downLoadSpeedtTextView;
	
	/**
	 * 下载的信息
	 */
	private TextView downLoadInfoTextView;
	
	/**
	 * 播放按钮
	 */
	private ImageButton playBtn;
	
	/**
	 * 视频的信息
	 */
	private VideoInfoVo videoInfoVo;
	
	/**
	 * 图片下载器
	 */
	ImageDownloader imageDownloader ;
	
	public VideoInfoView(Context context) 
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.video_info, this, true);
		
		videoPreView = (ImageView)findViewById(R.id.videoPreViewPic);
		videoNameTextView = (TextView)findViewById(R.id.videoName);
		authorNameTextView = (TextView)findViewById(R.id.authorName);
		teacherNameTextView = (TextView)findViewById(R.id.teacherName);
		totalTimeTextView = (TextView)findViewById(R.id.totalTime);
		downLoadSpeedtTextView = (TextView)findViewById(R.id.downLoadSpeed);
		downLoadInfoTextView = (TextView)findViewById(R.id.downLoadInfo);
		playBtn = (ImageButton)findViewById(R.id.playBtn);
		
		imageDownloader = new ImageDownloader(context);
		
		playBtn.setOnClickListener(new VideoOnClickListener());
		
	}

	public VideoInfoView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	
	/**
	 * 设置视频信息
	 * @param vo
	 */
	public void setVideoInfoVo(VideoInfoVo vo)
	{
		videoInfoVo = vo;
		
		imageDownloader.download(vo.preViewUrlCom, videoPreView, R.drawable.error_net_mid_pic, R.drawable.error_net_mid_pic, true);
		
		videoNameTextView.setText("<<"+vo.videoName+">>");
		authorNameTextView.setText("作者："+vo.authorName);
		teacherNameTextView.setText("讲师："+vo.teacherName);
		totalTimeTextView.setText(DataFormatUtil.second2Format(vo.totalTime));
		downLoadSpeedtTextView.setText("200K/S");
		downLoadInfoTextView.setText(FileUtils.showFileSize(vo.videoSize*1024));
		
		playBtn.setTag(vo);
	}

	/**
	 * 获取视频信息
	 * @return
	 */
	public VideoInfoVo getVideoInfoVo()
	{
		return videoInfoVo;
	}
	
	
}
