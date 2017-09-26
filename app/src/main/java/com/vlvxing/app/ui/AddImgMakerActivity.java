package com.vlvxing.app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.utils.CacheData;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 添加图片标注点
 */

public class AddImgMakerActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.save_txt)
    TextView saveTxt;
    private Bitmap bitmap;
    private double lat, lng;
    private String mLecenseUrl, address,id,path;
    private boolean isRecord; //判断是否点击了开始录制
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addimgmaker_layout);
        ButterKnife.bind(this);
        // 获取图片的宽度
        int img_width = MyApp.getInstance().getScreenWidth();
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.height = (img_width * 150) /240; // 750:500
        img.setLayoutParams(params);
        Intent intent = getIntent();
        type=intent.getIntExtra("type",0); //1编辑修改  0添加标注
        if (type==1){
            id=intent.getStringExtra("id");
            String picurl = intent.getStringExtra("url");
            if (!StringUtils.isStringNull(picurl)) {
                Glide.with(this).load(picurl).into(img);
            }
            headTitle.setText("编辑图片标注点");
        }else {
            path = intent.getStringExtra("path");
            lat = intent.getDoubleExtra("lat", 0);
            lng = intent.getDoubleExtra("lng", 0);
            address = intent.getStringExtra("address");
            isRecord = intent.getBooleanExtra("isRecord", false);
//            img.setImageBitmap(bitmap);
            Glide.with(this).load(path).into(img);
            headTitle.setText("添加图片标注点");
        }
    }

    private void uploadImg() {
        showDialog("上传中...");
        String url = Constants.URL_UPLOAD;//上传图片接口
        HashMap<String, File> fileMap = new HashMap<>();
//        String filePath = Constants.CACHE_DIR_UPLOADING_IMG + "/lecenseimg.jpg";//待上传图片缓存目录
        final File file = new File(path);//保存图片路径
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            bos.flush();
//            bos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        fileMap.put("file", file);
        HashMap<String, String> params = new HashMap<>();
        params.put("filemark", "3");
        RemoteDataHandler.asyncMultipartPost(url, params, fileMap, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                String json = data.getJson();
                if (null != json && !json.equals("")) {
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(data.getJson());
                        String status = obj.getString("status");
                        String message = obj.getString("message");
                        if ("1".equals(status)) {
                            mLecenseUrl = obj.getString("data").replace("\\", "/");
                            if (isRecord) {
                                saveRecord();
                            }else{
                                saveImg(); // 保存记录（单个图片或视频）
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplication(), "服务器或网络异常", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 保存记录（单个图片或视频）
     */
    private void saveImg() {
        String url = Constants.URL_SAVETRAVEL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        final String name = nameEdt.getText().toString().trim();
        if (!StringUtils.isStringNull(name)) {
            params.put("pathName", name);
        }
        params.put("address", address);
        params.put("lng", lng + "");
        params.put("lat", lat + "");
        params.put("picUrl", mLecenseUrl);
        params.put("time", System.currentTimeMillis() + "");
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
                    ToastUtils.show(AddImgMakerActivity.this, "提交成功!");
                    saveRecord();
                    finish();
                } else {
                    ToastUtils.show(AddImgMakerActivity.this, message);
                }
                dismissDialog();
            }
        });
    }

    private void saveRecord() {
         String name = nameEdt.getText().toString().trim();
        if (StringUtils.isStringNull(name)) {
           name="";
        }
        RecordModel model = new RecordModel(lng, lat, mLecenseUrl, "", "",System.currentTimeMillis() + "", name, address);
        List<RecordModel> list = new ArrayList<>();
        list.add(model);
        CacheData.saveRecentSubList(list, "addmaker");
        dismissDialog();
        finish();
    }

    @OnClick({R.id.return_lin, R.id.save_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.save_txt:
                if (type==0) {
                    uploadImg(); //添加标注点
                }else{
                    UpdateImg(); //编辑标注
                }
                break;
        }
    }

    /**
     * 编辑记录（单个图片或视频）
     */
    private void UpdateImg() {
        showDialog("上传中...");
        String url = Constants.URL_UPDATETRAVEL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        String name = nameEdt.getText().toString().trim();
        if (!StringUtils.isStringNull(name)) {
            params.put("pathName", name);
        }
        params.put("pathInfoId", id);
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
                    ToastUtils.show(AddImgMakerActivity.this, "修改成功!");
                   finish();
                    SaveAfterActivity.mContext.finish();
                } else {
                    ToastUtils.show(AddImgMakerActivity.this, message);
                }
                dismissDialog();
            }
        });
    }
}
