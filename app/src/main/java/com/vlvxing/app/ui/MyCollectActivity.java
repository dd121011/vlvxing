package com.vlvxing.app.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.ByCarAdapter;
import com.vlvxing.app.adapter.FarmyardAdapter;
import com.vlvxing.app.adapter.WeekAdapter;
import com.vlvxing.app.common.Constants;
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
 * Created by Administrator on 2017/6/7 0007.
 * 我的收藏
 */

public class MyCollectActivity extends BaseActivity {

    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.lin1)
    LinearLayout lin1;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.lin2)
    LinearLayout lin2;
    @Bind(R.id.line3)
    View line3;
    @Bind(R.id.lin3)
    LinearLayout lin3;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private int type = 1;
    private LinearLayout[] lin;
    private View[] line;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    List<HotRecomModel.DataBean> data_list = new ArrayList<>();
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollect_layout);
        ButterKnife.bind(this);
        lin = new LinearLayout[]{lin1, lin2, lin3};
        line = new View[]{line1, line2, line3};
        lin1.setSelected(true);

//        lin1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remarkType(lin1, 1);
//            }
//        });

        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarkType(lin2, 2,line2);
            }
        });

        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarkType(lin3, 3,line3);
            }
        });


//        adapter = new myAdapter(data_list, this);
//        listView.setAdapter(adapter);
        initData();

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
        String url = Constants.URL_MYCOLLECT;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("token", myApp.getUserTicket());
        params.put("type", type + ""); //1.线路  2.农家院 3.用车
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
                    listView.setVisibility(View.VISIBLE);
                    if (type == 1) {
                        WeekAdapter adapter = new WeekAdapter(data_list, MyCollectActivity.this);
                        listView.setAdapter(adapter);
                    } else if (type == 2) {
                        FarmyardAdapter adapter = new FarmyardAdapter(data_list, MyCollectActivity.this);
                        listView.setAdapter(adapter);
                    } else if (type == 3) {
                        ByCarAdapter adapter = new ByCarAdapter(data_list, MyCollectActivity.this);
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
                    ToastUtils.showT(MyCollectActivity.this, model.getMessage());
                }
            }
        });
    }


    private void remarkType(LinearLayout linear, int t,View lines) {
        listView.setVisibility(View.GONE);
        for (int i = 0; i < lin.length; i++) {
            if (linear != lin[i]) {
                lin[i].setSelected(false);
            }
        }
        for (int j = 0; j < line.length; j++) {
            if (lines != line[j]) {
                line[j].setVisibility(View.GONE);
            }
        }
        linear.setSelected(true);
        lines.setVisibility(View.VISIBLE);
        type = t;
        onRefresh();
    }

    @OnClick({R.id.return_lin, R.id.lin1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.lin1:
                remarkType(lin1, 1,line1);
                break;
        }
    }
}
