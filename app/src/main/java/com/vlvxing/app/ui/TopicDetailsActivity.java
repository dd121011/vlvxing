package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.bumptech.glide.Glide;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.MyProcessDialog;
import com.lidong.photopicker.ImageCaptureManager;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.MyDialog;
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.model.forum.TopicDetaisBean;
import com.vlvxing.app.model.forum.TopicDetaisHeaderBean;
import com.vlvxing.app.utils.ToastUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 话题详情(基于楼主)
 */

public class TopicDetailsActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.share_rel)
    RelativeLayout shareRel;
    @Bind(R.id.edit_lin)
    RelativeLayout edit_lin;//回复只有文字的布局
    @Bind(R.id.addimg_lay)
    LinearLayout addimgLay;//回复图文的布局
    @Bind(R.id.id_recyclerview_horizontal)
    RecyclerView mRecyclerView;//回复图文中的横向添加展示的图片列表

    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private ArrayList<TopicDetaisBean> list;
    private Context mcontext;
    private TopicDetaisHeaderBean headerBean;//楼主相关信息 model
    private boolean isShowing = false;//删除,分享弹出框默认不显示为false
    MyProcessDialog dialog;
    private String share_content;
    private String share_title, share_url;

    private GalleryAdapter mAdapter;

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager; // 相机拍照处理类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_topic_details);
        ButterKnife.bind(this);
        headTitle.setText("话题详情");
        mcontext = this;


        initHeaderData();//楼主相关数据
        initListData();//层主相关信息
        initView();//楼主header绑定  adapter绑定 层主相关监听
        //设置布局管理器(横向列表)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        setRecycleViewData();

    }

    private void setRecycleViewOnItemCliskListener(){
//        mRecyclerView.
    }

    private void setRecycleViewData() {
        imagePaths.add("000000");
        mAdapter = new GalleryAdapter(mcontext,imagePaths);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initHeaderData() {
        headerBean = new TopicDetaisHeaderBean();
        headerBean.setName("楼主");
        headerBean.setBody("我带着音乐去旅行!");
        headerBean.setDate("2017-9-18");
        headerBean.setTime("23:00");
        headerBean.setFollow(false);
    }


    private void initView() {
        //header
        View headerView = LayoutInflater.from(mcontext).inflate(R.layout.act_topic_details_header, null);
        Button btn = ((Button) headerView.findViewById(R.id.is_follow));//是否关注
        ImageView head_img = (ImageView) headerView.findViewById(R.id.head_img);//头像
        TextView name_header = (TextView) headerView.findViewById(R.id.name_header);//楼主昵称
        TextView date_header = (TextView) headerView.findViewById(R.id.date_header);//日期
        TextView time_header = (TextView) headerView.findViewById(R.id.time_header);//时间
        TextView header_body = (TextView) headerView.findViewById(R.id.header_body);//标题
        if (headerBean != null) {
            name_header.setText(headerBean.getName());
            date_header.setText(headerBean.getDate());
            time_header.setText(headerBean.getTime());
            header_body.setText(headerBean.getBody());
            if (headerBean.isFollow()) {
                //已关注
                btn.setText("取消关注");
            } else {
                btn.setText("+ 关注");
            }

        }

        //可以添加多个HeaderView
        listView.addHeaderView(headerView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (headerBean.isFollow()) {
                    btn.setText("+关注");
                    headerBean.setFollow(false);
                } else {
                    btn.setText("取消关注");
                    headerBean.setFollow(true);
                }

            }
        });
        MyAdapter adapter = new MyAdapter(list, mcontext);
        listView.setAdapter(adapter);


//        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (isRefreshing) return;
//                isRefreshing = true;
//                currentPage = 1;
//                initListData();
//            }
//        };
//        swipeRefresh.setOnRefreshListener(onRefreshListener);
//        swipeRefresh.setColorSchemeResources(R.color.color_ea5413);

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

    private void initListData() {
        list = new ArrayList<TopicDetaisBean>();
        TopicDetaisBean bean = new TopicDetaisBean();
        bean.setName("测试一");
        bean.setDate("2017-10-25");
        bean.setBody("测试标题哈哈哈哈");
        bean.setTime("15:00");

        ArrayList<TopicDetaisBean.Body> messageList = new ArrayList<>();

        TopicDetaisBean.Body body = new TopicDetaisBean.Body();
        body.setmBody("小伙伴们,给大家安利一个好用的APP:V旅行,旅游出行好帮手");
        body.setmName("路人甲 :");
        body.setmDate("09-18");
        body.setmTime("15:47");

        TopicDetaisBean.Body bbody = new TopicDetaisBean.Body();
        bbody.setmBody("明年去成都玩,大家约起来...");
        bbody.setmName("路人乙 :");
        bbody.setmDate("09-19");
        bbody.setmTime("15:47");

        TopicDetaisBean.Body bbbody = new TopicDetaisBean.Body();
        bbbody.setmBody("大家快去V旅行申请白金信用卡了,成功率超高的呢");
        bbbody.setmName("路人丙 :");
        bbbody.setmDate("09-28");
        bbbody.setmTime("15:57");

        messageList.add(body);
        messageList.add(bbody);
        messageList.add(bbbody);

        bean.setMessageList(messageList);

        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);

    }

    @OnClick({R.id.return_img,R.id.right_img, R.id.delete_txt, R.id.share_txt,R.id.open_pic,R.id.open_pic_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_img:
                finish();
                break;
            case R.id.right_img:
                //分享,删除弹出框
                if (isShowing) {
                    shareRel.setVisibility(View.GONE);
                    isShowing = false;
                } else {
                    shareRel.setVisibility(View.VISIBLE);
                    isShowing = true;

                }

                break;
            case R.id.delete_txt:
                //删除
                MyDialog dialog = new MyDialog(mcontext, "确定删除吗？");
                dialog.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickDelete();
                        dialog.dismissDialog();
                    }
                });

                break;
            case R.id.share_txt:
                //分享
                shareRel.setVisibility(View.GONE);
//                Point targetScreen = mBaiduMap.getMapStatus().targetScreen;
//                LatLngBounds bound = mBaiduMap.getMapStatus().bound;
//                LatLng northeast = bound.northeast; // 右上角
//                LatLng southwest = bound.southwest; // 左下角
//                LatLng northwest = new LatLng(northeast.latitude,
//                        southwest.longitude); // 左上角
//                LatLng southeast = new LatLng(southwest.latitude,
//                        northeast.longitude); // 右下角
//                double lng1 = northwest.longitude; //手机左上角经度
//                double lat1 = northwest.latitude;   //手机左上角纬度
//                double lng2 = southeast.longitude; //手机右下角经度
//                double lat2 = southeast.latitude; //手机右下角维度
                //http://handongkeji.com:8090/lvyoushejiao/shareurl/auth/travelRoadShare.json?token=kbuS1ABR8Q4ikISw3P2r2z_uDdjJ4aCLnaxYRemkdoVK_IjkiDXra6RfeerIep5_2uM8d7elPvWlql4KGusc-Q&year=2017&lng1=116.48232325518084&lat1=39.913853364218234&lng2=116.4839761373192&lat239.91205473402165
//                share_url = Constants.URL_ROADSHARE + "?token=" + MyApp.getInstance().getUserTicket() + "&year=" + year + "&lng1=" + lng1 + "&lat1=" + lat1 + "&lng2=" + lng2 + "&lat2=" + lat2;
                share_title = "V旅行";
                share_content = "看世界，V旅行";
                ShareDialog sdialog = new ShareDialog(mcontext);
                sdialog.setmOnclickListener(new ShareDialog.ClickSureListener() {
                    @Override
                    public void onClick(int type) { //type 1QQ 2微博  3微信  4朋友圈
                        if (type == 1) {
                            umShare(SHARE_MEDIA.QQ);
                        } else if (type == 2) {
                            umShare(SHARE_MEDIA.SINA);
                        } else if (type == 3) {
                            umShare(SHARE_MEDIA.WEIXIN);
                        } else {
                            umShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        sdialog.dismissDialog();
                    }
                });
                break;
            case R.id.open_pic:
                //打开图文类型的回复
                addimgLay.setVisibility(View.VISIBLE);
                //隐藏掉只能回复文字的布局
                edit_lin.setVisibility(View.GONE);
                break;
            case R.id.open_pic_img:
                //打开只能回复文字的布局
                edit_lin.setVisibility(View.VISIBLE);
                //隐藏掉图文类型的回复
                addimgLay.setVisibility(View.GONE);
                break;

        }
    }
    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains("000000")) {
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        mAdapter = new GalleryAdapter(mcontext,imagePaths);
        mRecyclerView.setAdapter(mAdapter);
        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 友盟分享
     */
    private void umShare(SHARE_MEDIA share_media) {
//        ShareAction shareAction = new ShareAction((Activity) mcontext);
//        shareAction.setPlatform(share_media).withMedia(new UMImage(mcontext, R.mipmap.logos)).withTitle(share_title).withText(share_content).withTargetUrl(share_url).setCallback(umShareListener).share();
        ShareAction shareAction = new ShareAction(this);
        UMWeb  web = new UMWeb(share_url);
        web.setTitle(share_title);//标题
        web.setThumb(new UMImage(this, R.mipmap.logos));
        web.setDescription("V旅行");//描述
        shareAction.setPlatform(share_media).withMedia(web).withText(share_content).setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {


        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
//            Log.d("plat", "platform" + platform);
//            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                Toast.makeText(mcontext, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(mcontext, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(mcontext, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(mcontext, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(mcontext).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
            }
        }
    }

        private void clickDelete() {
        dialog = new MyProcessDialog(mcontext);
        dialog.show();
        dialog.setMsg("删除中...");
//        String url = Constants.URL_DELETEPATHINFOS;
//        HashMap<String, String> params = new HashMap<>();
//        Point targetScreen = mBaiduMap.getMapStatus().targetScreen;
//        LatLngBounds bound = mBaiduMap.getMapStatus().bound;
//        LatLng northeast = bound.northeast; // 右上角
//        LatLng southwest = bound.southwest; // 左下角
//        LatLng northwest = new LatLng(northeast.latitude,
//                southwest.longitude); // 左上角
//        LatLng southeast = new LatLng(southwest.latitude,
//                northeast.longitude); // 右下角
//        double lng1 = northwest.longitude; //手机左上角经度
//        double lat1 = northwest.latitude;   //手机左上角纬度
//        double lng2 = southeast.longitude; //手机右下角经度
//        double lat2 = southeast.latitude; //手机右下角维度
//        params.put("token", MyApp.getInstance().getUserTicket());
//        params.put("year", year);
//        params.put("lng1", lng1 + "");
//        params.put("lat1", lat1 + "");
//        params.put("lng2", lng2 + "");
//        params.put("lat2", lat2 + "");
//        RemoteDataHandler.asyncTokenPost(url, mcontext, false, params, new RemoteDataHandler.Callback() {
//            @Override
//            public void dataLoaded(ResponseData data) throws JSONException {
//                String json = data.getJson();
//                if (StringUtils.isStringNull(json)) {
//                    return;
//                }
//                JSONObject obj = new JSONObject(json);
//                String status = obj.getString("status");
//                String message = obj.getString("message");
//                if (status.equals("1")) {
//                    ToastUtils.show(mcontext, "删除成功!");
//                    dialog.dismiss();
//                    initData();
//                } else {
//                    ToastUtils.show(mcontext, message);
//                }
//            }
//        });
    }
    class MyAdapter extends BaseAdapter {
        Context context;
        List<TopicDetaisBean> list;

        public MyAdapter(List<TopicDetaisBean> list, Context context) {
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
                        R.layout.act_topic_details_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final TopicDetaisBean bean = list.get(position);
            holder.name_txt.setText(bean.getName());
            holder.date_txt.setText(bean.getDate());
            holder.time_txt.setText(bean.getTime());
            holder.body_txt.setText(bean.getBody());
            holder.number_txt.setText(position + 2 + "楼");

            //头像
            if (StringUtils.isStringNull(bean.getImgUrl())) {
                holder.head_img.setImageResource(R.mipmap.touxiang_moren);
            } else {
                Glide.with(context).load(bean.getImgUrl()).into(holder.head_img);
            }


            SpannableString spannableDate;
            SpannableString spannableTime;
            SpannableString spannableSpace = new SpannableString("  ");

            //判断是否显示回复
            if (bean.getMessageList().size() == 0) {
                holder.first_lin.setVisibility(View.GONE);
                holder.second_lin.setVisibility(View.GONE);
                holder.more_lin.setVisibility(View.GONE);

            } else if (bean.getMessageList().size() == 1) {
                //只需要展示一条回复
                holder.first_name.setText(bean.getMessageList().get(0).getmName());
                holder.first_body.setText(bean.getMessageList().get(0).getmBody());

                spannableDate = new SpannableString(bean.getMessageList().get(0).getmDate());
                spannableDate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableDate.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime = new SpannableString(bean.getMessageList().get(0).getmTime());
                spannableTime.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.first_body.append(spannableSpace);
                holder.first_body.append(spannableDate);
                holder.first_body.append(spannableSpace);
                holder.first_body.append(spannableTime);

                holder.first_lin.setVisibility(View.VISIBLE);
                holder.second_lin.setVisibility(View.GONE);
                holder.more_lin.setVisibility(View.GONE);

            } else if (bean.getMessageList().size() == 2) {
                //需要展示两条回复
                holder.first_name.setText(bean.getMessageList().get(0).getmName());
                holder.first_body.setText(bean.getMessageList().get(0).getmBody());
                //设置并展示第一条回复的 日期+时间  由于风格不同,故用SpannableString
                spannableDate = new SpannableString(bean.getMessageList().get(0).getmDate());
                spannableDate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableDate.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime = new SpannableString(bean.getMessageList().get(0).getmTime());
                spannableTime.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.first_body.append(spannableSpace);
                holder.first_body.append(spannableDate);
                holder.first_body.append(spannableSpace);
                holder.first_body.append(spannableTime);



                holder.second_name.setText(bean.getMessageList().get(1).getmName());
                holder.second_body.setText(bean.getMessageList().get(1).getmBody());
                //设置并展示第二条回复的 日期+时间  由于风格不同,故用SpannableString
                spannableDate = new SpannableString(bean.getMessageList().get(1).getmDate());
                spannableDate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableDate.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime = new SpannableString(bean.getMessageList().get(1).getmTime());
                spannableTime.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.second_body.append(spannableSpace);
                holder.second_body.append(spannableDate);
                holder.second_body.append(spannableSpace);
                holder.second_body.append(spannableTime);


                holder.first_lin.setVisibility(View.VISIBLE);
                holder.second_lin.setVisibility(View.VISIBLE);
                holder.more_lin.setVisibility(View.GONE);
            } else if(bean.getMessageList().size() > 2){
                //展示更多N条回复,点击跳转至更多回复
                holder.more_number.setText(bean.getMessageList().size() - 2 + "");
                //需要展示两条回复
                holder.first_name.setText(bean.getMessageList().get(0).getmName());
                holder.first_body.setText(bean.getMessageList().get(0).getmBody());
                //设置并展示第一条回复的 日期+时间  由于风格不同,故用SpannableString
                spannableDate = new SpannableString(bean.getMessageList().get(0).getmDate());
                spannableDate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableDate.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime = new SpannableString(bean.getMessageList().get(0).getmTime());
                spannableTime.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.first_body.append(spannableSpace);
                holder.first_body.append(spannableDate);
                holder.first_body.append(spannableSpace);
                holder.first_body.append(spannableTime);



                holder.second_name.setText(bean.getMessageList().get(1).getmName());
                holder.second_body.setText(bean.getMessageList().get(1).getmBody());
                //设置并展示第二条回复的 日期+时间  由于风格不同,故用SpannableString
                spannableDate = new SpannableString(bean.getMessageList().get(1).getmDate());
                spannableDate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableDate.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime = new SpannableString(bean.getMessageList().get(1).getmTime());
                spannableTime.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableTime.setSpan(new AbsoluteSizeSpan(25), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.second_body.append(spannableSpace);
                holder.second_body.append(spannableDate);
                holder.second_body.append(spannableSpace);
                holder.second_body.append(spannableTime);


                holder.first_lin.setVisibility(View.VISIBLE);
                holder.second_lin.setVisibility(View.VISIBLE);
                holder.more_lin.setVisibility(View.VISIBLE);
                holder.more_lin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TopicLayerDetailsActivity.class);
                        startActivity(intent);
                    }
                });
            }


//            holder.itemLin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("data",bean)); //传实体类
//                    context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("id",id));
//                }
//            });
            return convertView;

        }


        class ViewHolder {
            @Bind(R.id.head_img)
            ImageView head_img;//头像
            @Bind(R.id.name_txt)
            TextView name_txt;//名字
            @Bind(R.id.number_txt)
            TextView number_txt;//楼层
            @Bind(R.id.date_txt)
            TextView date_txt;//日期
            @Bind(R.id.time_txt)
            TextView time_txt;//时间
            @Bind(R.id.body_txt)
            TextView body_txt;//内容
            @Bind(R.id.first_lin)
            LinearLayout first_lin;
            @Bind(R.id.first_name)
            TextView first_name;
            @Bind(R.id.first_body)
            TextView first_body;
            @Bind(R.id.second_lin)
            LinearLayout second_lin;
            @Bind(R.id.second_name)
            TextView second_name;
            @Bind(R.id.second_body)
            TextView second_body;
            @Bind(R.id.more_lin)
            LinearLayout more_lin;
            @Bind(R.id.more_number)
            TextView more_number;

            ViewHolder(View view) {
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


    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>
    {
        private LayoutInflater mInflater;
        private List<String> imagePaths;

        public GalleryAdapter(Context context, List<String> imagePaths)
        {
            this.imagePaths = imagePaths;
            if(imagePaths.size()==7){
                imagePaths.remove(imagePaths.size()-1);
            }
            mInflater = LayoutInflater.from(context);

        }


        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public ViewHolder(View arg0)
            {
                super(arg0);
            }

            ImageView mImg;
        }

        @Override
        public int getItemCount()
        {
            return imagePaths.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
            View view = mInflater.inflate(R.layout.act_topic_details_horizontal_recycleview_item,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.mImg = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);

            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
            String path = imagePaths.get(i);
            if(path.equals("000000")){
                viewHolder.mImg.setImageResource(R.mipmap.gridview_addpic);
            }else{
                Glide.with(mcontext)
                        .load(path)
                        .placeholder(R.mipmap.photo_default_error)
                        .error(R.mipmap.photo_default_error)
                        .centerCrop()
                        .crossFade()
                        .into(viewHolder.mImg);
            }

        }

    }


}
