package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vlvxing.app.R;
import com.handongkeji.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/22.
 */

public class PlaneDetailsActivity extends BaseActivity{

    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.details_withdrawal_txt)
    TextView detailsWithDrawal;//退改详情
    @Bind(R.id.book)
    Button book;//预订

    private Context mcontext;


    private String goCity ;
    private String arriveCity ;
    private String date ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_details);
        ButterKnife.bind(this);
        mcontext = this;
        goCity = getIntent().getStringExtra("goCity");//出发城市
        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
        date = getIntent().getStringExtra("date");//出发日期
        headTitle.setText(goCity+"-"+arriveCity);

    }


    @OnClick({R.id.return_lin, R.id.right_txt,R.id.details_withdrawal_txt,R.id.book})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.details_withdrawal_txt:
                //退改详情

                break;
            case R.id.book:
                //预订

                break;


        }
    }
}
