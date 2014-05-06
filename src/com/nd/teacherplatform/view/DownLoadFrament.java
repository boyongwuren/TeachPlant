package com.nd.teacherplatform.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.nd.teacherplatform.constant.SubjectTypeConst;
import com.nd.teacherplatform.interfaces.ChangeDownLoadFragmentInterface;
import com.nd.teacherplatform.interfaces.OpenDownLoad2PageInterface;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.DownLoadVideoSetVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.VideoSetVo;
import com.nd.teacherplatform.vo.adapter.DownLoadAdapter;
import com.nd.teacherplatform.vo.sqlite.HandlerLoadingTable;
import com.nd.teacherplatform.vo.sqlite.HandlerHasDownTable;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoSetTable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 已下载的一级界面
 * 
 * @author zmp
 * 
 */
public class DownLoadFrament extends LinearLayout
{

	private Map<String, ArrayList<VideoInfoVo>> dataMap;
	
	private ListView listView;
	
	private int curIndex = 0;
	
	private ChangeDownLoadFragmentInterface changeDownLoadFragmentInterface;
	
	/**
	 * 是否是 已下载界面
	 */
	private boolean isHasDownLoadPage = true;
	
	public ImageView noDataPicImageView;
	
	public DownLoadFrament(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}



	public DownLoadFrament(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}



	public DownLoadFrament(Context context)
	{
		 super(context);
		 curIndex = SubjectTypeConst.SUOYOU_ID;
		 isHasDownLoadPage = true;
		
		 listView = new ListView(SingleToolClass.curContext);
		 LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		 listView.setLayoutParams(lp);
		 this.addView(listView);
		 
		 
		 LayoutParams thislp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		 this.setLayoutParams(thislp);
		 
	}

 
 

	 
	
	/**
	 * 更改选中的学科
	 * <br> 切换视图
	 * @param curIndex
	 */
	public void changeViewShow(int curIndex)
	{
		this.curIndex = curIndex;
		initData();
	}
	
	private void initData()
	{
		if(isHasDownLoadPage == true)
		{
			dataMap = HandlerHasDownTable.getHasDownLoadInfo(curIndex);
		}else 
		{
			dataMap = HandlerLoadingTable.getLoadingVideos(curIndex);
		}
		
		if(dataMap == null||dataMap.size() == 0)
		{
			this.setVisibility(View.GONE);
			noDataPicImageView.setVisibility(View.VISIBLE);
			return;
		}
		
		Set<String> keySet = dataMap.keySet();
		
		ArrayList<DownLoadVideoSetVo> downLoadVideoSetVos = new ArrayList<DownLoadVideoSetVo>();
		
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) 
		{
			int videoSetId = Integer.parseInt((String) iterator.next());
			VideoSetVo videoSetVo = HandlerVideoSetTable.getVideoSetVo(videoSetId);
			
			ArrayList<VideoInfoVo> vos = dataMap.get(videoSetId+"");
			int hasDownNum = vos.size();//视频集下面 有几个已经下载完成的视频
			
		    DownLoadVideoSetVo vo = new DownLoadVideoSetVo();
		    vo.id = videoSetVo.id;
		    vo.hasDownNum = hasDownNum;
		    vo.preViewPicUrl = videoSetVo.preViewPicUrl;
		    vo.videoSetName = videoSetVo.videoSetName;
		    vo.videosSize = 100;
		    
		    downLoadVideoSetVos.add(vo);			
		}
		
		listView.setAdapter(new DownLoadAdapter(downLoadVideoSetVos,new OpenDownLoad2PageClass()));
	}
	
	private class OpenDownLoad2PageClass implements OpenDownLoad2PageInterface
	{

		@Override
		public void openDownLoad2Page(DownLoadVideoSetVo vo)
		{
			 ArrayList<VideoInfoVo> vos = dataMap.get(vo.id+"");
			 changeDownLoadFragmentInterface.changeDownLoadFragment(vo, vos);
		}
		
	}

	/**
	 * 设置 切换的回调
	 * @param changeDownLoadFragmentInterface
	 */
	public void setChangeDownLoadFragmentInterface(ChangeDownLoadFragmentInterface changeDownLoadFragmentInterface)
	{
		this.changeDownLoadFragmentInterface = changeDownLoadFragmentInterface;
	}





	/**
	 * 是否是 已下载界面
	 * @param isHasDownLoadPage
	 */
	public void setHasDownLoadPage(boolean isHasDownLoadPage)
	{
		this.isHasDownLoadPage = isHasDownLoadPage;
		initData();
	}
	

}
