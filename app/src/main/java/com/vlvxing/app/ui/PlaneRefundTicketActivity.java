package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.handongkeji.widget.NoScrollListView;
import com.qunar.bean.FlyPassenger;
import com.qunar.model.FlyOrder;
import com.vlvxing.app.R;

import com.vlvxing.app.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退票
 */

public class PlaneRefundTicketActivity extends BaseActivity{

    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键

    @Bind(R.id.date_txt)
    TextView date_txt;

    @Bind(R.id.month_txt)
    TextView month_txt;
    @Bind(R.id.hangbanhao)
    TextView hangbanhao;
    @Bind(R.id.left_city)
    TextView left_city;
    @Bind(R.id.left_time)
    TextView left_time;

    @Bind(R.id.right_city)
    TextView right_city;

    @Bind(R.id.right_time)
    TextView right_time;
    @Bind(R.id.stauts)
    TextView stauts;//状态

    @Bind(R.id.submit_btn)
    Button submit_btn;//确认退票
    @Bind(R.id.orderNo)
    TextView orderNo;

    @Bind(R.id.user_list)
    NoScrollListView user_list;//乘客信息列表

    private FlyOrder orderInfo = new FlyOrder();

    private Context mcontext;

    private List<FlyPassenger> passengersList = new ArrayList<>();//乘客信息表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_refund_ticket);
        ButterKnife.bind(this);
        mcontext = this;
        orderInfo = (FlyOrder)getIntent().getSerializableExtra("orderInfo");
        headTitle.setText("退票");
        if(orderInfo==null){
            ToastUtils.show(mcontext,"该订单不存在");
            return;
        }
        orderNo.setText(orderInfo.getOrderno());
        passengersList = orderInfo.getPassengers();
        MyAdapter adapter = new MyAdapter(mcontext);
        user_list.setAdapter(adapter);

    }


    @OnClick({R.id.return_lin,R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.submit_btn:
                //退票
                Intent intent = new Intent(mcontext,PlaneRefundSuccessActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }





    public final class ViewHolder {
        public TextView name;
        public TextView price;
    }




    //机票列表的数据源适配器
    class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater mInflater;
        public MyAdapter(Context context) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return passengersList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return passengersList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.act_plane_refund_item, null);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            FlyPassenger info = passengersList.get(position);
            holder.name.setText(info.getName());
            System.out.println("退票 nopayamount"+orderInfo.getNopayamount());
            System.out.println("退票 passengersList"+passengersList.size());
            holder.price.setText((orderInfo.getNopayamount())+"元");
            return convertView;
        }
    }

}
