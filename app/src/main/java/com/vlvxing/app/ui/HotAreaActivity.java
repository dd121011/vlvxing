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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handongkeji.adapter.SpinerPopWindowAdapter;
import com.handongkeji.common.SpinerPopWindow;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ParamsModle;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.SelestorCityActivity;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.WeekAdapter;
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
 * 首页--热门目的地
 */

public class HotAreaActivity extends BaseActivity {

    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private boolean isLoadMore;
    private boolean isRefreshing = true;
    private int currentPage = 1;
    private int pageSize = 5;
    List<HotRecomModel.DataBean> data_list = new ArrayList<>();

    private WeekAdapter adapter;
    private String areaid,isforeign;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noloadcommon_list);
        ButterKnife.bind(this);
        init();
        initData();
    }

    private void init() {
        Intent intent=getIntent();
        isforeign=intent.getStringExtra("isforeign");
        areaid=intent.getStringExtra("areaid");
        commonNoDataLayout.setVisibility(View.GONE);
        adapter = new WeekAdapter(data_list,this);
        listView.setAdapter(adapter);
        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isRefreshing) return;
                isRefreshing = true;
                currentPage = 1;
                initData();
            }
        };
        swipeRefresh.setColorSchemeResources(R.color.color_ea5413);
        swipeRefresh.setOnRefreshListener(onRefreshListener);

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

    private void onRefresh(){
        swipeRefresh.setVisibility(View.VISIBLE);
        commonNoDataLayout.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }


    private void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        String url = Constants.URL_PRODUCTLIST1;
        params.put("isForeign", isforeign); //1国内 2国外
        params.put("areaId",areaid);
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
                    ToastUtils.showT(HotAreaActivity.this, model.getMessage());
                }
            }
        });
    }

    @OnClick({R.id.return_lin, R.id.serch_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.serch_lin:
                startActivity(new Intent(this, SelectActivity.class).putExtra("type",3));
                break;
        }
    }

}
