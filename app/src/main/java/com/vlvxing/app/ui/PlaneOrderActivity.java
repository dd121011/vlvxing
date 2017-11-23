package com.vlvxing.app.ui;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.MyProcessDialog;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qunar.bean.CreateOrderResult;
import com.qunar.model.CreateOrderData;
import com.qunar.model.FlyOrder;
import com.qunar.model.PlaneOrderResult;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.IsInstallApp;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.MessageModel;
import com.vlvxing.app.model.plane.PlaneOrderBean;
import com.vlvxing.app.pay.Alipay;
import com.vlvxing.app.pay.WxPay;
import com.vlvxing.app.utils.ToastUtils;


import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 */

public class PlaneOrderActivity extends BaseActivity {
    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键

    @Bind(R.id.list_view)
    ListView listView;

    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private int flag = 0;//记录当前页面 改签0  退票1

    private List<FlyOrder> list ;
    private MyAdapter adapter;

    private CreateOrderResult result;//生单接口成功 返回到当前订单页面 返回一条数据



    private String price, pname, imgUrl, id;
    private String tradeNo, orderId, totalPrice="0.01", commodityName = "V旅行", commodityMessage = "支付";
    private int p = 0 ;//机票单价+机建+燃油费,从上个页面传递过来的(购票必选)
    private int j = 0;//机建+燃油费
    private int num = 1;//购票数量,从用户添加的乘客来统计
    private int h = 0;//航意险单价
    private int y = 20;//报销需要的邮费
    private int type,payWay;//支付方式
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_plane_order);
        ButterKnife.bind(this);
        mcontext = this;
        EventBus.getDefault().register(this);
        headTitle.setText("机票订单");
        list  = new ArrayList<>();
        getOrderData();
        adapter = new MyAdapter(mcontext);
        listView.setAdapter(adapter);
//        result = (CreateOrderResult)getIntent().getSerializableExtra("result");

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
            MyAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.act_my_plane_order_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


//            model.getOrderid();//订单id
//            model.getOrderno();//订单号
//            model.getAirlinecode();//航司
//            model.getAirlinename();//航空公司
//            model.getArritime();//到达时间
//            model.getCabin();//仓位
//            model.getDeptairportcity();//出发机场
//            model.getArriairportcity();//到达机场
            FlyOrder bean = list.get(position);
            holder.state.setText(bean.getStatusdesc());//订单状态
            holder.cityLeft.setText(bean.getDeptcity());//出发城市
            holder.cityRight.setText(bean.getArricity());//到达城市
            holder.startDate.setText(bean.getDeptdate());//出发日期
            holder.startTime.setText(bean.getDepttime());//出发时间
            holder.hangbanhao.setText(bean.getFlightnum());//航班号
            holder.price.setText(bean.getNopayamount()+"");//支付金额

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //行单击
                    int status = bean.getStatus();
                    //订单状态码
                    if(status == 0){//未完成订单,暂时跳转付款页面
                        tradeNo = bean.getOrderno();
                        orderId = bean.getOrderid();
                        totalPrice = bean.getNopayamount()+"";
                        payMoney();
                    }else if(status == 1 || status == -1){//已完成,跳转已完成订单 状态为-1时,该票已经改签过一次了,这张票失效
                        Intent intent = new Intent(mcontext,PlaneCompletedOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderInfo",bean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMsg(bean.getUserid(),bean.getOrderid(),position);
                }
            });
            return convertView;
        }


        class ViewHolder {
            public TextView state;//订单状态
            public TextView cityLeft;//出发城市
            public TextView cityRight;//到达城市
            public TextView startDate;//起飞日期
            public TextView startTime;//起飞时间
            public TextView hangbanhao;//航班号
            public TextView price;//订单价格
            public TextView delete;
            public LinearLayout layout;

            ViewHolder(View itemView) {
                state = (TextView) itemView.findViewById(R.id.state);
                cityLeft = (TextView) itemView.findViewById(R.id.city_left);
                cityRight = (TextView) itemView.findViewById(R.id.city_right);
                startDate = (TextView)itemView.findViewById(R.id.start_date);
                startTime = (TextView)itemView.findViewById(R.id.start_time);
                hangbanhao = (TextView)itemView.findViewById(R.id.hangbanhao);
                price = (TextView) itemView.findViewById(R.id.price);
                delete = (TextView) itemView.findViewById(R.id.tv_delete);
                layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            }
        }
    }


    protected void deleteMsg(Long userId,String orderId,int position) {
        MyProcessDialog dialog = new MyProcessDialog(this);
        dialog.show();
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String,Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("orderId",orderId);
        RemoteDataHandler.asyncPlaneGet(url+"deleteByOrderState",params,mcontext, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                String json = data.getJson();
                System.out.println("我的订单 删除操作 json"+json);
                if (StringUtils.isStringNull(json)) {
                    dialog.dismiss();
                    return;
                }
                try {
                    JSONObject obj = new JSONObject(json);
                    String status = obj.getString("status");
                    System.out.println("我的订单 删除操作 status"+status);
                    String message = obj.getString("message");
                    System.out.println("我的订单 删除操作 message"+message);
                    if (status.equals("1")) {
                        Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
                        //解决删除之后,删除按钮还存在的Bug
                        SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                        if (viewCache != null) {
                            viewCache.smoothClose();
                        }
                        list.remove(position);
                        System.out.println("我的订单 删除成功 message"+message);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show(mcontext, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.return_lin, R.id.right_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;

        }
    }

    private void getOrderData() {
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String,Object> params = new HashMap<>();
        params.put("userid", MyApp.getInstance().getUserId());

        showDialog("加载中...");
        RemoteDataHandler.asyncPlaneGet(url+"selectFlyOrderByUserId",params,mcontext,new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                System.out.println("我的订单 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                System.out.println("我的订单 json:"+json);
                Gson gson = new Gson();
                PlaneOrderResult result = gson.fromJson(json,PlaneOrderResult.class);

                if(result!=null){
                    Integer status = result.getStatus();//订单状态码
                    System.out.println("我的订单 status:"+status);
                    if(status != null){
                        if(status == 1){
                        System.out.println("我的订单 if status:"+status);
                        list.clear();
                        list = result.getData();
                        System.out.println("我的订单 clientsite;:"+list.get(0).getClientsite());
                        adapter.notifyDataSetChanged();
                        }
                    }else{
                        ToastUtils.showT(mcontext,"暂无数据");
                    }
                }
                dismissDialog();
            }
        });
    }

    public void payMoney() {
        double price = Double.parseDouble(totalPrice);
        Toast.makeText(this, "price"+price, Toast.LENGTH_SHORT).show();
        DecimalFormat decimalFormat = new DecimalFormat(
                "###################.###########");
        String totalMoney = decimalFormat.format(price);//变成整数类型
        Toast.makeText(this, "totalMoney"+totalMoney, Toast.LENGTH_SHORT).show();
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
