package com.handongkeji.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;


import com.handongkeji.widget.CallDialog;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.ui.LoginActivity;


/**
 * ClassName:OtherLoginActivity.java
 *
 * PackageName:com.handongkeji.lvxingyongche.widget
 *
 * Create On 2016-1-21上午9:03:52
 *
 * Site:http://www.handongkeji.com
 *
 * author:wmm
 *
 * Copyrights 2015-10-18 handongkeji All rights reserved.
 */
public class OtherLoginActivity {
	private static MyApp myApp;
	public static void OtherLogin(Context context, final String message){
		myApp = (MyApp) context.getApplicationContext();
		// 弹出的对话框
//		final CallDialogTO dialogs = new CallDialogTO(context);
//		dialogs.showDialog();
		CallDialog islogin=new CallDialog((Activity)context,message);
		islogin.setNegativeButtonListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, LoginActivity.class));
			}
		});
	}
}
