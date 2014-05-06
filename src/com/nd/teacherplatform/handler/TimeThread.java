package com.nd.teacherplatform.handler;

import android.os.Handler;
import android.os.Message;

/**
 * ��ʱ��
 * @author zmp
 *
 */
public class TimeThread extends Thread
{

	private Handler handler;
	
	private int sleepTime;
	
	private int runCount;
	
	/**
	 * @param handler
	 * @param sleepTime ������ٺ���ִ��һ��
	 * @param runCount  ִ�ж��ٴ�
	 */
	public TimeThread(Handler handler ,int sleepTime,int runCount)
	{
		this.handler = handler;
		this.sleepTime = sleepTime;
		this.runCount = runCount;
	}
	
	@Override
	public void run()
	{
		while(runCount>0)
		{
			
			runCount--;
			 try 
			 {
				Thread.sleep(sleepTime);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		}
	}

 
}
