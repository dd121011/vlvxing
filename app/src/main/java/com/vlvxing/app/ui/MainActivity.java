package com.vlvxing.app.ui;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.handongkeji.autoupdata.CheckVersion;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.Bimp;
import com.handongkeji.utils.BitmapUtils;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.NoScrollViewPager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.vlvxing.app.R;
import com.vlvxing.app.common.LocationPermissionDialog;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.fragment.ForumFragment;
import com.vlvxing.app.fragment.JiLuFragment;
import com.vlvxing.app.fragment.LvTuFragment;
import com.vlvxing.app.fragment.MainFragment;
import com.vlvxing.app.fragment.WoDeFragment;
import com.vlvxing.app.utils.AppShortCutUtil;
import com.vlvxing.app.utils.BDLocationUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.SettingService;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 主界面
 */

public class MainActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    NoScrollViewPager viewpager;
    @Bind(R.id.sy_img)
    ImageView syImg;
    @Bind(R.id.shouye_lin)
    LinearLayout shouyeLin;
    @Bind(R.id.fx_img)
    ImageView fxImg;
    @Bind(R.id.faxian_lin)
    LinearLayout faxianLin;
    @Bind(R.id.rw_img)
    ImageView rwImg;
    @Bind(R.id.rewan_lin)
    LinearLayout rewanLin;
    @Bind(R.id.wd_img)
    ImageView wdImg;
    @Bind(R.id.wode_lin)
    LinearLayout wodeLin;
    @Bind(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @Bind(R.id.fragmentContainerFrame)
    FrameLayout fragmentContainerFrame;
    private LinearLayout currentTab;
    private List<Fragment> fragment_list;
    private boolean isToWode = false;
    public static final int REQUEST_CODE_LOCATION = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);


        myApp.setIs_active(true);//当前app处于活跃状态,用于友盟消息推送,区分app是否存在于前台或者后台
        init();

        //提交表单权限，批量注册权限
        requestLocationPermission();

        //检查更新
        CheckVersion.update(this, true);
        //请求第三方分享登陆的权限
//        requestUMSharePermission();
    }

    /**
     * 请求第三方分享登陆的权限
     */
//    private void requestUMSharePermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.CALL_PHONE,
//                    Manifest.permission.READ_LOGS,
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.SET_DEBUG_APP,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW,
//                    Manifest.permission.GET_ACCOUNTS,
//                    Manifest.permission.WRITE_APN_SETTINGS};
//            ActivityCompat.requestPermissions(this, mPermissionList, 123);
//        }
//    }

    /**
     * 请求定位权限
     */
    private void requestLocationPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE



//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.READ_LOGS,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.SET_DEBUG_APP,
//                Manifest.permission.SYSTEM_ALERT_WINDOW,
//                Manifest.permission.GET_ACCOUNTS,
//                Manifest.permission.WRITE_APN_SETTINGS
        };
        //如果没有注册权限
        if (!AndPermission.hasPermission(this, permissions)) {
            AndPermission.with(this).permission(permissions).requestCode(REQUEST_CODE_LOCATION).send();
        } else {
            //如果注册过权限，直接触发定位
            startLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> grantPermissions) {
                startLocation();
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                try {
                    Class<?> aClass = Class.forName("com.yanzhenjie.permission.SettingExecutor");
                    Constructor<?> constructor = aClass.getDeclaredConstructor(Object.class, int.class);
                    constructor.setAccessible(true);
                    Object o = constructor.newInstance(MainActivity.this, 203);
                    new LocationPermissionDialog(MainActivity.this, (SettingService) o)
                            .setCancelable(false)
                            .setTitle("定位失败")
//                            .setMessage("我们需要获得您当前的位置，以便能向您提供周边的餐厅信息，去开启权限，应用详情-权限-位置信息")
                            .setPositiveButton("去开启定位")
                            .show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (resultCode == 1004) { //  65636 拍照  131172  选择本地图片
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem imageItem = images.get(0);
                String path = imageItem.path;
//                    File file1 = new File(path);
//                    path = file1.getAbsolutePath();
//                    com.handongkeji.utils.ImageItem item = new com.handongkeji.utils.ImageItem();
//                    item.setBitmap(tmpbitmap);
//                    item.setImagePath(path);
//                System.out.println("test 上传图片 主页面 getOrientation:"+item.getOrientation());
//                System.out.println("test 上传图片 主页面 path:"+path);
                ((JiLuFragment) fragment_list.get(1)).intentAddIng(path);
            }
        }

        if (resultCode == -1) { //获取拍摄视频的路径
            Uri data1 = data.getData();
            Cursor c = getContentResolver().query(data1, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
            if (c != null && c.moveToFirst()) {
                String path = c.getString(0);
                System.out.println("test 视频功能 主页面 path:"+path);
                ((JiLuFragment) fragment_list.get(1)).intentAddVideo(path);
            }
        }

        if (requestCode == 203) {
            requestLocationPermission();
        }
    }

    /**
     * 定位
     */
    private void startLocation() {
        new BDLocationUtils(this, bdLocation -> {
            if (bdLocation != null) {
                String city = bdLocation.getCity();
                double latitude = bdLocation.getLatitude();
                double longitude = bdLocation.getLongitude();
                if (!TextUtils.isEmpty(city)) {
                    city = city.replace("市", "").replace("省", "");
                }
                myApp.setLat(latitude + "");
                myApp.setLng(longitude + "");
                myApp.setCity_name(city);
                //发送消息
                EventBus.getDefault().post(0, "getCityBymainfrm");
            }
        });
    }


    /**
     * 初始化控件
     */
    private void init() {
        fragment_list = new ArrayList<>();
        fragment_list.add(new MainFragment());
        fragment_list.add(new JiLuFragment());
        fragment_list.add(new LvTuFragment());
//        fragment_list.add(new ForumFragment());
        fragment_list.add(new WoDeFragment());

        viewpager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        //设置viewpager预加载fragment的集合长度的个数
        viewpager.setOffscreenPageLimit(fragment_list.size());
        isCheckpager();
    }

    /**
     * 设置viewoager选中第一个fragment
     */
    private void isCheckpager() {
//        if (StringUtils.isStringNull(MyApp.getInstance().getUserTicket())) {
//            currentTab = wodeLin;
//            currentTab.setSelected(true);
//            viewpager.setCurrentItem(3);
//        } else {
        currentTab = shouyeLin;
        currentTab.setSelected(true);
        viewpager.setCurrentItem(0);
//        }
    }

    /**
     * launchMode为singleTask的时候，通过Intent启到一个Activity,如果系统已经存在一个实例，
     * 系统就会将请求发送到这个实例上，但这个时候，系统就不会再调用通常情况下我们处理请求数据的onCreate方法，
     * 而是调用onNewIntent方法
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intExtra = intent.getIntExtra("type", 0);
        if (intExtra == 1) {
            changeTab(wodeLin, 3); // 退出登录后跳到首页--我的
        } else if (intExtra == 3) {
//            changeTab(activity_lin, 2); // 我的--我参加的活动没数据，去活动首页逛逛
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String token = myApp.getUserTicket();
        if (isToWode && StringUtils.isStringNull(token)) {
            changeTab(wodeLin, 3);
            isToWode = false;
        }
        if(myApp.isSendUrl()){
            //分享后的线路 点击分享的链接跳转到app内部
            boolean lineDetails = getIntent().getBooleanExtra("lineDetails", false);
            if (lineDetails) {
                String id = getIntent().getStringExtra("productId");//线路id
                System.out.println("获得数据 首页   id:"+id);
                startActivity(new Intent(MainActivity.this, LineDetailsActivity.class).putExtra("id", id));
            }
            myApp.setSendUrl(false);
        }
        //如果该用户有未读消息,则每次进入app后所有的未读消息清零
        AppShortCutUtil.deleteShortCut(MainActivity.this,MainActivity.class);

    }

    /**
     * 传入布局对象、fragment下标，实现切换
     *
     * @param tab
     * @param index
     */
    private void changeTab(LinearLayout tab, int index) {
        currentTab.setSelected(false);
        currentTab = tab;
        currentTab.setSelected(true);
        viewpager.setCurrentItem(index);
    }

    @OnClick({R.id.shouye_lin, R.id.faxian_lin, R.id.rewan_lin, R.id.wode_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shouye_lin:
                changeTab(shouyeLin, 0);
                break;
            case R.id.faxian_lin: //记录
                //判断是否登录
                boolean flag = MyApp.getInstance().isLogin(this);
                if (!flag) {
                    return;
                }
                changeTab(faxianLin, 1);
                break;
            case R.id.rewan_lin:  //旅途
                //判断是否登录
                flag = MyApp.getInstance().isLogin(this);
                if (!flag) {
                    return;
                }
                changeTab(rewanLin, 2);
                break;
            case R.id.wode_lin:
                isToWode = true;
                changeTab(wodeLin, 3);
                break;
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragment_list.get(arg0);
        }

        @Override
        public int getCount() {
            return fragment_list.size();
        }
    }


    private long count = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long detla = System.currentTimeMillis() - count;
            if (detla > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                count = System.currentTimeMillis();
                return true;
            } else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
