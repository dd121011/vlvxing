package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.utils.DataPickerDialog;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/25 0025.
 * 定制游
 */

public class CustomTourActivity extends BaseActivity {

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
    @Bind(R.id.content_edt)
    EditText contentEdt;
    private String content;
    private Intent intent;
    private String end_areaid; //目的地id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customtour_layout);
        ButterKnife.bind(this);
        headTitle.setText("定制游");
        //设置第一次进入界面不弹出软键盘
        headTitle.setFocusable(true);
        headTitle.setFocusableInTouchMode(true);
        headTitle.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (data.getExtras() == null) {
            return;
        }
         //1 姓名  2 电话  3邮箱  4目的地  5出发地
        if (requestCode == 1) {
            String content = data.getStringExtra("content");
            nameTxt.setText(content);
        }
        if (requestCode == 2) {
            String content = data.getStringExtra("content");
            phoneTxt.setText(content);
        }
        if (requestCode ==3) {
            String content = data.getStringExtra("content");
            emailTxt.setText(content);
        }
        if (requestCode ==4) {
            String areaname = data.getStringExtra("areaname");
             end_areaid = data.getStringExtra("areaid");
            endTxt.setText(areaname);
        }
        if (requestCode ==5) {
            String content = data.getStringExtra("content");
            startTxt.setText(content);
        }
    }

    @OnClick({R.id.return_lin, R.id.start_txt, R.id.end_txt, R.id.date_txt, R.id.less_img, R.id.more_img, R.id.less1_img, R.id.more1_img,
            R.id.sure_txt,R.id.name_rel, R.id.phone_rel, R.id.email_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.start_txt: //出发地
                content = startTxt.getText().toString().trim();
                intent = new Intent(this, UpdateActivity.class).putExtra("type", 5).putExtra("content", content);
                startActivityForResult(intent, 5);
                break;
            case R.id.end_txt:  //目的地
//                intent = new Intent(this, SelestorCityActivity.class).putExtra("type", 1);
                intent=new Intent(this,SelectCityActivity.class);
                startActivityForResult(intent,4);
                break;
            case R.id.date_txt:
                TimeDialog();
//                intent=new Intent(this,DataPickerDialog.class);
//                startActivityForResult(intent,5);
                break;
            case R.id.less_img:
                LessNum(dayTxt);
                break;
            case R.id.more_img:
               addNum(dayTxt);
                break;
            case R.id.less1_img:
                LessNum(peopleTxt);
                break;
            case R.id.more1_img:
                addNum(peopleTxt);
                break;
            case R.id.sure_txt:
                clickSure();
                break;
            case R.id.name_rel:  //1 姓名  2 电话  3邮箱
                content = nameTxt.getText().toString().trim();
                if (StringUtils.isStringNull(content) || "未设置".equals(content)) {
                    content = "";
                }
                intent = new Intent(this, UpdateActivity.class).putExtra("type", 1).putExtra("content", content);
                startActivityForResult(intent, 1);
                break;
            case R.id.phone_rel:
                content = phoneTxt.getText().toString().trim();
                if (StringUtils.isStringNull(content) || "未设置".equals(content)) {
                    content = "";
                }
                intent = new Intent(this, UpdateActivity.class).putExtra("type", 2).putExtra("content", content);
                startActivityForResult(intent, 2);
                break;
            case R.id.email_rel:
                content = emailTxt.getText().toString().trim();
                if (StringUtils.isStringNull(content) || "未设置".equals(content)) {
                    content = "";
                }
                intent = new Intent(this, UpdateActivity.class).putExtra("type", 3).putExtra("content", content);
                startActivityForResult(intent, 3);
                break;
        }
    }

    private void clickSure() {
        String start = startTxt.getText().toString().trim();
        String end=endTxt.getText().toString().trim();
        String data=dateTxt.getText().toString().trim();
        String day=dayTxt.getText().toString().trim();
        String people=peopleTxt.getText().toString().trim();
        String name=nameTxt.getText().toString().trim();
        String phone=phoneTxt.getText().toString().trim();
        String email=emailTxt.getText().toString().trim();
        String content=contentEdt.getText().toString().trim();
        if (StringUtils.isStringNull(end)){
            ToastUtils.show(this,"请输入目的地!");
            return;
        }
        if (StringUtils.isStringNull(data)){
            ToastUtils.show(this,"请输入出发日期!");
            return;
        }
        if (StringUtils.isStringNull(name)){
            ToastUtils.show(this,"请输入姓名!");
            return;
        }
        if (StringUtils.isStringNull(phone)){
            ToastUtils.show(this,"请输入电话!");
            return;
        }
        if (StringUtils.isStringNull(email)){
            ToastUtils.show(this,"请输入邮箱!");
            return;
        }
        if (StringUtils.isStringNull(content)){
            ToastUtils.show(this,"请输入行程需求!");
            return;
        }
        showDialog("提交中...");
        String url = Constants.URL_SAVEPROCUSTOM;
        HashMap<String, String> params = new HashMap<>();
        params.put("token",myApp.getUserTicket());
        params.put("departure",start);//出发地
        params.put("destinationId",end_areaid);
        params.put("destination",end);
        params.put("time",data);
        params.put("days",day);
        params.put("peopleCounts",people);
        params.put("name",name);
        params.put("tel",phone);
        params.put("mail",email);
        params.put("requirement",content);
        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                JSONObject obj = new JSONObject(json);
                String status = obj.getString("status");
                String message = obj.getString("message");
                if (status.equals("1")) {
                    ToastUtils.show(CustomTourActivity.this,"定制成功!");
                    finish();
                }else {
                    ToastUtils.show(CustomTourActivity.this,message);
                }
                dismissDialog();
            }
        });
    }

    private void TimeDialog() {
//        final AlertDialog dialog = new AlertDialog.Builder(this).create();
//        dialog.show();
//        DatePicker picker = new DatePicker(this);
//        picker.setDate(2017, 6);
//        picker.setMode(DPMode.SINGLE);
//        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
//            @Override
//            public void onDatePicked(String date) {
//                ToastUtils.show(CustomTourActivity.this, date);
//                dialog.dismiss();
//            }
//        });
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setContentView(picker, params);
//        dialog.getWindow().setGravity(Gravity.BOTTOM);

        DataPickerDialog dialog=new DataPickerDialog(this);
        dialog.setmIOnItemClickListener(new DataPickerDialog.IOnItemClickListener() {
            @Override
            public void onItemClick(String result) {
                dateTxt.setText(result);
            }
        });
    }

    private void LessNum(TextView txt) {
        String subString = txt.getText().toString().trim();
        int num = Integer.valueOf(subString);
        if (num <= 1) {
            return;
        }
        txt.setText(String.valueOf(--num));
    }

    private void addNum(TextView txt) {
        String addString = txt.getText().toString().trim();
        int numAdd = Integer.valueOf(addString);
        if (numAdd >= 100) {
            return;
        }
        txt.setText(String.valueOf(++numAdd));
    }
}
