package com.vlvxing.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.handongkeji.ui.ViewPagerActivity;
import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.handongkeji.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vlvxing.app.R;
import com.vlvxing.app.common.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 动态列表图片展示adapter
 */
public class DTPicAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> array;
    // 用于从服务器端获取图片
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    int width;
    Intent intent;

    public DTPicAdapter(ArrayList<String> array, Context context) {
        this.array = array;
        this.context = context;
        // 无这行代码就不会显示默认图片
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.photo_pingjia)
                .showImageOnFail(R.mipmap.photo_pingjia).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();
    }

    @Override
    public int getCount() {
        return array == null ? 0 : array.size();
//		return 6;
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        PicHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.dt_grid_item, parent, false);
            ViewUtils.inject(this, convertView); // 注解
            viewHolder = new PicHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PicHolder) convertView.getTag();
        }
        String imgUrl = array.get(position);
        width = (MyApp.getScreenWidth() - CommonUtils.dip2px(context, 48)) / 4;
        LayoutParams params = viewHolder.show_img.getLayoutParams();
        params.width = width;
        params.height = width;
        viewHolder.show_img.setLayoutParams(params);
        if (!imgUrl.equals("null")) {
            ImageLoader.getInstance().displayImage(imgUrl,
                    viewHolder.show_img, options, animateFirstListener);
        }

        viewHolder.show_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ViewPagerActivity.class);
                // intent.putExtra("imageUrl", url);
                intent.putStringArrayListExtra("viewpagerlist", array);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}

class PicHolder {
    @ViewInject(R.id.show_img)
    ImageView show_img;
    View v;

    public PicHolder(View v) {
        super();
        this.v = v;
        ViewUtils.inject(this, v);
    }
}