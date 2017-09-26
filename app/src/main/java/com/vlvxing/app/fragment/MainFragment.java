package com.vlvxing.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.AutoTextView.AutoVerticalScrollTextView;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.SelestorCityActivity;
import com.handongkeji.ui.BrowseActivity;
import com.handongkeji.utils.CommonUtils;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.NoScrollGridView;
import com.handongkeji.widget.RoundImageView;
import com.sivin.Banner;
import com.sivin.BannerAdapter;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.GlideImageLoader;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.model.HotAreaModel;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.model.SysadModel;
import com.vlvxing.app.ui.ByCarActivity;
import com.vlvxing.app.ui.CustomTourActivity;
import com.vlvxing.app.ui.FindScienceActivity;
import com.vlvxing.app.ui.FuJinActivity;
import com.vlvxing.app.ui.HomeActivity;
import com.vlvxing.app.ui.HotAreaActivity;
import com.vlvxing.app.ui.LineDetailsActivity;
import com.vlvxing.app.ui.MessageCenterActivity;
import com.vlvxing.app.ui.PlaneTicketActivity;
import com.vlvxing.app.ui.SelectActivity;
import com.vlvxing.app.ui.VHeadsActivity;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/13 0013.
 * 首页Fragment
 */

public class MainFragment extends Fragment{

    Context mcontext;
    @Bind(R.id.returntop_img)
    ImageView returntopImg;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    //轮播图数据源
    private List<SysadModel.DataBean> img_list = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();
    private String[] strings;
    private String[] niceids;
    private String niceid;
    private Rotate3dAnimation anim;
    private boolean isRunning_gg = true;
    private int number = 0;
    private TextView[] tabs;
    private int index;
    private List<Fragment> frags;
    private List<HotAreaModel.DataBean> data_list = new ArrayList<>();
    List<HotRecomModel.DataBean> recom_list = new ArrayList<>();
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    private String type = "1"; //2热门推荐 1当季游玩
    private myAdapter adapter;
    private TextView cityTxt, numTxt, tab1, tab2;
    private RelativeLayout havemsgRel;
    private ImageView nomsgImg;
    private Banner publicPager;
    private AutoVerticalScrollTextView textSwitcher;
    private NoScrollGridView hotGrid;
    private View line1, line2, footer;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mcontext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initHeader();
        adapter = new myAdapter(recom_list, mcontext);
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
        initData();
        onRefresh();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String city = MyApp.getInstance().getCity_name();
        if (!StringUtils.isStringNull(city)) {
            cityTxt.setText(city);
        }
        getNoreadmsg();
    }

    private void initHeader() {
        footer = View.inflate(mcontext, R.layout.common_no_datawrap, null);  //设置尾部文件的ui
        View header = View.inflate(mcontext, R.layout.mainfrm_header, null); //设置头部文件的ui
        cityTxt = (TextView) header.findViewById(R.id.city_txt);
        numTxt = (TextView) header.findViewById(R.id.num_txt);
        havemsgRel = (RelativeLayout) header.findViewById(R.id.havemsg_rel);
        nomsgImg = (ImageView) header.findViewById(R.id.nomsg_img);
        publicPager = (Banner) header.findViewById(R.id.public_pager);
        // 获取图片的宽度
        int img_width = MyApp.getInstance().getScreenWidth();
        ViewGroup.LayoutParams params = publicPager.getLayoutParams();
        params.height = (img_width * 178) / 375; // 750:500
        publicPager.setLayoutParams(params);
        textSwitcher = (AutoVerticalScrollTextView) header.findViewById(R.id.textSwitcher);
        hotGrid = (NoScrollGridView) header.findViewById(R.id.hot_grid);
        tab1 = (TextView) header.findViewById(R.id.tab1);
        tab2 = (TextView) header.findViewById(R.id.tab2);
        line1 = (View) header.findViewById(R.id.line1);
        line2 = (View) header.findViewById(R.id.line2);
        getHeads();
        getBananer();
        getHot(); //热门目的地
        headerClick(header);  //头部点击事件
        getNoreadmsg(); //获取唯独消息数量
        listView.addHeaderView(header); //加载头部文件
    }

    private void getNoreadmsg() {
        if (StringUtils.isStringNull(MyApp.getInstance().getUserTicket())){
            return;
        }
        String url = Constants.URL_MSGCENUM;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApp.getInstance().getUserTicket());
        RemoteDataHandler.asyncTokenPost(url, mcontext, false, params, new RemoteDataHandler.Callback() {
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
                    String num = obj.getString("data");
                    if ("0".equals(num)) {
                        havemsgRel.setVisibility(View.GONE);
                        nomsgImg.setVisibility(View.VISIBLE);
                    } else {
                        havemsgRel.setVisibility(View.VISIBLE);
                        nomsgImg.setVisibility(View.GONE);
                        numTxt.setText(num);
                    }

                } else {
                    ToastUtils.show(mcontext, message);
                }
            }
        });
    }

    private void onRefresh() {
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }

    private void headerClick(View header) {
        publicPager.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SysadModel.DataBean bean = img_list.get(position);
                String adcontents = bean.getAdcontents();// url
                String adtype = bean.getAdtype();
//                if ("1".equals(adtype)) { //跳新闻详情
////                    startActivity(new Intent(mcontext, RenZAndPersonInfoActivity.class).putExtra("newid",adcontents));
//                } else if ("2".equals(adtype)) {
                Intent intent = new Intent(mcontext, BrowseActivity.class);
                intent.putExtra("url", adcontents);
                startActivity(intent);
//                } else if ("3".equals(adtype)) {
//
//                }
            }
        });
//        publicPager.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                SysadModel.DataBean bean = img_list.get(position);
//                String adcontents = bean.getAdcontents();// url
//                String adtype = bean.getAdtype();
////                if ("1".equals(adtype)) { //跳新闻详情
//////                    startActivity(new Intent(mcontext, RenZAndPersonInfoActivity.class).putExtra("newid",adcontents));
////                } else if ("2".equals(adtype)) {
//                Intent intent = new Intent(mcontext, BrowseActivity.class);
//                intent.putExtra("url", adcontents);
//                startActivity(intent);
////                } else if ("3".equals(adtype)) {
////
////                }
//            }
//        });
        header.findViewById(R.id.serch_lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, SelectActivity.class).putExtra("type",1));  //搜索
            }
        });
        header.findViewById(R.id.city_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, SelestorCityActivity.class));  //选择城市
            }
        });
        header.findViewById(R.id.fujin_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, FuJinActivity.class)); //附近
            }
        });
        header.findViewById(R.id.guonei_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, HomeActivity.class).putExtra("type", 1)); //type 1国内  2境外
            }
        });

        header.findViewById(R.id.jinwai_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, HomeActivity.class).putExtra("type", 2)); //type 1国内  2境外
            }
        });
        header.findViewById(R.id.jingdian_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, FindScienceActivity.class));   //发现景点
            }
        });

        header.findViewById(R.id.more_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mcontext,"暂未开放，敬请期待!");
            }
        });
        header.findViewById(R.id.lin1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(0);  //火车票
            }
        });
        header.findViewById(R.id.lin2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(1);  //机票
            }
        });
        header.findViewById(R.id.lin3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录
                boolean flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    mcontext.startActivity(new Intent(mcontext, CustomTourActivity.class));  //定制游
                }
            }
        });
        header.findViewById(R.id.lin4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, ByCarActivity.class)); //用车
            }
        });
        tab1.setOnClickListener(new View.OnClickListener() {  //当季游玩
            @Override
            public void onClick(View v) {
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                tab2.setSelected(false);
                tab1.setSelected(true);
                type="1";
                onRefresh();
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {  //热门推荐
            @Override
            public void onClick(View v) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                tab2.setSelected(true);
                tab1.setSelected(false);
                type="2";
                onRefresh();
            }
        });
        returntopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.smoothScrollToPosition(0); //回到顶部
            }
        });

        header.findViewById(R.id.msg_rel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录
                boolean flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    mcontext.startActivity(new Intent(mcontext, MessageCenterActivity.class));  //消息中心
                }
            }
        });

        header.findViewById(R.id.vheads_lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, VHeadsActivity.class));  //V头条
            }
        });

    }

    private void jump(int type) {
        String url = null;
        if (type == 0) { //0火车票  1机票
//            url = "http://url.cn/49r6q0b";
            url = "http://touch.train.qunar.com/?bd_source=vlvxing";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            url = "http://url.cn/49r5tyf";
            startActivity(new Intent(mcontext, PlaneTicketActivity.class));
        }
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
    }

    private void initData() {
        String url = Constants.URL_RECOMMEND;
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("typenum", type); //2热门推荐 1当季游玩
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
                        recom_list.clear();
                        listView.setHasMore(true);
                    }
                    if (len < pageSize) {
                        listView.setHasMore(false);
                    }

                    recom_list.addAll(lines);
                    if (data_list.size() <= 0) {
                        listView.addFooterView(footer); //添加尾部
                    } else {
                        listView.removeFooterView(footer); //移除尾部
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
            holder.contentTxt.setText(bean.getContext());
//            // 获取图片的宽度
            int img_width = MyApp.getInstance().getScreenWidth();
            ViewGroup.LayoutParams params = holder.bgImg.getLayoutParams();
            params.height = (img_width * 178) / 374; // 750:500
            holder.bgImg.setLayoutParams(params);
            String bgUrl = bean.getAdvertisebigpic();
            if (!StringUtils.isStringNull(bgUrl)){
                bgUrl=bgUrl.replace("_mid","_big");
            }
            if (StringUtils.isStringNull(bgUrl)) {
                holder.bgImg.setImageResource(R.mipmap.myinfo_bg);
            } else {
                Glide.with(context).load(bean.getAdvertisebigpic()).into(holder.bgImg);
            }
            holder.itemRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id", bean.getTravelproductid()));
                }
            });
            return convertView;

        }

        class ViewHolder {
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

    @Subscriber(tag = "getCityBymainfrm")
    public void getCity(int t) {
        String city = MyApp.getInstance().getCity_name();
        cityTxt.setText(city);
        getAreaidByName(city);
    }

    private void getAreaidByName(String city) {
        String url = Constants.URL_GETAREAID;
        HashMap<String, String> params = new HashMap<>();
        params.put("areaName", city);
        RemoteDataHandler.asyncPost(url, params, mcontext, false, new RemoteDataHandler.Callback() {
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
                    String areaId = obj.getString("data");
                    MyApp.getInstance().setAreaid(areaId);
                } else {
                    ToastUtils.show(mcontext, message);
                }
            }
        });
    }

//    @Override
//    public void OnBannerClick(int position) {
//        SysadModel.DataBean bean = img_list.get(position);
//        String adcontents = bean.getAdcontents();// url
//        String adtype = bean.getAdtype();
//        if ("1".equals(adtype)) { //跳新闻详情
////                    startActivity(new Intent(mcontext, RenZAndPersonInfoActivity.class).putExtra("newid",adcontents));
//        } else if ("2".equals(adtype)) {
//            Intent intent = new Intent(mcontext, BrowseActivity.class);
//            intent.putExtra("url", adcontents);
//            mcontext.startActivity(intent);
//        } else if ("3".equals(adtype)) {
//
//        }
//    }


    private void getHot() {
        String url = Constants.URL_HOTAREA;
        HashMap<String, String> params = new HashMap<>();
//        params.put("areaId", "1");
        RemoteDataHandler.asyncPost(url, params, mcontext, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                HotAreaModel model = gson.fromJson(json, HotAreaModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    List<HotAreaModel.DataBean> array = model.getData();
                    if (array.size() > 0) {
                        for (int i = 0; i < array.size(); i++) {
                            if (i < 3) {
                                HotAreaModel.DataBean dataBean = array.get(i);
                                data_list.add(dataBean);
                            }
                        }
                        hotAdapter hadapter = new hotAdapter(data_list, mcontext);
                        hotGrid.setAdapter(hadapter);
                    }
                } else {
                    ToastUtils.show(mcontext, model.getMessage());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning_gg = false;
    }

    private void getHeads() {
        String url = Constants.URL_VHEADS;
        HashMap<String, String> params = new HashMap<>();
//        params.put("areaId", "1");
        RemoteDataHandler.asyncPost(url, params, mcontext, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                JSONObject object = new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)) {
                    JSONArray array = object.getJSONArray("data");
                    strings = new String[array.length()];
                    niceids = new String[array.length()];
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        strings[i] = obj.getString("headname");
                        niceids[i] = obj.getString("vheadid");
                    }
                    textSwitcher.setAnimation(anim);
                    textSwitcher.setText(strings[0]);
                    niceid = niceids[0];
                    new Thread() {
                        @Override
                        public void run() {
                            while (isRunning_gg) {
                                SystemClock.sleep(3000);
                                handler.sendEmptyMessage(199);
                            }
                        }
                    }.start();
                }
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 199:
                    if (strings == null) {
                        textSwitcher.setText("");
                    } else {
                        textSwitcher.next();
                        number++;
                        textSwitcher.setText(strings[number % strings.length]);
                        niceid = niceids[number % niceids.length];
                    }
                    break;
            }
        }

        ;
    };

    //获取bananer图
    public void getBananer() {
        String url = Constants.URL_SYSAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("categoryId", "0"); //分类id(0:首页，1国内，2国外  3附近)
        RemoteDataHandler.asyncTokenPost(url, mcontext, true, params, new RemoteDataHandler.Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        String json = data.getJson();
                        if (StringUtils.isStringNull(json)) {
                            return;
                        }
                        Gson gson = new Gson();
                        SysadModel model = gson.fromJson(json, SysadModel.class);
                        final int status = model.getStatus();
                        if (status == 1) {
                            img_list = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                bannerData.add(model.getData().get(i).getAdpicture());
                            }
                            setBannerData();
                        } else {
                            ToastUtils.show(getContext(), model.getMessage());
                        }
                    }
                }
        );
    }

    /**
     * 设置bananer图
     */
    private void setBannerData() {
//        publicPager.setIndicatorGravity(BannerConfig.RIGHT);
//        publicPager.setImages(bannerData).
//       setImageLoader(new GlideImageLoader()).isAutoPlay(true).setDelayTime(1000)
//                .start();

        publicPager.setBannerAdapter(new BannerAdapter<String>(bannerData) {
            @Override
            protected void bindTips(TextView tv, String o) {

            }
            @Override
            public void bindImage(ImageView imageView, String o) {
                Glide.with(mcontext).load(o).into(imageView);
            }
        });
    }




    class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public Rotate3dAnimation(float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = textSwitcher.getHeight();
            mCenterX = textSwitcher.getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees
                    + ((mToDegrees - fromDegrees) * interpolatedTime);
            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;
            final Matrix matrix = t.getMatrix();
            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY
                        * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY
                        * (interpolatedTime), 0.0f);
            }
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    class hotAdapter extends BaseAdapter {
        List<HotAreaModel.DataBean> list;
        Context context;

        public hotAdapter(List<HotAreaModel.DataBean> list, Context context) {
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
                        R.layout.hot_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HotAreaModel.DataBean bean = list.get(position);
            String hotareaid = bean.getHotareaid();
            holder.bgImg.setType(RoundImageView.TYPE_ROUND);// 设置为圆角图片
            int width = (MyApp.getScreenWidth() - CommonUtils.dip2px(context, 38)) / 3;
            ViewGroup.LayoutParams params = holder.bgImg.getLayoutParams();
            params.width = width;
            params.height = width;
            holder.bgImg.setLayoutParams(params); //129201-10002097
            Glide.with(context).load(bean.getPic()).error(R.mipmap.dibai).into(holder.bgImg);
            holder.cityTxt.setText(bean.getAreaname());
            holder.itemRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, HotAreaActivity.class).putExtra("areaid", bean.getAreaid()).putExtra("isforeign", bean.getIsforeign()));
                }
            });
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.bg_img)
            RoundImageView bgImg;
            @Bind(R.id.city_txt)
            TextView cityTxt;
            @Bind(R.id.item_rel)
            RelativeLayout itemRel;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
