package com.vlvxing.app.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.handongkeji.widget.CallDialog;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.utils.ToastUtils;

import java.io.File;
import java.io.IOException;


/**
 * 设置头像弹出Dialog Activity
 *
 * @author chengs
 */
public class UserHeadimgSetDialogActivity2 extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private Intent intent, intent1;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    public final static int CAMERA_RESULT_CUT = 888;
    public static final int REQUEST_CODE4 = 4; //拍摄视频
    private File path, sppath;
    private Uri uri, spurl;
    public final static int CAMERA_RESULT = 777;
    private int type, t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_userheadimgset);
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WAKE_LOCK,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        initViews();
        initDatas();
        initViewsOper();
        initOnClick();
        //
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == requestCode) {
            boolean allGrant = true;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allGrant = false;
                }
            }
            if (allGrant) {
                initOnClick();
            } else {
                ToastUtils.show(UserHeadimgSetDialogActivity2.this, "您已拒绝开启照相机权限，如须使用照相机，请到设置中开启权限。");
            }
        }
    }

    private void takeVideo1() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivityForResult(intent, REQUEST_CODE4);
    }

    /**
     * 初始化视�?
     */
    public void initViews() {

        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0); //0选择本地图片  1拍摄视频
        t = intent.getIntExtra("t", 0); //1上传头像  0上传图片不裁剪
        if (type == 1) {
            btn_pick_photo.setText("拍摄视频");
        }
    }

    /**
     * 初始化数�?
     */
    public void initDatas() {
    }

    /**
     * 初始化视图操�?
     */
    public void initViewsOper() {
        // 从相册�?�择
        intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra("return-data", true);
        intent.setType("image/*");
        // 剪裁
        intent.putExtra("crop", "circleCrop");
        // 裁剪比例
        intent.putExtra("aspectX", 42);
        intent.putExtra("aspectY", 25);
        // 裁剪大小
        intent.putExtra("outputX", 336);
        intent.putExtra("outputY", 200);
//		intent.putExtra("scale", true);

        // 拍照 跳转到相�?
        intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
//        Uri outputUri = FileProvider.getUriForFile(this, "com.vlvxing.app.fileprovider",file);
//        Uri imageUri=FileProvider.getUriForFile(this, "com.vlvxing.app.fileprovider", new File("/storage/emulated/0/temp/1474960080319.jpg"));//通过FileProvider创建一个content类型的Uri
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setDataAndType(imageUri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
//        startActivityForResult(intent,1000);
    }

    /**
     * 初始化事�?
     */
    public void initOnClick() {
        // 添加按钮监听
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                UserHeadimgSetDialogActivity2.this.finish();
            }
        });
        btn_pick_photo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (type == 0) {
                    startActivityForResult(intent, PHOTO_REQUEST_GALLERY);// 从相册从选取
                } else { //拍摄视频
                    takeVideo1();
                }

            }
        });
        //拍照
        btn_take_photo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                path = new File(Constants.ENVIROMENT_DIR_CACHE + "cache.jpg");
                if (!path.exists()) {
                    try {
                        path.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

                //这和我写的一样啊  对啊 这是7.0 要这样写  这个是判断了啊
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//这是 7.0
                    uri = FileProvider.getUriForFile(getBaseContext(),"com.vlvxing.app.provider",path);
                } else {//<7.0
                    uri  = Uri.fromFile(path);
                }
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
            }
        });

    }

    // 实现onTouchEvent触屏函数但点击屏幕时�?毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        UserHeadimgSetDialogActivity2.this.finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_CAMERA || requestCode == PHOTO_REQUEST_GALLERY) {
            this.setResult(PHOTO_REQUEST_CUT, data);
        }
        if (requestCode == CAMERA_RESULT_CUT) { //裁剪
            if (data != null) { //这里已经拿到
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");  //
                    // 剪裁
                    intent.putExtra("crop", "true");
                    // 裁剪比例
                    intent.putExtra("aspectX", 42);
                    intent.putExtra("aspectY", 25);
                    // 裁剪大小
                    intent.putExtra("outputX", 336);
                    intent.putExtra("outputY", 200);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 1000);
                return;
            }
        } else if (requestCode == 1000) {
            this.setResult(PHOTO_REQUEST_CUT, data);
        } else if (requestCode == 4) {
            this.setResult(REQUEST_CODE4, data);
        }
        UserHeadimgSetDialogActivity2.this.finish();
    }
}
