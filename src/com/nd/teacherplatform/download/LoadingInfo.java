package com.nd.teacherplatform.download;

/**
 * 创建一个下载信息的实体类
 */
public class LoadingInfo
{
	private int threadId;// 下载器id
	private int startPos;// 开始点
	private int endPos;// 结束点
	private int compeleteSize;// 完成度
	private String fileUrl;// 下载器网络标识
	private int loadingVideoId = 0;//正在下载视频的Id

	public LoadingInfo(int threadId, int startPos, int endPos, int loadingVideoId,int compeleteSize, String fileUrl)
	{
		this.threadId = threadId;
		this.startPos = startPos;
		this.endPos = endPos;
		this.compeleteSize = compeleteSize;
		this.fileUrl = fileUrl;
		this.loadingVideoId = loadingVideoId;
	}

	public LoadingInfo()
	{
	}

	public String getUrl()
	{
		return fileUrl;
	}

	public void setUrl(String url)
	{
		this.fileUrl = url;
	}

	public int getThreadId()
	{
		return threadId;
	}

	public void setThreadId(int threadId)
	{
		this.threadId = threadId;
	}

	public int getStartPos()
	{
		return startPos;
	}

	public void setStartPos(int startPos)
	{
		this.startPos = startPos;
	}

	public int getEndPos()
	{
		return endPos;
	}

	public void setEndPos(int endPos)
	{
		this.endPos = endPos;
	}

	public int getCompeleteSize()
	{
		return compeleteSize;
	}

	public void setCompeleteSize(int compeleteSize)
	{
		this.compeleteSize = compeleteSize;
	}

	@Override
	public String toString()
	{
		return "DownloadInfo [threadId=" + threadId + ", startPos=" + startPos + ", endPos=" + endPos + ", compeleteSize=" + compeleteSize + "]";
	}

	public int getLoadingVideoId()
	{
		return loadingVideoId;
	}

	public void setLoadingVideoId(int loadingVideoId)
	{
		this.loadingVideoId = loadingVideoId;
	}
}
