package com.vlvxing.app.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.vlvxing.app.R;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.SettingService;


public class CommonDialog {
    private AlertDialog.Builder mBuilder;
    private SettingService mSettingService;

    public CommonDialog(@NonNull Context context, @NonNull SettingService settingService) {
        mBuilder = AlertDialog.build(context)
                .setCancelable(true)
                .setPositiveButton(R.string.confirm, mClickListener)
                .setNegativeButton(R.string.cancel, mClickListener);
        this.mSettingService = settingService;
    }

    @NonNull
    public CommonDialog setTitle(@NonNull String title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public CommonDialog setTitle(@StringRes int title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public CommonDialog setMessage(@NonNull String message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public CommonDialog setMessage(@StringRes int message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public CommonDialog setNegativeButton(@NonNull String text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public CommonDialog setCancelable(boolean cancelable){
        mBuilder.setCancelable(cancelable);
        return this;
    }

    @NonNull
    public CommonDialog setNegativeButton(@StringRes int text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public CommonDialog setPositiveButton(@NonNull String text) {
        mBuilder.setPositiveButton(text, mClickListener);
        return this;
    }

    @NonNull
    public CommonDialog setPositiveButton(@StringRes int text) {
        mBuilder.setPositiveButton(text, mClickListener);
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
