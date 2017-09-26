package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.model.SaveModel;
import com.vlvxing.app.utils.CacheData;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 保存轨迹
 */

public class SaveTrackActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.right_txt)
    TextView rightTxt;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.content_edt)
    EditText contentEdt;
    private String start,end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savetrack_layout);
        ButterKnife.bind(this);
        headTitle.setText("保存轨迹");
        rightTxt.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.return_lin, R.id.right_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt: //点击保存
                saveRecord();
                break;
        }
    }

    /**
     * 拼接路径的图片
     *标注
     * @return
     */
    private String generateJsonStr() {
        StringBuffer sb = new StringBuffer();
        List<RecordModel> list = CacheData.getRecentSubList("addmaker");
        SaveModel sm=new SaveModel();
        sm.setData(list);
        Gson gson=new Gson();
        String s = gson.toJson(sm);
        return s;
    }

    /**
     * 拼接路径的图片
     * 轨迹
     *
     * @return
     */
    private String generateCoordinate() {
        StringBuffer sb = new StringBuffer();
        List<RecordModel> list = CacheData.getRecentSubList("track");
        start=list.get(0).getAddress();
        end=list.get(list.size()-1).getAddress();
        for (int i=0;i<list.size();i++){
            String spliceString = "";
            RecordModel model = list.get(i);
            String lat = model.getLat()+"";
            String lng = model.getLng() + "";
            spliceString = lng + "#" + lat;
            sb.append(spliceString);
            if (i == list.size() - 1) {
                continue;
            }
            sb.append("-");
        }
        return sb.toString().trim();
    }
    private void saveRecord() {
        String name=nameEdt.getText().toString().trim();
//        if (StringUtils.isStringNull(name)){
//            ToastUtils.show(this,"请输入轨迹名称!");
//            return;
//        }
        String content=contentEdt.getText().toString().trim();
//        if (StringUtils.isStringNull(content)){
//            ToastUtils.show(this,"请输入轨迹描述!");
//            return;
//        }
        showDialog("");
        String url = Constants.URL_SAVETRAVELROAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("coordinate",generateCoordinate());
        params.put("token",myApp.getUserTicket());
        if (!StringUtils.isStringNull(name)){
            params.put("travelRoadTitle", name);
        }
        if (!StringUtils.isStringNull(content)){
            params.put("description", content);
        }
        params.put("startArea", start);
        params.put("destination", end);
        List<RecordModel> list = CacheData.getRecentSubList("addmaker");
        if (list.size()>0) {
            params.put("jsonPath", generateJsonStr()); //标注点数据
        }
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
                    ToastUtils.show(SaveTrackActivity.this, "保存成功!");
                    finish();
                } else {
                    ToastUtils.show(SaveTrackActivity.this, message);
                }
                dismissDialog();
            }
        });
    }
}
