package com.nd.teacherplatform.activity;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.util.RemoveGlobalOnLayoutClass;
import com.nd.teacherplatform.util.RenderStoryInfo;
import com.nd.teacherplatform.vo.CollectVo;
import com.nd.teacherplatform.vo.sqlite.HandlerCollectTable;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 收藏页面 和 下载页面的 父类
 * @author zmp
 *
 */
public abstract  class CollectDownLoadActiviey extends BaseActivity
{
	/**
	 * 左侧学科列表
	 */
	protected RadioGroup subjectRadioGroup;
	
	/**
	 * 回退按钮
	 */
	protected Button backBtn;
	
	/**
	 * 删除全部
	 */
	protected Button deleteAll;
	
	/**
	 * 没有数据的图片提示
	 */
	protected ImageView noCollectImageView;
	
	/**
	 * 当前选中的学科ID
	 */
	protected int curIndex = 0;
	
	private AlertDialog alertDialog;
	
	public CollectDownLoadActiviey()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化方法
	 * <br>onCreate setContentView 后调用
	 */
	protected void init()
	{
		subjectRadioGroup = (RadioGroup) findViewById(R.id.subjectGroup);
		backBtn = (Button) findViewById(R.id.backBtn);
		deleteAll = (Button) findViewById(R.id.deleteAll);
		noCollectImageView = (ImageView) findViewById(R.id.noCollectPic);
		noCollectImageView.setVisibility(View.GONE);
		
		subjectRadioGroup.setOnCheckedChangeListener(new GroupOnCheckedChangeClass());
		backBtn.setOnClickListener(new BackClickClass());
		deleteAll.setOnClickListener(new DeleteAllClass());
		
		RadioButton rGroup = (RadioButton) subjectRadioGroup.getChildAt(0);
		rGroup.setChecked(true);
		
		changeSubjectView(curIndex);
		caulateFileSize(deleteAll);
	}
	
	/**
	 * 点击了左边的学科列表
	 * @author zmp
	 *
	 */
	protected class GroupOnCheckedChangeClass implements OnCheckedChangeListener
	{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			 for (int i = 0; i < subjectRadioGroup.getChildCount(); i++) 
			 {
				View view = subjectRadioGroup.getChildAt(i);
				if(view.getId() == checkedId)
				{
					 //点击了第i个按钮
					curIndex = i;
					changeSubjectView(curIndex);
				}
			 }
		}
	}
	
	/**
	 * 子类重写
	 * 选择了某个学科
	 * 改变视图
	 * @param curIndex
	 */
	protected abstract void changeSubjectView(int curIndex);
	
	/**
	 * 点击了返回
	 * @author zmp
	 *
	 */
	protected class BackClickClass implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			 CollectDownLoadActiviey.this.finish();
		}
	}
	
	
	/**
	 * 删除所有记录
	 * @author zmp
	 *
	 */
	protected class DeleteAllClass implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			alertDialog = new AlertDialog.Builder(CollectDownLoadActiviey.this).create(); 
			alertDialog.show();
			
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.delect_all_collect_tip);
			Button sureButton = (Button) window.findViewById(R.id.sureBtn);
			Button cancelButton = (Button) window.findViewById(R.id.cancelBtn);
			
			sureButton.setOnClickListener(new SureOnClickClass());
			cancelButton.setOnClickListener(new CancelOnClickClass());
		}
	}
	
	
	/**
	 * 取消删除
	 * @author zmp
	 *
	 */
	private class CancelOnClickClass implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			if(alertDialog != null)
			{
				alertDialog.cancel();
			}
		}
		
	}
	
	/**
	 * 确定删除
	 * @author zmp
	 *
	 */
	private class SureOnClickClass implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			if(alertDialog != null)
			{
				alertDialog.cancel();
				doDeleteAll();
			}
		}
	}
	
	
	
	/**
	 * 删除所有数据
	 * 子类重写实现
	 */
	protected abstract void doDeleteAll();
	 
	
	/**
	 * 计算底部 容量的大小 
	 */
	protected void caulateFileSize(View targetView)
	{
		RenderStoryInfo renderStoryInfo = new RenderStoryInfo(this);
		RemoveGlobalOnLayoutClass removeGlobalOnLayoutClass = new RemoveGlobalOnLayoutClass(targetView, renderStoryInfo);
		renderStoryInfo.setRemoveInterface(removeGlobalOnLayoutClass);
		targetView.getViewTreeObserver().addOnGlobalLayoutListener(renderStoryInfo);
	}

}
