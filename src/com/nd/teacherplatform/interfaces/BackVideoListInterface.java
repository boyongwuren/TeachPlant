package com.nd.teacherplatform.interfaces;

import java.util.ArrayList;

import com.nd.teacherplatform.vo.UnitVideosVo;

/**
 * 获取 每一个必修下面的视频列表
 * @author zmp
 *
 */
public interface BackVideoListInterface
{
      public void setVideoList(ArrayList<UnitVideosVo> uvos);
}
