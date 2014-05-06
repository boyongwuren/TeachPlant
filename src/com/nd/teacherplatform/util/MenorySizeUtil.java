package com.nd.teacherplatform.util;

import java.io.File;
import java.text.DecimalFormat;

import android.os.Environment;
import android.os.StatFs;

/**
 *	��õ�SDCard,�ֻ��Ĵ洢�ռ�,���ÿռ�
 * @author ֣����
 * @version ����ʱ�䣺2010-8-13 ����03:22:04
 */
public class MenorySizeUtil {
	/**
	 * ������ֻ��ڴ���ܿռ��С  	
	 */
    public static String getTotalInternalMemorySize() {  
        File path = Environment.getDataDirectory();  
        StatFs stat = new StatFs(path.getPath());  
        long blockSize = stat.getBlockSize();  
        long totalBlocks = stat.getBlockCount();  
        return StringUtils.parseLongToKbOrMb(totalBlocks * blockSize,1);
    }  
  
    /**
     *   ������ֻ��ڴ�Ŀ��ÿռ��С 
     */
    public static String getAvailableInternalMemorySize() {  
        File path = Environment.getDataDirectory();  
        StatFs stat = new StatFs(path.getPath());  
        long blockSize = stat.getBlockSize();  
        long availableBlocks = stat.getAvailableBlocks();  
        return StringUtils.parseLongToKbOrMb(availableBlocks * blockSize,1);
    }  
  
    /**
     * ������ⲿ�洢�Ŀ��ÿռ��С  
     */
    public static String getAvailableExternalMemorySize() {  
        long availableExternalMemorySize = 0;  
        if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {  
            File path = Environment.getExternalStorageDirectory();  
            StatFs stat = new StatFs(path.getPath());  
            long blockSize = stat.getBlockSize();  
            long availableBlocks = stat.getAvailableBlocks();  
            availableExternalMemorySize = availableBlocks * blockSize;  
        }else if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_REMOVED)) {  
            availableExternalMemorySize = -1;  
  
        }  
        return StringUtils.parseLongToKbOrMb(availableExternalMemorySize,1);
    }  
  
    /**
     * ������ⲿ�洢���ܿռ��С  
     */
    public static String getTotalExternalMemorySize() {  
        long totalExternalMemorySize = 0;  
        if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {  
            File path = Environment.getExternalStorageDirectory();  
            StatFs stat = new StatFs(path.getPath());  
            long blockSize = stat.getBlockSize();  
            long totalBlocks = stat.getBlockCount();  
            totalExternalMemorySize = totalBlocks * blockSize;  
        } else if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_REMOVED)) {  
            totalExternalMemorySize = -1;  
  
        }  
        return StringUtils.parseLongToKbOrMb(totalExternalMemorySize,1);
    }  
  
    /* ����Ϊ�ַ�formatLongС[1]Ϊ��λKB��MB */  
    private static String formatLong(long size) {  
        if(size==-1){
        	return "������";
        }
    	String str = "";  
        if (size >= 1024) {  
            str = "KB";  
            size /= 1024;  
            if (size >= 1024) {  
                str = "MB";  
                size /= 1024; 
                if(size>=1024){
                    str = "GB";  
                    size /= 1024; 
                }
            }  
        }  
        DecimalFormat formatter = new DecimalFormat();  
        /* ÿ3��������,�ָ��磺1,000 */  
        formatter.setGroupingSize(3);  
        String result= formatter.format(size);  
        result += str;  
        return result;  
    }  
}
