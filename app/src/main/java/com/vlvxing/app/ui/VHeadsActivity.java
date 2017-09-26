package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.VheadsModel;
import com.vlvxing.app.utils.DataUtils;
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
 * 消息列表
 */

public class VHeadsActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    List<VheadsModel.DataBean> data_list = new ArrayList<>();
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private int type;
    private myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vheads_layout);
        ButterKnife.bind(this);
        headTitle.setText("V头条");
        adapter = new myAdapter(data_list, this);
        listView.setAdapter(adapter);
        initData();
    }


    private void initData() {
        showDialog("数据加载中...");
        String url = Constants.URL_VHEADS;
        HashMap<String, String> params = new HashMap<>();
//        params.put("currentPage", currentPage + "");
//        params.put("pageSize", pageSize + "");
//        params.put("areaId", "1");
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                VheadsModel model = gson.fromJson(json, VheadsModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    List<VheadsModel.DataBean> lines = model.getData();
//                    int len = lines.size();
//                    if (currentPage == 1) {
//                        data_list.clear();
//                        listView.setHasMore(true);
//                    }
//                    if (len < pageSize) {
//                        listView.setHasMore(false);
//                    }
                    data_list.addAll(lines);
                    if (data_list.size() <= 0) {
                        listView.setVisibility(View.GONE);
                        commonNoDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        commonNoDataLayout.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
//                    if (isLoadMore) {
//                        isLoadMore = false;
//                        listView.onLoadComplete(true);
//                    }
//                    if (isRefreshing) {
//                        isRefreshing = false;
//                        swipeRefresh.setRefreshing(false);
//                    }
                } else {
                    ToastUtils.showT(VHeadsActivity.this, model.getMessage());
                }
                dismissDialog();
            }
        });
    }


    class myAdapter extends BaseAdapter {
        Context context;
        List<VheadsModel.DataBean> list;

        public myAdapter(List<VheadsModel.DataBean> list, Context context) {
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
                        R.layout.vheads_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final VheadsModel.DataBean bean = list.get(position);
            holder.titleTxt.setText(bean.getHeadname());
            String t = DataUtils.format(bean.getTime(), "HH:mm");
            holder.timeTxt.setText(t);
            String d = DataUtils.format(bean.getTime(), "yyyy-MM-dd");
            holder.dataTxt.setText(d);
            String bgUrl = bean.getHeadpic();
                Glide.with(context).load(bgUrl).error(R.mipmap.photo_pingjia).into(holder.img);

            String id=bean.getVheadid();
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,VheadsDetailActivity.class).putExtra("bean",bean));
                }
            });
            return convertView;
        }


         class ViewHolder {
            @Bind(R.id.title_txt)
            TextView titleTxt;
            @Bind(R.id.data_txt)
            TextView dataTxt;
            @Bind(R.id.time_txt)
            TextView timeTxt;
            @Bind(R.id.img)
            ImageView img;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick({R.id.return_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
        }
    }
}
