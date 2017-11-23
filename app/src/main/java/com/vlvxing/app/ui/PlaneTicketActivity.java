package com.vlvxing.app.ui;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.PlaneSelestorCityActivity;

import com.handongkeji.ui.BaseActivity;

import com.handongkeji.ui.BrowseActivity;
import com.handongkeji.utils.StringUtils;
import com.qunar.bean.SearchFlightRequest;
import com.qunar.service.RequestService;
import com.sivin.Banner;
import com.sivin.BannerAdapter;
import com.vlvxing.app.R;

import com.vlvxing.app.adapter.DialogFourAdapter;
import com.vlvxing.app.adapter.DialogThreeAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.lib.CalendarSelectorActivity;
import com.vlvxing.app.model.SysadModel;
import com.vlvxing.app.utils.SharedPrefsUtil;
import com.vlvxing.app.utils.ToastUtils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 去哪儿网机票
 */

public class PlaneTicketActivity extends BaseActivity{
    public static final String PLANE_HISTORY_CITY = "plane_history_city";
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
    @Bind(R.id.left_city_lin)
    LinearLayout leftCityLin;//左边
    @Bind(R.id.right_city_lin)
    LinearLayout rightCityLin;//左边
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
    @Bind(R.id.date_right_lin)
    LinearLayout dateRightLin;//返程票 日期布局
    @Bind(R.id.txt_date_right)
    TextView dateRight;//返程日期
    @Bind(R.id.txt_day_right)
    TextView dayRight;//代表周几 或者今明后哪天
    private Context mcontext;
    private String dateFormat = null;
    private int flag = 1;//为1时代表単程 2时往返
    private Dialog vDialog;
    @Bind(R.id.plane_pager)
    Banner publicPager;//轮播
    //轮播图数据源
    private List<SysadModel.DataBean> img_list = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_tricket);
        ButterKnife.bind(this);
        headTitle.setText("机票");
        mcontext = this;
        txtDate.setText(getNowDate());
        radioGroupOnCheckChange();//注册単程、返程的选择事件
        if(MyApp.getInstance().getCity_name().equals("")||MyApp.getInstance().getCity_name()==null){
            cityLefttxt.setText("北京");
        }else{
            cityLefttxt.setText(MyApp.getInstance().getCity_name());
        }
        getBananer();
        int img_width = MyApp.getInstance().getScreenWidth();
        ViewGroup.LayoutParams params = publicPager.getLayoutParams();
        params.height = (img_width * 150) / 375; // 750:500
        publicPager.setLayoutParams(params);
        initBanner();
//        String city = MyApp.getInstance().getCity_name();
//        if (!StringUtils.isStringNull(city)) {
//            cityLefttxt.setText(city);
//        }
    }
    private void initBanner(){
        publicPager.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SysadModel.DataBean bean = img_list.get(position);
                String adcontents = bean.getAdcontents();// url
                String adtype = bean.getAdtype();
//                if ("1".equals(adtype)) { //跳新闻详情
////                    startActivity(new Intent(mcontext, RenZAndPersonInfoActivity.class).putExtra("newid",adcontents));
//                } else if ("2".equals(adtype)) {
                Intent intent = new Intent(mcontext, BrowseActivity.class);
                intent.putExtra("url", adcontents);
                startActivity(intent);
//                } else if ("3".equals(adtype)) {
//
//                }
            }
        });
    }

    //获取bananer图
    public void getBananer() {
        String url = Constants.URL_SYSAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("categoryId", "0"); //分类id(0:首页，1国内，2国外  3附近)
        RemoteDataHandler.asyncTokenPost(url, mcontext, true, params, new RemoteDataHandler.Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        String json = data.getJson();
                        if (StringUtils.isStringNull(json)) {
                            return;
                        }
                        Gson gson = new Gson();
                        SysadModel model = gson.fromJson(json, SysadModel.class);
                        final int status = model.getStatus();
                        if (status == 1) {
                            img_list = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                bannerData.add(model.getData().get(i).getAdpicture());
                            }
                            setBannerData();
                        } else {
//                            ToastUtils.show(getContext(), model.getMessage());
                        }
                    }
                }
        );
    }

    /**
     * 设置bananer图
     */
    private void setBannerData() {
//        publicPager.setIndicatorGravity(BannerConfig.RIGHT);
//        publicPager.setImages(bannerData).
//       setImageLoader(new GlideImageLoader()).isAutoPlay(true).setDelayTime(1000)
//                .start();

        publicPager.setBannerAdapter(new BannerAdapter<String>(bannerData) {
            @Override
            protected void bindTips(TextView tv, String o) {

            }
            @Override
            public void bindImage(ImageView imageView, String o) {
                Glide.with(mcontext).load(o).into(imageView);
            }
        });
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
                    flag = 1;
                    dateRightLin.setVisibility(View.INVISIBLE);
                }

                if(R.id.right_radio_btn == checkedId){
                    //往返
                    viewLeft.setVisibility(View.INVISIBLE);
                    viewRight.setVisibility(View.VISIBLE);
                    flag = 2;
                    dateRightLin.setVisibility(View.VISIBLE);
                    dateRight.setText(getNowDate());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.return_lin,R.id.city_txt_left,R.id.city_txt_right,R.id.search,R.id.go_or_come,R.id.bottom_left_btn,R.id.bottom_right_btn,R.id.date_lin})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.return_lin:
                finish();
                break;
            case R.id.city_txt_left:
                //出发城市
                Intent cityGo = new Intent(mcontext, PlaneSelestorCityActivity.class);
                cityGo.putExtra("type", 1);
                startActivityForResult(cityGo, 1);//出发城市选择
                break;
            case R.id.city_txt_right:
                //到达城市
                Intent cityArrive = new Intent(mcontext, PlaneSelestorCityActivity.class);
                cityArrive.putExtra("type", 2);
                startActivityForResult(cityArrive, 2);//到达城市选择
                break;
            case R.id.date_lin:
                //购票日期选择
                Intent i = new Intent(mcontext, CalendarSelectorActivity.class);
                i.putExtra(CalendarSelectorActivity.DAYS_OF_SELECT, 120);
                i.putExtra(CalendarSelectorActivity.ORDER_DAY, dateFormat);
                startActivityForResult(i, 3);//日历展示页面
                break;

            case R.id.search:
                String goCity = cityLefttxt.getText().toString();
                String arriveCity = cityRighttxt.getText().toString();
//                String date = txtDate.getText().toString();
                if (goCity != null && !goCity.equals("") && arriveCity != null && !arriveCity.equals("")&& dateFormat != null && !dateFormat.equals("")){
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
                Animation animationLeft = AnimationUtils.loadAnimation(this, R.anim.plane_translate_left);//加载Xml文件中的动画imgShow.startAnimation(scaleAnimation2);
                leftCityLin.startAnimation(animationLeft);//开始动画

                Animation animationRight = AnimationUtils.loadAnimation(this, R.anim.plane_translate_right);//加载Xml文件中的动画imgShow.startAnimation(scaleAnimation2);
                rightCityLin.startAnimation(animationRight);//开始动画

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
                //v行无忧
                showDialog();
                break;
        }
    }
    private void showDialog() {
        vDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.plane_vxingwuyou_dialog, null);
//        ListView listview = (ListView) contentView.findViewById(R.id.listview);//展示UI容器 body中listview
//        ImageView close = (ImageView) contentView.findViewById(R.id.close);//关闭

        vDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
        vDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        vDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        vDialog.setCanceledOnTouchOutside(true);
        vDialog.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK) {
            if (data!=null){
            //日期选择
                dateFormat = data.getStringExtra(CalendarSelectorActivity.ORDER_DAY).replaceAll("#", "-");
//                Toast.makeText(mcontext, " dateFormat!"+dateFormat, Toast.LENGTH_SHORT).show();
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
        }else if(requestCode == 1 && resultCode == RESULT_OK){
            //出发城市
            if (data!=null){
            String cityName = data.getStringExtra("name").toString().trim();
//            String locationId = data.getStringExtra("locationId");
            cityLefttxt.setText(cityName);
                SharedPrefsUtil.putValue(mcontext,PLANE_HISTORY_CITY,cityName);
            }
        }else if(requestCode == 2 && resultCode == RESULT_OK){
            //到达城市
            if (data!=null){
            String cityName = data.getStringExtra("name").toString().trim();
//            String locationId = data.getStringExtra("locationId");
            cityRighttxt.setText(cityName);
                SharedPrefsUtil.putValue(mcontext,PLANE_HISTORY_CITY,cityName);
            }
        }
    }
}
