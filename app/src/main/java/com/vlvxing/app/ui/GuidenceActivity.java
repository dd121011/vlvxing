package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vlvxing.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 引导页
 *
 * @ClassName:GuidenceActivity
 * @PackageName:com.youqin.pinche.ui.pinche
 * @Create On 2016/10/9   13:11
 * @Site:http://www.handongkeji.com
 * @author:zhouhao
 * @Copyrights 2016/10/9 handongkeji All rights reserved.
 */
public class GuidenceActivity extends Activity {


    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.indicater_container)
    LinearLayout indicaterContainer;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidence);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {
        inflater = getLayoutInflater();
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyPagerAdapter());

//        for (int i = 0; i < 4; i++) {
//            ImageView iv = new ImageView(this);
////            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(8, 8);
////            // 设置每个小圆点距离左边的间距
////            margin.setMargins(6, 0, 6, 0);
////            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(20, 20);
////            iv.setLayoutParams(params);
////            iv.setImageResource(R.drawable.guide_indicator_bg);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 0, 10, 0);
//            iv.setLayoutParams(lp);
//            iv.setImageResource(R.drawable.viewpager_indicater_selector);
//            if (i == 0) {
//                iv.setSelected(true);
//            }
//            indicaterContainer.addView(iv);
////            indicaterContainer.addView(iv,margin);
//        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int childCount = indicaterContainer.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i == position) {
                        indicaterContainer.getChildAt(i).setSelected(true);
                    } else {
                        indicaterContainer.getChildAt(i).setSelected(false);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.layout_guide_item, container, false);

            if (position == 0) {
                view.setBackgroundResource(R.mipmap.guide_01);
            } else if (position == 1) {
                view.setBackgroundResource(R.mipmap.guide_02);
            } else if (position == 2) {
                view.setBackgroundResource(R.mipmap.guide_03);
            } else if (position == 3) {
                view.setBackgroundResource(R.mipmap.guide_04);
                View viewById = view.findViewById(R.id.skip);
                viewById.setVisibility(View.VISIBLE);
                viewById.setOnClickListener(v -> {
                    startActivity(new Intent(GuidenceActivity.this, MainActivity.class));
                    GuidenceActivity.this.finish();
                });
            }

            container.addView(view);
            return view;
        }
    }
}
