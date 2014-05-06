package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.view.UnitVideoView;
import com.nd.teacherplatform.vo.UnitVideosVo;

/**
 * 某一个必修课下面的
 * 单元列表
 * @author zmp
 *
 */
public class UnitListAdapter extends BaseAdapter 
{

	private ArrayList<UnitVideosVo> unitVideosVos;
	
	public UnitListAdapter(ArrayList<UnitVideosVo> unitVideosVos) 
	{
           this.unitVideosVos = unitVideosVos;
	}

	@Override
	public int getCount()
	{
		return unitVideosVos.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		UnitVideosVo vo = unitVideosVos.get(position);
		
		UnitVideoView unitVideoView = null;
		
		if(convertView == null)
		{
			convertView = new UnitVideoView(SingleToolClass.curContext);
		}
		
		unitVideoView = (UnitVideoView)convertView;
		
		unitVideoView.setUnitVideosVo(vo);
		
		return unitVideoView;
	}

}
