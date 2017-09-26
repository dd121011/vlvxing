package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;
import com.vlvxing.app.model.ProCustomModel;
import com.vlvxing.app.utils.DataUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/6/5 0005.
 * 定制详情
 */

public class ProCustomDetailActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.start_txt)
    TextView startTxt;
    @Bind(R.id.end_txt)
    TextView endTxt;
    @Bind(R.id.date_txt)
    TextView dateTxt;
    @Bind(R.id.day_txt)
    TextView dayTxt;
    @Bind(R.id.people_txt)
    TextView peopleTxt;
    @Bind(R.id.name_txt)
    TextView nameTxt;
    @Bind(R.id.phone_txt)
    TextView phoneTxt;
    @Bind(R.id.email_txt)
    TextView emailTxt;
    @Bind(R.id.content_txt)
    TextView contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.procustomdetail_layout);
        ButterKnife.bind(this);
        headTitle.setText("定制详情");
        initData();
    }

    private void initData() {
        Intent intent=getIntent();
        ProCustomModel.DataBean model = intent.getParcelableExtra("data");
        String departure = model.getDeparture();
        String destination = model.getDestination();
        String time = model.getTime();
        time=DataUtils.format(time,"yyyy-MM-dd");
        String day = model.getDays();
        String name=model.getName();
        String people= model.getPeoplecounts();
        String phone=model.getTel();
        String email= model.getMail();
        String requirement=model.getRequirement();
        startTxt.setText(departure);
        endTxt.setText(destination);
        dateTxt.setText(time);
        dayTxt.setText(day);
        peopleTxt.setText(people);
        nameTxt.setText(name);
        emailTxt.setText(email);
        phoneTxt.setText(phone);
        contentTxt.setText(requirement);
    }

    @OnClick({R.id.return_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
        }
    }
}
