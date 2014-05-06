package com.nd.teacherplatform.view;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.interfaces.OpenDownLoad2PageInterface;
import com.nd.teacherplatform.util.FileUtils;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.DownLoadVideoSetVo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �����ص� һ������� item
 * @author zmp
 *
 */
public class DownLoadItemView extends LinearLayout
{

	private ImageView videoSetPic;
	
	private TextView videoSetName;
	
	private TextView videoNum;
	
	private TextView videosSize;
	
	private ImageView openListBtn;
	
	private DownLoadVideoSetVo vo;
	
	private OpenDownLoad2PageInterface openDownLoad2PageInterface;
	
	public DownLoadItemView(Context context,OpenDownLoad2PageInterface openDownLoad2PageInterface)
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.download_item, this, true);
		
		videoSetPic = (ImageView) findViewById(R.id.videoSetPic);
		videoSetName = (TextView) findViewById(R.id.videoSetName);
		videoNum = (TextView) findViewById(R.id.videoNum);
		videosSize = (TextView) findViewById(R.id.videosSize);
		openListBtn = (ImageView) findViewById(R.id.openListBtn);
		openListBtn.setOnClickListener(new OpenListClickClass());
		this.openDownLoad2PageInterface = openDownLoad2PageInterface;
	}

	public DownLoadItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DownLoadItemView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * �������ص����ݽṹ ��Ϣ
	 * @param vo
	 */
	public void setDownLoadVideoSetVo(DownLoadVideoSetVo vo)
	{
		this.vo = vo;
		System.out.println("picurl = "+vo.preViewPicUrl);
		SingleToolClass.imageDownloader.download(vo.preViewPicUrl, videoSetPic, R.drawable.error_net_pic, R.drawable.error_net_pic, true);
		videoSetName.setText(this.vo.videoSetName);
		videoNum.setText(vo.hasDownNum+"����Ƶ");
		videosSize.setText(FileUtils.showFileSize(vo.videosSize));
	}
	
	/**
	 * ��ȡ���ص����ݽṹ ��Ϣ
	 * @return
	 */
	public DownLoadVideoSetVo getDownLoadVideoSetVo()
	{
		return vo;
	}
	
	/**
	 * ���������һ������
	 * @author zmp
	 *
	 */
	private class OpenListClickClass implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			openDownLoad2PageInterface.openDownLoad2Page(vo);
		}
		
	}

}
