package com.nd.teacherplatform.interfaces;

import com.nd.teacherplatform.vo.CollectVo;

/**
 * �����ɾ��ĳһ����item
 * @author zmp
 *
 */
public interface DeleteCollectItemInterface
{
       public void deleteCollectItem(CollectVo vo);
       
       public void hideAllDeleteBtn();
}
