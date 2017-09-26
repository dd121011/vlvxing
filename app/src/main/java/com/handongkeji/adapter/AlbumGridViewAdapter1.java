package com.handongkeji.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;


import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.handongkeji.utils.CommonUtils;
import com.handongkeji.utils.ImageItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.vlvxing.app.R;
import com.vlvxing.app.common.MyApp;

import java.util.ArrayList;

/**
 * 这个是显示一个文件夹里面的所有图片时用的适配器
 * 
 * @author king
 * @version 2014年10月18日 下午11:49:35
 */
public class AlbumGridViewAdapter1 extends BaseAdapter implements
		OnClickListener {

	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private ArrayList<ImageItem> selectedDataList;
	private DisplayImageOptions options;
	private AnimateFirstDisplayListener animateFirstDisplayListener;

	private int width;
	private int height;
	private int widthPixels;

	public AlbumGridViewAdapter1(Context c, ArrayList<ImageItem> dataList,
								 ArrayList<ImageItem> selectedDataList) {
		mContext = c;
		this.dataList = dataList;
		this.selectedDataList = selectedDataList;

		height = CommonUtils.dip2px(mContext, 100);
		widthPixels = MyApp.getScreenWidth();
		width = (widthPixels - CommonUtils.dip2px(mContext, 36));

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.plugin_camera_no_pictures)
				.showImageOnFail(R.drawable.plugin_camera_no_pictures)
				.cacheInMemory(false).cacheOnDisk(false)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		animateFirstDisplayListener = new AnimateFirstDisplayListener();
	}

	public int getCount() {
		return dataList.size() == 0 ?0 :dataList.size()+2;
	}

	public Object getItem(int position) {
		if (position == 0) {
			return dataList.size() > 0 ? dataList.get(0) : null;
		} else {
			return position == (1 | 2) ? null : dataList.get(position - 2);
		}
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return position == 0 ? 0 : 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * 存放列表项控件句柄
	 */
	private class ViewHolder {
		public ImageView imageView;
		public ToggleButton toggleButton;
		public Button choosetoggle;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (getItemViewType(position) == 0) {

			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_grid_2, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.image_view);
				viewHolder.toggleButton = (ToggleButton) convertView
						.findViewById(R.id.toggle_button);
				viewHolder.choosetoggle = (Button) convertView
						.findViewById(R.id.choosedbt);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// 重新设置 高度和 宽度
			LayoutParams params = convertView.getLayoutParams();
			params.width = widthPixels;
			params.height = widthPixels / 2;
			convertView.setLayoutParams(params);

			ImageView image = (ImageView) convertView
					.findViewById(R.id.image_view);
			ImageItem item = dataList.get(0);
			
			
			Object tag = image.getTag();
			if (tag == null || !tag.equals(item.imagePath)) {
				image.setImageResource(R.drawable.plugin_camera_no_pictures);
			}
			
			
			image.setTag(item.imagePath);
			ImageLoader.getInstance().displayImage("file://"+item.imagePath, image, options,animateFirstDisplayListener);
			convertView.findViewById(R.id.camera).setOnClickListener(this);

			viewHolder.toggleButton.setTag(position);
			viewHolder.choosetoggle.setTag(position);
			viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(
					viewHolder.choosetoggle));
			if (selectedDataList.contains(item)) {
				viewHolder.toggleButton.setChecked(true);
				viewHolder.choosetoggle.setVisibility(View.VISIBLE);
			} else {
				viewHolder.toggleButton.setChecked(false);
				viewHolder.choosetoggle.setVisibility(View.GONE);
			}

		} else {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.plugin_camera_select_imageview, parent, false);
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.image_view);
				viewHolder.toggleButton = (ToggleButton) convertView
						.findViewById(R.id.toggle_button);
				viewHolder.choosetoggle = (Button) convertView
						.findViewById(R.id.choosedbt);
				convertView.setTag(viewHolder);
				
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			if (position == 1 || position == 2) {
				convertView.setVisibility(View.GONE);
				convertView.setEnabled(false);
				
				LayoutParams params = convertView.getLayoutParams();
				params.height = widthPixels/2;
				convertView.setLayoutParams(params);
				
			} else {
				convertView.setVisibility(View.VISIBLE);
				convertView.setEnabled(true);
				
				LayoutParams params = convertView.getLayoutParams();
				params.height = height;
				convertView.setLayoutParams(params);

				ImageItem item = dataList.get(position - 2);

				
				String localePath = TextUtils.isEmpty(item.imagePath) ? item.thumbnailPath
						: item.imagePath;
				
				Object tag = viewHolder.imageView.getTag();
                if (tag == null || !tag.equals(localePath)) {
                	viewHolder.imageView.setImageResource(R.drawable.plugin_camera_no_pictures);
				}
				
				if (TextUtils.isEmpty(localePath)) {
					viewHolder.imageView
							.setImageResource(R.drawable.plugin_camera_no_pictures);
				} else {
					viewHolder.imageView.setTag(localePath);
					ImageLoader.getInstance().displayImage("file://"+localePath,
							viewHolder.imageView, options,animateFirstDisplayListener);
				}
				viewHolder.toggleButton.setTag(position);
				viewHolder.choosetoggle.setTag(position);
				viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(
						viewHolder.choosetoggle));
				if (selectedDataList.contains(item)) {
					viewHolder.toggleButton.setChecked(true);
					viewHolder.choosetoggle.setVisibility(View.VISIBLE);
				} else {
					viewHolder.toggleButton.setChecked(false);
					viewHolder.choosetoggle.setVisibility(View.GONE);
				}
			}


		}
		return convertView;

	}

	private class ToggleClickListener implements OnClickListener {
		Button chooseBt;

		public ToggleClickListener(Button choosebt) {
			this.chooseBt = choosebt;
		}

		@Override
		public void onClick(View view) {
			if (view instanceof ToggleButton) {
				
				ToggleButton toggleButton = (ToggleButton) view;
				int position = (Integer) toggleButton.getTag();
				position = position > 0 ? position -2 : 0 ;
				if (dataList != null && mOnItemClickListener != null&& position < dataList.size()) {
					mOnItemClickListener.onItemClick(toggleButton, position,
							toggleButton.isChecked(), chooseBt);
				}
			}
		}
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	public interface OnItemClickListener {
		public void onItemClick(ToggleButton view, int position,
								boolean isChecked, Button chooseBt);

		public void onCameraClick(ImageView v);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.camera:
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onCameraClick((ImageView) v);
			}
			break;

		default:
			break;
		}
	}
}
