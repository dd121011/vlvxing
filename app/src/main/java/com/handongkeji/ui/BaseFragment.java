package com.handongkeji.ui;


import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.vlvxing.app.common.ActivityManager;
import com.vlvxing.app.common.MyApp;


@SuppressLint("NewApi")
public class BaseFragment  extends FragmentActivity {

	public MyApp myApp;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
        ActivityManager.getInstance().addActivity(this.getClass().getSimpleName(),this);
		myApp = (MyApp)getApplication();
//        PushAgent.getInstance(this).onAppStart();
	}

	protected void dialog() {
        Builder builder = new Builder(this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
        builder.setNegativeButton("取消",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this.getClass().getSimpleName());
    }
}
