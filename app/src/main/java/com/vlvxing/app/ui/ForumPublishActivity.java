package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.FileUtil;
import com.lidong.photopicker.ImageCaptureManager;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 社区--发表心情 图片
 */

public class ForumPublishActivity extends BaseActivity {

    private Context context;
    @Bind(R.id.grid_view)
    GridView gridView;//图片列表
    @Bind(R.id.edit_txt)
    EditText editText;
    @Bind(R.id.top_relay)
    RelativeLayout topRelay;

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager; // 相机拍照处理类
    private GridAdapter gridAdapter;
    private Dialog successDialog;
//    private int recLen = 0;//倒计时关闭dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forum_publish);
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
        successDialog = new Dialog(context, R.style.BottomDialog);
    }


    private void setGridViewSetting() {
        //图片列表相关设置
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);
    }


    private void setGridViewData() {
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
    }

    private void setGridViewOnItemClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if ("000000".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(context);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true);//是否显示拍照
                    intent.setMaxTotal(6);//最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths);//已选中的照片地址，用于回选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);

                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(context);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
    }

    @OnClick({R.id.return_img, R.id.submit_btn, R.id.location_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_img:
                finish();
                break;
            case R.id.submit_btn:
                //发表成功
                String body = editText.getText().toString();
                if (!"".equals(body)){
                    showDialog();
                }else{
                    Toast.makeText(context, "您不说点什么嘛", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.location_city:
                Intent intent = new Intent(context, ForumPublishLocaitonActivity.class);
                startActivityForResult(intent, 1);// 跳转并要求返回值，0代表请求值(可以随便写)
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (data != null) {
//                    Bundle bundle = data.getExtras();
//                    String result  = bundle.getString("result");// 得到子窗口ChildActivity的回传数据
                        data.getStringExtra("result");
                    }
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
//                    Log.d(TAG, "list: " + "list = [" + list.size());
                    Toast.makeText(context, "list = [" + list.size(), Toast.LENGTH_LONG);
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
//                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    Toast.makeText(context, "ListExtra = [" + ListExtra.size(), Toast.LENGTH_LONG);
                    loadAdpater(ListExtra);
                    break;

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains("000000")) {
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;

        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if (listUrls.size() == 7) {
                listUrls.remove(listUrls.size() - 1);
            }
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return listUrls.size();
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.forum_gridview_image_item, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String path = listUrls.get(position);
            if (path.equals("000000")) {
                holder.image.setImageResource(R.mipmap.gridview_addpic);
            } else {
                Glide.with(context)
                        .load(path)
                        .placeholder(R.mipmap.gridview_addpic)
                        .error(R.mipmap.gridview_addpic)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }


    //发表dialog,两秒后自动关闭
    final Handler handler = new Handler() {          // handle
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(successDialog.isShowing()){
                        successDialog.dismiss();
                    }
//                    recLen++;
                    break;
            }
            super.handleMessage(msg);
        }
    };


    private void showDialog() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_fabiaochenggong, null);
        successDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
        successDialog.getWindow().setGravity(Gravity.CENTER);
        successDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        successDialog.show();
        new Thread(new MyThread()).start();
    }
    //发表dialog,两秒后自动关闭
    public class MyThread implements  Runnable{
        @Override
        public void run(){
                try{
                    Thread.sleep(2000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }catch (Exception e){

                }
        }
    }


}
