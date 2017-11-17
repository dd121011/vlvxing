package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.handongkeji.common.ValidateHelper;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyProcessDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vlvxing.app.R;
import com.vlvxing.app.common.ActivityManager;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.IsInstallApp;
import com.vlvxing.app.presenter.LoginAndRegisterPresenter;
import com.vlvxing.app.utils.UmAddAliasAndTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 *
 * @ClassName:LoginActivity
 * @PackageName:com.handongkeji.user
 * @Create On 2016/10/9   9:34
 * @Site:http://www.handongkeji.com
 * @author:zhouhao
 * @Copyrights 2016/10/9 handongkeji All rights reserved.
 */
public class LoginActivity extends BaseActivity implements LoginAndRegisterPresenter.IView {


    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.pass_edt)
    EditText passEdt;
    private LoginAndRegisterPresenter mPresenter;
    private MyProcessDialog dialog;

    private static final int TYPE_WX = 1;                       //选中微信的类型
    private static final int TYPE_WB = 3;                       //选中微博的类型
    private static final int TYPE_QQ = 2;                       //选中QQ的类型
    private int TYPE_THIRELOGIN = 0;                            //选中的类型
    private final static int MES_LOGINERROR = 0;
    private final static int MESS_SUCCESSLOGIN = 1;
    private final static int MESS_FAILLOING = 2;
    private UMShareAPI mShareAPI = null;                         //第三方的分享
    private SHARE_MEDIA platform = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        mShareAPI = UMShareAPI.get(this);
        mPresenter = new LoginAndRegisterPresenter(this);
        dialog = new MyProcessDialog(this);
        dialog.setMsg("登录中...");
    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            Log.d("aaa",data.toString());
            // 获取uid
            Toast.makeText(getApplicationContext(), "get 授权", Toast.LENGTH_SHORT).show();
            String userOpenToken, uid = null,screen_name,imageurl;
            dialog.dismiss();
            if (null != data) {
                if (TYPE_THIRELOGIN == TYPE_WB) {
                    uid = data.get("id");
                    userOpenToken = data.get("id");
                    screen_name = data.get("screen_name");
                    imageurl = data.get("avatar_hd");
                } else if (TYPE_THIRELOGIN == TYPE_QQ){//openid
                    uid = data.get("openid");
                    userOpenToken = data.get("openid");
                    screen_name = data.get("screen_name");
                    imageurl = data.get("profile_image_url");
                }else {
                    uid = data.get("unionid");
                    userOpenToken = data.get("unionid");
                    screen_name = data.get("screen_name");
                    imageurl = data.get("profile_image_url");
                }
                loginByOpen(TYPE_THIRELOGIN, uid, userOpenToken,screen_name,imageurl);
            } else {
                Toast.makeText(LoginActivity.this, "get fail", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            dialog.dismiss();
            Log.d("aaa", "Faile");
//            Toast.makeText(getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            dialog.dismiss();
            Log.d("aaa", "cancel");
//            Toast.makeText(getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }

    };


    /**
     * 第三方的登录方法  判断有没有注册 如果没有注册则 跳到注册页面
     *
     * @param typeid
     * @param openId
     * @param openToken
     */
    public void loginByOpen(final int typeid, final String openId, final String openToken,String name,String pic) {
        String url = Constants.URL_LOGIN_OPEN;
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("useropentype", typeid + "");
        params.put("useropenid", openId);
        params.put("useropentoken", openToken);
        params.put("apptype", "1");
        params.put("usernick",name);
        params.put("picUrl",pic);
        RemoteDataHandler.asyncLoginPost(url, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                String json = data.getJson();
                Log.d("aaa", json + "");
                if (StringUtils.isStringNull(json)) {
                    dialog.dismiss();
                    return;
                }
                try {
                    JSONObject obj = new JSONObject(json);
                    String status = obj.getString("status");
                    if (status.equals("1")) {
//                        JSONObject validData = obj.getJSONObject("data");
//                        String useId = validData.getString("uid");
//                        String token = validData.getString("token");
//                        final String userid = obj.getString("uid");
//                        String mobileNum = obj.getString("account");
////                            String password = obj.getString("password");
//                        String thirdid = obj.getString("thirdid");                              //第三方的Id
//                        String userNick = obj.getString("userNick");
//                        String isRegister = obj.getString("isRegister");
                        String token=obj.getString("data");
                        String uid=obj.getString("message");
//                        if ("1".equals(isRegister)) {
//                            mPresenter.messagePush(token);
//                        }
                        //第三方的支付方式
                        myApp.setUserLogin("1", "1", token, uid, "1", "1");
//                        myApp.setPhone(mobileNum);
//                        myApp.setMessageT(mobileNum);
                        //添加别名

                        UmAddAliasAndTag.addUmengAlias(uid,"",LoginActivity.this);
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ActivityManager.getInstance().finishOthers(MainActivity.class.getSimpleName());
                            }
                        }.start();
                    } else if (status.equals("2")) {
                        Toast.makeText(LoginActivity.this, "还没有注册，请先注册", Toast.LENGTH_SHORT).show();
                        Log.i("LoginActivity", "进入注册页面");
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userOpenType", typeid + "");
                        bundle.putString("userOpenId", openId);
                        bundle.putString("openToken", openToken);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
    }

    private boolean checkPhoneNum(String phoneNum) {
        boolean result = ValidateHelper.isPhoneNumberValid(phoneNum);
        if (!result)
            Toast.makeText(this, "请输入正确的手机号",Toast.LENGTH_SHORT).show();
        return result;
    }



    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSignInOrRegisterComplete(String uid, String companyid) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        UmAddAliasAndTag.addUmengAlias(uid,companyid,LoginActivity.this);
        dialog.dismiss();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
    }

    @Override
    public void onGetVerifyCodeComplete(String msg) {

    }

    @Override
    public void onSignInOrRegisterError(String msg) {
        dialog.dismiss();
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }


    @OnClick({R.id.return_lin, R.id.forgetpwd_txt, R.id.login_txt, R.id.regist_txt, R.id.weixin, R.id.qq, R.id.web})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.forgetpwd_txt:
                startActivity(new Intent(this,ForgetPwdActivity.class).putExtra("type",1));
                break;
            case R.id.login_txt:
                //登录
                String phone= phoneEdt.getText().toString().trim();
                String pass=passEdt.getText().toString().trim();
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
                dialog.show();
                mPresenter.signInOrRegister(phone, null, pass, 0);

                break;
            case R.id.regist_txt:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.weixin: //微信登录
                if (!IsInstallApp.isInstall(this, "com.tencent.mm")) {
                    Toast.makeText(this, "您还没有安装微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.show();
                TYPE_THIRELOGIN = TYPE_WX;
                platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
                break;
            case R.id.web: //微博登录
                dialog.show();
                TYPE_THIRELOGIN = TYPE_WB;
                platform = SHARE_MEDIA.SINA;
                mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
                break;
            case R.id.qq: //QQ登录
                if (!IsInstallApp.isInstall(this, "com.tencent.mobileqq")) {
                    Toast.makeText(this, "您还没有安装QQ", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.show();
                TYPE_THIRELOGIN = TYPE_QQ;
                platform = SHARE_MEDIA.QQ;
                mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
