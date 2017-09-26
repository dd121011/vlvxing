package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.CallDialog;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.IsInstallApp;
import com.vlvxing.app.common.PayDialog;
import com.vlvxing.app.model.DetailModel;
import com.vlvxing.app.model.MyOrderModel;
import com.vlvxing.app.model.OrderDetaisModel;
import com.vlvxing.app.pay.Alipay;
import com.vlvxing.app.pay.WxPay;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/6 0006.
 * 订单详情
 */

public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.status_txt)
    TextView statusTxt;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.title_txt)
    TextView titleTxt;
    @Bind(R.id.distance_txt)
    TextView distanceTxt;
    @Bind(R.id.price_txt)
    TextView priceTxt;
    @Bind(R.id.bond_txt)
    TextView bondTxt;
    @Bind(R.id.name_txt)
    TextView nameTxt;
    @Bind(R.id.phone_txt)
    TextView phoneTxt;
    @Bind(R.id.address_txt)
    TextView addressTxt;
    @Bind(R.id.num_txt)
    TextView numTxt;
    @Bind(R.id.content_txt)
    TextView contentTxt;
    @Bind(R.id.pay_txt)
    TextView payTxt;
    @Bind(R.id.ordernum_txt)
    TextView ordernumTxt;
    @Bind(R.id.time_txt)
    TextView timeTxt;
    @Bind(R.id.goremark_txt)
    TextView goremarkTxt;
    @Bind(R.id.cancle_txt)
    TextView cancleTxt;
    @Bind(R.id.total_txt)
    TextView totalTxt;
    @Bind(R.id.exit_txt)
    TextView exitTxt;
    @Bind(R.id.pay_lin)
    LinearLayout payLin;
    @Bind(R.id.bottom_lin)
    LinearLayout bottomLin;
    @Bind(R.id.order_rel)
    RelativeLayout orderRel;
    private String orderid,productid;
    private int money,payWay;
    private String tradeNo, orderId, totalPrice="0.01", commodityName = "V旅行", commodityMessage = "支付";
    public static Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetail_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext=this;
        headTitle.setText("订单详情");
        Intent intent = getIntent();
        orderid=intent.getStringExtra("id");
//        MyOrderModel.DataBean bean = intent.getParcelableExtra("data");
        initData();
    }

    private void initData() {
        showDialog("数据加载中...");
        String url = Constants.URL_ORDERDETAIL;
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderid);
        params.put("token",myApp.getUserTicket());
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                OrderDetaisModel model = gson.fromJson(json, OrderDetaisModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    OrderDetaisModel.DataBean bean = model.getData();
                    productid=bean.getTravelproductid();
                    double pathlat = bean.getOrderaddresslat();//地点纬度
                    double pathlng = bean.getOrderaddresslng(); //地点经度
                    // 根据用户指定的两个坐标点，计算这两个点的实际地理距离
                    String l=myApp.getLat();
                    String ln=myApp.getLng();
                    double lat = Double.valueOf(l);
                    double lng = Double.valueOf(ln);
                    LatLng p1 = new LatLng(lat, lng);
                    LatLng p2 = new LatLng(pathlat, pathlng);
                    //计算p1、p2两点之间的直线距离，单位：米
                    double distance = DistanceUtil.getDistance(p1, p2);
                    distance = distance * 0.001; //米转换成千米
                    //四舍五入，保留两位小数
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    distanceTxt.setText(nf.format(distance) + "km");
                    String orderstatus = bean.getOrderstatus();
                    int s = Integer.valueOf(orderstatus); ////订单状态   0待支付   1已支付/待评价  2已评价   3已取消
                    if (s == 0) {
                        payLin.setVisibility(View.VISIBLE);
                        cancleTxt.setVisibility(View.GONE);
                        goremarkTxt.setVisibility(View.GONE);
                        orderstatus="待付款";
                    }else if (s==1){
                        payLin.setVisibility(View.GONE);
                        cancleTxt.setVisibility(View.GONE);
                        goremarkTxt.setVisibility(View.VISIBLE);
                        distanceTxt.setVisibility(View.VISIBLE);
                        orderstatus="已付款";
                    }else if (s==2){
                        bottomLin.setVisibility(View.GONE);
                        orderstatus="已评价";
                    }else if (s==3){
                        bottomLin.setVisibility(View.GONE);
                        distanceTxt.setVisibility(View.VISIBLE);
                        orderstatus="已取消";
                    }
                    statusTxt.setText("订单状态："+orderstatus);
                    titleTxt.setText(bean.getOrdername());
                    double price = bean.getOrderprice();
                    money = (int) price;
                    priceTxt.setText(money + ""); //单价
                    double priceall = bean.getOrderallprice();
                    int pa = (int) priceall;
//                    totalPrice=pa+"";
                    tradeNo=bean.getSystemtradeno();
                    orderId=bean.getOrderid();
                    totalTxt.setText(pa + "");  //总价
                    bondTxt.setText("*"+bean.getOrdercount());
                    String bgUrl = bean.getOrderpic();
                        Glide.with(OrderDetailActivity.this).load(bgUrl).error(R.mipmap.photo_pingjia).into(img);

                    nameTxt.setText(bean.getOrderusername());
                    phoneTxt.setText(bean.getOrderuserphone());
                    addressTxt.setText(bean.getOrderuseraddress());
                    numTxt.setText(bean.getOrderuserid()); //身份证号
                    contentTxt.setText(bean.getOrderusermessage());
                    payTxt.setText(pa+"");
                    ordernumTxt.setText(bean.getSystemtradeno()); //订单号
                    String time = DataUtils.format(bean.getOrdercreatetime(),"yyyy-MM-dd HH:mm");
                    timeTxt.setText(time);
                }else {
                    ToastUtils.show(OrderDetailActivity.this,model.getMessage());
                }
                dismissDialog();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext=null;
    }

    @Subscriber(tag = "changeMyorder")
    public void changeMyorder(int t){
        this.finish();
//        startActivity(new Intent(this,MyOrderActivity.class));
    }

    @OnClick({R.id.return_lin, R.id.goremark_txt, R.id.cancle_txt, R.id.exit_txt, R.id.quicklypay_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.goremark_txt: //去评价
               startActivity(new Intent(this,RemarkOrderActivity.class).putExtra("id",productid).putExtra("orderid",orderid));
                break;
            case R.id.cancle_txt:
                cancleOrder();
                break;
            case R.id.exit_txt: //取消订单
                cancleOrder();
                break;
            case R.id.quicklypay_txt: //立即支付
                PayDialog payDialog=new PayDialog(this,money+"");
                payDialog.setmOnclickListener(new PayDialog.ClickSureListener() {
                    @Override
                    public void onClick(int type) {
                        payDialog.dismissDialog();
                        payWay=type;
                        payMoney();
                    }
                });
                break;
        }
    }

    public void payMoney() {
        double price = Double.parseDouble(totalPrice);
        DecimalFormat decimalFormat = new DecimalFormat(
                "###################.###########");
        String totalMoney = decimalFormat.format(price);//变成整数类型
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
    private void cancleOrder() {
        CallDialog dialog=new CallDialog(this,"确定取消订单?");
        dialog.setNegativeButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("取消中...");
                String url = Constants.URL_DELETEORDER;
                HashMap<String, String> params = new HashMap<>();
                params.put("token", myApp.getUserTicket());
                params.put("orderId",orderid);
                RemoteDataHandler.asyncTokenPost(url, OrderDetailActivity.this, false, params, new RemoteDataHandler.Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) throws JSONException {
                        String json = data.getJson();
                        if (StringUtils.isStringNull(json)) {
                            return;
                        }
                        JSONObject obj = new JSONObject(json);
                        String status = obj.getString("status");
                        String message = obj.getString("message");
                        if (status.equals("1")) {
                            ToastUtils.show(OrderDetailActivity.this, "取消订单成功!");
                            finish();
                        } else {
                            ToastUtils.show(OrderDetailActivity.this, message);
                        }
                        dismissDialog();
                    }
                });

            }
        });
    }
}
