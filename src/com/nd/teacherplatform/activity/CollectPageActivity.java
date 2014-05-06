package com.nd.teacherplatform.activity;

import java.io.File;
import java.util.ArrayList;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.interfaces.DeleteCollectItemInterface;
import com.nd.teacherplatform.util.RemoveGlobalOnLayoutClass;
import com.nd.teacherplatform.util.RenderStoryInfo;
import com.nd.teacherplatform.view.CollectItemView;
import com.nd.teacherplatform.vo.CollectVo;
import com.nd.teacherplatform.vo.adapter.CollectListAdapter;
import com.nd.teacherplatform.vo.sqlite.HandlerCollectTable;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 收藏页面
 * @author zmp
 *
 */
public class CollectPageActivity extends CollectDownLoadActiviey
{
	
	private ListView collectListView;
	
	private ArrayList<CollectVo> cvos;

	public CollectPageActivity()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect_video_page);
		collectListView = (ListView) findViewById(R.id.collectListView);
		init();
	}


	
 
	@Override
	protected void changeSubjectView(int index)
	{
		cvos = HandlerCollectTable.getCollectData(index);
		if(cvos != null&&cvos.size() >0)
		{
			collectListView.setAdapter(new CollectListAdapter(cvos,new DeleteCollectItemClass()));
			collectListView.setVisibility(View.VISIBLE);
			noCollectImageView.setVisibility(View.GONE);
		}else 
		{
			collectListView.setVisibility(View.GONE);
			noCollectImageView.setVisibility(View.VISIBLE);
		}
		
	}
	
	/**
	 * 删除了某一个item
	 * @author zmp
	 *
	 */
	private class DeleteCollectItemClass implements DeleteCollectItemInterface
	{

		@Override
		public void deleteCollectItem(CollectVo vo)
		{
			HandlerCollectTable.deleteCollectById(vo.videoId);
			changeSubjectView(curIndex);
		}

		@Override
		public void hideAllDeleteBtn()
		{
			for (int i = 0; i < collectListView.getChildCount(); i++)
			{
				CollectItemView collectItemView = (CollectItemView) collectListView.getChildAt(i);
				collectItemView.hideDeleteItemBtn(false);
			}
		}
	}
	
	
 
	
	/**
	 * 删除所有数据
	 * 子类重写实现
	 */
	@Override
	protected void doDeleteAll()
	{
		if(cvos != null&& cvos.size()>0)
		{
			for (int i = 0; i < cvos.size(); i++) 
			{
				CollectVo vo = cvos.get(i);
				HandlerCollectTable.deleteCollectById(vo.videoId);
			}
		}
		
		changeSubjectView(curIndex);
	}

}
