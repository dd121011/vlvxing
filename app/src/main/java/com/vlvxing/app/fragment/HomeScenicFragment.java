package com.vlvxing.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.ByCarAdapter;
import com.vlvxing.app.adapter.WeekAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.ui.LineDetailsActivity;
import com.vlvxing.app.ui.ScenicCarActivity;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/13 0013.
 * 热门景点
 */

public class HomeScenicFragment extends Fragment {
    Context mcontext;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private boolean isInitialize = false;
    private boolean isVisible = false;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private int currentPage = 1;
    private int pageSize = 5;
    List<HotRecomModel.DataBean> data_list = new ArrayList<>();
    private myAdapter adapter;
    private LinearLayoutManager layout;
    private int type,t;
    private String areaid,isforeign;

    public static HomeScenicFragment getInstance(int type,int t,String isforeign,String id) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("t", t);
        bundle.putString("isforeign",isforeign);
        bundle.putString("areaid",id);
        HomeScenicFragment instance = new HomeScenicFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mcontext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loadcommon_list, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInitialize=true;
        lazyLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setFocusable(false);
        listView.setFocusableInTouchMode(false);
    }

    private void init() {
        commonNoDataLayout.setVisibility(View.GONE);
        adapter = new myAdapter(data_list,mcontext);
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
        Bundle bundle = getArguments();
        type = bundle.getInt("type", 0); //国内外：1全部  2特色  3特价  4跟团/免签   5排序
        t = bundle.getInt("t", 1); //1 国内  2国外
        isforeign = bundle.getString("isforeign"); //1国内 2国外
        areaid=bundle.getString("areaid");
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        String url = Constants.URL_PRODUCTLIST1;
        params.put("areaId",areaid);
        params.put("isForeign", isforeign);
        if (type == 2) {
            params.put("isFeature", "1");
        }
        if (type == 3) {
            params.put("isSpecialPrice", "1");
        }
        if (t==1){ //跟团/免签
            if (type == 4) {
                params.put("isGroup", "1");
            }
        }else if (t==2){
            if (type == 4) {
                params.put("isVisa", "1");
            }
        }
        if (type == 5) {
            params.put("isOrder", "1");
        }
        RemoteDataHandler.asyncPost(url, params, mcontext, true, new RemoteDataHandler.Callback() {
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
                    ToastUtils.showT(mcontext, model.getMessage());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = isVisibleToUser;
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (isVisible && isInitialize) {
            initData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ;
    }

    @OnClick({R.id.common_click_retry_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_click_retry_tv:
                onRefresh();
                break;

        }
    }

    class myAdapter extends BaseAdapter {
        Context context;
        List<HotRecomModel.DataBean> list;

        public myAdapter(List<HotRecomModel.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
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
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.sceniccar_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HotRecomModel.DataBean bean = list.get(position);
            holder.contentTxt.setText(bean.getProductname());
            String bgUrl = bean.getProductsmallpic();
            Glide.with(context).load(bean.getAdvertisebigpic()).error(R.mipmap.qiche).into(holder.carImg);
            holder.txt.setVisibility(View.VISIBLE);
            double price=bean.getPrice();
            int p=(int)price;
            holder.priceTxt.setText(p+"");
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id", bean.getTravelproductid()));
                }
            });
            return convertView;
        }

        class ViewHolder{
            @Bind(R.id.car_img)
            ImageView carImg;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.price_txt)
            TextView priceTxt;
            @Bind(R.id.txt)
            TextView txt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
