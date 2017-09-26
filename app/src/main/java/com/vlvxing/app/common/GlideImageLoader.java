package com.vlvxing.app.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vlvxing.app.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class GlideImageLoader extends ImageLoader {
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(path)
                .crossFade().error(R.mipmap.myinfo_bg)
                .into(imageView);
        //Glide 加载图片简单用法
//        options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .showImageForEmptyUri(R.mipmap.myinfo_bg)
//                .showImageOnFail(R.mipmap.myinfo_bg)
//                .cacheOnDisk(true).considerExifParams(true)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .bitmapConfig(Bitmap.Config.RGB_565).build();
//        com.nostra13.universalimageloader.core.ImageLoader
//                .getInstance().displayImage((String) path,
//                imageView, options,
//                animateFirstListener);
//        Glide.with(context).load((String) path).into(imageView);
    }


}
