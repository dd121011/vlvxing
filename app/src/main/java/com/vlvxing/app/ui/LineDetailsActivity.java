package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.CallDialog;
import com.handongkeji.widget.FixedViewPager;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.NoScrollGridView;
import com.handongkeji.widget.RoundImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.DTPicAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.GlideImageLoader;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.fragment.FeaturesFragment;
import com.vlvxing.app.fragment.FeaturesFragment1;
import com.vlvxing.app.fragment.FeaturesFragment2;
import com.vlvxing.app.model.DetailModel;
import com.vlvxing.app.model.RemarkModel;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.DistanceUtils;
import com.vlvxing.app.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

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
 * Created by Administrator on 2017/5/24 0024.
 * 线路详情
 */

public class LineDetailsActivity extends BaseActivity {

    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.public_pager)
    Banner publicPager;
    @Bind(R.id.content_txt)
    TextView contentTxt;
    @Bind(R.id.price_txt)
    TextView priceTxt;
    @Bind(R.id.txt1)
    TextView txt1;
    @Bind(R.id.txt2)
    TextView txt2;
    @Bind(R.id.txt3)
    TextView txt3;
    @Bind(R.id.viewPager)
    FixedViewPager viewPager;
    @Bind(R.id.tab1)
    TextView tab1;
    @Bind(R.id.tab2)
    TextView tab2;
    @Bind(R.id.all_txt)
    TextView allTxt;
    @Bind(R.id.good_txt)
    TextView goodTxt;
    @Bind(R.id.center_txt)
    TextView centerTxt;
    @Bind(R.id.bad_txt)
    TextView badTxt;
    @Bind(R.id.buy_txt)
    TextView buyTxt;
    @Bind(R.id.collect_txt)
    TextView collectTxt;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh; // 上拉刷新，下拉加載
    @Bind(R.id.common_click_retry_tv)
    TextView commonClickRetryTv;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    @Bind(R.id.recommon_lin)
    LinearLayout recommonLin;
    @Bind(R.id.details_lin)
    LinearLayout detailsLin;
    @Bind(R.id.bottom_lin)
    LinearLayout bottomLin;
    @Bind(R.id.content_txt0)
    TextView contentTxt0;
    @Bind(R.id.price_txt0)
    TextView priceTxt0;
    @Bind(R.id.address_txt)
    TextView addressTxt;
    @Bind(R.id.distance_txt)
    TextView distanceTxt;
    @Bind(R.id.isfrm_lin0)
    LinearLayout isfrmLin0;
    @Bind(R.id.isfrm_lin)
    LinearLayout isfrmLin;
    private TextView[] tabs, remarkTabs;

    private int index = 1;
    private List<Fragment> frags;
    private boolean isRefreshing = true;
    private int currentPage = 1;
    private int pageSize = 5;
    List<RemarkModel.DataBean.EvaluatesBean> data_list = new ArrayList<>();
    myAdapter adapter;
    private String id;
    private List<String> bannerData = new ArrayList<>();
    private String type = "0";//0全部评价 1好评，2中评，3差评
    private boolean IsFirst = true;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private boolean isLoadMore;
    private String introduction, feesdescription, notice, mobile;
    private String date;
    private int t, iscollect = 1; //1收藏 0微收藏
    private String share_title,share_content, share_url;
    DetailModel.DataBean bean=new DetailModel.DataBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linedetails);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        share_url = Constants.URL_SHOPDETAILSHARE + "?travelProductId=" + id;
        t = intent.getIntExtra("type", 0); //1表示从农家院进入详情
        if (t == 1) {
            isfrmLin0.setVisibility(View.VISIBLE);
            isfrmLin.setVisibility(View.GONE);
        }else {
            isfrmLin.setVisibility(View.VISIBLE);
            isfrmLin0.setVisibility(View.GONE);
        }
        tab1.setSelected(true);
        detailsLin.setVisibility(View.VISIBLE);
        bottomLin.setVisibility(View.VISIBLE);
        recommonLin.setVisibility(View.GONE);
        isCollect(); //判断是否是收藏
        getInfo();


        buyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录
                boolean flag = MyApp.getInstance().isLogin(LineDetailsActivity.this);
                if (!flag) {
                    return;
                }
                startActivity(new Intent(LineDetailsActivity.this, WriteOrderActivity.class).putExtra("date", date).putExtra("type", t));
            }
        });
    }

    private void getInfo() {
        showDialog("加载中...");
        String url = Constants.URL_LINEDETAILS;
        HashMap<String, String> params = new HashMap<>();
        params.put("productId", id);
        params.put("PathLat",myApp.getLat());
        params.put("PathLng",myApp.getLng());
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                date = json;
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                DetailModel model = gson.fromJson(json, DetailModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                     bean = model.getData();
                    introduction = bean.getIntroduction();
                    feesdescription = bean.getFeesdescription();
                    notice = bean.getNotice();
                    mobile = bean.getTel();
                    init();
                    String productbigpic = bean.getProductbigpic(); //轮播图
                    String[] split = productbigpic.split(",");
                    if (split.length > 0) {
                        for (int i = 0; i < split.length; i++) {
                            String s = split[i];
                            bannerData.add(s);
                        }
                        setBannerData();
                    }
                    share_title = bean.getProductname();
                    share_content=StringUtils.isStringNull(bean.getContext())?bean.getAddress():bean.getContext();
                    contentTxt.setText(bean.getContext());
                    double price = bean.getPrice();
                    int p = (int) price;
                    priceTxt.setText(p + "");
                    contentTxt0.setText(bean.getContext());
                    priceTxt0.setText(p+"");
                    // 根据用户指定的两个坐标点，计算这两个点的实际地理距离
//                    String distance= DistanceUtils.getDistance(bean.getPathlat(),bean.getPathlng());
                    String distance1 = bean.getDistance();
                    if (!StringUtils.isStringNull(distance1)) {
                        double distance = Double.parseDouble(bean.getDistance()) * 0.001; //米转换成千米
                        //四舍五入，保留两位小数
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMaximumFractionDigits(2);
                        String d = nf.format(distance);
                        distanceTxt.setText(d + "km");
                    }
                    addressTxt.setText(bean.getAddress());
                } else {
                    ToastUtils.show(LineDetailsActivity.this, model.getMessage());
                }
                dismissDialog();
            }
        });
    }

    /**
     * 设置bananer图
     */
    private void setBannerData() {
        publicPager.setIndicatorGravity(BannerConfig.CENTER);
//        publicPager.setBannerAdapter(new BannerAdapter<String>(bannerData) {
//            @Override
//            protected void bindTips(TextView tv, String o) {
//
//            }
//
//            @Override
//            public void bindImage(ImageView imageView, String o) {
//                Glide.with(LineDetailsActivity.this).load(o).into(imageView);
//            }
//        });
        publicPager.setImages(bannerData)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    /**
     * 初始化
     */
    private void init() {
//        listView.setNestedScrollingEnabled(false);
        frags = new ArrayList<Fragment>();
        //1待完成 2是待付款 3是已取消  4已完成    订单状态：0.待支付；1.已完成；2.取消,3待完成
        FeaturesFragment frm1 = new FeaturesFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("data", introduction);
        frm1.setArguments(bundle1);

        FeaturesFragment1 frm2 = new FeaturesFragment1();
        Bundle bundle2 = new Bundle();
        bundle2.putString("data", feesdescription);
        frm2.setArguments(bundle2);

        FeaturesFragment2 frm3 = new FeaturesFragment2();
        Bundle bundle3 = new Bundle();
        bundle3.putString("data", notice);
        frm3.setArguments(bundle3);


        frags.add(frm1);
        frags.add(frm2);
        frags.add(frm3);

        tabs = new TextView[]{txt1, txt2, txt3};
        remarkTabs = new TextView[]{allTxt, goodTxt, centerTxt, badTxt};
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                tabs[index].setSelected(false);
                index = arg0;
                tabs[index].setSelected(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

//                int width = indicater.getWidth();
//                ViewHelper.setTranslationX(indicater, arg0*width+arg1*width);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        index = index - 1;
        tabs[index].setSelected(true);
        viewPager.setCurrentItem(index);
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
//            return FeaturesFragment.getInstance(data);
            return frags.get(arg0);
        }

        @Override
        public int getCount() {
//            return 3;
            return frags.size();
        }
    }

    int count = 0;

    @OnClick({R.id.return_lin, R.id.tab1, R.id.tab2, R.id.right_lin, R.id.txt1, R.id.txt2, R.id.txt3, R.id.query_txt, R.id.collect_txt,
            R.id.buy_txt, R.id.all_txt, R.id.good_txt, R.id.center_txt, R.id.bad_txt,R.id.local_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.tab1:  //点击详情
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                tab2.setSelected(false);
                tab1.setSelected(true);
                detailsLin.setVisibility(View.VISIBLE);
                bottomLin.setVisibility(View.VISIBLE);
                recommonLin.setVisibility(View.GONE);

                break;
            case R.id.tab2:  //点击评价
                allTxt.setSelected(true);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                tab2.setSelected(true);
                tab1.setSelected(false);
                detailsLin.setVisibility(View.GONE);
                bottomLin.setVisibility(View.GONE);
                recommonLin.setVisibility(View.VISIBLE);
                initRemarkList();
                onRefresh();
                break;
            case R.id.right_lin:  //分享
                //判断是否登录
                boolean flag = MyApp.getInstance().isLogin(LineDetailsActivity.this);
                if (!flag) {
                    return;
                }
                ShareDialog sdialog = new ShareDialog(this);
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
            case R.id.txt1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.txt2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.txt3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.query_txt: //咨询
                clickQuery();
                break;
            case R.id.collect_txt: //收藏
                //判断是否登录
                 flag = MyApp.getInstance().isLogin(LineDetailsActivity.this);
                if (!flag) {
                    return;
                }
                isCollect();
                if (iscollect == 1) { //判断是否收藏
                    cancleCollect();
                } else {
                    clickCollect();
                }
                break;
//            case R.id.buy_txt:  //购买
//                startActivity(new Intent(this,OrderDetailActivity.class));
//                break;
            case R.id.all_txt: //全部
                remarkType(allTxt, "0");
                break;
            case R.id.good_txt: //好评
                remarkType(goodTxt, "1");
                break;
            case R.id.center_txt: //中评
                remarkType(centerTxt, "2");
                break;
            case R.id.bad_txt: //差评
                remarkType(badTxt, "3");
                break;
            case R.id.local_rel: //点击跳发现景点
                startActivity(new Intent(this,FindScienceActivity.class).putExtra("type",1).putExtra("bean",bean)); //0 首页发现景点  1农家乐发现景点
                break;
        }
    }


    /**
     * 友盟分享
     */
    private void umShare(SHARE_MEDIA share_media) {
//        ShareAction shareAction = new ShareAction(this);
//        shareAction.setPlatform(share_media).withMedia(new UMImage(this, R.mipmap.logos)).withTitle(share_title).withText(share_content).withTargetUrl(share_url).setCallback(umShareListener).share();

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
//                Toast.makeText(LineDetailsActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(LineDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(LineDetailsActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(LineDetailsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }


    private void clickQuery() {
        final CallDialog dialog = new CallDialog(this, "");
        dialog.setTitle("要拨打：" + mobile + "吗?");
        dialog.setNegativeButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.trim().length() != 0) {
                    Intent mobileIntent = new Intent(
                            "android.intent.action.CALL", Uri.parse("tel:"
                            + mobile));
                    ComponentName componentName = mobileIntent.resolveActivity(getPackageManager());
                    if (componentName != null) {
                        String className = componentName.getClassName();
                        Log.d("aaa", "onClick: "+className);
                    }
                    startActivity(mobileIntent); // 启动
                }
                // 否则Toast提示一下
                else {
                    ToastUtils.show(LineDetailsActivity.this, "电话为空");
                }
                dialog.dismissDialog();
            }
        });
    }

    private void isCollect() {
        if (StringUtils.isStringNull(myApp.getUserTicket())) {
            return;
        }
        String url = Constants.URL_ISCOLLECT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("productId", id);
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
                    String date = obj.getString("data");
                    if (date.equals("false")) { //False未收藏，true已收藏
                        collectTxt.setText("收藏");
                        iscollect = 0;

                    } else {
                        collectTxt.setText("已收藏");
                        iscollect = 1;
                    }
                } else {
                    ToastUtils.show(LineDetailsActivity.this, message);
                }
            }
        });
    }

    private void clickCollect() {
        showDialog("");
        String url = Constants.URL_COLLECT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("productId", id);
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
                    ToastUtils.show(LineDetailsActivity.this, "收藏成功!");
                    collectTxt.setText("已收藏");
                    dismissDialog();
                } else {
                    ToastUtils.show(LineDetailsActivity.this, message);
                }
            }
        });
    }

    private void cancleCollect() {
        showDialog("");
        String url = Constants.URL_CANCLECOLLECT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("productId", id);
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
                    ToastUtils.show(LineDetailsActivity.this, "取消成功!");
                    collectTxt.setText("收藏");
                    dismissDialog();
                } else {
                    ToastUtils.show(LineDetailsActivity.this, message);
                }
            }
        });
    }

    private void onRefresh() {
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }

    private void initRemarkList() {
        listView.setFocusable(false);
        listView.setFocusableInTouchMode(false);
        adapter = new myAdapter(data_list, this);
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

//        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        adapter = new myAdapter(data_list, this);
//        listView.setAdapter(adapter);
//        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        listView.setPullRefreshEnabled(true);
//        listView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate); //BallRotate 三个点文字显示正在加载
//        listView.setArrowImageView(R.drawable.iconfont_downgrey);
//        listView.setLoadingMoreEnabled(true);
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
        initData();
    }

    private void initData() {
        String url = Constants.URL_REMARKLIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("productId", id);
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        params.put("type", type); //0全部评价 1好评，2中评，3差评
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                RemarkModel remarkModel = gson.fromJson(json, RemarkModel.class);
                String status = remarkModel.getStatus();
                if ("1".equals(status)) {
                    RemarkModel.DataBean data1 = remarkModel.getData();
                    List<RemarkModel.DataBean.EvaluatesBean> evaluates = data1.getEvaluates();
                    int len = evaluates.size();
                    if (currentPage == 1) {
                        data_list.clear();
                        listView.setHasMore(true);
                    }
                    if (len < pageSize) {
//                        listView.setNoMore(true);
                        listView.setHasMore(false);
                    }
                    if (IsFirst) {
                        allTxt.setText("全部(" + data1.getAllCounts() + ")");
                        goodTxt.setText("好评(" + data1.getGoodCounts() + ")");
                        centerTxt.setText("中评(" + data1.getAverageCounts() + ")");
                        badTxt.setText("差评(" + data1.getBadCounts() + ")");
                        IsFirst = false;
                    }
                    data_list.addAll(evaluates);
                    if (data_list.size() <= 0) {
                        swipeRefresh.setVisibility(View.GONE);
                        commonNoDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        swipeRefresh.setVisibility(View.VISIBLE);
                        commonNoDataLayout.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
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
                    ToastUtils.showT(LineDetailsActivity.this, remarkModel.getMessage());
                }
            }
        });
    }

    private void remarkType(TextView txt, String t) {
        listView.setVisibility(View.GONE);
        for (int i = 0; i < remarkTabs.length; i++) {
            if (!txt.equals(remarkTabs[i].toString())) {
                remarkTabs[i].setSelected(false);
            }
        }
        txt.setSelected(true);
        type = t;
        onRefresh();
    }


    class myAdapter extends BaseAdapter {
        Context context;
        List<RemarkModel.DataBean.EvaluatesBean> list;

        public myAdapter(List<RemarkModel.DataBean.EvaluatesBean> list, Context context) {
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
                        R.layout.remark_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            RemarkModel.DataBean.EvaluatesBean bean = list.get(position);
            holder.nameTxt.setText(StringUtils.isStringNull(bean.getUsernick()) ? "菜花花" : bean.getUsernick());
            holder.contentTxt.setText(bean.getEvaluatecontent());
            String time = DataUtils.parseString(bean.getCreatetime());
            holder.timeTxt.setText(time);
            String head = bean.getUserpic();
            if (!StringUtils.isStringNull(head)) {
                Glide.with(context).load(head).into(holder.headImg);
            }
            String productbigpic = bean.getEvaluatepic(); //轮播图
            if (!StringUtils.isStringNull(productbigpic)) {
                String[] split = productbigpic.split(",");
                ArrayList<String> pic = new ArrayList<>();
                if (split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        String s = split[i];
                        pic.add(s);
                    }
                    DTPicAdapter adapter = new DTPicAdapter(pic,
                            LineDetailsActivity.this);
                    holder.imgGrid.setAdapter(adapter);
                }
            }
            return convertView;
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.head_img)
            RoundImageView headImg;
            @Bind(R.id.name_txt)
            TextView nameTxt;
            @Bind(R.id.time_txt)
            TextView timeTxt;
            @Bind(R.id.content_txt)
            TextView contentTxt;
            @Bind(R.id.img_grid)
            NoScrollGridView imgGrid;


            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
//    class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
//        Context context;
//        List<RemarkModel.DataBean.EvaluatesBean> list;
//
//        public myAdapter(List<RemarkModel.DataBean.EvaluatesBean> list, Context context) {
//            this.list = list;
//            this.context = context;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.remark_item, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            RemarkModel.DataBean.EvaluatesBean bean = list.get(position);
//            holder.nameTxt.setText(StringUtils.isStringNull(bean.getUsernick()) ? "菜花花" : bean.getUsernick());
//            holder.contentTxt.setText(bean.getEvaluatecontent());
//            String time = DataUtils.parseString(bean.getCreatetime());
//            holder.timeTxt.setText(time);
//            String head = bean.getUserpic();
//            if (!StringUtils.isStringNull(head)) {
//                Glide.with(context).load(head).into(holder.headImg);
//            }
//            String productbigpic = bean.getEvaluatepic(); //轮播图
//            String[] split = productbigpic.split(",");
//            ArrayList<String> pic = new ArrayList<>();
//            if (split.length > 0) {
//                for (int i = 0; i < split.length; i++) {
//                    String s = split[i];
//                    pic.add(s);
//                }
//                DTPicAdapter adapter = new DTPicAdapter(pic,
//                        LineDetailsActivity.this);
//                holder.imgGrid.setAdapter(adapter);
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size() > 0 ? list.size() : 0;
////            return 20;
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            @Bind(R.id.head_img)
//            RoundImageView headImg;
//            @Bind(R.id.name_txt)
//            TextView nameTxt;
//            @Bind(R.id.time_txt)
//            TextView timeTxt;
//            @Bind(R.id.content_txt)
//            TextView contentTxt;
//            @Bind(R.id.img_grid)
//            NoScrollGridView imgGrid;
//
//
//            ViewHolder(View view) {
//                super(view);
//                ButterKnife.bind(this, view);
//            }
//        }
//    }
}
