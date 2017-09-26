package com.handongkeji.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 加载图片的监听器
 * 
 * @ClassName AnimateFirstDisplayListener
 * 
 * @PackageName com.handongkeji.utils
 * 
 * @CreateOn 2015-12-8下午6:53:00
 * 
 * @Site http://www.handongkeji.com
 * 
 * @author chaiqs
 * 
 * @Copyrights 2015-12-8 handongkeji All rights reserved.
 */
public class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		if (loadedImage != null) {
			ImageView imageView = (ImageView) view;
			boolean firstDisplay = !displayedImages.contains(imageUri);
			if (firstDisplay) {
				FadeInBitmapDisplayer.animate(imageView, 500);
				displayedImages.add(imageUri);
			}
		}
	}

}
