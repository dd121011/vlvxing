package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.NoScrollListView;
import com.qunar.bean.FlyPassenger;
import com.qunar.bean.Passenger;
import com.qunar.model.FlyOrder;
import com.vlvxing.app.R;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 已完成订单详情
 */

public class PlaneCompletedOrderActivity extends BaseActivity{
    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.date_txt)
    TextView date_txt;//日期
    @Bind(R.id.month_txt)
    TextView month_txt;//周几
    @Bind(R.id.deptCity_txt)
    TextView deptCity_txt;//出发城市
    @Bind(R.id.arriCity_txt)
    TextView arriCity_txt;//到达城市
    @Bind(R.id.dep_airport)
    TextView dep_airport;//出发机场
    @Bind(R.id.arr_airport)
    TextView arr_airport;//到达机场
    @Bind(R.id.flight_times)
    TextView flight_times;//飞行时间
    @Bind(R.id.e_time)
    TextView e_time;//到达时间
    @Bind(R.id.b_time)
    TextView b_time;//出起时间
    @Bind(R.id.orderNo)
    TextView orderNo;//订单号

    @Bind(R.id.phone)
    TextView phone;

    @Bind(R.id.list_view)
    NoScrollListView list_view;//乘机人列表
    private FlyOrder orderInfo = new FlyOrder();
    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private List<FlyPassenger> list = new ArrayList<>();
    private boolean isSee = false;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_completed_order);
        ButterKnife.bind(this);
        mcontext = this;
        orderInfo = (FlyOrder) getIntent().getSerializableExtra("orderInfo");
        list = orderInfo.getPassengers();
        if(orderInfo!=null){
            initData();
        }else{
            ToastUtils.show(mcontext,"该订单不存在");
        }
        String phone_number = orderInfo.getPhone().substring(0,3)+"****"+orderInfo.getPhone().substring(7);
        phone.setText(phone_number);
        headTitle.setText("订单详情");
        adapter = new MyAdapter(mcontext);
        list_view.setAdapter(adapter);

    }
    private void initData(){
        orderNo.setText(orderInfo.getOrderno());//订单号
        date_txt.setText(orderInfo.getDeptdate());//起飞日期
        deptCity_txt.setText(orderInfo.getArricity());//起飞城市
        arriCity_txt.setText(orderInfo.getDeptcity());//到达城市
        dep_airport.setText(orderInfo.getDeptairportcity());//出发机场
        arr_airport.setText(orderInfo.getArriairportcity());//到达机场
        b_time.setText(orderInfo.getDepttime());//出发时间
        e_time.setText(orderInfo.getArritime());//到达时间
        flight_times.setText(orderInfo.getFlightTimes());
        month_txt.setText(DataUtils.getWeek(orderInfo.getDeptdate()));
    }


    @OnClick({R.id.return_lin, R.id.change_btn,R.id.refund_btn,R.id.see_number_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.change_btn:
                //改签
                Intent intent = new Intent(mcontext,PlaneChangeTicketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderInfo",orderInfo);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case R.id.refund_btn:
                //退票

                Intent intent1 = new Intent(mcontext,PlaneRefundTicketActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("orderInfo",orderInfo);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                finish();
                break;
            case R.id.see_number_lin:
//                if(isSee){
//                    isSee = false;
//                }else{
                    isSee = true;
//                }
                phone.setText(orderInfo.getPhone());
                adapter.notifyDataSetChanged();
                break;


        }
    }

    class MyAdapter extends BaseAdapter {
        Context context;

        public MyAdapter( Context context) {
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
                        R.layout.act_plane_order_passengers_item, parent, false);
                holder = new MyAdapter.ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FlyPassenger bean = list.get(position);
            holder.name.setText(bean.getName());
            String show_id = "";
            if(isSee){
                show_id = bean.getCardno();
            }else{
                // 用于显示的加*身份证 4-11位加星号
                show_id = bean.getCardno().substring(0, 3) + "********" + bean.getCardno().substring(11);
            }

            holder.idCard.setText(show_id);

            return convertView;
        }


        class ViewHolder {
            public TextView name;
            public TextView idCard;


            ViewHolder(View itemView) {
                name = (TextView) itemView.findViewById(R.id.name);
                idCard = (TextView) itemView.findViewById(R.id.id_card);
            }
        }
    }

}
