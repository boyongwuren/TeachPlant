package com.nd.teacherplatform.interfaces;

import java.util.ArrayList;

import com.nd.teacherplatform.vo.SearchListVo;

/**
 * 返回搜索结果的列表
 * @author zmp
 *
 */
public interface BackSearchListInterface
{
      public void setSearchListVos(ArrayList<SearchListVo> slvos);
}
