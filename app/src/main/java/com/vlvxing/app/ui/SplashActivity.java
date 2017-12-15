package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.handongkeji.utils.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.SysadModel;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 欢迎页
 */

public class SplashActivity extends BaseActivity {

    private ImageView img;
    private Context mcontext;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img = (ImageView) findViewById(R.id.img);
        mcontext = this;
    }


    //获取bananer图
    public void getImgSource() {
        String url = Constants.URL_SYSAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("categoryId", "5"); //分类id(0:首页，1国内，2国外  3附近 4机票 5欢迎页)
        RemoteDataHandler.asyncTokenPost(url, mcontext, true, params, new RemoteDataHandler.Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        String json = data.getJson();
                        if (StringUtils.isStringNull(json)) {
                            return;
                        }
                        Gson gson = new Gson();
                        SysadModel model = gson.fromJson(json, SysadModel.class);
                        final int status = model.getStatus();
                        if (status == 1) {
//                            Glide.with(mcontext).load("").into(img);
                            if(model.getData()!=null){
                                String imgUrl = model.getData().get(0).getAdpicture();
                                if (!StringUtils.isStringNull(imgUrl)) { // 加载图片
                                    ImageLoader
                                            .getInstance().displayImage(imgUrl,
                                            img);
                                } else {
                                    img.setImageResource(R.mipmap.splash);
                                }
                            }else {
                                img.setImageResource(R.mipmap.splash);
                            }

                        } else {
                            img.setImageResource(R.mipmap.splash);
                        }
                        handler.postDelayed(() -> {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            Intent i_getvalue = getIntent();
                            String action = i_getvalue.getAction();
                            if (Intent.ACTION_VIEW.equals(action)) {
                                Uri uri = i_getvalue.getData();

                                if (uri != null) {
                                    String id = uri.getQueryParameter("id");
//                    String scheme = uri.getScheme();String host = uri.getHost();String port = uri.getPort() + "";String path = uri.getPath();String query = uri.getQuery();
//                    System.out.println("获得的数据name=" + id + "/r" + "scheme" + scheme + "/r" + "host" +
//                            "host" + host + "/r" + "port" + port + "/r" + "path" + path + "/r" + "query" + query);
                                    if (!id.equals("")) {
                                        System.out.println("获得数据 欢迎   id:" + id);
                                        intent.putExtra("productId", id);
                                        intent.putExtra("lineDetails", true);
                                        MyApp.getInstance().setSendUrl(true);
                                    }
                                }
                            }
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }, 3000);

                    }
                }
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences splash = getSharedPreferences("splash", Context.MODE_PRIVATE);
        int splash1 = splash.getInt("splash", 0);

        String token = myApp.getUserTicket();
//        //判断是否是第一次进入界面
//        handler.postDelayed(() -> {
//            startActivity(new Intent(SplashActivity.this,MainActivity.class));
//            SplashActivity.this.finish();
//        },1000);


        if (splash1 == 1) {
            getImgSource();

        } else { //引导页
            splash.edit().putInt("splash", 1).commit();
            handler.postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, GuidenceActivity.class));
                SplashActivity.this.finish();
            }, 3000);
        }
    }
}
