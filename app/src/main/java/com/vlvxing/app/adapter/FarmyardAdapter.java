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
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.ui.FarmyardActivity;
import com.vlvxing.app.ui.LineDetailsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/7 0007.
 * 农家院
 */


   public class FarmyardAdapter extends BaseAdapter {
        Context context;
        List<HotRecomModel.DataBean> list;

        public FarmyardAdapter(List<HotRecomModel.DataBean> list, Context context) {
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
                        R.layout.farmyard_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HotRecomModel.DataBean bean = list.get(position);
            holder.contentTxt.setText(bean.getContext());
            double price=bean.getPrice();
            int p=(int)price;
            holder.priceTxt.setText(p+"");
            holder.distanceTxt.setText(bean.getDistance()+"km");
            // 获取图片的宽度
            int img_width = MyApp.getInstance().getScreenWidth();
            ViewGroup.LayoutParams params = holder.bgImg.getLayoutParams();
            params.height = (img_width * 178) / 374; // 750:500
            holder.bgImg.setLayoutParams(params);
            String bgUrl = bean.getAdvertisebigpic();
            if (!StringUtils.isStringNull(bgUrl)){
                bgUrl=bgUrl.replace("_mid","_big");
            }
                Glide.with(context).load(bgUrl).error(R.mipmap.myinfo_bg).into(holder.bgImg);

            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id", bean.getTravelproductid()).putExtra("type",1));
                }
            });
            return convertView;
        }

        class ViewHolder{
            @Bind(R.id.bg_img)
            ImageView bgImg;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.distance_txt)
            TextView distanceTxt;
            @Bind(R.id.price_txt)
            TextView priceTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

}
