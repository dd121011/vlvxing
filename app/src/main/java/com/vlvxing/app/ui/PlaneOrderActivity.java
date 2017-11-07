package com.vlvxing.app.ui;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.MyProcessDialog;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.MessageModel;
import com.vlvxing.app.model.plane.PlaneOrderBean;
import com.vlvxing.app.utils.ToastUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 */

public class PlaneOrderActivity extends BaseActivity {
    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键

    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;


    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private int flag = 0;//记录当前页面 改签0  退票1




    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 10;
    ArrayList<PlaneOrderBean> data_list = new ArrayList<PlaneOrderBean>();
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_plane_order);
        ButterKnife.bind(this);
        mcontext = this;
//        goCity = getIntent().getStringExtra("goCity");//出发城市
//        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
//        date = getIntent().getStringExtra("date");//出发日期
        headTitle.setText("机票订单");
//        mList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            mList.add(i + "");
//        }

        initData();

        adapter = new MyAdapter(data_list, mcontext);
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

    private void initData(){
        data_list = new ArrayList<PlaneOrderBean>();
        PlaneOrderBean bean = new PlaneOrderBean();
        bean.setArrCity("深圳");
        bean.setDate("17-9-23");
        bean.setDptCity("北京");
        bean.setPrice(1120);
        bean.setState(1);
        data_list.add(bean);
        data_list.add(bean);
        data_list.add(bean);
        data_list.add(bean);
//        listView.setVisibility(View.VISIBLE);

    }

    class MyAdapter extends BaseAdapter {
        Context context;
        ArrayList<PlaneOrderBean> list;

        public MyAdapter(ArrayList<PlaneOrderBean> list, Context context) {
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
            MyAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.act_my_plane_order_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final PlaneOrderBean bean = list.get(position);
            String title;
            if ( bean.getState() == 1) {
                holder.state.setText("已完成");
            } else {
                title = "未完成";
            }
            holder.cityLeft.setText(bean.getDptCity());
            holder.cityRight.setText(bean.getArrCity());
            holder.price.setText(bean.getPrice()+"");



            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //行单击
                    Intent intent = new Intent(mcontext,PlaneCompletedOrderActivity.class);
                    startActivity(intent);
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    deleteMsg(bean.getId());
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }


        class ViewHolder {
            public TextView state;//订单状态
            public TextView cityLeft;//出发城市
            public TextView cityRight;//到达城市
            public TextView price;//订单价格
            public TextView delete;
            public LinearLayout layout;

            ViewHolder(View itemView) {
                state = (TextView) itemView.findViewById(R.id.state);
                cityLeft = (TextView) itemView.findViewById(R.id.city_left);
                cityRight = (TextView) itemView.findViewById(R.id.city_right);
                price = (TextView) itemView.findViewById(R.id.price);
                delete = (TextView) itemView.findViewById(R.id.tv_delete);
                layout = (LinearLayout) itemView.findViewById(R.id.item_layout);

            }
        }
    }


    protected void deleteMsg(String msgId) {
        MyProcessDialog dialog = new MyProcessDialog(this);
        dialog.show();
        String url = Constants.URL_DELETE_MSG;
        HashMap<String, String> params = new HashMap<>();
        params.put("msgId", msgId);
        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                Log.d("aaa", "dataLoaded: " + data);
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    dialog.dismiss();
                    return;
                }
                try {
                    JSONObject obj = new JSONObject(json);
                    String status = obj.getString("status");
                    String message = obj.getString("message");
                    if (status.equals("1")) {
                        Toast.makeText(mcontext, "删除成功", Toast.LENGTH_SHORT).show();
                        SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                        if (viewCache != null) {
                            viewCache.smoothClose();
                        }
                        onRefreshListener.onRefresh();
                    } else {
                        ToastUtils.show(mcontext, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.return_lin, R.id.right_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;

        }
    }

//    private void initData() {
//        String url = Constants.URL_MSGCELIST;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("currentPage", currentPage + "");
//        params.put("pageSize", pageSize + "");
//        params.put("token", myApp.getUserTicket());
//        params.put("msgType", type + ""); //消息类型  1：系统消息，2：通知消息
//        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
//            @Override
//            public void dataLoaded(ResponseData data) throws JSONException {
//                String json = data.getJson();
//                if (StringUtils.isStringNull(json)) {
//                    return;
//                }
//                Gson gson = new Gson();
//                MessageModel model = gson.fromJson(json, MessageModel.class);
//                String status = model.getStatus();
//                if ("1".equals(status)) {
//                    List<MessageModel.DataBean> lines = model.getData();
//                    int len = lines.size();
//                    if (currentPage == 1) {
//                        data_list.clear();
//                        listView.setHasMore(true);
//                    }
//                    if (len < pageSize) {
//                        listView.setHasMore(false);
//                    }
//                    data_list.addAll(lines);
//                    if (data_list.size() <= 0) {
//                        swipeRefresh.setVisibility(View.GONE);
//                        commonNoDataLayout.setVisibility(View.VISIBLE);
//                    } else {
//                        swipeRefresh.setVisibility(View.VISIBLE);
//                        commonNoDataLayout.setVisibility(View.GONE);
//                    }
//                    listView.setVisibility(View.VISIBLE);
//                    adapter.notifyDataSetChanged();
//                    if (isLoadMore) {
//                        isLoadMore = false;
//                        listView.onLoadComplete(true);
//                    }
//                    if (isRefreshing) {
//                        isRefreshing = false;
//                        swipeRefresh.setRefreshing(false);
//                    }
//                } else {
//                    ToastUtils.showT(SystemMessageActivity.this, model.getMessage());
//                }
//            }
//        });
//    }

}
