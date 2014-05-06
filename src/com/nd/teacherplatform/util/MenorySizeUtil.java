package com.nd.teacherplatform.util;

import java.io.File;
import java.text.DecimalFormat;

import android.os.Environment;
import android.os.StatFs;

/**
 *	获得得SDCard,手机的存储空间,可用空间
 * @author 郑晓彬
 * @version 创建时间：2010-8-13 下午03:22:04
 */
public class MenorySizeUtil {
	/**
	 * 这个是手机内存的总空间大小  	
	 */
    public static String getTotalInternalMemorySize() {  
        File path = Environment.getDataDirectory();  
        StatFs stat = new StatFs(path.getPath());  
        long blockSize = stat.getBlockSize();  
        long totalBlocks = stat.getBlockCount();  
        return StringUtils.parseLongToKbOrMb(totalBlocks * blockSize,1);
    }  
  
    /**
     *   这个是手机内存的可用空间大小 
     */
    public static String getAvailableInternalMemorySize() {  
        File path = Environment.getDataDirectory();  
        StatFs stat = new StatFs(path.getPath());  
        long blockSize = stat.getBlockSize();  
        long availableBlocks = stat.getAvailableBlocks();  
        return StringUtils.parseLongToKbOrMb(availableBlocks * blockSize,1);
    }  
  
    /**
     * 这个是外部存储的可用空间大小  
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
     * 这个是外部存储的总空间大小  
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
  
    /* 返回为字符formatLong小[1]为单位KB或MB */  
    private static String formatLong(long size) {  
        if(size==-1){
        	return "不可用";
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
        /* 每3个数字用,分隔如：1,000 */  
        formatter.setGroupingSize(3);  
        String result= formatter.format(size);  
        result += str;  
        return result;  
    }  
}
