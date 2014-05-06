package com.nd.teacherplatform.view;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.util.Utility;
import com.nd.teacherplatform.vo.UnitVideosVo;
import com.nd.teacherplatform.vo.adapter.UnitVideoAdapter;

import android.content.Context;
import android.test.UiThreadTest;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ÿһ����Ƶ����
 * ÿһ�����޵�
 * ÿһ����Ԫ����Ϣ
 * @author zmp
 *
 */
public class UnitVideoView extends LinearLayout 
{

	/**
	 * ��Ԫ�ı���
	 */
	private TextView unitTitltTextView;
	
	/**
	 * ��Ƶ�б�
	 */
	private ListView videoListView;
	
	/**
	 * ÿһ����Ԫ����Ƶ��Ϣ
	 */
	private UnitVideosVo unitVideosVo;
	
	
	public UnitVideoView(Context context) 
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.unit_video, this, true);
		unitTitltTextView = (TextView)findViewById(R.id.unitTitle);
		videoListView = (ListView)findViewById(R.id.videoListView);
	}

	public UnitVideoView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
	
	/**
	 * ���õ�Ԫ����Ƶ��Ϣ
	 * @param vo
	 */
	public void setUnitVideosVo(UnitVideosVo vo)
	{
		unitVideosVo = vo;
		
		unitTitltTextView.setText(vo.unitName);
		
		UnitVideoAdapter unitVideoAdapter = new UnitVideoAdapter(vo.videoInfoVos);
		videoListView.setAdapter(unitVideoAdapter);
		
		Utility.setListViewHeightBasedOnChildren(videoListView);
	}
	
	/**
	 * ��ȡ��Ԫ����Ƶ��Ϣ
	 * @return
	 */
	public UnitVideosVo getUnitVideosVo()
	{
		return unitVideosVo;
	}

}
