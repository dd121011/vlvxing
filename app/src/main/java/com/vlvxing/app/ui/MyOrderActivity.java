package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.MyOrderModel;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 *
 * @ClassName:MyOrderActivity
 * @PackageName:com.vlvxing.app.ui.mine
 * @Create On 2017/5/23 0023   15:59
 * @Site:http://www.handongkeji.com
 * @author:zhouhao
 * @Copyrights 2017/5/23 0023 handongkeji All rights reserved.
 */
public class MyOrderActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_travel)
    TextView tvTravel;
    @Bind(R.id.guideline)
    Guideline guideline;
    @Bind(R.id.tv_usecar)
    TextView tvUsecar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    private int type = 1,t = 1;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    myAdapter adapter;
    private List<MyOrderModel.DataBean> recom_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initData();
        initView();
        initTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void initTab() {
        final String[] tabTitles = {"全部", "待付款", "待评价"};
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                type = position+1;
                currentPage=1;
                onRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < tabTitles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab().setText(tabTitles[i]);
            tabLayout.addTab(tab);
        }
    }


    private void initView() {
        tvTravel.setSelected(true);
        adapter = new myAdapter(recom_list, this);
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
    }

    private void onRefresh() {
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }

    private void initData() {
        String url = Constants.URL_MYORDER;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("statutType", type + ""); //1.全部  2待付款  3待评价
        params.put("orderType", t + ""); //1.出行订单  2用车订单
        params.put("token", myApp.getUserTicket());
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                MyOrderModel model = gson.fromJson(json, MyOrderModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    List<MyOrderModel.DataBean> lines = model.getData();
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
                    ToastUtils.showT(MyOrderActivity.this, model.getMessage());
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.tv_travel, R.id.tv_usecar, R.id.common_click_retry_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_travel:
                tvTravel.setSelected(true);
                tvUsecar.setSelected(false);
                t = 1;
                onRefresh();
                break;
            case R.id.tv_usecar:
                tvTravel.setSelected(false);
                tvUsecar.setSelected(true);
                t = 2;
                onRefresh();
                break;
            case R.id.common_click_retry_tv:
                onRefresh();
                break;
        }
    }

    class myAdapter extends BaseAdapter {
        Context context;
        List<MyOrderModel.DataBean> list;

        public myAdapter(List<MyOrderModel.DataBean> list, Context context) {
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
                        R.layout.myorder_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final MyOrderModel.DataBean bean = list.get(position);
            //订单状态   0待支付   1已支付/待评价  2已评价   3已取消
            String orderstatus = bean.getOrderstatus();
            final int s = Integer.valueOf(orderstatus);
            if (s == 0) {
                holder.statusTxt.setText("待付款");
                holder.statusTxt.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.red_btn));
//                holder.statusTxt.setBackgroundDrawable(getDrawable(R.drawable.red_btn)); 主要原因是因为是版本不对，如果是android 5.0以上的手机是支持的
            } else if (s == 1) {
                holder.statusTxt.setText("已付款"); //待评价
                holder.statusTxt.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.blue_btn));
//                holder.statusTxt.setBackgroundDrawable(getDrawable(R.drawable.blue_btn));
            } else if (s == 2) {
                holder.statusTxt.setText("已评价");
                holder.statusTxt.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.lowgray_btn));
//                holder.statusTxt.setBackgroundDrawable(getDrawable(R.drawable.lowgray_btn));
            } else if (s == 3) {
                holder.statusTxt.setText("已取消");
                holder.statusTxt.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.lowgray_btn));
//                holder.statusTxt.setBackgroundDrawable(getDrawable(R.drawable.lowgray_btn));
            }
            holder.contentTxt.setText(bean.getOrdername());
            double price = bean.getOrderallprice();
            int p = (int) price;
            holder.priceTxt.setText(p + "");
            String bgUrl = bean.getOrderpic();
            if (StringUtils.isStringNull(bgUrl)) {
                holder.carImg.setImageResource(R.mipmap.myinfo_bg);
            } else {
                Glide.with(context).load(bgUrl).into(holder.carImg);
            }
            String id=bean.getOrderid();
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("data",bean)); //传实体类
                    context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("id",id));
                }
            });
            return convertView;

        }


        class ViewHolder {
            @Bind(R.id.car_img)
            ImageView carImg;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.price_txt)
            TextView priceTxt;
            @Bind(R.id.txt)
            TextView txt;
            @Bind(R.id.status_txt)
            TextView statusTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
