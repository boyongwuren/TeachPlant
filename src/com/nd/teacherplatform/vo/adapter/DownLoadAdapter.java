package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.teacherplatform.interfaces.OpenDownLoad2PageInterface;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.view.DownLoadItemView;
import com.nd.teacherplatform.vo.DownLoadVideoSetVo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 已下载界面的 DownLoadFrameng的适配器
 * @author zmp
 *
 */
public class DownLoadAdapter extends BaseAdapter
{

	private ArrayList<DownLoadVideoSetVo> vos;
	
	private OpenDownLoad2PageInterface openDownLoad2PageInterface;
	
	public DownLoadAdapter(ArrayList<DownLoadVideoSetVo> vos,OpenDownLoad2PageInterface openDownLoad2PageInterface)
	{
		this.vos = vos;
		this.openDownLoad2PageInterface = openDownLoad2PageInterface;
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
		DownLoadItemView downLoadItem ;
		
		if(convertView == null)
		{
			convertView = new DownLoadItemView(SingleToolClass.curContext,openDownLoad2PageInterface);
		}
		
		downLoadItem = (DownLoadItemView) convertView;
		
		DownLoadVideoSetVo vo = vos.get(position);
		downLoadItem.setDownLoadVideoSetVo(vo);
		
		return downLoadItem;
	}

}
