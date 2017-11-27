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
import android.widget.TextView;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.NoScrollListView;
import com.qunar.bean.applyRefund.ApplyRefundResponse;
import com.qunar.bean.applyRefund.RefundApplyResult;
import com.qunar.bean.refundSearch.BasePassengerPriceInfo;
import com.qunar.bean.refundSearch.RefundFeeInfo;
import com.qunar.bean.refundSearch.RefundPassengerPriceInfoList;
import com.qunar.model.FlyOrder;
import com.qunar.model.PlaneAppRefundResult;
import com.vlvxing.app.R;

import com.vlvxing.app.common.Constants;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Bind(R.id.refundfee_txt)
    TextView refundfee_txt;//退票需要的手续费
    @Bind(R.id.user_list)
    NoScrollListView user_list;//乘客信息列表

    private FlyOrder orderInfo = new FlyOrder();

    private Context mcontext;

    private List<RefundPassengerPriceInfoList> userIdList = new ArrayList<>();

    private MyAdapter adapter;
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
        refundOrder();
        orderNo.setText(orderInfo.getOrderno());
        adapter = new MyAdapter(mcontext);
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
                submitRefundOrder();
                break;
        }
    }


    /**
     * 退票查询
     */
    private void refundOrder() {
        showDialog("加载中...");
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String,Object> params = new HashMap<>();
        params.put("orderNo",orderInfo.getOrderno());
        RemoteDataHandler.asyncPlaneGet(url+"refundSearch",params,mcontext,new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                System.out.println("退票查询接口 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                JSONObject jsonObject = new JSONObject(json);
                System.out.println("退票查询接口 json:"+json);
                if(jsonObject.getInt("status")==0){
                    ToastUtils.show(mcontext,jsonObject.getString("message"));
                    dismissDialog();
                    return;
                }
                if(jsonObject.getInt("status")==1){

                    if(jsonObject.getString("message").equalsIgnoreCase("SUCCESS")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject1.getJSONArray("baseOrderInfo");

                        JSONObject jsonObject2 = (JSONObject) array.get(0);

//                        String statusDesc = jsonObject2.getString("statusDesc");//出票完成

                        JSONArray jsonArray = jsonObject1.getJSONArray("infoList");

                        System.out.println("退票查询接口 jsonArray.size:"+jsonArray.length());
                        userIdList.clear();
                        for (int i = 0; i< jsonArray.length();i++)
                        {
                            JSONObject info   = (JSONObject)jsonArray.get(i);

                            JSONObject info1 = info.getJSONObject("basePassengerPriceInfo");

                            BasePassengerPriceInfo basePassengerPriceInfo = new BasePassengerPriceInfo();
                            basePassengerPriceInfo.setCardNum(info1.getString("cardNum"));
                            basePassengerPriceInfo.setPassengerId(info1.getInt("passengerId"));
                            basePassengerPriceInfo.setPassengerName(info1.getString("passengerName"));

                            JSONObject info2 = info.getJSONObject("refundFeeInfo");
                            RefundFeeInfo refundFeeInfo = new RefundFeeInfo();
                            refundFeeInfo.setRefundFee(info2.getInt("refundFee"));
                            refundFeeInfo.setReturnRefundFee(info2.getInt("returnRefundFee"));

                            RefundPassengerPriceInfoList list = new RefundPassengerPriceInfoList();
                            list.setBasePassengerPriceInfo(basePassengerPriceInfo);
                            list.setRefundFeeInfo(refundFeeInfo);
                            userIdList.add(list);
                        }
                        System.out.println("退票查询接口 userIdList.size:"+userIdList.size());
                        System.out.println("退票查询接口 getPassengerId:"+userIdList.get(0).getBasePassengerPriceInfo().getPassengerId());
                        System.out.println("退票 basePassengerPriceInfo:"+userIdList.get(0));
                        adapter.notifyDataSetChanged();
                    }else{
                        ToastUtils.show(mcontext, jsonObject.getString("message"));
                        dismissDialog();
                    }
                }else{
                    ToastUtils.show(mcontext, "系统繁忙,请稍后再试");
                    dismissDialog();
                }


                dismissDialog();
            }
        });
    }


    /**
     * 退票申请
     */
    private void submitRefundOrder() {
        showDialog("加载中...");
        String passengerIds = "";
        int returnRefundFee = 0;
        for(int i = 0;i < userIdList.size();i++){
            passengerIds += userIdList.get(i).getBasePassengerPriceInfo().getPassengerId()+",";
            returnRefundFee += userIdList.get(i).getRefundFeeInfo().getReturnRefundFee();
        }
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String,Object> params = new HashMap<>();
        params.put("orderNo",orderInfo.getOrderno());
        params.put("refundCauseId",16);//申请原因
//        params.put("refundCause","旅行");//退票备注
        params.put("passengerIds",passengerIds);//乘机人ID,多个的话","隔开
        params.put("returnRefundFee",returnRefundFee);//应退的钱
//        params.put("callbackUrl","callbackUrl");//退票完成消息回调地址applyRefund
        System.out.println("退票申请接口 passengerIds:"+passengerIds);
        RemoteDataHandler.asyncPlaneGet("http://192.168.1.103:8080/"+"test2",params,mcontext,new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                System.out.println("退票申请接口 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                System.out.println("退票申请接口 json:"+json);
                Gson gson = new Gson();
                PlaneAppRefundResult model = gson.fromJson(json,PlaneAppRefundResult.class);
                int status = model.getStatus();

                if(status==1){
                    ApplyRefundResponse bean = model.getData();
                    int orderStatus = bean.getCode();
                    if(orderStatus ==0){
                        List<ApplyRefundResponse.ResultBean> list = bean.getResult();
                        ApplyRefundResponse.ResultBean.RefundApplyResultBean refundApplyResult = list.get(0).getRefundApplyResult();
                        refundApplyResult.getReason();//当前订单状态不可以退票;退款完成  退票信息
                        list.get(0).getTicketNum();//票号
                        //跳转至退票成功页面
                        if(refundApplyResult.isSuccess()){
                            Intent intent = new Intent(mcontext,PlaneRefundSuccessActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            ToastUtils.show(mcontext,refundApplyResult.getReason().toString());
                        }

                    }else{
                        dismissDialog();
                        ToastUtils.show(mcontext, "当前订单状态不可以退票");
                    }

                }else{
                    ToastUtils.show(mcontext, model.getMessage());
                }
                dismissDialog();
            }
        });
    }


    public final class ViewHolder {
        public TextView name;
        public TextView price;
    }




    //乘机人列表
    class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater mInflater;
        public MyAdapter(Context context) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return userIdList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return userIdList.get(arg0);
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
            BasePassengerPriceInfo basePassengerPriceInfo = userIdList.get(position).getBasePassengerPriceInfo();
            RefundFeeInfo refundFeeInfo = userIdList.get(position).getRefundFeeInfo();

            holder.name.setText(basePassengerPriceInfo.getPassengerName());
            holder.price.setText((refundFeeInfo.getReturnRefundFee())+"元");
            System.out.println("退票 basePassengerPriceInfo:"+basePassengerPriceInfo);
            return convertView;
        }
    }

}
