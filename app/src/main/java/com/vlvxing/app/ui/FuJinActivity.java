package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.SelestorCityActivity;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.ui.BrowseActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.FixedViewPager;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.PagerSlidingTabStrip;
import com.sivin.Banner;
import com.sivin.BannerAdapter;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.GlideImageLoader;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.fragment.FuJinFragment;
import com.vlvxing.app.fragment.MainFragment;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.model.SysadModel;
import com.vlvxing.app.utils.ToastUtils;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 附近
 */

public class FuJinActivity extends BaseActivity {
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private com.sivin.Banner publicPager;
    private TextView cityTxt;

    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    private int type = 0;
    List<HotRecomModel.DataBean> data_list = new ArrayList<>();
    private FuJinAdapter adapter;
    private LinearLayout lin1, lin2, lin3, lin4, lin5;
    private View line1, line2, line3, line4, line5;
    //    @Bind(R.id.city_txt)
//    TextView cityTxt;
//    @Bind(R.id.public_pager)
//    Banner publicPager;
//    @Bind(R.id.pagerTabStrip)
//    PagerSlidingTabStrip pagerTabStrip;
//    @Bind(R.id.viewPager)
//    FixedViewPager viewPager;
    //轮播图数据源
    private List<SysadModel.DataBean> img_list = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();
    String[] tabTitles;
    private LinearLayout[] tabs;
    private View[] lines;
    private View footer;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private boolean isAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadcommon_list);
        ButterKnife.bind(this);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (cityTxt != null) {
            cityTxt.setText(myApp.getCity_name());
        }
        onRefresh();
    }

    private void onRefresh() {
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }

    private void initView() {
        commonNoDataLayout.setVisibility(View.GONE);
        footer = View.inflate(this, R.layout.common_no_datawrap, null);
        View header = View.inflate(this, R.layout.activity_fujin, null);
        listView.addHeaderView(header);
        initHeader(header);
        adapter = new FuJinAdapter(data_list, this);
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
        initData();
    }

    private void headerOnclick(View header) {
        header.findViewById(R.id.return_lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //返回
            }
        });
        publicPager.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SysadModel.DataBean bean = img_list.get(position);
                String adcontents = bean.getAdcontents();// url
                String adtype = bean.getAdtype();
//                if ("1".equals(adtype)) { //跳新闻详情
////                    startActivity(new Intent(mcontext, RenZAndPersonInfoActivity.class).putExtra("newid",adcontents));
//                } else if ("2".equals(adtype)) {
                Intent intent = new Intent(FuJinActivity.this, BrowseActivity.class);
                intent.putExtra("url", adcontents);
                startActivity(intent);
//                } else if ("3".equals(adtype)) {
//
//                }
            }
        });
        header.findViewById(R.id.select_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuJinActivity.this, SelectActivity.class).putExtra("type", 2));  //搜索
            }
        });
        header.findViewById(R.id.city_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuJinActivity.this, SelestorCityActivity.class));  //选择城市
            }
        });
        header.findViewById(R.id.tab1).setOnClickListener(new View.OnClickListener() { //自驾游
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuJinActivity.this, WeekActivity.class).putExtra("type", 1));  //自驾游
            }
        });
        header.findViewById(R.id.tab2).setOnClickListener(new View.OnClickListener() { //周末游
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuJinActivity.this, WeekActivity.class).putExtra("type", 2)); //周末游
            }
        });
        header.findViewById(R.id.tab3).setOnClickListener(new View.OnClickListener() { //农家院
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuJinActivity.this, FarmyardActivity.class));  //农家院
            }
        });
        header.findViewById(R.id.tab4).setOnClickListener(new View.OnClickListener() { //景点用车
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuJinActivity.this, ScenicCarActivity.class));  //景点用车
            }
        });
        header.findViewById(R.id.tab5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(FuJinActivity.this, "暂未开放，敬请期待!");
            }
        });

        lin1.setOnClickListener(new View.OnClickListener() { //全部
            @Override
            public void onClick(View v) {
                isSelected(lin1, line1, 0);
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() { //自助
            @Override
            public void onClick(View v) {
                isSelected(lin2, line2, 2);
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() { //特价
            @Override
            public void onClick(View v) {
                isSelected(lin3, line3, 3);
            }
        });
        lin4.setOnClickListener(new View.OnClickListener() { //主题
            @Override
            public void onClick(View v) {
                isSelected(lin4, line4, 4);
            }
        });
        lin5.setOnClickListener(new View.OnClickListener() { //排序
            @Override
            public void onClick(View v) {
                isSelected(lin5, line5, 5);
            }
        });
    }

    private void isSelected(LinearLayout lin, View line, int t) {
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].setSelected(false);
            lines[i].setVisibility(View.GONE);

        }
        lin.setSelected(true);
        line.setVisibility(View.VISIBLE);
        type = t;
        onRefresh();
    }

    private void initData() {
        if (isAdd) {
            listView.removeFooterView(footer); //移除尾部
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        String url = Constants.URL_PRODUCTLIST2;
        params.put("areaId", MyApp.getInstance().getAreaid());
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
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
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
                        listView.setHasMore(true);
                    }
                    if (len < pageSize) {
                        listView.setHasMore(false);
                    }
                    data_list.addAll(lines);
                    if (data_list.size() <= 0) {
                        listView.addFooterView(footer); //添加尾部
                    } else {
                        isAdd = true;
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
                    ToastUtils.showT(FuJinActivity.this, model.getMessage());
                }
            }
        });
    }

    //获取bananer图
    public void getBananer() {
        String url = Constants.URL_SYSAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("categoryId", "3"); //分类id(0:首页，1国内，2国外  3附近)
        RemoteDataHandler.asyncTokenPost(url, this, true, params, new RemoteDataHandler.Callback() {
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
                            ToastUtils.show(FuJinActivity.this, model.getMessage());
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
//        publicPager.setImages(bannerData)
//                .setImageLoader(new GlideImageLoader())
//                .start();

        publicPager.setBannerAdapter(new BannerAdapter<String>(bannerData) {
            @Override
            protected void bindTips(TextView tv, String o) {

            }

            @Override
            public void bindImage(ImageView imageView, String o) {
                Glide.with(FuJinActivity.this).load(o).into(imageView);
            }
        });
    }
//    @OnClick({R.id.return_lin, R.id.select_txt, R.id.city_txt, R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4, R.id.tab5})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.return_lin:
//                finish();
//                break;
//            case R.id.select_txt:
//                startActivity(new Intent(this, SelectActivity.class));
//                break;
//            case R.id.city_txt:
//                startActivity(new Intent(this, SelestorCityActivity.class));
//                break;
//            case R.id.tab1: //自驾游
//                startActivity(new Intent(this,WeekActivity.class).putExtra("type",1));
//                break;
//            case R.id.tab2:   //周末游
//                startActivity(new Intent(this,WeekActivity.class).putExtra("type",2));
//                break;
//            case R.id.tab3:  //农家院
//                startActivity(new Intent(this,FarmyardActivity.class));
//                break;
//            case R.id.tab4:  //景点用车
//                startActivity(new Intent(this,ScenicCarActivity.class));
//                break;
//            case R.id.tab5:  //更多
//                break;
//        }
//    }

//    /**
//     * 轮播图的点击事件
//     */
//    @Override
//    public void OnBannerClick(int position) {
//        SysadModel.DataBean bean = img_list.get(position);
//        String adcontents = bean.getAdcontents();// url
//        String adtype = bean.getAdtype();
//        if ("1".equals(adtype)) { //跳新闻详情
////                    startActivity(new Intent(mcontext, RenZAndPersonInfoActivity.class).putExtra("newid",adcontents));
//        } else if ("2".equals(adtype)) {
//            Intent intent = new Intent(FuJinActivity.this, BrowseActivity.class);
//            intent.putExtra("url", adcontents);
//            startActivity(intent);
//        } else if ("3".equals(adtype)) {
//
//        }
//
//    }

    class FuJinAdapter extends BaseAdapter {
        Context context;
        List<HotRecomModel.DataBean> list;

        public FuJinAdapter(List<HotRecomModel.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
        }

//        @Override
//        public int getItemViewType(int position) {
//            return position == 0 ? 0 : 1;
//        }
//
//        @Override
//        public int getViewTypeCount() {
//            return 2;
//        }

        @Override
        public int getCount() {
//            return list == null ? 1 : list.size()+1;
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
//            if (getItemViewType(position) == 0) {
//                convertView = LayoutInflater.from(context).inflate(
//                        R.layout.activity_fujin, parent, false);
//                initHeader(convertView);
//            } else {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.fujin_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HotRecomModel.DataBean bean = list.get(position);
            holder.nameTxt.setText(bean.getProductname());
            holder.contentTxt.setText(bean.getContext());
            double price = bean.getPrice();
            int p = (int) price;
            holder.priceTxt.setText("￥" + p);
            // 获取图片的宽度
            int img_width = MyApp.getInstance().getScreenWidth();
            ViewGroup.LayoutParams params = holder.rel.getLayoutParams();
            params.height = (img_width * 178) / 374; // 750:500
            holder.rel.setLayoutParams(params);
            String bgUrl = bean.getAdvertisebigpic();
            if (!StringUtils.isStringNull(bgUrl)) {
                bgUrl = bgUrl.replace("_mid", "_big");
            }
            Glide.with(context).load(bgUrl).error(R.mipmap.myinfo_bg).into(holder.bgImg);

            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, LineDetailsActivity.class).putExtra("id", bean.getTravelproductid()));
                }
            });
//            }
            return convertView;
        }

        class ViewHolder {
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
            @Bind(R.id.rel)
            RelativeLayout rel;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


        class MyPagerAdapter extends FragmentPagerAdapter {


            public MyPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return FuJinFragment.getInstance(position, 0);
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        }
    }

    private void initHeader(View header) {
        lin1 = (LinearLayout) header.findViewById(R.id.lin1);
        lin2 = (LinearLayout) header.findViewById(R.id.lin2);
        lin3 = (LinearLayout) header.findViewById(R.id.lin3);
        lin4 = (LinearLayout) header.findViewById(R.id.lin4);
        lin5 = (LinearLayout) header.findViewById(R.id.lin5);
        line1 = (View) header.findViewById(R.id.line1);
        line2 = (View) header.findViewById(R.id.line2);
        line3 = (View) header.findViewById(R.id.line3);
        line4 = (View) header.findViewById(R.id.line4);
        line5 = (View) header.findViewById(R.id.line5);
//        tabTitles = new String[]{"全部", "自助","特价","主题","排序"};
        tabs = new LinearLayout[]{lin1, lin2, lin3, lin4, lin5};
        lines = new View[]{line1, line2, line3, line4, line5};
        publicPager = (Banner) header.findViewById(R.id.public_pager);
        int width = myApp.getScreenWidth();
        ViewGroup.LayoutParams pub = publicPager.getLayoutParams();
        pub.height = (width * 178) / 375;
        publicPager.setLayoutParams(pub);
        cityTxt = (TextView) header.findViewById(R.id.city_txt);
//        publicPager.setIndicatorGravity(BannerConfig.RIGHT);
        cityTxt.setText(myApp.getCity_name());
        getBananer();
        headerOnclick(header);
    }
}
