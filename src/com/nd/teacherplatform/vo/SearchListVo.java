package com.nd.teacherplatform.vo;

/**
 * 视频搜索列表的
 * 结果数据结构
 * type = 1 标示 视频集数据
 * type = 2 标示 视频数据
 * 参考 SearchResultTypeConst
 * @author zmp
 *
 */
public class SearchListVo
{
	/**
	 * 数据类型
	 * 参考 SearchResultTypeConst
	 */
	 public int type = 0;
	 
	 public VideoSetVo videoSetVo;
	 
	 public VideoInfoVo videoInfoVo;

}
