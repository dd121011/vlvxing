package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.StringUtils;
import com.qunar.bean.SearchFlightResponse;
import com.qunar.bean.SearchQuoteResponse;
import com.qunar.bean.Vendor;
import com.qunar.model.PlaneDetailsResult;
import com.qunar.model.PlaneResult;
import com.vlvxing.app.R;
import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/22.
 */

public class PlaneDetailsActivity extends BaseActivity{

    @Bind(R.id.head_title_left)
    TextView headTitleLeft;//标题
    @Bind(R.id.head_title_right)
    TextView headTitleRight;//标题右
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.details_withdrawal_txt)
    TextView detailsWithDrawal;//退改详情
    @Bind(R.id.book)
    Button book;//预订
    @Bind(R.id.com_code)
    TextView com_code;//航班全名
    @Bind(R.id.dep_airport)
    TextView dep_airport;//到达机场
    @Bind(R.id.arr_airport)
    TextView arr_airport;//到达机场

    @Bind(R.id.b_time)
    TextView b_time;//起飞时间
    @Bind(R.id.e_time)
    TextView e_time;//到达时间
    @Bind(R.id.correct)
    TextView correct;//准点率
    @Bind(R.id.meal)
    TextView meal;//有无餐食
    @Bind(R.id.price_txt)
    TextView price_txt;//价格
    @Bind(R.id.plane_style)
    TextView plane_style;//机型
    @Bind(R.id.flight_times)
    TextView flight_times;//飞行时间
    @Bind(R.id.date)
    TextView date_txt;//xx月xx日

    private Context mcontext;
    private String goCity;
    private String arriveCity;
    private String date;
    private String flightNum;
    private String planeStyle;
    private String flightTimes;
    private Vendor vendor ;
    private String com;
    private String code;
    private String bTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_details);
        ButterKnife.bind(this);
        mcontext = this;
        goCity = getIntent().getStringExtra("goCity");//出发城市
        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
        date = getIntent().getStringExtra("date");//出发日期
        flightNum = getIntent().getStringExtra("flightNum");//航班号
        planeStyle = getIntent().getStringExtra("planeStyle");//机型
        flightTimes = getIntent().getStringExtra("flightTimes");//飞行时间

        headTitleLeft.setText(goCity);
        headTitleRight.setText(arriveCity);
        initData();

    }


    @OnClick({R.id.return_lin, R.id.right_txt,R.id.details_withdrawal_txt,R.id.book})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.details_withdrawal_txt:
                //退改详情
                break;
            case R.id.book:
                //预订
                Intent intent = new Intent(mcontext,PlaneBuyDetailsActivity.class);
                intent.putExtra("goCity",goCity);//出发城市
                intent.putExtra("arriveCity",arriveCity);//到达城市
                intent.putExtra("date",date);//出发日期
                intent.putExtra("com",com);//航班号
                intent.putExtra("code",code);//航司
                intent.putExtra("bTime","bTime");//起飞时间
                Bundle bundle = new Bundle();
//                bundle.putSerializable("vendor",vendor);
//                intent.putExtras(bundle);
//                startActivity(intent);
                break;

        }
    }
    private void initData() {
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String,Object> params = new HashMap<>();
        params.put("dptCity",goCity);
        params.put("arrCity",arriveCity);
        params.put("date",date);
        params.put("ex_track","youxuan");
        params.put("flightNum",flightNum);

        showDialog("加载中...");
        RemoteDataHandler.asyncPlaneGet(url+"searchQuote",params,mcontext,new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                System.out.println("机票接口详情 接口回调 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                System.out.println("机票接口详情 json:"+json);
                Gson gson = new Gson();
                PlaneDetailsResult model = gson.fromJson(json,PlaneDetailsResult.class);
                int status = model.getStatus();
                if(status==1){

                    //-1是空  -2是错误的
                    SearchQuoteResponse response = model.getData();
                    SearchQuoteResponse.Result result = response.getResult();
                    if(result==null){
                        ToastUtils.show(mcontext, "航班信息异常");
                            return;
                    }
                    com = result.getCom();//航班公司
                    code = result.getCode();//航班号
                    com_code.setText(com+code);

                    String depAirport = result.getDepAirport();//出发机场
                    dep_airport.setText(depAirport);

                    String arrAirport = result.getArrAirport();//到达机场
                    arr_airport.setText(arrAirport);

                    String correctStr = result.getCorrect();//准点率
                    correct.setText(correctStr);

                    bTime = result.getBtime();//出发时间
                    b_time.setText(bTime);
                    String eTime = result.getEtime();//到达时间
                    e_time.setText(eTime);

                    String mealStr = result.getMeal();//true表示有餐食
                    if (mealStr.equals("true")){
                        meal.setText("有餐食");
                    }else{
                        meal.setText("无餐食");
                    }

                    plane_style.setText(planeStyle);//机型
                    flight_times.setText(flightTimes);//飞行时间
                    String dateStr = result.getDate();
                    String weekStr = DataUtils.getWeek(dateStr);
                    String dateArr[] = dateStr.split("-");
                    date_txt.setText(dateArr[1]+"月"+dateArr[2]+"日"+weekStr);

                    List<Vendor> vendors = result.getVendors();
                    System.out.println("机票接口详情  vendors"+vendors.size());
                    if(vendors.size()>0){
                        vendor = vendors.get(0);
                        System.out.print("机票接口详情  vendor"+vendor);
                        String barePrice = vendor.getBarePrice()+"";//销售特价
                        String vppr =  vendor.getVppr()+"";//票面价
                        System.out.println("价格销售特价"+barePrice);
                        System.out.println("价格票面价"+vppr);
                        price_txt.setText("￥ "+barePrice);
//                        body_list.setVisibility(View.VISIBLE);
//                        mData.clear();
//                        mData.addAll(info);
//                        adapter.notifyDataSetChanged();
                    }else{
//                        body_list.setVisibility(View.INVISIBLE);
//                        mData.clear();
//                        adapter.notifyDataSetChanged();
                        ToastUtils.show(mcontext, "航班信息异常");
                    }

                }else{
                    ToastUtils.show(mcontext, model.getMessage());
                }
                dismissDialog();
            }
        });
    }
}
