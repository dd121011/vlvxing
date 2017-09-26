package com.vlvxing.app.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vlvxing.app.R;
import com.vlvxing.app.common.MyApp;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 拍照  解决android7.0权限问题
 * @author caihuan
 * */

public class TakePhotoDialog {

    Context activity;
    Dialog ad;
    @Bind(R.id.btn_take_photo)
    Button btnTakePhoto;
    @Bind(R.id.btn_pick_photo)
    Button btnPickPhoto;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    private int type, t; //从本地相册选取还是拍视频


    public TakePhotoDialog(Context activity, int type) {
        this.activity = activity;
        this.type = type;
        init();
    }


    private void init() {
        ad = new Dialog(activity, R.style.tc_dialog);
        Window window = ad.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM);
        ad.show(); //显示Dialog
        // 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        window.setContentView(R.layout.act_userheadimgset);
        ButterKnife.bind(this, ad.getWindow().getDecorView());
        View root = window.findViewById(R.id.root);
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.width = (int) (MyApp.getScreenWidth()); //设置Dialog的宽度
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        root.setLayoutParams(params);
        if (type == 0) {
            btnPickPhoto.setText("从相册选择照片");
        } else if (type == 1) {
            btnPickPhoto.setText("拍视频");
        }
    }

    public void showDialog() {
        ad.show();
    }

    public void dismissDialog() {
        ad.dismiss();
    }


    private ClickSureListener mOnTimePickListener;

    public void setmOnclickListener(ClickSureListener mOnTimePickListener) {
        this.mOnTimePickListener = mOnTimePickListener;
    }

    @OnClick({R.id.btn_take_photo, R.id.btn_pick_photo, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_photo: //拍照
                t = 1;
                if (mOnTimePickListener != null) {
                    mOnTimePickListener.onClick(t);
                }
                break;
            case R.id.btn_pick_photo: //1选择本地相册  0拍视频
                t = 2;
                if (mOnTimePickListener != null) {
                    mOnTimePickListener.onClick(t);
                }
                break;
            case R.id.btn_cancel:
                dismissDialog();
                break;
        }
    }

    public interface ClickSureListener {
        public void onClick(int t);
    }


}
