package com.vlvxing.app.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Address;
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
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.handongkeji.common.SystemHelper;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.BitmapUtils;
import com.handongkeji.utils.CommonUtils;
import com.handongkeji.utils.ProviderUtils;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.CallDialog;
import com.handongkeji.widget.MyProcessDialog;
import com.lidong.photopicker.Image;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vlvxing.app.R;
import com.vlvxing.app.album.util.DrivingRouteOverlay;
import com.vlvxing.app.album.util.OverlayBitmapUtils;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.ItineraryObject;
import com.vlvxing.app.common.JourneyBase;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.MyDialog;
import com.vlvxing.app.common.OverlayManager;
import com.vlvxing.app.common.PoiOverlay;
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.common.WalkingRouteOverlay;
import com.vlvxing.app.dialog.TakePhotoDialog;
import com.vlvxing.app.model.RecordMapModel;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.ui.AddImgMakerActivity;
import com.vlvxing.app.ui.AddVideoMakerActivity;
import com.vlvxing.app.ui.FindScienceActivity;
import com.vlvxing.app.ui.MyOverlayManager;
import com.vlvxing.app.ui.SaveAfterActivity;
import com.vlvxing.app.ui.SaveAfterVideoActivity;
import com.vlvxing.app.ui.SaveTrackActivity;
import com.vlvxing.app.ui.SelectCityActivity;
import com.vlvxing.app.ui.SettingActivity;
import com.vlvxing.app.ui.ShopMainActivity;
import com.vlvxing.app.ui.TrackDetailActivity;
import com.vlvxing.app.ui.UserHeadimgSetDialogActivity2;
import com.vlvxing.app.utils.CacheData;
import com.vlvxing.app.utils.DistanceUtils;
import com.vlvxing.app.utils.FileUtil;
import com.vlvxing.app.utils.ImageUtil;
import com.vlvxing.app.utils.ToastUtils;
import com.vlvxing.app.widget.ImagePickerHelper;
import com.vlvxing.app.widget.PickerLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Administrator on 2017/4/13 0013.
 * 记录Fragment
 */

public class JiLuFragment extends Fragment implements BDLocationListener, OnGetPoiSearchResultListener, OnGetSuggestionResultListener, OnGetRoutePlanResultListener {

    //年份管理轨迹记录
    @Bind(R.id.year_lin1)
    LinearLayout yearLin1;
    @Bind(R.id.year_img1)
    ImageView yearImg1;
    @Bind(R.id.year_rel1)
    RelativeLayout yearRel1;
    @Bind(R.id.year_txt1)
    TextView yearTxt1;
    //分享相关绑定操作
    @Bind(R.id.share_lin)
    LinearLayout shareLin;

    private String share_title, share_url;
    private String share_content;

    @Bind(R.id.year_lin2)
    LinearLayout yearLin2;
    @Bind(R.id.year_img2)
    ImageView yearImg2;
    @Bind(R.id.year_rel2)
    RelativeLayout yearRel2;
    @Bind(R.id.year_txt2)
    TextView yearTxt2;

    @Bind(R.id.year_list)
    ListView yearList;
    private List<String> year_array;
    private boolean isFirst = true;
    private Overlay maker_overlay;
    private int counts = 0;
    private String year;

    public static final int RESULT_CAPTURE = 100;//选择相机
    public static final int REQUEST_CODE4 = 4; //拍摄视频
    //用于设置个性化地图的样式文件
    // 提供三种样式模板："custom_config_blue.txt"，"custom_config_dark.txt"，"custom_config_midnightblue.txt"
    private static String PATH = "custom_config_dark.txt";
    Context mContext;
    @Bind(R.id.select_lin)
    LinearLayout select_lin;
    @Bind(R.id.select_txt)
    TextView selectTxt;
    @Bind(R.id.select_edt)
    EditText selectEdt;
    @Bind(R.id.exit_txt)
    TextView exitTxt;
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.navigate_lin)
    LinearLayout navigateLin;
    BaiduMap mBaiduMap;
    @Bind(R.id.return_lin)
    LinearLayout returnLin;
    @Bind(R.id.map_rel)
    RelativeLayout mapRel;
    @Bind(R.id.result_list)
    ListView resultList;
    @Bind(R.id.play_img)
    ImageView playImg;
    @Bind(R.id.go_destination)
    RelativeLayout destination;
    @Bind(R.id.tmc_lin)
    LinearLayout tmcLin;//实时路况 外层lin
    @Bind(R.id.tmc_check)
    CheckBox tmcCheck;//实时路况控制
    int radius = 300;
    float zoomLevel;
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RouteLine route = null;
    MassTransitRouteLine massroute = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    WalkingRouteResult nowResultwalk = null;
    // 搜索相关
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    int nowSearchType = -1; // 当前进行的检索，供判断浏览节点时结果使用。
    int count = 0, num = 0, nums = 0, lnum = 0;
    /**
     * 定位模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode;
    /**
     * 定位端
     */
    private LocationClient mLocClient;
    /**
     * 是否是第一次定位
     */
    private boolean isFirstLoc = true;
    /**
     * 定位坐标
     */
    private LatLng locationLatLng;
    private UiSettings mUiSetting; // 百度地图的UI设置、可以控制指南针、俯视角等控制
    //    private ArrayList<LatLng> itinerarys = new ArrayList<LatLng>();
//    ArrayList<ItineraryObject> object = new ArrayList<>();
    private double myLat, myLng;
    private String adress;
    private String mCurrentCity;
    private boolean isRecord = false, isLocal = true;
    private Bitmap bitmap;
    private String addrStr;
    private int loadIndex = 0;
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private List<SuggestionResult.SuggestionInfo> suggest;
    private ArrayAdapter<String> sugAdapter = null;
    private boolean isshowMaker = true;
    private boolean isOver = false, isWc = false;
    private TextView popupText = null; // 泡泡view
    private boolean isSelect = true;
    private PoiOverlay overlay;
    private Overlay local_overlay;
    private List<Overlay> markerOverlays = new ArrayList<>();
    private List<Overlay> recordOverlays = new ArrayList<>();
    private Overlay dingwei_overlay;
    private boolean isPhoto = false;
    private LatLng lng = null;  //目的地经纬度

    private MyProcessDialog umDialog;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jilu_fragment, container, false);
        ButterKnife.bind(this, view);
        setMapCustomFile(mContext, PATH);
        selectTxt.setVisibility(View.GONE);
        selectEdt.setVisibility(View.VISIBLE);
        selectEdt.setHint("搜索地区");
        returnLin.setVisibility(View.GONE);
        exitTxt.setVisibility(View.VISIBLE);
        exitTxt.setText("搜索");
        exitTxt.setTextColor(getResources().getColor(R.color.color_ea5413));
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PickerLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        mBaiduMap = mMapView.getMap();
        // 初始化定位
        mLocClient = new LocationClient(mContext);
        initMap();
        //获取当前的日期
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR) + "";
        bindYear();
        tmcSetting();//实时路况
        umDialog = new MyProcessDialog(getActivity());
        umDialog.setMsg("加载中...");
        return view;
    }
    private void tmcSetting(){
        //实时路况
        tmcCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mBaiduMap.setTrafficEnabled(true);//开启实时路况
                }else{
                    mBaiduMap.setTrafficEnabled(false);//关闭实时路况
                }
            }
        });
    }
    private void initData() {
//        if (markerOverlays.size()>0) {
//            for (Overlay markerOverlay : markerOverlays) {
//                markerOverlay.remove();
//            }
//        }
        lng = null;
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
                            addHistoryMarkers(tbean.getDestinationlat(), tbean.getDestinationlng(), j, 2, "", roadid, null);
                        }
                    }
                    if (lines.size() > 0) {
                        for (int i = 0; i < lines.size(); i++) {
                            RecordMapModel.DataBean bean = lines.get(i);
                            id = bean.getPathinfoid(); //途中点id
                            builder.include(new LatLng(bean.getPathlat(), bean.getPathlng()));
                            addHistoryMarkers(bean.getPathlat(), bean.getPathlng(), travel.size()+i, Integer.parseInt(bean.getType()), id, "", bean);
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
    //年份管理中的添加覆盖物的方法
    private void addHistoryMarkers(double lat, double lng, int i, int t, String id, String roadid, RecordMapModel.DataBean bean) {

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

    //绑定年月
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
            JiLuFragment.myAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.year_item, parent, false);
                holder = new JiLuFragment.myAdapter.ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (JiLuFragment.myAdapter.ViewHolder) convertView.getTag();
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
        zoomLevel = mBaiduMap.getMapStatus().zoom;
        mUiSetting = mBaiduMap.getUiSettings();
        mUiSetting.setOverlookingGesturesEnabled(false); // 禁止俯视角手势
        mUiSetting.setCompassEnabled(false); // 禁止指南针
        mUiSetting.setRotateGesturesEnabled(false); // 禁止旋转手势

//        MapView.setMapCustomEnable(false); //设置不显示个性化
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        /**
         * 隐藏缩放属性
         */
        mBaiduMap.setBuildingsEnabled(false); // 不显示楼快效果
        mMapView.showZoomControls(true); //设置是否显示缩放控件
        mMapView.getChildAt(2).setPadding(0, 0, 6, 83);//这是控制缩放控件的位置
        mMapView.showScaleControl(false);
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
        option.setScanSpan(1000);
        // 设置 LocationClientOption
        mLocClient.setLocOption(option);
        EventBus.getDefault().register(this);

        //标注点的点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                List<RecordModel> list = CacheData.getRecentSubList("addmaker");
//                if (list.size() > 0) {
                Bundle info = marker.getExtraInfo();
                if (info == null) {
                    return false;
                }
                if (lng !=null)
                {
                    return false;
                }
                String id = marker.getExtraInfo().getString("id");
                int type = marker.getExtraInfo().getInt("type", 0); // //0图片  1视屏 2轨迹
                System.out.println("test 视频功能  记录碎片 marker单击事件 id:"+id);
                System.out.println("test 视频功能  记录碎片 marker单击事件 type:"+type);
                RecordMapModel.DataBean bean = null;
                if (type != 2) {
                    bean = marker.getExtraInfo().getParcelable("bean");
                }
                if (type == 0) {
                    startActivity(new Intent(mContext, SaveAfterActivity.class).putExtra("id", id).putExtra("data", bean)); //图片详情
                } else if (type == 1) {
                    System.out.println("test 视频功能  记录碎片 marker单击事件 type=1,跳转至-SaveAfterVideoActivity"+type);
                    startActivity(new Intent(mContext, SaveAfterVideoActivity.class).putExtra("id", id).putExtra("data", bean)); //视频详情

                } else if(type == 2){
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
//                initData();
//                getRecordPoint();
//                if (isWc){
//                    LatLng center = new LatLng(myLat, myLng);
//                    //周边搜索
//                    PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword("厕所").sortType(PoiSortType.distance_from_near_to_far).location(center)
//                            .radius(radius).pageNum(loadIndex);
//                    mPoiSearch.searchNearby(nearbySearchOption);
//
//                }
            }
        });

    }

    private void getRecordPoint() {
//        mBaiduMap.clear();
        List<RecordModel> list = CacheData.getRecentSubList("addmaker");
        if (list.size() <= 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            RecordModel model = list.get(i);
            String picUrl = model.getPicUrl();
            int t = 0;
            if (!StringUtils.isStringNull(picUrl)) { //上传的是图片
                t = 1;
            } else { //视屏
                t = 2;
            }
            addMarkers(model.getLat(), model.getLng(), i, t);
        }
    }

    private void addMarkers(double lat, double lng, int i, int t) {
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lng);
        Bundle extraInfo = new Bundle();
        extraInfo.putString("id", i + "");
        extraInfo.putInt("type", t);
        View view = View.inflate(mContext, R.layout.record_makeritem, null);
        TextView txt = (TextView) view.findViewById(R.id.num_txt);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        int to = i + 1;
        txt.setText(to + "");
        if (t == 1) { //1 图片  2视屏
            img.setImageResource(R.mipmap.tupian);
        } else {
            img.setImageResource(R.mipmap.shipin);
        }
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
        //构建MarkerOption，用于在地图上添加Marker
        MarkerOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap).extraInfo(extraInfo);
//        //在地图上添加Marker，并显示
        markerOverlays.add(mBaiduMap.addOverlay(option));
//        if (!isshowMaker) { //移除标注
////            option.visible(true);
//            overlay.remove();;
//        }
    }

    /**
     * 在地图中添加折线和marker点
     */
    protected void AddorDeleteMarkerInMap() {
        lng = null;
        mBaiduMap.clear();
        isLocal = true;
        List<LatLng> latLngs = new ArrayList<LatLng>();
        List<RecordModel> list = CacheData.getRecentSubList("track");
        if (list.size() <= 0) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < list.size(); i++) {
            RecordModel model = list.get(i);
            LatLng point = new LatLng(model.getLat(), model.getLng());
            BitmapDescriptor bdA = null;
            if (i == 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.qidian);
                bdA = BitmapDescriptorFactory.fromBitmap(OverlayBitmapUtils
                        .createOverlayBitmap(mContext, bitmap, "起", 0, 0,
                                "#ffffff"));
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bdA);
                //在地图上添加Marker，并显示
                Overlay overlay = mBaiduMap.addOverlay(option);
            } else if (i == list.size() - 1) {
                if (MyApp.getInstance().isCity_id_flag()) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.zhongdian);
                    bdA = BitmapDescriptorFactory.fromBitmap(OverlayBitmapUtils
                            .createOverlayBitmap(mContext, bitmap, "终", 0, 0,
                                    "#ffffff"));
                } else {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.dingwei);
                    bdA = BitmapDescriptorFactory.fromBitmap(bitmap);
                }
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bdA);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
            }
            latLngs.add(point);
            builder.include(new LatLng(model.getLat(), model.getLng()));
        }

        if (list.size() >= 2) {
            //折线
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xAA00FF00).points(latLngs);
            mBaiduMap.addOverlay(ooPolyline);
        }
        LatLngBounds bounds = builder.build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
    }

    private void addMarkInMap(RecordModel model) {
        List<RecordModel> list = CacheData.getRecentSubList("track");
        BitmapDescriptor bdA = null;
        LatLng point = new LatLng(model.getLat(), model.getLng());
        if (list.size() == 1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.qidian);
            bdA = BitmapDescriptorFactory.fromBitmap(OverlayBitmapUtils
                    .createOverlayBitmap(mContext, bitmap, "起", 0, 0,
                            "#ffffff"));
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bdA);
            //在地图上添加Marker，并显示
            recordOverlays.add(mBaiduMap.addOverlay(option));

        } else {
            if (MyApp.getInstance().isCity_id_flag()) {
//                isRecord = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.zhongdian);
                bdA = BitmapDescriptorFactory.fromBitmap(OverlayBitmapUtils
                        .createOverlayBitmap(mContext, bitmap, "终", 0, 0,
                                "#ffffff"));
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bdA);
                //在地图上添加Marker，并显示
                if (dingwei_overlay != null) {
                    dingwei_overlay.remove();
                }
                recordOverlays.add(mBaiduMap.addOverlay(option));
                Log.d("aaa", "addMarkInMap: 绘制终点");

            } else {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.dingwei);
                bdA = BitmapDescriptorFactory.fromBitmap(bitmap);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bdA);
                //在地图上添加Marker，并显示
                if (dingwei_overlay != null) {
                    dingwei_overlay.remove();
                }
                dingwei_overlay = mBaiduMap.addOverlay(option);
                recordOverlays.add(dingwei_overlay);
            }

        }

        List<LatLng> latLngs = new ArrayList<LatLng>();
        if (list.size() > 1) {
            RecordModel recordModel = list.get(list.size() - 2);
            LatLng point0 = new LatLng(recordModel.getLat(), recordModel.getLng());
            latLngs.add(point0);
            latLngs.add(point);
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xAA00FF00).points(latLngs);
            recordOverlays.add(mBaiduMap.addOverlay(ooPolyline));
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (RecordModel recordModel : list) {
            builder.include(new LatLng(recordModel.getLat(), recordModel.getLng()));
        }
        LatLngBounds bounds = builder.build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
    }

    @OnClick({R.id.exit_txt, R.id.add_img, R.id.less_img, R.id.conceal_lin, R.id.wc_lin, R.id.navigate_lin, R.id.play_lin, R.id.camera_lin, R.id.over_lin,R.id.year_lin1,R.id.share_lin,R.id.go_destination})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.year_lin1:
                destination.setVisibility(View.GONE);
                if (counts == 0) {
                    yearImg2.setVisibility(View.VISIBLE);
                    yearRel2.setVisibility(View.GONE);
                } else {
                    yearImg2.setVisibility(View.GONE);
                    yearRel2.setVisibility(View.VISIBLE);
                }
                yearLin2.setVisibility(View.VISIBLE);
                yearLin1.setVisibility(View.GONE);
                JiLuFragment.myAdapter adapter = new JiLuFragment.myAdapter(mContext, year_array);
                yearList.setAdapter(adapter);
                counts = 1;
                break;
            case R.id.go_destination://前往目的地路线
                destination.setVisibility(View.GONE);
                if (lng!=null) {
                    mBaiduMap.clear();
                    PlanNode stNode = PlanNode.withLocation(new LatLng(myLat, myLng)); //当前位置的经纬度
                    PlanNode enNode = PlanNode.withLocation(lng);  //目的地经纬度
                    mSearch.walkingSearch((new WalkingRoutePlanOption())
                            .from(stNode).to(enNode));
                }
                break;
            case R.id.exit_txt: //路程规则
                if (!MyApp.getInstance().isCity_id_flag()) {
                    ToastUtils.show(mContext, "录制中不能查看路程规则!");
                    return;
                }
                String end = selectEdt.getText().toString().trim();
                if (StringUtils.isStringNull(end)) {
                    ToastUtils.show(mContext, "请输入目的地！");
                    return;
                }
                lng = null;//先置空目标经纬度
                mapRel.setVisibility(View.GONE);
                resultList.setVisibility(View.VISIBLE);
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(end).city(selectEdt.getText().toString()));

                break;
            case R.id.add_img: //放大
                if (zoomLevel <= 18) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                } else {
                    Toast.makeText(getActivity(), "已经放至最大！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.less_img: //缩小
                if (zoomLevel > 4) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                } else {
                    Toast.makeText(getActivity(), "已经缩至最小！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.conceal_lin: //影藏和显示标注物
                if (count == 0) {
                    isshowMaker = false;
                    for (Overlay markerOverlay : markerOverlays) {
                        markerOverlay.remove();
                    }
//                    getRecordPoint();
                    count++;
                } else {
                    isshowMaker = true;
                    getRecordPoint();
                    count = 0;
                }
                break;
            case R.id.wc_lin:
                if (nums == 0) {
                    isWc = true;
                    LatLng center = new LatLng(myLat, myLng);
                    //周边搜索
                    PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword("厕所").sortType(PoiSortType.distance_from_near_to_far).location(center)
                            .radius(radius).pageNum(loadIndex);
                    mPoiSearch.searchNearby(nearbySearchOption);
                    nums++;
                } else {
                    isWc = false;
                    nums = 0;
                    if (overlay != null) {
                        overlay.removeFromMap();
                    }
                }
                break;
            case R.id.navigate_lin: //定位当前位置
                lng = null;
                mBaiduMap.clear();
                destination.setVisibility(View.GONE);
                isLocal = true;
//                if (isLocal) {
//                    isLocal=false;
//                    if (local_overlay==null) {
//                        return;
//                    }
//                    local_overlay.remove();
//                }else {
//                    isLocal=true;
//                }
                break;
            case R.id.play_lin: //开始录制 打开定位
                destination.setVisibility(View.GONE);
                isLocal = true;
                mBaiduMap.clear();
                lng = null;

                //从onResume()函数中copy过来的
                int isrecord = MyApp.getInstance().getIsrecord();
                if (isrecord == 0)
                    AddorDeleteMarkerInMap();


                if (isRecord) {
                    ToastUtils.show(mContext, "已经开始录制了!");
                    return;
                }
//                for (Overlay recordOverlay : recordOverlays) {
//                    recordOverlay.remove();
//                }
                isRecord = true;  //  标记已经开始录制
                MyApp.getInstance().setCity_id_flag(false);  //  开始录制了，将状态保存到sp中，持久化保存
                lng = null;
                mBaiduMap.clear();
                isLocal = true;
                CacheData.getFileNameById("1"); //删除该轨迹本地缓存的数据
                isLocal = true;
//                MyApp.getInstance().setCity_id_flag(false);
                playImg.setImageResource(R.mipmap.zanting);
                break;
            case R.id.camera_lin:
                destination.setVisibility(View.GONE);
                isPhoto = true;
                if (!isRecord) {
                    lng = null;
                    mBaiduMap.clear();
//                    // 开启定位图层
//                    mBaiduMap.setMyLocationEnabled(true);
//                    // 定位图层显示方式
//                    mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//                            mCurrentMode, true, null));
//                    new MapStatus.Builder().target(new LatLng(MyApp.getInstance().get))
//                    mBaiduMap.setMapStatus();
                    CacheData.getFileNameById("1"); //删除该轨迹本地缓存的数据
                }
                TakePhotoDialog tpdialog = new TakePhotoDialog(mContext, 1);
                tpdialog.setmOnclickListener(new TakePhotoDialog.ClickSureListener() {
                    @Override
                    public void onClick(int t) {
                        if (t == 1) { //拍照
                            tpdialog.dismissDialog();
                            Intent intent = new Intent(mContext, ImageGridActivity.class);
                            startActivityForResult(intent, RESULT_CAPTURE);
//                            ImagePickerHelper.selectSinglePhoto((Activity) mContext,RESULT_CAPTURE,false);
                        } else if (t == 2) { //拍视频用的系统的
                            if (Build.VERSION.SDK_INT >= 23) {
                                String[] mPermissionList = new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WAKE_LOCK,
                                        Manifest.permission.ACCESS_NETWORK_STATE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                };
                                ActivityCompat.requestPermissions((Activity) mContext, mPermissionList, 123);
                            }
                            tpdialog.dismissDialog();
                            Intent intent = new Intent();
                            intent.setAction("android.media.action.VIDEO_CAPTURE");
                            intent.addCategory("android.intent.category.DEFAULT");
                            startActivityForResult(intent, REQUEST_CODE4);
//                            Intent intent = new Intent(mContext, UserHeadimgSetDialogActivity2.class).putExtra("type",1);
//                            startActivityForResult(intent, 1);
                        }
                    }
                });
                break;
            case R.id.over_lin: //录制结束
                destination.setVisibility(View.GONE);
                if (MyApp.getInstance().isCity_id_flag()) {
                    ToastUtils.show(mContext, "还没有开始录制，不能保存!");
                    return;
                }

                final MyDialog dialog = new MyDialog(mContext, "确认结束运动?");
                dialog.setCancelText("取消");
                dialog.setOkText("确认");
                dialog.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(mContext, SaveTrackActivity.class));
                        playImg.setImageResource(R.mipmap.play);
                        isOver = true;
//                        MyApp.getInstance().setCity_id_flag(true);
//                        AddorDeleteMarkerInMap();
                        dialog.dismissDialog();
                    }
                });
                break;
            case R.id.share_lin:
                destination.setVisibility(View.GONE);
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


    // 设置个性化地图config文件路径
    private void setMapCustomFile(Context context, String PATH) {
        FileOutputStream out = null;
        InputStream inputStream = null;
        String moduleName = null;
        try {
            inputStream = context.getAssets()
                    .open("customConfigdir/" + PATH);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            moduleName = context.getFilesDir().getAbsolutePath();
            File f = new File(moduleName + "/" + PATH);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new FileOutputStream(f);
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MapView.setCustomMapStylePath(moduleName + "/" + PATH);

    }



    /**
     * Mainactivity中有onActivityResult方法，所以Fragment中的onActivityResult方法不走
     */
    public void intentAddIng(String path) {
        Intent intent = new Intent(mContext, AddImgMakerActivity.class).putExtra("path", path).putExtra("lat", myLat)
                .putExtra("lng", myLng).putExtra("address", addrStr).putExtra("isRecord", isRecord);
        startActivity(intent);
    }

    public void intentAddVideo(String path) {
        Intent intent = new Intent(mContext, AddVideoMakerActivity.class).putExtra("lat", myLat)
                .putExtra("lng", myLng).putExtra("address", addrStr).putExtra("isRecord", isRecord);
        intent.putExtra("relesaeWhat", "dajiakan");
        intent.putExtra("videoPath", path); //视频路径
        startActivity(intent);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

//        iv_addMarker.setClickable(false);
        // 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置

        if (bdLocation == null || mBaiduMap == null) {
            return;
        }
//        Toast.makeText(mContext, " 记录onReceiveLocation", Toast.LENGTH_SHORT).show();
        myLat = bdLocation.getLatitude();
        myLng = bdLocation.getLongitude();
        addrStr = bdLocation.getAddrStr();
        mCurrentCity = bdLocation.getCity();
        if (isLocal) { //定位当前位置
            isLocal = false;
            LatLng point = new LatLng(myLat, myLng);
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
            local_overlay = mBaiduMap.addOverlay(option);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(point);
            LatLngBounds bounds = builder.build();
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
        }

        if (isRecord) { //是否点击开始录制的按钮!MyApp.getInstance().isCity_id_flag()
            if (isOver) {
                isOver = false;
                isRecord = false;
                MyApp.getInstance().setCity_id_flag(true);
                Log.d("aaa", "onReceiveLocation: 结束");
            }
            List<RecordModel> list = CacheData.getRecentSubList("track");
            if (list.size() > 0) { //判断走的路程超过2米没有
                RecordModel modle = list.get(list.size() - 1);
                double pathlat = modle.getLat();
                double pathlng = modle.getLng();
                LatLng p2 = new LatLng(myLat, myLng); //
                LatLng p1 = new LatLng(pathlat, pathlng);
                //计算p1、p2两点之间的直线距离，单位：米
                double distance = DistanceUtil.getDistance(p1, p2);
                if (distance > 2) {
                    RecordModel model = new RecordModel(myLng, myLat, "", "", "", "", "", addrStr);
                    List<RecordModel> temp = new ArrayList<>();
                    temp.add(model);
                    CacheData.saveRecentSubList(temp, "track");
                    addMarkInMap(model);
                }
            } else {
                RecordModel model = new RecordModel(myLng, myLat, "", "", "", "", "", addrStr);
                List<RecordModel> temp = new ArrayList<>();
                temp.add(model);
                CacheData.saveRecentSubList(temp, "track");
                addMarkInMap(model);
            }

            if (isPhoto) {
                isPhoto = false;
                RecordModel model = new RecordModel(myLng, myLat, "", "", "", "", "", addrStr);
                List<RecordModel> temp = new ArrayList<>();
                temp.add(model);
                CacheData.saveRecentSubList(temp, "track");
            }
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

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
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        isRecord = !MyApp.getInstance().isCity_id_flag();   //  取出上次保存的录制状态  isRecord 表示开始录制，所以对状态取反
        int isrecord = MyApp.getInstance().getIsrecord();
//        if (isrecord == 0)
//            AddorDeleteMarkerInMap();
//        getRecordPoint();
//        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
        isLocal = true;
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
        // 退出时停止定位
        mLocClient.stop();
        // 退出时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
        mMapView = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(mContext, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//            mBaiduMap.clear();
            overlay = new MyPoiOverlay(mBaiduMap);
            List<OverlayOptions> options = overlay.getOverlayOptions();
//            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(mContext, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(mContext, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /**
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param res
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        suggest = new ArrayList<SuggestionResult.SuggestionInfo>();
        if (res.getAllSuggestions() != null) {
            suggest.addAll(res.getAllSuggestions());
            resultAdapter adapter = new resultAdapter(mContext, suggest);
            resultList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    //步行
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("检索地址有歧义，请重新设置。\n可通过getSuggestAddrInfo()接口获得建议查询信息");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
//            mBtnPre.setVisibility(View.VISIBLE);
//            mBtnNext.setVisibility(View.VISIBLE);

            if (result.getRouteLines().size() > 1) {
                nowResultwalk = result;
            } else if (result.getRouteLines().size() == 1) {
                // 直接显示
                route = result.getRouteLines().get(0);
                WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
//                mBaiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
//                mMapView.refresh();// 刷新地图

            } else {
                Log.d("route result", "结果数<0");
                return;
            }

        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    class resultAdapter extends BaseAdapter {
        Context mContext;
        List<SuggestionResult.SuggestionInfo> list;

        public resultAdapter(Context mContext, List<SuggestionResult.SuggestionInfo> list) {
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
                        R.layout.city_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SuggestionResult.SuggestionInfo s = list.get(position);
            holder.pinyinTxt.setVisibility(View.GONE);
            holder.cityTxt.setText(s.key);
            holder.itemLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (routeOverlay != null) {
                        routeOverlay.removeFromMap();
                    }
//                        selectEdt.setText("");
//                        selectEdt.setHint("搜索地区");
                    mapRel.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                    selectEdt.setText(s.key);
                    selectEdt.setSelection(s.key.length());

//
//                        // 路线规则传地址有时候能显示有时不能显示，所以最好传经纬度
//                        PlanNode stNode = PlanNode.withCityNameAndPlaceName(mCurrentCity, addrStr);
//                        PlanNode enNode = PlanNode.withCityNameAndPlaceName(s.city, s.key);

                    //目标经纬度，用来展示用户查询之后的结果。
                    lng = new LatLng(s.pt.latitude, s.pt.longitude);
                    moveToLocation(lng);
                    //前往目的地路线按钮显示
                    destination.setVisibility(View.VISIBLE);
                    //设置弹出按钮 类似高德地图 路线

                    //设置起终点、途经点信息，对于tranist search 来说，城市名无意义
//                    PlanNode stNode = PlanNode.withLocation(new LatLng(myLat, myLng)); //当前位置的经纬度
//                    PlanNode enNode = PlanNode.withLocation(new LatLng(s.pt.latitude, s.pt.longitude));  //目的地经纬度
//                    mSearch.walkingSearch((new WalkingRoutePlanOption())
//                            .from(stNode).to(enNode));
                }
            });
            return convertView;
        }
        //屏幕中心点移动至目的地
        private void moveToLocation(LatLng lng){
            if (lng!=null) {
                //构建Marker图标
//                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.null);
//                //构建MarkerOption，用于在地图上添加Marker
//                OverlayOptions option = new MarkerOptions()
//                        .position(lng)
//                        .icon(bitmap);
//                //在地图上添加Marker，并显示
//                mBaiduMap.addOverlay(option);
                //定义地图状态
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(lng)
                        .zoom(zoomLevel)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdate);
            }
        }


        class ViewHolder {
            @Bind(R.id.city_txt)
            TextView cityTxt;
            @Bind(R.id.pinyin_txt)
            TextView pinyinTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);

            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
//            return  false;
        }
    }

    /**
     * 设置起点终点图标
     */
    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.qidian);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.zhongdian);
            }
            return null;
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
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调，可以用来处理等待框，或相关的文字提示
//            umDialog.show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
//            umDialog.dismiss();
            Log.d("plat", "platform" + platform);
//            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                Toast.makeText(mContext, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(mContext, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            umDialog.dismiss();
//            Toast.makeText(mContext, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            umDialog.dismiss();
//            Toast.makeText(mContext, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
//        if (data == null) {
//            return;
//        }
//        if (data.getData() == null) {
//            return;
//        }
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
//        if(umDialog.isShowing()){
//            umDialog.dismiss();
//        }
    }


}
