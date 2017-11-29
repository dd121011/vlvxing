package com.vlvxing.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyProcessDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.MyDialog;
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.model.RecordMapModel;
import com.vlvxing.app.ui.SaveAfterActivity;
import com.vlvxing.app.ui.SaveAfterVideoActivity;
import com.vlvxing.app.ui.TrackDetailActivity;
import com.vlvxing.app.utils.BDLocationUtils;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/13 0013.
 * 热玩Fragment
 */

public class LvTuFragment extends Fragment implements BDLocationListener{
    Context mContext;
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.map)
    MapView mMapView;
    BaiduMap mBaiduMap;
    @Bind(R.id.year_list)
    ListView yearList;
    @Bind(R.id.return_lin)
    LinearLayout returnLin;
    @Bind(R.id.year_img1)
    ImageView yearImg1;
    @Bind(R.id.year_txt1)
    TextView yearTxt1;
    @Bind(R.id.year_rel1)
    RelativeLayout yearRel1;
    @Bind(R.id.year_lin1)
    LinearLayout yearLin1;
    @Bind(R.id.year_img2)
    ImageView yearImg2;
    @Bind(R.id.year_txt2)
    TextView yearTxt2;
    @Bind(R.id.year_rel2)
    RelativeLayout yearRel2;
    @Bind(R.id.year_lin2)
    LinearLayout yearLin2;
    @Bind(R.id.share_txt)
    TextView shareTxt;
    @Bind(R.id.lins)
    LinearLayout lins;
    @Bind(R.id.share_rel)
    RelativeLayout shareRel;
    @Bind(R.id.right_img)
    ImageView rightImg;

    private List<String> year_array;
    private int count = 0;
    private String year;
    float zoomLevel;
    private String share_title, share_url;
    private boolean isFirst = true;
    private Overlay maker_overlay;
    private Overlay local_overlay;
    private boolean isLocal = true;
    private List<Overlay> markerOverlays = new ArrayList<>();
    private String share_content;
    /**
     * 定位端
     */
    private LocationClient mLocClient;
    /**
     * 定位模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private MyProcessDialog dialog;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lvtu_fragment, container, false);
        ButterKnife.bind(this, view);
        headTitle.setText("旅途");
        rightImg.setVisibility(View.VISIBLE);
        returnLin.setVisibility(View.GONE);
        lins.setVisibility(View.GONE);
        //获取当前的日期，给定年份管理时间轴的年份
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR) + "";
        dialog = new MyProcessDialog(getActivity());
        dialog.setMsg("加载中...");
        initMap();
//        startLocation();
        bindYear();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocClient.isStarted()) {
            mLocClient.start();
        }
    }
    private void startLocation() {
        isLocal = false;
        new BDLocationUtils(mContext, bdLocation -> {
            if (bdLocation != null) {
                String city = bdLocation.getCity();
                double latitude = bdLocation.getLatitude();
                double longitude = bdLocation.getLongitude();
                if (!TextUtils.isEmpty(city)) {
                    city = city.replace("市", "").replace("省", "");
                }
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
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        isFirst=true;
        isLocal=true;
        initData();
    }

    private void bindYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int y = 0;
        year_array = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            y = year - i;
            year_array.add(y + "");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时停止定位
        mLocClient.stop();
        // 退出时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        if (bdLocation == null || mBaiduMap == null) {
            return;
        }
//        Toast.makeText(mContext, " 旅途onReceiveLocation", Toast.LENGTH_SHORT).show();
        String city = bdLocation.getCity();
        double latitude = bdLocation.getLatitude();
        double longitude = bdLocation.getLongitude();
        if (!TextUtils.isEmpty(city)) {
            city = city.replace("市", "").replace("省", "");
        }
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

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    //旅途中年份管理列表的设配器
    class myAdapter extends BaseAdapter {
        Context mContext;
        List<String> list;

        public myAdapter(Context mContext, List<String> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size() > 0 ? list.size() : 0;
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
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.year_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String years = list.get(position);
            holder.yearTxt.setText(years);
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    year = years;
                    yearTxt2.setText(years);
                    yearTxt1.setText(years);
                    yearLin1.setVisibility(View.VISIBLE);
                    yearRel1.setVisibility(View.VISIBLE);
                    yearImg1.setVisibility(View.GONE);
                    yearLin2.setVisibility(View.GONE);
                    initData();
                }
            });

            return convertView;
        }


        class ViewHolder {
            @Bind(R.id.year_txt)
            TextView yearTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    private void initMap() {
        mBaiduMap = mMapView.getMap();
        zoomLevel = mBaiduMap.getMapStatus().zoom;
        mMapView.setMapCustomEnable(false);
        mBaiduMap.setBuildingsEnabled(false); // 不显示楼快效果
        mMapView.showZoomControls(true); //设置是否显示缩放控件
        mMapView.getChildAt(2).setPadding(0, 0, 6, 83);//这是控制缩放控件的位置
        mMapView.showScaleControl(false);
        // 初始化定位
        mLocClient = new LocationClient(mContext);
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
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle info = marker.getExtraInfo();
                if (info == null) {
                    return false;
                }


                String id = marker.getExtraInfo().getString("id");
                int type = marker.getExtraInfo().getInt("type", 0); // //0图片  1视屏 2轨迹
                RecordMapModel.DataBean bean = null;
                if (type != 2) {
                    bean = marker.getExtraInfo().getParcelable("bean");
                }
                if (type == 0) {
                    startActivity(new Intent(mContext, SaveAfterActivity.class).putExtra("id", id).putExtra("data", bean)); //图片详情
                } else if (type == 1) {
                    startActivity(new Intent(mContext, SaveAfterVideoActivity.class).putExtra("id", id).putExtra("data", bean)); //视频详情
                } else {
                    startActivity(new Intent(mContext, TrackDetailActivity.class).putExtra("id", id)); //轨迹详情
                }
                return true;
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                initData();
            }
        });


    }

    private void initData() {
//        if (markerOverlays.size()>0) {
//            for (Overlay markerOverlay : markerOverlays) {
//                markerOverlay.remove();
//            }
//        }
        mBaiduMap.clear();
        Point targetScreen = mBaiduMap.getMapStatus().targetScreen;
        LatLngBounds bound = mBaiduMap.getMapStatus().bound;
        LatLng northeast = bound.northeast; // 右上角
        LatLng southwest = bound.southwest; // 左下角
        LatLng northwest = new LatLng(northeast.latitude,
                southwest.longitude); // 左上角
        LatLng southeast = new LatLng(southwest.latitude,
                northeast.longitude); // 右下角
        double lng1 = northwest.longitude; //手机左上角经度
        double lat1 = northwest.latitude;   //手机左上角纬度
        double lng2 = southeast.longitude; //手机右下角经度
        double lat2 = southeast.latitude; //手机右下角维度
        String url = Constants.URL_TRAVELROAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApp.getInstance().getUserTicket());
        params.put("year", year);
        params.put("lng1", lng1 + "");
        params.put("lat1", lat1 + "");
        params.put("lng2", lng2 + "");
        params.put("lat2", lat2 + "");
        RemoteDataHandler.asyncTokenPost(url, mContext, true, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
//                Log.i("aaa","json:"+json);
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                RecordMapModel model = gson.fromJson(json, RecordMapModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    String roadid = null, id = null;
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    List<RecordMapModel.ContentBean> travel = model.getContent(); //轨迹
                    List<RecordMapModel.DataBean> lines = model.getData(); //标注点
                    if (travel.size() > 0) {
                        for (int j = 0; j < travel.size(); j++) {
                            RecordMapModel.ContentBean tbean = travel.get(j);
                            roadid=tbean.getTravelroadid();
                            builder.include(new LatLng(tbean.getDestinationlat(), tbean.getDestinationlng()));
                            addMarkers(tbean.getDestinationlat(), tbean.getDestinationlng(), j, 2, "", roadid, null);
                        }
                    }
                    if (lines.size() > 0) {
                        for (int i = 0; i < lines.size(); i++) {
                            RecordMapModel.DataBean bean = lines.get(i);
                            id = bean.getPathinfoid(); //途中点id
                            builder.include(new LatLng(bean.getPathlat(), bean.getPathlng()));
                            addMarkers(bean.getPathlat(), bean.getPathlng(), travel.size()+i, Integer.parseInt(bean.getType()), id, "", bean);
                        }
                    }
                    if (isFirst) {
                        isFirst = false;
                        LatLngBounds bounds = builder.build();
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
                    }

                    //最后一条数据
//                    RecordMapModel.DataBean bean = lines.get(lines.size() - 1);
//                    if (!StringUtils.isStringNull(bean.getTravelroadid())) {
//                        roadid = bean.getTravelroadid(); //轨迹id
//                    } else {
//                        id = bean.getPathinfoid(); //途中点id
//                    }
//                    addMarkers(bean.getPathlat(), bean.getPathlng(), lines.size() - 1, bean.getType(), id, roadid, bean);
                }
            }
        });
    }

    private void addMarkers(double lat, double lng, int i, int t, String id, String roadid, RecordMapModel.DataBean bean) {

        View view = View.inflate(mContext, R.layout.record_makeritem, null);
        TextView txt = (TextView) view.findViewById(R.id.num_txt);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        int to = i + 1;
        txt.setText(to + "");
        String ids = null;
        if (!StringUtils.isStringNull(roadid)) { //轨迹
            img.setImageResource(R.mipmap.guiji);
            ids = roadid;
            t = 2;
        } else {
            ids = id;
            if (t == 0) { //0图片  1视屏 2轨迹
                img.setImageResource(R.mipmap.tupian);
            } else if (t == 1) {
                img.setImageResource(R.mipmap.shipin);
            }
        }
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lng);
        Bundle extraInfo = new Bundle();
        extraInfo.putString("id", ids);
        extraInfo.putInt("type", t);
        if (t != 2) {
            extraInfo.putParcelable("bean", bean);
        }
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap).extraInfo(extraInfo);
//        //在地图上添加Marker，并显示
        maker_overlay = mBaiduMap.addOverlay(option);
    }


    int counts = 0;

    @OnClick({R.id.right_img, R.id.add_img, R.id.less_img, R.id.year_lin1, R.id.navigate_lin, R.id.delete_txt, R.id.share_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_img:  //分享
                if (counts == 0) {
                    shareRel.setVisibility(View.VISIBLE);
                    counts++;
                } else {
                    shareRel.setVisibility(View.GONE);
                    counts = 0;
                }
                break;
            case R.id.add_img:
                if (zoomLevel <= 18) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                } else {
                    Toast.makeText(getActivity(), "已经放至最大！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.less_img:
                if (zoomLevel > 4) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                } else {
                    Toast.makeText(getActivity(), "已经缩至最小！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.year_lin1:
                if (count == 0) {
                    yearImg2.setVisibility(View.VISIBLE);
                    yearRel2.setVisibility(View.GONE);
                } else {
                    yearImg2.setVisibility(View.GONE);
                    yearRel2.setVisibility(View.VISIBLE);
                }
                yearLin2.setVisibility(View.VISIBLE);
                yearLin1.setVisibility(View.GONE);
                myAdapter adapter = new myAdapter(mContext, year_array);
                yearList.setAdapter(adapter);
                count = 1;
                break;
            case R.id.navigate_lin: //定位当前位置
                isLocal = true;
//                startLocation();
                break;
            case R.id.delete_txt:
                shareRel.setVisibility(View.GONE);
                MyDialog dialog = new MyDialog(mContext, "确定删除吗？");
                dialog.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickDelete();
                        dialog.dismissDialog();
                    }
                });
                break;
            case R.id.share_txt:
                shareRel.setVisibility(View.GONE);
                Point targetScreen = mBaiduMap.getMapStatus().targetScreen;
                LatLngBounds bound = mBaiduMap.getMapStatus().bound;
                LatLng northeast = bound.northeast; // 右上角
                LatLng southwest = bound.southwest; // 左下角
                LatLng northwest = new LatLng(northeast.latitude,
                        southwest.longitude); // 左上角
                LatLng southeast = new LatLng(southwest.latitude,
                        northeast.longitude); // 右下角
                double lng1 = northwest.longitude; //手机左上角经度
                double lat1 = northwest.latitude;   //手机左上角纬度
                double lng2 = southeast.longitude; //手机右下角经度
                double lat2 = southeast.latitude; //手机右下角维度
                //http://handongkeji.com:8090/lvyoushejiao/shareurl/auth/travelRoadShare.json?token=kbuS1ABR8Q4ikISw3P2r2z_uDdjJ4aCLnaxYRemkdoVK_IjkiDXra6RfeerIep5_2uM8d7elPvWlql4KGusc-Q&year=2017&lng1=116.48232325518084&lat1=39.913853364218234&lng2=116.4839761373192&lat239.91205473402165
                share_url = Constants.URL_ROADSHARE + "?token=" + MyApp.getInstance().getUserTicket() + "&year=" + year + "&lng1=" + lng1 + "&lat1=" + lat1 + "&lng2=" + lng2 + "&lat2=" + lat2;
                share_title = "V旅行";
                share_content = "看世界，V旅行";
                ShareDialog sdialog = new ShareDialog(mContext);
                sdialog.setmOnclickListener(new ShareDialog.ClickSureListener() {
                    @Override
                    public void onClick(int type) { //type 1QQ 2微博  3微信  4朋友圈
                        if (type == 1) {
                            umShare(SHARE_MEDIA.QQ);
                        } else if (type == 2) {
                            umShare(SHARE_MEDIA.SINA);
                        } else if (type == 3) {
                            umShare(SHARE_MEDIA.WEIXIN);
                        } else {
                            umShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        sdialog.dismissDialog();
                    }
                });
                break;

        }
    }

    /**
     * 友盟分享
     */
    private void umShare(SHARE_MEDIA share_media) {
        ShareAction shareAction = new ShareAction(getActivity());
        UMWeb  web = new UMWeb(share_url);
        web.setTitle(share_title);//标题
        web.setThumb(new UMImage(getActivity(), R.mipmap.logos));
        web.setDescription("V旅行");//描述
        shareAction.setPlatform(share_media).withMedia(web).withText(share_content).setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
//            dialog.show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
//            dialog.dismiss();
//            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                Toast.makeText(mContext, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(mContext, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            dialog.dismiss();
//            Toast.makeText(mContext, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            dialog.dismiss();
//            Toast.makeText(mContext, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }

    private void clickDelete() {
        dialog = new MyProcessDialog(mContext);
        dialog.show();
        dialog.setMsg("删除中...");
        String url = Constants.URL_DELETEPATHINFOS;
        HashMap<String, String> params = new HashMap<>();
        Point targetScreen = mBaiduMap.getMapStatus().targetScreen;
        LatLngBounds bound = mBaiduMap.getMapStatus().bound;
        LatLng northeast = bound.northeast; // 右上角
        LatLng southwest = bound.southwest; // 左下角
        LatLng northwest = new LatLng(northeast.latitude,
                southwest.longitude); // 左上角
        LatLng southeast = new LatLng(southwest.latitude,
                northeast.longitude); // 右下角
        double lng1 = northwest.longitude; //手机左上角经度
        double lat1 = northwest.latitude;   //手机左上角纬度
        double lng2 = southeast.longitude; //手机右下角经度
        double lat2 = southeast.latitude; //手机右下角维度
        params.put("token", MyApp.getInstance().getUserTicket());
        params.put("year", year);
        params.put("lng1", lng1 + "");
        params.put("lat1", lat1 + "");
        params.put("lng2", lng2 + "");
        params.put("lat2", lat2 + "");
        RemoteDataHandler.asyncTokenPost(url, mContext, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                JSONObject obj = new JSONObject(json);
                String status = obj.getString("status");
                String message = obj.getString("message");
                if (status.equals("1")) {
                    ToastUtils.show(mContext, "删除成功!");
                    dialog.dismiss();
                    initData();
                } else {
                    ToastUtils.show(mContext, message);
                }
            }
        });
    }

    static class ViewHolder {
        @Bind(R.id.year_txt)
        TextView yearTxt;
        @Bind(R.id.item_lin)
        LinearLayout itemLin;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
