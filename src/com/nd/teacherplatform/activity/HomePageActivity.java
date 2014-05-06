package com.nd.teacherplatform.activity;

import java.util.ArrayList;

import com.nd.teacherplatform.vo.sqlite.HandlerVideoSetTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nd.pad.onlinevideo.R;
import com.nd.studentpad.padserviceapi.PadServiceApi;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.download.DownLoaderHelp;
import com.nd.teacherplatform.handler.TimeThread;
import com.nd.teacherplatform.handler.VideoOnClickListener;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.view.RecentPlayItemView;
import com.nd.teacherplatform.vo.ReCommendVideoVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.adapter.HomePageRecentPlayAdapter;
import com.nd.teacherplatform.vo.adapter.RecommendVideoAdapter;
import com.nd.teacherplatform.vo.sqlite.HandlerLoadingTable;
import com.nd.teacherplatform.vo.sqlite.HandlerRecentPlayTable;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoInfoTable;

/**
 * ��ҳ����
 * 
 * @author zmp
 * 
 */
public class HomePageActivity extends HomeSubjectActivity
{

	/**
	 * �Ƽ���Ƶ
	 */
	private GridView recommendGridView;

	/**
	 * û��������ż�¼��ͼƬ
	 */
	private ImageView noRecentPlayImageView;

	/**
	 * ������ż�¼����ͼ
	 */
	private GridView recentPlayGridView;

	/**
	 * ��һ����������ŵ�item
	 */
	private RecentPlayItemView firstRecentPlayItem;

	private PadServiceApi padServiceApi;

	public HomePageActivity()
	{

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		addListener();

		this.setSubjectType("");

		recommendGridView = (GridView) findViewById(R.id.recommendVideoGridView);
		noRecentPlayImageView = (ImageView) findViewById(R.id.noRecentWatchPic);
		recentPlayGridView = (GridView) findViewById(R.id.recentWatchGridView);
		firstRecentPlayItem = (RecentPlayItemView) findViewById(R.id.firstRecentPlayItem);
		firstRecentPlayItem.setOnClickListener(new VideoOnClickListener());

		SingleToolClass.loadTipView = findViewById(R.id.loadTipProgress);
		SingleToolClass.loadTipView.setVisibility(View.GONE);

		// ����ȡ����
		String paramString = "{\"op\":\"Video.getrecommendvideolist\",\"sid\":\"\",\"uid\":\"\",\"pageIndex\":1,\"pageSize\":5}";
		LoadServerAPIHelp.loadServerApi(URLConst.REMEMOND_VIDEO_URL, new LoadApiBackClass(), paramString);

		// ���ر����Ҳ�ó����á�����û������û����
		// String jsonString =
		// SingleToolClass.sharedPreferences.getString(SharedPreferenceKey.RECOMMEND_VIDEO_KEY,
		// SharedPreferenceKey.RECOMMEND_VIDEO_DEF_VALUE);
		// setRecomendVideoData(jsonString);

		mHandler.sendEmptyMessage(Flag_init);
		// padServiceApi = PadServiceApi.GetInstance();
		// if (null != padServiceApi)
		// padServiceApi.InitApi(getApplicationInfo().packageName, this);

		showRecentPlay();

		loadFile();
	}

	private static final int Flag_init = 1; // ��ʼ���ӿ�
	private static final int Flag_release = 2;// �˳�ʱ�ӿ��ͷ�

	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (null == padServiceApi)
				padServiceApi = PadServiceApi.GetInstance();
			switch (msg.what)
			{
				case Flag_init:
					if (null != padServiceApi)
						padServiceApi.InitApi(getApplicationInfo().packageName, HomePageActivity.this);
					break;
				case Flag_release:
					if (null != padServiceApi)
						padServiceApi.ReleaseApi();
					break;
			}
		}
	};

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mHandler.sendEmptyMessage(Flag_release);
		// if (null != padServiceApi)
		// padServiceApi.ReleaseApi();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return true;
	}

	/**
	 * ���ط���˽ӿڷ��� �Ƽ���Ƶ�ķ��ؽӿ�
	 * 
	 * @author zmp
	 * 
	 */
	private class LoadApiBackClass implements LoadApiBackInterface
	{

		@Override
		public void loadApiBackString(String jsonString)
		{
			setRecomendVideoData(jsonString);
		}
	}

	/**
	 * ����json��ʽ���������Ƽ���Ƶ������ ����json
	 * 
	 * @param jsonString
	 *            { 'data': [ {'id':01
	 *            ,'preViewUrlCom':'http://www.baidu.com/icon' ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'�ٶ�','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':1
	 *            0 0 } , {'id':02 ,'preViewUrlCom':'http://www.baidu.com/icon'
	 *            ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'����','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':1
	 *            0 0 } , {'id':03 ,'preViewUrlCom':'http://www.baidu.com/icon'
	 *            ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'����','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':1
	 *            0 0 } , {'id':04 ,'preViewUrlCom':'http://www.baidu.com/icon'
	 *            ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'����','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':1
	 *            0 0 } , {'id':05 ,'preViewUrlCom':'http://www.baidu.com/icon'
	 *            ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'�Ѻ�','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':1
	 *            0 0 } , {'id':06 ,'preViewUrlCom':'http://www.baidu.com/icon'
	 *            ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'�ȸ�','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':1
	 *            0 0 } , {'id':07 ,'preViewUrlCom':'http://www.baidu.com/icon'
	 *            ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'����','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':1
	 *            0 0 } , {'id':08 ,'preViewUrlCom':'http://www.baidu.com/icon'
	 *            ,
	 *            'preViewUrlBig':'http://www.baidu.com/icon','videoName':'��è','tearchName':'com','videoType':'com','author':'����','fileFormat':'�ļ���ʽ','size':1024,'allTime':
	 *            1 0 0 } ] }
	 */
	private void setRecomendVideoData(String jsonString)
	{
		ArrayList<ReCommendVideoVo> reCommendVideoVos = new ArrayList<ReCommendVideoVo>();
		Boolean hasItem = false;// �������ݴ�������
		try {
			JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				hasItem = true;
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String preViewUrlCom = jsonObject.getString("preViewUrlCom");
				String preViewUrlBig = jsonObject.getString("preViewUrlBig");
				String videoName = jsonObject.getString("videoName");
				String tearchName = jsonObject.getString("tearchName");
				String authorName = jsonObject.getString("author");
				String videoFormat = jsonObject.getString("fileFormat");
				String videoSetName = jsonObject.getString("collectName");
				String videoSetPic = jsonObject.getString("collectImage");
				String stageName = jsonObject.getString("stageName");
				int videoType = jsonObject.getInt("videoType");
				int videoSetID = jsonObject.getInt("collectId");
				int videoSize = jsonObject.getInt("size");
				int allTime = jsonObject.getInt("allTime");
				int videoId = jsonObject.getInt("id");
				int subjectID = jsonObject.getInt("subjectId");
				ReCommendVideoVo vo = new ReCommendVideoVo();
				vo.preViewUrlBig = preViewUrlBig;
				vo.preViewUrlCom = preViewUrlCom;
				vo.videoName = videoName;
				vo.setVideoRequireType(videoType);
				vo.videoRequireTypeName = stageName;
				vo.teacherName = tearchName;
				vo.authorName = authorName;
				vo.videoFormat = videoFormat;
				vo.videoSize = videoSize;
				vo.totalTime = allTime;
				vo.id = videoId;
				vo.sujectID = subjectID;
				vo.setVideoSetID(videoSetID);
				HandlerVideoSetTable.insertVideoSetInfo(videoSetName, 0, videoSetID, videoSetPic, 0);
				reCommendVideoVos.add(vo);

				HandlerVideoInfoTable.insertDataToVideoInfoTable(vo);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		if (hasItem == true) {
			// SingleToolClass.sharedPreferences.edit().putString(SharedPreferenceKey.RECOMMEND_VIDEO_KEY,
			// SharedPreferenceKey.RECOMMEND_VIDEO_DEF_VALUE);
			recommendGridView.setAdapter(new RecommendVideoAdapter(reCommendVideoVos));// �����Ƽ���Ƶ
		}

	}

	@Override
	protected void updataRecentPlay()
	{
		super.updataRecentPlay();

		showRecentPlay();
	}

	/**
	 * //������Ų���
	 * 
	 */
	private void showRecentPlay()
	{
		ArrayList<VideoInfoVo> recentPlayVos = HandlerRecentPlayTable.getRecentPlayRecord();
		if (recentPlayVos.size() == 0) {
			// û��������ŵļ�¼
			noRecentPlayImageView.setVisibility(View.VISIBLE);

			recentPlayGridView.setVisibility(View.GONE);
			firstRecentPlayItem.setVisibility(View.GONE);
		} else {
			noRecentPlayImageView.setVisibility(View.GONE);
			recentPlayGridView.setVisibility(View.VISIBLE);
			firstRecentPlayItem.setVisibility(View.VISIBLE);

			VideoInfoVo firstRecentPlayVo = recentPlayVos.remove(0);
			firstRecentPlayItem.setIsFirst(true);
			firstRecentPlayItem.setRecentPalyVo(firstRecentPlayVo);
			firstRecentPlayItem.setTag(firstRecentPlayVo);

			recentPlayGridView.setAdapter(new HomePageRecentPlayAdapter(recentPlayVos));
		}
	}

	private boolean isBack = false;

	@Override
	public void onBackPressed()
	{
		if (isBack == false) {
			isBack = true;
			Toast toast = Toast.makeText(this, R.string.sureExitApp, Toast.LENGTH_SHORT);
			toast.show();
			new TimeThread(new ExitAppHandler(), 2000, 1).start();
		} else {
			SingleToolClass.isFirstInit = true;
			DownLoaderHelp.stopLoadThread();
			super.onBackPressed();
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(0);
		}
	}

	private class ExitAppHandler extends Handler
	{
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			isBack = false;

			System.out.println("isbace");
		}
	}

	/**
	 * ���� δ��ɵļ����ļ�
	 */
	private void loadFile()
	{
		if (SingleToolClass.isFirstInit) {
			SingleToolClass.isFirstInit = false;
			ArrayList<String> videoIds = HandlerLoadingTable.getVideoId();
			ArrayList<VideoInfoVo> vos = new ArrayList<VideoInfoVo>();
			for (int i = 0; i < videoIds.size(); i++) {
				VideoInfoVo vo = HandlerVideoInfoTable.getVideoInfoVo(Integer.parseInt(videoIds.get(i)));
				vos.add(vo);
			}

			for (int i = 0; i < vos.size(); i++) {
				DownLoaderHelp.downLoadHelp(vos.get(i));
			}
		}
	}

}
