package com.vlvxing.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买详情 添加用户身份信息 提交订单
 */

public class PlaneBuyDetailsActivity extends BaseActivity{

    @Bind(R.id.head_title_left)
    TextView headTitleLeft;//标题
    @Bind(R.id.head_title_right)
    TextView headTitleRight;//标题右
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.bottom_left_lin)
    LinearLayout bottomLeftLin;//总价linearlayout
    @Bind(R.id.editLin)
    LinearLayout editLin;
//    @Bind(R.id.details_withdrawal_txt)
//    TextView detailsWithDrawal;//退改详情
    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private int type=1; //支付方式
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_buy_details);
        ButterKnife.bind(this);
        mcontext = this;
        goCity = getIntent().getStringExtra("goCity");//出发城市
        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
        date = getIntent().getStringExtra("date");//出发日期
        headTitleLeft.setText(goCity);
        headTitleRight.setText(arriveCity);
        //设置第一次进入界面不弹出软键盘
        editLin.setFocusable(true);
        editLin.setFocusableInTouchMode(true);
        editLin.requestFocus();

    }

    private void showWindow(View v) {

        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           View view = layoutInflater.inflate(R.layout.act_plane_buy_price_popupwindow_, null);

            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(view, 300, 350);
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        popupWindow.showAtLocation(v, Gravity.TOP, 0,0);

        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
//        int xPos = windowManager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;

//        popupWindow.showAsDropDown(parent, xPos, 0);


    }

    @OnClick({R.id.return_lin, R.id.right_txt})
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
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.bottom_left_lin:
                //预订
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();;
                }else{
                    showWindow(bottomLeftLin);
                }
                break;

        }
    }
}
