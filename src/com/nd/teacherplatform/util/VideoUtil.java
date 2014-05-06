
package com.nd.teacherplatform.util;

import java.lang.reflect.Method;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;


/***
 * 视频工具类
 * @author Administrator
 *
 */
public class VideoUtil {
	/**
	 * 通过文件路径获取 媒体库中id
	 * @param context
	 * @param path
	 */
	public static long getVideoIdByPath(Context context,String path) {
		try {
			Cursor cursor = context.getContentResolver()
					.query(
							MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
							new String[] { "_id", "_display_name", "_data",
									"title", "_size", "duration",
									"mini_thumb_magic", "bookmark" },
							" _data = '" + path + "'", null, null);
			if (cursor == null || cursor.isAfterLast()) {
				return 0;
			} else {
				cursor.moveToFirst();
				long id = cursor.getLong(cursor.getColumnIndex("_id"));
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/***
	 * 获取视频截图
	 * @param context
	 * @param id 视频在数据库中的ID
	 * @return
	 */
	public static  Bitmap getVideoThumb(Context context, long id){
		try
		{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			Object[] params=new Object[4];
			params[0]=context.getContentResolver();
			params[1]=id;
			params[2]= MediaStore.Images.Thumbnails.MICRO_KIND;
			params[3]=options;
			Class[] argsClass = new Class[4];
			argsClass[0]=ContentResolver.class;
			argsClass[1]=Long.TYPE;
			argsClass[2]=Integer.TYPE;
			argsClass[3]=options.getClass();

			Bitmap b = (Bitmap)invokeStaticMethod("android.provider.MediaStore$Video$Thumbnails","getThumbnail",argsClass,params);				
			return b; 		
		}catch (Exception e) {
			Log.e("getVideoThumb", e.toString());
			return null;
		}
	}
	
	/**
	 * 反射执行某个类的静态方法
	* @author zxb   
	* @date 2011-6-16 下午05:35:10
	* @param className
	* @param methodName
	* @param args
	* @return
	* @throws Exception
	 */
	public static Object invokeStaticMethod(String className, String methodName,
			Class[] args,Object[] params) throws Exception {
		Class ownerClass = Class.forName(className);

		Method method = ownerClass.getMethod(methodName, args);
		return method.invoke(null, params);
	}
}
