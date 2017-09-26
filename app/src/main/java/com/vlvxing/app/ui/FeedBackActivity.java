package com.vlvxing.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.common.ValidateHelper;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.content_txt)
    EditText contentTxt;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        headTitle.setText("意见反馈");
        //设置第一次进入界面不弹出软键盘
        headTitle.setFocusable(true);
        headTitle.setFocusableInTouchMode(true);
        headTitle.requestFocus();
    }

    @OnClick({R.id.return_lin, R.id.submit_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.submit_txt:
                clickSubmit();
                break;
        }
    }

    private void clickSubmit() {
        String content=contentTxt.getText().toString().trim();
        if (StringUtils.isStringNull(content)){
            ToastUtils.show(this,"请输入您的意见!");
            return;
        }
        String phone=phoneEdt.getText().toString().trim();
        if (!ValidateHelper.isPhoneNumberValid(phone)) {
            ToastUtils.show(this,"请输入正确的手机号!");
            return;
        }
        String url= Constants.URL_FEEDBACK;
        HashMap<String,String> params=new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("content",content);
        params.put("phone",phone);
        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json))return;
                JSONObject object=new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)){
                    ToastUtils.show(FeedBackActivity.this,"提交成功");
                    FeedBackActivity.this.finish();
                }else {
                    ToastUtils.show(FeedBackActivity.this,message);
                }
            }
        });
    }
}
