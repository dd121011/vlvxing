package com.vlvxing.app.common;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShareDialog{
    Context activity;
    Dialog ad;
    @Bind(R.id.txt)
    TextView txt;
    @Bind(R.id.qq_txt)
    TextView qqTxt;
    @Bind(R.id.wb_txt)
    TextView wbTxt;
    @Bind(R.id.wx_txt)
    TextView wxTxt;
    @Bind(R.id.friend_txt)
    TextView friendTxt;
    int type;
    public ShareDialog(Context activity) {
        this.activity = activity;
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
        window.setContentView(R.layout.share_layout);
        ButterKnife.bind(this, ad.getWindow().getDecorView());
        View root = window.findViewById(R.id.root);
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.width = (int) (MyApp.getScreenWidth() * 0.9f); //设置Dialog的宽度
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        root.setLayoutParams(params);


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

    public interface ClickSureListener{
        public void onClick(int type);
    }

    @OnClick({R.id.qq_txt, R.id.wb_txt, R.id.wx_txt, R.id.friend_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qq_txt: //type 1QQ 2微博  3微信  4朋友圈
                type=1;
                if (mOnTimePickListener != null){
                    mOnTimePickListener.onClick(type);
                }
                break;
            case R.id.wb_txt:
                type=2;
                if (mOnTimePickListener != null){
                    mOnTimePickListener.onClick(type);
                }
                break;
            case R.id.wx_txt:
                type=3;
                if (mOnTimePickListener != null){
                    mOnTimePickListener.onClick(type);
                }
                break;
            case R.id.friend_txt:
                type=4;
                if (mOnTimePickListener != null){
                    mOnTimePickListener.onClick(type);
                }
                break;
        }
    }
}
