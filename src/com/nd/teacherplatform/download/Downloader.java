package com.nd.teacherplatform.download;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.nd.teacherplatform.constant.FileConst;
import com.nd.teacherplatform.constant.HandlerConst;
import com.nd.teacherplatform.download.DownLoaderHelp.MyHandler;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.sqlite.HandlerLoadingTable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Downloader
{
	private String localSaveFilePath;// ����·��
	private String localSaveFiledirectory;
	private int threadcount;// �߳���
	private Handler mHandler;// ��Ϣ������
	private int fileSize;// ��Ҫ���ص��ļ��Ĵ�С
	private List<LoadingInfo> infos;// ���������Ϣ��ļ���
	private static final int INIT = 1;// �����������ص�״̬����ʼ��״̬����������״̬����ͣ״̬
	private static final int DOWNLOADING = 2;
	private static final int PAUSE = 3;
	private int state = INIT;
	private VideoInfoVo vo;
	
	/**
	 * �߳�����
	 */
	public ArrayList<MyThread> threads = new ArrayList<MyThread>();

	public Downloader(VideoInfoVo vo, int threadcount, Handler mHandler)
	{
		this.vo = vo;
		this.localSaveFilePath = FileConst.VIDEO_PATH+File.separator+vo.getSubjectName()+File.separator+vo.videoSetName+File.separator+vo.videoName+".mp4";
		this.localSaveFiledirectory = FileConst.VIDEO_PATH+File.separator+vo.getSubjectName()+File.separator+vo.videoSetName;
		this.threadcount = threadcount;
		this.mHandler = mHandler;
		System.out.println("����1");
		new InfoThread().start();
	}
	
	public class InfoThread extends Thread
	{
		public InfoThread()
		{
			
		}
		
		@Override
		public void run()
		{
			 getDownloaderInfors();

		     download();
		} 
	}

	

	/**
	 * �õ�downloader�����Ϣ ���Ƚ����ж��Ƿ��ǵ�һ�����أ�����ǵ�һ�ξ�Ҫ���г�ʼ������������������Ϣ���浽���ݿ���
	 * ������ǵ�һ�����أ��Ǿ�Ҫ�����ݿ��ж���֮ǰ���ص���Ϣ����ʼλ�ã�����Ϊֹ���ļ���С�ȣ�������������Ϣ���ظ�������
	 */
	public LoadingFileInfo getDownloaderInfors()
	{
		System.out.println("����2");
		if (isFirst(vo.id)) 
		{
			System.out.println("����2-1");
			init();
			int range = fileSize / threadcount;
			infos = new ArrayList<LoadingInfo>();
			for (int i = 0; i < threadcount - 1; i++) 
			{
				LoadingInfo info = new LoadingInfo(i, i * range, (i + 1) * range - 1,vo.id, 0, vo.videoURL);
				infos.add(info);
			}
			LoadingInfo info = new LoadingInfo(threadcount - 1, (threadcount - 1) * range, fileSize - 1, vo.id,0, vo.videoURL);
			infos.add(info);
			// ����infos�е����ݵ����ݿ�
			HandlerLoadingTable.saveInfos(infos);
			// ����һ��LoadInfo��������������ľ�����Ϣ
			LoadingFileInfo loadInfo = new LoadingFileInfo(fileSize, 0, vo.videoURL);
			return loadInfo;
		} else 
		{
			System.out.println("����2-2");
			// �õ����ݿ������е�urlstr���������ľ�����Ϣ
			infos = HandlerLoadingTable.getInfos(vo.id);
			 
			int size = 0;
			int compeleteSize = 0;
			for (LoadingInfo info : infos) {
				compeleteSize += info.getCompeleteSize();
				size += info.getEndPos() - info.getStartPos() + 1;
			}

            fileSize = size;
			return new LoadingFileInfo(size, compeleteSize, vo.videoURL);
		}
	}

	/**
	 * ��ʼ��
	 */
	private void init()
	{
		try {
			
			String fileUrl = vo.videoURL;
			fileUrl = URLEncoder.encode(fileUrl,"utf-8").replaceAll("\\+", "%20");  
			fileUrl = fileUrl.replaceAll("%3A", ":").replaceAll("%2F", "/");  
			
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			fileSize = connection.getContentLength();

			File fileDir = new File(localSaveFiledirectory);
			if(!fileDir.exists())
			{
				fileDir.mkdirs();
			}
			
			File file = new File(localSaveFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			// ���ط����ļ�
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.setLength(fileSize);
			accessFile.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ж��Ƿ��ǵ�һ�� ����
	 */
	private boolean isFirst(int videoId)
	{
		return !HandlerLoadingTable.isHasInfors(videoId);
	}
	
	

	/**
	 * �����߳̿�ʼ��������
	 */
	public void download()
	{
		if (infos != null)
		{
			if (state == DOWNLOADING)
			{
				return;
			}
			state = DOWNLOADING;
			for (LoadingInfo info : infos)
			{
				MyThread myThread = new MyThread(info.getThreadId(), info.getStartPos(), info.getEndPos(), info.getCompeleteSize(), info.getUrl());
				myThread.start();
				threads.add(myThread);
			}
		}
	}
	
	

	public class MyThread extends Thread
	{
		private int threadId;
		private int startPos;
		private int endPos;
		private int compeleteSize;
		private String fileUrl;
		public boolean isRun = true;

		public MyThread(int threadId, int startPos, int endPos, int compeleteSize, String fileUrl)
		{
			this.threadId = threadId;
			this.startPos = startPos;
			this.endPos = endPos;
			this.compeleteSize = compeleteSize;
			this.fileUrl = fileUrl;
		}

		@Override
		public void run()
		{
			System.out.println("����3");
			HttpURLConnection connection = null;
			RandomAccessFile randomAccessFile = null;
			InputStream is = null;
			try {
				System.out.println(fileUrl+" ...........................");
				
				int count = 3;
				while (count>0) 
				{
					try {
						
						System.out.println("����4");
						
						String tempUrl = fileUrl;
						
						tempUrl = URLEncoder.encode(tempUrl,"utf-8").replaceAll("\\+", "%20");  
						tempUrl = tempUrl.replaceAll("%3A", ":").replaceAll("%2F", "/");  

						
						URL url = new URL(tempUrl);
						connection = (HttpURLConnection) url.openConnection();
						connection.setConnectTimeout(10000);
						connection.setRequestMethod("GET");
						// ���÷�Χ����ʽΪRange��bytes x-y;
						connection.setRequestProperty("Range", "bytes=" + (startPos + compeleteSize) + "-" + endPos);
		
						randomAccessFile = new RandomAccessFile(localSaveFilePath, "rwd");
						randomAccessFile.seek(startPos + compeleteSize);
						// ��Ҫ���ص��ļ�д�������ڱ���·���µ��ļ���
						is = connection.getInputStream();
					    
						break;
					} catch (Exception e) 
					{
						 count--;
						 e.printStackTrace();
					}
				}
				
				byte[] buffer = new byte[4096];
				int length = -1;
				
				System.out.println("����5");
				
				while (isRun)
				{
					System.out.println("����6"+(state == DOWNLOADING));
					while (state == DOWNLOADING &&(length = is.read(buffer)) != -1) 
					{
						randomAccessFile.write(buffer, 0, length);
						compeleteSize += length;
						// �������ݿ��е�������Ϣ
						HandlerLoadingTable.updataInfos(threadId, compeleteSize, fileUrl);
						// ����Ϣ��������Ϣ�������������Խ��������и���
						Message message = Message.obtain();
						message.what = HandlerConst.PROGRESS_INFO;
						message.obj = vo.id+"";
						message.arg1 = compeleteSize;
	                    message.arg2 = fileSize;
	
	                    if(compeleteSize >= fileSize)
	                    {
	                    	isRun = false;
	                    }
	                    
						mHandler.sendMessage(message);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
					randomAccessFile.close();
					connection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	 

	// ������ͣ
	public void setPause()
	{
		state = PAUSE;
	}
	
	/**
	 * ���ó� ��������
	 */
	public void setDownLoad()
	{
		state = DOWNLOADING;
	}

	// ��������״̬
	public void reset()
	{
		state = INIT;
	}
	
	/**
	 * �ж��Ƿ���������
	 */
	public boolean isdownloading()
	{
		return state == DOWNLOADING;
	}
	
	/**
	 * ֹͣ�߳� 
	 */
	public void stopThread()
	{
		for (int i = 0; i < threads.size(); i++)
		{
			MyThread thread = threads.get(i);
			thread.isRun = false;
			state = INIT;
		}
	}

}
