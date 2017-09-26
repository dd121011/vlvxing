package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.common.ValidateHelper;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyProcessDialog;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.presenter.LoginAndRegisterPresenter;
import com.vlvxing.app.utils.ToastUtils;
import com.vlvxing.app.utils.UmAddAliasAndTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 *
 * @ClassName:RegisterActivity
 * @PackageName:com.handongkeji.user
 * @Create On 2016/10/9   9:35
 * @Site:http://www.handongkeji.com
 * @author:caihuan
 * @Copyrights 2016/10/9 handongkeji All rights reserved.
 */
public class ForgetPwdActivity extends BaseActivity implements LoginAndRegisterPresenter.IView {


    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.pass_edt)
    EditText passEdt;
    @Bind(R.id.code_edt)
    EditText codeEdt;
    @Bind(R.id.getcode_txt)
    TextView getcodeTxt;
    private LoginAndRegisterPresenter mPresenter;
    private CountDownTask task;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        Intent intent=getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type==1){ //找回密码
            headTitle.setText("找回密码");
        }else {
            headTitle.setText("修改密码");
        }

        mPresenter = new LoginAndRegisterPresenter(this);
        task = new CountDownTask(60000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel();
    }

    private boolean checkPhoneNum(String phoneNum) {
        boolean result = ValidateHelper.isPhoneNumberValid(phoneNum);
        if (!result)
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        return result;
    }

    private boolean checkVerifyCode(String userCode) {
        boolean result = !TextUtils.isEmpty(userCode);
        if (!result)
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
        return result;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSignInOrRegisterComplete(String uid, String companyid) {
    }


    @Override
    public void onGetVerifyCodeComplete(String msg) {
        codeEdt.requestFocus();
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignInOrRegisterError(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.return_lin, R.id.getcode_txt, R.id.sure_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.getcode_txt:
                phone = phoneEdt.getText().toString().trim();
                if (checkPhoneNum(phone)) {
                    task.start();
                    getcodeTxt.setEnabled(false);
                    mPresenter.getVerifyCode(phone, 1, "2","2");
                }
                break;
            case R.id.sure_txt:
                clickSure();
                break;
        }
    }

    private void clickSure() {
        String phone= phoneEdt.getText().toString().trim();
        String pass=passEdt.getText().toString().trim();
        String code=codeEdt.getText().toString().trim();
        if (StringUtils.isStringNull(pass)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6) {
            Toast.makeText(this, "密码位数不能少于6位!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidateHelper.isPhoneNumberValid(phone)) {
            Toast.makeText(this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog("提交中...");
        String url= Constants.URL_FORGETPWD;
        HashMap<String, String> params = new HashMap<>();
        params.put("phoneNum", phone);
        params.put("infoCode",code );
        params.put("password",pass);
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json=data.getJson();
                if (StringUtils.isStringNull(json)){
                    dismissDialog();
                    return;
                }
                JSONObject object=new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)){
                    dismissDialog();
                    ToastUtils.show(ForgetPwdActivity.this,"修改成功!");
                    ForgetPwdActivity.this.finish();
                }else {
                    ToastUtils.show(ForgetPwdActivity.this,message);
                }
            }
        });
    }

    class CountDownTask extends CountDownTimer {

        public CountDownTask(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getcodeTxt.setText(getString(R.string.getVerifyCodeAgain, millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            getcodeTxt.setText(getString(R.string.login_get_verify_code));
            getcodeTxt.setEnabled(true);
        }
    }

}
