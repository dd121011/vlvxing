package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.common.ValidateHelper;
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
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.model.UserInfo;
import com.vlvxing.app.pay.Alipay;
import com.vlvxing.app.pay.WxPay;
import com.vlvxing.app.utils.CacheData;
import com.vlvxing.app.utils.CacheUserData;
import com.vlvxing.app.utils.DistanceUtils;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/6 0006.
 * 填写订单
 */

public class WriteOrderActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.title_txt)
    TextView titleTxt;
    @Bind(R.id.distance_txt)
    TextView distanceTxt;
    @Bind(R.id.price_txt)
    TextView priceTxt;
    @Bind(R.id.number_txt)
    TextView numberTxt;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.address_edt)
    EditText addressEdt;
    @Bind(R.id.num_edt)
    EditText numEdt;
    @Bind(R.id.content_edt)
    EditText contentEdt;
    @Bind(R.id.total_txt)
    TextView totalTxt;
    private int p;
    private String price, pname, imgUrl, id;
    private double pathlat, pathlng;
    private int type,payWay;
    private String tradeNo, orderId, totalPrice="0.01", commodityName = "V旅行", commodityMessage = "支付";
    private String phone;
    private char array[]=new char[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writeorder_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        headTitle.setText("填写订单");
//        phoneEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length()>0) {
////                    if (StringUtils.isStringNull(phone)){
//                    if (array.length > 0) {
//                        for (int i = 0; i < array.length; i++) { //循环判断本地和输入的值是否相等
//                            String txt = s.toString();
//                            if (i < s.length()) {
//                                char c = txt.charAt(i);
//                                char c1 = array[i];
//                                if (c != c1) {
//                                    array[i] = c;
//                                }
//                            } else {
//                                array[i] = 'a';
//                            }
//                        }
//                    }
//                }
//            }
//        });
        List<UserInfo> list = CacheUserData.getRecentSubList();
        if (list.size()>0){
            UserInfo info = list.get(list.size()-1);
            nameEdt.setText(info.getName());
            //电话号码中间四位数用星号替代
             phone= info.getPhone();
//            array=phone.toCharArray();
//            if(!TextUtils.isEmpty(phone)){
//                StringBuilder sb  =new StringBuilder();
//                for (int i = 0; i < phone.length(); i++) {
//                    char c = phone.charAt(i);
//                    if (i >= 3 && i <= 6) {
//                        sb.append('*');
//                    } else {
//                        sb.append(c);
//                    }
//                }
//
//                phoneEdt.setText(sb.toString());
//            }
            phoneEdt.setText(phone);
            addressEdt.setText(info.getAddress());
            numEdt.setText(info.getCard());
            //身份证中间四位数用星号替代
            String card= info.getCard();
//            if(!TextUtils.isEmpty(card)){
//                StringBuilder sb  =new StringBuilder();
//                for (int i = 0; i < card.length(); i++) {
//                    char c = card.charAt(i);
//                    if (i >= 6 && i <= 13) {
//                        sb.append('*');
//                    } else {
//                        sb.append(c);
//                    }
//                }
//
//                numEdt.setText(sb.toString());
//            }
            numEdt.setText(card);
        }
        //设置第一次进入界面不弹出软键盘
        headTitle.setFocusable(true);
        headTitle.setFocusableInTouchMode(true);
        headTitle.requestFocus();


        Intent intent = getIntent();
        type=intent.getIntExtra("type",0);
        String json = intent.getStringExtra("date");
        Gson gson = new Gson();
        DetailModel model = gson.fromJson(json, DetailModel.class);
        DetailModel.DataBean bean = model.getData();
        id = bean.getTravelproductid();
        double price=bean.getPrice();
        p=(int)price;
        priceTxt.setText(p+"");
        totalTxt.setText(p+"");
        pname = bean.getProductname();
        titleTxt.setText(pname);
        pathlat = bean.getPathlat();//地点纬度
        pathlng = bean.getPathlng(); //地点经度
        imgUrl = bean.getProductsmallpic();
        Glide.with(this).load(imgUrl).error(R.mipmap.qiche).into(img);
        if (type == 1) { //1表示从农家院进入详情
            distanceTxt.setVisibility(View.VISIBLE);
           // 根据用户指定的两个坐标点，计算这两个点的实际地理距离
            String distance= DistanceUtils.getDistance(pathlat,pathlng);
            distanceTxt.setText(distance + "km");
        }
    }

    @Subscriber(tag = "changeMyorders")
    public void changeMyorder(int t){
        this.finish();
        startActivity(new Intent(this,MyOrderActivity.class));
    }
    @OnClick({R.id.return_lin, R.id.less_img, R.id.more_img, R.id.submit_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.less_img:
                LessNum(numberTxt);
                break;
            case R.id.more_img:
                addNum(numberTxt);
                break;
            case R.id.submit_txt:
                checkParams();
                break;
        }
    }

  private void checkParams(){
      String name = nameEdt.getText().toString().trim();
      String phones = phoneEdt.getText().toString().trim();
//      if (!StringUtils.isStringNull(phone)){
////          phones=array.toString();
//
//      }
      String address = addressEdt.getText().toString().trim();
      String num = numEdt.getText().toString().trim(); //身份证号
      String number = numberTxt.getText().toString().trim();
      String content = contentEdt.getText().toString().trim();
      if (StringUtils.isStringNull(name)) {
          ToastUtils.show(this, "请输入姓名!");
          return;
      }
      if (StringUtils.isStringNull(phones)) {
          ToastUtils.show(this, "请输入电话!");
          return;
      }
      if (!ValidateHelper.isPhoneNumberValid(phones)) {
          Toast.makeText(this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
          return;
      }
      if (StringUtils.isStringNull(address)) {
          ToastUtils.show(this, "请输入地址!");
          return;
      }
      if (StringUtils.isStringNull(num)) {
          ToastUtils.show(this, "请输入身份证号!");
          return;
      }
      if (!ValidateHelper.isIDCard(num)) {
          Toast.makeText(this, "请输入正确的身份证号!", Toast.LENGTH_SHORT).show();
          return;
      }

      //把填写的用户信息保存到本地
      List<UserInfo> list = CacheUserData.getRecentSubList();
//      if (list.size() <= 0) {
          saveUserInfo(name,phones,address,num);
//      }
      String total = totalTxt.getText().toString().trim();
      PayDialog payDialog=new PayDialog(this,total);
      payDialog.setmOnclickListener(new PayDialog.ClickSureListener() {
          @Override
          public void onClick(int type) {
              payWay=type; //支付方式 1支付宝 2微信
              payDialog.dismissDialog();
              clickSubmit();
          }
      });
    }

    private void saveUserInfo(String name,String phone,String address,String card) {
        UserInfo model = new UserInfo(name,phone,address,card);
        List<UserInfo> list = new ArrayList<>();
        list.add(model);
        CacheUserData.saveRecentSubList(list);
    }
    private void clickSubmit() {
        String name = nameEdt.getText().toString().trim();
        String phone = phoneEdt.getText().toString().trim();
        String address = addressEdt.getText().toString().trim();
        String num = numEdt.getText().toString().trim(); //身份证号
        String total = totalTxt.getText().toString().trim();
        String number = numberTxt.getText().toString().trim();
        String content = contentEdt.getText().toString().trim();
//        if (StringUtils.isStringNull(name)) {
//            ToastUtils.show(this, "请输入姓名!");
//            return;
//        }
//        if (StringUtils.isStringNull(phone)) {
//            ToastUtils.show(this, "请输入电话!");
//            return;
//        }
//        if (!ValidateHelper.isPhoneNumberValid(phone)) {
//            Toast.makeText(this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
//        }
//        if (StringUtils.isStringNull(address)) {
//            ToastUtils.show(this, "请输入地址!");
//            return;
//        }
//        if (StringUtils.isStringNull(num)) {
//            ToastUtils.show(this, "请输入身份证号!");
//            return;
//        }
//        if (!ValidateHelper.isIDCard(num)) {
//            Toast.makeText(this, "请输入正确的身份证号!", Toast.LENGTH_SHORT).show();
//        }
        showDialog("提交中...");
        String url = Constants.URL_SAVEORDER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("travelProductId", id);
        params.put("orderName",pname);
        params.put("orderAllPrice", total);
        params.put("orderCount", number);
        params.put("orderPrice", p+"");
        params.put("orderUserName", name);
        params.put("orderUserPhone", phone);
        params.put("orderUserAddress", address);
        params.put("orderUserId", num);
        params.put("orderUserMessage", content);
        params.put("orderPic", imgUrl);
        if (type == 1) {
            params.put("orderAddressLng", pathlng + "");
            params.put("orderAddressLat", pathlat + "");
        }
        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
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
//                    ToastUtils.show(WriteOrderActivity.this, "提交成功!");
                    JSONObject object=obj.getJSONObject("data");
                   orderId= object.getString("orderid");
//                    totalPrice=object.getString("orderallprice");
                    tradeNo=object.getString("systemtradeno");
                    payMoney();
                } else {
                    ToastUtils.show(WriteOrderActivity.this, message);
                }
                dismissDialog();
            }
        });

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
    private void LessNum(TextView txt) {
        String subString = txt.getText().toString().trim();
        int num = Integer.valueOf(subString);
        if (num <= 1) {
            return;
        }
        int n=--num;
        int t = p * num;
        totalTxt.setText(t + "");
        txt.setText(n+"");
    }

    private void addNum(TextView txt) {
        String addString = txt.getText().toString().trim();
        int numAdd = Integer.valueOf(addString);
        if (numAdd >= 100) {
            return;
        }
       int a= ++numAdd;
       int t = p * a;
        totalTxt.setText(t + "");
        txt.setText(a+"");
    }
}
