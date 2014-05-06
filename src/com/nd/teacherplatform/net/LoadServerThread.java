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
 * ��������߳�
 * ȡapi�ӿ�
 * @author Administrator
 *
 */
public class LoadServerThread extends Thread 
{

	/**
	 * ����õ�ַ
	 */
	private String loadUrl = "";
	
	/**
	 * ��������handler
	 */
	private Handler handler;
	
	/**
	 * ����
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
			//����HttpPost�����趨ͷ��ʽ
			HttpPost httpPost = new HttpPost(loadUrl);
			httpPost.addHeader("charset", "utf-8");  
			httpPost.addHeader("Content-Type", "application/json"); 


			//��װ�ύ����
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

			//ִ���ύ
			HttpResponse httpResponse = null;
			httpResponse = httpclient.execute(httpPost);
			//�жϷ���״̬
			int statusCode=httpResponse.getStatusLine().getStatusCode();

			String responseBody = "";
			
			if(statusCode != HttpStatus.SC_OK) 
			{
				//����
				System.out.println("������ء����Ǵ���");
				msg.what = HandlerConst.LOAD_FALL;
				dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "������ء����Ǵ��� statusCode = "+statusCode);
			}else if(statusCode == HttpStatus.SC_OK)
			{
				responseBody = EntityUtils.toString(httpResponse.getEntity());
				msg.what = HandlerConst.LOAD_OK;
				dataBundle.putString(HandlerConst.LOAD_FILE_KEY, responseBody);
			}else 
			{
				System.out.println("������ء��������");
				msg.what = HandlerConst.LOAD_FALL;
				dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "������ء��������");
			}
			
			
		} catch (MalformedURLException e)
        {
			msg.what = HandlerConst.LOAD_FALL;
			dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "=================�쳣 Mal");
			System.out.println("=================�쳣 Mal");
			e.printStackTrace();
			// TODO: handle exception
		}catch (IOException e) 
		{
			msg.what = HandlerConst.LOAD_FALL;
			dataBundle.putString(HandlerConst.LOAD_FILE_KEY, "=================�쳣 io");
			System.out.println("=================�쳣 io");
			e.printStackTrace();
			// TODO: handle exception
		}finally
		{
			
		}
		
		 msg.setData(dataBundle);
         handler.sendMessage(msg);
	        
	}

}
