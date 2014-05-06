package com.nd.teacherplatform.handler;

import android.os.Handler;
import android.os.Message;

/**
 * 计时器
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
	 * @param sleepTime 间隔多少毫秒执行一次
	 * @param runCount  执行多少次
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
