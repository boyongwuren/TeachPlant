package com.nd.teacherplatform.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;

 
/**
 * 一些常用的全局属性
 * SharedPreferences
 * WindowManager
 * @author zmp
 *
 */
public class SingleToolClass
{

	/**
	 * 应用是否首次启动
	 */
	 public static boolean isFirstInit = true;
	
	 public static SharedPreferences sharedPreferences;
	 
	 public static WindowManager windowManager;
	 
	 public static TelephonyManager telephonyManager;
	 
	 /**
	  * 应用程序的上下文
	  */
	 public static Context curContext;
	 
	 /**
	  * 保存加载提示的视图
	  */
	 public static View loadTipView;
	 
	 /**
	  * 返回屏幕的宽度
	 * @return
	 */
	public static int getScreenWidth()
	 {
		 return windowManager.getDefaultDisplay().getWidth();
	 }
	 
	/**
	 * 返回屏幕的高度
	 * @return
	 */
	 public static int getScreenHeight()
	 {
		 return windowManager.getDefaultDisplay().getHeight();
	 }

	 /**
	  * 获取设备ＩＤ
	 * @return
	 */
	public static String getServerId()
	 {
		 return Secure.getString(SingleToolClass.curContext.getContentResolver(), Secure.ANDROID_ID);
	 }
	
	/**
	 * 图片下载器
	 */
	public static ImageDownloader imageDownloader;
}
