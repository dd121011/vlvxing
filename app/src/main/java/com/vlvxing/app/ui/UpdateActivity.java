package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.common.ValidateHelper;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/25 0025.
 * 编辑用户名、电话、邮箱
 */

public class UpdateActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.right_txt)
    TextView rightTxt;
    @Bind(R.id.content_edt)
    EditText edt;
    private int  type;
    private String title,content;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);
        ButterKnife.bind(this);
        headTitle.setText(title);
        rightTxt.setVisibility(View.VISIBLE);
        intent=getIntent();
        type=intent.getIntExtra("type",0);
        String contents=intent.getStringExtra("content");
        if (type==1) {//1 姓名  2 电话  3邮箱 4用户名
            title="姓名";
        }else if (type==2){
            title="电话";
            if (StringUtils.isStringNull(contents)){
                content="请填写你的电话";
            }
        }else if (type==3){
            title="邮箱";
            if (StringUtils.isStringNull(contents)) {
                content = "请填写你的邮箱";
            }
        }else if (type==4){
            title="用户名";
        }else {
            title="出发地";
        }
        headTitle.setText(title);
        if (StringUtils.isStringNull(contents)){
            edt.setHint(content);
        }else {
            edt.setText(contents);
            edt.setSelection(contents.length());
        }
    }

    @OnClick({R.id.return_lin, R.id.right_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
                clickSave();
                break;
        }
    }

    private void clickSave() {
        String txt = edt.getText().toString().trim();
        if (StringUtils.isStringNull(txt)){
            ToastUtils.show(this,"输入内容不能为空!");
            return;
        }
        boolean result=true;
        if (type==2){
            String phoneNum = edt.getText().toString().trim();
             result = ValidateHelper.isPhoneNumberValid(phoneNum);
            if (!result) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            content=phoneNum;
        }else if (type==3){
            String email = edt.getText().toString().trim();
             result = ValidateHelper.isEmail(email);
            if (!result) {
                Toast.makeText(this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
                return;
            }
            content=email;
        }else {
            content = edt.getText().toString().trim();
        }

        if (!result){
            return;
        }
        intent.putExtra("content",content);
        setResult(type,intent);
        finish();
    }
}
