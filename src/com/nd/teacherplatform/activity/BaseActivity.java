package com.nd.teacherplatform.activity;

import com.nd.teacherplatform.util.SingleToolClass;

import android.app.Activity;
import android.os.Bundle;

/**
 * Ӧ�õ� ���� activity
 * ����activity�������BaseActivity
 * @author zmp
 *
 */
public class BaseActivity extends Activity
{

	public BaseActivity()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		SingleToolClass.curContext = this;
	}

}
