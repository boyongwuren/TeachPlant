package com.nd.teacherplatform.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.ActionConst;
import com.nd.teacherplatform.constant.SubjectTypeConst;

/**
 * 主界面和各学科的基类activity
 * 
 * @author zmp
 * 
 */
public class HomeSubjectActivity extends BaseActivity
{

	/**
	 * 这个activity是哪个学科的界面
	 */
	private String subjectTypeString;

	/**
	 * 学科type
	 */
	protected int subjectTypeInt = 1;

	private MyBroadcastReciver myBroadcastReciver;

	public HomeSubjectActivity()
	{

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ActionConst.VIDEO_PLAY_OVER);
		myBroadcastReciver = new MyBroadcastReciver();
		this.registerReceiver(myBroadcastReciver, intentFilter);
	}

	@Override
	public void finish()
	{
		try {
			this.unregisterReceiver(myBroadcastReciver);
		} catch (Exception e) {
		}
		super.finish();
	}

	private class MyBroadcastReciver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			// TODO Auto-generated method stub
			String actionString = intent.getAction();
			if (ActionConst.VIDEO_PLAY_OVER.equals(actionString)) {
				updataRecentPlay();
			}
		}
	}

	/**
	 * 更新最近播放的视图 子类重写
	 */
	protected void updataRecentPlay()
	{

	}

	/**
	 * 设置当前页面是那个学科界面的标识
	 * 
	 * @param subjectType
	 */
	public void setSubjectType(String subjectTypeString)
	{
		this.subjectTypeString = subjectTypeString;

		if (this.subjectTypeString.equals(SubjectTypeConst.YUWEN)) {
			findViewById(R.id.yuWenBtn).setSelected(true);
			subjectTypeInt = 1;
		} else if (this.subjectTypeString.equals(SubjectTypeConst.DILI)) {
			subjectTypeInt = 7;
			findViewById(R.id.diLiBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.HUAXUE)) {
			subjectTypeInt = 5;
			findViewById(R.id.huaXueBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.LISHI)) {
			subjectTypeInt = 8;
			findViewById(R.id.liShiBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.QITA)) {
			subjectTypeInt = 10;
			findViewById(R.id.qiTaBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.SHENGWU)) {
			subjectTypeInt = 6;
			findViewById(R.id.shengWuBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.SHUXUE)) {
			subjectTypeInt = 2;
			findViewById(R.id.shuXueBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.WULI)) {
			subjectTypeInt = 4;
			findViewById(R.id.wuLiBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.YINGYU)) {
			subjectTypeInt = 3;
			findViewById(R.id.yingYuBtn).setSelected(true);
		} else if (this.subjectTypeString.equals(SubjectTypeConst.ZHENGZHI)) {
			subjectTypeInt = 9;
			findViewById(R.id.zhengZhiBtn).setSelected(true);
		} else {
			subjectTypeInt = 0;
			findViewById(R.id.homeBtn).setSelected(true);
		}
	}

	/**
	 * 获取当前页面是那个学科界面的标识
	 * 
	 * @return
	 */
	public String getSubjectType()
	{
		return this.subjectTypeString;
	}

	/**
	 * 为底部的按钮添加监听事件
	 */
	protected void addListener()
	{
		findViewById(R.id.homeBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.yuWenBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.shuXueBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.yingYuBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.wuLiBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.huaXueBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.shengWuBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.diLiBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.liShiBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.zhengZhiBtn).setOnClickListener(new BtnClickListener());
		findViewById(R.id.qiTaBtn).setOnClickListener(new BtnClickListener());

		findViewById(R.id.collectTxt).setOnClickListener(new TopRightBtnClickListener());
		findViewById(R.id.searchTxt).setOnClickListener(new TopRightBtnClickListener());
		findViewById(R.id.downLoadManager).setOnClickListener(new TopRightBtnClickListener());
	}

	/**
	 * 底部按钮点击的处理
	 * 
	 * @author zmp
	 * 
	 */
	private class BtnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (v.isSelected() == true) {
				return;
			}

			switch (v.getId())
			{
				case R.id.homeBtn:
					clickHomePage((ImageButton) v);
					break;

				case R.id.yuWenBtn:
					Intent intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.YUWEN);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.shuXueBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.SHUXUE);
					HomeSubjectActivity.this.startActivity(intent);
					HomeSubjectActivity.this.finish();
					break;

				case R.id.yingYuBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.YINGYU);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.wuLiBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.WULI);
					HomeSubjectActivity.this.startActivity(intent);
					HomeSubjectActivity.this.finish();
					break;

				case R.id.huaXueBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.HUAXUE);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.shengWuBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.SHENGWU);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.diLiBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.DILI);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.liShiBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.LISHI);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.zhengZhiBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.ZHENGZHI);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.qiTaBtn:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SubjectPageActivity.class);
					intent.putExtra(SubjectTypeConst.CATEGORY_KEY, SubjectTypeConst.QITA);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				default:
					break;
			}

			overridePendingTransition(0, 0);

			if(HomeSubjectActivity.this instanceof HomePageActivity)
			{
				
			}else 
			{
				HomeSubjectActivity.this.finish();
			}
		}

	}

	/**
	 * 右上按钮点击的处理
	 * 
	 * @author zmp
	 * 
	 */
	private class TopRightBtnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (v.isSelected() == true) {
				return;
			}

			Intent intent;
			switch (v.getId())
			{
				case R.id.downLoadManager:
					intent = new Intent(HomeSubjectActivity.this, DownLoadPageActivity.class);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.collectTxt:
					intent = new Intent(HomeSubjectActivity.this, CollectPageActivity.class);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				case R.id.searchTxt:
					intent = new Intent(HomeSubjectActivity.this.getApplicationContext(), SearchPageActivity.class);
					HomeSubjectActivity.this.startActivity(intent);
					break;

				default:
					break;

			}
		}

	}

	/**
	 * 点击首页按钮
	 * 
	 * @param button
	 */
	protected void clickHomePage(ImageButton button)
	{
		Intent intent = new Intent(this, HomePageActivity.class);
		startActivity(intent);
	}

}
