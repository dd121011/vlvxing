package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.DialogFourAdapter;
import com.vlvxing.app.adapter.DialogThreeAdapter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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


    @Bind(R.id.check1)
    CheckBox check1;
    @Bind(R.id.check2)
    CheckBox check2;


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
    private Dialog bottomDialog;

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


    private void showPop(){
        View popupView = LayoutInflater.from(this).inflate(R.layout.act_plane_buy_price_popupwindow_, null);
        PopupWindow window = new PopupWindow(popupView, 400, 600);

        // TODO: 2016/5/17 设置背景颜色
//        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(ban_back, 0, 20);
    }

    private void showDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.act_plane_buy_price_popupwindow_, null);
//        ListView listview = (ListView) contentView.findViewById(R.id.listview);//展示UI容器 body中listview
//        ImageView close = (ImageView) contentView.findViewById(R.id.close);//关闭

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();

    }


    @OnClick({R.id.return_lin, R.id.right_txt,R.id.bottom_left_lin,R.id.pay_rel,R.id.wxpay_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_rel: //支付宝
                type=1;
                check1.setChecked(true);
                check2.setChecked(false);
                break;
            case R.id.wxpay_rel:  //微信
                type=2;
                check2.setChecked(true);
                check1.setChecked(false);
                break;
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.bottom_left_lin:
//                Toast.makeText(mcontext, "点击了", Toast.LENGTH_SHORT).show();
                showDialog();
                //预订
//                showPop();
//                if(bottomDialog.isShowing()){
//                    bottomDialog.dismiss();
//                }else{
//                    showDialog();
//                }
                break;

        }
    }
}
