package com.vlvxing.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.StringUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
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
 * Created by Administrator on 2017/4/13 0013.
 * 热玩Fragment
 */

public class FuJinFragment extends Fragment {
    Context mcontext;
    @Bind(R.id.list_view)
    XRecyclerView listView;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private boolean isInitialize = false;
    private boolean isVisible = false;
    private boolean isRefreshing = true;
    private int currentPage = 1;
    private int pageSize = 10;
    List<HotRecomModel.DataBean> data_list = new ArrayList<>();
    private myAdapter adapter;
    private LinearLayoutManager layout;
    private int type, t;

    public static FuJinFragment getInstance(int type, int t) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("t", t); // 0附近  1国内 2国外
        FuJinFragment instance = new FuJinFragment();
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
        View view = inflater.inflate(R.layout.common_list, container, false);
        ButterKnife.bind(this, view);
        init();
//        lazyLoad();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setFocusable(false);
        listView.setFocusableInTouchMode(false);
    }

    private void init() {
        listView.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
        adapter = new myAdapter(data_list, mcontext);
        listView.setAdapter(adapter);
        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listView.setNestedScrollingEnabled(false); //解觉滑动冲突
        listView.setPullRefreshEnabled(false);
        listView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate); //BallRotate 三个点文字显示正在加载
        listView.setArrowImageView(R.drawable.iconfont_downgrey);

        listView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefreshing = false;
                currentPage++;
                initData();
            }
        });
        initData();
    }

    private void initData() {
        String url;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        Bundle bundle = getArguments();
        type = bundle.getInt("type", 0); //国内外：1全部  2特色  3特价  4跟团/免签   5排序      附近：1全部  2自助  3特价  4主题   5排序
        t = bundle.getInt("t", 0); // 0 附近  1国内 2国外
        if (t==0){
            url = Constants.URL_PRODUCTLIST2;
            params.put("areaId",MyApp.getInstance().getAreaid());
            if (type == 2) {
                params.put("isSelf", "1");
            }
            if (type == 3) {
                params.put("isSpecialPrice", "1");
            }
            if (type == 4) {
                params.put("isTheme", "1");
            }
            if (type == 5) {
                params.put("isOrder", "1");
            }
        }else {
            url = Constants.URL_PRODUCTLIST1;
            params.put("isForeign", t + "");
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
                    }
                    if (len < pageSize) {
                        listView.setNoMore(true);
                    }
                    data_list.addAll(lines);
                    if (data_list.size() <= 0) {
                        listView.setVisibility(View.GONE);
                        commonNoDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        commonNoDataLayout.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                    if (isRefreshing) {
                        listView.refreshComplete();
                    } else {
                        listView.loadMoreComplete();
                    }
                    isRefreshing = true;
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
//            lazyLoad();
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
//                initData();
                break;

        }
    }

    class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
        Context context;
        List<HotRecomModel.DataBean> list;

        public myAdapter(List<HotRecomModel.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.fujin_item, parent, false);
            return new myAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final myAdapter.ViewHolder holder, int position) {
            final HotRecomModel.DataBean bean = list.get(position);
            holder.nameTxt.setText(bean.getProductname());
            holder.contentTxt.setText(bean.getContext());
            double price=bean.getPrice();
            int p=(int)price;
            holder.priceTxt.setText("￥"+p);
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
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id", bean.getTravelproductid()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size() > 0 ? list.size() : 0;
//            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.bg_img)
            ImageView bgImg;
            @Bind(R.id.name_txt)
            TextView nameTxt;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.price_txt)
            TextView priceTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
