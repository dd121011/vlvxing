package com.handongkeji.selecity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.model.PinyinComparator;
import com.handongkeji.selecity.model.SortModel;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.CharacterParser;
import com.handongkeji.widget.KeyboardLayout1;
import com.handongkeji.widget.MyGridView;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PlaneSelestorCityActivity extends BaseActivity implements PlaneSideBar.OnTouchingLetterChangedListener{
    List<String> resourseData = new ArrayList<String>();
    @Bind(R.id.root)
    LinearLayout root;
    @Bind(R.id.site)
    TextView site;
    @Bind(R.id.result_list)
    ListView resultList;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    @Bind(R.id.country_lvcountry)
    ListView countryLvcountry;
    @Bind(R.id.dialog)
    TextView dialog;
    @Bind(R.id.fast_scroller)
    PlaneSideBar sideBar;
    @Bind(R.id.select_edt)
    EditText selectEdt;
    @Bind(R.id.serch_lin)
    RelativeLayout serchLin;
    @Bind(R.id.back)
    LinearLayout backLin;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;//国内、国际的父控件
    @Bind(R.id.left_radio_btn)
    RadioButton leftBtn;//国内
    @Bind(R.id.right_radio_btn)
    RadioButton rightBtn;//国际
    @Bind(R.id.view_left)
    View viewLeft;//类似背景选择器 左
    @Bind(R.id.view_right)
    View viewRight;//类似背景选择器 右
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private PinyinComparator pinyinComparator;
    public JSONArray jsonArrays;
    private int type;
    private Intent intent;
    private LinearLayout listViewHeader;
    private Context mcontext;
    LayoutInflater inflater;
    private String historyCityName [] ={"广 州","北 京","上 海"};
    private String hotCityName [] ={"北 京","上 海","广 州","深 圳"};
    private String historyCityResult = "";
    private String hotCityResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_selestor_city);
        ButterKnife.bind(this);
        //设置第一次进入界面不弹出软键盘
        serchLin.setFocusable(true);
        serchLin.setFocusableInTouchMode(true);
        serchLin.requestFocus();
        mcontext = this;
        inflater= LayoutInflater.from(this);
        init();// 初始化控件
        loadData();// 获取首页列表
        radioGroupOnCheckChange();//注册国际、国内的选择事件

    }
    /**
     * 単程、往返的父容器选择状态的事件监听
     */
    private void radioGroupOnCheckChange(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(leftBtn.getId() == checkedId){
                    //単程
                    viewLeft.setVisibility(View.VISIBLE);
                    viewRight.setVisibility(View.INVISIBLE);
                }

                if(rightBtn.getId() == checkedId){
                    //往返
                    viewLeft.setVisibility(View.INVISIBLE);
                    viewRight.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void init() {
        View headerView = getLayoutInflater().inflate(R.layout.plane_listview_header, null);
        GridView historyCity = (MyGridView) headerView.findViewById(R.id.history_city);
        GridView hotCity = (MyGridView) headerView.findViewById(R.id.hot_city);
        GridViewHistory historyAdapter =new GridViewHistory(mcontext,historyCityName);
        historyCity.setAdapter(historyAdapter);

        GridViewHot hotAdapter = new GridViewHot(mcontext,hotCityName);
        hotCity.setAdapter(hotAdapter);

        historyCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                historyAdapter.changeState(position);
                int selectorPosition = position;
                historyCityResult = historyCityName[selectorPosition];

                if (type == 1) {
//                        myApp.setCity_name(name);
//                        myApp.setAreaid(locationId);
                    intent.putExtra("name", historyCityResult.trim());
                    intent.putExtra("locationId", "");
                    setResult(RESULT_OK, intent);
                    finish();
//                        startActivity(new Intent(mContext, MainActivity.class));
                } else if(type == 2){
//                        intent.putExtra("areaname", name);
//                        intent.putExtra("areaid", locationId);
                    intent.putExtra("name", historyCityResult.trim());
                    intent.putExtra("locationId", "");
                    setResult(RESULT_OK, intent);
                    finish();
                }
//                Toast.makeText(mcontext, "历史城市"+selectorPosition, Toast.LENGTH_SHORT).show();
            }
        });


        hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotAdapter.changeState(position);
                int selectorPosition = position;

                hotCityResult    = hotCityName[selectorPosition];

                if (type == 1) {
//                        myApp.setCity_name(name);
//                        myApp.setAreaid(locationId);
                    intent.putExtra("name", hotCityResult.trim());
                    intent.putExtra("locationId", "");
                    setResult(RESULT_OK, intent);
                    finish();
//                        startActivity(new Intent(mContext, MainActivity.class));
                } else if(type == 2){
//                        intent.putExtra("areaname", name);
//                        intent.putExtra("areaid", locationId);
                    intent.putExtra("name", hotCityResult.trim());
                    intent.putExtra("locationId", "");
                    setResult(RESULT_OK, intent);
                    finish();
                }


//                Toast.makeText(mcontext, "热门城市"+selectorPosition, Toast.LENGTH_SHORT).show();
            }
        });


        commonNoDataLayout.setVisibility(View.GONE);
        intent = getIntent();
        type = intent.getIntExtra("type", 0); //判断如果是1 则是出发城市， 2 则是到达城市
        // 城市
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);
        countryLvcountry.addHeaderView(headerView);

        adapter = new SortAdapter(PlaneSelestorCityActivity.this);
        countryLvcountry.setAdapter(adapter);

        sideBar.setOnTouchingLetterChangedListener(this);
        //搜索框的编辑状态变化的事件监听
        selectEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    resultList.setVisibility(View.VISIBLE);
                    countryLvcountry.setVisibility(View.GONE);
                    selectCity();
                } else {
                    resultList.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({ R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back:
                finish();
                break;
        }
    }



    class resultAdapter extends BaseAdapter {
        Context mContext;
        List<JSONObject> list;

        public resultAdapter(Context mContext, List<JSONObject> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size() > 0 ? list.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.selectcity_item, parent, false);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.title = (TextView) convertView.findViewById(R.id.title_txt);
                JSONObject obj = list.get(position);
                try {
                    final String areaname = obj.getString("areaname");
                    final String areaid = obj.getString("areaid");
                    holder.title.setText(areaname);
                    holder.title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (type == 1) {
                                intent.putExtra("areaname", areaname);
                                intent.putExtra("areaid", areaid);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                            if(type == 2){
                                intent.putExtra("areaname", areaname);
                                intent.putExtra("areaid", areaid);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        class ViewHolder {
            TextView title;
        }
    }


    @Override
    public void onTouchingLetterChanged(String s) {
        if("历史".equals(s)){
            Toast.makeText(PlaneSelestorCityActivity.this, "历史", Toast.LENGTH_SHORT).show();
        }
        if("热门".equals(s)){
            Toast.makeText(PlaneSelestorCityActivity.this, "热门", Toast.LENGTH_SHORT).show();
        }
        int position = adapter.getPositionForSection(s.charAt(0));
//        Toast.makeText(PlaneSelestorCityActivity.this, "position"+position, Toast.LENGTH_SHORT).show();
        if (position != -1) {
            resultList.setSelection(position);
        }
    }

    private List<SortModel> filledData(JSONArray array) throws JSONException {

        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject tmpObj = (JSONObject) array.get(i);
            String tmpString = tmpObj.getString("areaname");
            String idString = tmpObj.getString("areaid");
            SortModel sortModel = new SortModel();
            sortModel.setName(tmpString);
            sortModel.setAreaid(idString);
            String pinyin = characterParser.getSelling(tmpString);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }

        return mSortList;
    }

    private void selectCity() {
        String url = Constants.URL_CITYSELECT;
        HashMap<String, String> params = new HashMap<String, String>();
        String city = selectEdt.getText().toString().trim();
        params.put("areaName", city);
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                String json = data.getJson();
                if (null != json && !json.equals("")) {
                    JSONObject obj;
                    try {
                        obj = new JSONObject(json);
                        String status = obj.getString("status");
                        String message = obj.getString("message");
                        if (status.equals("1")) {
                            String datas = obj.getString("data");
                            JSONArray array = new JSONArray(datas);
                            List<JSONObject> result = new ArrayList<JSONObject>();
                            if (array.length() <= 0) {
                                commonNoDataLayout.setVisibility(View.VISIBLE);
                                resultList.setVisibility(View.GONE);
                            } else {
                                commonNoDataLayout.setVisibility(View.GONE);
                                resultList.setVisibility(View.VISIBLE);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    result.add(object);
                                }
                                resultAdapter adapter = new resultAdapter(PlaneSelestorCityActivity.this, result);
                                resultList.setAdapter(adapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadData() {
        showDialog("加载中...");
        String url = Constants.URL_CITYLIST;
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("parentareaid", parentareaid);
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                String json = data.getJson();
                if (null != json && !json.equals("")) {
                    JSONObject obj;
                    try {
                        obj = new JSONObject(json);
                        String status = obj.getString("status");
                        String message = obj.getString("message");
                        if (status.equals("1")) {
                            String datas = obj.getString("data");
                            JSONArray array = new JSONArray(datas);
                            jsonArrays = new JSONArray();
                            if (array.length() > 0) {
                                commonNoDataLayout.setVisibility(View.GONE);
                                countryLvcountry.setVisibility(View.VISIBLE);
                                for (int i = 0; i < array.length(); i++) {
//                                JSONObject jsonObject = array.getJSONObject(i);
//                                String cityDatas = jsonObject.getString("children");
//                                JSONArray cityArray = new JSONArray(cityDatas);
//                                for (int j = 0; j < cityArray.length(); j++) {
                                    jsonArrays.put(array.get(i));
//                                }
                                }
                                SourceDateList = filledData(jsonArrays);
                                Collections.sort(SourceDateList, pinyinComparator);
                                adapter.LoadData(SourceDateList);
                            } else {
                                commonNoDataLayout.setVisibility(View.VISIBLE);
                                countryLvcountry.setVisibility(View.GONE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dismissDialog();
                }
            }
        });
    }


    class SortAdapter extends BaseAdapter implements SectionIndexer {
        private List<SortModel> list = null;
        private Context mContext;
        private MyApp myApp;

        public SortAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void LoadData(List<SortModel> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public void updateListView(List<SortModel> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public int getCount() {
            return list == null ? 0 : list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("NewApi")
        public View getView(final int position, View view, ViewGroup arg2) {
            ViewHolder viewHolder = null;
            final SortModel mContent = list.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
                viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
                viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
                viewHolder.line = (View) view.findViewById(R.id.line);
                myApp = (MyApp) ((Activity) mContext).getApplication();
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            int section = getSectionForPosition(position);
            if (position == getPositionForSection(section)) {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(mContent.getSortLetters());
                viewHolder.line.setVisibility(View.GONE);
            } else {
                viewHolder.tvLetter.setVisibility(View.GONE);
                viewHolder.line.setVisibility(View.VISIBLE);
            }
            final String name = list.get(position).getName();
            final String locationId = list.get(position).getAreaid();
            viewHolder.tvTitle.setText(name);
            viewHolder.tvTitle.setSelected(false);
            viewHolder.tvTitle.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selector));
            final TextView tvTitle = viewHolder.tvTitle;
            viewHolder.tvTitle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (type == 1) {
//                        myApp.setCity_name(name);
//                        myApp.setAreaid(locationId);
                        intent.putExtra("name", name);
                        intent.putExtra("locationId", locationId);
                        setResult(RESULT_OK, intent);
                        finish();
//                        startActivity(new Intent(mContext, MainActivity.class));
                    } else if(type == 2){
//                        intent.putExtra("areaname", name);
//                        intent.putExtra("areaid", locationId);
                        intent.putExtra("name", name);
                        intent.putExtra("locationId", locationId);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
            return view;
        }

        public int getSectionForPosition(int position) {
            return list.get(position).getSortLetters().charAt(0);
        }

        public int getPositionForSection(int section) {
            for (int i = 0; i < getCount(); i++) {
                String sortStr = list.get(i).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }

            return -1;
        }

        private String getAlpha(String str) {
            String sortStr = str.trim().substring(0, 1).toUpperCase();
            if (sortStr.matches("[A-Z]")) {
                return sortStr;
            } else {
                return "#";
            }
        }

        @Override
        public Object[] getSections() {
            return null;
        }
    }

    static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        View line;
    }


    class GridViewHistory extends BaseAdapter{
        private Context context=null;
        private String data[]=null;
        private int selectorPosition = -1;

        private class Holder{
            LinearLayout city_lin;
            ImageView item_img;
            TextView item_tex;
        }
        //构造方法
        public GridViewHistory(Context context, String[] data) {
            this.context = context;
            this.data = data;

        }


        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Holder holder;
            if(view==null){
                view = inflater.inflate(R.layout.act_plane_hot_city_gridview_item,null);
                holder=new Holder();
                holder.item_img=(ImageView)view.findViewById(R.id.city_selector_img);
                holder.item_tex=(TextView)view.findViewById(R.id.city_name);
                holder.city_lin = (LinearLayout)view.findViewById(R.id.city_lin);
                view.setTag(holder);
            }else{
                holder=(Holder) view.getTag();
            }

            holder.item_tex.setText(data[position]);
            if (selectorPosition == position) {
                holder.item_img.setVisibility(View.VISIBLE);
            } else {
                //其他的恢复原来的状态
                holder.item_img.setVisibility(View.INVISIBLE);
            }

            return view;
        }
        public void changeState(int pos) {
            selectorPosition = pos;
            notifyDataSetChanged();

        }
    }

    class GridViewHot extends BaseAdapter{
        private Context context=null;
        private String data[]=null;
        private int selectorPosition = -1;

        private class Holder{
            LinearLayout city_lin;
            ImageView item_img;
            TextView item_tex;
        }
        //构造方法
        public GridViewHot(Context context, String[] data) {
            this.context = context;
            this.data = data;

        }


        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Holder holder;
            if(view==null){
                view = inflater.inflate(R.layout.act_plane_hot_city_gridview_item,null);
                holder=new Holder();
                holder.item_img=(ImageView)view.findViewById(R.id.city_selector_img);
                holder.item_tex=(TextView)view.findViewById(R.id.city_name);
                holder.city_lin = (LinearLayout)view.findViewById(R.id.city_lin);
                view.setTag(holder);
            }else{
                holder=(Holder) view.getTag();
            }

            holder.item_tex.setText(data[position]);
            if (selectorPosition == position) {
                holder.item_img.setVisibility(View.VISIBLE);
            } else {
                //其他的恢复原来的状态
                holder.item_img.setVisibility(View.INVISIBLE);
            }

            return view;
        }
        public void changeState(int pos) {
            selectorPosition = pos;
            notifyDataSetChanged();

        }
    }

}