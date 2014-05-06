package com.nd.teacherplatform.download;

/**
 * ����һ��������Ϣ��ʵ����
 */
public class LoadingInfo
{
	private int threadId;// ������id
	private int startPos;// ��ʼ��
	private int endPos;// ������
	private int compeleteSize;// ��ɶ�
	private String fileUrl;// �����������ʶ
	private int loadingVideoId = 0;//����������Ƶ��Id

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
