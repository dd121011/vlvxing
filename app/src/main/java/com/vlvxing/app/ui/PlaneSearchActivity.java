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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.DialogFourAdapter;
import com.vlvxing.app.adapter.DialogThreeAdapter;
import com.vlvxing.app.model.PlaneBottonDialogThreeModel;
import java.text.DateFormat;
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


public class PlaneSearchActivity extends BaseActivity  {


    @Bind(R.id.head_title_left)
    TextView headTitleLeft;//标题
    @Bind(R.id.head_title_right)
    TextView headTitleRight;//标题右
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    @Bind(R.id.screenBtn)
    Button screenBtn;//筛选按钮
    private HorizontalCalendar horizontalCalendar;
    private ListView body_list;
    private List<Map<String, String>> mData;
    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private Dialog bottomDialog;
//    private List<PlaneScreenBottomListViewFirstModel> li;
    private String strThree = "" ;//记录航空公司中用户选择了哪些航空公司
    private HashMap<String,Boolean> mapSecond ;//记录起飞时刻中用户选择了哪些航班
    //航空公司
    private List<PlaneBottonDialogThreeModel>  bottomDialogThreeData;//航空公司
    private HashMap<Integer,Boolean> lCurrentItem;//航空公司listview 有哪些item是被选中的

    //直飞经停
    private String firstResult = "";

    //机场落地
    private List<PlaneBottonDialogThreeModel>  bottomDialogFourQifeiData;//航空公司 起飞机场
    private List<PlaneBottonDialogThreeModel>  bottomDialogFourJiangluoData;//航空公司 起飞机场
    private int fourFirstListviewPosition=-1;//记录用户最后选择的起飞机场
    private int fourSecondListviewPosition=-1;//记录用户最后选择的降落机场
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_horizontal);
        goCity = getIntent().getStringExtra("goCity");//出发城市
        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
        date = getIntent().getStringExtra("date");//出发日期
        ButterKnife.bind(this);
        mcontext = this;

        //航空公司list数据绑定
        initThreeData();
        //机场落地 list数据绑定
        initFourData();
        //起飞时段 记录了哪些航班
        mapSecond =  new HashMap<String,Boolean>();

//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
//        Date d = null;
//        try {
//            d = sdf.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(d);

        headTitleLeft.setText(goCity);
        headTitleRight.setText(arriveCity);
        body_list = (ListView) findViewById(R.id.body_list);

        /** 一个月后结束  1 */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);//设置购票日期暂时只能在一个月内

        /** 从现在起1个月前开始   -1*/
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

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
                .defaultSelectedDate(defaultDate.getTime())
                .textColor(Color.WHITE, Color.parseColor("#ea5413"))//字体颜色（正常，选中）
                .selectedDateBackground(Color.TRANSPARENT)//item背景色
                .backgroundColor(Color.TRANSPARENT, Color.parseColor("#FFFFFF"))//背景选择 (正常，选中)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Toast.makeText(PlaneSearchActivity.this, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();

            }
        });


        mData = getListViewData();
                  MyAdapter adapter = new MyAdapter(this);
        body_list.setAdapter(adapter);

        body_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intnet = new Intent(mcontext,PlaneDetailsActivity.class);
                intnet.putExtra("goCity",goCity);//出发城市
                intnet.putExtra("arriveCity",arriveCity);//到达城市
                intnet.putExtra("date",date);//出发日期
                startActivity(intnet);
            }
        });
    }

    //航空公司list数据绑定
    private void initThreeData(){
        bottomDialogThreeData = getDialogThreeData();
        lCurrentItem = new HashMap<Integer, Boolean>();
        //默认都是未被选中的状态
        for(int i=0;i<bottomDialogThreeData.size();i++){
            lCurrentItem.put(i,false);
        }
    }

    //机场落地list数据绑定
    private void initFourData(){
        bottomDialogFourQifeiData = getDialogFourQifeiData();
        bottomDialogFourJiangluoData = getDialogFourJiangluoData();
    }

    @OnClick({R.id.return_lin, R.id.right_txt,R.id.screenBtn})
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
                showDialog();
                break;
        }
    }
    private void showDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.plane_screen_diglog_bottom, null);
        ListView listview = (ListView) contentView.findViewById(R.id.listview);//展示UI容器 body中listview
        ImageView close = (ImageView) contentView.findViewById(R.id.close);//关闭
        RadioGroup radioGroup = (RadioGroup)contentView.findViewById(R.id.radio_group);//弹出框左侧四个功能按钮
        //提交
        Button  submitBtn = (Button)contentView.findViewById(R.id.submit_btn);
        //清除全部
        Button clear_btn = (Button)contentView.findViewById(R.id.clear_btn);
        //直飞/经停
        LinearLayout first_lin = (LinearLayout)contentView.findViewById(R.id.first_lin);//直飞/经停的布局 first
        CheckBox firstCheck = (CheckBox)contentView.findViewById(R.id.check);//直飞/经停的选择状态按钮
        //起飞时刻
        LinearLayout second_lin = (LinearLayout)contentView.findViewById(R.id.second_lin);//起飞时段 second
        LinearLayout first = (LinearLayout)contentView.findViewById(R.id.first);
        LinearLayout second = (LinearLayout)contentView.findViewById(R.id.second);
        LinearLayout three = (LinearLayout)contentView.findViewById(R.id.three);
        LinearLayout four = (LinearLayout)contentView.findViewById(R.id.four);
        CheckBox checkFirst = (CheckBox)contentView.findViewById(R.id.check_first);
        CheckBox checkSecond = (CheckBox)contentView.findViewById(R.id.check_second);
        CheckBox checkThree = (CheckBox)contentView.findViewById(R.id.check_three);
        CheckBox checkFour = (CheckBox)contentView.findViewById(R.id.check_four);
        //机场落地
        LinearLayout fourLin = (LinearLayout)contentView.findViewById(R.id.four_lin);
        //起飞机场 列表
        ListView firstList = (ListView)contentView.findViewById(R.id.first_list);
        //降落机场 列表
        ListView secondList = (ListView)contentView.findViewById(R.id.second_list);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        //清除全部
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        //完成
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        if (firstResult.equals("直飞")){
            firstCheck.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.first_btn://直飞/经停
                        first_lin.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);
                        second_lin.setVisibility(View.GONE);
                        fourLin.setVisibility(View.GONE);

                        first_lin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(firstCheck.isChecked()){
                                    firstCheck.setChecked(false);
                                    firstResult = "";
                                }else{
                                    firstCheck.setChecked(true);
                                    firstResult = "直飞";
                                }
                            }
                        });
                        break;
                    case R.id.second_btn://起飞时刻
                        first_lin.setVisibility(View.GONE);
                        listview.setVisibility(View.GONE);
                        fourLin.setVisibility(View.GONE);
                        second_lin.setVisibility(View.VISIBLE);
                        if(mapSecond.size()>0){
                            for (Map.Entry<String, Boolean> entry : mapSecond.entrySet())
                            {
                                if (entry.getValue()){
                                    switch(entry.getKey()){
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
                                if (checkFirst.isChecked()){
                                    checkFirst.setChecked(false);
                                    mapSecond.put("first",false);
                                }else{
                                    checkFirst.setChecked(true);
                                    mapSecond.put("first",true);
                                }
                            }
                        });
                        second.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkSecond.isChecked()){
                                    checkSecond.setChecked(false);
                                    mapSecond.put("second",false);
                                }else{
                                    checkSecond.setChecked(true);
                                    mapSecond.put("second",true);
                                }
                            }
                        });
                        three.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(checkThree.isChecked()){
                                    checkThree.setChecked(false);
                                    mapSecond.put("three",false);
                                }else{
                                    checkThree.setChecked(true);
                                    mapSecond.put("three",true);
                                }
                            }
                        });
                        four.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(checkFour.isChecked()){
                                    checkFour.setChecked(false);
                                    mapSecond.put("four",false);
                                }else{
                                    checkFour.setChecked(true);
                                    mapSecond.put("four",true);
                                }
                            }
                        });
                           break;
                    case R.id.three_btn://航空公司
                        first_lin.setVisibility(View.GONE);
                        listview.setVisibility(View.VISIBLE);
                        fourLin.setVisibility(View.GONE);
                        second_lin.setVisibility(View.GONE);

                        DialogThreeAdapter adapter = new DialogThreeAdapter(mcontext,bottomDialogThreeData,lCurrentItem);
                        listview.setAdapter(adapter);
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                                strThree = "";
                                if(lCurrentItem.get(arg2)){//如果已经被选中的话，这次点击就取消选中
                                    lCurrentItem.put(arg2,false);
                                }else{
                                    lCurrentItem.put(arg2,true);
                                }
                                adapter.notifyDataSetChanged();
                                Set<Integer> set = new HashSet<Integer>();
                                for (Map.Entry<Integer, Boolean> entry : lCurrentItem.entrySet())
                                {
                                    if (entry.getValue()){
                                        set.add(entry.getKey());
                                    }
                                }

                                for(Integer value : set){
                                    strThree =strThree+""+ bottomDialogThreeData.get(value).getTitle();
                                }

                                Toast.makeText(PlaneSearchActivity.this, ""+strThree, Toast.LENGTH_SHORT).show();

                            }
                        });
                        break;
                    case R.id.four_btn://机场落地
                        first_lin.setVisibility(View.GONE);
                        listview.setVisibility(View.GONE);
                        second_lin.setVisibility(View.GONE);
                        fourLin.setVisibility(View.VISIBLE);

                        //起飞机场
                        DialogFourAdapter adapterFourQifei = new DialogFourAdapter(mcontext,bottomDialogFourQifeiData);
                        firstList.setAdapter(adapterFourQifei);
                        if (fourFirstListviewPosition!=-1){
                            adapterFourQifei.setCurrentItem(fourFirstListviewPosition);
                            adapterFourQifei.setClick(true);
                            adapterFourQifei.notifyDataSetChanged();
                        }
                        firstList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                adapterFourQifei.setCurrentItem(position);
                                adapterFourQifei.setClick(true);
                                adapterFourQifei.notifyDataSetChanged();
                                fourFirstListviewPosition = position;

                            }
                        });
                        //落地机场
                        DialogFourAdapter adapterFourLuodi = new DialogFourAdapter(mcontext,bottomDialogFourJiangluoData);
                        secondList.setAdapter(adapterFourLuodi);
                        if (fourSecondListviewPosition!=-1){
                            adapterFourLuodi.setCurrentItem(fourSecondListviewPosition);
                            adapterFourLuodi.setClick(true);
                            adapterFourLuodi.notifyDataSetChanged();
                        }
                        secondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                adapterFourLuodi.setCurrentItem(position);
                                adapterFourLuodi.setClick(true);
                                adapterFourLuodi.notifyDataSetChanged();
                                fourSecondListviewPosition = position;
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
    //机票列表的数据源
    private List<PlaneBottonDialogThreeModel> getDialogThreeData() {
        List<PlaneBottonDialogThreeModel> list  = new ArrayList<PlaneBottonDialogThreeModel>();
        PlaneBottonDialogThreeModel map = new PlaneBottonDialogThreeModel();
        map.setTitle("南方航空");
        map.setPrice( "1947");
        map.setBo(false);
        PlaneBottonDialogThreeModel map1 = new PlaneBottonDialogThreeModel();
        map1.setTitle( "厦门航空");
        map1.setPrice( "1947");
        map1.setBo(false);
        PlaneBottonDialogThreeModel map2 = new PlaneBottonDialogThreeModel();
        map2.setTitle( "深圳航空");
        map2.setPrice("1947");
        map2.setBo(false);
        PlaneBottonDialogThreeModel map3 = new PlaneBottonDialogThreeModel();
        map3.setTitle("昆明航空");
        map3.setPrice( "1947");
        map3.setBo(false);
        PlaneBottonDialogThreeModel map4 = new PlaneBottonDialogThreeModel();
        map4.setTitle("中国航空");
        map4.setPrice( "1947");
        map4.setBo(false);
        PlaneBottonDialogThreeModel map5 = new PlaneBottonDialogThreeModel();
        map5.setTitle( "海南航空");
        map5.setPrice( "1947");
        map5.setBo(false);
        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        return list;
    }

    //机场落地 起飞机场 的数据源
    private List<PlaneBottonDialogThreeModel> getDialogFourQifeiData() {
        List<PlaneBottonDialogThreeModel> list  = new ArrayList<PlaneBottonDialogThreeModel>();
        PlaneBottonDialogThreeModel map = new PlaneBottonDialogThreeModel();
        map.setTitle("首都机场");
        map.setPrice( "1915");
        map.setBo(false);

        list.add(map);
        return list;
    }

    //机场落地 起飞机场 的数据源
    private List<PlaneBottonDialogThreeModel> getDialogFourJiangluoData() {
        List<PlaneBottonDialogThreeModel> list  = new ArrayList<PlaneBottonDialogThreeModel>();
        PlaneBottonDialogThreeModel map = new PlaneBottonDialogThreeModel();
        map.setTitle( "保安机场");
        map.setPrice( "1915");
        map.setBo(false);

        list.add(map);
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


    //机票列表的数据源
    private List<Map<String,String>> getListViewData() {
        List<Map<String, String>> list  = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("goTime", "23:00");
        map.put("goOther", "首都T1");
        map.put("arriveTime", "01:30");
        map.put("arriveOther", "保安T3");
        map.put("time", "3小时25分钟");
        map.put("price", "￥1754");
        map.put("planeName", "海南航空HU7707");
        map.put("planeStyle", "空壳330（宽）");
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        return list;
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
            holder.goTime.setText((String) mData.get(position).get("goTime"));
            holder.goOther.setText((String) mData.get(position).get("goOther"));
            holder.time.setText((String) mData.get(position).get("time"));
            holder.arriveTime.setText((String) mData.get(position).get("arriveTime"));
            holder.arriveOther.setText((String) mData.get(position).get("arriveOther"));
            holder.price.setText((String) mData.get(position).get("price"));
            holder.planeName.setText((String) mData.get(position).get("planeName"));
            holder.planeStyle.setText((String) mData.get(position).get("planeStyle"));
            return convertView;
        }
    }
}
