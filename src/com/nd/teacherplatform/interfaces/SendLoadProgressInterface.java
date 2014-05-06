package com.nd.teacherplatform.interfaces;

/**
 * 通知 文件加载的进度
 * Created by zmp on 13-10-27.
 */
public interface SendLoadProgressInterface
{
          public void setLoadFileProgress(int videoId,int hasComplete,int totalSize);
}
