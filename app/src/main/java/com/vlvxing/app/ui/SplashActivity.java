package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;

/**
 * 欢迎页
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences splash = getSharedPreferences("splash", Context.MODE_PRIVATE);
        int splash1 = splash.getInt("splash", 0);
        Handler handler = new Handler();
        String token = myApp.getUserTicket();
//        //判断是否是第一次进入界面
//        handler.postDelayed(() -> {
//            startActivity(new Intent(SplashActivity.this,MainActivity.class));
//            SplashActivity.this.finish();
//        },1000);


        if (splash1 == 1) {
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
                        if(!id.equals("")){
                            intent.putExtra("productId",id);
                            intent.putExtra("lineDetails",true);
                        }
                    }
                }
                startActivity(intent);
                SplashActivity.this.finish();
            }, 1000);
        } else { //引导页
            splash.edit().putInt("splash", 1).commit();
            handler.postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, GuidenceActivity.class));
                SplashActivity.this.finish();
            }, 1000);
        }
    }
}
