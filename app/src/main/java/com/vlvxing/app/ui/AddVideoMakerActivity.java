package com.vlvxing.app.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyProcessDialog;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.utils.CacheData;
import com.vlvxing.app.utils.MyVideoThumbLoader;
import com.vlvxing.app.utils.ToastUtils;
import com.vlvxing.app.utils.UpLoadVideoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 添加视频标注点
 */

public class AddVideoMakerActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.save_txt)
    TextView saveTxt;
    @Bind(R.id.right_img)
    ImageView rightImg;
    @Bind(R.id.video_img)
    ImageView videoImg;
    private double lat, lng;
    private String coverUrl, address,videoPath,id;
    private boolean isRecord; //判断是否点击了开始录制
    private File file; //视频封面图
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvideomaker_layout);
        ButterKnife.bind(this);
        rightImg.setImageResource(R.mipmap.more);
        rightImg.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        type=intent.getIntExtra("type",0); //1编辑修改  0添加标注
        if (type==1){
            id=intent.getStringExtra("id");
            String coverurl = intent.getStringExtra("url");
            if (!StringUtils.isStringNull(coverurl)) {
                Glide.with(this).load(coverurl).into(img);
            }
            videoPath = intent.getStringExtra("videoPath"); //获取视频路径
            if (videoPath != null) {
                Bitmap videoThumb2 = MyVideoThumbLoader.getVideoThumb2(videoPath); //获取视频封面图
                file = saveBitmapFile(videoThumb2); //将视频封面图保存成图片
                img.setImageBitmap(videoThumb2); //将视频封面图设置到控件上
            }
            headTitle.setText("编辑视频标注点");
        }else {
            lat = intent.getDoubleExtra("lat", 0);
            lng = intent.getDoubleExtra("lng", 0);
            address = intent.getStringExtra("address");
            isRecord = intent.getBooleanExtra("isRecord", false);
//        img.setImageBitmap(bitmap);
            //设置视频
            videoPath = intent.getStringExtra("videoPath"); //获取视频路径
            if (videoPath != null) {
                Bitmap videoThumb2 = MyVideoThumbLoader.getVideoThumb2(videoPath); //获取视频封面图
                file = saveBitmapFile(videoThumb2); //将视频封面图保存成图片
                img.setImageBitmap(videoThumb2); //将视频封面图设置到控件上
            }
            headTitle.setText("添加视频标注点");
        }
    }

    //  Bitmap对象保存为图片文件Constants.CACHE_DIR_IMAGE+"/"+System.currentTimeMillis()+".jpg"
    public File saveBitmapFile(Bitmap bitmap){
        File file=new File(videoPath);//将要保存图片的路径
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
///storage/emulated/0/DCIM/Camera/VID_20170724_142021.mp4
    private void uploadImg() {
        showDialog("上传中...");
        String url = Constants.URL_UPLOAD;//上传图片接口
        HashMap<String, File> fileMap = new HashMap<>();
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
                            coverUrl = obj.getString("data").replace("\\", "/");
                            if (isRecord) {
                                saveRecord();
                            } else {
                                MyProcessDialog progressDialog = new MyProcessDialog(AddVideoMakerActivity.this);
                                progressDialog.setMsg("正在上传视频");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                final UpLoadVideoUtils upVideoUtil = new UpLoadVideoUtils(AddVideoMakerActivity.this,videoPath,coverUrl,progressDialog);
                                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        saveImg(upVideoUtil.successUrl); // 保存记录（单个图片或视频）
                                    }
                                });

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
    private void saveImg(String videoPath) {
        if (StringUtils.isStringNull(videoPath)){
            return;
        }
        showDialog("上传中...");
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
        params.put("videoUrl",videoPath); //上传成功后返回的视频路径
        params.put("coverUrl", coverUrl); //封面图，用户上传的第一张图片
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
                    ToastUtils.show(AddVideoMakerActivity.this, "提交成功!");
                    saveRecord();
                    finish();
                } else {
                    ToastUtils.show(AddVideoMakerActivity.this, message);
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
        RecordModel model = new RecordModel(lng, lat, "",coverUrl,videoPath, System.currentTimeMillis() + "", name, address);
        List<RecordModel> list = new ArrayList<>();
        list.add(model);
        CacheData.saveRecentSubList(list, "addmaker");
        dismissDialog();
        finish();
    }

    @OnClick({R.id.return_lin, R.id.save_txt,R.id.video_img})
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
            case R.id.video_img: //播放视频
                Intent intent = new Intent(this,VideoPlayActivity.class);
                intent.putExtra("path",videoPath);
                startActivity(intent);
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
        final String name = nameEdt.getText().toString().trim();
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
                    ToastUtils.show(AddVideoMakerActivity.this, "修改成功!");
                    finish();
                    SaveAfterVideoActivity.mContext.finish();
                } else {
                    ToastUtils.show(AddVideoMakerActivity.this, message);
                }
                dismissDialog();
            }
        });
    }
}
