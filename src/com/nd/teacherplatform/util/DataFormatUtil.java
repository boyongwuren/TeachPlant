package com.nd.teacherplatform.util;

/**
 * ������ʾ��ص�ת������
 * 
 * @author zmp
 * 
 */
public class DataFormatUtil {

	/**
	 * ������ת����ʱ���ʽ
	 * 
	 * @param milliSecond
	 *            ʱ��ĺ�����
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
