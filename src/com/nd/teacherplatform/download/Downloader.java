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
	private String localSaveFilePath;// 保存路径
	private String localSaveFiledirectory;
	private int threadcount;// 线程数
	private Handler mHandler;// 消息处理器
	private int fileSize;// 所要下载的文件的大小
	private List<LoadingInfo> infos;// 存放下载信息类的集合
	private static final int INIT = 1;// 定义三种下载的状态：初始化状态，正在下载状态，暂停状态
	private static final int DOWNLOADING = 2;
	private static final int PAUSE = 3;
	private int state = INIT;
	private VideoInfoVo vo;
	
	/**
	 * 线程容器
	 */
	public ArrayList<MyThread> threads = new ArrayList<MyThread>();

	public Downloader(VideoInfoVo vo, int threadcount, Handler mHandler)
	{
		this.vo = vo;
		this.localSaveFilePath = FileConst.VIDEO_PATH+File.separator+vo.getSubjectName()+File.separator+vo.videoSetName+File.separator+vo.videoName+".mp4";
		this.localSaveFiledirectory = FileConst.VIDEO_PATH+File.separator+vo.getSubjectName()+File.separator+vo.videoSetName;
		this.threadcount = threadcount;
		this.mHandler = mHandler;
		System.out.println("下载1");
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
	 * 得到downloader里的信息 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中
	 * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），并将下载信息返回给下载器
	 */
	public LoadingFileInfo getDownloaderInfors()
	{
		System.out.println("下载2");
		if (isFirst(vo.id)) 
		{
			System.out.println("下载2-1");
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
			// 保存infos中的数据到数据库
			HandlerLoadingTable.saveInfos(infos);
			// 创建一个LoadInfo对象记载下载器的具体信息
			LoadingFileInfo loadInfo = new LoadingFileInfo(fileSize, 0, vo.videoURL);
			return loadInfo;
		} else 
		{
			System.out.println("下载2-2");
			// 得到数据库中已有的urlstr的下载器的具体信息
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
	 * 初始化
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
			// 本地访问文件
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.setLength(fileSize);
			accessFile.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是第一次 下载
	 */
	private boolean isFirst(int videoId)
	{
		return !HandlerLoadingTable.isHasInfors(videoId);
	}
	
	

	/**
	 * 利用线程开始下载数据
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
			System.out.println("下载3");
			HttpURLConnection connection = null;
			RandomAccessFile randomAccessFile = null;
			InputStream is = null;
			try {
				System.out.println(fileUrl+" ...........................");
				
				int count = 3;
				while (count>0) 
				{
					try {
						
						System.out.println("下载4");
						
						String tempUrl = fileUrl;
						
						tempUrl = URLEncoder.encode(tempUrl,"utf-8").replaceAll("\\+", "%20");  
						tempUrl = tempUrl.replaceAll("%3A", ":").replaceAll("%2F", "/");  

						
						URL url = new URL(tempUrl);
						connection = (HttpURLConnection) url.openConnection();
						connection.setConnectTimeout(10000);
						connection.setRequestMethod("GET");
						// 设置范围，格式为Range：bytes x-y;
						connection.setRequestProperty("Range", "bytes=" + (startPos + compeleteSize) + "-" + endPos);
		
						randomAccessFile = new RandomAccessFile(localSaveFilePath, "rwd");
						randomAccessFile.seek(startPos + compeleteSize);
						// 将要下载的文件写到保存在保存路径下的文件中
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
				
				System.out.println("下载5");
				
				while (isRun)
				{
					System.out.println("下载6"+(state == DOWNLOADING));
					while (state == DOWNLOADING &&(length = is.read(buffer)) != -1) 
					{
						randomAccessFile.write(buffer, 0, length);
						compeleteSize += length;
						// 更新数据库中的下载信息
						HandlerLoadingTable.updataInfos(threadId, compeleteSize, fileUrl);
						// 用消息将下载信息传给进度条，对进度条进行更新
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

	 

	// 设置暂停
	public void setPause()
	{
		state = PAUSE;
	}
	
	/**
	 * 设置成 正在下载
	 */
	public void setDownLoad()
	{
		state = DOWNLOADING;
	}

	// 重置下载状态
	public void reset()
	{
		state = INIT;
	}
	
	/**
	 * 判断是否正在下载
	 */
	public boolean isdownloading()
	{
		return state == DOWNLOADING;
	}
	
	/**
	 * 停止线程 
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
