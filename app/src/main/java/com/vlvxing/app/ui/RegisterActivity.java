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
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyProcessDialog;
import com.vlvxing.app.R;
import com.vlvxing.app.presenter.LoginAndRegisterPresenter;
import com.vlvxing.app.utils.UmAddAliasAndTag;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 *
 * @ClassName:RegisterActivity
 * @PackageName:com.handongkeji.user
 * @Create On 2016/10/9   9:35
 * @Site:http://www.handongkeji.com
 * @author:zhouhao
 * @Copyrights 2016/10/9 handongkeji All rights reserved.
 */
public class RegisterActivity extends BaseActivity implements LoginAndRegisterPresenter.IView {


    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.pass_edt)
    EditText passEdt;
    @Bind(R.id.code_edt)
    EditText codeEdt;
    @Bind(R.id.getcode_txt)
    TextView getcodeTxt;
    @Bind(R.id.regist_txt)
    TextView registTxt;
    private LoginAndRegisterPresenter mPresenter;
    private MyProcessDialog dialog;
    private CountDownTask task;

    private String TAG = "RegisterActivity";
    private String userOpenType, userOpenId, openToken;    //判断注册的类型，第三方返回的Id 和 token
    private boolean isChangeUrl = false;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userOpenType = bundle.getString("userOpenType");
            userOpenId = bundle.getString("userOpenId");
            openToken = bundle.getString("openToken");
            if (null != userOpenType && !userOpenType.equals("")) {
                isChangeUrl = true;
            }
        }

        mPresenter = new LoginAndRegisterPresenter(this);
        dialog = new MyProcessDialog(this);
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
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        UmAddAliasAndTag.addUmengAlias(uid, companyid, this);
        dialog.dismiss();
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }


    @Override
    public void onGetVerifyCodeComplete(String msg) {
        if (msg.contains("已注册")) {
            getcodeTxt.setText(getString(R.string.login_get_verify_code));
            getcodeTxt.setEnabled(true);
        }
        codeEdt.requestFocus();
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignInOrRegisterError(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.return_lin, R.id.login_txt, R.id.getcode_txt, R.id.regist_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.login_txt:
                startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                break;
            case R.id.getcode_txt:
                phone = phoneEdt.getText().toString().trim();
                if (checkPhoneNum(phone)) {
                    task.start();
                    getcodeTxt.setEnabled(false);
                    mPresenter.getVerifyCode(phone, 1, "1","1");
                }
                break;
            case R.id.regist_txt:
                phone = phoneEdt.getText().toString().trim();
                String verifyCode = codeEdt.getText().toString().trim();
                String pass = passEdt.getText().toString().trim();
                if (StringUtils.isStringNull(pass)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(this, "密码位数不能少于6位!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkPhoneNum(phone) && checkVerifyCode(verifyCode)) {
                    dialog.show();
                    if (isChangeUrl) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("userOpenType", userOpenType);                            //第三方的登录信息的参数
                        params.put("userOpenId", userOpenId);
                        params.put("userOpenToken", openToken);
                        params.put("userMobile", phone);
//                        params.put("username", phone);
                        params.put("userPass", pass);
                        params.put("code", verifyCode);
//                        params.put("appType", "1");
                        mPresenter.registerByOpen(params);
                    } else {
                        mPresenter.signInOrRegister(phone, verifyCode, pass, 1);
                    }
                }
                break;
        }
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
