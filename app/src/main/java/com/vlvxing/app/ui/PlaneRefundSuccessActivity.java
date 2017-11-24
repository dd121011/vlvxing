package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.qunar.model.FlyOrder;
import com.vlvxing.app.R;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退票完成页面
 */

public class PlaneRefundSuccessActivity extends BaseActivity{
    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.query_btn)
    Button query_btn;
    @Bind(R.id.buy_btn)
    Button buy_btn;
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_refund_success_order);
        ButterKnife.bind(this);
        mcontext = this;

        headTitle.setText("退票完成");

    }
    private void initData(){


    }


    @OnClick({R.id.return_lin, R.id.query_btn,R.id.buy_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.buy_btn:
                //继续购票
                Intent intent = new Intent(mcontext,PlaneTicketActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.query_btn:
                //我的订单页面
                Intent intent1 = new Intent(mcontext,PlaneOrderActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

}
