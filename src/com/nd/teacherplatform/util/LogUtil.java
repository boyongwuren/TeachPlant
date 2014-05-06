package com.nd.teacherplatform.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

/**
 * 日志�?
 * @author 李永�?
 *
 */
public class LogUtil {
	public static boolean debugEnable = false;

	public static void e(String tag, String msg) {
		if(debugEnable)
			Log.e(tag, msg);
	}

	public static void w(String tag, String msg) {
		if(debugEnable)
			Log.w(tag, msg);
	}
	
	public static void i(String tag, String msg) {
		if(debugEnable)
			Log.i(tag, msg);
	}
	
	public static void d(String tag, String msg) {
		if(debugEnable)
			Log.d(tag, msg);
	}

	public static void v(String tag, String msg) {
		if(debugEnable)
			Log.v(tag, msg);
	}

	public static void setDebugEnable(boolean debugEnable) {
		LogUtil.debugEnable = debugEnable;
	}

	public static boolean isDebugenable() {
		return debugEnable;
	}
	
	/**
	 * ��ʾ��������������Ϣ
	 * @param context
	 * @param info
	 */
	public static void showServerBackInfo(String info)
	{
		 AlertDialog alertDialog = new AlertDialog.Builder(SingleToolClass.curContext).
					setMessage(info).
					setTitle("����˷�������").
					setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.cancel();
						}
					}).create();
			alertDialog.show();
	}
	
	/**
	 * toast ��ʾ
	 * @param text
	 */
	public static void showTip(String text)
	{
		Toast toast = Toast.makeText(SingleToolClass.curContext, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	
	
	
}
