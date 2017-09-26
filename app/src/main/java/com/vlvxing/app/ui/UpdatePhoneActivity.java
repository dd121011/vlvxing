package com.vlvxing.app.ui;

import android.content.Context;
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
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.presenter.LoginAndRegisterPresenter;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更改手机号
 *
 * @ClassName:RegisterActivity
 * @PackageName:com.handongkeji.user
 * @Create On 2016/10/9   9:35
 * @Site:http://www.handongkeji.com
 * @author:caihuan
 * @Copyrights 2016/10/9 handongkeji All rights reserved.
 */
public class UpdatePhoneActivity extends BaseActivity implements LoginAndRegisterPresenter.IView {


    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
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
        setContentView(R.layout.activity_updatephone);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
       headTitle.setText("修改手机号");
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
                    mPresenter.getVerifyCode(phone, 1, "3","3");
                }
                break;
            case R.id.sure_txt:
                clickSure();
                break;
        }
    }

    private void clickSure() {
        final String phone= phoneEdt.getText().toString().trim();
        String code=codeEdt.getText().toString().trim();
        if (!ValidateHelper.isPhoneNumberValid(phone)) {
            Toast.makeText(this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog("提交中...");
        String url= Constants.URL_UPDATEPHONE;
        HashMap<String, String> params = new HashMap<>();
        params.put("phoneNum", myApp.getUserMobile());
        params.put("newphoneNum",phone); //新手机号
        params.put("userId",myApp.getUserId());
        params.put("infoCode",code );
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
                    myApp.setPhone(phone);
                    ToastUtils.show(UpdatePhoneActivity.this,"修改成功!");
                    UpdatePhoneActivity.this.finish();
                }else {
                    dismissDialog();
                    ToastUtils.show(UpdatePhoneActivity.this,message);
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
