package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;

/**
 *
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
        if (splash1 == 1){
            handler.postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            },1000);
        }else{ //引导页
            splash.edit().putInt("splash",1).commit();
            handler.postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this,GuidenceActivity.class));
                SplashActivity.this.finish();
            },1000);
        }
    }
}
