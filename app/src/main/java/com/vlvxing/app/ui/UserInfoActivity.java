package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.common.ValidateHelper;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.handongkeji.utils.BitmapUtils;
import com.handongkeji.utils.CommonUtils;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.RoundImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.UserInfo;
import com.vlvxing.app.utils.ImageUtil;
import com.vlvxing.app.utils.ToastUtils;
import com.vlvxing.app.widget.ImagePickerHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/19 0019.
 * 个人信息
 */

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.right_txt)
    TextView rightTxt;
    @Bind(R.id.head_img)
    ImageView headImg;
    @Bind(R.id.name_txt)
    EditText nameTxt;
    @Bind(R.id.sex_txt)
    TextView sexTxt;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.address_edt)
    EditText addressEdt;
    @Bind(R.id.num_edt)
    EditText numEdt;
    private static final int PHOTO_REQUEST_CUT = 0;//图片头像
    private static final int INFO_USER_GENDER = 5;//性别
    private Intent intent;
//    private Bitmap bitmap;
    private String mLecenseUrl;
    private int isselect_img=0,isselect_name=0,isselect_sex=0; //判断是否修改用户信息
    private String path; //选择图片的本地路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_userinfo);
        ButterKnife.bind(this);
        headTitle.setText("个人信息");
        rightTxt.setVisibility(View.VISIBLE);
        rightTxt.setText("保存");

//        int width = CommonUtils.dip2px(this,64);
//        ViewGroup.LayoutParams params = headImg.getLayoutParams();
//        params.width = width;
//        params.height = width;
//        headImg.setLayoutParams(params);
        //设置第一次进入界面不弹出软键盘
        headTitle.setFocusable(true);
        headTitle.setFocusableInTouchMode(true);
        headTitle.requestFocus();
        rightTxt.setTextColor(getResources().getColor(R.color.color_ea5413));
        getUserInfo();
    }

    private void getUserInfo() {
        showDialog("加载中...");
        String url= Constants.URL_GETUSERINFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json=data.getJson();
                if (StringUtils.isStringNull(json)){
                    dismissDialog();
                    return;
                }
                JSONObject object=new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)){
                    dismissDialog();
                    JSONObject obj = object.getJSONObject("data");
                    String userpic = obj.getString("userpic");
                    Glide.with(UserInfoActivity.this).load(userpic).error(R.mipmap.touxiang_moren).into(headImg);
                    String usernick = obj.getString("usernick");
                    nameTxt.setText(usernick);
                    String sex="方便对您的称呼";
                    String usersex = obj.getString("usersex"); //1男，2女
                    if ("1".equals(usersex)){
                        sex="男";
                    }else if ("2".equals(usersex)){
                        sex="女";
                    }
                    sexTxt.setText(sex);
                    String username = obj.getString("username");
                    if (!StringUtils.isStringNull(username)){
                        nameEdt.setText(username);
                    }
                    String usercontactaddr = obj.getString("usercontactaddr");
                    if (!StringUtils.isStringNull(usercontactaddr)){
                        addressEdt.setText(usercontactaddr);
                    }
                    String usernumbe = obj.getString("usernumber");
                    if (!StringUtils.isStringNull(usernumbe)){
                        numEdt.setText(usernumbe);
                    }
                    String usermobile = obj.getString("usermobile");
                    if (!StringUtils.isStringNull(usermobile)){
                        phoneEdt.setText(usermobile);
                    }
                }else {
                    ToastUtils.show(UserInfoActivity.this,message);
                }
                dismissDialog();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (data.getExtras() == null) {
            return;
        }
//        if (requestCode == PHOTO_REQUEST_CUT) {//获取图片
//            Uri uri = data.getData();
//            if (data.getExtras().get("data") != null) {
//                bitmap = (Bitmap) data.getExtras().get("data");
//            }
//            if (null == bitmap) {
//                return;  //content://media/external/images/media/34336
//            }
//            isselect_img=1;
////            bitmap = BitmapUtils.compressImage(bitmap);
//            headImg.setImageBitmap(bitmap);
//
////            uploadHeadImg();
//        }
        if (requestCode == INFO_USER_GENDER) {
            String man = data.getStringExtra("man");
            String woman = data.getStringExtra("woman");
            String sex = null;
            if (man != null) {
                sex = "男";
                isselect_name=1;
            }
            if (woman != null) {
                isselect_sex=1;
                sex = "女";
            }
            sexTxt.setText(sex);
        }
        if (requestCode == 4) {
            String content = data.getStringExtra("content");
            if (!StringUtils.isStringNull(content)) {
                isselect_name=1;
                nameTxt.setText(content);
            }
        }

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == PHOTO_REQUEST_CUT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem imageItem = images.get(0);
                 path = imageItem.path;
                Glide.with(UserInfoActivity.this).load(path).into(headImg);
            }
        }
    }

    @OnClick({R.id.return_lin, R.id.right_txt, R.id.headimg_rel, R.id.name_rel, R.id.sex_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
                clickSave();
                break;
            case R.id.headimg_rel:
//                 intent = new Intent(this, UserHeadimgSetDialogActivity2.class).putExtra("t",1); //上传头像要裁剪
//                startActivityForResult(intent, PHOTO_REQUEST_CUT);
                ImagePickerHelper.selectSinglePhoto(this,PHOTO_REQUEST_CUT,true);
                break;
//            case R.id.name_rel:
//                String content = nameTxt.getText().toString().trim();
//                if (StringUtils.isStringNull(content) || "未设置".equals(content)) {
//                    content = "";
//                }
//                intent = new Intent(this, UpdateActivity.class).putExtra("type", 4).putExtra("content", content);
//                startActivityForResult(intent, 4);
//                break;
            case R.id.sex_rel:
                String trim = sexTxt.getText().toString().trim();
                intent = new Intent(this, GenderSexDialogActivity.class).putExtra("gender", trim);
                startActivityForResult(intent, INFO_USER_GENDER);
                break;
        }
    }

    private void clickSave() {
//        if (isselect_name==0&&isselect_sex==0&&isselect_img==0){
//            ToastUtils.show(this,"你未编辑修改任何数据!");
//            return;
//        }
        //判断是否上传头像
        if (!StringUtils.isStringNull(path)) {
            uploadImg();
        }else {
            saveUserInfo();
        }
    }

    private void saveUserInfo() {
        showDialog("提交中...");
        String url=Constants.URL_SAVEUSERINFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        if (!StringUtils.isStringNull(mLecenseUrl)){
            params.put("userPic",mLecenseUrl);
        }
        String name=nameTxt.getText().toString().trim();
        if (!StringUtils.isStringNull(name)) {
            params.put("userNick", name);
        }
        String uname = nameEdt.getText().toString().trim();
        if (!StringUtils.isStringNull(uname)){
            params.put("username",uname);
        }
        String phone = phoneEdt.getText().toString().trim();
        if (!StringUtils.isStringNull(phone)){
            if (!ValidateHelper.isPhoneNumberValid(phone)) {
                Toast.makeText(this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
                return;
            }
            params.put("usermobile",phone);
        }
        String address = addressEdt.getText().toString().trim();
        if (!StringUtils.isStringNull(address)){
            params.put("usercontactaddr",address);
        }
        String num = numEdt.getText().toString().trim();
        if (!StringUtils.isStringNull(num)){
            if (!ValidateHelper.isIDCard(num)) {
                Toast.makeText(this, "请输入正确的身份证号!", Toast.LENGTH_SHORT).show();
                return;
            }
            params.put("usernumber",num); //用户身份证
        }
        String sex=sexTxt.getText().toString().trim();  //1男2女
        if ("男".equals(sex)){
            sex="1";
        }else if ("女".equals(sex)){
            sex="2";
        }
        if ("1".equals(sex)||"2".equals(sex)) {
            params.put("userSex", sex);
        }

        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json=data.getJson();
                if (StringUtils.isStringNull(json)){
                    dismissDialog();
                    return;
                }
                JSONObject object=new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)){
                    dismissDialog();
                    ToastUtils.show(UserInfoActivity.this,"修改成功!");
                }else {
                    dismissDialog();
                    ToastUtils.show(UserInfoActivity.this,message);
                }
            }
        });
    }

    private void uploadImg() {
        showDialog("上传图片中...");
            String url = Constants.URL_UPLOAD;//上传图片接口
            HashMap<String, File> fileMap = new HashMap<>();
//            String filePath = Constants.CACHE_DIR_UPLOADING_IMG + "/lecenseimg.jpg";//待上传图片缓存目录
            final File file = new File(path);//保存图片路径
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            try {
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                bos.flush();
//                bos.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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
//                                headImg.setImageBitmap(bitmap);
                                mLecenseUrl = obj.getString("data").replace("\\", "/");
                                dismissDialog();
                                saveUserInfo();
                                Log.d("上传图片成功", mLecenseUrl);
//                                    SaveData(mLecenseUrl, "");//url
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
}
