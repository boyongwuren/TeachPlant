package com.nd.teacherplatform.util;

import com.nd.teacherplatform.constant.HandlerConst;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.net.LoadServerThread;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * �����࣬�������ط���˽ӿڵ�ͳһ���
 * @author zmp
 *
 */
public class LoadServerAPIHelp 
{

	public LoadServerAPIHelp()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * �����������
	 * ���������̼߳��ط���˶�Ӧ�Ľӿ�
	 * @param serverApiUrl
	 * @param loadApiBackInterface ������ɵĻص�
	 */
	public static void loadServerApi(String serverApiUrl,LoadApiBackInterface loadApiBackInterface,String paramString)
	{
		if(SingleToolClass.loadTipView != null)
		{
			SingleToolClass.loadTipView.setVisibility(View.VISIBLE);
		}
		new LoadServerThread(serverApiUrl, new LoadDataHandler(loadApiBackInterface),paramString).start();
	}
	
	
	/**
	 * ������ط���˽ӿ���ɵĲ���
	 * @author zmp
	 *
	 */
	private static class LoadDataHandler extends Handler
	{
		private LoadApiBackInterface loadApiBackInterface;
		
		
		public LoadDataHandler(LoadApiBackInterface loadApiBackInterface)
		{
			this.loadApiBackInterface = loadApiBackInterface;
		}
		
		@Override
		public void handleMessage(Message msg)
		{
			if(SingleToolClass.loadTipView != null)
			{
				SingleToolClass.loadTipView.setVisibility(View.GONE);
			}
			switch (msg.what) 
			{
				case HandlerConst.LOAD_OK:
					if(loadApiBackInterface != null)
					{
						loadApiBackInterface.loadApiBackString(msg.getData().getString(HandlerConst.LOAD_FILE_KEY));
					}
					break;
					
				case HandlerConst.LOAD_FALL:
					break;
					
				default:
					break;
			}
			
			LogUtil.showServerBackInfo(msg.getData().getString(HandlerConst.LOAD_FILE_KEY));
		}
	}

}
