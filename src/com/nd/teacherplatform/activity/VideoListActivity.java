package com.nd.teacherplatform.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.constant.VideoListConst;
import com.nd.teacherplatform.interfaces.BackVideoListInterface;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.net.GetVideoList;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.UnitVideosVo;
import com.nd.teacherplatform.vo.adapter.UnitListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 点击视频集出现的界面
 * 每一个视频集的 
 * 必须课程的视频列表
 * @author zmp
 *
 */
public class VideoListActivity extends BaseActivity 
{
	/**
	 * 对应的视频集的ID
	 */
    private int videoSetID = 0;
	
	/**
	 * 必修课 单选框
	 */
	private RadioGroup requireGroup;
	
	/**
	 * 视频列表
	 */
	private ListView videosListView;
	
	/**
	 * 某一个必修课下面的 视频列表数据
	 */
	private ArrayList<UnitVideosVo> unitVideosVos;
	
	/**
	 * 标识 是哪个学科
	 */
	private int subjectID = 0;
	
	/**
	 * 标识 点击的是 必修几
	 */
	private int requireID = 0;
	
	/**
	 * 顶部的 必修课程的 数组
	 */
	private ArrayList<String> titleRequire;
	
	
	public VideoListActivity() 
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle saveBundle)
	{
		super.onCreate(saveBundle);
		
		
		setContentView(R.layout.video_list);

		titleRequire = new ArrayList<String>();
		
		requireGroup = (RadioGroup)findViewById(R.id.requireGroup);
		
		videosListView = (ListView)findViewById(R.id.unitListView);

		this.subjectID = getIntent().getIntExtra(VideoListConst.SUBJECT_ID_KEY, 0);
		this.videoSetID = getIntent().getIntExtra(VideoListConst.VIDEO_SET_ID_KEY, 0);
		
		SingleToolClass.loadTipView = findViewById(R.id.loadTipProgress);
		SingleToolClass.loadTipView.setVisibility(View.GONE);
		
		//初始化  请求 拥有几个必修课 每个必修课的ID
		requestRequireInfo();
		
		RadioButton rb = (RadioButton) requireGroup.getChildAt(0);
		rb.setChecked(true);
		
		requireGroup.setOnCheckedChangeListener(new OnCheckedChangeListenerClass());
		
		 
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		 this.finish();
		 return false;
	}
	
	
	/**
	 * 单选框 被点击了
	 * @author zmp
	 *
	 */
	private class OnCheckedChangeListenerClass implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) 
		{
			for (int i = 0; i < requireGroup.getChildCount(); i++) 
			{
				RadioButton rb = (RadioButton) requireGroup.getChildAt(i);
				if(rb.getId() == checkedId)
				{
					if(requistIds != null && requistIds.size() >0)
					{
						requestVideoListInfo(Integer.parseInt(requistIds.get(i)));
					}
					return;
				}
			}
		}
		
	}
	
	
	/**
	 * 请求头部 必修的信息
	 */
	private void requestRequireInfo()
	{
		 //请求服务器 请求 视频集 对应的 必修课程的数目 
		 String paramsString = "{\"op\":\"Video.getstagelist\",\"sid\":\"\",\"uid\":\"\",\"subjectId\":"+subjectID+",\"collectId\":"+videoSetID+"}";
		 LoadServerAPIHelp.loadServerApi(URLConst.GET_VIDEOSET_REQUIRE_URL, new LoadVideoSetRequireApiBackClass(),paramsString);
	}
	
	/**
	 * 请求服务器得到对应的视频集的 必修课数目
	 * @author zmp
	 *
	 */
	private class LoadVideoSetRequireApiBackClass implements LoadApiBackInterface
	{

		@Override
		public void loadApiBackString(String jsonString) 
		{
			//得到拥有几个必修了。
			paresRequireInfo(jsonString);
			
			//自动请求第一个必修的信息
			if(requistIds != null&&requistIds.size()>0)
			{
				requestVideoListInfo(Integer.parseInt(requistIds.get(0)));
			}
		}
	}
	
	private ArrayList<String> requistIds = new ArrayList<String>(); 
	
	/**
	 * 解析服务器返回的必修json
	 * @param jsonsString
	 */
	private void paresRequireInfo(String jsonString)
	{
		JSONObject jsonObject;

		try {
			jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++)
			{
			    JSONObject tempJsonObject = jsonArray.getJSONObject(i);
			    int id = tempJsonObject.getInt("id");//得到必修课的名称
			    String requireName = tempJsonObject.getString("name");//得到必修课的名称
			    titleRequire.add(requireName);
			    requistIds.add(id+"");
			}
			
		} catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		try
		{
 
			for (int i = 0; i < requireGroup.getChildCount(); i++) 
			{
				RadioButton rb = (RadioButton) requireGroup.getChildAt(i);
				rb.setText("");
			}
	
			for (int i = 0; i < titleRequire.size(); i++) 
			{
				RadioButton rb = (RadioButton) requireGroup.getChildAt(i);
				rb.setText(titleRequire.get(i));
			}
		
		} catch (Exception e) 
		{
			
		}
		
	}
	
	
	
	/**
	 * 请求某一个必修下面
	 * 拥有的视频信息
	 * @param requireID 必修的ID
	 */
	private void requestVideoListInfo(int requireID)
	{
		this.requireID = requireID;
		GetVideoList.getVideoList(subjectID, videoSetID, this.requireID, new BackVideoListClass());
	}
	
	private class BackVideoListClass implements BackVideoListInterface
	{

		@Override
		public void setVideoList(ArrayList<UnitVideosVo> uvos)
		{
			// TODO Auto-generated method stub
			unitVideosVos = uvos;
			setUnitVideosVo(unitVideosVos);
		}
		
	}
	
	
 
	/**
	 * 设置某一个必修课下面的 视频列表的数据
	 * @param unitVideosVos
	 */
	public void setUnitVideosVo(ArrayList<UnitVideosVo> unitVideosVos)
	{
		this.unitVideosVos = unitVideosVos;
		
		UnitListAdapter unitListAdapter = new UnitListAdapter(this.unitVideosVos);
		videosListView.setAdapter(unitListAdapter);
		
	}

}
