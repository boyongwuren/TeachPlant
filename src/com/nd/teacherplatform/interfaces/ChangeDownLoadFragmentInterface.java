package com.nd.teacherplatform.interfaces;

import java.util.ArrayList;

import com.nd.teacherplatform.vo.DownLoadVideoSetVo;
import com.nd.teacherplatform.vo.VideoInfoVo;

/**
 * ���ؽ��� �� һ���Ͷ��������л�
 * @author zmp
 *
 */
public interface ChangeDownLoadFragmentInterface
{
       public void changeDownLoadFragment(DownLoadVideoSetVo downLoadVideoSetVo,ArrayList<VideoInfoVo> videoInfoVos);
}
