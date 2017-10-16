package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 社区--发表心情
 */

public class ForumPublishActivity extends BaseActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forum_publish);
        ButterKnife.bind(this);
        context = this;
    }


    @OnClick({R.id.return_img,R.id.submit_btn,R.id.location_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_img:
                finish();
                break;
            case R.id.submit_btn:
                finish();
                break;
            case R.id.location_city:
                Intent intent = new Intent(context,ForumPublishLocaitonActivity.class);
                startActivityForResult(intent,1);// 跳转并要求返回值，0代表请求值(可以随便写)
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:

                if (data != null){
//                    Bundle bundle = data.getExtras();
//                    String result  = bundle.getString("result");// 得到子窗口ChildActivity的回传数据
                      data.getStringExtra("result");
                }


                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
