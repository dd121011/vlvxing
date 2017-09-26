package com.handongkeji.autoupdata;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class UpdataService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String downUrl = intent.getStringExtra("downUrl");
		String targetSDPath = intent.getStringExtra("targetSDPath");
		downloadApk(downUrl, targetSDPath);
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void downloadApk(String downUrl, String targetSDPath){
		 HttpUtils utils = new HttpUtils();
	        utils.download(downUrl, targetSDPath, new RequestCallBack<File>() {
				
				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					  //下载成功，开始安装
	                install(responseInfo.result);
				}
				
				@Override
				public void onFailure(HttpException error, String msg) {
					CheckVersion.resterAlert();
				}
			} );
	}
	
	 private void install(File file) {

       Intent intent = new Intent(Intent.ACTION_VIEW);
       intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
       stopSelf();
   }
	 
}
