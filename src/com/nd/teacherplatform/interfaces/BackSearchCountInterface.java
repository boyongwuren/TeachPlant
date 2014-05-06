package com.nd.teacherplatform.interfaces;

import java.util.ArrayList;

import com.nd.teacherplatform.vo.SearchCountVo;

/**
 * 返回了 搜索的数量
 * @author zmp
 *
 */
public interface BackSearchCountInterface
{
      public void setSearchCountVo(ArrayList<SearchCountVo> vos);
}
