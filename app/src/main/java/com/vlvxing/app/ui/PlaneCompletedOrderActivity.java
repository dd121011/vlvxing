package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 已完成订单详情
 */

public class PlaneCompletedOrderActivity extends BaseActivity{
    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.submit_btn)
    Button submitBtn;//提交
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.info_txt)
    TextView infoTxt;//航班信息
    @Bind(R.id.information_lin)
    LinearLayout informationLin;//旅客信息
    @Bind(R.id.shuttle_lin)
    LinearLayout shuttleLin;//机场接送
    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private int flag = 0;//记录当前页面 改签0  退票1
//    private Dialog dialog;//详情 改签退票
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_completed_order);
        ButterKnife.bind(this);
        mcontext = this;
//        goCity = getIntent().getStringExtra("goCity");//出发城市
//        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
//        date = getIntent().getStringExtra("date");//出发日期
        headTitle.setText("已完成订单详情");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //改签
                if(checkedId==R.id.left_radio_btn){
                    flag = 0;
                    infoTxt.setVisibility(View.GONE);
                    informationLin.setVisibility(View.GONE);
                    shuttleLin.setVisibility(View.VISIBLE);
                    submitBtn.setText("改签(一张)");
                }
                if(checkedId==R.id.right_radio_btn){
                    flag = 1;
                    infoTxt.setVisibility(View.VISIBLE);
                    informationLin.setVisibility(View.VISIBLE);
                    shuttleLin.setVisibility(View.GONE);
                    submitBtn.setText("退票(一张)");
                }
            }
        });
    }
    private void showDialog() {
        Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.plane_completed_order_dialog, null);
        Button btn = (Button)contentView.findViewById(R.id.submit_btn);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = (getResources().getDisplayMetrics().widthPixels)-30;
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        //设置点击外部空白处可以关闭Activity
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
//        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.show();

        //确定退票
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.return_lin, R.id.right_txt,R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.submit_btn:
                if(flag==0){
                    //改签
                    showDialog();
                }
                if(flag==1){
                    //退票
                    showDialog();
                }
                //提交
//				clickSave();
                break;

        }
    }

}
