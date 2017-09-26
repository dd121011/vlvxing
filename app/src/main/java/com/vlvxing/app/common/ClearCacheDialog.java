package com.vlvxing.app.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;


import com.vlvxing.app.R;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.SettingService;

public class ClearCacheDialog {

    private AlertDialog.Builder mBuilder;
    private SettingService mSettingService;

    public ClearCacheDialog(@NonNull Context context, SettingService mSettingService) {
        this.mBuilder = AlertDialog.build(context)
        .setMessage("确定清除本地缓存?")
        .setPositiveButton(R.string.confirm, mClickListener)
                .setNegativeButton(R.string.cancel, mClickListener);;
        this.mSettingService = mSettingService;
    }

    public void show() {
        mBuilder.show();
    }

    private DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    mSettingService.cancel();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    mSettingService.execute();
                    break;
            }
        }
    };
}
