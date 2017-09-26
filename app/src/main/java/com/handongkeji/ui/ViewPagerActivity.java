/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handongkeji.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;


import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.handongkeji.widget.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vlvxing.app.R;

import java.io.File;
import java.util.ArrayList;

/**
 * ViewPager切换和双击放大缩小
 * 
 * @ClassName ViewPagerActivity
 * 
 * @PackageName com.handongkeji.photoview.sample
 * 
 * @Create On 2016-1-21 上午9:11:47
 * 
 * @Site http //www.handongkeji.com
 * 
 * @author tany
 * 
 * @Copyrights 2016-1-21 handongkeji All rights reserved.
 */
public class ViewPagerActivity extends Activity {

	private ViewPager mViewPager;
	private ArrayList<String> list;
	private int position;
	private DisplayImageOptions options;
	private ImageView img;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// mViewPager = new HackyViewPager(this);
		setContentView(R.layout.viewpage_delite);
		Intent intent = getIntent();
		list = intent.getStringArrayListExtra("viewpagerlist");

		if (list == null) {
			list = new ArrayList<String>();
		}
		// if (list.size() >= 3) {
		// list.remove(0);
		// list.remove(list.size() - 1);
		// }
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading).showImageForEmptyUri(R.mipmap.photo_pingjia).showImageOnFail(R.mipmap.photo_pingjia).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();

		position = intent.getIntExtra("position", 0);
		mViewPager = (ViewPager) findViewById(R.id.delete);
		img = (ImageView) findViewById(R.id.iv_delete);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ViewPagerActivity.this.finish();
			}
		});
		mViewPager.setAdapter(new SamplePagerAdapter());
		mViewPager.setCurrentItem(position);
	}

	class SamplePagerAdapter extends PagerAdapter {

		/*
		 * private static int[] sDrawables = { R.drawable.wallpaper,
		 * R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
		 * R.drawable.wallpaper, R.drawable.wallpaper };
		 */

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			String url = list.get(position);
			if (url.startsWith("http://")) {
				
				if (url.contains("_mid")) {
					url.replaceFirst("_mid", "_big");
				}
			}else{
				url = Uri.fromFile(new File(url))+"";
			}
			com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(url, photoView, options, animateFirstListener);
			// photoView.setImageResource(sDrawables[position]);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}
