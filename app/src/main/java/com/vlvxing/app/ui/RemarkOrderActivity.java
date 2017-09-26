package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.AlbumActivity1;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.handongkeji.utils.Bimp;
import com.handongkeji.utils.BitmapUtils;
import com.handongkeji.utils.CommonUtils;

import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyGridView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.utils.ImageUtil;
import com.vlvxing.app.utils.ToastUtils;
import com.vlvxing.app.widget.ImagePickerHelper;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
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
 * Created by Administrator on 2017/6/7 0007.
 * 评价
 */

public class RemarkOrderActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.good_lin)
    LinearLayout goodLin;
    @Bind(R.id.center_lin)
    LinearLayout centerLin;
    @Bind(R.id.bad_lin)
    LinearLayout badLin;
    @Bind(R.id.content_txt)
    EditText contentTxt;
    @Bind(R.id.img_grid)
    MyGridView img_grid;
    private UploadImgGridAdapter adapter;
    public static List<String> urls = new ArrayList<>(); // 用于存放上传图片
    public static final int IMG_RESULT = 1; // 选择多张图片
    private static final int PHOTO_REQUEST_GALLERY2 = 2;
    private Bitmap bitmap;
    private Bitmap tmpbitmap;
    private int count, type = 1;
    private LinearLayout[] lin;
    private String id, orderid;

    private static final int IMAGE_PICKER = 0;// 标签
    ArrayList<ImageItem> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remarkorder_layout);
        ButterKnife.bind(this);
        headTitle.setText("评价");
        goodLin.setSelected(true);

        //设置第一次进入界面不弹出软键盘
        headTitle.setFocusable(true);
        headTitle.setFocusableInTouchMode(true);
        headTitle.requestFocus();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        orderid = intent.getStringExtra("orderid");
        lin = new LinearLayout[]{goodLin, centerLin, badLin};

        adapter = new UploadImgGridAdapter(this);
        img_grid.setAdapter(adapter);
//        picsBind();
        // 点击选择要上传的图片
        img_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    ImagePickerHelper.selectMultiPhoto(RemarkOrderActivity.this, IMG_RESULT, adapter.getCountCanSelect(), true);
                }
            }
        });
    }

    // 选多张图片用到
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMG_RESULT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.i("aaa","images:"+images.size());
                for (int i = 0; i < images.size(); i++) {
                    ImageItem imageItem = images.get(i);
                    String path = imageItem.path;
                    if (path != null) {
                        File file1 = new File(path);
                        path = file1.getAbsolutePath();
                        com.handongkeji.utils.ImageItem item = new com.handongkeji.utils.ImageItem();
                        item.setBitmap(tmpbitmap);
                        item.setImagePath(path);
                        Bimp.tempSelectBitmap.add(item);
                    }
                }
                Log.i("aaa","Bimp:"+Bimp.tempSelectBitmap.size());
                adapter.notifyDataSetChanged();
//                adapter.addImages(images);

            } else {
                Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage(final File file) {
        String upload_img = Constants.URL_UPLOAD;
        HashMap<String, String> params_upload = new HashMap<String, String>();
        params_upload.put("filemark", 3 + ""); // 存放文件位置(用户图片)
        HashMap<String, File> fileMap = new HashMap<String, File>();
        fileMap.put("file", file); // 上传图片的文件
        RemoteDataHandler.asyncMultipartPost(upload_img, params_upload,
                fileMap, new RemoteDataHandler.Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        file.delete();
                        String json = data.getJson();
                        if (null == json || "".equals(json)) {
                            return;
                        }
                        try {
                            JSONObject jso = new JSONObject(json);
                            int status = jso.getInt("status");
                            if (status == 1) {
                                String string = jso.getString("data");
                                urls.add(string);
                                count++;
                            }
                            if (count >= Bimp.tempSelectBitmap.size()) {
                                publish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 上传图片完成后才能发布评论
     */
    private void uploadPic() {
        // 获取选择上传的图片
        int size = Bimp.tempSelectBitmap.size();
        // urls = new ArrayList<String>();
        // 判断是否上传图片
        count = 0;
        for (int i = 0; i < size; i++) {
            com.handongkeji.utils.ImageItem imageItem = Bimp.tempSelectBitmap.get(i);
            String imagePath = imageItem.getImagePath();
            float orientation = imageItem.getOrientation();
            if (!TextUtils.isEmpty(imagePath) && !"null".equals(imagePath)) {  //对上传的图片进行压缩
                File originalFile = new File(imagePath);
                // 原始图片的大小
                long originalLength = originalFile.length();
                String path = Constants.CACHE_DIR_UPLOADING_IMG + "/" + System.currentTimeMillis() + i + ".jpg";
                File resultFile = compressBitmap(imagePath, path, orientation);
                // 压缩后图片的大小
                long resultLength = resultFile.length();
                // 调用上传图片的方法
                uploadImage(resultFile);
            }
        }
    }

    /**
     * 对上传的图片进行压缩
     *
     * @param originalPath 原始图片的路径
     * @param resultPath   压缩后图片的路径
     * @return
     */
    private File compressBitmap(String originalPath, String resultPath, float orientation) {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(originalPath, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int w = outWidth / 480;
        int h = outHeight / 800;

        int sampleSize = Math.max(w, h);

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap decodeFile = BitmapFactory.decodeFile(originalPath, options);
        if (orientation > 0) {
            Matrix m = new Matrix();
            m.postRotate(orientation);
            decodeFile = Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), m, true);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int quality = 80;
        do {
            bos.reset();
            quality -= 10;
            decodeFile.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        } while (bos.size() > 30 * 1024 && quality > 20);


        File file = new File(resultPath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] byteArray = bos.toByteArray();
            fos.write(byteArray, 0, byteArray.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long length = file.length();
        return file;
    }

    /**
     * 发布动态的方法
     */
    private void publish() {
        String content = contentTxt.getText().toString().trim();
        if (StringUtils.isStringNull(content)) {
            ToastUtils.show(this, "评价内容不能为空!");
            return;
        }
        showDialog("提交中...");
        String url = Constants.URL_SAVEREMARK;
        HashMap<String, String> params = new HashMap<String, String>();
        if (urls.size() > 0) {
            //获取图片
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < urls.size(); i++) {
                sb.append(urls.get(i));
                if (i == urls.size() - 1) {
                    continue;
                }
                sb.append(",");
            }
            String s = sb.toString().trim();
            params.put("evaluatePic", s); //上传的图片
        }
        params.put("level", type + "");
        params.put("content", content);
        params.put("token", myApp.getUserTicket());
        params.put("productId", id);
        params.put("orderId", orderid);
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                String jsonString = data.getJson();
                if (jsonString != null && !"".equals(jsonString)) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if (status.equals("1")) {
                            Toast.makeText(RemarkOrderActivity.this, "评价成功!",
                                    Toast.LENGTH_SHORT).show();
                            OrderDetailActivity.mContext.finish();
                            finish();
                            dismissDialog();
                        } else {
                            Toast.makeText(RemarkOrderActivity.this, message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.tempSelectBitmap.clear();// 清空缓存图片
    }

//    /**
//     * 显示上传图片的GridView
//     */
//    public void picsBind() {
//        adapter = new UploadImgGridAdapter(this);
//        img_grid.setAdapter(adapter);
//    }

    /**
     * 用于上传图片显示的适配器
     */
    class UploadImgGridAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private int selectedPosition = -1;
        Context mContext;
        DisplayImageOptions options;

        public UploadImgGridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            mContext = context;
            // 无这行代码就不会显示默认图片
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.add)
                    .showImageOnFail(R.drawable.add).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565).build();
        }

       //还可以选择的照片数量
        public int getCountCanSelect() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 0;
            } else {
                return 9 - getCount() + 1;
            }
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return arg0;
        }

        public long getItemId(int arg0) {
            return arg0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.uploadimg_item,
                        parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int width = (MyApp.getScreenWidth() - CommonUtils.dip2px(mContext, 38)) / 3;
            ViewGroup.LayoutParams params = holder.img_rel.getLayoutParams();
            params.width = width;
            params.height = width;


            if (position == Bimp.tempSelectBitmap.size()) {
                ImageLoader.getInstance().displayImage("", holder.image,
                        options, new AnimateFirstDisplayListener());
                holder.delete_img.setVisibility(View.GONE);
            } else {
                com.handongkeji.utils.ImageItem imageItem = Bimp.tempSelectBitmap.get(position);
                String imagePath = imageItem.imagePath;
                String thumbnailPath = imageItem.thumbnailPath;
                imagePath = TextUtils.isEmpty(imagePath) ? thumbnailPath
                        : imagePath;
                Uri uri = Uri.fromFile(new File(imagePath));
//                Glide.with(RemarkOrderActivity.this).load(imagePath).into(holder.image);
                ImageLoader.getInstance().displayImage(uri + "", holder.image,
                        options, new AnimateFirstDisplayListener());
                holder.delete_img.setVisibility(View.VISIBLE);
            }
            final int p = position;
            holder.delete_img.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bimp.tempSelectBitmap.remove(p);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_grida_image)
            ImageView image;
            @Bind(R.id.delete)
            ImageView delete_img;
            @Bind(R.id.img_rel)
            RelativeLayout img_rel;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }



//    /**
//     * 用于上传图片显示的适配器
//     */
//    class UploadImgGridAdapter extends BaseAdapter {
//
//        private LayoutInflater inflater;
//        private int selectedPosition = -1;
//        Context c;
//        ArrayList<ImageItem> images;
//
//        public UploadImgGridAdapter(Context context) {
//            inflater = LayoutInflater.from(context);
//            this.images = new ArrayList<>();
//            images.add(new ImageItem());
//            c = context;
//        }
//
//
//        private boolean isReachtoMaxSize = false;
//
//
//        //还可以选择的照片数量
//        public int getCountCanSelect() {
//            if (isReachtoMaxSize) {
//                return 0;
//            } else {
//                return 9 - getCount() + 1;
//            }
//        }
//
//        /**
//         * 添加照片
//         *
//         * @param list
//         */
//        public void addImages(List<ImageItem> list) {
//            if (images.size() <= 9) {
//
//                if (!isReachtoMaxSize) {//未达到上限
//
//                    //去掉加号
//                    images.remove(images.size() - 1);
//                    images.addAll(list);
//                    if (images.size() < 9) {
//                        images.add(new ImageItem());
//                    } else {
//                        isReachtoMaxSize = true;
//                    }
//                    notifyDataSetChanged();
//                }
//            }
//        }
//
//        public int getCount() {
////            return images.size();
//            return images.size() > 0 ? images.size() : 0;
//        }
//
//        public Object getItem(int arg0) {
//            return images.get(arg0);
//        }
//
//        public long getItemId(int arg0) {
//            return arg0;
//        }
//
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.uploadimg_item,
//                        parent, false);
//                holder = new ViewHolder(convertView);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            //按屏幕的比例计算图片的大小
//            int width = (MyApp.getScreenWidth() - CommonUtils.dip2px(c, 38)) / 3;
//            ViewGroup.LayoutParams params = holder.img_rel.getLayoutParams();
//            params.width = width;
//            params.height = width;
//            holder.img_rel.setLayoutParams(params);
//
//
//            ImageItem imageItem = images.get(position);
//            String imagePath = imageItem.path;
//            if (position != images.size() - 1) {
//                Glide.with(RemarkOrderActivity.this).load(imagePath).into(holder.image);
//                holder.delete_img.setVisibility(View.VISIBLE);
//            } else {
//                if (images.size() < 9) {
//                    holder.image.setImageResource(R.drawable.add);
//                    holder.delete_img.setVisibility(View.GONE);
//                } else {
//                    Glide.with(RemarkOrderActivity.this).load(imagePath).into(holder.image);
//                    holder.delete_img.setVisibility(View.VISIBLE);
//                }
//            }
//            final int p = position;
//            holder.delete_img.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Bimp.tempSelectBitmap.remove(p);
//                    notifyDataSetChanged();
//                }
//            });
//            return convertView;
//        }
//
//        class ViewHolder {
//            @Bind(R.id.item_grida_image)
//            ImageView image;
//            @Bind(R.id.delete)
//            ImageView delete_img;
//            @Bind(R.id.img_rel)
//            RelativeLayout img_rel;
//
//            ViewHolder(View view) {
//                ButterKnife.bind(this, view);
//            }
//        }
//    }


    @OnClick({R.id.return_lin, R.id.good_lin, R.id.center_lin, R.id.bad_lin, R.id.submit_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.good_lin: //的等级   1好评，2中评，3差评
                remarkType(goodLin, 1);
                break;
            case R.id.center_lin:
                remarkType(centerLin, 2);
                break;
            case R.id.bad_lin:
                remarkType(badLin, 3);
                break;
            case R.id.submit_txt: //提交 判断是否上传了图片
//                if (Bimp.tempSelectBitmap.size() == 0) {
//                     ToastUtils.show(this, "请选择图片!");
//                    return;
//                }
                if (Bimp.tempSelectBitmap.size() > 0) {
                    uploadPic();
                } else {
                    publish();
                }
                break;
        }
    }

    private void remarkType(LinearLayout linear, int t) {
        for (int i = 0; i < lin.length; i++) {
            if (linear != lin[i]) {
                lin[i].setSelected(false);
            }
        }
        linear.setSelected(true);
        type = t;
    }
}
