package com.nd.teacherplatform.view;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.interfaces.DeleteCollectItemInterface;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.CollectVo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �ղ��б�� item ��ͼ
 * @author zmp
 *
 */
public class CollectItemView extends LinearLayout
{

	private ImageView videoPreView;
	
	private TextView videoSetName;
	private TextView subjectName;
	private TextView requireName;
	private TextView videoName;
	private TextView teacherName;
	
	private ImageButton deleteItemBtn;
	
	private CollectVo vo;
	
	/**
	 * ����
	 */
	private GestureDetector gestureDetector;
	
	private DeleteCollectItemInterface deleteCollectItemIntenface;
	
	public CollectItemView(Context context , DeleteCollectItemInterface deleteCollectItemIntenface)
	{
		super(context);
		LayoutInflater.from(SingleToolClass.curContext).inflate(R.layout.collect_item, this, true);
		
		videoPreView = (ImageView) findViewById(R.id.videoPreView);
		videoSetName = (TextView) findViewById(R.id.videoSetName);
		subjectName = (TextView) findViewById(R.id.subjectName);
		requireName = (TextView) findViewById(R.id.requireName);
		videoName = (TextView) findViewById(R.id.videoName);
		teacherName = (TextView) findViewById(R.id.teacherName);
		deleteItemBtn = (ImageButton) findViewById(R.id.deleteItemBtn);
		deleteItemBtn.setVisibility(View.GONE);
		deleteItemBtn.setOnClickListener(new DeleteItemClickClass());
		gestureDetector = new GestureDetector(context, new ItemOnGestureClass());
		
		this.setOnTouchListener(new ItemOnTouchClass());
		
		this.deleteCollectItemIntenface = deleteCollectItemIntenface;
	}
	
	/**
	 * �Ƿ���ʾ ɾ����ť
	 * @param isShow
	 */
	public void hideDeleteItemBtn(Boolean isShow)
	{
		if(isShow)
		{
			deleteItemBtn.setVisibility(View.VISIBLE);
		}else 
		{
			deleteItemBtn.setVisibility(View.GONE);
		}
	}
	
	private class ItemOnTouchClass implements OnTouchListener
	{

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			// һ��Ҫ����true����Ȼ��ȡ�����������¼�
			if(deleteCollectItemIntenface != null&&event.getAction() == MotionEvent.ACTION_DOWN)
			{
				deleteCollectItemIntenface.hideAllDeleteBtn();
			}
			gestureDetector.onTouchEvent(event);
			return true;
		}
	}
	
	/**
	 * �����ж�
	 * @author zmp
	 *
	 */
	private class ItemOnGestureClass extends SimpleOnGestureListener
	{

		/**
		 *  �����ƶ��ľ���
		 *  ����Ϊ����
		 */
		private int moveDis = 0;
		
		/**
		 * ����
		 */
		@Override
		public boolean onSingleTapUp(MotionEvent e)
		{
			System.out.println("onSingleTapUp");
			return super.onSingleTapUp(e);
		}

		/**
		 * ����
		 */
		@Override
		public void onLongPress(MotionEvent e)
		{
			System.out.println("onLongPress");
			super.onLongPress(e);
		}

		/**
		 * ����
		 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			moveDis += distanceX;
			if(Math.abs(moveDis) >=300)
			{
				//��ʾ��ť
				deleteItemBtn.setVisibility(View.VISIBLE);
			}
			System.out.println("onScroll"+" distanceX = "+distanceX+" distanceY = "+distanceY);
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		/**
		 * ��
		 */
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			System.out.println("onFling"+" velocityX = "+velocityX+" velocityY = "+velocityY);
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onShowPress(MotionEvent e)
		{
			System.out.println("onShowPress");
			super.onShowPress(e);
		}

		@Override
		public boolean onDown(MotionEvent e)
		{
			moveDis = 0;
			System.out.println("onDown");
			return super.onDown(e);
		}

		/**
		 * ˫��
		 */
		@Override
		public boolean onDoubleTap(MotionEvent e)
		{
			System.out.println("onDoubleTap");
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e)
		{
			System.out.println("onDoubleTapEvent");
			return super.onDoubleTapEvent(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e)
		{
			System.out.println("onSingleTapConfirmed");
			return super.onSingleTapConfirmed(e);
		}

	}
	
	/**
	 * �����ɾ����ť
	 * @author zmp
	 *
	 */
	private class DeleteItemClickClass implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if(deleteCollectItemIntenface != null)
			{
				deleteCollectItemIntenface.deleteCollectItem(vo);
			}
		}
	}

	public CollectItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��������
	 * @param vo
	 */
	public void setCollectVo(CollectVo vo)
	{
		this.vo = vo;
		
		SingleToolClass.imageDownloader.download(vo.vo.preViewUrlCom, videoPreView, R.drawable.error_net_pic, R.drawable.error_net_pic, true);
		videoSetName.setText("��"+vo.vo.videoSetName+"��");
		subjectName.setText(vo.vo.getSubjectName());
		requireName.setText(vo.vo.videoRequireTypeName);
		videoName.setText("��"+vo.vo.videoName+"��");
		teacherName.setText("��ʦ��"+vo.vo.teacherName);
	}
	
	public CollectVo getCollectVo()
	{
		return vo;
	}

}
