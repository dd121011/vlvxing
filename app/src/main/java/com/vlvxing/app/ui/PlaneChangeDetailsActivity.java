package com.vlvxing.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.qunar.bean.BookingResponseParam;
import com.qunar.bean.ExpressInfo;
import com.qunar.bean.ExtInfo;
import com.qunar.bean.FlightInfo1;
import com.qunar.bean.PriceInfo;
import com.qunar.bean.TgqPointCharge;
import com.qunar.bean.TgqShowData;
import com.qunar.bean.applyChange.ApplyChangeResponse;
import com.qunar.bean.applyChange.ApplyChangeResult;
import com.qunar.bean.changeSearch.PlaneChangeSerachBean;
import com.qunar.model.PlaneBookResult;
import com.qunar.model.PlaneChangeApplyResult;
import com.qunar.model.PlaneChangeSuccessResult;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.IsInstallApp;
import com.vlvxing.app.model.PlaneUserInfo;
import com.vlvxing.app.pay.Alipay;
import com.vlvxing.app.pay.WxPay;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买详情 添加用户身份信息 提交订单
 */

public class PlaneChangeDetailsActivity extends BaseActivity {
   @Bind(R.id.title_txt)
   TextView title_txt;
    @Bind(R.id.bottom_left_lin)
    LinearLayout bottomLeftLin;//总价linearlayout
    @Bind(R.id.quicklypay_btn)
    Button quicklypay;//提交订单
    @Bind(R.id.pay_lin)
    LinearLayout payLin;//支付
    @Bind(R.id.body)
    LinearLayout bodyLin;//详情
    @Bind(R.id.total_txt)
    TextView totalTxt;//总金额
    @Bind(R.id.date)
    TextView dateTxt;
    @Bind(R.id.check1)
    CheckBox check1;//支付宝
    @Bind(R.id.check2)
    CheckBox check2;//微信
    @Bind(R.id.b_time)
    TextView b_time;//出发时间
    @Bind(R.id.dep_airport)
    TextView dep_airport;//出发机场
    @Bind(R.id.e_time)
    TextView e_time;//到达时间
    @Bind(R.id.arr_airport)
    TextView arr_airport;//到达机场
    private Context mcontext;
    private String date;
    private int type;//支付方式
    private int payWay = 1;
    private Dialog bottomDialog;
    private ArrayList<PlaneUserInfo> userInfoList;
    private PopupWindow mPopupWindow;
    private boolean isPopWindowShowing = false;
    private View mGrayLayout;
    private int fromYDelta;
    private String price, pname, imgUrl, id;
    private int p = 0 ;//机票单价+机建+燃油费,从上个页面传递过来的(购票必选)
    private int userNumber = 1;//购票数量,从用户添加的乘客来统计
    private String orderNo = "";
    private String dateResult = "";
    private PlaneChangeSerachBean.ResultBean.ChangeSearchResultBean.TgqReasonsBean.ChangeFlightSegmentListBean orderInfo;
    private String passengerIds = "" ;
    private String startDate = "";
    private String gQId = "";
    private String tradeNo, orderId, totalPrice="0.01", commodityName = "V旅行", commodityMessage = "支付";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_change_details);
        ButterKnife.bind(this);
        mcontext = this;
        showDialog("加载中...");
        orderInfo = (PlaneChangeSerachBean.ResultBean.ChangeSearchResultBean.TgqReasonsBean.ChangeFlightSegmentListBean) getIntent().getSerializableExtra("orderInfo");
        dateResult = getIntent().getStringExtra("date");
        orderNo = getIntent().getStringExtra("orderNo");
        passengerIds = getIntent().getStringExtra("id");
        startDate = getIntent().getStringExtra("startDate");
        userNumber = getIntent().getIntExtra("userNumber",1);
        title_txt.setText("改签申请");
        mGrayLayout = (View) findViewById(R.id.gray_layout);
        userInfoList = new ArrayList<PlaneUserInfo>();
        PlaneUserInfo model = new PlaneUserInfo(1, "", "");
        userInfoList.add(model);
        initData();


    }

    private void initData(){
//        holder.planeName.setText(info.getFlight()+info.getFlightNo());//航空公司全名
//        holder.planeStyle.setText(info.getFlightType());//机型全名
        p = Integer.parseInt(orderInfo.getAllFee())*userNumber;
        if(p ==0){
            quicklypay.setText("改签");
        }else{
            quicklypay.setText("支付");
        }
        dateTxt.setText(dateResult);
        b_time.setText(orderInfo.getStartTime());
        dep_airport.setText(orderInfo.getStartPlace());
        e_time.setText(orderInfo.getEndTime());
        arr_airport.setText(orderInfo.getEndPlace());
        totalTxt.setText(orderInfo.getAllFee());
        payLin.setVisibility(View.VISIBLE);
        bodyLin.setVisibility(View.VISIBLE);
        dismissDialog();
    }






    private void showDialog() {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.act_plane_change_price_popupwindow_, null);
        bottomDialog.setContentView(contentView);

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = (getResources().getDisplayMetrics().widthPixels);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView firstPrice = (TextView)contentView.findViewById(R.id.b_first_price);//票价

        TextView firstNumber = (TextView)contentView.findViewById(R.id.b_first_number);

        Button quicklypay_btn = (Button)contentView.findViewById(R.id.quicklypay_btn);
        if(p ==0){
            quicklypay_btn.setText("改签");
        }else{
            quicklypay_btn.setText("支付");
        }
        TextView total_txt = (TextView)contentView.findViewById(R.id.total_txt);//总金额
        firstPrice.setText(p+"");
        firstNumber.setText(userNumber+"");

        total_txt.setText(totalTxt.getText().toString());
        LinearLayout bottomLeftLin = (LinearLayout) contentView.findViewById(R.id.bottom_left_lin);
        //设置点击外部空白处可以关闭Activity
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.setCancelable(true);
//        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        bottomLeftLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.btn_back, R.id.bottom_left_lin, R.id.pay_rel, R.id.wxpay_rel, R.id.quicklypay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_rel: //支付宝
                type = 1;
                payWay = 1;
                check1.setChecked(true);
                check2.setChecked(false);
                break;
            case R.id.wxpay_rel:  //微信
                type = 2;
                payWay = 2;
                check2.setChecked(true);
                check1.setChecked(false);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.bottom_left_lin:
                //显示总价明细
                showDialog();
                break;

            case R.id.quicklypay_btn:
                getOrderIdData();
                break;
        }
    }




    public void payMoney() {
        double price = Double.parseDouble(totalPrice);
        Toast.makeText(this, "price"+price, Toast.LENGTH_SHORT).show();
        DecimalFormat decimalFormat = new DecimalFormat(
                "###################.###########");
        String totalMoney = decimalFormat.format(price);//变成整数类型
        Toast.makeText(this, "totalMoney"+totalMoney, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "payWay"+payWay, Toast.LENGTH_SHORT).show();
//        int payMethod = dialog.getPayMethod();
        switch (payWay) {
            case 1:   //  支付宝支付
                Alipay alipay = new Alipay(this);
                alipay.getPlaneOrderInfo(tradeNo, totalMoney, orderId, commodityName, commodityMessage);
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


    private void getOrderIdData() {
        //改签申请
        String url = Constants.QUNAR_BASE_URL;
//        String url = "http://192.168.1.103/";
        HashMap<String,Object> params = new HashMap<>();
        params.put("orderNo",orderNo);
        params.put("changeCauseId",1);
        params.put("passengerIds",passengerIds);
        params.put("uniqKey",orderInfo.getUniqKey());
        params.put("gqFee",orderInfo.getGqFee());
        params.put("upgradeFee",orderInfo.getUpgradeFee());
        params.put("flightNo",orderInfo.getFlightNo());
        params.put("cabinCode",orderInfo.getCabinCode());
        params.put("startDate",startDate);
        params.put("startTime",orderInfo.getStartTime());
        params.put("endTime",orderInfo.getEndTime());
        params.put("applyRemarks","改签");
        params.put("allFee",orderInfo.getAllFee());
        showDialog("加载中...");
        RemoteDataHandler.asyncPlaneGet(url+"applyChange",params,mcontext,new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                System.out.println("改签申请 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                System.out.println("改签申请 json:"+json);
                Gson gson = new Gson();
                PlaneChangeSuccessResult model = gson.fromJson(json,PlaneChangeSuccessResult.class);
                int status = model.getStatus();
                if(status==1){
                    if(model.getMessage().equalsIgnoreCase("SUCCESS")){
                    ApplyChangeResponse applyChangeResponse = model.getData();
                    if(applyChangeResponse.getCode()==0){
                        List<ApplyChangeResponse.ResultBean>  resultList = applyChangeResponse.getResult();
                        ApplyChangeResponse.ResultBean.ChangeApplyResultBean changeApplyResult = resultList.get(0).getChangeApplyResult();
                        gQId = changeApplyResult.getGqId()+"";
                        orderNo = changeApplyResult.getOrderNo();
                        dismissDialog();

                        if(p==0){
                            ToastUtils.show(mcontext,"改签申请已递交,请您耐心等待...");
                            Intent intent = new Intent(mcontext,PlaneOrderActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            payMoney();
                        }
                    }
                    }else{
                        dismissDialog();
                        ToastUtils.show(mcontext, model.getMessage());
                    }
                }else{
                    ToastUtils.show(mcontext, "系统繁忙,请稍后再试");
                    dismissDialog();
                }
                dismissDialog();

            }
        });
    }


    private void getPayData() {//改签支付成功返回

        String url = Constants.QUNAR_BASE_URL;
        HashMap<String,Object> params = new HashMap<>();
        params.put("orderNo",orderNo);
        params.put("gqId",gQId);
        params.put("passengerIds",passengerIds);
        params.put("totalAmount",p);

        showDialog("加载中...");
        RemoteDataHandler.asyncPlaneGet(url+"changepay",params,mcontext,new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                System.out.println("改签支付接口 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                System.out.println("改签支付接口 json:"+json);
                Gson gson = new Gson();
                PlaneChangeApplyResult model = gson.fromJson(json,PlaneChangeApplyResult.class);
                int status = model.getStatus();
                if(status==1){
                    ApplyChangeResult response = model.getData();

                    ApplyChangeResult.ResultBean result = response.getResult();
                    if(result==null){
                        ToastUtils.show(mcontext, "系统繁忙,请稍后再试!");
                        dismissDialog();
                        return;
                    }
                    int code = result.getCode();
                    if(code ==0){
                        List<ApplyChangeResult.ResultBean.ResultsBean> resultsBeanList =  result.getResults();
                        ApplyChangeResult.ResultBean.ResultsBean bean =  resultsBeanList.get(0);
                        orderNo = bean.getOrderNo();
//                        payAmount = bean.getPayAmount();


                    }



                }else{
                    ToastUtils.show(mcontext, model.getMessage());
                }
                dismissDialog();

            }
        });
    }



}
