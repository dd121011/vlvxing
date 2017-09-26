package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handongkeji.autoupdata.CheckVersion;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.CallDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vlvxing.app.R;
import com.vlvxing.app.common.ClearCacheDialog;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.utils.ToastUtils;
import com.vlvxing.app.utils.UmAddAliasAndTag;
import com.yanzhenjie.permission.SettingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/5/18 0018.
 * 设置
 */

public class SettingActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.phone_txt)
    TextView phoneTxt;
    @Bind(R.id.num_txt)
    TextView numTxt;
    @Bind(R.id.remind_img)
    ImageView remindImg;
    private boolean isSetSound = false; //是否设置信息提醒
    public final ObservableField<String> cacheSize = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        headTitle.setText("设置");
//        getPushStatus();
        getUserInfo();
        setPhone();
        getCacheSize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPhone();
    }

    private void getUserInfo() {
        String token = MyApp.getInstance().getUserTicket();
        if (StringUtils.isStringNull(token)) {
            return;
        }
        String url = Constants.URL_GETUSERINFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                JSONObject object = new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)) {
                    JSONObject obj = object.getJSONObject("data");
                    String ispush = obj.getString("userispush"); //1推送  2不推送
                    int isPush = Integer.parseInt(ispush);
                    if (isPush == 1) {
                        remindImg.setBackgroundResource(R.drawable.switchbutton_on);
                        isSetSound = false;
                    } else {
                        remindImg.setBackgroundResource(R.drawable.switchbutton_off);
                        isSetSound = true;
                    }
                } else {
                    ToastUtils.show(SettingActivity.this, message);
                }
            }
        });
    }

    private void setPhone() {
        //电话号码中间四位数用星号替代
        String phone = myApp.getPhone();
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }

            phoneTxt.setText(sb.toString());
        }
    }

    @OnClick({R.id.exit_txt, R.id.return_lin, R.id.rel1, R.id.rel2, R.id.rel3, R.id.rel4, R.id.rel5, R.id.rel6, R.id.rel7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_txt:  //退出登录
                CallDialog islogin = new CallDialog((Activity) this, "你确定要退出登录？");
                islogin.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myApp.logout(); //清空用户信息
                        UmAddAliasAndTag.RemoveAlias(myApp.getUserId(), "", SettingActivity.this);
                        startActivity(new Intent(SettingActivity.this, MainActivity.class));
                    }
                });
                break;
            case R.id.return_lin:
                finish();
                break;
            case R.id.rel1:  //修改密码
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.rel2:   //更换手机号
                startActivity(new Intent(this, UpdatePhoneActivity.class));
                break;
            case R.id.rel3:  //信息提醒  //1推送  2不推送
                if (isSetSound) {
                    remindImg.setBackgroundResource(R.drawable.switchbutton_on);
                    isSetSound = false;
                    isPushMsg(1);
                } else {
                    remindImg.setBackgroundResource(R.drawable.switchbutton_off);
                    isSetSound = true;
                    isPushMsg(2);
                }
                break;
            case R.id.rel4:  //关于我们
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.rel5:  //意见反馈
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.rel6:  //清除本地缓存
                ClearCacheDialog clearCacheDialog = new ClearCacheDialog(this, new SettingService() {
                    @Override
                    public void execute() {
                        File externalCacheDir = getExternalCacheDir();
                        deleteCache(externalCacheDir);
                        getCacheSize();
                    }

                    @Override
                    public void cancel() {

                    }
                });
                clearCacheDialog.show();

                break;
            case R.id.rel7:  //检查更新
                CheckVersion.update(this, true);
                break;
        }
    }

    private void isPushMsg(int type) {
        String url = Constants.URL_TOGGLE_MESSAGE;
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", myApp.getUserId());
        params.put("isPush", type + ""); //1推送  2不推送
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                JSONObject object = new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)) {
                    dismissDialog();
                    ToastUtils.show(SettingActivity.this, "修改成功!");
                } else {
                    ToastUtils.show(SettingActivity.this, message);
                }
            }
        });
    }

    private void getPushStatus() {
        String url = Constants.URL_TOGGLE_MESSAGE;
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", myApp.getUserId());
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                JSONObject object = new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)) {
                    String statu = object.getString("Data");
                    int s = Integer.parseInt(statu);
                    if (s == 1) { //1推送2不推送
                        remindImg.setBackgroundResource(R.drawable.switchbutton_on);
                        isSetSound = false;
                    } else {
                        remindImg.setBackgroundResource(R.drawable.switchbutton_off);
                        isSetSound = true;
                    }
                } else {
                    ToastUtils.show(SettingActivity.this, message);
                }
            }
        });
    }

    public void getCacheSize() {
        Schedulers.newThread().createWorker().schedule(() -> {
            File externalCacheDir = getExternalCacheDir();
            int fileLength = getFileLength(externalCacheDir);
            String size = "";
            if (fileLength == 0) {
                size = "";
            } else if (fileLength / (1024 * 1024) < 0.1) {
                size = fileLength / 1024 + "KB";
            } else {
                size = fileLength / (1024 * 1024) + "M";
            }
            cacheSize.set(size);
            final String temp = size;
            runOnUiThread(() -> numTxt.setText(temp));
        });
    }

    private int getFileLength(File file) {
        int size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                size += getFileLength(file1);
            }
        } else {
            size += file.length();
        }
        return size;
    }

    private void deleteCache(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                deleteCache(file1);
            }
        } else {
            file.delete();
        }
    }
}
