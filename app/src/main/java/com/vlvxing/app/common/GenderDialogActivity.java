package com.vlvxing.app.common;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


import com.handongkeji.selecity.model.SPUtils;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.ui.BrowseActivity;
import com.vlvxing.app.R;


/**
 *
 * @ClassName: UserHeadimgSetDialogActivity
 *
 * @PackageName:com.yidianxinxi.user
 *
 * @Create On 2016-2-16上午9:13:09
 *
 * @Site:http://www.handongkeji.com
 *
 * @author:wmm
 *
 * @Copyrights 2016-2-162015-12-16 handongkeji All rights reserved.
 */
public class GenderDialogActivity extends BaseActivity {

	private Button wBaidu, mapBaidu, btn_cancel,map_gaode,map_google,map_tencent;
	private LinearLayout layout;
	Intent mIntent = new Intent();
	private static List<String> mapPackage = new ArrayList<String>(6);
	private double weidu;
	private double jingdu;
	private String address;
	private String x;
	private String y;
	private double currentLat;
	private double currentLng;
	private String origin;
	// 初始化各地图包的信息
			static {
				mapPackage.add("uninstalled");
				mapPackage.add("com.google.android.apps.maps");
				mapPackage.add("brut.googlemaps");
				mapPackage.add("com.baidu.BaiduMap");
				mapPackage.add("com.mapbar.android.mapbarmap");
				mapPackage.add("com.autonavi.minimap");
			}

	private String origin1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_genderdimgset);
		initViews();
		initOnClick();

	}

	/**
	 * 初始化视图
	 */
	public void initViews() {

		Intent intent = getIntent();
		weidu = intent.getDoubleExtra("weidu", 0);
		jingdu = intent.getDoubleExtra("jingdu",0);
		currentLat = intent.getDoubleExtra("currentLat", 0);
		currentLng = intent.getDoubleExtra("currentLng", 0);
		address = intent.getStringExtra("address");
		origin1 = intent.getStringExtra("origin");

		wBaidu = (Button) findViewById(R.id.map_w_baidu);
		mapBaidu = (Button) findViewById(R.id.map_baidu);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		map_gaode = (Button) findViewById(R.id.map_gaode);
		map_google = (Button) findViewById(R.id.map_google);
		map_tencent = (Button) findViewById(R.id.map_tencent);

		layout = (LinearLayout) findViewById(R.id.pop_layout);
		if (isInstall("com.autonavi.minimap")) {

			map_gaode.setVisibility(View.VISIBLE);
		}
		if (isInstallByread("com.baidu.BaiduMap")) {
			mapBaidu.setVisibility(View.VISIBLE);
		}

		if (isInstall("com.baidu.BaiduMap")) {
			mapBaidu.setVisibility(View.VISIBLE);
		}

		HashMap<String, String> hashMap = SPUtils.getLoc(GenderDialogActivity.this);
		x = hashMap.get("x");
		y = hashMap.get("y");
		this.origin = address.substring(0,2);
	}

	// 检查手机装没有地图
	private boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	private boolean isInstall(String packgeName){
		PackageInfo packageInfo = null ;
		try {
			packageInfo = getPackageManager().getPackageInfo(packgeName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			packageInfo = null ;
		}


		if (packageInfo == null) {
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 初始化事件
	 */
	public void initOnClick() {
		// 添加按钮监听
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GenderDialogActivity.this.finish();
			}
		});
		wBaidu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				//百度地图网页版跳转  http://api.map.baidu.com/direction?origin=&destination=北京大望路22&mode=driving&origin_region=北京&destination_region=北京&output=html&src=com.vlvxing.app
				mIntent = new Intent(GenderDialogActivity.this, BrowseActivity.class);
//				mIntent.putExtra("url", "http://api.map.baidu.com/line?region=北京&name=518&output=html&src=yourCompanyName|yourAppName");
				mIntent.putExtra("url", "http://api.map.baidu.com/direction?origin=北京&destination="+address+"&mode=driving&origin_region="+myApp.getCity_name()+"&destination_region="+origin+"&output=html&src=com.vlvxing.app");
				startActivity(mIntent);
				GenderDialogActivity.this.finish();
			}

		});

		mapBaidu.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
//				//移动APP调起Android百度地图方式举例
				try {
					mIntent = Intent.getIntent("intent://map/direction?origin="+origin1+"&destination="+"大望路地铁站"+"&mode=driving&origin_region="+myApp.getCity_name()+"&destination_region="+origin+"&src=com.vlvxing.app#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//					mIntent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving&region=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
					startActivity(mIntent); //启动调用
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});

		map_gaode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//高德地图跳转
				Intent mIntent = new Intent("android.intent.action.VIEW",
						android.net.Uri.parse("androidamap://navi?sourceApplication=com.vlvxing.app&poiname="+address+"&lat="+weidu+"&lon="+jingdu+"&dev=0&style=2"));
				mIntent.setPackage("com.autonavi.minimap");
				startActivity(mIntent);
			}
		});

		map_tencent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GenderDialogActivity.this, BrowseActivity.class);
				String url = "http://apis.map.qq.com/uri/v1/routeplan?type=bus&fromcoord=+"+currentLat+","+currentLng+"&tocoord="+weidu+","+jingdu+"&policy=1&referer=com.vlvxing.app";
				intent.putExtra("url", url);
				startActivity(intent);
			}
		});

//		map_google.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				//高德地图跳转
////				Intent mIntent = new Intent("android.intent.action.VIEW",
////						android.net.Uri.parse("androidamap://navi?sourceApplication=com.fangyuan.emianbao&poiname="+address+"&lat="+lag+"&lon="+lng+"&dev=0&style=2"));
//				mIntent.setPackage("com.autonavi.minimap");
//				startActivity(mIntent);
////				mIntent.putExtra("woman", "男");
////				Result();
//			}
//		});

	}

//	private void Result() {
//		// 设置结果，并进行传送
////		setResult(BaiDuMap.PHOTO_GENDER_CUT, mIntent);
//		GenderDialogActivity.this.finish();
//
//	}

	// 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		GenderDialogActivity.this.finish();
		return true;
	}

	// ----------------------------------------------------

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
