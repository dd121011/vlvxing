package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * 社区--我的消息
 */

public class ForumPublishMyMessageActivity extends BaseActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forum_publish_my_message);
        ButterKnife.bind(this);
        context = this;


    }



    @OnClick({R.id.return_img, R.id.my_comment_lin, R.id.my_reminder_lin,R.id.my_private_letters_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_img:
                finish();
                break;
            case R.id.my_comment_lin:
                //评论我的
                Intent intent = new Intent(context,ForumPublishMyCommentActivity.class);
                startActivity(intent);
                break;
            case R.id.my_reminder_lin:
                Intent intent2 = new Intent(context,ForumPublishMyReminderActivity.class);
                startActivity(intent2);
                //关注提醒
                break;
            case R.id.my_private_letters_lin:
                //我的私信
//                finish();
                break;
        }
    }

}
