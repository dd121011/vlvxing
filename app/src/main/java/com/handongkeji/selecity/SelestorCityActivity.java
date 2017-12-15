package com.handongkeji.selecity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.model.PinyinComparator;
import com.handongkeji.selecity.model.SortModel;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.CharacterParser;
import com.handongkeji.widget.KeyboardLayout1;
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


public class SelestorCityActivity extends BaseActivity implements SideBar.OnTouchingLetterChangedListener, KeyboardLayout1.onKybdsChangeListener {
    List<String> resourseData = new ArrayList<String>();
    @Bind(R.id.root)
    KeyboardLayout1 root;
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.line)
    View line;
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
    SideBar sideBar;
    @Bind(R.id.local_txt)
    TextView localTxt;
    @Bind(R.id.select_edt)
    EditText selectEdt;
    @Bind(R.id.exit_txt)
    TextView exitTxt;
    @Bind(R.id.select_lin)
    LinearLayout selectLin;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private PinyinComparator pinyinComparator;
    public JSONArray jsonArrays;
    private int type;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selestor_city);
        ButterKnife.bind(this);
        init();// 初始化控件
        loadData();// 获取首页列表

    }

    private void init() {
        commonNoDataLayout.setVisibility(View.GONE);
        intent = getIntent();
        type = intent.getIntExtra("type", 0); //判断是否是从定制游进入的 1是
        if (type == 0) {
            line.setVisibility(View.VISIBLE);
            localTxt.setVisibility(View.VISIBLE);
            selectLin.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.GONE);
            localTxt.setVisibility(View.GONE);
            selectLin.setVisibility(View.VISIBLE);
        }

        localTxt.setText("当前位置:" + myApp.getCity_name());
        headTitle.setText("选择城市");

        //设置第一次进入界面不弹出软键盘
        headTitle.setFocusable(true);
        headTitle.setFocusableInTouchMode(true);
        headTitle.requestFocus();

        root.setOnkbdStateListener(this);

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);
        adapter = new SortAdapter(SelestorCityActivity.this);
        countryLvcountry.setAdapter(adapter);
        sideBar.setOnTouchingLetterChangedListener(this);
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

    @OnClick({R.id.return_lin, R.id.exit_txt, R.id.local_txt, R.id.common_click_retry_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.exit_txt:
                sideBar.setVisibility(View.VISIBLE);
                countryLvcountry.setVisibility(View.VISIBLE);
                resultList.setVisibility(View.GONE);
                //设置第一次进入界面不弹出软键盘
                headTitle.setFocusable(true);
                headTitle.setFocusableInTouchMode(true);
                headTitle.requestFocus();
                selectEdt.setText("");
                selectEdt.setHint("搜索国家、城市");
                break;
            case R.id.local_txt:
                break;
            case R.id.common_click_retry_tv:

                break;
        }
    }

    /**
     * 软件盘状态的监听
     */
    @Override
    public void onKeyBoardStateChange(int state) {
        switch (state) {
            case KeyboardLayout1.KEYBOARD_STATE_HIDE: // 软键盘隐藏

                break;
            case KeyboardLayout1.KEYBOARD_STATE_SHOW: // 软键盘显示
                sideBar.setVisibility(View.GONE);
                countryLvcountry.setVisibility(View.GONE);
                break;
            default:
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
                            if (type == 0) {
                                myApp.setCity_name(areaname);
                                myApp.setAreaid(areaid);
                                finish();
                            } else {
                                intent.putExtra("areaname", areaname);
                                intent.putExtra("areaid", areaid);
                                setResult(4, intent);
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
        int position = adapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            countryLvcountry.setSelection(position);
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
                                resultAdapter adapter = new resultAdapter(SelestorCityActivity.this, result);
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
                            List<SortModel> mSortList = new ArrayList<SortModel>();
                            if (array.length() > 0) {
                                commonNoDataLayout.setVisibility(View.GONE);
                                countryLvcountry.setVisibility(View.VISIBLE);
                                for (int i = 0; i < array.length(); i++) {
//                                JSONObject jsonObject = array.getJSONObject(i);
//                                String cityDatas = jsonObject.getString("children");
//                                JSONArray cityArray = new JSONArray(cityDatas);
//                                for (int j = 0; j < cityArray.length(); j++) {
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

                                SourceDateList = mSortList;
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
                    finish();
                    if (type == 0) {
                        myApp.setCity_name(name);
                        myApp.setAreaid(locationId);
                        finish();
//                        startActivity(new Intent(mContext, MainActivity.class));
                    } else {
                        intent.putExtra("areaname", name);
                        intent.putExtra("areaid", locationId);
                        setResult(4, intent);
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

}