package com.handongkeji.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;

import com.umeng.socialize.utils.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumAsyncTask extends
		AsyncTask<Void, List<ImageItem>, List<ImageItem>> {
	
	private static final int DEFAULT_NUM = 50 ;
	
	private int numPerLoad;
	Context context;
	ContentResolver cr;

	boolean hasBuildImagesBucketList = false;

	public AlbumAsyncTask(Context context) {
		super();
		this.context = context;
		cr = context.getContentResolver();
		numPerLoad = DEFAULT_NUM;
	}
	
	public void setNumPerLoading(int number){
		this.numPerLoad = number;
	}

	private List<ImageItem> buildImagesBucketList() {
		
		List<ImageItem> imageList = new ArrayList<ImageItem>();
		String columns[] = new String[] { Media._ID, Media.DATA, Media.TITLE,
				Media.SIZE , Media.ORIENTATION};
		Cursor cur = cr.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
				Media.BUCKET_ID + " DESC");
		
		if (cur.moveToFirst()) {
			int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
			int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
			int orientationIndex = cur.getColumnIndexOrThrow(Media.ORIENTATION);
			do {
				String _id = cur.getString(photoIDIndex);
				String path = cur.getString(photoPathIndex);
				String orientation = cur.getString(orientationIndex);

				ImageItem imageItem = new ImageItem();
				imageItem.imageId = _id;
				imageItem.imagePath = path;
				Log.d("aaa", "orientation "+orientation);
				orientation = TextUtils.isEmpty(orientation)?"0":orientation;
				imageItem.setOrientation(Float.valueOf(orientation));
				imageItem.thumbnailPath = getThumbnail(_id);
				File f = new File(path);
				// 判断图片是否有效(能否打开)
				if (f.exists() && f.length() > 0) {
					imageList.add(imageItem);
				}
				if (imageList.size() == numPerLoad) {
					List<ImageItem> list = new ArrayList<ImageItem>(imageList);
					publishProgress(list);
					imageList.clear();
				}

			} while (cur.moveToNext());
		}
		hasBuildImagesBucketList = true;
		cur.close();
		return imageList;
	}

	private static final String[] THUMBNAIL_STORE_IMAGE = {
			MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.DATA };

	private String getThumbnail(String id) {
		// 获取大图的缩略图
		Cursor cursor = cr.query(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
				THUMBNAIL_STORE_IMAGE, MediaStore.Images.Thumbnails.IMAGE_ID
						+ " = ?", new String[] { id }, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			String data = cursor.getString(1);
			cursor.close();
			return data;
		}
		cursor.close();
		return null;
	}

	@Override
	protected void onPreExecute() {
		cr = context.getContentResolver();
	}

	@Override
	protected void onProgressUpdate(List<ImageItem>... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(List<ImageItem> result) {
		super.onPostExecute(result);
	}

	@Override
	protected List<ImageItem> doInBackground(Void... params) {
		List<ImageItem> buildImagesBucketList = buildImagesBucketList();
		return buildImagesBucketList;
	}


}
