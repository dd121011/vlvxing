package com.handongkeji.widget;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类 
 * 用于判断网络是否连接
 * @author Administrator
 *
 */
public class NetUtils {

	public static boolean isNet(Context context){
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		return info.isConnected();  //网络是否连接
	}
	
}
