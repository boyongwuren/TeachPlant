package com.nd.teacherplatform.vo;

/**
 * 视频集的数据结构
 * @author zmp
 *
 */
public class VideoSetVo 
{
	/**
	 * 视频集的ID
	 */
	public int id = 0;

	/**
	 * 视频预览图片的地址
	 */
     public String preViewPicUrl = "";
   	 
     /**
      * 视频集的名称
      */
     public String videoSetName = "";
     
     /**
      * 总共视频的节数
      */
     public int totalVideoNum = 0;
     
     /**
      * 已经下载的集数
      */
     public int hasDownNum = 0;
     
}
