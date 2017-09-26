package com.vlvxing.app.pay;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.widget.MyProcessDialog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 微信支付
 * @ClassName:WxPay

 * @PackageName:com.ruigai.b2c.pay

 * @Create On 2016/9/27   18:04

 * @Site:http://www.handongkeji.com

 * @author:zhouhao

 * @Copyrights 2016/9/27 handongkeji All rights reserved.
 */
public class WxPay {


    private Activity mActivity;

    public WxPay(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * @param trade_no         系统订单号
     * @param price            支付金额
     * @param orderId          第三方订单号
     */
    public void getOrderInfo(String trade_no, String price, String orderId) {

        String url = Constants.URL_WXORDER;
        HashMap<String, String> params = new HashMap<>();
        if (TextUtils.isEmpty(price)) {
            price = "0";
        }

        SharedPreferences sp = mActivity.getSharedPreferences("WXOrderId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("orderid", orderId);
        editor.commit();

        double p = Double.valueOf(price);
        params.put("token", ((MyApp) mActivity.getApplication()).getUserTicket());
        params.put("order_id",orderId);
        params.put("total_fee", ((int) (p * 100)) + "");
        params.put("out_trade_no", trade_no);
        RemoteDataHandler.asyncTokenPost(url, mActivity, false, params, data -> {
            String json = data.getJson();
            if (TextUtils.isEmpty(json)) return;
            JSONObject jso = null;
            int status = 0;
            try {
                jso = new JSONObject(json);
                status = jso.getInt("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status == 1) {
                try {
                    JSONObject object = jso.getJSONObject("data");
                    if (object.length()==0){
                        Toast.makeText(mActivity,"订单状态已改变",Toast.LENGTH_SHORT).show();
                    }
                    pay(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
//                    dialog.dismiss();
                }
            }
        });
    }

    private void pay(JSONObject object) throws JSONException {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(mActivity, Constants.WX_ID, false);
        iwxapi.registerApp(Constants.WX_ID);
        PayReq payReq = new PayReq();

        payReq.appId = Constants.WX_ID;
        payReq.partnerId = object.getString("partner_id");
        payReq.prepayId = object.getString("prepay_id");
        payReq.packageValue = "Sign=WXPay";
        payReq.nonceStr = object.getString("nonce_str");
        payReq.timeStamp = genTimeStamp() + "";

        List<Pair<String,String>> signParams = new LinkedList<Pair<String,String>>();
        signParams.add(new Pair("appid", payReq.appId));
        signParams.add(new Pair("noncestr", payReq.nonceStr));
        signParams.add(new Pair("package", payReq.packageValue));
        signParams.add(new Pair("partnerid", payReq.partnerId));
        signParams.add(new Pair("prepayid", payReq.prepayId));
        signParams.add(new Pair("timestamp", payReq.timeStamp));

        payReq.sign = genAppSign(signParams);

//        payReq.sign = object.getString("sign");
        iwxapi.sendReq(payReq);

    }

    private String genAppSign(List<Pair<String,String>> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).first);
            sb.append('=');
            sb.append(params.get(i).second);
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.WX_KEY);// API_KEY

        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        System.out.println("appSign--" + appSign);
        return appSign;
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

}
