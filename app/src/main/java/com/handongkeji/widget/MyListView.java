package com.handongkeji.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListView;

import com.vlvxing.app.R;


/**
 * 自定义ListView，可上拉加载
 * @author zhouhao
 *
 */
public class MyListView extends ListView implements OnScrollListener {

	private View mFooterView;
	private boolean hasMore = true;

	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}


	public MyListView(Context context) {
		this(context, null);
	}
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyListView);
		int color = typedArray.getColor(R.styleable.MyListView_footer_background, Color.TRANSPARENT);
		typedArray.recycle();

		mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_footer_view, this, false);
		mFooterView.setBackgroundColor(color);
		if (mFooterView != null && Build.VERSION.SDK_INT < 19) {
			addFooterView(mFooterView);
			mFooterView.setVisibility(View.GONE);
		}
		this.setOnScrollListener(this);
	}

	private boolean isLoading = false;
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if (scrollState == SCROLL_STATE_IDLE) {
			// 到底部，加载更多
			if (isBottomReached()) {
				if (!hasMore) {
					if (Build.VERSION.SDK_INT >= 19) {
						removeFooterView(mFooterView);
					}else{
						mFooterView.setVisibility(View.GONE);
					}
					return;
				}
				if (Build.VERSION.SDK_INT >= 19) {
					if (isLoading) {
						return;
					}
					addFooterView(mFooterView, null, true);
					smoothScrollToPosition(getAdapter().getCount()-1);
					isLoading = true;
				}else{
					mFooterView.setVisibility(View.VISIBLE);
				}
				if (loadDataListener != null) {
					loadDataListener.onLoadMore();
				}
			}

		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

	private boolean isBottomReached() {
		final Adapter adapter = getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;

		} else {
			final int lastItemPosition = getCount() - 1;
			final int lastVisiblePosition = getLastVisiblePosition();

			if (lastVisiblePosition >= lastItemPosition - 1) {
				final int childIndex = lastVisiblePosition - getFirstVisiblePosition();
				final View lastVisibleChild = getChildAt(childIndex);
				if (lastVisibleChild != null) {
					// 最后一项已经到头并继续向上拉
					return lastVisibleChild.getBottom() <= getHeight();
				}
			}
		}

		return false;
	}

	private LoadDataListener loadDataListener;

	public void setLoadDataListener(LoadDataListener listener) {
		this.loadDataListener = listener;
	}

	public interface LoadDataListener {

		public void onLoadMore();

	}

	public void onLoadComplete(boolean hasMore) {
		if (Build.VERSION.SDK_INT >= 19) {
			removeFooterView(mFooterView);
			isLoading = false;
		}else {
			mFooterView.setVisibility(View.GONE);
		}
	}
	
	public void setHasMore(boolean hasMore){
		this.hasMore = hasMore;
	}
	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
//	   super.onMeasure(widthMeasureSpec, expandSpec);
//	}
}
