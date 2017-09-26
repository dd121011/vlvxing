package com.vlvxing.app.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.dialog.CommonDialog;
import com.vlvxing.app.model.DetailModel;
import com.vlvxing.app.model.NearModel;
import com.vlvxing.app.utils.BDLocationUtils;
import com.vlvxing.app.utils.ToastUtils;
import com.yanzhenjie.permission.SettingService;

import org.json.JSONException;

import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/8 0008.
 * 发现景点
 */

public class FindScienceActivity extends BaseActivity implements BDLocationListener {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.map)
    MapView map;
    BaiduMap mBaiduMap;
    private int type;//0 首页发现景点  1农家乐发现景点
    DetailModel.DataBean bean;
    private Overlay local_overlay;
    float zoomLevel;
    private Overlay maker_overlay;
    private boolean isLocal=true;
    private boolean isFirst=true;
    /**
     * 定位端
     */
    private LocationClient mLocClient;
    /**
     * 定位模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findscience_layout);
        ButterKnife.bind(this);
        headTitle.setText("发现景点");
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        if (type == 1) {
            bean = intent.getParcelableExtra("bean");
        }
        mBaiduMap = map.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        zoomLevel = mBaiduMap.getMapStatus().zoom;
        map.setMapCustomEnable(false);
        mBaiduMap.setBuildingsEnabled(false); // 不显示楼快效果
        map.showZoomControls(true); //设置是否显示缩放控件
        map.getChildAt(2).setPadding(0,0,6,95);//这是控制缩放控件的位置
        map.showScaleControl(false);
        // 初始化定位
        mLocClient = new LocationClient(this);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位图层显示方式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));

        /**
         * 设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效 customMarker用户自定义定位图标
         * enableDirection是否允许显示方向信息 locationMode定位图层显示方式
         */
        // 注册定位监听
        mLocClient.registerLocationListener(this);
        // 定位选项
        LocationClientOption option = new LocationClientOption();
        /**
         * coorType - 取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系
         * ：bd09ll
         */
        option.setCoorType("bd09ll");
        // 设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
        // 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);
        /**
         * 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps)模式 Hight_Accuracy
         * 高精度模式
         */
        // option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置是否打开gps进行定位
        option.setOpenGps(true);
        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效 500
        option.setScanSpan(2000);
        // 设置 LocationClientOption
        mLocClient.setLocOption(option);
        //当地图载入成功后回调此方法
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (type == 0) {
                    initData();
                } else {
                    mBaiduMap.clear();
                    LatLng point = new LatLng(bean.getPathlat(), bean.getPathlng());
                    BitmapDescriptor bdA = null;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.location_red);
                    bdA = BitmapDescriptorFactory.fromBitmap(bitmap);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bdA);
                    //在地图上添加Marker，并显示
                    Overlay overlay = mBaiduMap.addOverlay(option);
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(bean.getPathlat(), bean.getPathlng()));
                    LatLngBounds bounds = builder.build();
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
                    LinearLayout layout = new LinearLayout(FindScienceActivity.this);
                    layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setGravity(Gravity.CENTER);
                    View view = LayoutInflater.from(FindScienceActivity.this).inflate(
                            R.layout.info_layout, layout, true);
                    //第三个参数表示要显示的View和设置的坐标(position)之间的y轴偏移
                    MyInfo info = new MyInfo(view, new LatLng(bean.getPathlat(), bean.getPathlng()), -40);
                    mBaiduMap.showInfoWindow(info);
                }
            }
        });


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle info = marker.getExtraInfo();
                if (info == null) {
                    return false;
                }
                String id = marker.getExtraInfo().getString("id");
                startActivity(new Intent(FindScienceActivity.this, ShopMainActivity.class).putExtra("id", id));
                return true;
            }
        });

//        startLocation();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocClient.isStarted()) {
            mLocClient.start();
        }
    }

    @Override
    protected void onPause() {
        map.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        map.onResume();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时停止定位
        mLocClient.stop();
        // 退出时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
    }

    @OnClick({R.id.return_lin, R.id.navigate_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.navigate_lin:
                isLocal=true;
//                startLocation();
                break;
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation != null) {
            String city = bdLocation.getCity();
            curLat = bdLocation.getLatitude();
            curLng = bdLocation.getLongitude();
            if (!TextUtils.isEmpty(city)) {
                city = city.replace("市", "").replace("省", "");
            }
            curAddress = bdLocation.getAddress().address;
            LatLng point = new LatLng(curLat, curLng);
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
            if (isLocal) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(point);
                LatLngBounds bounds = builder.build();
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
            }
            isLocal=false;
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    /**
     * 自定义InfoWindow
     */
    class MyInfo extends InfoWindow {
        public MyInfo(View view, LatLng latLng, int i) {
            super(view, latLng, i);
            ViewHolder viewHolder = null;
            if (view.getTag() == null) {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.titleTxt.setText(bean.getAddress());
            double distance = Double.parseDouble(bean.getDistance()) * 0.001; //米转换成千米
            //四舍五入，保留两位小数
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            String d = nf.format(distance);
            viewHolder.distanceTxt.setText("距您" + d + "公里");
            String a = bean.getAddress();
            String lat = bean.getPathlat() + "";
            String lng = bean.getPathlng() + "";
            viewHolder.goHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(FindScienceActivity.this, BaiDuMap.class);
//                    if (!TextUtils.isEmpty(a) && !"null".equals(a)) {
//                        intent.putExtra("address", a);
//                    }
//
//                    if (!TextUtils.isEmpty(lat) && !"null".equals(lat)) {
//                        intent.putExtra("weidu", bean.getPathlat());
//                    }
//
//                    if (!TextUtils.isEmpty(lng) && !"null".equals(lng)) {
//                        intent.putExtra("jingdu", bean.getPathlng());
//                    }
//
//                    startActivity(intent);
                    Intent mIntent;
                    if (isInstall("com.baidu.BaiduMap")) {
                        //移动APP调起Android百度地图方式举例
                        try {
//                            mIntent = Intent.getIntent("intent://map/direction?origin=" + curAddress + "&destination=" + "大望路地铁站" + "&mode=walking&origin_region=" + myApp.getCity_name() + "&destination_region=" + bean.getAddress() + "&src=com.vlvxing.app#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//                            mIntent = Intent.getIntent("intent://map/direction?origin=latlng:"+curLat+","+curLng+"|name:我家&destination=大雁塔&mode=driving&region=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                            mIntent = Intent.getIntent("baidumap://map/walknavi?origin=" + curLat + "," + curLng + "&destination=" + bean.getPathlat() + "," + bean.getPathlng());
                            startActivity(mIntent); //启动调用
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } else {

                        new CommonDialog(FindScienceActivity.this, new SettingService() {
                            @Override
                            public void execute() {

                            }

                            @Override
                            public void cancel() {

                            }
                        }).setMessage("您尚未安装百度地图").show();

//                        //百度地图网页版跳转  http://api.map.baidu.com/direction?origin=&destination=北京大望路22&mode=driving&origin_region=北京&destination_region=北京&output=html&src=com.vlvxing.app
//                        mIntent = new Intent(FindScienceActivity.this, BrowseActivity.class);
////				mIntent.putExtra("url", "http://api.map.baidu.com/line?region=北京&name=518&output=html&src=yourCompanyName|yourAppName");
////                        mIntent.putExtra("url", "http://api.map.baidu.com/direction?origin=" + curAddress + "&destination=" + bean.getAddress() + "&mode=walking&origin_region=" + myApp.getCity_name() + "&destination_region=" + bean.getAddress() + "&output=html&src=com.vlvxing.app");
//                        try {
//                            mIntent = Intent.getIntent("baidumap://map/walknavi?origin="+curLat+","+curLng+"&destination="+bean.getPathlat()+","+bean.getPathlng());
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }
//                        startActivity(mIntent);
                    }


//                    Intent i1 = new Intent();
//                    // 步行导航
////String url="baidumap://map/walknavi?origin=40.057406655722,116.2964407172&destination=39.91441,116.40405";  baidumap://map/walknavi?origin=39.91284,116.483008&destination=39.913365,116.485951
//                    String url="baidumap://map/walknavi?origin="+myApp.getLat()+","+myApp.getLng()+"&destination="+lat+","+lng;
//                    i1.setData(Uri.parse(url));
//                    startActivity(i1);
                }
            });
        }

        public MyInfo(BitmapDescriptor bitmapDescriptor, LatLng latLng, int i, OnInfoWindowClickListener onInfoWindowClickListener) {
            super(bitmapDescriptor, latLng, i, onInfoWindowClickListener);
        }

        class ViewHolder {
            @Bind(R.id.title_txt)
            TextView titleTxt;
            @Bind(R.id.distance_txt)
            TextView distanceTxt;
            @Bind(R.id.go_here)
            LinearLayout goHere;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    private void initData() {
//        mBaiduMap.clear();
        showDialog("加载中...");
//        Point targetScreen = mBaiduMap.getMapStatus().targetScreen;
//        LatLngBounds bound = mBaiduMap.getMapStatus().bound;
//        LatLng northeast = bound.northeast; // 右上角
//        LatLng southwest = bound.southwest; // 左下角
//        LatLng northwest = new LatLng(northeast.latitude,
//                southwest.longitude); // 左上角
//        LatLng southeast = new LatLng(southwest.latitude,
//                northeast.longitude); // 右下角
//        double lng1 = northwest.longitude; //手机左上角经度
//        double lat1 = northwest.latitude;   //手机左上角纬度
//        double lng2 = southeast.longitude; //手机右下角经度
//        double lat2 = southeast.latitude; //手机右下角维度
        String url = Constants.URL_NEARBUS;
        HashMap<String, String> params = new HashMap<>();
        params.put("areaId", myApp.getAreaid());
        params.put("PathLat", myApp.getLat());
        params.put("PathLng", myApp.getLng());
//        params.put("lng1", lng1 + "");
//        params.put("lat1", lat1 + "");
//        params.put("lng2", lng2 + "");
//        params.put("lat2", lat2 + "");

        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                NearModel model = gson.fromJson(json, NearModel.class);
                String status = model.getStatus();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                if ("1".equals(status)) {
                    List<NearModel.DataBean> lines = model.getData();
                    for (int i = 0; i < lines.size(); i++) {
                        NearModel.DataBean bean = lines.get(i);
                        String pathlat = bean.getPathlat();
                        String pathlng = bean.getPathlng();
                        builder.include(new LatLng(Double.parseDouble(pathlat), Double.parseDouble(pathlng)));
                        addMarkers(Double.parseDouble(pathlat), Double.parseDouble(pathlng), bean.getBusinessid(), bean.getBusinessname());
                    }

                    if (isFirst) {
                        isFirst = false;
//                        LatLngBounds bounds = builder.build();
//                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
                    }
                } else {
                    ToastUtils.show(FindScienceActivity.this, model.getMessage());
                }
                dismissDialog();
            }
        });
    }

    private void addMarkers(double lat, double lng, String id, String name) {
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lng);
        Bundle extraInfo = new Bundle();
        extraInfo.putString("id", id);
        View view = View.inflate(this, R.layout.mapmaker_showtxt, null);
        TextView txt = (TextView) view.findViewById(R.id.name_txt);
        txt.setText(name);
        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.location_red);
        BitmapDescriptor  bdA = BitmapDescriptorFactory.fromBitmap(bitmap);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bdA).extraInfo(extraInfo);
//        //在地图上添加Marker，并显示
         maker_overlay = mBaiduMap.addOverlay(option);
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(new LatLng(lat,lng));
//        LatLngBounds bounds = builder.build();
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
    }

    private Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();

        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
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
                if (isLocal) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(point);
                    LatLngBounds bounds = builder.build();
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
                }
                isLocal=false;
            }
        });
    }

    private String curAddress;
    private double curLat;
    private double curLng;

    private boolean isInstall(String packgeName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packgeName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }

        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
