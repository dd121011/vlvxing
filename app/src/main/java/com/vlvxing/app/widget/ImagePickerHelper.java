package com.vlvxing.app.widget;

import android.app.Activity;
import android.content.Intent;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class ImagePickerHelper {

    /**
     * 选取单张图片
     * 默认显示相机
     */
    public static void selectSinglePhoto(Activity activity, int requestCode, boolean isCrop) {
        selectSinglePhoto(activity, requestCode, isCrop, true);
    }

    /**
     * 选取单张图片
     */
    public static void selectSinglePhoto(Activity activity, int requestCode, boolean isCrop, boolean isShowCamera) {
        initImagePickerConfig(false, 1, isCrop, isShowCamera);
        startImageActivityForResult(activity, requestCode);
    }

    /**
     * 选取多张张图片  (不支持裁剪)
     * 默认显示相机
     */
    public static void selectMultiPhoto(Activity activity, int requestCode, int num) {
        selectMultiPhoto(activity, requestCode, num, true);
    }

    /**
     * 选取多张张图片  (不支持裁剪)
     */
    public static void selectMultiPhoto(Activity activity, int requestCode, int num, boolean isShowCamera) {
        initImagePickerConfig(true, num, false, isShowCamera);
        startImageActivityForResult(activity, requestCode);
    }

    /**
     * 跳转到选择图片界面
     */
    private static void startImageActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
    /**
     * 配置 信息
     */
    private static void initImagePickerConfig(boolean isMultiPhoto, int num, boolean isCrop, boolean isShowCamera) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PickerLoader());   //设置图片加载器
        imagePicker.setShowCamera(isShowCamera);  //显示拍照按钮
        imagePicker.setMultiMode(isMultiPhoto);
        imagePicker.setCrop(isCrop);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setSelectLimit(num);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}
