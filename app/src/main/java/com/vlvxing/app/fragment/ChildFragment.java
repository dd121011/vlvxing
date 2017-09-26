package com.vlvxing.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.ui.LineDetailsActivity;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class ChildFragment extends Fragment {

    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh; // 上拉刷新，下拉加載
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private Context context;
    private boolean isInitialize = false;
    private boolean isVisible = false;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    List<HotRecomModel.DataBean> data_list = new ArrayList<>();
    private myAdapter adapter;
    private String type="1";//0是当季游玩，1是热门推荐
    private LinearLayoutManager layout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        Log.d("aaa", "onAttach: ...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.loadcommon_list, container, false);
        ButterKnife.bind(this, view);
        init();
        initData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInitialize=true;
        lazyLoad();
    }
    private void init() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");

//        layout = new LinearLayoutManager(context);
//        listView.setLayoutManager(layout);
        adapter = new myAdapter(data_list, context);
//        listView.setFocusable(true);
//        listView.setFocusableInTouchMode(false);
//        listView.requestFocus();
        listView.setAdapter(adapter);

        onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
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
//        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        adapter = new myAdapter(data_list, context);
//        listView.setAdapter(adapter);
////        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        listView.setNestedScrollingEnabled(false);
//        listView.setPullRefreshEnabled(false);
//        listView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate); //BallRotate 三个点文字显示正在加载
//        listView.setArrowImageView(R.drawable.iconfont_downgrey);
//
//        listView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                currentPage = 1;
//                initData();
//            }
//
//            @Override
//            public void onLoadMore() {
//                isRefreshing = false;
//                currentPage++;
//                initData();
//            }
//        });

    }

    private void onRefresh(){
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }
    private void initData() {
        String url = Constants.URL_RECOMMEND;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("typenum", type); //1热门推荐 2当季游玩
        RemoteDataHandler.asyncPost(url, params, context, true, new RemoteDataHandler.Callback() {
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
//                        listView.setNoMore(true);
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
//                    if (isRefreshing) {
//                        listView.refreshComplete();
//                    } else {
//                        listView.loadMoreComplete();
//                    }
//                    isRefreshing = true;
                } else {
                    ToastUtils.showT(context, model.getMessage());
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
        Log.d("aaa", "setUserVisibleHint: ...");
        if (isVisibleToUser) {
            isVisible = isVisibleToUser;
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (isVisible && isInitialize) {
            onRefresh();
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
                currentPage=1;
                lazyLoad();
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
            return list == null ? 0 : list.size();
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
                        R.layout.hotrecommend_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HotRecomModel.DataBean bean = list.get(position);
            holder.nameTxt.setText(bean.getProductname());
            holder.contentTxt.setText(bean.getIntroduction());
            // 获取图片的宽度
            int img_width = MyApp.getInstance().getScreenWidth();
            ViewGroup.LayoutParams params = holder.bgImg.getLayoutParams();
            params.height = (img_width * 137) / 375; // 750:500
            holder.bgImg.setLayoutParams(params);
            String bgUrl = bean.getAdvertisebigpic();
            if (StringUtils.isStringNull(bgUrl)) {
                holder.bgImg.setImageResource(R.mipmap.myinfo_bg);
            } else {
                Glide.with(context).load(bean.getAdvertisebigpic()).into(holder.bgImg);
            }
            holder.itemRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id",bean.getTravelproductid()));
                }
            });
            return convertView;

        }

        class ViewHolder{
            @Bind(R.id.bg_img)
            ImageView bgImg;
            @Bind(R.id.name_txt)
            TextView nameTxt;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.item_rel)
            RelativeLayout itemRel;
            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
//
//class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
//    Context context;
//    List<HotRecomModel.DataBean> list;
//
//    public myAdapter(List<HotRecomModel.DataBean> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.hotrecommend_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        final HotRecomModel.DataBean bean = list.get(position);
//        holder.nameTxt.setText(bean.getProductname());
//        holder.contentTxt.setText(bean.getIntroduction());
//        // 获取图片的宽度
//        int img_width = MyApp.getInstance().getScreenWidth();
//        ViewGroup.LayoutParams params = holder.bgImg.getLayoutParams();
//        params.height = (img_width * 137) / 375; // 750:500
//        holder.bgImg.setLayoutParams(params);
////        Glide.with(context).load(bean.getAdvertisebigpic()).into(holder.bgImg);
//        holder.itemRel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id",bean.getTravelproductid()));
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size() > 0 ? list.size() : 0;
////            return 5;
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        @Bind(R.id.bg_img)
//        ImageView bgImg;
//        @Bind(R.id.name_txt)
//        TextView nameTxt;
//        @Bind(R.id.content_txt)
//        TextView contentTxt;
//        @Bind(R.id.item_rel)
//        RelativeLayout itemRel;
//        ViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//}

}
