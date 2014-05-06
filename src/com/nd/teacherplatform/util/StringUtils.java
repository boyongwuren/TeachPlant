package com.nd.teacherplatform.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * �ַ�����ʽ����
 * @author ������
 *
 */
public class StringUtils {
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
	public final static String EMPTY = "";

	/**
	 * ����ת��Ϊ���ݴ�С
	 * @param num
	 * @return
	 */
	public static String parseLongToKbOrMb(long num) {
		long n = num;
		if (n < 1024) {
			return n + "B";
		}
		n = n / 1024;
		if (n < 1024) {
			return n + "KB";
		}
		n = n / 1024;
		if (n < 1024) {
			return n + "MB";
		}
		n = n / 1024;
		return n + "GB";
	}
	
	/**
	 * ����
	 * @param num
	 * @param scale
	 * @return
	 */
	public static String parseLongToKbOrMb(long num, int scale) {
		float scaleNum;
		switch (scale) {
		case 0:
			scaleNum = 1;
			break;
		case 1:
			scaleNum = 10f;
			break;
		case 2:
			scaleNum = 100f;
			break;
		case 3:
			scaleNum = 1000f;
			break;
		case 4:
			scaleNum = 10000f;
			break;
		default:
			scaleNum = 1;
		}
		float n = num;
		if (n < 1024) {
			return Math.round(n * scaleNum) / scaleNum + "B";
		}
		n = n / 1024;
		if (n < 1024) {
			return Math.round(n * scaleNum) / scaleNum + "KB";
		}
		n = n / 1024;
		if (n < 1024) {
			return Math.round(n * scaleNum) / scaleNum + "MB";
		}
		n = n / 1024;
		return Math.round(n * scaleNum) / scaleNum + "GB";

	}
	
	/**
	 * ��ʽ�������ַ���
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * ��ʽ�������ַ���
	 * 
	 * @param date
	 * @return ����2011-3-24
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * ��ȡ��ǰʱ�� ��ʽΪyyyy-MM-dd ����2011-07-08
	 * 
	 * @return
	 */
	public static String getDate() {
		return formatDate(new Date(), DEFAULT_DATE_PATTERN);
	}

	/**
	 * ��ȡ��ǰʱ��
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * ��ʽ������ʱ���ַ���
	 * 
	 * @param date
	 * @return ����2011-11-30 16:06:54
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DEFAULT_DATETIME_PATTERN);
	}

	public static String join(final ArrayList<String> array, String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null && array.size() > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	//	public static String trim(String str) {
	//		if (IsUtil.isNullOrEmpty(str)) {
	//			return "";
	//		}
	//		return str.trim();
	//	}
	//
	//	/** ������ת����unicode���� */
	//	public static String gbEncoding(final String gbString) {
	//		char[] utfBytes = gbString.toCharArray();
	//		String unicodeBytes = "";
	//		for (char utfByte : utfBytes) {
	//			String hexB = Integer.toHexString(utfByte);
	//			if (hexB.length() <= 2) {
	//				hexB = "00" + hexB;
	//			}
	//			unicodeBytes = unicodeBytes + "\\u" + hexB;
//		}
	//		//System.out.println("unicodeBytes is: " + unicodeBytes);  
	//		return unicodeBytes;
	//	}
	//
	//	/** ��unicode����ת������??*/
	//	public static String decodeUnicode(final String dataStr) {
	//		int start = 0;
	//		int end = 0;
	//		final StringBuffer buffer = new StringBuffer();
	//		while (start > -1) {
	//			end = dataStr.indexOf("\\u", start + 2);
//			String charStr = "";
	//			if (end == -1) {
	//				charStr = dataStr.substring(start + 2, dataStr.length());
	//			} else {
	//				charStr = dataStr.substring(start + 2, end);
	//			}
	//			char letter = (char) Integer.parseInt(charStr, 16); // 16����parse�����ַ���??  
	//			buffer.append(new Character(letter).toString());
	//			start = end;
	//		}
	//		//System.out.println(buffer.toString());
	//		return buffer.toString();
	//	}

}
