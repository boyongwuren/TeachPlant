package com.nd.teacherplatform.net;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.nd.teacherplatform.constant.HandlerConst;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 请求服务线程
 * 取api接口
 * @author Administrator
 *
 */
public class LoadServerThread extends Thread 
{

	/**
	 * 请求得地址
	 */
	private String loadUrl = "";
	
	/**
	 * 处理结果的handler
	 */
	private Handler handler;
	
	/**
	 * 参数
	 */
	private String paramsString = "";
   	
	public LoadServerThread(String loadUrl,Handler handler,String paramsString) 
	{
		this.loadUrl = loadUrl;
		this.handler = handler;
		this.paramsString = paramsString;
	}

	public LoadServerThread(Runnable runnable) {
		super(runnable);
		// TODO Auto-generated constructor stub
	}

	public LoadServerThread(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
	}

	public LoadServerThread(Runnable runnable, String threadName) {
		super(runnable, threadName);
		// TODO Auto-generated constructor stub
	}

	public LoadServerThread(ThreadGroup group, Runnable runnable) {
		super(group, runnable);
		// TODO Auto-generated constructor stub
	}

	public LoadServerThread(ThreadGroup group, String threadName) {
		super(group, threadName);
		// TODO Auto-generated constructor stub
	}

	public LoadServerThread(ThreadGroup group, Runnable runnable,
			String threadName) {
		super(group, runnable, threadName);
		// TODO Auto-generated constructor stub
	}

	public LoadServerThread(ThreadGroup group, Runnable runnable,
			String threadName, long stackSize) {
		super(group, runnable, threadName, stackSize);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run()
	{
		    Message msg = new Message();
		    Bundle dataBundle = new Bundle();
		try 
		{
			//创建HttpPost对象，设定头格式
			HttpPost httpPost = new HttpPost(loadUrl);
			httpPost.addHeader("charset", "utf-8");  
			httpPost.addHeader("Content-Type", "application/json"); 


			//封装提交参数
			BasicHttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("charset", "utf-8"); 
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
			HttpConnectionParams.setSoTimeout(httpParams,10000);
			HttpClient httpclient = new DefaultHttpClient(httpParams);

			String result = paramsString;

//			result=URLEncoder.encode(result,"utf-8");
			StringEntity s = new StringEntity(result,"utf-8");  
			s.setContentType("application/json");  
			httpPost.setEntity(s);

			//执行提交
			HttpResponse httpResponse = null;
			httpResponse = httpclient.execute(httpPost);
			//判断返回状态
			int statusCode=httpResponse.getStatusLine().getStatusCode();

			String responseBody = "";
			
			if(statusCode != HttpStatus.SC_OK) 
			{
				//错误
				System.out.println("结果返回。但是错误");
				msg.what = HandlerConst.LOAD_FALL;
				dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "结果返回。但是错误 statusCode = "+statusCode);
			}else if(statusCode == HttpStatus.SC_OK)
			{
				responseBody = EntityUtils.toString(httpResponse.getEntity());
				msg.what = HandlerConst.LOAD_OK;
				dataBundle.putString(HandlerConst.LOAD_FILE_KEY, responseBody);
			}else 
			{
				System.out.println("结果返回。情况不明");
				msg.what = HandlerConst.LOAD_FALL;
				dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "结果返回。情况不明");
			}
			
			
		} catch (MalformedURLException e)
        {
			msg.what = HandlerConst.LOAD_FALL;
			dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "=================异常 Mal");
			System.out.println("=================异常 Mal");
			e.printStackTrace();
			// TODO: handle exception
		}catch (IOException e) 
		{
			msg.what = HandlerConst.LOAD_FALL;
			dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "=================异常 io");
			System.out.println("=================异常 io");
			e.printStackTrace();
			// TODO: handle exception
		}finally
		{
			
		}
		
		 msg.setData(dataBundle);
         handler.sendMessage(msg);
	        
	}

}
