package com.handongkeji.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.handongkeji.adapter.AlbumGridViewAdapter1;
import com.handongkeji.utils.AlbumAsyncTask;
import com.handongkeji.utils.Bimp;
import com.handongkeji.utils.ImageItem;
import com.handongkeji.utils.PublicWay;
import com.handongkeji.utils.Res;
import com.handongkeji.widget.MyTitleLayout;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个是进入相册显示所有图片的界面
 * 
 * @author king
 * @version 2014年10月18日 下午11:47:15
 */
public class AlbumActivity1 extends Activity implements OnClickListener {
	// 显示手机里的所有图片的列表控件
	private GridView gridView;
	// 当手机里没有图片时，提示用户没有图片的控件
	private TextView tv;
	// gridView的adapter
	private AlbumGridViewAdapter1 gridImageAdapter;
	// 完成按钮
	private Button okButton;
	// 标题
	MyTitleLayout topTitle;
	
	private ArrayList<ImageItem> dataList;
	
	 private String mPhotoPath;//拍照保存路径
	 private static final int REQUEST_CODE_CAMERA = 5;// 拍照
	 private static final int REQUEST_CODE_CROP = 6;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		setContentView(R.layout.plugin_camera_album1);
		// 注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
		
		topTitle = (MyTitleLayout) findViewById(R.id.addProductTopTitle);
		topTitle.setTitle("选择照片");
		topTitle.setBackBtnVisible(true);
		topTitle.setRightText("");
		
		init();
		initListener();
		// 这个函数主要用来控制预览和完成按钮的状态
		isShowOkBt();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// mContext.unregisterReceiver(this);
			gridImageAdapter.notifyDataSetChanged();
		}
	};

	// 完成按钮的监听
	private class AlbumSendListener implements OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

	// 初始化，给一些对象赋值
	private void init() {
		
		
		dataList = new ArrayList<ImageItem>();
		
		gridView = (GridView) findViewById(R.id.myGrid);
		gridImageAdapter = new AlbumGridViewAdapter1(this, dataList, Bimp.tempSelectBitmap);
		gridView.setAdapter(gridImageAdapter);
		tv = (TextView) findViewById(R.id.myText);
		gridView.setEmptyView(tv);
		okButton = (Button) findViewById(R.id.ok_button);
		okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
		
		new AlbumAsyncTask(this) {

			@Override
			protected void onProgressUpdate(List<ImageItem>... values) {
				dataList.addAll(values[0]);
				gridImageAdapter.notifyDataSetChanged();
			}

			@Override
			protected void onPostExecute(List<ImageItem> result) {
				dataList.addAll(result);
				gridImageAdapter.notifyDataSetChanged();
			}

		}.execute();
	}
	
	private void initListener() {

		gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter1.OnItemClickListener() {

			@Override
			public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked, Button chooseBt) {
				
					onPicClick(toggleButton, position, isChecked, chooseBt);
				}

			@Override
			public void onCameraClick(ImageView v) {
				onClick(v);
			}

		});

		okButton.setOnClickListener(new AlbumSendListener());

	}
	
	private void onPicClick(final ToggleButton toggleButton,
			int position, boolean isChecked, Button chooseBt) {
		if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
			toggleButton.setChecked(false);
			chooseBt.setVisibility(View.GONE);
			if (!removeOneData(dataList.get(position))) {
				Toast.makeText(AlbumActivity1.this, Res.getString("only_choose_num"), Toast.LENGTH_SHORT).show();
			}
			return;
		}
		if (isChecked) {
			chooseBt.setVisibility(View.VISIBLE);
			Bimp.tempSelectBitmap.add(dataList.get(position));
			okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
		} else {
			Bimp.tempSelectBitmap.remove(dataList.get(position));
			chooseBt.setVisibility(View.GONE);
			okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
		}
		isShowOkBt();
	}

	private boolean removeOneData(ImageItem imageItem) {
		if (Bimp.tempSelectBitmap.contains(imageItem)) {
			Bimp.tempSelectBitmap.remove(imageItem);
			okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			return true;
		}
		return false;
	}

	public void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {
			okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			okButton.setPressed(true);
			okButton.setClickable(true);
			okButton.setTextColor(Color.WHITE);
		} else {
			okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			okButton.setPressed(false);
			okButton.setClickable(false);
			okButton.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	public ArrayList<ImageItem> getPhotos(){
		return dataList;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}

	@Override
	protected void onRestart() {
		isShowOkBt();
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CODE_CAMERA:
			cameraCut();
			break;
		case REQUEST_CODE_CROP:
			if (data == null) {
				return;
			}
			if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
				Toast.makeText(this, "最多只能选9张图片", Toast.LENGTH_SHORT).show();
			}else{
				Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				ImageItem item = new ImageItem();
				item.setBitmap(bitmap);
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(mPhotoPath));
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					item.setImagePath(mPhotoPath);
					Bimp.tempSelectBitmap.add(item);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally{
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				this.finish();
			}
		default:
			break;
		}
	}
	
	 private void cameraCut() {
	        Intent intent = new Intent("com.android.camera.action.CROP");
	        
	        File file = new File(mPhotoPath);
	        if (!file.exists() || file.length() == 0) {
				return;
			}
	        
	        intent.setDataAndType(Uri.fromFile(new File(mPhotoPath)), "image/*");
	        intent.putExtra("crop", "true");
	        intent.putExtra("aspectX", 2);
	        intent.putExtra("aspectY", 1);
	        intent.putExtra("outputX", 300);
	        intent.putExtra("outputY", 150);
//				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(temp));
	        intent.putExtra("return-data", true);
//				intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//				intent.putExtra("noFaceDetection", true); // no face detection
	        startActivityForResult(intent, REQUEST_CODE_CROP);
	    }

	@Override
	public void onClick(View v) {
		mPhotoPath = Constants.CACHE_DIR_IMAGE + SystemClock.uptimeMillis() + ".jpg";
		 File f = new File(Constants.CACHE_DIR_IMAGE);
		 
		 if (!f.exists()) {
			f.mkdirs();
		}
           File file = new File(mPhotoPath);
           if (!file.exists()) {
               try {
                   file.createNewFile();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
           startActivityForResult(intent, REQUEST_CODE_CAMERA);
		
	}
}
