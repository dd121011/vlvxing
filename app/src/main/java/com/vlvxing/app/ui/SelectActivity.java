package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handongkeji.adapter.SearchAutoAdapter;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.modle.SearchAutoData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.CallDialog;
import com.handongkeji.widget.KeyboardLayout1;
import com.handongkeji.widget.MyListView;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.ByCarAdapter;
import com.vlvxing.app.adapter.FarmyardAdapter;
import com.vlvxing.app.adapter.FuJinAdapter;
import com.vlvxing.app.adapter.HotRecomAdapter;
import com.vlvxing.app.adapter.WeekAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/27 0027.
 * 搜索
 */

public class SelectActivity extends BaseActivity implements KeyboardLayout1.onKybdsChangeListener{

    public static final String SEARCH_HISTORY = "search_history";
    @Bind(R.id.select_txt)
    TextView selectTxt;
    @Bind(R.id.select_edt)
    EditText selectEdt;
    @Bind(R.id.exit_txt)
    TextView exitTxt;
    @Bind(R.id.history_grid)
    GridView historyGrid;
    @Bind(R.id.history_lin)
    LinearLayout historyLin;
    @Bind(R.id.city_list)
    ListView resultList;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private SearchAutoAdapter mSearchAutoAdapter;
    private String title;
    private int type;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    List<HotRecomModel.DataBean> recom_list = new ArrayList<>();
    private String areaid;
    private String isforegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_layout);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        init();

      swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
          @Override
          public void onRefresh() {
              if (isRefreshing) return;
              isRefreshing = true;
              currentPage = 1;
              swipeRefresh.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      initData();
                  }
              }, 1000);
          }
      });
        swipeRefresh.setColorSchemeResources(R.color.color_ea5413);
        listView.setLoadDataListener(new MyListView.LoadDataListener() {
            @Override
            public void onLoadMore() {
                if (isLoadMore) return;
                isLoadMore = true;
                currentPage++;
                initData();
            }
        });
    }

    private void init() {
        swipeRefresh.setVisibility(View.GONE);
        selectEdt.setVisibility(View.VISIBLE);
        selectTxt.setVisibility(View.GONE);
        exitTxt.setVisibility(View.VISIBLE);

        // 历史记录
        mSearchAutoAdapter = new SearchAutoAdapter(this, -1, null);
        historyGrid.setAdapter(mSearchAutoAdapter);
        historyGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                SearchAutoData data = (SearchAutoData) mSearchAutoAdapter
                        .getItem(position);
                selectEdt.setText(data.getContent());
                historyLin.setVisibility(View.GONE);
                resultList.setVisibility(View.VISIBLE);
                selectCity();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(SelectActivity.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        /**
         * 编辑框监听事件
         */
        selectEdt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                saveSearchHistory(); // 保存搜索的历史记录
                mSearchAutoAdapter.initSearchHistory(); // 读取搜索的历史记录
                mSearchAutoAdapter.performFiltering(s);
                if (selectEdt.getText().toString().length() > 0) {
                    historyLin.setVisibility(View.GONE);
                    resultList.setVisibility(View.VISIBLE);
                    swipeRefresh.setVisibility(View.GONE);
                    selectCity();
                } else {
//                    listView.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int len = s.length();
                selectEdt.setSelection(len, len); // 编辑框的焦点位于文本之后
            }
        });

        /**
         * 键盘搜索事件
         */
        selectEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                title = selectEdt.getText().toString();
                if (title.length() <= 0) {
                    Toast.makeText(SelectActivity.this, "请输入搜索内容",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        saveSearchHistory(); // 保存搜索的历史记录
                        mSearchAutoAdapter.initSearchHistory(); // 读取搜索的历史记录
//                        search_list.setHasMore(true);
                        historyLin.setVisibility(View.GONE);
                        if (title != null && !title.equals("")) {
                            title = title.replace(" ", ""); // 去除字符串所有的空格
//                            initData(areaId);
//                            currentPage = 1;
                        }
                    }
                }
                return false;
            }
        });
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
                               resultAdapter adapter = new resultAdapter(SelectActivity.this, result);
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
    /*
	 * 保存搜索记录
	 */
    private void saveSearchHistory() {
        title = selectEdt.getText().toString().trim();
        if (title.length() < 1) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (title.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, title);
        }
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, title + ",").commit();
        }
    }


    private void clearHistory(){
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        sp.edit().putString(SEARCH_HISTORY, "").commit();
    }

    @OnClick({R.id.return_lin, R.id.exit_txt,R.id.clear_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.exit_txt:
                selectEdt.setText(""); // 取消
                historyLin.setVisibility(View.VISIBLE);
                swipeRefresh.setVisibility(View.GONE);
                resultList.setVisibility(View.GONE);
                commonNoDataLayout.setVisibility(View.GONE);
                mSearchAutoAdapter.notifyDataSetChanged();
                break;
            case R.id.clear_txt:  //清除历史记录
                final CallDialog dialog=new CallDialog(this,"确定清空历史记录吗?");
                dialog.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearHistory();
                        mSearchAutoAdapter.clear();
                        dialog.dismissDialog();
                    }
                });
                break;
        }
    }

    @Override
    public void onKeyBoardStateChange(int state) {
        switch (state) {
            case KeyboardLayout1.KEYBOARD_STATE_HIDE: // 软键盘隐藏
                historyLin.setVisibility(View.VISIBLE);
                break;
            case KeyboardLayout1.KEYBOARD_STATE_SHOW: // 软键盘显示
                historyLin.setVisibility(View.GONE);
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
                        R.layout.city_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            JSONObject obj = list.get(position);
            try {
                final String areaname = obj.getString("areaname");


                String areanamewithspell=obj.getString("areanamewithspell");
                holder.cityTxt.setText(areaname);
                holder.pinyinTxt.setText(areanamewithspell);
                holder.itemLin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resultList.setVisibility(View.GONE);
                        swipeRefresh.setVisibility(View.VISIBLE);
                        try {
                            areaid = obj.getString("parentareaid");
                            isforegin=obj.getString("isforegin");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initData();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return convertView;
        }


        class ViewHolder {
            @Bind(R.id.city_txt)
            TextView cityTxt;
            @Bind(R.id.pinyin_txt)
            TextView pinyinTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private void initData() {
        String url = Constants.URL_PRODUCTLIST1;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("areaId",areaid);
        params.put("isForeign",isforegin);

        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                HotRecomModel model = gson.fromJson(json, HotRecomModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    List<HotRecomModel.DataBean> lines = model.getData();
                    int len = lines.size();
                    if (currentPage == 1) {
                        recom_list.clear();
                        listView.setHasMore(true);
                    }
                    if (len < pageSize) {
                        listView.setHasMore(false);
                    }
                    recom_list.addAll(lines);
                    if (recom_list.size() <= 0) {
                        swipeRefresh.setVisibility(View.GONE);
                        commonNoDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        swipeRefresh.setVisibility(View.VISIBLE);
                        commonNoDataLayout.setVisibility(View.GONE);
                    }
                    if (type==1){ //首页
                        HotRecomAdapter adapter=new HotRecomAdapter(recom_list,SelectActivity.this);
                        listView.setAdapter(adapter);
                    }else  if (type==2){ //附近 国内 国外
                        FuJinAdapter adapter=new FuJinAdapter(recom_list,SelectActivity.this);
                        listView.setAdapter(adapter);
                    }else if (type==3){ //自驾游、周末游、热门目的地、热门景点
                        WeekAdapter adapter=new WeekAdapter(recom_list,SelectActivity.this);
                        listView.setAdapter(adapter);
                    }else if (type==4){ //农家院
                        FarmyardAdapter adapter=new FarmyardAdapter(recom_list,SelectActivity.this);
                        listView.setAdapter(adapter);
                    }else if (type==5){ //景点用车
                        ByCarAdapter adapter=new ByCarAdapter(recom_list,SelectActivity.this);
                        listView.setAdapter(adapter);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        listView.onLoadComplete(true);
                    }
                    if (isRefreshing) {
                        isRefreshing = false;
                        swipeRefresh.setRefreshing(false);
                    }

                } else {
                    ToastUtils.showT(SelectActivity.this, model.getMessage());
                }
            }
        });

    }
}
