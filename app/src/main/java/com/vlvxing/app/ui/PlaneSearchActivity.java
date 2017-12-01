package com.vlvxing.app.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.lidroid.xutils.db.annotation.Check;
import com.qunar.bean.SearchFlightRequest;
import com.qunar.bean.SearchFlightResponse;
import com.qunar.model.PlaneResult;
import com.qunar.service.RequestService;
import com.sivin.Banner;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.DialogFourAdapter;
import com.vlvxing.app.adapter.DialogThreeAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.model.PlaneBottonDialogThreeModel;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;


public class PlaneSearchActivity extends BaseActivity {


    @Bind(R.id.head_title_left)
    TextView headTitleLeft;//标题
    @Bind(R.id.head_title_right)
    TextView headTitleRight;//标题右
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.screenBtn)
    Button screenBtn;//筛选按钮

    @Bind(R.id.left_checkbox)
    CheckBox leftCheckBox;
    @Bind(R.id.right_checkbox)
    CheckBox rightCheckBox;
    @Bind(R.id.left_check_txt)
    TextView leftCheckTxt;
    @Bind(R.id.right_check_txt)
    TextView rightCheckTxt;

    private HorizontalCalendar horizontalCalendar;
    private ListView body_list;
    private MyAdapter adapter;
    private boolean isLoadMore;//是否已经在加载更多
    private int currentPage = 1;
    private int pageSize = 5;
    private List<SearchFlightResponse.FlightInfo> mData = new ArrayList<>();
    private Context mcontext;
    private String goCity;
    private String arriveCity;
    private String date;
    private Dialog bottomDialog;
    private String strFirst = "";//直飞经停
    private String strSecond = "";//起飞时段
    private String strThree = "";//记录航空公司中用户选择了哪些航空公司
    private String strFourQifei = "";
    private String strFourJiangluo = "";
    private HashMap<String, Boolean> mapSecond;//记录起飞时刻中用户选择了哪些航班时段
    //航空公司
    private List<PlaneBottonDialogThreeModel> bottomDialogThreeData;//航空公司
//    private HashMap<Integer, Boolean> lCurrentItem;//航空公司listview 有哪些item是被选中的

    //直飞经停
    private Boolean firstResult = false;

    //机场落地
    private List<PlaneBottonDialogThreeModel> bottomDialogFourQifeiData;//航空公司 起飞机场
    private List<PlaneBottonDialogThreeModel> bottomDialogFourJiangluoData;//航空公司 降落机场

    private HashMap<String, Boolean> firstHashMap = new HashMap<>();//起飞机场 记录用户选择的所有机场
    private HashMap<String, Boolean> secondHashMap = new HashMap<>();
    ;//降落机场 记录用户选择的所有机场
    //接口条件
    private HashMap<String, Object> params;//查询航班条件参数
    private String dateResult = "";
    private String sort = "1";//1时间+  2价格+  3飞行时间 -1(-)  -2(-)  -3(-)

    private HashMap<String, String> hsThreeData = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_horizontal);
        ButterKnife.bind(this);
        goCity = getIntent().getStringExtra("goCity").trim();//出发城市
        arriveCity = getIntent().getStringExtra("arriveCity").trim();//到达城市
        date = getIntent().getStringExtra("date").trim();//出发日期
        arrToString();
        mcontext = this;
        params = new HashMap<>();//查询航班条件参数
        //底部筛选弹出框
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        //获取上个页面,用户选择的日期,并转换成日历对象,设置横向日历默认选中的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        headTitleLeft.setText(goCity);
        headTitleRight.setText(arriveCity);
        body_list = (ListView) findViewById(R.id.body_list);
        body_list.setVisibility(View.INVISIBLE);//默认机票列表不显示
        /** 一个月后结束  1 */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 4);//设置购票日期暂时只能在3个月内

        /** 从现在起1个月前开始   -1*/
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        //默认选中位置
        final Calendar defaultDate = Calendar.getInstance();
        defaultDate.add(Calendar.MONTH, 0);//日历起始设置 当前月数 - + 都可实现
        defaultDate.add(Calendar.DAY_OF_WEEK, 0);//日历起始设置  当前天数-  + 都可实现
        //以参数的形式设置 字体颜色

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())//开始日期
                .endDate(endDate.getTime())//结束日期
                .datesNumberOnScreen(5)//一页显示5个
                .dayNameFormat("EEE")//日
                .dayNumberFormat("dd")//月
                .monthFormat("MMM")//年
                .showDayName(true)//显示日
                .showMonthName(true)//显示月
//                .defaultSelectedDate(defaultDate.getTime())//默认选中的是
                .defaultSelectedDate(calendar.getTime())
                .textColor(Color.WHITE, Color.parseColor("#ea5413"))//字体颜色（正常，选中）
                .selectedDateBackground(Color.TRANSPARENT)//item背景色
                .backgroundColor(Color.TRANSPARENT, Color.parseColor("#FFFFFF"))//背景选择 (正常，选中)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date d, int position) {
                date = DateFormat.getDateInstance().format(d).toString();
//                System.out.println("时间转换 :onDateSelected date"+date);
                arrToString();
                initData();
            }
        });
        initData();
        adapter = new MyAdapter(this);
        body_list.setAdapter(adapter);

        body_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intnet = new Intent(mcontext, PlaneDetailsActivity.class);
                if (goCity.equals("")) {
                    ToastUtils.show(mcontext, "请选择出发城市");
                    return;
                }
                if (arriveCity.equals("")) {
                    ToastUtils.show(mcontext, "请选择到达城市");
                    return;
                }
                intnet.putExtra("goCity", goCity);//出发城市
                intnet.putExtra("arriveCity", arriveCity);//到达城市
                intnet.putExtra("date", dateResult);//出发日期
                intnet.putExtra("flightNum", mData.get(position).getFlightNum());//航班号
                intnet.putExtra("planeStyle", mData.get(position).getFlightTypeFullName());//出发日期
                intnet.putExtra("flightTimes", mData.get(position).getFlightTimes());//飞行时间
                startActivity(intnet);
            }
        });
        setOnChecked();//底部导航栏 时间,价格排序

        clearSecondAllChecked();//初始化筛选中的起飞时段

    }

    //时间,价格排序
    private void setOnChecked() {
        leftCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sort = "-1";
                } else {
                    sort = "1";
                }
                initData();
            }
        });

        rightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sort = "-2";
                } else {
                    sort = "2";
                }
                initData();
            }
        });
    }

    /**
     * date字符串 xxxx-x-x转换 xxxx-xx-xx
     */
    private void arrToString() {
        if (date.indexOf("年") != -1) {//包含年
            date = date.replace('年', '-');
            date = date.replace('月', '-');
            date = date.substring(0, date.length() - 1);
        }
        String[] dateStr = date.split("-");
        if (dateStr[1].length() == 1) {
            dateStr[1] = 0 + dateStr[1];
        }
        if (dateStr[2].length() == 1) {
            dateStr[2] = 0 + dateStr[2];
        }
        dateResult = dateStr[0] + "-" + dateStr[1] + "-" + dateStr[2];
    }

    private void initData() {
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;

        params.put("dptCity", goCity);
        params.put("arrCity", arriveCity);
        params.put("date", dateResult);
        params.put("ex_track", "youxuan");
        params.put("dptFlyTime", strSecond);
        params.put("sort", sort); //1时间+  2价格+   -1(-)  -2(-)
        params.put("airlineName", strThree);//航空公司
        params.put("dptAirpot", strFourQifei);//出发机场
        params.put("arriAirpot", strFourJiangluo);//到达机场
        if (firstResult) {
            params.put("stop", "true");
        } else {
            params.put("stop", "false");
        }
        showDialog("加载中...");
        RemoteDataHandler.asyncPlaneGet(url + "searchFlight", params, mcontext, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                Gson gson = new Gson();
                PlaneResult model = gson.fromJson(json, PlaneResult.class);
                int status = model.getStatus();
                if (status == 1) {

                    //-1是空  -2是错误的
                    SearchFlightResponse response = model.getData();
                    SearchFlightResponse.Result result = response.getResult();

                    Map<String, String> companySet = result.getAirlineMap();//航空公司
                    //航空公司list数据绑定,访问一次重置一次
                    initThreeData(companySet);


                    //获取到航空公司之后,设置每个item默认未选中(目的是 更新航空公司集合的长度)
                    clearThreeAllChecked();


                    Set<String> qifeiSet = result.getDptAirportSet();//起飞机场
                    Set<String> jiangluoSet = result.getArrAirportSet();//降落机场
                    //机场落地 list数据绑定
                    initFourData(qifeiSet, jiangluoSet);
                    //起飞时段 记录了哪些航班
//                    clearSecondAllChecked();//每次查询新数据,都需要重置用户的选中状态

                    List<SearchFlightResponse.FlightInfo> info = result.getFlightInfos();
                    if (info.size() > 0) {
                        body_list.setVisibility(View.VISIBLE);
                        mData.clear();
                        mData.addAll(info);
                        adapter.notifyDataSetChanged();
                    } else {
                        body_list.setVisibility(View.INVISIBLE);
                        mData.clear();
                        adapter.notifyDataSetChanged();
                        ToastUtils.show(mcontext, "暂无航班");
                    }

                } else {
                    body_list.setVisibility(View.INVISIBLE);
                    ToastUtils.show(mcontext, "暂无航班");
                }
                dismissDialog();

            }
        });
    }

    //直飞
    private void clearFirstAllChecked() {
        firstResult = false;
    }

    //起飞时段 初始化(重置)用户选中状态
    private void clearSecondAllChecked() {
        //起飞时段 记录了哪些航班
        mapSecond = new HashMap<>();
        mapSecond.put("first", false);
        mapSecond.put("second", false);
        mapSecond.put("three", false);
        mapSecond.put("four", false);
        strSecond = "";
    }

    //航空公司list数据绑定
    private void initThreeData(Map<String, String> companyMap) {
        bottomDialogThreeData = getDialogThreeData(companyMap);
        strThree = "";
    }

    //航空公司  重置用户选中状态
    private void clearThreeAllChecked() {
        //默认都是未被选中的状态
        for (int i = 0; i < bottomDialogThreeData.size(); i++) {
            bottomDialogThreeData.get(i).setBo(false);
        }

        strThree = "";

    }

    //机场起飞,落地list数据绑定
    private void initFourData(Set<String> qifeiSet, Set<String> jiangluoSet) {
        bottomDialogFourQifeiData = getDialogFourQifeiData(qifeiSet);
        bottomDialogFourJiangluoData = getDialogFourJiangluoData(jiangluoSet);

    }

    //初始化;清理; 起飞机场 降落机场的选中状态
    private void clearFourAllChecked() {
        //起飞机场   默认填充所有的为false
        firstHashMap = new HashMap<String, Boolean>();
        for (int i = 0; i < bottomDialogFourQifeiData.size(); i++) {
            bottomDialogFourQifeiData.get(i).setBo(false);
        }

        //降落机场 默认填充所有的为false
        secondHashMap = new HashMap<String, Boolean>();
        for (int i = 0; i < bottomDialogFourJiangluoData.size(); i++) {
            bottomDialogFourJiangluoData.get(i).setBo(false);
        }

    }

    //获取起飞时段的数据
    private void getStrSecondResult() {
        //起飞时段
        strSecond = "";
        if (mapSecond.size() > 0) {
            if (mapSecond.get("first")) {
                strSecond += "1";
            }
            if (mapSecond.get("second")) {
                strSecond += "2";
            }
            if (mapSecond.get("three")) {
                strSecond += "3";
            }
            if (mapSecond.get("four")) {
                strSecond += "4";
            }

        }

//        System.out.println("飞机起飞时段 最终结果:"+strSecond);
    }

    //获取航空公司的最终结果
    private void getStrThreeResult() {
        //航空公司
        strThree = "";
        for (int i = 0; i < bottomDialogThreeData.size(); i++) {
            if (bottomDialogThreeData.get(i).isBo()) {//判断哪些是被选中的,如果被选中的话,则拼接在最终结果中
                strThree = strThree + "" + bottomDialogThreeData.get(i).getCode() + ",";//获取二字码,拼接在接口中
            } else {
                if (hsThreeData.containsKey(bottomDialogThreeData.get(i).getCode())) {
                    strThree = strThree + "" + bottomDialogThreeData.get(i).getCode() + ",";//获取二字码,拼接在接口中
                }

            }
        }

//        System.out.println("飞机航空公司  最终结果:"+strThree);
    }

    //获取起飞机场/落地机场的最终结果
    private void getStrFourResult() {
        //机场落地  起飞机场
        strFourQifei = "";
        for (int i = 0; i < bottomDialogFourQifeiData.size(); i++) {
            if (bottomDialogFourQifeiData.get(i).isBo()) {//判断哪些是被选中的,如果被选中的话,则拼接在最终结果中
                strFourQifei = strFourQifei + "" + bottomDialogFourQifeiData.get(i).getTitle() + ",";//获取二字码,拼接在接口中
            } else {
                if (firstHashMap.containsKey(bottomDialogFourQifeiData.get(i).getTitle())) {
                    strFourQifei = strFourQifei + "" + bottomDialogFourQifeiData.get(i).getTitle() + ",";//获取二字码,拼接在接口中
                }

            }
        }

//        System.out.println("飞机起飞机场:"+strFourQifei);

        //机场落地  降落机场
        strFourJiangluo = "";
        for (int i = 0; i < bottomDialogFourJiangluoData.size(); i++) {
            if (bottomDialogFourJiangluoData.get(i).isBo()) {//判断哪些是被选中的,如果被选中的话,则拼接在最终结果中
                strFourJiangluo = strFourJiangluo + "" + bottomDialogFourJiangluoData.get(i).getTitle() + ",";//获取二字码,拼接在接口中
            } else {
                if (secondHashMap.containsKey(bottomDialogFourJiangluoData.get(i).getTitle())) {
                    strFourJiangluo = strFourJiangluo + "" + bottomDialogFourJiangluoData.get(i).getTitle() + ",";//获取二字码,拼接在接口中
                }

            }
        }


//        System.out.println("飞机落地机场:"+strFourJiangluo);
    }

    //完成条件选择 提交条件查询
    private void submit() {
//        if(firstResult){
////            System.out.println("飞机直飞经停  最终结果:true");
//        }else{
////            System.out.println("飞机直飞经停  最终结果:false");
//        }
        //获取起飞时段的最终结果
        getStrSecondResult();
        //获取航空公司的最终结果
        getStrThreeResult();
        //获取起飞机场/落地机场的最终结果
        getStrFourResult();

    }

    @OnClick({R.id.return_lin, R.id.right_txt, R.id.screenBtn, R.id.left_check_lin, R.id.right_check_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;
            case R.id.screenBtn:
                //筛选按钮
                if (!bottomDialog.isShowing()) {
                    showDialog();
                }
                break;
            case R.id.left_check_lin:
                //只要点击任意一个,另一个checkbox都会取消任何状态  两种排序方式不可混合
                rightCheckTxt.setTextColor(Color.parseColor("#666666"));
                rightCheckBox.setChecked(false);

                //按时间排序
                if (leftCheckBox.isChecked()) {
                    leftCheckTxt.setTextColor(Color.parseColor("#666666"));
                    leftCheckTxt.setText("从晚到早");
                    leftCheckBox.setChecked(false);
                } else {
                    leftCheckTxt.setTextColor(Color.parseColor("#ea5413"));
                    leftCheckTxt.setText("从早到晚");
                    leftCheckBox.setChecked(true);
                }
                break;
            case R.id.right_check_lin:
                //只要点击任意一个,另一个checkbox都会取消任何状态 两种排序方式不可混合
                leftCheckTxt.setTextColor(Color.parseColor("#666666"));
                leftCheckBox.setChecked(false);

                //按价格排序
                if (rightCheckBox.isChecked()) {
                    rightCheckTxt.setText("从高到低");
                    rightCheckTxt.setTextColor(Color.parseColor("#666666"));
                    rightCheckBox.setChecked(false);
                } else {
                    rightCheckTxt.setText("从低到高");
                    rightCheckTxt.setTextColor(Color.parseColor("#ea5413"));
                    rightCheckBox.setChecked(true);
                }
                break;
        }
    }

    private void showDialog() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.plane_screen_diglog_bottom, null);
        ListView listview = (ListView) contentView.findViewById(R.id.listview);//展示UI容器 body中listview
        ImageView close = (ImageView) contentView.findViewById(R.id.close);//关闭
        RadioGroup radioGroup = (RadioGroup) contentView.findViewById(R.id.radio_group);//弹出框左侧四个功能按钮
        //提交
        Button submitBtn = (Button) contentView.findViewById(R.id.submit_btn);
        //清除全部
        Button clear_btn = (Button) contentView.findViewById(R.id.clear_btn);
        //直飞/经停
        LinearLayout first_lin = (LinearLayout) contentView.findViewById(R.id.first_lin);//直飞/经停的布局 first
        CheckBox firstCheck = (CheckBox) contentView.findViewById(R.id.check);//直飞/经停的选择状态按钮

        if (firstResult) {
            firstCheck.setChecked(true);
        } else {
            firstCheck.setChecked(false);
        }
        //起飞时刻
        LinearLayout second_lin = (LinearLayout) contentView.findViewById(R.id.second_lin);//起飞时段 second
        LinearLayout first = (LinearLayout) contentView.findViewById(R.id.first);
        LinearLayout second = (LinearLayout) contentView.findViewById(R.id.second);
        LinearLayout three = (LinearLayout) contentView.findViewById(R.id.three);
        LinearLayout four = (LinearLayout) contentView.findViewById(R.id.four);
        CheckBox checkFirst = (CheckBox) contentView.findViewById(R.id.check_first);
        CheckBox checkSecond = (CheckBox) contentView.findViewById(R.id.check_second);
        CheckBox checkThree = (CheckBox) contentView.findViewById(R.id.check_three);
        CheckBox checkFour = (CheckBox) contentView.findViewById(R.id.check_four);


        //机场落地
        LinearLayout fourLin = (LinearLayout) contentView.findViewById(R.id.four_lin);
        //起飞机场 列表1
        ListView firstList = (ListView) contentView.findViewById(R.id.first_list);
        //降落机场 列表2
        ListView secondList = (ListView) contentView.findViewById(R.id.second_list);

        bottomDialog.setContentView(contentView);

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        //清除全部
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFirstAllChecked();
                clearSecondAllChecked();
                clearThreeAllChecked();
                hsThreeData.clear();
                firstHashMap.clear();
                secondHashMap.clear();
                clearFourAllChecked();
                bottomDialog.dismiss();
                initData();
            }
        });
        //完成
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
                bottomDialog.dismiss();
                initData();
            }
        });

        first_lin.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
        second_lin.setVisibility(View.GONE);
        fourLin.setVisibility(View.GONE);
        first_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstCheck.isChecked()) {
                    firstCheck.setChecked(false);
                    firstResult = false;
                } else {
                    firstCheck.setChecked(true);
                    firstResult = true;
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.first_btn://直飞/经停
                        first_lin.setVisibility(View.VISIBLE);//设置 直飞/经停 显示
                        listview.setVisibility(View.GONE);
                        second_lin.setVisibility(View.GONE);
                        fourLin.setVisibility(View.GONE);
                        first_lin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (firstCheck.isChecked()) {
                                    firstCheck.setChecked(false);
                                    firstResult = false;
                                } else {
                                    firstCheck.setChecked(true);
                                    firstResult = true;
                                }
                            }
                        });
                        break;
                    case R.id.second_btn://起飞时刻
                        first_lin.setVisibility(View.GONE);
                        listview.setVisibility(View.GONE);
                        fourLin.setVisibility(View.GONE);
                        second_lin.setVisibility(View.VISIBLE);
                        if (mapSecond.size() > 0) {
                            for (Map.Entry<String, Boolean> entry : mapSecond.entrySet()) {
                                if (entry.getValue()) {
                                    switch (entry.getKey()) {
                                        case "first":
                                            checkFirst.setChecked(true);
                                            break;
                                        case "second":
                                            checkSecond.setChecked(true);
                                            break;
                                        case "three":
                                            checkThree.setChecked(true);
                                            break;
                                        case "four":
                                            checkFour.setChecked(true);
                                            break;
                                    }
                                }
                            }
                        }

                        first.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkFirst.isChecked()) {
                                    checkFirst.setChecked(false);
                                    mapSecond.put("first", false);
                                } else {
                                    checkFirst.setChecked(true);
                                    mapSecond.put("first", true);
                                }
                            }
                        });
                        second.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkSecond.isChecked()) {
                                    checkSecond.setChecked(false);
                                    mapSecond.put("second", false);
                                } else {
                                    checkSecond.setChecked(true);
                                    mapSecond.put("second", true);
                                }
                            }
                        });
                        three.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkThree.isChecked()) {
                                    checkThree.setChecked(false);
                                    mapSecond.put("three", false);
                                } else {
                                    checkThree.setChecked(true);
                                    mapSecond.put("three", true);
                                }
                            }
                        });
                        four.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (checkFour.isChecked()) {
                                    checkFour.setChecked(false);
                                    mapSecond.put("four", false);
                                } else {
                                    checkFour.setChecked(true);
                                    mapSecond.put("four", true);
                                }
                            }
                        });
                        break;
                    case R.id.three_btn://航空公司
                        first_lin.setVisibility(View.GONE);
                        listview.setVisibility(View.VISIBLE);
                        fourLin.setVisibility(View.GONE);
                        second_lin.setVisibility(View.GONE);
                        DialogThreeAdapter adapter = new DialogThreeAdapter(mcontext, bottomDialogThreeData, hsThreeData);
                        if (bottomDialogThreeData != null) {
                            listview.setAdapter(adapter);
                        }

                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {

                                if (bottomDialogThreeData.get(arg2).isBo()) {//如果已经被选中的话，这次点击就取消选中
                                    if (hsThreeData.containsKey(bottomDialogThreeData.get(arg2).getCode())) {
                                        hsThreeData.remove(bottomDialogThreeData.get(arg2).getCode());
                                    }
                                    bottomDialogThreeData.get(arg2).setBo(false);
                                } else {
                                    bottomDialogThreeData.get(arg2).setBo(true);
                                    hsThreeData.put(bottomDialogThreeData.get(arg2).getCode(), bottomDialogThreeData.get(arg2).getTitle());
                                }

                                adapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    case R.id.four_btn://机场落地
                        first_lin.setVisibility(View.GONE);
                        listview.setVisibility(View.GONE);
                        second_lin.setVisibility(View.GONE);
                        fourLin.setVisibility(View.VISIBLE);
                        //起飞机场
                        DialogFourAdapter adapterFourQifei = new DialogFourAdapter(mcontext, bottomDialogFourQifeiData, firstHashMap);

                        if (bottomDialogFourQifeiData != null) {
                            firstList.setAdapter(adapterFourQifei);
                        }


                        firstList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (bottomDialogFourQifeiData.get(position).isBo()) {//如果已经被选中的话，这次点击就取消选中
                                    if (firstHashMap.containsKey(bottomDialogFourQifeiData.get(position).getTitle())) {
                                        firstHashMap.remove(bottomDialogFourQifeiData.get(position).getTitle());
                                    }
                                    bottomDialogFourQifeiData.get(position).setBo(false);
                                } else {
                                    firstHashMap.put(bottomDialogFourQifeiData.get(position).getTitle(), true);
                                    bottomDialogFourQifeiData.get(position).setBo(true);
                                }
                                adapterFourQifei.notifyDataSetChanged();

                            }
                        });
                        //落地机场
                        DialogFourAdapter adapterFourLuodi = new DialogFourAdapter(mcontext, bottomDialogFourJiangluoData, secondHashMap);
                        if (bottomDialogFourJiangluoData != null) {
                            secondList.setAdapter(adapterFourLuodi);
                        }

                        secondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (bottomDialogFourJiangluoData.get(position).isBo()) {

                                    if (secondHashMap.containsKey(bottomDialogFourJiangluoData.get(position).getTitle())) {
                                        secondHashMap.remove(bottomDialogFourJiangluoData.get(position).getTitle());
                                    }
                                    bottomDialogFourJiangluoData.get(position).setBo(false);

                                } else {
                                    secondHashMap.put(bottomDialogFourJiangluoData.get(position).getTitle(), true);
                                    bottomDialogFourJiangluoData.get(position).setBo(true);

                                }
                                adapterFourLuodi.notifyDataSetChanged();

                            }
                        });
                        break;
                }
            }
        });

        //关闭
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //航空公司列表的数据源
    private List<PlaneBottonDialogThreeModel> getDialogThreeData(Map<String, String> companyMap) {
        List<PlaneBottonDialogThreeModel> list = new ArrayList<PlaneBottonDialogThreeModel>();
        int i = 0;
        for (Map.Entry<String, String> entry : companyMap.entrySet()) {
            PlaneBottonDialogThreeModel map = new PlaneBottonDialogThreeModel();
            map.setTitle(entry.getKey());
            map.setBo(false);
            map.setCode(entry.getValue());
            map.setPosition(i);
            list.add(map);
            i++;
        }
        return list;
    }

    //机场落地 起飞机场 的数据源
    private List<PlaneBottonDialogThreeModel> getDialogFourQifeiData(Set<String> qifeiSet) {
        List<PlaneBottonDialogThreeModel> list = new ArrayList<PlaneBottonDialogThreeModel>();
        for (String str : qifeiSet) {
            PlaneBottonDialogThreeModel map = new PlaneBottonDialogThreeModel();
            map.setTitle(str);
            map.setBo(false);
            list.add(map);
        }
        return list;
    }

    //机场落地 落地机场 的数据源
    private List<PlaneBottonDialogThreeModel> getDialogFourJiangluoData(Set<String> jiangluoSet) {
        List<PlaneBottonDialogThreeModel> list = new ArrayList<PlaneBottonDialogThreeModel>();
        for (String str : jiangluoSet) {
            PlaneBottonDialogThreeModel map = new PlaneBottonDialogThreeModel();
            map.setTitle(str);
            map.setBo(false);
            list.add(map);
        }
        return list;
    }

    public final class ViewHolder {
        public TextView goTime;
        public TextView goOther;
        public TextView arriveTime;
        public TextView arriveOther;
        public TextView time;
        public TextView price;
        public TextView planeName;
        public TextView planeStyle;
    }


    //机票列表的数据源适配器
    class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.search_plane_listview_item, null);
                holder.goTime = (TextView) convertView.findViewById(R.id.goTime);
                holder.goOther = (TextView) convertView.findViewById(R.id.goOther);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.arriveTime = (TextView) convertView.findViewById(R.id.arriveTime);
                holder.arriveOther = (TextView) convertView.findViewById(R.id.arriveOther);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                holder.planeName = (TextView) convertView.findViewById(R.id.planeName);
                holder.planeStyle = (TextView) convertView.findViewById(R.id.planeStyle);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SearchFlightResponse.FlightInfo info = mData.get(position);
            holder.goTime.setText(info.getDptTime());//出发时间

//            if( !info.getDptTerminal().equals("") || info.getDptTerminal()!=null ){
//                holder.goOther.setText(info.getDptTerminal());//出发机场
//            }

            holder.goOther.setText(info.getDptAirport());//出发机场
            holder.time.setText(info.getFlightTimes());//飞行时间
            holder.arriveTime.setText(info.getArrTime());//到达时间
//            if( !info.getArrTerminal().equals("") || info.getArrTerminal()!=null ){
//                holder.arriveOther.setText(info.getDptAirport()+info.getArrTerminal());//到达机场
//            }
            holder.arriveOther.setText(info.getArrAirport());//到达机场
            holder.price.setText(info.getBarePrice());//销售价
            holder.planeName.setText(info.getAirlineName());//航空公司全名
            holder.planeStyle.setText(info.getFlightTypeFullName());//机型全名
            return convertView;
        }
    }

}
