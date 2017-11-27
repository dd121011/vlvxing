package com.vlvxing.app.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.qunar.bean.SearchFlightResponse;
import com.qunar.bean.changeSearch.PlaneChangeSerachBean;
import com.qunar.model.PlaneChangeSerachResult;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

/**
 * 改签查询
 */
public class PlaneChangeSearchActivity extends BaseActivity {


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
    @Bind(R.id.bottom_lin_lay)
    LinearLayout bottom_lin_lay;
    @Bind(R.id.img)
    ImageView img;

    private HorizontalCalendar horizontalCalendar;
    private ListView body_list;
    private MyAdapter adapter;
    private List<PlaneChangeSerachBean.ResultBean.ChangeSearchResultBean.TgqReasonsBean.ChangeFlightSegmentListBean> listData = new ArrayList<>();
    private List<PlaneChangeSerachBean.ResultBean.ChangeSearchResultBean.FlightSegmentListBean> listBeen = new ArrayList<>();//存储航空公司,出发城市,到达城市等信息
    private Context mcontext;
    private String orderNo;

    private String date;
    //接口条件
    private HashMap<String, Object> params;//查询航班条件参数
    private String dateResult = "";
    private String id = "";

    private int changeReason = 0;//改签原因
    private int userNumber = 1;//需要改签的乘客个人
    private String arriCity = "";//出发
    private String deptCity = "";//到达
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plane_horizontal);
        ButterKnife.bind(this);
        bottom_lin_lay.setVisibility(View.GONE);
        orderNo = getIntent().getStringExtra("orderNo").trim();
        date = getIntent().getStringExtra("changeDate").trim();
        arriCity = getIntent().getStringExtra("arriCity").trim();
        deptCity = getIntent().getStringExtra("deptCity").trim();
        headTitleLeft.setText(arriCity);
        headTitleRight.setText(deptCity);
        arrToString();
        mcontext = this;
        params = new HashMap<>();//查询航班条件参数
        //底部筛选弹出框
//        bottomDialog = new Dialog(this, R.style.BottomDialog);
        //获取上个页面,用户选择的日期,并转换成日历对象,设置横向日历默认选中的日期
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        body_list = (ListView) findViewById(R.id.body_list);
        body_list.setVisibility(View.INVISIBLE);//默认机票列表不显示
        /** 一个月后结束  1 */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 3);//设置购票日期暂时只能在3个月内

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
//                Toast.makeText(PlaneSearchActivity.this, DateFormat.getDateInstance().format(d) + " is selected!", Toast.LENGTH_SHORT).show();
                date = DateFormat.getDateInstance().format(d).toString();
                arrToString();
                initData();
            }
        });

        initData();
        adapter = new MyAdapter(this);
        body_list.setAdapter(adapter);

        setOnItemclickLisenter();

    }
    private void setOnItemclickLisenter(){
        body_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mcontext,PlaneChangeDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderInfo",listData.get(i));
                intent.putExtras(bundle);
                intent.putExtra("date",dateResult);
                intent.putExtra("orderNo",orderNo);
                intent.putExtra("id",id);
                intent.putExtra("startDate",dateResult);
                intent.putExtra("userNumber",userNumber);
                startActivity(intent);
            }
        });
    }
    /**
     * date字符串 xxxx-x-x转换 xxxx-xx-xx
     */
    private void arrToString(){
        System.out.println("时间转换 date:"+date);
        if(date.indexOf("年")!=-1){//包含年
            date = date.replace('年', '-');
            date = date.replace('月', '-');
            date = date.substring(0,date.length()-1);
        }
        String[] dateStr = date.split("-");
        if(dateStr[1].length()==1) {
            dateStr[1] = 0 + dateStr[1];
            System.out.println("时间转换 长度为1");
        }
        if (dateStr[2].length()==1) {
            dateStr[2] = 0 + dateStr[2];
        }
        dateResult = dateStr[0]+"-"+dateStr[1]+"-"+dateStr[2];
    }
    private void initData() {
        //机票列表的数据源
        String url = Constants.QUNAR_BASE_URL;
        params.put("orderNo",orderNo);
        params.put("changeDate",dateResult);
        showDialog("加载中...");
        RemoteDataHandler.asyncPlaneGet(url+"changeSearch",params,mcontext,new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                System.out.println("机票改签查询 data:"+data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dismissDialog();
                    return;
                }
                System.out.println("机票改签查询 json:"+json);
                Gson gson = new Gson();
                PlaneChangeSerachResult result = gson.fromJson(json,PlaneChangeSerachResult.class);
                PlaneChangeSerachBean model =result.getData();
                List<PlaneChangeSerachBean.ResultBean> list = model.getResult();
                id = "";
                userNumber = list.size();
                for(int i = 0; i<list.size();i++){
                    id += list.get(i).getId()+",";
                }
                PlaneChangeSerachBean.ResultBean bean = list.get(0);
                PlaneChangeSerachBean.ResultBean.ChangeSearchResultBean resultBean = bean.getChangeSearchResult();
                listBeen.clear();
                if(resultBean.getFlightSegmentList()!=null){
                    listBeen.addAll(resultBean.getFlightSegmentList());
//                    headTitleLeft.setText(listBeen.get(0).getDptCity());
//                    headTitleRight.setText(listBeen.get(0).getArrCity());
                    List<PlaneChangeSerachBean.ResultBean.ChangeSearchResultBean.TgqReasonsBean> baenList = resultBean.getTgqReasons();
                    System.out.println("机票改签查询 baenList:"+baenList.size());
                    listData.clear();
                    listData = baenList.get(changeReason).getChangeFlightSegmentList();
                    System.out.println("机票改签查询 resultList:"+listData.size());
                    body_list.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtils.show(mcontext,resultBean.getReason()+"");
                }

                dismissDialog();

            }
        });
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

                break;
            case R.id.left_check_lin:

                break;
            case R.id.right_check_lin:

                break;

        }
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


    public final class ViewHolder {
        public TextView goTime;
        public TextView goOther;
        public TextView arriveTime;
        public TextView arriveOther;
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
            return listData.size();
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
                convertView = mInflater.inflate(R.layout.search_change_plane_listview_item, null);
                holder.goTime = (TextView) convertView.findViewById(R.id.goTime);
                holder.goOther = (TextView) convertView.findViewById(R.id.goOther);

                holder.arriveTime = (TextView) convertView.findViewById(R.id.arriveTime);
                holder.arriveOther = (TextView) convertView.findViewById(R.id.arriveOther);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                holder.planeName = (TextView) convertView.findViewById(R.id.planeName);
                holder.planeStyle = (TextView) convertView.findViewById(R.id.planeStyle);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PlaneChangeSerachBean.ResultBean.ChangeSearchResultBean.TgqReasonsBean.ChangeFlightSegmentListBean info = listData.get(position);
            holder.goTime.setText(info.getStartTime());//出发时间
            System.out.println("机票改签  出发时间 info.getStartTime()"+info.getStartTime());
            holder.goOther.setText(listBeen.get(0).getDptPort());//出发机场
            System.out.println("机票改签  出发机场 info.info.getStartPlace()()"+info.getStartPlace());
            holder.arriveTime.setText(info.getEndTime());//到达时间
            holder.arriveOther.setText(listBeen.get(0).getArrPort());//到达机场
            holder.price.setText(info.getAllFee());//改签费
            holder.planeName.setText(info.getFlight()+info.getFlightNo());//航空公司全名
            holder.planeStyle.setText(info.getFlightType());//机型全名
            return convertView;
        }
    }
}
