package com.nd.teacherplatform.interfaces;

import com.nd.teacherplatform.vo.CollectVo;

/**
 * 点击了删除某一个的item
 * @author zmp
 *
 */
public interface DeleteCollectItemInterface
{
       public void deleteCollectItem(CollectVo vo);
       
       public void hideAllDeleteBtn();
}
