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

public class MyRemindAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String,String>> mData;

    public MyRemindAdapter(Context context, ArrayList<HashMap<String,String>> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.act_my_reminder_item, parent, false));
    }


    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.name.setText(mData.get(position).get("name"));
        viewHolder.date.setText(mData.get(position).get("date"));

    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView headerImg;//用户头像
        public TextView name;//昵称
        public TextView date;//时间
        public LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            headerImg = (ImageView) itemView.findViewById(R.id.header_img);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            layout = (LinearLayout)itemView.findViewById(R.id.item_layout);
        }
    }
}
