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
 * �ղ�ҳ�� �� ����ҳ��� ����
 * @author zmp
 *
 */
public abstract  class CollectDownLoadActiviey extends BaseActivity
{
	/**
	 * ���ѧ���б�
	 */
	protected RadioGroup subjectRadioGroup;
	
	/**
	 * ���˰�ť
	 */
	protected Button backBtn;
	
	/**
	 * ɾ��ȫ��
	 */
	protected Button deleteAll;
	
	/**
	 * û�����ݵ�ͼƬ��ʾ
	 */
	protected ImageView noCollectImageView;
	
	/**
	 * ��ǰѡ�е�ѧ��ID
	 */
	protected int curIndex = 0;
	
	private AlertDialog alertDialog;
	
	public CollectDownLoadActiviey()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��ʼ������
	 * <br>onCreate setContentView �����
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
	 * �������ߵ�ѧ���б�
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
					 //����˵�i����ť
					curIndex = i;
					changeSubjectView(curIndex);
				}
			 }
		}
	}
	
	/**
	 * ������д
	 * ѡ����ĳ��ѧ��
	 * �ı���ͼ
	 * @param curIndex
	 */
	protected abstract void changeSubjectView(int curIndex);
	
	/**
	 * ����˷���
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
	 * ɾ�����м�¼
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
	 * ȡ��ɾ��
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
	 * ȷ��ɾ��
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
	 * ɾ����������
	 * ������дʵ��
	 */
	protected abstract void doDeleteAll();
	 
	
	/**
	 * ����ײ� �����Ĵ�С 
	 */
	protected void caulateFileSize(View targetView)
	{
		RenderStoryInfo renderStoryInfo = new RenderStoryInfo(this);
		RemoveGlobalOnLayoutClass removeGlobalOnLayoutClass = new RemoveGlobalOnLayoutClass(targetView, renderStoryInfo);
		renderStoryInfo.setRemoveInterface(removeGlobalOnLayoutClass);
		targetView.getViewTreeObserver().addOnGlobalLayoutListener(renderStoryInfo);
	}

}
