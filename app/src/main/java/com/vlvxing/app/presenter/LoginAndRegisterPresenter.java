package com.vlvxing.app.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.BaseBean;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @ClassName:LoginAndRegisterPresenter
 * @PackageName:com.youqin.pinche.presenter
 * @Create On 2016/10/9   16:30
 * @Site:http://www.handongkeji.com
 * @author:zhouhao
 * @Copyrights 2016/10/9 handongkeji All rights reserved.
 */
public class
LoginAndRegisterPresenter {

    public interface IView {
        public Context getContext();

        public void onSignInOrRegisterComplete(String uid,String companyid);

        public void onGetVerifyCodeComplete(String msg);

        public void onSignInOrRegisterError(String msg);
    }

    private Context mContext;
    private IView mIView;

    public LoginAndRegisterPresenter(IView mIView) {
        this.mIView = mIView;
        mContext = mIView.getContext();
    }

    /**
     * 获取验证码
     */
    public void getVerifyCode(String phoneNum, final int isSignIn, String forwhat,String userType) {
        String url;
        if (isSignIn == 0) {
            url = Constants.URL_VERIFYCODE_SIGNIN;
        } else {  //注册
            url = Constants.URL_GETVERIFYCODE;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("phoneNum", phoneNum);
        params.put("forwhat", forwhat);  //类型(1:注册 2:找回密码)  更换手机号3
        params.put("userType",userType); //注册时未1，修改密码时未2  更换手机号3
        RemoteDataHandler.asyncPost(url, params, mContext, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (TextUtils.isEmpty(json) || "null".equals(json)) return;
                try {
                    JSONObject object = new JSONObject(json);
                    int status = object.getInt("status");
                    if (status == 0) {
                        if (mIView != null) {
                            String msg = "";
                            if (isSignIn == 0) {
                                msg = "获取验证码失败";
                            } else if (isSignIn == 1) {
                                msg = object.getString("message");
                            }
                            mIView.onGetVerifyCodeComplete(msg);
                        }

                    } else if (status == 1) {
                        if (mIView != null) {
                            mIView.onGetVerifyCodeComplete("已发送验证码");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 登录 / 注册
     *
     * @param phoneNum
     * @param verifyCode
     * @param isSignIn   0 登录,  1 注册
     */
    public void signInOrRegister(final String phoneNum, String verifyCode, String pass, final int isSignIn) {
        String url;
        if (isSignIn == 0) {
            url = Constants.URL_SIGN_IN;
        } else {
            url = Constants.URL_REGISTER;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("phoneNum", phoneNum);
        if (!StringUtils.isStringNull(verifyCode)) { //登录不传验证码
            params.put("userCode", verifyCode);
        }
        params.put("password",pass);
        params.put("appType", "1");
        RemoteDataHandler.asyncPost(url, params, mContext, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (TextUtils.isEmpty(json) || "null".equals(json)) return;
                Gson gson = new Gson();
                Type type = new TypeToken<BaseBean<HashMap<String, String>>>() {
                }.getType();
                BaseBean<HashMap<String, String>> baseBean = gson.fromJson(json, type);
                if (baseBean.getStatus() == 1) {
                    HashMap<String, String> map = baseBean.getData();
                    String uid = map.get("userId");
                    String token = map.get("token");
                    String companyid = "1";
//                    try {
//                        companyid = map.get("companyid");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    String isRegister=map.get("isRegister");
//                    if ("1".equals(isRegister)) {
//                        messagePush(token);
//                    }
                    ((MyApp) mContext.getApplicationContext()).setUserLogin(phoneNum, "123456", token, uid);
                    MyApp.getInstance().setPhone(phoneNum);
                    if (mIView != null) {
                        mIView.onSignInOrRegisterComplete(uid,companyid);
                    }
                } else {
                    if (mIView != null) {
                        String msg = "";
                        if (isSignIn == 0) {
                            msg = "登录失败，手机号或验证码错误！";
                        } else if (isSignIn == 1) {
                            msg = "注册失败，手机号或验证码错误！";
                        }
                        mIView.onSignInOrRegisterError(msg);
                    }
                }
            }
        });
    }

    public void registerByOpen(final HashMap<String,String> params){
        String url = Constants.URL_REGISTER_OPEN;
        RemoteDataHandler.asyncPost(url, params, mContext, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (TextUtils.isEmpty(json) || "null".equals(json)) return;
                JSONObject object=new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
//                Gson gson = new Gson();
//                Type type = new TypeToken<BaseBean<HashMap<String, String>>>() {
//                }.getType();
//                BaseBean<HashMap<String, String>> baseBean = gson.fromJson(json, type);
                if ("1".equals(status)) {
//                    HashMap<String, String> map = baseBean.getData();
////                    String uid = map.get("uid");
//                    String token = map.get("data");
//                    String isRegister=map.get("isRegister");
//                String tag = map.get("tag");//注册的时候需要一个tag添加到友盟后天
//                    if ("1".equals(isRegister)) {
//                        messagePush(token);
//                    }
                    String token=object.getString("data");
                    String uid="1";
                    ((MyApp) mContext.getApplicationContext()).setUserLogin(params.get("userMobile"), "123456", token, uid);
                    MyApp.getInstance().setPhone(params.get("userMobile"));
                    if (mIView != null) {
                        mIView.onSignInOrRegisterComplete(uid,"");
                    }
                } else {
                    if (mIView != null) {
                        String msg = "注册失败，手机号或验证码错误！";
                        mIView.onSignInOrRegisterError(msg);
                    }
                }
            }
        });
    }

    public void messagePush(String token){
        String url=Constants.URL_MESSAGEPUSH;
        HashMap<String,String> params=new HashMap<>();
        params.put("token",token);
        RemoteDataHandler.asyncTokenPost(url, mContext, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json=data.getJson();
                if (StringUtils.isStringNull(json)){
                    return;
                }
                JSONObject object=new JSONObject(json);
                String status=object.getString("status");
                String message=object.getString("message");
                if ("1".equals(status)){

                }else{
                    Toast.makeText(mContext,message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
