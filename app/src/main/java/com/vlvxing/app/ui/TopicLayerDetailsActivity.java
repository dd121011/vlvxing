package com.vlvxing.app.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.vlvxing.app.R;
import com.vlvxing.app.model.forum.TopicDetaisBean;
import com.vlvxing.app.model.forum.TopicDetaisHeaderBean;
import com.vlvxing.app.model.forum.TopicLayerDetaisBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 话题详情(基于层主)
 */

public class TopicLayerDetailsActivity extends BaseActivity {
    //楼主相关控件绑定
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.number_btn)
    Button number_btn;
    @Bind(R.id.date_header)
    TextView date_header;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private ArrayList<TopicLayerDetaisBean> list ;
    private Context mcontext;
    private TopicLayerDetaisBean beanBody;//楼主相关信息 model
    private boolean isShowing = false;//删除,分享弹出框默认不显示为false
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_topic_layer_details);
        ButterKnife.bind(this);

        mcontext = this;

        initHeaderData();//楼主相关数据
        initListData();//层主相关信息
        initView();//楼主header绑定  adapter绑定 层主相关监听


    }



    private void initHeaderData(){
        beanBody = new TopicLayerDetaisBean();
        beanBody.setName("小V");
        beanBody.setBody("老哥稳!");
        beanBody.setDate("9-18");
        beanBody.setTime("23:00");
        beanBody.setNumber(2);
        headTitle.setText(beanBody.getNumber()+"楼");
        number_btn.setText(beanBody.getNumber()+"楼");
        date_header.setText(beanBody.getDate());


        //头像
//        if (StringUtils.isStringNull(beanBody.getImgUrl())) {
//            head_img.setImageResource(R.mipmap.touxiang_moren);
//        } else {
//            Glide.with(context).load(bean.getImgUrl()).into(holder.head_img);
//        }
    }



    private void initView(){


        MyAdapter adapter = new MyAdapter(list,mcontext);
        listView.setAdapter(adapter);


        listView.setLoadDataListener(new MyListView.LoadDataListener() {
            @Override
            public void onLoadMore() {
                if (isLoadMore) return;
                isLoadMore = true;
                currentPage++;
                initListData();
            }
        });
    }


//    private void onRefresh() {
//        swipeRefresh.setRefreshing(true);
//        onRefreshListener.onRefresh();
//    }

    private void initListData(){
        list = new ArrayList<TopicLayerDetaisBean>();
        TopicLayerDetaisBean bean = new TopicLayerDetaisBean();
        bean.setName("测试一");
        bean.setDate("09-18");
        bean.setBody("回复内容啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
        bean.setTime("15:00");


        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);

    }

    @OnClick({R.id.return_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_img:
                finish();
                break;
        }
    }
    class MyAdapter extends BaseAdapter {
        Context context;
        List<TopicLayerDetaisBean> list;

        public MyAdapter(List<TopicLayerDetaisBean> list, Context context) {
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
                        R.layout.act_topic_layer_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            final TopicLayerDetaisBean bean = list.get(position);
            holder.name.setText(bean.getName()+" : ");
//            holder.date_txt.setText(bean.getDate());
//            holder.time_txt.setText(bean.getTime());
            holder.body.setText(bean.getBody());
            SpannableString spannableDate = new SpannableString(bean.getDate());
            spannableDate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableDate.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            SpannableString spannableTime = new SpannableString(bean.getTime());
            spannableTime.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableTime.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString spannableSpace = new SpannableString("  ");
//            spannableSpace.setSpan(null,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.body.append(spannableSpace);
            holder.body.append(spannableDate);
            holder.body.append(spannableSpace);
            holder.body.append(spannableTime);

            //前景色


            //背景色
            //ss.setSpan(new BackgroundColorSpan(Color.RED), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //可点击触发事件
            //ss.setSpan(new TextClickSpan(), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //触发事件的话，要加上下面这句话
            //tvTest.setMovementMethod(LinkMovementMethod.getInstance());

            //下划线
            //ss.setSpan(new UnderlineSpan(), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //放置图片
            //ss.setSpan(new ImageSpan(this, R.drawable.ic_launcher), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



            return convertView;

        }


        class ViewHolder {

            @Bind(R.id.name)
            TextView name;//名字

            @Bind(R.id.body)
            TextView body;

            ViewHolder(View view){
                ButterKnife.bind(this, view);
            }
        }
    }

//    private void initData() {
//        String url = Constants.URL_MYORDER;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("currentPage", currentPage + "");
//        params.put("pageSize", pageSize + "");
//        params.put("statutType", type + ""); //1.全部  2待付款  3待评价
//        params.put("orderType", t + ""); //1.出行订单  2用车订单
//        params.put("token", myApp.getUserTicket());
//        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
//            @Override
//            public void dataLoaded(ResponseData data) throws JSONException {
//                String json = data.getJson();
//                if (StringUtils.isStringNull(json)) {
//                    return;
//                }
//                Gson gson = new Gson();
//                MyOrderModel model = gson.fromJson(json, MyOrderModel.class);
//                String status = model.getStatus();
//                String status = model.getStatus();
//                if ("1".equals(status)) {
//                    List<MyOrderModel.DataBean> lines = model.getData();
//                    int len = lines.size();
//                    if (currentPage == 1) {
//                        recom_list.clear();
//                        listView.setHasMore(true);
//                    }
//                    if (len < pageSize) {
//                        listView.setHasMore(false);
//                    }
//
//                    recom_list.addAll(lines);
//                    if (recom_list.size() <= 0) {
//                        swipeRefresh.setVisibility(View.GONE);
//                        commonNoDataLayout.setVisibility(View.VISIBLE);
//                    } else {
//                        swipeRefresh.setVisibility(View.VISIBLE);
//                        commonNoDataLayout.setVisibility(View.GONE);
//                    }
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
//                    ToastUtils.showT(MyOrderActivity.this, model.getMessage());
//                }
//            }
//        });
//    }


}
