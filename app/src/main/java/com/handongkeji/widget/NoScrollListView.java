package com.handongkeji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 项目名称：NoScrollListView
 * 创建人：Zopahr
 * 创建时间：2017/10112 14:00
 * 修改备注：解决scrollview冲突
 */
public class NoScrollListView extends ListView{

    public NoScrollListView(Context context, AttributeSet attrs){
         super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
         super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //下面这句话是关键
    	if (ev.getAction()==MotionEvent.ACTION_MOVE) {
    		return true;
    	}
    	return super.dispatchTouchEvent(ev);//也有所不同哦
    }

}