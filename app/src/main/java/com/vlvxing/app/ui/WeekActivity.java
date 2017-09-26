package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.handongkeji.ui.BaseActivity;
import com.handongkeji.widget.FixedViewPager;
import com.handongkeji.widget.PagerSlidingTabStrip;
import com.vlvxing.app.R;
import com.vlvxing.app.fragment.FuJinFragment;
import com.vlvxing.app.fragment.WeekFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 附近--自驾游、周末游
 */

public class WeekActivity extends BaseActivity {

    @Bind(R.id.pagerTabStrip)
    PagerSlidingTabStrip pagerTabStrip;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    String[] tabTitles;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        tabTitles = new String[]{"全部", "自助", "特价", "主题", "排序"};
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
            return WeekFragment.getInstance(position+1,type);
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
