package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.DateUtils;
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
import com.handongkeji.widget.CallDialog;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.MyDialog;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.model.ProCustomModel;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/5 0005.
 * 我的定制
 */

public class MyProCustomActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.right_txt)
    TextView rightTxt;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    @Bind(R.id.check_all)
    LinearLayout checkAll;
    @Bind(R.id.isall_lin)
    LinearLayout isallLin;
    private int count = 0;
    //    private boolean isRefreshing = true;
//    private int currentPage = 1;
//    private int pageSize = 5;
//    private boolean isLoadMore;
    List<ProCustomModel.DataBean> data_list = new ArrayList<>();
    myAdapter adapter;
    private List<String> check_array=new ArrayList<>();
    private boolean check_all,check_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprocustom_layout);
        ButterKnife.bind(this);
        headTitle.setText("我的定制");
        rightTxt.setText("编辑");
        rightTxt.setVisibility(View.VISIBLE);
        checkAll.setVisibility(View.GONE);
        adapter=new myAdapter(data_list,this);
        listView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        showDialog("数据加载中...");
        HashMap<String, String> params = new HashMap<>();
//        params.put("currentPage", currentPage + "");
//        params.put("pageSize", pageSize + "");
        String url = Constants.URL_PROCUSTOM;
        params.put("token", myApp.getUserTicket());

        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                ProCustomModel model = gson.fromJson(json, ProCustomModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    List<ProCustomModel.DataBean> lines = model.getData();
                    int len = lines.size();
//                    if (currentPage == 1) {
//                        data_list.clear();
//                        listView.setHasMore(true);
//                    }
//                    if (len < pageSize) {
//                        listView.setHasMore(false);
//                    }
                    data_list.addAll(lines);
                    if (data_list.size() <= 0) {
                        checkAll.setVisibility(View.GONE);
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
                    ToastUtils.showT(MyProCustomActivity.this, model.getMessage());
                }
                dismissDialog();
            }
        });
    }

    //自驾游、周末游的适配器
    class myAdapter extends BaseAdapter {
        Context context;


        public List<ProCustomModel.DataBean> getList() {
            return list;
        }

        List<ProCustomModel.DataBean> list;

        public myAdapter(List<ProCustomModel.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
        }

        public void removeItems(List<String> remove_list){
            if(remove_list==null){
                return;
            }
//            for (int i = 0; i < remove_list.size(); i++) {
//                String id = remove_list.get(i);


                for (int j=0;j<list.size();j++){
                    ProCustomModel.DataBean dataBean = list.get(j);
//                    String customswimid = dataBean.getCustomswimid();
//                    if (customswimid.equals(id)){
//                        list.remove(j);
//
//                    }
                    if(remove_list.contains(dataBean.getCustomswimid())){
                        list.remove(dataBean);
                    }
//                }
            }
            if (list.size()==0){
                commonNoDataLayout.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                checkAll.setVisibility(View.GONE);

            }
            notifyDataSetChanged();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.myprocustom_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (count==0){
                holder.img.setVisibility(View.VISIBLE);
                holder.ischeckImg.setVisibility(View.GONE);
            }else {
                holder.img.setVisibility(View.GONE);
                holder.ischeckImg.setVisibility(View.VISIBLE);
            }
            final ProCustomModel.DataBean bean = list.get(position);
            final String departure = bean.getDeparture();
            holder.startTxt.setText(departure);
            final  String destination = bean.getDestination();
            holder.endTxt.setText(destination);
            final   String time = DataUtils.parseString(bean.getTime());
            holder.timeTxt.setText(time);
            final String id=bean.getCustomswimid();
            final ViewHolder finalHolder = holder;
            holder.ischeckImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalHolder.ischeckImg.isSelected()){
                        finalHolder.ischeckImg.setSelected(false);
                        check_array.remove(id);
                        check_all = false;
                        check_no = true;
                        if (check_array.size()<list.size()) {
                            checkAll.setSelected(false);
                        }
                    }else {
                        finalHolder.ischeckImg.setSelected(true);
                        check_array.add(id);
                        check_no = false;
                        if (check_array.size() == list.size()) {
                            check_all = true;
                            checkAll.setSelected(true);
                        }
                    }
                }
            });

            if (check_all){
                holder.ischeckImg.setSelected(true);
            }else if (check_no){
                holder.ischeckImg.setSelected(false);
            }

            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(context,ProCustomDetailActivity.class);
                    intent.putExtra("data",bean); //intent传实体类
                    context.startActivity(intent);
                }
            });
            return convertView;
        }


         class ViewHolder {
             @Bind(R.id.ischeck_img)
             ImageView ischeckImg;
             @Bind(R.id.img)
             ImageView img;
            @Bind(R.id.end_txt)
            TextView endTxt;
            @Bind(R.id.start_txt)
            TextView startTxt;
            @Bind(R.id.time_txt)
            TextView timeTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick({R.id.return_lin, R.id.right_txt, R.id.common_click_retry_tv, R.id.delete_txt,R.id.isall_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
                if (count == 0) {
                    count++;
                    rightTxt.setText("完成");
                    checkAll.setVisibility(View.VISIBLE);
                } else {
                    count = 0;
                    rightTxt.setText("编辑");
                    checkAll.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.common_click_retry_tv:
                break;
            case R.id.delete_txt:
                if (check_array.size()<=0) {
                    ToastUtils.show(this,"请选择要删除的路线!");
                    return;
                }
                final MyDialog isdelete=new MyDialog(this,"确定删除选中路线?");
                isdelete.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickDelete();
                        isdelete.dismissDialog();
                    }
                });
                break;
            case R.id.isall_lin:
                if (isallLin.isSelected()){
                    isallLin.setSelected(false);
                    check_no=true;
                    check_all=false;
                    if (check_array.size()>0) {
                        check_array.clear();
                    }
                }else{
                    isallLin.setSelected(true);
                    check_all=true;
                    check_no=false;
                    List<ProCustomModel.DataBean> obj = adapter.getList();
                    for (ProCustomModel.DataBean bean : obj) {
                        check_array.add(bean .getCustomswimid());
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void clickDelete() {
        showDialog("删除中...");
        // 获取点击的id
        StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < check_array.size(); i++) {
            sb2.append(check_array.get(i));
            if (i == check_array.size() - 1) {
                continue;
            }
            sb2.append(","); // 追加，多个id用逗号隔开 //sb2.toString().trim()
        }
        String url = Constants.URL_DELETEPROCUSTOM;
        HashMap<String, String> params = new HashMap<>();
        params.put("token",myApp.getUserTicket());
        params.put("ids",sb2.toString().trim());//出发地
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
                    ToastUtils.show(MyProCustomActivity.this,"删除成功!");
                    adapter.removeItems(check_array);
                }else {
                    ToastUtils.show(MyProCustomActivity.this,message);
                }
                dismissDialog();
            }
        });
    }
}
