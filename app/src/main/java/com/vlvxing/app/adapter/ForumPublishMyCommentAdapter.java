package com.vlvxing.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vlvxing.app.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 我的评论
 */
public class ForumPublishMyCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String,String>> mData;

    public ForumPublishMyCommentAdapter(Context context, ArrayList<HashMap<String,String>> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.act_my_comment_item, parent, false));
    }


    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.name.setText(mData.get(position).get("name"));
        viewHolder.hour.setText(mData.get(position).get("hour"));


    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    public void changeBtnView(int position) {
        mData.get(position).put("name","点击过后的昵称");
        mData.get(position).put("hour","刚刚");
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView headerImg;//用户头像
        public TextView name;//昵称
        public TextView hour;//时间
        public TextView bodyTxt;//评论详情
        public TextView btnTxt;//回复
        public TextView time;//时间
        public TextView date;//时间
        public TextView huati;//话题
        public LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            headerImg = (ImageView) itemView.findViewById(R.id.header_img);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            bodyTxt = (TextView) itemView.findViewById(R.id.body_txt);
            btnTxt = (TextView) itemView.findViewById(R.id.btn_txt);
            date = (TextView) itemView.findViewById(R.id.date);
            hour = (TextView) itemView.findViewById(R.id.hour);
            huati = (TextView) itemView.findViewById(R.id.huati);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
