package com.vlvxing.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.handongkeji.utils.Bimp;
import com.handongkeji.utils.CommonUtils;
import com.lzy.imagepicker.bean.ImageItem;
import com.vlvxing.app.R;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.ui.RemarkOrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/10 0010.
 */


/**
 * 用于上传图片显示的适配器
 */
public class UploadImgAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int selectedPosition = -1;
    Context c;
    ArrayList<ImageItem> images;

    public UploadImgAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.images = new ArrayList<>();
        images.add(new ImageItem());
        c = context;
    }


    private boolean isReachtoMaxSize = false;


    //还可以选择的照片数量
    public int getCountCanSelect() {
        if (isReachtoMaxSize) {
            return 0;
        } else {
            return 9 - getCount() + 1;
        }
    }

    /**
     * 添加照片
     *
     * @param list
     */
    public void addImages(List<ImageItem> list) {
        if (images.size() <= 9) {

            if (!isReachtoMaxSize) {//未达到上限

                //去掉加号
                images.remove(images.size() - 1);
                images.addAll(list);
                if (images.size() < 9) {
                    images.add(new ImageItem());
                } else {
                    isReachtoMaxSize = true;
                }
                notifyDataSetChanged();
            }
        }
    }

    public int getCount() {
        return images.size();
//            return images.size() > 0 ? images.size() : 0;
    }

    public Object getItem(int arg0) {
        return images.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.uploadimg_item,
                    parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //按屏幕的比例计算图片的大小
        int width = (MyApp.getScreenWidth() - CommonUtils.dip2px(c, 38)) / 3;
        ViewGroup.LayoutParams params = holder.img_rel.getLayoutParams();
        params.width = width;
        params.height = width;
        holder.img_rel.setLayoutParams(params);


        ImageItem imageItem = images.get(position);
        String imagePath = imageItem.path;
        if (position != images.size() - 1) {
            Glide.with(c).load(imagePath).into(holder.image);
            holder.delete_img.setVisibility(View.VISIBLE);
        } else {
            if (images.size() < 9) {
                holder.image.setImageResource(R.drawable.add);
                holder.delete_img.setVisibility(View.GONE);
            } else {
                Glide.with(c).load(imagePath).into(holder.image);
                holder.delete_img.setVisibility(View.VISIBLE);
            }
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
