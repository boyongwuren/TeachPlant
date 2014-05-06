package com.nd.teacherplatform.activity;

import java.util.ArrayList;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.interfaces.ChangeDownLoadFragmentInterface;
import com.nd.teacherplatform.view.DownLoad2Frament;
import com.nd.teacherplatform.view.DownLoadFrament;
import com.nd.teacherplatform.vo.DownLoadVideoSetVo;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * ����ҳ��
 * @author zmp
 *
 */
public class DownLoadPageActivity extends CollectDownLoadActiviey
{
	
	private RadioGroup titleGroup;
	
	private DownLoadFrament downLoadFrament;
	
	private DownLoad2Frament downLoad2Frament;
	
	private RelativeLayout framentContent;
	
	/**
	 * �Ƿ��������ؽ���
	 */
	private boolean isHasDownPaga = true;
	
	public DownLoadPageActivity()
	{
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.down_load_page);
		 
		titleGroup = (RadioGroup) findViewById(R.id.downGroup);
		
		RadioButton titleRadioButton =  (RadioButton) titleGroup.getChildAt(0);
		titleRadioButton.setChecked(true);
		
		
		titleGroup.setOnCheckedChangeListener(new TitleGroupClickClass());
		
		framentContent = (RelativeLayout) findViewById(R.id.framentContent);
		
		downLoadFrament = new DownLoadFrament(this);
		framentContent.addView(downLoadFrament);
		downLoadFrament.setChangeDownLoadFragmentInterface(new ChangeDownLoadFragmenClass());
		
		downLoad2Frament = new DownLoad2Frament(this);
		framentContent.addView(downLoad2Frament);
		
		downLoad2Frament.setVisibility(View.GONE);
		
		init();
	}
	
	private class TitleGroupClickClass implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			if(checkedId == R.id.hasDown)
			{
				//�л���������
				isHasDownPaga = true;
			}else 
			{
				//�л��� ��������
				isHasDownPaga = false;
			}
			
			if(downLoadFrament.getVisibility() != View.VISIBLE)
			{
				downLoadFrament.setVisibility(View.VISIBLE);
				downLoad2Frament.setVisibility(View.GONE);
			}
			
			noCollectImageView.setVisibility(View.GONE);
			downLoadFrament.setVisibility(View.VISIBLE);
			downLoadFrament.setHasDownLoadPage(isHasDownPaga);
		}
	}
	
	 
		
	 
	
	/**
	 * ��ʾ�б�ҳ��
	 * @author Administrator
	 *
	 */
	private class ChangeDownLoadFragmenClass implements ChangeDownLoadFragmentInterface
	{

		@Override
		public void changeDownLoadFragment(DownLoadVideoSetVo downLoadVideoSetVo, ArrayList<VideoInfoVo> videoInfoVos)
		{
			downLoad2Frament.setHasDownPage(isHasDownPaga);
			downLoad2Frament.setDownLoadVideoSetVo(downLoadVideoSetVo);
			downLoad2Frament.setVideoInfoVos(videoInfoVos);
			
			downLoad2Frament.setVisibility(View.VISIBLE);
			downLoadFrament.setVisibility(View.GONE);
		}
		
	}


	@Override
	protected void changeSubjectView(int curIndex)
	{
		showDownLoadFrament(curIndex);
	}
	
	/**
	 * ��ʾdownLoadFragment ҳ��
	 * @param curIndex
	 */
	private void showDownLoadFrament(int curIndex)
	{
		if(downLoadFrament.noDataPicImageView == null)
		{
			downLoadFrament.noDataPicImageView = noCollectImageView;
		}
		noCollectImageView.setVisibility(View.GONE);
		downLoadFrament.setVisibility(View.VISIBLE);
		downLoadFrament.changeViewShow(curIndex);
		downLoad2Frament.setVisibility(View.GONE);
	}


	@Override
	protected void doDeleteAll()
	{
		// TODO Auto-generated method stub
		
	}
	
 
	@Override
	public void onBackPressed()
	{
		if (downLoadFrament.getVisibility() != View.VISIBLE&&noCollectImageView.getVisibility() != View.VISIBLE) 
		{
			showDownLoadFrament(curIndex);
		}else 
		{
		   super.onBackPressed();
		}
	}
	 
	

}
