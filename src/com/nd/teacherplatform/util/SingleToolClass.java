package com.nd.teacherplatform.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;

 
/**
 * һЩ���õ�ȫ������
 * SharedPreferences
 * WindowManager
 * @author zmp
 *
 */
public class SingleToolClass
{

	/**
	 * Ӧ���Ƿ��״�����
	 */
	 public static boolean isFirstInit = true;
	
	 public static SharedPreferences sharedPreferences;
	 
	 public static WindowManager windowManager;
	 
	 public static TelephonyManager telephonyManager;
	 
	 /**
	  * Ӧ�ó����������
	  */
	 public static Context curContext;
	 
	 /**
	  * ���������ʾ����ͼ
	  */
	 public static View loadTipView;
	 
	 /**
	  * ������Ļ�Ŀ��
	 * @return
	 */
	public static int getScreenWidth()
	 {
		 return windowManager.getDefaultDisplay().getWidth();
	 }
	 
	/**
	 * ������Ļ�ĸ߶�
	 * @return
	 */
	 public static int getScreenHeight()
	 {
		 return windowManager.getDefaultDisplay().getHeight();
	 }

	 /**
	  * ��ȡ�豸�ɣ�
	 * @return
	 */
	public static String getServerId()
	 {
		 return Secure.getString(SingleToolClass.curContext.getContentResolver(), Secure.ANDROID_ID);
	 }
	
	/**
	 * ͼƬ������
	 */
	public static ImageDownloader imageDownloader;
}
