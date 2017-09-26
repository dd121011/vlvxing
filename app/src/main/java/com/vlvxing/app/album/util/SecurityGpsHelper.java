package com.vlvxing.app.album.util;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

/**
 * 
 * @author hb
 *  判定或打开GPS定位的类
 */
public class SecurityGpsHelper {

	/**
	 * 判断Gps是否打开
	 * @param context
	 * @return
	 */
	public static boolean checkGpsIsOpen(Context context){
		 boolean flag = false;
		 LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		 flag= lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return flag;
	}
	/**
	 * 打开gps定位
	 */
	public static  void openGps(Context context){
		Intent gpsIntent = new Intent();
		gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		gpsIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
