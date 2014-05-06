package com.nd.teacherplatform.util;

/**
 * 日期显示相关的转换工具
 * 
 * @author zmp
 * 
 */
public class DataFormatUtil {

	/**
	 * 把秒数转换成时间格式
	 * 
	 * @param milliSecond
	 *            时间的毫秒数
	 * @return hh:mm:ss
	 */
	public static String second2Format(long milliSecond) {
//		long second = milliSecond / 1000;
		long second = milliSecond;
		long s = second % 60;

		long m = second / 60;

		long h = m / 60;

		m = m % 60;

		return h + ":" + m + ":" + s;
	}

}
