package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.view.DownLoad2ItemView;
import com.nd.teacherplatform.vo.VideoInfoVo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 下载界面 二级界面的 listView 的适配器
 * @author zmp
 *
 */
public class DownLoadListAdapter extends BaseAdapter
{

	private boolean isHasDownItem;
	private ArrayList<VideoInfoVo> vos;
	
	private DelectOneVideoInfoClass delectOneVideoInfoClass;
	
	public DownLoadListAdapter(ArrayList<VideoInfoVo> vos,boolean isHasDownItem)
	{
		this.vos = vos;
		this.isHasDownItem = isHasDownItem;
		
		delectOneVideoInfoClass = new DelectOneVideoInfoClass();
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return vos.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		DownLoad2ItemView downLoad2ItemView;
		
		if(convertView == null)
		{
			convertView = new DownLoad2ItemView(SingleToolClass.curContext,delectOneVideoInfoClass);
		}
		
		
		downLoad2ItemView = (DownLoad2ItemView) convertView;

		downLoad2ItemView.setHasDownItem(isHasDownItem);
		
		VideoInfoVo vo = vos.get(position);
		downLoad2ItemView.setVo(vo);
		
		return downLoad2ItemView;
	}
	
	public interface DelectOneVideoInfoInterface
	{
		public void delectOneVideoInfo(int videoId);
	}
	
	private class DelectOneVideoInfoClass implements DelectOneVideoInfoInterface
	{
		public void delectOneVideoInfo(int videoId)
		{
			for (int i = 0; i < vos.size(); i++) 
			{
				VideoInfoVo vo = vos.get(i);
				if(vo.id == videoId)
				{
					vos.remove(vo);
					break;
				}
			}
			
			DownLoadListAdapter.this.notifyDataSetChanged();
		}
	}
	
	

}
