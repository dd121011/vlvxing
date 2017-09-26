package com.vlvxing.app.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.FileUtil;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/18 0018.
 * 关于我们
 */

public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.phone_txt)
    TextView phoneTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        headTitle.setText("关于我们");
        initDatas();
    }

    private void initDatas() {
        showDialog("加载中...");
        String url = Constants.URL_ABOUTWE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {

            @Override
            public void dataLoaded(ResponseData data) {
                String json = data.getJson();
                if (null != json && !json.equals("")) {
                    try {
                        JSONObject obj = new JSONObject(json);
                        Log.e("TAG", "obj" + obj);
                        String status = obj.getString("status");
                        if ("1".equals(status)) {
                            JSONObject object = obj.getJSONObject("data");
                            String phone = object.getString("phonenum");
                            String context = object.getString("textcontext");
                            String companyname = object.getString("companyname");
                            phoneTxt.setText(phone);
                            String text = context;
                            if (!TextUtils.isEmpty(text)) {

                                int width = (int) (myApp.getScreenWidth() * 0.32f - 30f);
                                if (text != null) {
                                    String[] splitd = text.split("img");
                                    StringBuffer sbd = new StringBuffer();
                                    for (int i = 0; i < splitd.length; i++) {
                                        if (i < splitd.length - 1) {
                                            sbd.append(splitd[i] + "img name='cellImage' ");
                                        } else {
                                            sbd.append(splitd[i]);
                                        }
                                    }
                                    text = sbd.toString();
                                    text += "<script> var imgs = document.getElementsByName(\"cellImage\");var width ="
                                            + width
                                            + ";for(var i=0;i<imgs.length;i++){var img = imgs[i];var iWidth = img.offsetWidth;var iHeight = img.offsetHeight;var height = iHeight * width / iWidth;img.style.width = width + 'px';img.style.height = height + 'px';} </script>";
                                    File file = new File(Constants.ENVIROMENT_DIR_CACHE + "style.html");
                                    FileUtil.writeFile(file, text);
                                    webview.getSettings().setDefaultTextEncodingName("UTF-8");
                                    webview.getSettings().setJavaScriptEnabled(true);
                                    webview.loadUrl("file://" + file.getPath());
                                }
                            }
                        }
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }

                }
                dismissDialog();
            }

        });

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
