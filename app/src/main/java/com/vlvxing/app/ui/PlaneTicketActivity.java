package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.handongkeji.selecity.PlaneSelestorCityActivity;

import com.handongkeji.ui.BaseActivity;

import com.vlvxing.app.R;

import com.vlvxing.app.lib.CalendarSelectorActivity;


import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 去哪儿网机票
 */

public class PlaneTicketActivity extends BaseActivity{

    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;//単程、往返的父控件
    @Bind(R.id.left_radio_btn)
    RadioButton leftBtn;//単程
    @Bind(R.id.right_radio_btn)
    RadioButton rightBtn;//往返
    @Bind(R.id.view_left)
    View viewLeft;//类似背景选择器 左
    @Bind(R.id.view_right)
    View viewRight;//类似背景选择器 右
    @Bind(R.id.city_txt_left)
    TextView cityLefttxt;//出发城市
    @Bind(R.id.city_txt_right)
    TextView cityRighttxt;//到达城市
    @Bind(R.id.txt_date)
    TextView txtDate;//日期
    @Bind(R.id.txt_day)
    TextView txtDay;//具体哪天
    @Bind(R.id.search)
    Button search;//搜索
    @Bind(R.id.go_or_come)
    LinearLayout imgBtn;//切换城市
    @Bind(R.id.bottom_left_btn)
    Button bottomLeftBtn;
    @Bind(R.id.bottom_right_btn)
    Button bottomGightBtn;
    private Context mcontext;
    private String dateFormat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_tricket);
        ButterKnife.bind(this);
        headTitle.setText("机票");
        mcontext = this;
        txtDate.setText(getNowDate());
        radioGroupOnCheckChange();//注册単程、返程的选择事件
//        String city = MyApp.getInstance().getCity_name();
//        if (!StringUtils.isStringNull(city)) {
//            cityLefttxt.setText(city);
//        }

    }

    //获取当前日期
    private String getNowDate(){
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
//        mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
//        mHour = c.get(Calendar.HOUR_OF_DAY);//时
//        mMinute = c.get(Calendar.MINUTE);//分
        String date = mMonth+"月"+mDay+"日";
        dateFormat = mYear+"-"+mMonth+"-"+mDay;
        return date;
    }

    /**
     * 単程、往返的父容器选择状态的事件监听
     */
    private void radioGroupOnCheckChange(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(R.id.left_radio_btn == checkedId){
                    //単程
                    viewLeft.setVisibility(View.VISIBLE);
                    viewRight.setVisibility(View.INVISIBLE);
                }

                if(R.id.right_radio_btn == checkedId){
                    //往返
                    viewLeft.setVisibility(View.INVISIBLE);
                    viewRight.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.return_lin,R.id.city_txt_left,R.id.city_txt_right,R.id.txt_date,R.id.search,R.id.go_or_come,R.id.bottom_left_btn,R.id.bottom_right_btn})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.return_lin:
                finish();
                break;
            case R.id.city_txt_left:
                //出发城市
                Intent cityGo = new Intent(mcontext, PlaneSelestorCityActivity.class);
                cityGo.putExtra("type", 1);
                startActivityForResult(cityGo, 1);//日历展示页面
                break;
            case R.id.city_txt_right:
                //到达城市
                Intent cityArrive = new Intent(mcontext, PlaneSelestorCityActivity.class);
                cityArrive.putExtra("type", 2);
                startActivityForResult(cityArrive, 2);//日历展示页面
                break;
            case R.id.txt_date:
                Intent i = new Intent(mcontext, CalendarSelectorActivity.class);
                i.putExtra(CalendarSelectorActivity.DAYS_OF_SELECT, 60);
                i.putExtra(CalendarSelectorActivity.ORDER_DAY, "20170919");
                startActivityForResult(i, 3);//日历展示页面
                break;
            case R.id.search:
                String goCity = cityLefttxt.getText().toString();
                String arriveCity = cityRighttxt.getText().toString();
//                String date = txtDate.getText().toString();

                if (goCity != null && arriveCity != null && dateFormat != null){
                Intent searchIntent = new Intent(mcontext, PlaneSearchActivity.class);
                searchIntent.putExtra("goCity", goCity);
                searchIntent.putExtra("arriveCity", arriveCity);
                searchIntent.putExtra("date", dateFormat);

//                startActivityForResult(searchIntent, 4);//横向日期选择并展示车票列表
                startActivity(searchIntent);//横向日期选择并展示车票列表
                }else{
                    Toast.makeText(mcontext, " 请选择出发城市和到达城市,以及出发日期!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_or_come:
                //切换城市
                String left = cityLefttxt.getText().toString();
                String right = cityRighttxt.getText().toString();
                cityLefttxt.setText(right);
                cityRighttxt.setText(left);
                break;
            case R.id.bottom_left_btn:
                Intent intent = new Intent(mcontext,PlaneOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_right_btn:

                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK) {
            if (data!=null){
            //日期选择
                 dateFormat = data.getStringExtra(CalendarSelectorActivity.ORDER_DAY).replaceAll("#", "-");

                Toast.makeText(mcontext, " dateFormat!"+dateFormat, Toast.LENGTH_SHORT).show();
            String orderInfo = data.getStringExtra(CalendarSelectorActivity.ORDER_DAY);
                if(orderInfo!=null){
                    String[] all = orderInfo.split("#");
                    txtDate.setText(all[1]+"月"+all[2]+"日");
                }
            String days = data.getStringExtra("days");
                if(days!=null){
                    txtDay.setVisibility(View.VISIBLE);
                    txtDay.setText(days);
                }else{
                    txtDay.setVisibility(View.INVISIBLE);
                }


            }
			 //*****注意*****
			// 如需转换为Calendar
			// 正确转换方法（因为2月没有30天）：
//			String[] info = orderInfo.split("#");
//			Calendar c = Calendar.getInstance();
//			c.set(Integer.valueOf(info[0]), Integer.valueOf(info[1]) - 1, Integer.valueOf(info[2]));
			// 错误转换方法：
//			c.set(Integer.valueOf(info[0]), Integer.valueOf(info[1]), Integer.valueOf(info[2]));
//			c.add(Calendar.MONTH, -1);

        }else if(requestCode == 1 && resultCode == RESULT_OK){
            //出发城市
            if (data!=null){
            String cityName = data.getStringExtra("name");
//            String locationId = data.getStringExtra("locationId");
            cityLefttxt.setText(cityName);
            }
        }else if(requestCode == 2 && resultCode == RESULT_OK){
            //到达城市
            if (data!=null){
            String cityName = data.getStringExtra("name");
//            String locationId = data.getStringExtra("locationId");
            cityRighttxt.setText(cityName);
            }
        }
    }
}
