package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.handongkeji.ui.BaseActivity;
import com.handongkeji.widget.FixedViewPager;
import com.handongkeji.widget.PagerSlidingTabStrip;
import com.vlvxing.app.R;
import com.vlvxing.app.common.DecoratorViewPager;
import com.vlvxing.app.fragment.HomeScenicFragment;
import com.vlvxing.app.fragment.WeekFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 国内景点
 */

public class HomeScenicActivity extends BaseActivity {

    @Bind(R.id.pagerTabStrip)
    PagerSlidingTabStrip pagerTabStrip;
    @Bind(R.id.viewPager)
    DecoratorViewPager viewPager;
    String[] tabTitles;
    private int type;
    private String isforeign,areaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        viewPager.setNestedpParent((ViewGroup) viewPager.getParent());
        Intent intent=getIntent();
        isforeign=intent.getStringExtra("isforeign");
        areaid=intent.getStringExtra("areaid");
        type=intent.getIntExtra("type",0); //type 1国内  2境外
        if (type==1){
            tabTitles = new String[]{"全部", "特色","特价","跟团","排序"};
        }else {
            tabTitles = new String[]{"全部", "特色","特价","免签","排序"};
        }
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pagerTabStrip.setViewPager(viewPager);
    }

        @OnClick({R.id.return_lin, R.id.serch_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.serch_lin:
                startActivity(new Intent(this, SelectActivity.class).putExtra("type",3));
                break;
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HomeScenicFragment.getInstance(position,type,isforeign,areaid);
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
