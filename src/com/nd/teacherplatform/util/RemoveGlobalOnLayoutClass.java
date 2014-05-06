package com.nd.teacherplatform.util;

import android.view.View;

import com.nd.teacherplatform.interfaces.RemoveGlobalOnLayoutInterface;

/**
 * ÒÆ³ýÊÂ¼þ
 * @author zmp
 *
 */
public class RemoveGlobalOnLayoutClass implements RemoveGlobalOnLayoutInterface
{

	private View targetView;
	
	private RenderStoryInfo renderStoryInfo;
	
	public RemoveGlobalOnLayoutClass(View targetView , RenderStoryInfo renderStoryInfo)
	{
		this.targetView = targetView;
		this.renderStoryInfo = renderStoryInfo;
	}

	@Override
	public void removeGlobalOnLayout()
	{
		// TODO Auto-generated method stub
		this.targetView.getViewTreeObserver().removeGlobalOnLayoutListener(renderStoryInfo);
	}

}
