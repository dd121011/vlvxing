package com.vlvxing.app.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.handongkeji.selecity.model.SPUtils;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyTitleLayout;
import com.vlvxing.app.R;

/**
 * 显示地图
 * 
 * @ClassName BaiDuMap
 * 
 * @PackageName com.handongkeji.wutongwang.common
 * 
 * @Create On 2015-12-18 上午9:56:39
 * 
 * @Site http //www.handongkeji.com
 * 
 * @author tany
 * 
 * @Copyrights 2015-12-18 handongkeji All rights reserved.
 */
public class BaiDuMap extends Activity {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
//	private LocationClient locationClient;
//	private LocationMode locationMode;
//	private BaiduMap baiduMap;
//	private boolean isFirLoc = true;
	private double lng, lag;
	public MyLocationListenner myListener = new MyLocationListenner();
	public LocationClient mLocClient;
	// private LinearLayout ll;
	private MyTitleLayout topTitle;
	private String address;
	private LinearLayout sta;
	public static final int PHOTO_GENDER_CUT = 5;// 
//	private double weidu;
//	private double jingdu;
	private double weidu2;
	private double jingdu2;
	private String origin;


	// private AddressPopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baidumap);
		topTitle = (MyTitleLayout) findViewById(R.id.mapTopTitle);
		topTitle.setTitle("地址");
		topTitle.setBackBtnVisible(true);
		topTitle.setRightText("");
		sta = (LinearLayout) findViewById(R.id.sta_naviga);
		
		sta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaiDuMap.this, GenderDialogActivity.class)
						.putExtra("weidu", weidu2)
						.putExtra("jingdu", jingdu2)
						.putExtra("address", address)
						.putExtra("origin",origin);
				startActivityForResult(intent, PHOTO_GENDER_CUT);
			}
		});
		startLocation();
	}

	// 开始定位
		private void startLocation() {
			Intent intent = getIntent();
			weidu2 = intent.getDoubleExtra("weidu",0);
			jingdu2 = intent.getDoubleExtra("jingdu",0);
			if (StringUtils.isStringNull(weidu2+""))
				lag = weidu2;
			if (StringUtils.isStringNull(jingdu2+""))
				lng = jingdu2;
			address = intent.getStringExtra("address");
			if (address == null || address.equals("null")) {
				address = "";
			}
			mMapView = (MapView) findViewById(R.id.tencentMap);
			mBaiduMap = mMapView.getMap();

			MapStatus mMapStatus = new MapStatus.Builder()
//					.target(new LatLng(lag, lng))
					.zoom(13)
					.build();
			// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

			// 改变地图状态
			mBaiduMap.animateMapStatus(mMapStatusUpdate);

			// 开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(13).build()));
			// 定位初始化
			mLocClient = new LocationClient(this);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(1000);
			mLocClient.setLocOption(option);
			mLocClient.start();
			mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
				@Override
				public void onMapStatusChangeStart(MapStatus status) {
					// updateMapState(status);
				}

				// 地图变化结束后的定位
				@Override
				public void onMapStatusChangeFinish(MapStatus status) {
					// updateMapState(status.target);
				}

				@Override
				public void onMapStatusChange(MapStatus status) {
					// updateMapState(status);
				}
			});
		}

		/**
		 * 定位SDK监听函数
		 */

		public class MyLocationListenner implements BDLocationListener {



			@Override
			public void onReceiveLocation(BDLocation location) {
				SPUtils.saveLoc(BaiDuMap.this, location.getLatitude(), location.getLongitude());
				origin = location.getAddress().address;
				// 此处设置开发者获取到的方向信息，顺时针0-360
				MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100).latitude(lag).longitude(lng).build();
				mBaiduMap.setMyLocationData(locData);
				mLocClient.stop();
				mLocClient.unRegisterLocationListener(this);
			}

			@Override
			public void onConnectHotSpotMessage(String s, int i) {

			}

			public void onReceivePoi(BDLocation poiLocation) {
			}
		}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

}
