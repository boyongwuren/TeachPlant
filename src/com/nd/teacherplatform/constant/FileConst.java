package com.nd.teacherplatform.constant;

import android.os.Environment;

/**
 * 文件存储相关的 文件地址常量
 * @author zmp
 *
 */
public class FileConst
{
	/**
	 * 视频根目录
	 */
     public static final String VIDEO_PATH = Environment.getExternalStorageDirectory().getPath()+ "/onlineVideo/video";
     
     /**
      * 图片跟目录
      */
     public static final String PIC_PATH = Environment.getExternalStorageDirectory().getPath()+ "/onlineVideo/pic";
     
     /**
      * 视频文件的后缀
      */
     public static final String FILE_FORMAT = ".mp4";
 
}
