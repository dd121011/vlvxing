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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.SelestorCityActivity;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.ui.BrowseActivity;
import com.handongkeji.utils.CommonUtils;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.FixedViewPager;
import com.handongkeji.widget.MyListView;
import com.handongkeji.widget.NoScrollGridView;
import com.handongkeji.widget.PagerSlidingTabStrip;
import com.handongkeji.widget.RoundImageView;
import com.sivin.Banner;
import com.sivin.BannerAdapter;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.FuJinAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.GlideImageLoader;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.fragment.FuJinFragment;
import com.vlvxing.app.fragment.HomeScenicFragment;
import com.vlvxing.app.fragment.MainFragment;
import com.vlvxing.app.model.HotAreaModel;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.model.HotScienceModel;
import com.vlvxing.app.model.SysadModel;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 国内、境外
 */

public class HomeActivity extends BaseActivity{
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;

    private Banner publicPager;
    private NoScrollGridView hotGrid;
    private TextView cityTxt,tab1,tab2,tab3,tab4,tab5;
    //    @Bind(R.id.city_txt)
//    TextView cityTxt;
//    @Bind(R.id.public_pager)
//    Banner publicPager;
//    @Bind(R.id.pagerTabStrip)
//    PagerSlidingTabStrip pagerTabStrip;
//    @Bind(R.id.viewPager)
//    FixedViewPager viewPager;
//    @Bind(R.id.hot_grid)
//    NoScrollGridView hotGrid;
    //轮播图数据源
    private List<SysadModel.DataBean> img_list = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();
    String[] tabTitles;
    private List<HotScienceModel.DataBean> data_list = new ArrayList<>();
    List<HotRecomModel.DataBean> recom_list = new ArrayList<>();
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;
    private LinearLayout lin1, lin2, lin3, lin4, lin5;
    private View line1, line2, line3, line4, line5;
    private LinearLayout[] tabs;
    private View[] lines;
    private View footer;
    private int t, type;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private FuJinAdapter adapter;
    private boolean isAdd=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadcommon_list);
        ButterKnife.bind(this);
        initView();
    }


    private void onRefresh() {
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }

    private void initView() {
        commonNoDataLayout.setVisibility(View.GONE);
        Intent intent = getIntent();
        t = intent.getIntExtra("type", 0); //type 1国内--跟团  2境外--免签
        if (t == 1) {
            tabTitles = new String[]{"全部", "特色", "特价", "跟团", "排序"};
        } else {
            tabTitles = new String[]{"全部", "特色", "特价", "免签", "排序"};
        }
        footer = View.inflate(this, R.layout.common_no_datawrap, null);
        View header = View.inflate(this, R.layout.activity_home, null);
        listView.addHeaderView(header);
        initHeader(header);
        adapter = new FuJinAdapter(recom_list, this);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (cityTxt!=null){
            cityTxt.setText(myApp.getCity_name());
        }
        onRefresh();
    }

    private void headerOnclick(View header) {
        header.findViewById(R.id.return_lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //返回
            }
        });
        publicPager.setOnBannerItemClickListener(new com.sivin.Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SysadModel.DataBean bean = img_list.get(position);
                String adcontents = bean.getAdcontents();// url
                String adtype = bean.getAdtype();
//                if ("1".equals(adtype)) { //跳新闻详情
////                    startActivity(new Intent(mcontext, RenZAndPersonInfoActivity.class).putExtra("newid",adcontents));
//                } else if ("2".equals(adtype)) {
                Intent intent = new Intent(HomeActivity.this, BrowseActivity.class);
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
                startActivity(new Intent(HomeActivity.this, SelectActivity.class).putExtra("type",2));  //搜索
            }
        });
        header.findViewById(R.id.city_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SelestorCityActivity.class));  //选择城市
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
//        listView.setVisibility(View.GONE);
        for (int i = 0; i < tabs.length; i++) {
//            if (tabs[i] != lin) {
            tabs[i].setSelected(false);
            lines[i].setVisibility(View.GONE);

//            }
        }
        lin.setSelected(true);
        line.setVisibility(View.VISIBLE);
        type = t;
        onRefresh();
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
        tab1= (TextView) header.findViewById(R.id.title1);
        tab2= (TextView) header.findViewById(R.id.title2);
        tab3= (TextView) header.findViewById(R.id.title3);
        tab4= (TextView) header.findViewById(R.id.title4);
        tab5= (TextView) header.findViewById(R.id.title5);
        String title = null;
        if (t==1) { //1国内--跟团  2境外--免签
            title="跟团";
        }else if (t==2){
            title="免签";
        }
        tab4.setText(title);
        tab1.setText("全部");
        tab2.setText("特色");
        tab3.setText("特价");
        tab5.setText("排序");
        // tabTitles = new String[]{"全部", "特色", "特价", "跟团", "排序"};

//        tabTitles = new String[]{"全部", "自助","特价","主题","排序"};
        tabs = new LinearLayout[]{lin1, lin2, lin3, lin4, lin5};
        lines = new View[]{line1, line2, line3, line4, line5};
        hotGrid = (NoScrollGridView) header.findViewById(R.id.hot_grid);
        publicPager = (Banner) header.findViewById(R.id.public_pager);
        cityTxt = (TextView) header.findViewById(R.id.city_txt);
//        publicPager.setIndicatorGravity(BannerConfig.RIGHT);
        // 获取图片的宽度
        int img_width = MyApp.getInstance().getScreenWidth();
        ViewGroup.LayoutParams params = publicPager.getLayoutParams();
        params.height = (img_width * 178) / 375; // 750:500
        publicPager.setLayoutParams(params);
        cityTxt.setText(myApp.getCity_name());
        getBananer();
        getHot();
        headerOnclick(header);
    }

    private void initData() {
        if (isAdd){
            listView.removeFooterView(footer); //移除尾部
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        String url = Constants.URL_PRODUCTLIST1;
        params.put("isForeign", t + ""); //1国内 2国外
//        if (t==1) {
//            params.put("areaId", myApp.getAreaid());
//        }
        if (type == 2) {  //国内外：1全部  2特色  3特价  4跟团/免签   5排序
            params.put("isFeature", "1");
        }
        if (type == 3) {
            params.put("isSpecialPrice", "1");
        }
        if (t == 1) { //跟团/免签
            if (type == 4) {
                params.put("isGroup", "1");
            }
        } else if (t == 2) {
            if (type == 4) {
                params.put("isVisa", "1");
            }
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
                    recom_list.clear();
                    listView.setHasMore(true);
                }
                if (len < pageSize) {
                    listView.setHasMore(false);
                }
                recom_list.addAll(lines);
                if (recom_list.size() <= 0) {
                    listView.addFooterView(footer); //添加尾部
                    isAdd=true;
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
                ToastUtils.showT(HomeActivity.this, model.getMessage());
            }
        }
    });

}

    /**
     * 热门景点
     */
    private void getHot() {
        String url = Constants.URL_HOTSCIENCE;
        HashMap<String, String> params = new HashMap<>();
        params.put("isForegin", t + ""); //1国内，2国外
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                HotScienceModel model = gson.fromJson(json, HotScienceModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    List<HotScienceModel.DataBean> array = model.getData();
                    if (array.size() > 0) {
                        for (int i = 0; i < array.size(); i++) {
                            HotScienceModel.DataBean dataBean = array.get(i);
                            data_list.add(dataBean);
                        }
                        //String hotspotsid, String areaid, String areaname, String isforegin, String pic
                        data_list.add(new HotScienceModel.DataBean("1", "-1", "更多", t + "", "more"));
                        hotAdapter hadapter = new hotAdapter(data_list, HomeActivity.this);
                        hotGrid.setAdapter(hadapter);
                    }
                } else {
                    ToastUtils.show(HomeActivity.this, model.getMessage());
                }
            }
        });
    }

    //获取bananer图
    public void getBananer() {
        String url = Constants.URL_SYSAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("categoryId", t + ""); //分类id(0:首页，1国内，2国外  3附近)
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
                            ToastUtils.show(HomeActivity.this, model.getMessage());
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
                Glide.with(HomeActivity.this).load(o).into(imageView);
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
//            Intent intent = new Intent(this, BrowseActivity.class);
//            intent.putExtra("url", adcontents);
//            startActivity(intent);
//        } else if ("3".equals(adtype)) {
//
//        }
//    }

class MyPagerAdapter extends FragmentPagerAdapter {


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FuJinFragment.getInstance(position, type);
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

class hotAdapter extends BaseAdapter {
    List<HotScienceModel.DataBean> list;
    Context context;

    public hotAdapter(List<HotScienceModel.DataBean> list, Context context) {
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
        hotAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.hot_item, parent, false);
            holder = new hotAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (hotAdapter.ViewHolder) convertView.getTag();
        }
        final HotScienceModel.DataBean bean = list.get(position);
        String hotspotsid = bean.getHotspotsid();
        String pic = bean.getPic();
        holder.bgImg.setType(RoundImageView.TYPE_ROUND);// 设置为圆角图片
        int width = (MyApp.getScreenWidth() - CommonUtils.dip2px(context, 48)) / 4;
        ViewGroup.LayoutParams params = holder.bgImg.getLayoutParams();
        params.width = width;
        params.height = width;
        holder.bgImg.setLayoutParams(params); //129201-10002097
        if ("more".equals(pic)) {
            holder.bgImg.setImageResource(R.drawable.gengduo_bg);
        } else {
                Glide.with(context).load(bean.getPic()).error(R.mipmap.dibai).into(holder.bgImg);

        }
        holder.cityTxt.setText(bean.getAreaname());
        holder.bgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HomeScenicActivity.class).putExtra("type", t).putExtra("isforeign",bean.getIsforegin()).putExtra("areaid",bean.getAreaid())); //热门景点
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.bg_img)
        RoundImageView bgImg;
        @Bind(R.id.city_txt)
        TextView cityTxt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
}
