package com.vlvxing.app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.album.util.OverlayBitmapUtils;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.model.RecordMapModel;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.model.TrackDetailModel;
import com.vlvxing.app.utils.BDLocationUtils;
import com.vlvxing.app.utils.CacheData;
import com.vlvxing.app.utils.DataUtils;

import org.json.JSONException;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/21 0021.
 * 轨迹线路
 */

public class TrackActivity extends BaseActivity{

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.map)
    MapView mMapView;
    BaiduMap mBaiduMap;
//    /**
//     * 定位模式
//     */
//    private MyLocationConfiguration.LocationMode mCurrentMode;
//    /**
//     * 定位端
//     */
//    private LocationClient mLocClient;
    private Polyline mPolyline;
    private String id; //轨迹id
    List<TrackDetailModel.DataBean.PathinfosBean> lines=new ArrayList<>();
    List<LatLng> latLngs = new ArrayList<LatLng>(); //轨迹的点
    boolean isLocal=false;
    int radius = 300;
    float zoomLevel;
    private Overlay local_overlay;
    private String curAddress;
    private boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findscience_layout);
        ButterKnife.bind(this);
        headTitle.setText("轨迹线路");
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        mBaiduMap = mMapView.getMap();
        zoomLevel = mBaiduMap.getMapStatus().zoom;
        mMapView.setMapCustomEnable(false);
        mBaiduMap.setBuildingsEnabled(false); // 不显示楼快效果
        mMapView.showZoomControls(true); //设置是否显示缩放控件
        mMapView.getChildAt(2).setPadding(0,0,6,95);//这是控制缩放控件的位置
        mMapView.showScaleControl(false);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
//        mBaiduMap = mMapView.getMap();
//        // 初始化定位
//        mLocClient = new LocationClient(this);
//        mBaiduMap.setBuildingsEnabled(false); // 不显示楼快效果
//        mMapView.showZoomControls(true); //设置是否显示缩放控件
//        mMapView.getChildAt(2).setPadding(0,0,6,83);//这是控制缩放控件的位置
//        mMapView.showScaleControl(false);
//        zoomLevel = mBaiduMap.getMapStatus().zoom;
//
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        // 定位图层显示方式
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//                mCurrentMode, true, null));
//
//        /**
//         * 设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效 customMarker用户自定义定位图标
//         * enableDirection是否允许显示方向信息 locationMode定位图层显示方式
//         */
//        // 定位选项
//        LocationClientOption option = new LocationClientOption();
//        /**
//         * coorType - 取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系
//         * ：bd09ll
//         */
//        option.setCoorType("bd09ll");
//        // 设置是否需要地址信息，默认为无地址
//        option.setIsNeedAddress(true);
//        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
//        // 可以用作地址信息的补充
//        option.setIsNeedLocationDescribe(true);
//        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
//        option.setIsNeedLocationPoiList(true);
//        /**
//         * 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps)模式 Hight_Accuracy
//         * 高精度模式
//         */
//        // option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        // 设置是否打开gps进行定位
//        option.setOpenGps(true);
//        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效 500
//        option.setScanSpan(200);
//        // 设置 LocationClientOption
//        mLocClient.setLocOption(option);

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
//                if (isLocal) {
//                    AddMaker();
//                    AddLines();
//                }
            }
        });

        //标注点的点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                if (extraInfo==null){
                    return false;
                }
                String id = marker.getExtraInfo().getString("id");
                int type = marker.getExtraInfo().getInt("type", 0); // //1图片  1视屏
                TrackDetailModel.DataBean.PathinfosBean bean = marker.getExtraInfo().getParcelable("bean");
                if (type == 1) {
                    startActivity(new Intent(TrackActivity.this, SaveAfterActivity.class).putExtra("id", id).putExtra("data", bean).putExtra("type",2)); //图片详情
                } else if (type == 2) {
                    startActivity(new Intent(TrackActivity.this, SaveAfterVideoActivity.class).putExtra("id", id).putExtra("data", bean).putExtra("type",2)); //视频详情
                }
                return true;
            }
        });

        initData();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mBaiduMap.setMyLocationEnabled(true);
//        if (!mLocClient.isStarted()) {
//            mLocClient.start();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // 退出时停止定位
//        mLocClient.stop();
//        // 退出时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
//    }
    private void initData() {
        showDialog("加载中...");
        String url = Constants.URL_GETTRAROAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("travelRoadId", id);
        RemoteDataHandler.asyncTokenPost(url, this, true, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                TrackDetailModel model = gson.fromJson(json, TrackDetailModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    dismissDialog();
                    TrackDetailModel.DataBean bean = model.getData();
                    String coordinate = bean.getCoordinate();
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    if (!StringUtils.isStringNull(coordinate)){
                        String[] split = coordinate.split("-");
                        for (String s : split) {
                            String[] split1 = s.split("#");
                            String lng = split1[0];
                            String lat=split1[1];
                            LatLng point = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            builder.include(point);
                            latLngs.add(point);
                        }
                    }
                    if (isFirst) {
                        isFirst = false;
                        LatLngBounds bounds = builder.build();
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
                    }
                    lines = bean.getPathinfos();
                    isLocal=true;
                    AddMaker();
                    AddLines();
                }
            }
        });
    }

    private void AddMaker() {
        mBaiduMap.clear();
        for (int i = 0; i < lines.size(); i++) {
            TrackDetailModel.DataBean.PathinfosBean model = lines.get(i);
            String picUrl = model.getPicurl();
            View view = View.inflate(this, R.layout.record_makeritem, null);
            TextView txt = (TextView) view.findViewById(R.id.num_txt);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            int t = 0;
            if (!StringUtils.isStringNull(picUrl)) { //上传的是图片
                t = 1;
                img.setImageResource(R.mipmap.tupian);
            } else { //视屏
                t = 2;
                img.setImageResource(R.mipmap.shipin);

            }
            //定义Maker坐标点
            LatLng point = new LatLng(model.getPathlat(), model.getPathlng());
            Bundle extraInfo = new Bundle();
            extraInfo.putString("id", model.getPathinfoid());
            extraInfo.putInt("type",t);
            extraInfo.putParcelable("bean", model);
            int to = i + 1;
            txt.setText(to + "");
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap).extraInfo(extraInfo);
        //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }
    }


    /**
     * 在地图中添加折线和marker点
     */
    protected void AddLines(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < latLngs.size(); i++) {
            LatLng latLng = latLngs.get(i);
            LatLng point = new LatLng(latLng.latitude, latLng.longitude);
            builder.include(new LatLng(latLng.latitude, latLng.longitude));
            BitmapDescriptor bdA = null;
            if (i == 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.qidian);
                bdA = BitmapDescriptorFactory.fromBitmap(OverlayBitmapUtils
                        .createOverlayBitmap(this, bitmap, "起", 0, 0,
                                "#ffffff"));
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bdA);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
            } else if (i == latLngs.size() - 1) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.zhongdian);
                    bdA = BitmapDescriptorFactory.fromBitmap(OverlayBitmapUtils
                            .createOverlayBitmap(this, bitmap, "终", 0, 0,
                                    "#ffffff"));
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bdA);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
            }
            builder.include(new LatLng(latLng.latitude, latLng.longitude));
        }
        if (latLngs.size()>2) {
            //折线
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xAA00FF00).points(latLngs);
            mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        }
//        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLngs.get(latLngs.size() - 1));
//        // 移动到某经纬度
//        mBaiduMap.setMapStatus(update);
        LatLngBounds bounds = builder.build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
//        MapStatus mMapStatus = new MapStatus.Builder()
//                .target(latLngs.get(latLngs.size() - 1))
//                .zoom(18)
//                .build();
//        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //改变地图状态
//        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }



    @OnClick({R.id.return_lin, R.id.navigate_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.navigate_lin: //定位
                startLocation();
                break;
        }
    }

    private void startLocation() {
        new BDLocationUtils(this, bdLocation -> {
            if (bdLocation != null) {
                String city = bdLocation.getCity();
                double latitude = bdLocation.getLatitude();
                double longitude = bdLocation.getLongitude();
                if (!TextUtils.isEmpty(city)) {
                    city = city.replace("市", "").replace("省", "");
                }
                curAddress = bdLocation.getAddress().address;
                LatLng point = new LatLng(latitude, longitude);
                BitmapDescriptor bdA = null;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.sy_zhifeiji);
                bdA = BitmapDescriptorFactory.fromBitmap(bitmap);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bdA);
                //在地图上添加Marker，并显示
                if (local_overlay != null) {
                    local_overlay.remove();
                }
                //在地图上添加Marker，并显示
                local_overlay = mBaiduMap.addOverlay(option);
//                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                builder.include(point);
//                LatLngBounds bounds = builder.build();
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
            }
        });
    }

}
