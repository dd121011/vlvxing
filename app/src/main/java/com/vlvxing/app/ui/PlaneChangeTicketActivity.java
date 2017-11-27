package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.selecity.PlaneSelestorCityActivity;
import com.handongkeji.ui.BaseActivity;
import com.qunar.model.FlyOrder;
import com.qunar.model.PlaneRefundSerachResult;
import com.vlvxing.app.R;
import com.vlvxing.app.lib.CalendarSelectorActivity;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.SharedPrefsUtil;
import com.vlvxing.app.utils.ToastUtils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 改签
 */

public class PlaneChangeTicketActivity extends BaseActivity{

    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.date_txt)
    TextView date_txt;//日期
    @Bind(R.id.city_txt)
    TextView city_txt;//城市
    @Bind(R.id.city_lin)
    RelativeLayout cityLin;
    @Bind(R.id.date_lin)
    RelativeLayout dateLin;
    @Bind(R.id.month_txt)
    TextView month_txt;//周几
    private FlyOrder orderInfo = new FlyOrder();
    private Context mcontext;
    private String dateFormat = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_change_ticket);
        ButterKnife.bind(this);
        mcontext = this;
        orderInfo = (FlyOrder) getIntent().getSerializableExtra("orderInfo");
        if(orderInfo!=null){
            initData();
        }else{
            ToastUtils.show(mcontext,"该订单不存在");
        }
        headTitle.setText("改签");
    }

    private void initData(){
        String[] split =   orderInfo.getDeptdate().split("-");
        date_txt.setText(split[1]+"月"+split[2]+"日");//起飞日期
        city_txt.setText(orderInfo.getDeptcity());
        month_txt.setVisibility(View.VISIBLE);
        month_txt.setText(DataUtils.getWeek(orderInfo.getDeptdate()));
    }


    @OnClick({R.id.return_lin, R.id.change_btn,R.id.city_lin,R.id.date_lin,R.id.change_info_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.change_btn:
                //确认改签
                Intent searchIntent = new Intent(mcontext,PlaneChangeSearchActivity.class);
                searchIntent.putExtra("orderNo", orderInfo.getOrderno());
                searchIntent.putExtra("arriCity",orderInfo.getArricity());
                searchIntent.putExtra("deptCity",orderInfo.getDeptcity());
                if(dateFormat==null){
                    searchIntent.putExtra("changeDate", orderInfo.getDeptdate());
                }else{
                    searchIntent.putExtra("changeDate", dateFormat);
                }
//                startActivityForResult(searchIntent, 4);//横向日期选择并展示车票列表
                startActivity(searchIntent);//横向日期选择并展示改签车票列表
                break;
            case R.id.change_info_lin:
                //改签说明
                Intent intent = new Intent(mcontext,PlaneChangeInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.city_lin:
                //到达城市
//                Intent cityArrive = new Intent(mcontext, PlaneSelestorCityActivity.class);
//                cityArrive.putExtra("type", 5);
//                startActivityForResult(cityArrive, 5);//到达城市选择
                break;
            case R.id.date_lin:
                //购票日期选择
                Intent i = new Intent(mcontext, CalendarSelectorActivity.class);
                i.putExtra(CalendarSelectorActivity.DAYS_OF_SELECT, 120);
                i.putExtra(CalendarSelectorActivity.ORDER_DAY, dateFormat);
                startActivityForResult(i, 4);//日历展示页面
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == RESULT_OK) {
            if (data!=null){
                //日期选择
                dateFormat = data.getStringExtra(CalendarSelectorActivity.ORDER_DAY).replaceAll("#", "-");
                String orderInfo = data.getStringExtra(CalendarSelectorActivity.ORDER_DAY);
                if(orderInfo!=null){
                    String[] all = orderInfo.split("#");
                    date_txt.setText(all[1]+"月"+all[2]+"日");
                }
                String days = data.getStringExtra("days");
                if(days!=null){
                    month_txt.setVisibility(View.VISIBLE);
                    month_txt.setText(days);
                }else{
                    month_txt.setVisibility(View.INVISIBLE);
                }
            }
        }else if(requestCode == 5 && resultCode == RESULT_OK){
            //出发城市
            if (data!=null){
                String cityName = data.getStringExtra("name").toString().trim();
//            String locationId = data.getStringExtra("locationId");
                city_txt.setText(cityName);
                SharedPrefsUtil.putValue(mcontext,PlaneTicketActivity.PLANE_HISTORY_CITY,cityName);
            }
        }
    }
}
