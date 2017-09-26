package com.vlvxing.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.fragment.FuJinFragment;
import com.vlvxing.app.model.HotRecomModel;
import com.vlvxing.app.model.PlaneScreenBottomListViewFirstModel;
import com.vlvxing.app.ui.LineDetailsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class PlaneScreenBottomListViewAdapter extends BaseAdapter {
	Context context;
	List<PlaneScreenBottomListViewFirstModel> list;

	public PlaneScreenBottomListViewAdapter(List<PlaneScreenBottomListViewFirstModel> list, Context context) {
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.plane_bottom_first_listview_layout, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final PlaneScreenBottomListViewFirstModel bean = list.get(position);
		holder.title.setText(bean.getTitle());
		holder.price.setText("￥"+bean.getPrice());

		holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			}
		});

		return convertView;
	}

	class ViewHolder {
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.price)
		TextView price;
		@Bind(R.id.check)
		CheckBox check;


		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
