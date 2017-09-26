package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.adapter.SpinerPopWindowAdapter;
import com.handongkeji.common.SpinerPopWindow;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ParamsModle;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.FarmyardAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.fragment.WeekFragment;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/24 0024.
 * 农家院
 */

public class FarmyardActivity extends BaseActivity {

    @Bind(R.id.tab_tv_0)
    TextView tabTv0;
    @Bind(R.id.tab_type)
    LinearLayout tabType;
    @Bind(R.id.tab_tv_1)
    TextView tabTv1;
    @Bind(R.id.tab_local)
    LinearLayout tabLocal;
    @Bind(R.id.lin_tabs)
    LinearLayout linTabs;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    @Bind(R.id.tab_iv_0)
    ImageView tabIv0;
    @Bind(R.id.tab_iv_1)
    ImageView tabIv1;
    private boolean isInitialize = false;
    private boolean isVisible = false;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    List<HotRecomModel.DataBean> data_list = new ArrayList<>();

    private SpinerPopWindow mSpinerPopWindow;// 下拉菜单
    private SpinerPopWindowAdapter spinerPopWindowAdapter;
    private List<ParamsModle> Desclist; // 选择排序
    private List<ParamsModle> Locallist; // 位置选择
    private int orderid = 0, localid = 0;
    private FarmyardAdapter adapter;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmyard);
        ButterKnife.bind(this);
        init();
        initData();
    }

    private void init() {
        tabTv0.setText("选择排序");
        tabTv1.setText("位置选择");
        // 下拉框
        spinerPopWindowAdapter = new SpinerPopWindowAdapter(this);
        mSpinerPopWindow = new SpinerPopWindow(this);
        SetDescList();
        SetLocalList();
        listView.setFocusable(false);
        listView.setFocusableInTouchMode(false);
        adapter = new FarmyardAdapter(data_list, this);
        listView.setAdapter(adapter);
        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
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
        };
        swipeRefresh.setOnRefreshListener(onRefreshListener);
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
        onRefresh();
    }

    private void onRefresh() {
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }
    private void initData() {
        String url = Constants.URL_FARMYARD;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize+"");
        params.put("areaId", MyApp.getInstance().getAreaid());
        params.put("PathLng",myApp.getLng());//经度
        params.put("PathLat",myApp.getLat());//纬度
        params.put("location",localid+""); //0:不限，1：500m 2：1km 3:3km 5:5km 10:10km
        params.put("oderbyType",orderid+""); //0低价优先，1高价优先
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
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
                        data_list.clear();
                        listView.setHasMore(true);
                    }
                    if (len < pageSize) {
                        listView.setHasMore(false);
                    }
                    data_list.addAll(lines);
                    if (data_list.size() <= 0) {
                        swipeRefresh.setVisibility(View.GONE);
                        commonNoDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        swipeRefresh.setVisibility(View.VISIBLE);
                        commonNoDataLayout.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                    if (isLoadMore) {
                        isLoadMore = false;
                        listView.onLoadComplete(true);
                    }
                    if (isRefreshing) {
                        isRefreshing = false;
                        swipeRefresh.setRefreshing(false);
                    }
                } else {
                    ToastUtils.showT(FarmyardActivity.this, model.getMessage());
                }
            }
        });
    }

    private PopupWindow popu;
    boolean isPopuShow = false;

    private void showPopupWindow() {
        popu = new PopupWindow(myApp.getScreenWidth(),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setOutsideTouchable(false);
        popu.showAsDropDown(linTabs);
    }

    private boolean isPause = true;

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
        if (isPopuShow && popu != null) {
            popu.dismiss();
        }
    }

    /**
     * 设置排序下拉列表的数据
     */
    private void SetDescList() { //0低价优先，1高价优先
        Desclist = new ArrayList<>();
        ParamsModle desc = new ParamsModle(0, "低价优先");
        Desclist.add(desc);
        ParamsModle desc1 = new ParamsModle(1, "高价优先");
        Desclist.add(desc1);
    }

    /**
     * 设置位置下拉列表的数据
     */
    private void SetLocalList() { //0:不限，1：500m 2：1km 3:3km 5:5km 10:10km
        Locallist = new ArrayList<>();
        ParamsModle price = new ParamsModle(0, "不限");
        Locallist.add(price);
        ParamsModle price1 = new ParamsModle(1, "500m");
        Locallist.add(price1);
        ParamsModle price2 = new ParamsModle(2, "1km");
        Locallist.add(price2);
        ParamsModle price3 = new ParamsModle(3, "2km");
        Locallist.add(price3);
        ParamsModle price4 = new ParamsModle(5, "5km");
        Locallist.add(price4);
        ParamsModle price5 = new ParamsModle(10, "10km");
        Locallist.add(price5);
    }

    /**
     * 初始化智能排序下拉框的数据
     */
    private void initPopupWindowData(final LinearLayout lin, final TextView mText,
                                     final List<ParamsModle> typeList, final int typeid) {
        spinerPopWindowAdapter
                .refreshData(typeList, mText.getText().toString());
        mSpinerPopWindow.setAdatper(spinerPopWindowAdapter);
        mSpinerPopWindow.setItemListener(new SpinerPopWindowAdapter.IOnItemSelectListener() {

            @Override
            public void onItemClick(int pos) {
                if (pos >= 0 && pos <= typeList.size()) {
                    String Name = typeList.get(pos).getDesc();
                    if (typeid == 1) {
                        orderid = typeList.get(pos).getId();
                    } else if (typeid == 2) {
                        localid = typeList.get(pos).getId();

                    }
                    mText.setText(Name);
                    currentPage = 1;
                   onRefresh();
                }

            }
        });
        mSpinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lin.setSelected(false);
            }
        });
    }

    /**
     * 显示SpinWindow
     */
    private void showSpinWindow() {
        mSpinerPopWindow.showAsDropDown(linTabs);

    }

    @OnClick({R.id.return_lin, R.id.serch_lin, R.id.tab_type, R.id.tab_local})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.serch_lin:
                startActivity(new Intent(this, SelectActivity.class).putExtra("type",4));
                break;
            case R.id.tab_type: //选择排序
                if (tabType.isSelected()){
                    tabType.setSelected(false);
                }else{
                    tabType.setSelected(true);
                }
                initPopupWindowData(tabType,tabTv0, Desclist, 1);
                showSpinWindow();
                break;
            case R.id.tab_local:  //位置选择
                if (tabLocal.isSelected()){
                    tabLocal.setSelected(false);
                }else{
                    tabLocal.setSelected(true);
                }
                initPopupWindowData(tabLocal,tabTv1, Locallist, 2);
                showSpinWindow();
                break;
        }
    }


}
