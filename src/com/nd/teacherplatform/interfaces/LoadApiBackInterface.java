package com.nd.teacherplatform.interfaces;


/**
 * 服务端api接口加载返回的操作
 * @author zmp
 *
 */
public interface LoadApiBackInterface
{
	/**
	 * 服务端api接口返回
	 * @param jsonString 服务端返回来的字符串
	 */
   public void loadApiBackString(String jsonString);
}
