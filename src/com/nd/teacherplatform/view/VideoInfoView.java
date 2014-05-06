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
 * ��Ƶ�������<br>
 * ÿһ����������� <br>
 * ÿһ����Ԫ��  <br>
 * ��Ƶ��Ϣ ��ʾ�Ĳ���<br>
 * @author zmp
 *
 */
public class VideoInfoView extends LinearLayout 
{

	/**
	 * Ԥ��ͼƬ
	 */
	private ImageView videoPreView;
	
	/**
	 * ��Ƶ����
	 */
	private TextView videoNameTextView;
	
	/**
	 * ��������
	 */
	private TextView authorNameTextView;
	
	/**
	 * ��ʦ����
	 */
	private TextView teacherNameTextView;
	
	/**
	 *  ��Ƶ��ʱ��
	 */
	private TextView totalTimeTextView;
	
	/**
	 * �����ٶ�
	 */
	private TextView downLoadSpeedtTextView;
	
	/**
	 * ���ص���Ϣ
	 */
	private TextView downLoadInfoTextView;
	
	/**
	 * ���Ű�ť
	 */
	private ImageButton playBtn;
	
	/**
	 * ��Ƶ����Ϣ
	 */
	private VideoInfoVo videoInfoVo;
	
	/**
	 * ͼƬ������
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
	 * ������Ƶ��Ϣ
	 * @param vo
	 */
	public void setVideoInfoVo(VideoInfoVo vo)
	{
		videoInfoVo = vo;
		
		imageDownloader.download(vo.preViewUrlCom, videoPreView, R.drawable.error_net_mid_pic, R.drawable.error_net_mid_pic, true);
		
		videoNameTextView.setText("<<"+vo.videoName+">>");
		authorNameTextView.setText("���ߣ�"+vo.authorName);
		teacherNameTextView.setText("��ʦ��"+vo.teacherName);
		totalTimeTextView.setText(DataFormatUtil.second2Format(vo.totalTime));
		downLoadSpeedtTextView.setText("200K/S");
		downLoadInfoTextView.setText(FileUtils.showFileSize(vo.videoSize*1024));
		
		playBtn.setTag(vo);
	}

	/**
	 * ��ȡ��Ƶ��Ϣ
	 * @return
	 */
	public VideoInfoVo getVideoInfoVo()
	{
		return videoInfoVo;
	}
	
	
}
