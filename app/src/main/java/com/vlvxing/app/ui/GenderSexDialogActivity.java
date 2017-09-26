package com.vlvxing.app.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;

public class GenderSexDialogActivity extends BaseActivity {
    private Button btn_cancel;
    private RelativeLayout btn_take_photo, btn_pick_photo;
    private LinearLayout layout;
    private static final int PHOTO_GENDER_CUT = 5;// 选择男女结果
    Intent mIntent = new Intent();

    private ImageView imagenan,imagenv;
    private String usersex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_sex_dialog);
        initViews();
        Intent intent=getIntent();
        usersex=intent.getStringExtra("gender");
        if ("男".equals(usersex)) {
            imagenan.setVisibility(View.VISIBLE);
            imagenv.setVisibility(View.INVISIBLE);
        }else if("女".equals(usersex)){
            imagenan.setVisibility(View.INVISIBLE);
            imagenv.setVisibility(View.VISIBLE);
        }
        initOnClick();
    }
    /**
     * 初始化视图
     */
    public void initViews() {
        btn_take_photo = (RelativeLayout) findViewById(R.id.btn_take_photo);
        btn_pick_photo = (RelativeLayout) findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        layout = (LinearLayout) findViewById(R.id.pop_layout);
        imagenan=(ImageView) findViewById(R.id.image_check_nan);
        imagenv=(ImageView) findViewById(R.id.image_check_nv);

    }

    /**
     * 初始化事件
     */
    public void initOnClick() {
        // 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        // layout.setOnClickListener(new OnClickListener() {
        //
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
        // Toast.LENGTH_SHORT).show();
        // }
        // });
        // 添加按钮监听
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GenderSexDialogActivity.this.finish();
            }
        });
        btn_pick_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mIntent.putExtra("woman", "女");
                imagenan.setVisibility(View.INVISIBLE);
                imagenv.setVisibility(View.VISIBLE);

                Result();
            }

        });
        btn_take_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mIntent.putExtra("man", "男");
                imagenv.setVisibility(View.INVISIBLE);
                imagenan.setVisibility(View.VISIBLE);

                Result();
            }
        });

    }
    private void Result() {
        // 设置结果，并进行传送
        setResult(PHOTO_GENDER_CUT, mIntent);
        GenderSexDialogActivity.this.finish();

    }
    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GenderSexDialogActivity.this.finish();
        return true;
    }

    // ----------------------------------------------------

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
