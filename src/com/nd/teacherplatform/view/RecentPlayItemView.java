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
 * ������ŵ�ITEM
 * @author zmp
 *
 */
public class RecentPlayItemView extends LinearLayout 
{
    /**
     * Ԥ��ͼ
     */
	private ImageView preViewImg;
	
	/**
	 * ʱ�������
	 */
	private ProgressBar timeProgressBar;
	
	/**
	 * ��ǰ���ŵ�ʱ��
	 */
	private TextView timeInfo;
	
	/**
	 * ��Ƶ��ʱ��
	 */
	private TextView totalTime;
	
	/**
	 * ��Ƶ����
	 */
	private TextView videName;
	
	/**
	 * ��Ƶ����
	 */
	private TextView videoType;
	
	/**
	 * ��ʦ����
	 */
	private TextView tearchName;
	
	/**
	 * �ǲ��ǵ�һ�� ��һ���Ļ� ͼƬ�Ĵ����
	 */
	private boolean isFirst = false;
	
	/**
	 * ���������Ƶ�����ݽṹ
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
	 * �����Ƿ��ǵ�һ��Ԫ��
	 * ��һ��Ԫ����ʾ���Ǵ�ͼ
	 * @param isFirst
	 */
	public void setIsFirst(boolean isFirst)
	{
		this.isFirst = isFirst;
	}
	
	/**
	 * ���� ������ŵ����ݽṹ
	 * @param vo
	 */
	public void setRecentPalyVo(VideoInfoVo vo)
	{
		this.vo = vo;
		
		 if(isFirst)
		 {
			 //��һ��Ԫ�� �ô�ͼ
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
	 * ��ȡ ������ŵ����ݽṹ
	 * @return
	 */
	public VideoInfoVo getRecentPlayVo()
	{
		return this.vo;
	}
	

}
