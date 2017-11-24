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
import android.widget.Toast;

import com.handongkeji.ui.BaseActivity;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.NoScrollListView;
import com.qunar.bean.FlyPassenger;
import com.qunar.bean.Passenger;
import com.qunar.model.FlyOrder;
import com.vlvxing.app.R;
import com.vlvxing.app.common.IsInstallApp;
import com.vlvxing.app.common.PayDialog;
import com.vlvxing.app.pay.Alipay;
import com.vlvxing.app.pay.WxPay;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单详情
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
    @Bind(R.id.body_lin)
    LinearLayout body_lin;

    @Bind(R.id.pay_bottom_lin)
    LinearLayout pay_bottom_lin;//支付
    @Bind(R.id.bottom_lin)
    LinearLayout bottom_lin;//退票,改签
    @Bind(R.id.pay_btn)
    Button pay_btn;//支付


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
    private int cancharge = 0;//为0时,不支持退票,并且不支持改签    为1时,支持改签  为2时,支持退票
    private int canrefund = 0;
    private int status = -2;//0 未支付  1 已支付   -1 改签  3 退票



    private String tradeNo, orderId, totalPrice = "0.01", commodityName = "V旅行", commodityMessage = "支付";
    private int payWay;//支付方式
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_completed_order);
        ButterKnife.bind(this);
        mcontext = this;
        orderInfo = (FlyOrder) getIntent().getSerializableExtra("orderInfo");
        if(orderInfo!=null){
            status = orderInfo.getStatus();
            list = orderInfo.getPassengers();
            if(status==0){//未支付
                pay_bottom_lin.setVisibility(View.VISIBLE);
                bottom_lin.setVisibility(View.GONE);
            }else{
                //已支付
                bottom_lin.setVisibility(View.VISIBLE);
                pay_bottom_lin.setVisibility(View.GONE);
            }
            initData();
        }else{
            ToastUtils.show(mcontext,"系统繁忙,请稍后再试");
        }


    }
    private void initData(){
        orderInfo.getAllowchange();//是否允许签转  true,false
        orderInfo.getCancharge();//是否允许改签   true,false
        orderInfo.getCanrefund();//是否允许退票   true,false
        if(orderInfo.getCancharge().equals("true")){//改签
            cancharge = 1;
        }else{
            cancharge = 0;
        }
        if(orderInfo.getCanrefund().equals("true")){
            canrefund = 1;
        }else{
            canrefund = 0;
        }
        orderNo.setText(orderInfo.getOrderno());//订单号
        date_txt.setText(orderInfo.getDeptdate());//起飞日期
        deptCity_txt.setText(orderInfo.getArricity());//起飞城市
        arriCity_txt.setText(orderInfo.getDeptcity());//到达城市
//        if(orderInfo){
//
//        }
        dep_airport.setText(orderInfo.getDeptairportcity());//出发机场
        arr_airport.setText(orderInfo.getArriairportcity());//到达机场
        b_time.setText(orderInfo.getDepttime());//出发时间
        e_time.setText(orderInfo.getArritime());//到达时间
        flight_times.setText(orderInfo.getFlightTimes());
        month_txt.setText(DataUtils.getWeek(orderInfo.getDeptdate()));


        String phone_number = orderInfo.getPhone().substring(0,3)+"****"+orderInfo.getPhone().substring(7);
        phone.setText(phone_number);
        headTitle.setText("订单详情");
        adapter = new MyAdapter(mcontext);
        list_view.setAdapter(adapter);
        body_lin.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.return_lin, R.id.change_btn,R.id.refund_btn,R.id.see_number_lin,R.id.pay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.change_btn:
                if(cancharge==1){
                    //改签
                    Intent intent = new Intent(mcontext,PlaneChangeTicketActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderInfo",orderInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    ToastUtils.show(mcontext,"该机票订单不支持改签");
                }
                break;
            case R.id.refund_btn:
                if(canrefund==1){
                    //退票
                    Intent intent1 = new Intent(mcontext,PlaneRefundTicketActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("orderInfo",orderInfo);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                }else{
                    ToastUtils.show(mcontext,"该机票订单不支持退票");
                }
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
            case R.id.pay_btn:
                tradeNo = orderInfo.getOrderno();
                orderId = orderInfo.getOrderid();
                totalPrice = orderInfo.getNopayamount()+"";

                PayDialog payDialog=new PayDialog(this,totalPrice);
                payDialog.setmOnclickListener(new PayDialog.ClickSureListener() {
                    @Override
                    public void onClick(int type) {
                        payWay=type; //支付方式 1支付宝 2微信
                        payDialog.dismissDialog();
                        payMoney();
                    }
                });
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
        public boolean isEnabled(int position) {
            //设置listview不可点击
            return false;
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

    public void payMoney() {
        double price = Double.parseDouble(totalPrice);
        Toast.makeText(this, "price" + price, Toast.LENGTH_SHORT).show();
        DecimalFormat decimalFormat = new DecimalFormat(
                "###################.###########");
        String totalMoney = decimalFormat.format(price);//变成整数类型
        Toast.makeText(this, "totalMoney" + totalMoney, Toast.LENGTH_SHORT).show();
//        int payMethod = dialog.getPayMethod();
        switch (payWay) {
            case 1:   //  支付宝支付
                Alipay alipay = new Alipay(this);

                alipay.getOrderInfo(tradeNo, totalMoney, orderId, commodityName, commodityMessage);
                break;
            case 2:          //  微信支付
                if (!IsInstallApp.isInstall(this, "com.tencent.mm")) {
                    Toast.makeText(this, "您还没有安装微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                WxPay wxPay = new WxPay(this);
                wxPay.getOrderInfo(tradeNo, totalMoney, orderId); //生成微信支付参数
                break;
        }
    }

}
