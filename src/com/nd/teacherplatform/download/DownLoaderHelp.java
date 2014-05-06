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
     * �㲥��Ϣ��
     * */
    private static MyHandler myHandler = new MyHandler();

    /**
     * ��¼������Ϣ
     * key videoId
     * */
    private static Map<String,Downloader> downLoadMap = new HashMap<String, Downloader>();

    /**
     * ��Ҫ�������ؽ�����Ϣ�ģ����˶�������
     * */
    public static ArrayList<SendLoadProgressInterface> slpis = new ArrayList<SendLoadProgressInterface>();

    /**
     * �����ļ�
     * */
    public static void downLoadHelp(VideoInfoVo vo)
    {
    	boolean hasDownLoad = HandlerHasDownTable.hasDownLoadVideo(vo.id);
    	
    	System.out.println("���ص���ƵID = "+vo.id);
    	
    	if(hasDownLoad)
    	{
    		//�Ѿ����ع�����ļ���
    		LogUtil.showTip("�ļ��Ѿ��������");
    		return ;
    	}
    	
    	if(downLoadMap.get(vo.id+"") == null)
    	{
    		Downloader downloader = new Downloader(vo,1,myHandler);
    		downLoadMap.put(vo.id+"",downloader);
    	}else 
    	{
			LogUtil.showTip("��Ƶ����������");
		}
    }
    
    /**
     *ֹͣ�߳� 
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
     * �Ƿ���������
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
     * ��������
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
     * ������ͣ
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
            	//�ļ����������
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



