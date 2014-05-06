package com.nd.teacherplatform.activity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONStringer;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nd.pad.onlinevideo.R;
import com.nd.studentpad.padserviceapi.PadServiceApi;
import com.nd.teacherplatform.animation.ComposerButtonAnimation;
import com.nd.teacherplatform.animation.ComposerButtonGrowAnimationIn;
import com.nd.teacherplatform.animation.InOutAnimation;
import com.nd.teacherplatform.constant.ActionConst;
import com.nd.teacherplatform.constant.Constants;
import com.nd.teacherplatform.constant.VideoListConst;
import com.nd.teacherplatform.constant.databaseconst.VideoInfoTableConst;
import com.nd.teacherplatform.download.DownLoaderHelp;
import com.nd.teacherplatform.interfaces.BackVideoKnowLedgleInterface;
import com.nd.teacherplatform.interfaces.BackVideoListInterface;
import com.nd.teacherplatform.net.GetKnowLedgeList;
import com.nd.teacherplatform.net.GetVideoList;
import com.nd.teacherplatform.vitamio.LibsChecker;
import com.nd.teacherplatform.vo.KnowLedgeVo;
import com.nd.teacherplatform.vo.UnitVideosVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.adapter.VideoGistAdapter;
import com.nd.teacherplatform.vo.adapter.VideoGistAdapter.gistItemClickListener;
import com.nd.teacherplatform.vo.adapter.VideoUnitAdapter;
import com.nd.teacherplatform.vo.adapter.VideoUnitAdapter.courseUnitItemClickListener;
import com.nd.teacherplatform.vo.sqlite.HandlerCollectTable;
import com.nd.teacherplatform.vo.sqlite.HandlerRecentPlayTable;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoInfoTable;
import com.nd.teacherplatform.widget.MediaController;
import com.nd.teacherplatform.widget.MediaController.btnSoundClickListener;
import com.nd.teacherplatform.widget.MediaController.onMediaControlsVisibleChangeListener;
import com.nd.teacherplatform.widget.VideoView;

public class VideoPlayerActivity extends BaseActivity implements
		OnCompletionListener, OnInfoListener {

	private static final String TAG = "VideoPlayerActivity"; // 调试标签
	private String mVideoPath = ""; // 视频链接或路径
	private String mVideoName = ""; // 视频标题
	private String mVideoSetNameString = ""; // 视频集类型 如：海淀视频
	private String mVideoTypeString = ""; // 视频类型 如：必修一
	private VideoView mVideoView;
	private VideoInfoVo videoInfo = null;

	private AudioManager mAudioManager;
	/** 最大声音 */
	private int mMaxVolume;
	/** 当前声音 */
	private int mVolume = -1;

	/** 当前亮度 */
	// private float mBrightness = -1f;

	/** 当前缩放模式 */
	private int mLayout = VideoView.VIDEO_LAYOUT_ZOOM;

	private GestureDetector mGestureDetector;
	private MediaController mMediaController;
	private View mLoadingView;
	private LinearLayout layout_video_title;

	// 右下角操作区按钮相关
	private RelativeLayout layout_video_op;
	private ViewGroup composerButtonsWrapper;
	private View composerButtonsShowHideButtonIcon;
	private View composerButtonsShowHideButton;
	private Animation rotateStoryAddButtonIn;
	private Animation rotateStoryAddButtonOut;
	private boolean btnOpIsShowing = false;

	// 声音控制相关
	private SeekBar pb_video_sound;
	private LinearLayout layout_video_sound;
	private TextView tv_video_sound;

	// 左边列表相关
	private LinearLayout layout_video_list;
	private ListView lv_video_gist, lv_video_list;
	private TextView tv_videolist_title;

	// private VideoUnitAdapter videoUnitAdapter;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);

		// 检测Vitamio是否解压解码包
		if (!LibsChecker.checkVitamioLibs(this, R.string.init_decoders)) //
		{
			Toast.makeText(this, R.string.decoerserror, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		// 看视频
		sendRpcAction(Constants.PadServiceApi_Action_TeacherPlat,
				Constants.PadServiceApi_Key_TeacherPlat,
				Constants.PadServiceApi_Flag_Begin);

		setContentView(R.layout.video_play_activity);

		videoInfo = (VideoInfoVo) this.getIntent().getSerializableExtra(
				VideoListConst.VIDEO_PLAY_KEY);
		if (videoInfo != null) {
			mVideoPath = videoInfo.videoURL;
			// mVideoPath =
			// "http://km.ndea.91.com/%2fupload%2fnews%2f2013%2f10_10%2f2013101010223062.wmv";
			// mVideoPath =
			// "http://121.207.254.170:8081/%E9%AB%98%E4%B8%AD%E5%8C%96%E5%AD%A6%20%20%E8%9B%8B%E7%99%BD%E8%B4%A8_%E8%87%AA%E5%AE%9A%E4%B9%89%E8%BD%AC%E7%A0%81_640x480.mp4";
			// mVideoPath = "http://121.207.254.170:8081/13.MP4";
			mVideoName = "《" + videoInfo.videoName + "》";
			if (null != videoInfo.videoSetName
					&& !videoInfo.videoSetName.isEmpty())
				mVideoSetNameString = "[" + videoInfo.videoSetName + "]";
			if (null != videoInfo.videoRequireTypeName
					&& !videoInfo.videoRequireTypeName.isEmpty())
				mVideoTypeString = "(" + videoInfo.videoRequireTypeName + ")";
		}

		// 声音
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		initView();
	}

	private void initView() {
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		// 绑定事件
		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnInfoListener(this);
		// 绑定数据
		if (mVideoPath.startsWith("http:"))
			mVideoView.setVideoURI(Uri.parse(mVideoPath));
		else
			mVideoView.setVideoPath(mVideoPath);
		// mVideoView.setVideoRaw(R.raw.test_video);

		mLoadingView = findViewById(R.id.video_loading);
		mLoadingView.setVisibility(View.VISIBLE);

		layout_video_title = (LinearLayout) findViewById(R.id.layout_video_title);
		layout_video_title.setVisibility(View.INVISIBLE);
		// 声音
		layout_video_op = (RelativeLayout) findViewById(R.id.layout_video_op);
		layout_video_op.setVisibility(View.INVISIBLE);
		layout_video_op.setTag(false);
		layout_video_sound = (LinearLayout) findViewById(R.id.layout_video_sound);
		layout_video_sound.setVisibility(View.GONE);
		pb_video_sound = (SeekBar) findViewById(R.id.pb_video_sound);
		tv_video_sound = (TextView) findViewById(R.id.tv_video_sound);
		setSoundInfo();
		pb_video_sound.setOnSeekBarChangeListener(soundSeekBarChange);

		mMediaController = new MediaController(this);
		mMediaController.setFileName(mVideoName);
		mMediaController.setMediaControlsVisibleChangeListener(mcVisibleChange);
		mMediaController.setBtnSoundClickListener(mcBtnSoundClick);
		mVideoView.setMediaController(mMediaController);
		mVideoView.requestFocus();

		// 初始化操作区按钮
		initVideoOp();

		TextView tv_video_title = (TextView) findViewById(R.id.tv_video_title);
		tv_video_title.setText(mVideoName);

		TextView tv_video_type = (TextView) findViewById(R.id.tv_video_type);
		tv_video_type.setText(mVideoSetNameString + mVideoTypeString);

		ImageButton btn_video_back = (ImageButton) findViewById(R.id.btn_video_back);
		btn_video_back.setOnClickListener(btnVideoBackOnClick);

		lv_video_gist = (ListView) findViewById(R.id.lv_video_gist);
		lv_video_list = (ListView) findViewById(R.id.lv_video_list);
		lv_video_list.setVisibility(View.GONE);
		lv_video_gist.setVisibility(View.GONE);
		lv_video_list.setTag(0);
		lv_video_gist.setTag(0);

		// videoUnitAdapter = new VideoUnitAdapter(
		// new ArrayList<UnitVideosVo>(), this);
		// lv_video_list.setAdapter(videoUnitAdapter);

		layout_video_list = (LinearLayout) findViewById(R.id.layout_video_list);
		layout_video_list.setVisibility(View.GONE);
		tv_videolist_title = (TextView) findViewById(R.id.tv_videolist_title);

		mGestureDetector = new GestureDetector(this, new MyGestureListener());
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	private void initVideoOp() {
		composerButtonsWrapper = (ViewGroup) findViewById(R.id.composer_buttons_wrapper);
		composerButtonsShowHideButton = findViewById(R.id.composer_buttons_show_hide_button);
		composerButtonsShowHideButtonIcon = findViewById(R.id.composer_buttons_show_hide_button_icon);
		rotateStoryAddButtonIn = AnimationUtils.loadAnimation(this,
				R.anim.rotate_story_add_button_in);
		rotateStoryAddButtonOut = AnimationUtils.loadAnimation(this,
				R.anim.rotate_story_add_button_out);

		composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleComposerButtons();
			}
		});
		for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
			composerButtonsWrapper.getChildAt(i).setOnClickListener(
					btnOperOnClick);
		}
		composerButtonsShowHideButton
				.startAnimation(new ComposerButtonGrowAnimationIn(200));
	}

	private void toggleComposerButtons() {
		if (!btnOpIsShowing) {
			ComposerButtonAnimation.startAnimations(
					this.composerButtonsWrapper, InOutAnimation.Direction.IN);
			this.composerButtonsShowHideButtonIcon
					.startAnimation(this.rotateStoryAddButtonIn);
		} else {
			ComposerButtonAnimation.startAnimations(
					this.composerButtonsWrapper, InOutAnimation.Direction.OUT);
			this.composerButtonsShowHideButtonIcon
					.startAnimation(this.rotateStoryAddButtonOut);
		}
		btnOpIsShowing = !btnOpIsShowing;
	}

	// 播放控制条--显示或隐藏状态变化时回调
	private onMediaControlsVisibleChangeListener mcVisibleChange = new onMediaControlsVisibleChangeListener() {
		public void onVisibleChange(int visible) {
			// 首次设置跟随控制条
			if (layout_video_op.getTag().equals(false)) {
				RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layout_video_op
						.getLayoutParams();
				lp.bottomMargin = mMediaController.getControlerHeight();
				layout_video_op.setLayoutParams(lp);
				layout_video_op.setTag(true);
			}
			layout_video_op.setVisibility(visible);
			layout_video_title.setVisibility(visible);
			if (visible == View.GONE || visible == View.INVISIBLE) {
				layout_video_sound.setVisibility(View.GONE);
				// layout_video_list.setVisibility(View.GONE);
			}
		}
	};

	// 播放控制条--声音按钮点击回调
	private btnSoundClickListener mcBtnSoundClick = new btnSoundClickListener() {
		public void onClick() {
			setSoundInfo();
			layout_video_sound.setVisibility(View.VISIBLE);
		}
	};

	private void setSoundInfo() {
		mMaxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		pb_video_sound.setMax(mMaxVolume);
		pb_video_sound.setProgress(mVolume);
		setSoundText();
	}

	private void setSoundText() {
		String txt = "";
		if (mMaxVolume > 0)
			txt = String.format("%.0f", mVolume * 100.00 / mMaxVolume) + "%";
		tv_video_sound.setText(txt);
	}

	private SeekBar.OnSeekBarChangeListener soundSeekBarChange = new SeekBar.OnSeekBarChangeListener() // 调音监听器
	{
		public void onProgressChanged(SeekBar arg0, int progress,
				boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,
					0);
			mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前值
			setSoundText();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};

	// TODO 操作区按钮点击
	private ImageButton.OnClickListener btnOperOnClick = new ImageButton.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.video_collect:
				HandlerCollectTable.insertToCollectTable(-1, videoInfo.id);
				Toast.makeText(VideoPlayerActivity.this,
						R.string.video_list_add_collect, Toast.LENGTH_SHORT)
						.show();
				break;
			case R.id.video_download:
				DownLoaderHelp.downLoadHelp(videoInfo);
				Toast.makeText(VideoPlayerActivity.this,
						R.string.video_list_add_download, Toast.LENGTH_SHORT)
						.show();
				break;
			case R.id.video_gist:
				if (layout_video_list.getVisibility() == View.VISIBLE) {
					layout_video_list.setVisibility(View.GONE);
				} else {
					if (null != lv_video_gist.getTag()
							&& lv_video_gist.getTag().equals(1)) {
						lv_video_gist.setVisibility(View.VISIBLE);
						layout_video_list.setVisibility(View.VISIBLE);
					} else {
						GetKnowLedgeList.getKnowLedgeList(videoInfo.id,
								new BackVideoGistClass());
					}
				}
				break;
			case R.id.video_list:
				if (layout_video_list.getVisibility() == View.VISIBLE) {
					layout_video_list.setVisibility(View.GONE);
				} else {
					if (null != lv_video_list.getTag()
							&& lv_video_list.getTag().equals(1)) {
						lv_video_list.setVisibility(View.VISIBLE);
						layout_video_list.setVisibility(View.VISIBLE);
					} else {
						GetVideoList.getVideoList(videoInfo.sujectID,
								videoInfo.getVideoSetID(),
								videoInfo.getVideoRequireType(),
								new BackVideoListClass());
					}
				}
				break;
			}
		}
	};

	// 视频要点
	private class BackVideoGistClass implements BackVideoKnowLedgleInterface {
		public void setKonwLedgle(ArrayList<KnowLedgeVo> vos) {
			if (vos.size() > 0) {
				VideoGistAdapter videoGistAdapter = new VideoGistAdapter(vos,
						VideoPlayerActivity.this);
				videoGistAdapter.setGistItemClickListener(gistItemClick);
				tv_videolist_title.setText(R.string.video_gisttitle);
				lv_video_list.setVisibility(View.GONE);
				lv_video_gist.setAdapter(videoGistAdapter);
				lv_video_gist.setTag(1);
				lv_video_gist.setVisibility(View.VISIBLE);
				layout_video_list.setVisibility(View.VISIBLE);
			} else {
				Toast.makeText(VideoPlayerActivity.this, R.string.video_nogist,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// TODO 视频要点列表项点击回调--跳至当前要点所在时间
	private gistItemClickListener gistItemClick = new gistItemClickListener() {
		public void onClick(int time) {
			if (mVideoView != null)
				mVideoView.seekTo(time);
		}
	};

	// 视频列表
	private class BackVideoListClass implements BackVideoListInterface {
		@Override
		public void setVideoList(ArrayList<UnitVideosVo> uvos) {
			// videoUnitAdapter.setAdapterData(uvos);

			if (uvos.size() > 0) {
				VideoUnitAdapter videoUnitAdapter = new VideoUnitAdapter(uvos,
						VideoPlayerActivity.this);
				videoUnitAdapter
						.setCourseUnitItemClickListener(courseUnitItemClick);
				tv_videolist_title.setText(VideoPlayerActivity.this
						.getResources().getString(
								R.string.video_list_coursetitle)
						+ " " + videoInfo.videoRequireTypeName);
				lv_video_gist.setVisibility(View.GONE);
				lv_video_list.setAdapter(videoUnitAdapter);
				lv_video_list.setTag(1);
				lv_video_list.setVisibility(View.VISIBLE);
				layout_video_list.setVisibility(View.VISIBLE);
			} else {
				Toast.makeText(VideoPlayerActivity.this,
						R.string.video_list_nocourse, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	// 视频单元项--课程项点击回调-->播放点击的课程
	private courseUnitItemClickListener courseUnitItemClick = new courseUnitItemClickListener() {
		public void onClick(String videoURL) {
			if (mVideoView != null) {
				mVideoPath = videoURL;
				// 绑定数据
				if (mVideoPath.startsWith("http:"))
					mVideoView.setVideoURI(Uri.parse(mVideoPath));
				else
					mVideoView.setVideoPath(mVideoPath);
			}
		}
	};

	// 返回
	private ImageButton.OnClickListener btnVideoBackOnClick = new ImageButton.OnClickListener() {
		@Override
		public void onClick(View v) {
			setVideoBackInfo(mVideoView.getCurrentPosition());
			finish();
		}
	};

	private void setVideoBackInfo(long curPosition) {
		if (videoInfo != null) {
			// String curDateTime = DateFormat.getDateTimeInstance().format(
			// new java.util.Date());
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			String curDateTime = sDateFormat.format(new java.util.Date());

			HandlerRecentPlayTable.insertDataToRecentPlayTable(videoInfo.id);

			ContentValues cv = new ContentValues();
			cv.put(VideoInfoTableConst.VIDEOINFO__LASTPLAYTIME, curDateTime);
			cv.put(VideoInfoTableConst.VIDEOINFO__PLAYTIME, curPosition / 1000);
			HandlerVideoInfoTable.updataTable(videoInfo.id, cv);
		}

		// 发送消息 通知更新最近播放
		Intent intent = new Intent();
		intent.setAction(ActionConst.VIDEO_PLAY_OVER);
		VideoPlayerActivity.this.sendBroadcast(intent);

		// 在线视频退出时
		sendRpcAction(Constants.PadServiceApi_Action_TeacherPlat,
				Constants.PadServiceApi_Key_TeacherPlat,
				Constants.PadServiceApi_Flag_End);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("MainSettingsActivity", "onKeyDown");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setVideoBackInfo(mVideoView.getCurrentPosition());
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mVideoView != null)
			mVideoView.pause();
		// 视频到后台
		sendRpcAction(Constants.PadServiceApi_Action_TeacherPlat,
				Constants.PadServiceApi_Key_TeacherPlat,
				Constants.PadServiceApi_Flag_End);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mVideoView != null)
			mVideoView.resume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mVideoView != null)
			mVideoView.stopPlayback();
	}

	@Override
	public void onCompletion(MediaPlayer player) {
		setVideoBackInfo(mVideoView.getDuration());
		sendRpcAction(Constants.PadServiceApi_Action_Common,
				Constants.PadServiceApi_Key_Common, videoInfo.videoName);
		finish();
	}

	// private void stopPlayer() {
	// if (mVideoView != null)
	// mVideoView.pause();
	// }

	private void startPlayer() {
		if (mVideoView != null)
			mVideoView.start();
	}

	private boolean isPlaying() {
		return mVideoView != null && mVideoView.isPlaying();
	}

	@Override
	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		switch (arg1) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			// 开始缓冲，暂停播放
			if (isPlaying()) {
				mLoadingView.setVisibility(View.GONE);
			} else {
				mLoadingView.setVisibility(View.VISIBLE);
			}

			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			// 缓冲完成，自动播放
			if (!isPlaying())
				startPlayer();
			mLoadingView.setVisibility(View.GONE);
			break;
		case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			// 显示 下载速度
			// Log.i("VideoPlayerActivity", "download rate:" + arg2);
			// mListener.onDownloadRateChanged(arg2);
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event))
			return true;

		// 处理手势结束
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:
			endGesture();
			break;
		}

		return super.onTouchEvent(event);
	}

	/** 手势结束 */
	private void endGesture() {
		mVolume = -1;
		// mBrightness = -1f;

		// 隐藏
		mDismissHandler.removeMessages(0);
		mDismissHandler.sendEmptyMessageDelayed(0, 500);
	}

	/** 定时隐藏 */
	private Handler mDismissHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// mVolumeBrightnessLayout.setVisibility(View.GONE);
		}
	};

	private class MyGestureListener extends SimpleOnGestureListener {

		/** 双击 */
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mLayout == VideoView.VIDEO_LAYOUT_ZOOM)
				mLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
			else
				mLayout++;
			if (mVideoView != null)
				mVideoView.setVideoLayout(mLayout, 0);
			return true;
		}

		/** 滑动 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// float mOldX = e1.getX(), mOldY = e1.getY();
			// int y = (int) e2.getRawY();
			// Display disp = getWindowManager().getDefaultDisplay();
			// int windowWidth = disp.getWidth();
			// int windowHeight = disp.getHeight();

			// if (mOldX > windowWidth * 4.0 / 5)// 右边滑动
			// onVolumeSlide((mOldY - y) / windowHeight);
			// else if (mOldX < windowWidth / 5.0)// 左边滑动
			// onBrightnessSlide((mOldY - y) / windowHeight);

			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}

	// 向PadServiceApi 发送action
	private void sendRpcAction(int actionID, String actionKey, int stepFlag) {
		JSONStringer jsonStringer = new JSONStringer();
		try {
			jsonStringer.object();
			jsonStringer.key(actionKey).value(1);
			jsonStringer.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int iRet = PadServiceApi.GetInstance().SendRpcAction(actionID,
				jsonStringer.toString());
		Log.d(TAG, "SendRpcAction ret = " + String.valueOf(iRet));
	}

	private void sendRpcAction(int actionID, String actionKey,
			String actionValue) {
		JSONStringer jsonStringer = new JSONStringer();
		try {
			jsonStringer.object();
			jsonStringer.key(actionKey).value(actionValue);
			jsonStringer.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int iRet = PadServiceApi.GetInstance().SendRpcAction(actionID,
				jsonStringer.toString());
		Log.d(TAG, "SendRpcAction ret = " + String.valueOf(iRet));
	}
}
