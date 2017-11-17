package com.vlvxing.app.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.handongkeji.alipay.*;
import com.handongkeji.common.HttpHelper;
import com.handongkeji.handler.RemoteDataHandler;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.ui.OrderDetailActivity;
import com.vlvxing.app.ui.SaveAfterActivity;


import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/18. Zophar 新增机票支付配合后台
 */
public class Alipay {

    private static final String TAG = "Alipay";

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2017050307090883";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    // 商户PID
    public static final String PARTNER = "2088521415442521";
    // 商户收款账号
    public static final String SELLER = "13693571703@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCiDzeR1dYBWM5w3X/xuTsLHWtiXjnGaE6XKzBIz+37BCtjhpds3B5FGL1a8+TD4ZicUPt2UdHcPsg4u06MCUvPBuC8/XidEk26CL9q3WZoFwg6O6jdSB/PYCJVnMj6kaZH6hQ1J9jcdt+DpZqboiK4WPaCRJBZjMHxc/lDRAGP87IcIpFMNRGr2i5iiyMT3IRCqtWpyhaDdF78H+YCZDbw6BtV8KgyOTR2h4Kui4wKn2uQIBKnn/wjcxVVeEamuKyEHs1ODfI/Q0pLYbMfQLjlmNT6Y8NYfvkWyDoJxmlQ9q+fm4GSc6UsZith1qRPOGfKf4JvVrQXN1UlDp1vH0prAgMBAAECggEAHVKHiXmZeTDqWX2actBDIKW4ElpeNcoVvkBwQhxtQOfmOeHWcDn7uCDb0iMjF1YwWMhGV4hCYolxjOJHOgtMji7+ZvOt6S2wpqKCkAFzUA4qzO6ShUdRiiIo1DjEHKJR5d0yvtNw56MSmi1SpipWAekG4Vcqjylqxhdn2IXrinIRfXT2Dvv0Mtc69JyfmvNyj1EPwITg1QN35srAoRdZV77uUQHZtknIwj8Iv3frfkN8dk7/1/z06DRWqdGoktc8ks0xPfUmxjc4Gt46+9+bHnZjRnxVS0Yg17At8FgXj4B3dJ+OgpREKlntk5lKMjmBSjdSQCpzZnCVbxEgx+rgsQKBgQDilDAAbI4UmI/1cspGVxON8L9CN48255s/VrJGuzpGeWgWCwKCGkyvFvsmcQTbIup5NNBJeuDCUhoihgtanayhzPPZI1CruiG4xEi4UCwzmlGrAGQgJUEg1q33Ssq3htVq2IhED51g3F5GGSG1fm8uXgVA0ESbVsEF5ykSL5CVdQKBgQC3Gk9SZZbFfRZe2ZG1JxvUn/5VzDLQMlvYe+CSzZN/yvTboK1o+Pn4RKcyAHVmNDJGjrRi6K6KO4mXRTT/oI+z+IsyGhuEMPpmQwrdJMZkFsjgCaMn5LbIXXD4KsD5mkngzEcdg3fHNroMSJoJo6wA6QgguyoGxUxjBlCgr1oEXwKBgDhfynmwiMFTHC5juzQ5IiJkzhF5pWZxxczCk2TJ3LwAJQenAZ7BUGcX/rEX2a90RoaVv2Uo75cpulrwow6OwI8ZYWqbQM0ZaJ6CJ93EBNtp//veAwuEeWnkQDCRKVL0d1E4Qt88S1n4OnSSeDKiHKOavQ/dQdSshyYi17MwpnapAoGAbvyuqiZT/r90yOeej6z2DKsycnVFM7fivlVQagvcZXDzfRFnK6MHejq7Ee7/OoVXGDe2FeIusjGmlB/qIS0FDjUQwrzagwVhMo1ez29UJiU9e7e7wxm6xbcGHBdmQ6YHNBD+5l/jQcjlez6iyEcCosaeG6D3t4TuaXPReaTwrocCgYAJa6XNMsHocSZID7hI6U5VKbWVQbV0JO0QzUWSb44Avp81IZnLnH2gYT66TDyuks+oYblvIpMmPl80oxbfUFySzufOl0dswHJdXq93DW0N2W9our7/oblXusg+YKmky6YIo1NdOvl1xymwzeZrWyc9pao+s5B1JWy4HURlfdJm/A==";
    // 支付宝公钥
//    public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt1EU9jfyI6tQnommheZKUl957OZ72QrEas3WQPLT/KBfeb49+EL3XtQOrU/w03dlfBkq30tAOOZw6IGBNVd5wOG+0iH5gOmHF5bBobLs2T44ePPwVgy8LfmihFWfxtdSLTg0h4wUlaqmdm/dvceCUmWqKEuXlqx6LCk1GgJHIA1ugzqIRhoDm00guZit4zw/xBUIiPO/rGL3BIFE/upAqV+aPIsdbW9ZvF4jtQ5Vtwh0xWJY+zPaUb65X6MCdG2omXtFN1/3zx49cU1+98oUHxdJrnju+AwoQiSiB4Xfwry0tjLavg/zQtD24OG6xHR4mzoqDhJzoouPO262PWGbPQIDAQAB";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    //机票订单
    private static final int SDK_PLANE_PAY_FLAG = 3;
    private static final int SDK_PLANE_CHECK_FLAG = 4;


    private Activity mActivity;
    private int getCount = 0;
    private String orderid;
    public Alipay(Activity activity){
        mActivity = activity;
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            System.out.println("支付 -------handleMessage-----what----:"+msg.what);
            switch (msg.what) {
                case 110:
                    // 支付成功要跳回我的订单
                    Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(0, "changeMyorders"); //跳转到我的订单
                    EventBus.getDefault().post(0, "changeMyorder"); //跳转到我的订单
//                    EventBus.getDefault().post(0, Activity_AuctionDetail.TAG_PAYBZJCOMPLETE);
                    break;
                case 111:
                    // 循环结束没成功也要跳回我的账户
                    Toast.makeText(mActivity, "服务器延迟，支付订单正在处理中", Toast.LENGTH_SHORT).show();
                    break;
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                        EventBus.getDefault().post(0, "changeMyorder"); //跳转到我的订单
                        RemoteDataHandler.THREADPOOL.execute(() -> GetServerStatus());//zophar 验证支付是否成功,成功则进入case 110;

                    } else {// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mActivity, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mActivity, "支付已取消!", Toast.LENGTH_SHORT).show();// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        }
                        EventBus.getDefault().post(0, "paySuccesstomyorder");
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(mActivity, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }


                //机票相关
                case 210:
                    // 支付成功要跳回我的机票订单
                    Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(0, "changeMyPlaneOrders"); //跳转到我的机票订单
                    break;
                case 211:
                    // 循环结束没成功也要跳回我的账户
                    Toast.makeText(mActivity, "服务器延迟，支付订单正在处理中", Toast.LENGTH_SHORT).show();
                    break;
                case SDK_PLANE_PAY_FLAG: {

                    System.out.println("支付 -------SDK_PLANE_PAY_FLAG---------:"+SDK_PLANE_PAY_FLAG);

                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签

                    System.out.println("支付 resultInfo"+resultInfo);

                    String resultStatus = payResult.getResultStatus();

                    System.out.println("支付 resultStatus"+resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        System.out.println("支付 9000");
                       EventBus.getDefault().post(0, "changeMyPlaneOrders"); //跳转到我的机票订单
                        RemoteDataHandler.THREADPOOL.execute(() -> GetPlaneOrderServerStatus());//zophar 验证支付是否成功,成功则进入case 210;

                    } else {
                        System.out.println("支付 else");
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            System.out.println("支付 8000");
                            Toast.makeText(mActivity, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("支付 8000 else");
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mActivity, "支付已取消!", Toast.LENGTH_SHORT).show();
                        }
//                        EventBus.getDefault().post(0, "paySuccesstomyorder");
                    }
                    break;
                }
                case SDK_PLANE_CHECK_FLAG: {
                    Toast.makeText(mActivity, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }



                default:
                    break;

            }
        };
    };

    private void GetServerStatus() {
        String url = Constants.URL_ORDER_STATUS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("orderid", orderid);
        String json;
        try {
            json = HttpHelper.post(url, params);
            JSONObject obj = new JSONObject(json);
            String status = obj.getString("status");
            String message = obj.getString("message");
            JSONObject object=obj.getJSONObject("data");
            String orderstatus=object.getString("orderstatus");
            if (orderstatus.equals("1")) {  //已支付
                Message msg = new Message();
                msg.what = 110;
                mHandler.sendMessage(msg);
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getCount < 20) {
                    getCount++;
                    GetServerStatus();
                } else {
                    Message msg = new Message();
                    msg.what = 111;
                    mHandler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Zophar 机票订单状态
    private void GetPlaneOrderServerStatus() {
        System.out.println("支付  GetPlaneOrderServerStatus");
        String url = Constants.URL_PLANE_ORDER_STATUS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("orderid", orderid);
        System.out.println("支付 GetPlaneOrderServerStatus  orderid"+orderid);
        String json;
        try {
            json = HttpHelper.post(url, params);
            System.out.println("支付 json"+json);
            JSONObject obj = new JSONObject(json);
            System.out.println("支付 obj"+obj);
            String status = obj.getString("status");
            String message = obj.getString("message");
            JSONObject object=obj.getJSONObject("data");
            String orderstatus=object.getString("orderstatus");
            if (orderstatus.equals("1")) {  //已支付
                Message msg = new Message();
                msg.what = 210;
                mHandler.sendMessage(msg);
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getCount < 20) {
                    getCount++;
                    GetPlaneOrderServerStatus();
                } else {
                    Message msg = new Message();
                    msg.what = 211;
                    mHandler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean check(){
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA2_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mActivity).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                    mActivity.finish();
                }
            }).show();
            return false;
        }
        return true;
    }

    public void getOrderInfo(String trade_no, String price, String orderId, String commodityName, String commodityMessage){

        if (!this.check()){
            return ;
        }

        orderid = orderId;

        Map<String,String> biz_content = new HashMap<>();
        biz_content.put("subject",commodityName);
        biz_content.put("out_trade_no",trade_no);
        biz_content.put("total_amount",price);
        biz_content.put("product_code","QUICK_MSECURITY_PAY");
        biz_content.put("body",commodityMessage);
        biz_content.put("timeout_express","30m");

        JSONObject jsonObject = new JSONObject(biz_content);
        String s = jsonObject.toString();
        Map<String,String> params = new HashMap<>();
        params.put("app_id",APPID);
        params.put("method","alipay.trade.app.pay");
        params.put("charset","utf-8");
        params.put("sign_type","RSA2");
        params.put("timestamp",getTimestamp());
        params.put("version","1.0");
        params.put("notify_url", Constants.URL_ALIPAY);
        params.put("biz_content",s);

        String orderInfo = buildOrderParam(params);
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        String sign = getSign(params, RSA2_PRIVATE,rsa2);

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&" + sign;
        Schedulers.newThread().createWorker().schedule(() -> pay(payInfo));

    }

    public void getPlaneOrderInfo(String trade_no, String price, String orderId, String commodityName, String commodityMessage){

        System.out.println("支付 trade_no:"+trade_no);
        System.out.println("支付  price:"+price);
        System.out.println("支付  orderId:"+orderId);
        System.out.println("支付  commodityName:"+commodityName);
        if (!this.check()){
            return ;
        }

        orderid = orderId;

        Map<String,String> biz_content = new HashMap<>();
        biz_content.put("subject",commodityName);
        biz_content.put("out_trade_no",trade_no);
        biz_content.put("total_amount",price);
        biz_content.put("product_code","QUICK_MSECURITY_PAY");
        biz_content.put("body",commodityMessage);
        biz_content.put("timeout_express","30m");

        JSONObject jsonObject = new JSONObject(biz_content);
        String s = jsonObject.toString();
        Map<String,String> params = new HashMap<>();
        params.put("app_id",APPID);
        params.put("method","alipay.trade.app.pay");
        params.put("charset","utf-8");
        params.put("sign_type","RSA2");
        params.put("timestamp",getTimestamp());
        params.put("version","1.0");
        params.put("notify_url", Constants.URL_ALIPAY);
        params.put("biz_content",s);

        String orderInfo = buildOrderParam(params);
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        String sign = getSign(params, RSA2_PRIVATE,rsa2);

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&" + sign;
        Schedulers.newThread().createWorker().schedule(() -> planePay(payInfo));

    }

    public void pay(String payInfo){
        PayTask payTask = new PayTask(mActivity);
        String result = payTask.pay(payInfo, true);
        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        mHandler.sendMessage(msg);
    }

    public void planePay(String payInfo){
        System.out.println("支付planePay  payInfo"+payInfo);
        PayTask payTask = new PayTask(mActivity);
        String result = payTask.pay(payInfo, true);
        Message msg = new Message();
        msg.what = SDK_PLANE_PAY_FLAG;
        msg.obj = result;
        System.out.println("支付 what  "+SDK_PLANE_PAY_FLAG);
        mHandler.sendMessage(msg);
    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey,rsa2);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }
}


