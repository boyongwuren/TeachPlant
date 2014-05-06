package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import com.nd.teacherplatform.interfaces.DeleteCollectItemInterface;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.view.CollectItemView;
import com.nd.teacherplatform.vo.CollectVo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 收藏列表的适配器
 * @author zmp
 *
 */
public class CollectListAdapter extends BaseAdapter
{

	private ArrayList<CollectVo> cvos;
	
	private DeleteCollectItemInterface deleteCollectItemIntenface;
	
	public CollectListAdapter(ArrayList<CollectVo> cVos,DeleteCollectItemInterface deleteCollectItemIntenface)
	{
		this.cvos = cVos;
		this.deleteCollectItemIntenface = deleteCollectItemIntenface;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return cvos.size();
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
		 CollectItemView collectItemView ;
		 
		 if(convertView == null)
		 {
			 convertView = new CollectItemView(SingleToolClass.curContext,deleteCollectItemIntenface);
		 }
		 
		 collectItemView = (CollectItemView) convertView;
		 collectItemView.hideDeleteItemBtn(false);
		 
		 CollectVo vo = cvos.get(position);
		 
		 collectItemView.setCollectVo(vo);
		 
		return collectItemView;
	}

}
