package com.vlvxing.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.ui.LineDetailsActivity;
import com.vlvxing.app.ui.ScenicCarActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/7 0007.
 * 景点用车
 */


    public   class ByCarAdapter extends BaseAdapter {
        Context context;
        List<HotRecomModel.DataBean> list;

        public ByCarAdapter(List<HotRecomModel.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
        }
        @Override
        public int getCount() {
            return list.size() > 0 ? list.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.bycar_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HotRecomModel.DataBean bean = list.get(position);
            holder.contentTxt.setText(bean.getContext());
            String bgUrl = bean.getProductsmallpic();
            if (!StringUtils.isStringNull(bgUrl)){
                bgUrl=bgUrl.replace("_mid","_big");
            }
                Glide.with(context).load(bgUrl).error(R.mipmap.qiche).into(holder.carImg);

            double price=bean.getPrice();
            int p=(int)price;
            holder.priceTxt.setText(p+"/车");
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id", bean.getTravelproductid()));
                }
            });
            return convertView;
        }

        class ViewHolder{
            @Bind(R.id.car_img)
            ImageView carImg;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.price_txt)
            TextView priceTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

}
