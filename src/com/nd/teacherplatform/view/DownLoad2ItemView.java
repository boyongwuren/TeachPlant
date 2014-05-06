package com.nd.teacherplatform.view;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.download.DownLoaderHelp;
import com.nd.teacherplatform.handler.PlayVideoHelp;
import com.nd.teacherplatform.interfaces.SendLoadProgressInterface;
import com.nd.teacherplatform.util.FileUtils;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nd.teacherplatform.vo.adapter.DownLoadListAdapter.DelectOneVideoInfoInterface;
import com.nd.teacherplatform.vo.sqlite.HandlerHasDownTable;
import com.nd.teacherplatform.vo.sqlite.HandlerLoadingTable;

/**
 * 已下载页面的二级页面 每一条item
 * @author zmp
 *
 */
public class DownLoad2ItemView extends LinearLayout
{

	private TextView subjectName;

	private TextView requireName;

	private TextView videoName;
	
	private TextView teacherName;
	
	private TextView videoSize;

	private TextView speedTxt;

	private ImageButton playBtn;

	private ImageButton downLoadBtn;

	private ImageButton pauseBtn;
	
	private ProgressBar downLoadingProgress;
	
	private VideoInfoVo vo;
	
	/**
	 * 是否是在已 下载页面下面的item
	 */
	private boolean isHasDownItem = true;
	
	private DelectOneVideoInfoInterface delectOneVideoInfoInterface;

	public DownLoad2ItemView(Context context,DelectOneVideoInfoInterface delectOneVideoInfoInterface)
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.download2_item, this, true);
		
		subjectName = (TextView) findViewById(R.id.subjectName);
		requireName = (TextView) findViewById(R.id.requireName);
		videoName = (TextView) findViewById(R.id.videoName);
		teacherName = (TextView) findViewById(R.id.teacherName);
		videoSize = (TextView) findViewById(R.id.videoSize);
		speedTxt = (TextView) findViewById(R.id.speedTxt);
		playBtn = (ImageButton) findViewById(R.id.playBtn);
		pauseBtn = (ImageButton) findViewById(R.id.pauseBtn);
		downLoadBtn = (ImageButton) findViewById(R.id.downLoadBtn);
		downLoadingProgress = (ProgressBar) findViewById(R.id.downLoadingProgress);
		this.delectOneVideoInfoInterface = delectOneVideoInfoInterface;
		downLoadBtn.setOnClickListener(new PlayBtnClickClass());
		pauseBtn.setOnClickListener(new PlayBtnClickClass());
	}

	public DownLoad2ItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public DownLoad2ItemView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	
	public VideoInfoVo getVo()
	{
		return vo;
	}

	public void setVo(VideoInfoVo vo)
	{
		this.vo = vo;
		subjectName.setText("【"+vo.getSubjectName()+"】");
		requireName.setText(vo.videoRequireTypeName);
		videoName.setText("《"+vo.videoName+"》");
		teacherName.setText("讲师："+vo.teacherName);
		videoSize.setText(FileUtils.showFileSize(vo.videoSize));
		playBtn.setOnClickListener(new PlayBtnClickClass());
		
		if(isHasDownItem == false)
		{
			boolean isDownload = DownLoaderHelp.isDownLoad(vo.id);
			changePauseBtn(isDownload);
		}
	}
	
	/**
	 * 暂停和下载 切换
	 * @param isLoading
	 */
	private void changePauseBtn(boolean isLoading)
	{
		if(isLoading)
		{
			downLoadBtn.setVisibility(View.INVISIBLE);
			pauseBtn.setVisibility(View.VISIBLE);
		}else 
		{
			downLoadBtn.setVisibility(View.VISIBLE);
			pauseBtn.setVisibility(View.INVISIBLE);
		}
	}
	
	
	/**
	 * 点击了播放按钮
	 * @author zmp
	 *
	 */
	private class PlayBtnClickClass implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.playBtn:
					PlayVideoHelp.playVideo(vo,null);
					break;

				case R.id.downLoadBtn:
					DownLoaderHelp.setDownload(vo.id);
					changePauseBtn(true);
					break;

				case R.id.pauseBtn:
					DownLoaderHelp.setPause(vo.id);
					changePauseBtn(false);
					break;

				default:
					break;
			}
		}
		
	}


	public boolean isHasDownItem()
	{
		return isHasDownItem;
	}

    private SendLoadProgressInterface sendLoadProgressInterface;
	
	/**
	 * 是否是在已 下载页面下面的item
	 * @param isHasDownItem
	 */
	public void setHasDownItem(boolean isHasDownItem)
	{
		this.isHasDownItem = isHasDownItem;
		if(isHasDownItem)
		{
			downLoadingProgress.setVisibility(View.INVISIBLE);
			speedTxt.setVisibility(View.INVISIBLE);
			downLoadBtn.setVisibility(View.INVISIBLE);
			pauseBtn.setVisibility(View.INVISIBLE);
		}else 
		{
			downLoadingProgress.setVisibility(View.VISIBLE);
			speedTxt.setVisibility(View.VISIBLE);
			downLoadBtn.setVisibility(View.VISIBLE);
			pauseBtn.setVisibility(View.VISIBLE);
            sendLoadProgressInterface = new SendLoadProgressClass();
            DownLoaderHelp.slpis.add(sendLoadProgressInterface);
		}
	}

    private class SendLoadProgressClass implements SendLoadProgressInterface
    {
    	private long lastTime = -1;
    	private long lastComplete = -1;
        @Override
        public void setLoadFileProgress(int videoId, int hasComplete, int totalSize)
        {
             if(videoId == vo.id)
             {
                 //是这个视频
                 downLoadingProgress.setMax(totalSize);
                 downLoadingProgress.setProgress(hasComplete);
                 vo.videoSize = hasComplete;
                 videoSize.setText(FileUtils.showFileSize(vo.videoSize));
                 
                 //显示加载速度
                 long intervalTime = 0;
                 long intervalComplete = 0;
                 if(lastTime<0)
                 {
                	 lastTime = System.currentTimeMillis();
                	 lastComplete = hasComplete;
                 }else 
                 {
					 intervalTime = System.currentTimeMillis() - lastTime;
					 intervalComplete = hasComplete - lastComplete;
					 
					 lastTime = System.currentTimeMillis();
					 lastComplete = hasComplete;
					 
					 speedTxt.setText(FileUtils.showFileSize(intervalComplete*1000/intervalTime)+"/s");
				 }
                 
                  
                 
                 
                 if(hasComplete>=totalSize)
                 {
                     //视频下载完成
                     if(sendLoadProgressInterface != null)
                     {
                         DownLoaderHelp.slpis.remove(sendLoadProgressInterface);
                         delectOneVideoInfoInterface.delectOneVideoInfo(videoId);
                     }
                 }
             }
        }
    }
	

}
