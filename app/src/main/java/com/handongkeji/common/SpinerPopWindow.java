package com.handongkeji.common;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.handongkeji.adapter.SpinerPopWindowAdapter;
import com.handongkeji.modle.ParamsModle;
import com.vlvxing.app.R;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener {

	private Context mContext;
	private ListView mListView;
	private SpinerPopWindowAdapter mAdapter;
	private SpinerPopWindowAdapter.IOnItemSelectListener mItemSelectListener;

	public SpinerPopWindow(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public void setItemListener(SpinerPopWindowAdapter.IOnItemSelectListener listener) {
		mItemSelectListener = listener;
	}

	public void setAdatper(SpinerPopWindowAdapter adapter) {
		mAdapter = adapter;
		mListView.setAdapter(mAdapter);
	}

	private void init() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
		setContentView(view);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		mListView = (ListView) view.findViewById(R.id.listview);
		mListView.setOnItemClickListener(this);
	}

	public void refreshData(List<ParamsModle> list, String text) {
		if (list != null) {
			if (mAdapter != null) {
				mAdapter.refreshData(list, text);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null) {
			mItemSelectListener.onItemClick(pos);
		}
	}

}