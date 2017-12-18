package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.ui.BrowseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.MyProcessDialog;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qunar.model.FlyOrder;
import com.qunar.model.PlaneOrderMessageResult;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.MessageModel;
import com.vlvxing.app.model.OrderDetaisModel;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
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

public class SystemMessageActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private LinearLayout[] lin;
    private View[] line;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 10;
    List<MessageModel.DataBean> data_list = new ArrayList<>();
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private int type;
    private myAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);
        ButterKnife.bind(this);
        context = this;
        listView.setVisibility(View.GONE);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        String title;
        if (type == 1) {
            title = "系统公告";
        } else {
            title = "订单";
        }
        headTitle.setText(title);
        adapter = new myAdapter(data_list, this);
        listView.setAdapter(adapter);
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
        String url = Constants.URL_MSGCELIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("token", myApp.getUserTicket());
        params.put("msgType", type + ""); //消息类型  1：系统消息，2：通知消息
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                MessageModel model = gson.fromJson(json, MessageModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    List<MessageModel.DataBean> lines = model.getData();
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
                    ToastUtils.showT(SystemMessageActivity.this, model.getMessage());
                }
            }
        });
    }


    class myAdapter extends BaseAdapter {
        Context context;
        List<MessageModel.DataBean> list;

        public myAdapter(List<MessageModel.DataBean> list, Context context) {
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
                        R.layout.message_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final MessageModel.DataBean bean = list.get(position);
            String title;
            if (type == 1) {
                title = "系统消息";
//                title = "";
            } else {
                title = "订单通知";
            }
            holder.titleTxt.setText(title);
            String t = DataUtils.format(bean.getMsgtime(), "yyyy-MM-dd HH:mm");
            holder.timeTxt.setText(t);
            holder.contentTxt.setText(bean.getMsgcontent());
            String id = bean.getOrderid();
            String msgid = bean.getMsgid();
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (type == 1) {
                        if (bean.getType() == 1) {
                            if (bean.getMsgurl() != null) {
                                Intent intent = new Intent(context, BrowseActivity.class);
                                intent.putExtra("url", bean.getMsgurl());
                                startActivity(intent);
                            }
                        } else if (bean.getType() == 2) {
                            String activityName = bean.getAndroidclassname();
                            try {
                                if(!"".equals(activityName)&&activityName!=null){
                                    Class clazz = Class.forName(activityName);
                                    System.out.println("通知:clazz:"+clazz);
                                    Intent intentActivity = new Intent(context, clazz);
//                                intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentActivity);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        } else if (bean.getType() == 3) {
                            int productid = bean.getProductid();
                            if (productid != 0) {
                                //线路
                                Intent intentLine = new Intent(context, LineDetailsActivity.class);
                                intentLine.putExtra("id", productid);
//                            intentLine.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentLine);
                            }
                        }
                    }
                    if (type == 2) { //订单
                        if (bean.getType() == 3) {
                            getPlaneOrderMessage(id);
                        } else {
                            context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("id", id));
                        }

                    }

                    changeMsgStatus(msgid);
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMsg(bean.getMsgid());
                }
            });
            return convertView;
        }

        private void getPlaneOrderMessage(String id) {
            showDialog("数据加载中...");
            String url = Constants.URL_ORDERDETAIL;
            HashMap<String, String> params = new HashMap<>();
            params.put("orderId", id);
            params.put("token", myApp.getUserTicket());
            RemoteDataHandler.asyncPost(url, params, SystemMessageActivity.this, true, new RemoteDataHandler.Callback() {
                @Override
                public void dataLoaded(ResponseData data) throws JSONException {
                    String json = data.getJson();
                    System.out.println("友盟  json" + json);
                    if (StringUtils.isStringNull(json)) {
                        return;
                    }
                    Gson gson = new Gson();
                    PlaneOrderMessageResult info = gson.fromJson(json, PlaneOrderMessageResult.class);
                    System.out.println("友盟  orderInfo" + info.getData());
                    context.startActivity(new Intent(context, PlaneCompletedOrderActivity.class).putExtra("orderInfo", info.getData()));
                    dismissDialog();
                }
            });
        }

        class ViewHolder {
            @Bind(R.id.title_txt)
            TextView titleTxt;
            @Bind(R.id.time_txt)
            TextView timeTxt;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;
            @Bind(R.id.tv_delete)
            TextView delete;

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

    /**
     * 点击查看，更改消息状态
     */
    private void changeMsgStatus(String id) {
        String url = Constants.URL_READMSG;
        HashMap<String, String> params = new HashMap<>();
        params.put("MsgId", id);
        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                JSONObject obj = new JSONObject(json);
                String status = obj.getString("status");
                String message = obj.getString("message");
                if (status.equals("1")) {

                } else {
                    ToastUtils.show(SystemMessageActivity.this, message);
                }
            }
        });
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
                        Toast.makeText(SystemMessageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                        if (viewCache != null) {
                            viewCache.smoothClose();
                        }
                        onRefreshListener.onRefresh();
                    } else {
                        ToastUtils.show(SystemMessageActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
    }
}
