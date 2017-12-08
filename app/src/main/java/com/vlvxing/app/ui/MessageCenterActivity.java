package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/8 0008.
 * 消息中心
 */

public class MessageCenterActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.rel)
    RelativeLayout rel;
    @Bind(R.id.time_txt)
    TextView timeTxt;
    @Bind(R.id.content_txt)
    TextView contentTxt;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.rel1)
    RelativeLayout rel1;
    @Bind(R.id.time1_txt)
    TextView time1Txt;
    @Bind(R.id.content1_txt)
    TextView content1Txt;
    @Bind(R.id.system_lin)
    LinearLayout systemLin;
    @Bind(R.id.ordermsg_lin)
    LinearLayout ordermsgLin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msgcenter_layout);
        ButterKnife.bind(this);
        headTitle.setText("消息中心");
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        showDialog("加载中...");
        String url = Constants.URL_MSGCENTER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());

        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                JSONObject obj = new JSONObject(json);
                String status = obj.getString("status");
                String message = obj.getString("message");
                if (status.equals("1")) {
                    systemLin.setVisibility(View.VISIBLE);
                    ordermsgLin.setVisibility(View.VISIBLE);
                    JSONArray array = obj.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String type = object.getString("messageType"); //消息类型， 1：系统消息，2：通知消息
                        String time = object.getString("lastRecordTime");
                        String t="";
                        if (!StringUtils.isStringNull(time)) {
                             t = DataUtils.format(time, "yyyy-MM-dd HH:mm");
                        }
                        String content = object.getString("messageText");
                        //true：有未读消息  false：无未读消息
                        String flag = object.getString("hasNoRead");
                        msgType(type, t, content, flag);

                    }
                } else {
                    ToastUtils.show(MessageCenterActivity.this, message);
                }
                dismissDialog();
            }
        });
    }

    private void msgType(String type, String t, String content, String flag) {
        if ("1".equals(type)) { //true：有未读消息  false：无未读消息
            timeTxt.setText(StringUtils.isStringNull(t)?"":t);
            contentTxt.setText(StringUtils.isStringNull(content)?"":content);
            if ("true".equals(flag)) {
                rel.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
            } else {
                rel.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
            }
        } else {
            time1Txt.setText(StringUtils.isStringNull(t)?"":t);
            content1Txt.setText(StringUtils.isStringNull(content)?"":content);
            if ("true".equals(flag)) {
                rel1.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);
            } else {
                rel1.setVisibility(View.GONE);
                img1.setVisibility(View.VISIBLE);
            }
        }
    }


    @OnClick({R.id.return_lin, R.id.system_lin, R.id.ordermsg_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.system_lin: //系统消息
                startActivity(new Intent(this, SystemMessageActivity.class).putExtra("type", 1));
                break;
            case R.id.ordermsg_lin:  //订单消息
                startActivity(new Intent(this, SystemMessageActivity.class).putExtra("type", 2));
                break;
        }
    }
}
