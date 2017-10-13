package com.vlvxing.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.ui.LineDetailsActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Zophar
 * 根据图片个数，动态排列gridview布局
 */


    public class ImageGridViewAdapter extends BaseAdapter {
    Context context;
    LinkedList<Integer> imgList;
    int  mImageWidth1;
    int  mImageWidth2;
    int  mImageWidth3;

    public  ImageGridViewAdapter(Context context , LinkedList<Integer> imgList){
        this.context = context;
        this.imgList = imgList;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics outMetrics =  new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(outMetrics);

        mImageWidth3 = (int) ((outMetrics.widthPixels -12* outMetrics.density)/3);

        mImageWidth2 = (int) ((outMetrics.widthPixels -12* outMetrics.density)/2);

        mImageWidth1 = (int) ((outMetrics.widthPixels -12* outMetrics.density)/1);

// layoutParams = new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

// layoutParams.setMargins(1,1,1,1);//4个参数按顺序分别是左上右下

    }
    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null){
            viewHodler   = new ViewHodler();
            convertView = View.inflate(context,R.layout.image_gridview_item,null);
            viewHodler.imageView = (ImageView) convertView.findViewById(R.id.imgview);
            //根据图片的数量设置图片的大小，这里的大小是写的具体数值，也可以获得手机屏幕的尺寸来设置图片的大小
            if (imgList.size()==1){
                ViewGroup.LayoutParams params = viewHodler.imageView.getLayoutParams();
                params.height = mImageWidth1;
                params.width = mImageWidth1;
                viewHodler.imageView.setLayoutParams(params);
            }else if (imgList.size()==2||imgList.size()==4){
                ViewGroup.LayoutParams params = viewHodler.imageView.getLayoutParams();
                params.height = mImageWidth2;
                params.width = mImageWidth2;
                viewHodler.imageView.setLayoutParams(params);
            }else {
                ViewGroup.LayoutParams params = viewHodler.imageView.getLayoutParams();
                params.height = mImageWidth3;
                params.width = mImageWidth3;
                viewHodler.imageView.setLayoutParams(params);
            }
            convertView.setTag(viewHodler);
        }else{
            viewHodler =(ViewHodler) convertView.getTag();
        }

        viewHodler.imageView.setImageResource(imgList.get(position));
        return  convertView;
    }

    class  ViewHodler{
        ImageView imageView;
    }
}
