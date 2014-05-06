package com.nd.teacherplatform.activity;


import java.io.File;
import java.util.ArrayList;

import com.nd.teacherplatform.vo.sqlite.HandlerRecentPlayTable;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoSetTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.FileConst;
import com.nd.teacherplatform.constant.SharedPreferenceKey;
import com.nd.teacherplatform.constant.SubjectTypeConst;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.constant.VideoListConst;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.interfaces.VideoSetOnClickInterface;
import com.nd.teacherplatform.util.FileUtils;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.RecentPlayVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.VideoSetVo;
import com.nd.teacherplatform.vo.adapter.HomePageRecentPlayAdapter;
import com.nd.teacherplatform.vo.adapter.SubjectRecentPlayAdapter;
import com.nd.teacherplatform.vo.adapter.VideoSetAdapter;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoInfoTable;
import com.nd.teacherplatform.vo.sqlite.SqliteOpenHelperClass;

/**
 * 各学科公用模块
 * @author zmp
 *
 */
public class SubjectPageActivity extends HomeSubjectActivity 
{

	/**
	 * 顶部标题文本
	 */
	private TextView titleTxt;
	
	/**
	 * 视频集列表
	 */
	private GridView videoGridView;
	
	/**
	 * 最近播放
	 */
	private ListView recentPlayListView;
	

	
	public SubjectPageActivity()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_page);
		addListener();
		
		 titleTxt = (TextView)findViewById(R.id.titleTxt);
		 
		 videoGridView = (GridView)findViewById(R.id.videoGridView);
		 
		 recentPlayListView = (ListView)findViewById(R.id.curPlayListView);
		 
		 SingleToolClass.loadTipView = findViewById(R.id.loadTipProgress);
		 SingleToolClass.loadTipView.setVisibility(View.GONE);
		 
		 //接收传过来的科目类型
		 this.setSubjectType( getIntent().getStringExtra(SubjectTypeConst.CATEGORY_KEY) );
		 
		 titleTxt.setText(this.getSubjectType());
		 
		 //请求服务器对应学科的视频集
		 String paramsString = "{\"op\":\"Video.getcollectlist\",\"sid\":\"\",\"uid\":\"\",\"subjectId\":"+this.subjectTypeInt+"}";
		 LoadServerAPIHelp.loadServerApi(URLConst.SUBJECT_VIDEO_SET_URL, new LoadApiBackClass(),paramsString);
		 
		 //本地的数据取出，以防没网络
//		 String videoSetJsonString = SingleToolClass.sharedPreferences.getString(this.getSubjectType(), SharedPreferenceKey.SUBJECT_VIDEO_SET);
//	     setVideoSetData(videoSetJsonString);
	     
	     showRecentPlay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		return true;
	}

	/**
	 * 加载服务端接口返回
	 * 学科视频集的返回接口
	 * @author zmp
	 *
	 */
	private class LoadApiBackClass implements LoadApiBackInterface
	{

		@Override
		public void loadApiBackString(String jsonString)
		{
			setVideoSetData(jsonString);
		}
	}
	
	
	/**
	 * 根据json格式数据设置各科视频集的数据
	 * 解析json
	 * @param
	 * {
	 *   'data':
	 *            [   {'id':01 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'百度','totalVideoNum':'100'},   
				      {'id':02 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'网易','totalVideoNum':'100'},   
				      {'id':03 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'美团','totalVideoNum':'200'},   
				      {'id':04 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'新浪','totalVideoNum':'13'},   
				      {'id':05 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'搜狐','totalVideoNum':'75'},   
				      {'id':06 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'谷歌','totalVideoNum':'78'},   
				      {'id':07 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'宜搜','totalVideoNum':'55'},   
				      {'id':08 ,'preViewUrl':'http://www.baidu.com/icon' ,'videoSetName':'天猫','totalVideoNum':'99'}
				  ]
		} 
	 */
	private void setVideoSetData(String jsonsString)
	{
		ArrayList<VideoSetVo> videoSetVos = new ArrayList<VideoSetVo>();
		
		Boolean hasItem = false;
		
		try 
		{
			JSONArray jsonArray = new JSONObject(jsonsString).getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) 
			{
				hasItem = true;
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int videoSetId = jsonObject.getInt("id");
				String preViewUrl = jsonObject.getString("preViewUrl");
				String videoSetName = jsonObject.getString("videoSetName");
				int totalVideoNum = jsonObject.getInt("totalVideoNum");
				
				VideoSetVo vo = new VideoSetVo();
				vo.id = videoSetId;
				vo.preViewPicUrl = preViewUrl;
				vo.videoSetName = videoSetName;
				vo.totalVideoNum = totalVideoNum;
				vo.hasDownNum = FileUtils.getFileChildFile(FileConst.VIDEO_PATH+File.separator+getSubjectType()+File.separator+videoSetName);
				
				videoSetVos.add(vo);

                //插入数据库 存起来
                HandlerVideoSetTable.insertVideoSetInfo(videoSetName,0,videoSetId,preViewUrl,totalVideoNum);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		if(hasItem == true)
		{
			SingleToolClass.sharedPreferences.edit().putString(this.getSubjectType(), jsonsString);
			videoGridView.setAdapter(new VideoSetAdapter(videoSetVos,new VideoSetOnClickClass()));
		}
	}
	
	/**
	 *  视频集被点击
	 *  打开视频集界面
	 * @author zmp
	 *
	 */
	private class VideoSetOnClickClass implements VideoSetOnClickInterface
	{
		@Override
		public void videoSetOnClick(int videoSetId) 
		{
			Intent intent = new Intent(SubjectPageActivity.this,VideoListActivity.class);
			intent.putExtra(VideoListConst.SUBJECT_ID_KEY, subjectTypeInt);
			intent.putExtra(VideoListConst.VIDEO_SET_ID_KEY, videoSetId);
			SubjectPageActivity.this.startActivity(intent);
		}
		
	}
	
	@Override
	protected void updataRecentPlay()
	{
		super.updataRecentPlay();
		
		 showRecentPlay();
	}
	
	/**
	 * 最近播放部分
	 */
	private void showRecentPlay()
	{
		 //最近播放部分
	     ArrayList<VideoInfoVo> recentPlayVos = HandlerRecentPlayTable.getSubjectRecentPlayRecord(this.subjectTypeInt);
	     recentPlayListView.setAdapter(new SubjectRecentPlayAdapter(recentPlayVos));
	}
	
}
