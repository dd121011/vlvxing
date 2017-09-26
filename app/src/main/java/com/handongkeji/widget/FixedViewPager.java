package com.handongkeji.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FixedViewPager extends ViewPager {
    public FixedViewPager(Context context) {
        super(context);
        init();
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("aaa", "onMeasure: heightMeasureSpec " + MeasureSpec.getSize(heightMeasureSpec));
        int childCount = getChildCount();
        int currentItem = getCurrentItem();
        if (currentItem >= 0 && currentItem < childCount) {
            View childAt = getChildAt(currentItem);
            childAt.measure(widthMeasureSpec, 0);
            int measuredHeight = childAt.getMeasuredHeight();
            Log.d("aaa", "onMeasure: measuredHeight " + measuredHeight);
            if (MeasureSpec.getSize(heightMeasureSpec) < measuredHeight) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

}
