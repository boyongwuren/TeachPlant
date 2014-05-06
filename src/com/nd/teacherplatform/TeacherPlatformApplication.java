package com.nd.teacherplatform;

import java.util.ArrayList;

import com.nd.teacherplatform.constant.FileConst;
import com.nd.teacherplatform.constant.SharedPreferenceKey;
import com.nd.teacherplatform.download.DownLoaderHelp;
import com.nd.teacherplatform.util.FileUtils;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.util.Tools;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.sqlite.HandlerLoadingTable;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoInfoTable;

import android.app.Application;
import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * ��������ʱ�����ȵ��õ�
 * ����һЩ���ݵĳ�ʼ��
 * @author zmp
 *
 */
public class TeacherPlatformApplication extends Application 
{

	public TeacherPlatformApplication() 
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		//�������ݵĳ�ʼ��
		SingleToolClass.curContext = this;
		
		SingleToolClass.sharedPreferences = this.getSharedPreferences(SharedPreferenceKey.SHAREDPREFERENCEKEY, 0);
		
		SingleToolClass.windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		
		SingleToolClass.telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
		DisplayMetrics dm = new DisplayMetrics();   
		SingleToolClass.windowManager.getDefaultDisplay().getMetrics(dm);   
	    System.out.println("��Ļ�ֱ���Ϊ:"+dm.widthPixels+" * "+dm.heightPixels); 
	    System.out.println("״̬��:"+ Tools.getStatusBarHeight()); 
	    
	    final float scale = this.getResources().getDisplayMetrics().density;
	    System.out.println("scale = "+scale);
	    
	    System.out.println("serverid = "+Secure.getString(this.getContentResolver(), Secure.ANDROID_ID));

        SingleToolClass.imageDownloader = new ImageDownloader(this);
	 
        FileUtils.createIfNoExists(FileConst.PIC_PATH);
        FileUtils.createIfNoExists(FileConst.VIDEO_PATH);
        
	}

}
