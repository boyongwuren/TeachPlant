package com.nd.teacherplatform.download;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.nd.teacherplatform.constant.HandlerConst;
import com.nd.teacherplatform.interfaces.SendLoadProgressInterface;
import com.nd.teacherplatform.util.LogUtil;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.sqlite.HandlerHasDownTable;
import com.nd.teacherplatform.vo.sqlite.HandlerLoadingTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zmp on 13-10-27.
 */
public class DownLoaderHelp
{
    /**
     * 广播消息用
     * */
    private static MyHandler myHandler = new MyHandler();

    /**
     * 记录下载信息
     * key videoId
     * */
    private static Map<String,Downloader> downLoadMap = new HashMap<String, Downloader>();

    /**
     * 需要监听加载进度消息的，往此队列里塞
     * */
    public static ArrayList<SendLoadProgressInterface> slpis = new ArrayList<SendLoadProgressInterface>();

    /**
     * 下载文件
     * */
    public static void downLoadHelp(VideoInfoVo vo)
    {
    	boolean hasDownLoad = HandlerHasDownTable.hasDownLoadVideo(vo.id);
    	
    	System.out.println("下载的视频ID = "+vo.id);
    	
    	if(hasDownLoad)
    	{
    		//已经下载过这个文件了
    		LogUtil.showTip("文件已经下载完成");
    		return ;
    	}
    	
    	if(downLoadMap.get(vo.id+"") == null)
    	{
    		Downloader downloader = new Downloader(vo,1,myHandler);
    		downLoadMap.put(vo.id+"",downloader);
    	}else 
    	{
			LogUtil.showTip("视频正在下载中");
		}
    }
    
    /**
     *停止线程 
     */
    public static void stopLoadThread()
    {
    	for (Downloader downloader : downLoadMap.values()) 
    	{
			downloader.stopThread();
		}
    	
    	downLoadMap.clear();
    }
    
    
    
    /**
     * 是否正在下载
     * @param videoId
     * @return
     */
    public static boolean isDownLoad(int videoId)
    {
    	Downloader downloader = downLoadMap.get(videoId+"");
    	if(downloader != null)
    	{
    		return downloader.isdownloading();
    	}
    	
    	return false;
    }
    
    /**
     * 设置下载
     * @param videoId
     */
    public static void setDownload(int videoId)
    {
    	Downloader downloader = downLoadMap.get(videoId+"");
        if(downloader != null)
        {
        	downloader.setDownLoad();
        }
    }

    /**
     * 设置暂停
     * @param videoId
     */
    public static void setPause(int videoId)
    {
    	Downloader downloader = downLoadMap.get(videoId+"");
    	if(downloader != null)
    	{
    		downloader.setPause();
    	}
    }

    public static class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            int videoId = Integer.parseInt((String)msg.obj);
            int completeSize = msg.arg1;
            int totalSize = msg.arg2;
            
            Log.w("sys", "videoId = "+videoId+" completeSize = "+completeSize+" totalSize = "+totalSize);

            switch (msg.what)
            {
                case HandlerConst.PROGRESS_INFO:
                   for (int i = 0;i<slpis.size();i++)
                   {
                       SendLoadProgressInterface slpi = slpis.get(i);
                       slpi.setLoadFileProgress(videoId,completeSize,totalSize);
                   }
                    break;

            }

            if(completeSize >= totalSize)
            {
            	//文件下载完成了
            	downLoadMap.remove(downLoadMap.get(videoId+""));
            	HandlerHasDownTable.insertDataToTable(videoId);
                HandlerLoadingTable.delete(videoId);
            }
            
        }

        public MyHandler()
        {
            super();
        }
    }
}



