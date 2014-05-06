package com.nd.teacherplatform.interfaces;

import java.util.ArrayList;

import com.nd.teacherplatform.vo.DownLoadVideoSetVo;
import com.nd.teacherplatform.vo.VideoInfoVo;

/**
 * 下载界面 在 一级和二级界面切换
 * @author zmp
 *
 */
public interface ChangeDownLoadFragmentInterface
{
       public void changeDownLoadFragment(DownLoadVideoSetVo downLoadVideoSetVo,ArrayList<VideoInfoVo> videoInfoVos);
}
