package com.vlvxing.app.common;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.vlvxing.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PayDialog{

    Context activity;
    Dialog ad;
    String money;
    @Bind(R.id.money_txt)
    TextView moneyTxt;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.sure_txt)
    TextView sureTxt;
    private int type=1; //支付方式

    public PayDialog(Context activity, String money) {
        this.activity = activity;
        this.money = money;
        init();
    }

    private void init() {
        ad = new Dialog(activity, R.style.tc_dialog);
        Window window = ad.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.CENTER);
        ad.show(); //显示Dialog
        // 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        window.setContentView(R.layout.pay_dialog);
        ButterKnife.bind(this,ad.getWindow().getDecorView());
        View root = window.findViewById(R.id.root);
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.width = (int) (MyApp.getScreenWidth() * 0.7f); //设置Dialog的宽度
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        root.setLayoutParams(params);

        moneyTxt.setText("支付￥"+money);

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

    @OnClick({R.id.pay_rel, R.id.wxpay_rel,R.id.sure_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_rel: //支付宝
                type=1;
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.GONE);
                break;
            case R.id.wxpay_rel:  //微信
                type=2;
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.VISIBLE);
                break;
            case R.id.sure_txt:
                if (mOnTimePickListener != null){
                    mOnTimePickListener.onClick(type);
                }
                break;
        }
    }
}
