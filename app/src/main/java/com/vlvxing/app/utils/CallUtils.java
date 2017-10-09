package com.vlvxing.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.CallDialog;
import com.vlvxing.app.ui.LineDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拨打电话工具类
 */

public class CallUtils {

    public static void call(final Context context, final String mobile){
        final CallDialog dialog = new CallDialog(context, "");
        dialog.setTitle("要拨打：" + mobile + "吗?");
        dialog.setNegativeButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.trim().length() != 0) {
                    Intent mobileIntent = new Intent(
                            "android.intent.action.CALL", Uri.parse("tel:"
                            + mobile));
                    context.startActivity(mobileIntent); // 启动
                }
                // 否则Toast提示一下
                else {
                    ToastUtils.show(context, "电话为空");
                }
                dialog.dismissDialog();
            }
        });
    }
}
