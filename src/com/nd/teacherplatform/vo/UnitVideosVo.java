package com.nd.teacherplatform.vo;

import java.util.ArrayList;

/**
 * 每个单元的视频的信息
 * @author zmp
 *
 */
public class UnitVideosVo 
{
	/**
	 * 第几单元的ID标识
	 */
   public int id = 0;
	 
   /**
    * 单元名称
    */
   public String unitName = "";
   
   /**
    * 单元里面 视频的 信息
    */
   public ArrayList<VideoInfoVo> videoInfoVos;

}
