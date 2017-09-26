package com.qunar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.qunar.adapter.PlaneOrderAdapter;
import com.vlvxing.app.R;

/**
 * 我的机票订单的 行布局中自定义控件 滑动删除
 */

public class SlideRecyclerView extends RecyclerView {
    private Context mContext;

    //上一次的触摸点
    private int mLastX, mLastY;
    //当前触摸的item的位置
    private int mPosition = -1;

    //item对应的布局
    private LinearLayout mItemLayout;
    /**
     * 用于记录上一次操作的那个item
     */
    private LinearLayout mTempLayout;
    //删除按钮
    private TextView mDelete;

    //最大滑动距离(即删除按钮的宽度)
    private int mMaxLength;
    //是否在垂直滑动列表
    private boolean isDragging;
    //item是在否跟随手指移动
    private boolean isItemMoving;

    //item是否开始自动滑动
    private boolean isStartScroll;
    public static final int CLOSED = 0;
    public static final int CLOSING = 1;
    public static final int OPENNING = 2;
    public static final int OPENED = 3;
    private int mDeleteBtnState;
    private Scroller mScroller;
    /**
     * 用于收起上一次操作的那个item的Scroller
     */
    private Scroller mTempScroller;

    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * 删除按钮回调
         *
         * @param position
         */
        void onDeleteClick(int position);
    }

    private OnItemClickListener mListener;

    public SlideRecyclerView(Context context) {
        this(context, null);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mScroller = new Scroller(context, new LinearInterpolator());
        mTempScroller = new Scroller(context, new LinearInterpolator());
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDeleteBtnState == CLOSING || mDeleteBtnState == OPENNING) {
                    return false;
                }
                View view = findChildViewUnder(x, y);
                if (view == null) {
                    return false;
                }
                PlaneOrderAdapter.MyViewHolder viewHolder = (PlaneOrderAdapter.MyViewHolder) getChildViewHolder(view);
                if (mDeleteBtnState == OPENED) {
                    if (mPosition != viewHolder.getAdapterPosition() && mTempLayout != null) {
                        mTempScroller.startScroll(mTempLayout.getScrollX(), 0, -mMaxLength, 0, 200);
                        invalidate();
                        mDeleteBtnState = CLOSED;
                    }
                }
                if (mPosition != viewHolder.getAdapterPosition()) {
                    mItemLayout = viewHolder.layout;
                    mPosition = viewHolder.getAdapterPosition();
                    mDelete = (TextView) mItemLayout.findViewById(R.id.item_delete);
                    mMaxLength = mDelete.getWidth();
                    mDelete.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onDeleteClick(mPosition);
                            mItemLayout.scrollTo(0, 0);
                            mDeleteBtnState = CLOSED;
                        }
                    });
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;
                int scrollX = mItemLayout.getScrollX();
                if (Math.abs(dx) > Math.abs(dy)) {//左边界检测
                    isItemMoving = true;
                    if (scrollX + dx <= 0) {
                        mItemLayout.scrollTo(0, 0);
                        return true;
                    } else if (scrollX + dx >= mMaxLength) {//右边界检测
                        mItemLayout.scrollTo(mMaxLength, 0);
                        return true;
                    }
                    mItemLayout.scrollBy(dx, 0);//item跟随手指滑动
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null) {
                    mListener.onItemClick(mItemLayout, mPosition);
                }
                isItemMoving = false;
                int deltaX = 0;
                int upScrollX = mItemLayout.getScrollX();
                if (upScrollX >= mMaxLength / 2) {//item的左滑动距离大于删除按钮宽度的一半，则则显示删除按钮
                    deltaX = mMaxLength - upScrollX;
                    mDeleteBtnState = OPENNING;
                } else if (upScrollX < mMaxLength / 2) {//否则隐藏
                    deltaX = -upScrollX;
                    mDeleteBtnState = CLOSING;
                }
                //item自动滑动到指定位置
                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mTempScroller.computeScrollOffset()) {
            mTempLayout.scrollTo(mTempScroller.getCurrX(), mTempScroller.getCurrY());
            invalidate();
        }
        if (mScroller.computeScrollOffset()) {
            mItemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mDeleteBtnState == CLOSING) {
                mDeleteBtnState = CLOSED;
            }
            if (mDeleteBtnState == OPENNING) {
                mDeleteBtnState = OPENED;
                mTempLayout = mItemLayout;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
