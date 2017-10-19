package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.ui.BaseActivity;
import com.lidong.photopicker.ImageCaptureManager;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.vlvxing.app.R;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 社区--发表心情 视频
 */

public class ForumPublishVideoActivity extends BaseActivity {

    private Context context;

    @Bind(R.id.edit_txt)
    EditText editText;
    @Bind(R.id.top_relay)
    RelativeLayout topRelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forum_publish_video);
        ButterKnife.bind(this);
        context = this;
        //图片列表相关设置
        setGridViewSetting();
        setGridViewOnItemClickListener();
        setGridViewData();

        //设置第一次进入界面不弹出软键盘
        topRelay.setFocusable(true);
        topRelay.setFocusableInTouchMode(true);
        topRelay.requestFocus();

    }



    private void setGridViewSetting(){

    }


    private void setGridViewData(){

    }

    private void setGridViewOnItemClickListener(){

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
        if(resultCode == RESULT_OK){
            switch (requestCode)
            {
                case 1:
                    if (data != null){
//                    Bundle bundle = data.getExtras();
//                    String result  = bundle.getString("result");// 得到子窗口ChildActivity的回传数据
                        data.getStringExtra("result");
                    }


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }




}
