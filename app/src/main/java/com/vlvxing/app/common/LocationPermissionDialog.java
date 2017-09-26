package com.vlvxing.app.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;


import com.vlvxing.app.R;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.SettingService;

public class LocationPermissionDialog {
    private AlertDialog.Builder mBuilder;
    private SettingService mSettingService;

   public LocationPermissionDialog(@NonNull Context context, @NonNull SettingService settingService) {
        mBuilder = AlertDialog.build(context)
                .setCancelable(false)
                .setTitle(R.string.permission_title_permission_failed)
                .setMessage(R.string.permission_message_permission_failed)
                .setPositiveButton(R.string.permission_setting, mClickListener);
//                .setNegativeButton(R.string.permission_cancel, mClickListener);
        this.mSettingService = settingService;
    }

    @NonNull
    public LocationPermissionDialog setTitle(@NonNull String title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public LocationPermissionDialog setTitle(@StringRes int title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public LocationPermissionDialog setMessage(@NonNull String message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public LocationPermissionDialog setMessage(@StringRes int message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public LocationPermissionDialog setNegativeButton(@NonNull String text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public LocationPermissionDialog setNegativeButton(@StringRes int text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public LocationPermissionDialog setPositiveButton(@NonNull String text) {
        mBuilder.setPositiveButton(text, mClickListener);
        return this;
    }

    @NonNull
    public LocationPermissionDialog setPositiveButton(@StringRes int text) {
        mBuilder.setPositiveButton(text, mClickListener);
        return this;
    }

    public LocationPermissionDialog setCancelable(boolean cancelable){
        mBuilder.setCancelable(cancelable);
        return this;
    }

    public void show() {
        mBuilder.show();
    }


    /**
     * The dialog's btn click listener.
     */
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
