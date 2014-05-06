package com.nd.teacherplatform.constant;

import android.os.Environment;

/**
 * �ļ��洢��ص� �ļ���ַ����
 * @author zmp
 *
 */
public class FileConst
{
	/**
	 * ��Ƶ��Ŀ¼
	 */
     public static final String VIDEO_PATH = Environment.getExternalStorageDirectory().getPath()+ "/onlineVideo/video";
     
     /**
      * ͼƬ��Ŀ¼
      */
     public static final String PIC_PATH = Environment.getExternalStorageDirectory().getPath()+ "/onlineVideo/pic";
     
     /**
      * ��Ƶ�ļ��ĺ�׺
      */
     public static final String FILE_FORMAT = ".mp4";
 
}
