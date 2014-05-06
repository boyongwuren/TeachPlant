package com.nd.teacherplatform.widget;

import android.R.integer;
import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.nd.pad.onlinevideo.R;
import com.nd.android.pandahome.widget.listener.PandaWidgetListener;
import com.nd.android.pandahome.widget.view.WidgetCommonBackground;
import com.nd.teacherplatform.activity.HomePageActivity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.MediaController; 
import android.widget.VideoView; 

/**
 * <br>Description: 4x1���С�����Ⱦ�������ϵ�View����չ��ViewGroup
 * <br>Author:caizp
 * <br>Date:2013-3-25����11:49:09
 */
public class WidgetEduMedia6x4 extends WidgetCommonBackground implements PandaWidgetListener, OnLongClickListener {
	private Context mContext;
	private static final String TAG = "WidgetEduMedia6x4";
	
	/**
	 * С���ID���������������ϲ�ͬ��С���
	 */
	private int widgetId;
	
	/**
	 * С�����Ԫ��
	 */
	private Integer mPrevXOffest;
	
	private ImageView mImgVideo1;
	private ImageView mImgVideo2;
	private ImageView mImgVideo3;
	private ImageView mImgVideo4;
	
	private ImageView mImgSelected;
	
	// ��Ƶ���ŵİ�ť
	private ImageView mImgPlayController;
	// �Ƿ����ڲ���
	private Boolean bIsPlay = false;
	
	private VideoView mVideoView; 
    private int mPositionWhenPaused = -1; 
    
	private boolean isControllerShow = true;
	private boolean isPaused = false;
	private boolean isFullScreen = false;
	private boolean isOnline = false;
  
	private final static int SCREEN_FULL = 0;
	private final static int SCREEN_DEFAULT = 1;
	
	private final static int PROGRESS_CHANGED = 1;
	
    private MediaController mMediaController; 
    private String mCurVideoURL;
    
    private SeekBar seekBar;
	
	public WidgetEduMedia6x4(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * <br>Description: ���캯����View.inflate()��Ⱦʱ����
	 * <br>Author:caizp
	 * <br>Date:2013-3-25����11:53:38
	 * @param context
	 */
	public WidgetEduMedia6x4(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		Log.e("DemoWidget", "pkg="+mContext.getPackageName());
	}

	/**
     * ִ�а�ť�ɰ��ƶ�����
     * @param from
     * @param to
     */
    private void translate(int from, int to)
    {
        //ͼƬ�ڰ�ť�·�ˮƽ������ʾ
        TranslateAnimation anim = new TranslateAnimation(from, to, 0, 0);
        anim.setDuration(200);
        anim.setFillAfter(true);
        mImgSelected.startAnimation(anim);
    }
    
	/**
	 * <br>Description: ����View
	 * <br>Author:caizp
	 * <br>Date:2013-5-27����5:20:34
	 * @see com.nd.android.pandahome.widget.view.WidgetCommonBackground#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		mCurVideoURL = Environment.getExternalStorageDirectory()+"/onlineVideo/1.mp4";
		
		Log.e(TAG, mCurVideoURL);

		VideoThreed videoThreed = new VideoThreed();
		videoThreed.start();

		seekBar = (SeekBar)findViewById(R.id.seekbar_def);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					mVideoView.seekTo(progress);
				}
			}
		});
        
		initVideoView();
       
		// ��Ƶ���Ž���
        mImgPlayController = (ImageView)findViewById(R.id.imgPlayController);
        mImgPlayController.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!bIsPlay) {
					mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_stop_play));
					playVideo(mCurVideoURL);
				}
				else {
					mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_start_play));
					stopVideo();
				}
				bIsPlay = !bIsPlay;
			}
		});
        
		mImgSelected = (ImageView)findViewById(R.id.imgSelected);
		mPrevXOffest = mImgSelected.getLeft();

		mImgVideo1 = (ImageView)findViewById(R.id.imgVideo1);
		mImgVideo1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int to = mImgVideo1.getLeft();
				
				translate(mPrevXOffest,to);
				mPrevXOffest = to;
				
				mCurVideoURL = Environment.getExternalStorageDirectory()+"/onlineVideo/1�����ϵĺ������ʾ.mp4";
				
				mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_start_play));
				stopVideo();
				bIsPlay = false;
			}
		});
		
		mImgVideo2 = (ImageView)findViewById(R.id.imgVideo2);
		mImgVideo2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int to = mImgVideo2.getLeft();
							
				translate(mPrevXOffest,to);
				mPrevXOffest = to;
				
				mCurVideoURL = Environment.getExternalStorageDirectory()+"/onlineVideo/2��Ԫ���뼯��֮��Ĺ�ϵ.mp4";
				
				mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_start_play));
				stopVideo();
				bIsPlay = false;
			}
		});
		
		mImgVideo3 = (ImageView)findViewById(R.id.imgVideo3);
		mImgVideo3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int to = mImgVideo3.getLeft();
				
				translate(mPrevXOffest,to);
				mPrevXOffest = to;

				mCurVideoURL = Environment.getExternalStorageDirectory()+"/onlineVideo/3�����ϵİ��������.mp4";
				mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_start_play));
				stopVideo();
				bIsPlay = false;
			}
		});
		
		mImgVideo4 = (ImageView)findViewById(R.id.btnStartApp);
		mImgVideo4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_start_play));
				stopVideo();
				bIsPlay = false;
				
				int to = mImgVideo4.getLeft();
				translate(mPrevXOffest,to);
				mPrevXOffest = to;
				
				Intent intent = new Intent(mContext, HomePageActivity.class);
				intent.putExtra("widgetId", widgetId);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
	}
	
	/**
     * <br>Description: ʵ��OnLongClickListener�ӿڣ���д�÷�������֤���widget�����õ���¼�������Ҳ�ɳ����϶�
     * ʹ�÷���
     * TextView showText = (TextView) this.findViewById(R.id.show_text);
     * showText.setOnLongClickListener(this);
     * <br>Author:caizp
     * <br>Date:2013-5-14����5:28:06
     * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
     */
	@Override
	public boolean onLongClick(View v) {
		if(null != getParent()) {
			return ((View)getParent()).performLongClick();
		}
		return false;
	}
	
	/**
	 * <br>Description: С��������������ʱ�Զ����ã����ڸ÷�������ɳ�ʼ����������������Service���������ݵ�
	 * <br>Author:caizp
	 * <br>Date:2013-3-25����11:46:02
	 * @param widgetId �������������ϵĶ��С�����ÿ��С�����ӵ��Ψһ��ʶ
	 */
	@Override
	public void onLoad(int widgetId) {
		this.widgetId = widgetId;
		buildData(widgetId);
	}
	
	/**
	 * <br>Description: С����������ϱ�ɾ��ʱ�Զ����ã�����С���������������
	 * <br>Author:caizp
	 * <br>Date:2013-3-25����11:47:10
	 * @param widgetId
	 */
	@Override
	public void onDestory(int widgetId) {
		clearDataAndRelease(widgetId);
	}
	
	/**
	 * <br>Description: �������ݣ�������ݲ�ͬ��widgetId���治ͬ���Ő_1??7
	 * <br>Author:caizp
	 * <br>Date:2013-5-28����11:09:04
	 * @param widgetId
	 */
	private void buildData(int widgetId) {
		
	}
	
	/**
	 * <br>Description: ������ݣ��ͷ�ͼƬ��Դ(�÷���Ϊʾ��)
	 * <br>Author:caizp
	 * <br>Date:2013-3-25����11:59:29
	 * @param widgetId
	 */
	private void clearDataAndRelease(int widgetId) {
		
	}
	
	private void initVideoView() {
		mVideoView = (VideoView)findViewById(R.id.video_view); 
		mVideoView.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mVideoView.stopPlayback();
				isOnline = false;

				mVideoView.stopPlayback();

				return false;
			}
		});

		mVideoView.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				int i = mVideoView.getDuration();
				
				seekBar.setMax(i);
				mVideoView.start();
				
				myHandler.sendEmptyMessage(PROGRESS_CHANGED);
			}
		});

		mVideoView.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {

			}
		});
	}
	
	private void playVideo(String url)
	{
		Uri uri = Uri.parse(url);
		if (uri != null) {
			//������Ƶ��Ŀ¼��õ�һ��Ƶ
			mVideoView.stopPlayback();
			mVideoView.setVideoURI(uri);
			mVideoView.start(); 
			
			seekBar.setProgress(0);
			
			mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_stop_play));
			bIsPlay = true;
		}
	}
	
	private void stopVideo() {
		mImgPlayController.setImageDrawable(getResources().getDrawable(R.drawable.img_start_play));
		bIsPlay = false;
		mVideoView.stopPlayback();
	}
	
	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) 
            {   
            case PROGRESS_CHANGED:  
				int i = mVideoView.getCurrentPosition();
				seekBar.setProgress(i);
				Log.e(TAG, "play position:"+i);
	            break;
            }
			super.handleMessage(msg);
		}
	};
	
	//��Ƶ����������
	 class VideoThreed extends Thread
	 {
	  public void run()
	  {
	    while (!Thread.currentThread().isInterrupted()) 
	    {    
	    	Log.e(TAG, "play thread is play " + bIsPlay);
	    	if (bIsPlay) 
	    	{
			     if(mVideoView.getCurrentPosition()==mVideoView.getDuration())
			     {
			    	 return;
			     }
			     
	             Message message = new Message();   
	             message.what =PROGRESS_CHANGED;   
	             WidgetEduMedia6x4.this.myHandler.sendMessage(message);   
			}

             
             try 
             {   
                 Thread.sleep(1000);    
             } 
             catch (InterruptedException e) 
             {   
                 Thread.currentThread().interrupt();   
             }   
	    }   
	  }
	 }

}
