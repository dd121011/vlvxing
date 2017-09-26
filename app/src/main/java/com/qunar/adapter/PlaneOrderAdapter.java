package com.qunar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vlvxing.app.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaneOrderAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String,String>> mData;

    public PlaneOrderAdapter(Context context,ArrayList<HashMap<String,String>> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.act_my_plane_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.state.setText(mData.get(position).get("state"));
        viewHolder.cityLeft.setText(mData.get(position).get("cityLeft"));
        viewHolder.cityRight.setText(mData.get(position).get("cityRight"));
        viewHolder.price.setText(mData.get(position).get("price"));
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
        public TextView state;//订单状态
        public TextView cityLeft;//出发城市
        public TextView cityRight;//到达城市
        public TextView price;//订单价格
        public TextView delete;
        public LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.state);
            cityLeft = (TextView) itemView.findViewById(R.id.city_left);
            cityRight = (TextView) itemView.findViewById(R.id.city_right);
            price = (TextView) itemView.findViewById(R.id.price);
            delete = (TextView) itemView.findViewById(R.id.item_delete);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
