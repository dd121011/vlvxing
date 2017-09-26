package com.handongkeji.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.handongkeji.modle.ParamsModle;
import com.vlvxing.app.R;


public class SpinerPopWindowAdapter extends BaseAdapter {

	public static interface IOnItemSelectListener {
		public void onItemClick(int pos);
	};

	private Context mContext;
	private List<ParamsModle> mTypeList = new ArrayList<ParamsModle>();
	private LayoutInflater mInflater;
	private String mTextString;

	public SpinerPopWindowAdapter(Context context) {
		init(context);
	}

	public void refreshData(List<ParamsModle> typeList, String text) {
		mTypeList = typeList;
		mTextString = text;
	}

	private void init(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		return mTypeList == null ? 0 : mTypeList.size();
	}

	@Override
	public Object getItem(int pos) {
		return mTypeList.get(pos).getDesc();
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.spiner_item_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textView);
			viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Object item = getItem(pos);
		viewHolder.mTextView.setText(item.toString());
		if (mTextString.equals(item.toString())) {
//			viewHolder.mTextView.setTextColor(Color.parseColor("#7ab33e"));
			viewHolder.mImageView.setSelected(true);
		} else {
//			viewHolder.mTextView.setTextColor(Color.parseColor("#666666"));
			viewHolder.mImageView.setSelected(false);
		}

		return convertView;
	}

	public static class ViewHolder {
		public TextView mTextView;
		public ImageView mImageView;
	}

}
