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
 * �����Ƶ�����ֵĽ���
 * ÿһ����Ƶ���� 
 * ����γ̵���Ƶ�б�
 * @author zmp
 *
 */
public class VideoListActivity extends BaseActivity 
{
	/**
	 * ��Ӧ����Ƶ����ID
	 */
    private int videoSetID = 0;
	
	/**
	 * ���޿� ��ѡ��
	 */
	private RadioGroup requireGroup;
	
	/**
	 * ��Ƶ�б�
	 */
	private ListView videosListView;
	
	/**
	 * ĳһ�����޿������ ��Ƶ�б�����
	 */
	private ArrayList<UnitVideosVo> unitVideosVos;
	
	/**
	 * ��ʶ ���ĸ�ѧ��
	 */
	private int subjectID = 0;
	
	/**
	 * ��ʶ ������� ���޼�
	 */
	private int requireID = 0;
	
	/**
	 * ������ ���޿γ̵� ����
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
		
		//��ʼ��  ���� ӵ�м������޿� ÿ�����޿ε�ID
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
	 * ��ѡ�� �������
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
	 * ����ͷ�� ���޵���Ϣ
	 */
	private void requestRequireInfo()
	{
		 //��������� ���� ��Ƶ�� ��Ӧ�� ���޿γ̵���Ŀ 
		 String paramsString = "{\"op\":\"Video.getstagelist\",\"sid\":\"\",\"uid\":\"\",\"subjectId\":"+subjectID+",\"collectId\":"+videoSetID+"}";
		 LoadServerAPIHelp.loadServerApi(URLConst.GET_VIDEOSET_REQUIRE_URL, new LoadVideoSetRequireApiBackClass(),paramsString);
	}
	
	/**
	 * ����������õ���Ӧ����Ƶ���� ���޿���Ŀ
	 * @author zmp
	 *
	 */
	private class LoadVideoSetRequireApiBackClass implements LoadApiBackInterface
	{

		@Override
		public void loadApiBackString(String jsonString) 
		{
			//�õ�ӵ�м��������ˡ�
			paresRequireInfo(jsonString);
			
			//�Զ������һ�����޵���Ϣ
			if(requistIds != null&&requistIds.size()>0)
			{
				requestVideoListInfo(Integer.parseInt(requistIds.get(0)));
			}
		}
	}
	
	private ArrayList<String> requistIds = new ArrayList<String>(); 
	
	/**
	 * �������������صı���json
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
			    int id = tempJsonObject.getInt("id");//�õ����޿ε�����
			    String requireName = tempJsonObject.getString("name");//�õ����޿ε�����
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
	 * ����ĳһ����������
	 * ӵ�е���Ƶ��Ϣ
	 * @param requireID ���޵�ID
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
	 * ����ĳһ�����޿������ ��Ƶ�б������
	 * @param unitVideosVos
	 */
	public void setUnitVideosVo(ArrayList<UnitVideosVo> unitVideosVos)
	{
		this.unitVideosVos = unitVideosVos;
		
		UnitListAdapter unitListAdapter = new UnitListAdapter(this.unitVideosVos);
		videosListView.setAdapter(unitListAdapter);
		
	}

}
