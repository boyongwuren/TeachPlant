package com.nd.teacherplatform.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/***
 * Õ¯¬Áª∑æ≥≈–∂œ¿‡
 * @author Administrator
 *
 */
public class NetWorkConnectUtils {
	/**
	 * 
	 * @param context
	 * @return true if the network is available, false otherwise.
	 */
	public synchronized static boolean isAvailable(Context context) {
		boolean result = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (null != connectivityManager) {
			networkInfo = connectivityManager.getActiveNetworkInfo();
			if (null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
				result = true;
			}
		}
		return result;
	}
}
