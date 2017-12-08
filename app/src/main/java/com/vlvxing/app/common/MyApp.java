/**
 * ClassName:MyApp.java
 * PackageName:com.shopnc_local_life.android.common
 * Create On 2013-8-6下午4:52:02
 * Site:http://weibo.com/hjgang or http://t.qq.com/hjgang_
 * EMAIL:hjgang@bizpower.com or hjgang@yahoo.cn
 * Copyrights 2013-2-18 hjgang All rights reserved.
 */
package com.vlvxing.app.common;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.handongkeji.common.SystemHelper;
import com.handongkeji.ui.BrowseActivity;
import com.handongkeji.widget.CallDialog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.vlvxing.app.R;
import com.vlvxing.app.location.service.LocationService;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.ui.LineDetailsActivity;
import com.vlvxing.app.ui.LoginActivity;
import com.vlvxing.app.ui.MainActivity;
import com.vlvxing.app.ui.PlaneTicketActivity;
import com.vlvxing.app.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class MyApp extends Application {
    /**
     * 系统初始化配置文件操作器
     */
    private SharedPreferences sysInitSharedPreferences;
    /**
     * 记录默认城市名称
     */
    private String city_id;
    private String city_name;
    private boolean city_id_flag;
    private String phone;
    private String member_key;
    private String first_start_flag;
    private static boolean is_active = false;//app是否处于活跃状态,用于友盟消息推送,区分app是否存在于前台或者后台

    private static int screenWidth;

    private static int screenHeight;

    private String Photo_path;

    private TabHost tabHost;
    private RadioButton btn_test2;
    private AccountManager am;
    private Account[] accounts;
    private String Ticket = "ticket";
    private String lat, lng;
    private int milege;
    private String Userid = "userid";
    private String areaid;
    // private String Communityid="communityid";
    private String Mobile = "mobile";
    private String UserName = "username";

    public static List<RecordModel> rlist;
    public static Context applicationContext;
    private static MyApp instance;
    // login user name
    public final String PREF_USERNAME = "username";
    public String message;
    public String messaget;
    //	public Typeface typeface;
//	public List<EmojiBean> iterator;
    public boolean RESULT_FROM_VIEW_PHOTOS = false;

    private String Thirdid = "thirdid";                                                    //第三方的鹰眼Id
    private String UserNick = "userNick";                                                //支付的方式
    private String ThirdId = "thirdid";
    private String AuccontName = "com.vlvxing.app";
    public LocationService locationService;
    public Vibrator mVibrator;

//	public boolean isrecord() {
//		boolean isrecord = SharedPrefsUtil.getValue(applicationContext, "isrecord", false);
//		return isrecord;
//	}
//
//	public void setIsrecord(boolean isrecord) {
//		this.isrecord = isrecord;
//		SharedPrefsUtil.putValue(applicationContext,"isrecord",isrecord);
//	}

    public int getIsrecord() {
        int isrecord = SharedPrefsUtil.getValue(applicationContext, "isrecord", 0);
        return isrecord;
    }

    public void setIsrecord(int isrecord) {
        this.isrecord = isrecord;
        SharedPrefsUtil.putValue(applicationContext, "isrecord", isrecord);
    }

    private int isrecord;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";

    // public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分包
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initYoumeng();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxd3cb391989fe67f1", "226c687e3cf3f69ed0ee44a54cc1aef8");
        PlatformConfig.setSinaWeibo("557320894", "61d277edc31ad735fce0e1f93502f8f8", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106153781", "53AkGv7rAsG5OMli");
        Constants.init(this);

        sysInitSharedPreferences = getSharedPreferences(Constants.SYSTEM_INIT_FILE_NAME, MODE_PRIVATE);
        am = AccountManager.get(this);
        DisplayMetrics dm = SystemHelper.getScreenInfo(this);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        applicationContext = this;
        instance = this;

//		LocalImageHelper.init(this);
        /**
         * 异步加载图片
         */
//		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");
        File cacheDir = new File(Constants.CACHE_DIR, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(applicationContext).memoryCacheExtraOptions(480, 800) // max
                // width,
                // max
                // height，即保存的每个缓存文件的最大长宽
                .discCacheExtraOptions(480, 800, null) // Can slow ImageLoader,
                // use it carefully
                // (Better don't use
                // it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(10)// 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You
                // can
                // pass
                // your
                // own
                // memory
                // cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024).discCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5
                // 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100) // 缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).imageDownloader(new BaseImageDownloader(applicationContext, 5 * 1000, 30 * 1000)) // connectTimeout
                // s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
//		iterator = EmojiGetter.readXML(this);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);// 全局初始化此配置
    }


    public static MyApp getInstance() {
        return instance;
    }


    public static int getScreenWidth() {
        return screenWidth;
    }


    public void setUserLogin(String mobile, String pass, String ticket, String userid) {
        if (accounts != null) {
            if (accounts.length == 0) {
                Account ac = new Account(mobile, getString(R.string.account_type));
                Bundle userdata = new Bundle();
                userdata.putString(Ticket, ticket);
                userdata.putString(Userid, userid);
                userdata.putString(Mobile, mobile); //
                userdata.putString(UserName, pass);
                am.addAccountExplicitly(ac, pass, userdata);
            } else {
                am.setUserData(accounts[0], Ticket, ticket);
            }
        }
    }

    public void setUserLogin(String mobile, String pass, String ticket, String userid, String thirdid, String userNick) {
        if (accounts != null) {
            if (accounts.length == 0) {
                Account ac = new Account(mobile, AuccontName);
                Bundle userdata = new Bundle();
                userdata.putString(Ticket, ticket);
                userdata.putString(Userid, userid);
                userdata.putString(Mobile, mobile);
                userdata.putString(UserName, pass);
                userdata.putString(Thirdid, thirdid);
                userdata.putString(UserNick, userNick);
                userdata.putString(ThirdId, thirdid);
                am.addAccountExplicitly(ac, pass, userdata);
            } else {
                am.setUserData(accounts[0], Ticket, ticket);
            }
        }
    }

    public String getLng() {
        return sysInitSharedPreferences.getString("lng", "");
    }

    public void setLng(String lng) {
        this.lng = lng;
        sysInitSharedPreferences.edit().putString("lng", this.lng).commit();
    }

    public String getLat() {
        return sysInitSharedPreferences.getString("lat", "");
    }

    public void setLat(String lat) {
        this.lat = lat;
        sysInitSharedPreferences.edit().putString("lat", this.lat).commit();
    }

    public String getAreaid() {
        return sysInitSharedPreferences.getString("areaid", "");
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
        sysInitSharedPreferences.edit().putString("areaid", this.areaid).commit();
    }

    public String getMessage() {
        return sysInitSharedPreferences.getString("message", "");
    }

    public void setMessage(String message) {
        this.message = message;
        sysInitSharedPreferences.edit().putString("message", this.message).commit();
    }

    public String getMessageT() {
        return sysInitSharedPreferences.getString("messaget", "");
    }

    public void setMessageT(String messaget) {
        this.messaget = messaget;
        sysInitSharedPreferences.edit().putString("messaget", this.messaget).commit();
    }

    // public void setName(String name) {
    // if (accounts.length == 0) {
    // Account ac = new Account(name, getString(R.string.account_type));
    // Bundle userdata1 = new Bundle();
    // userdata1.putString(Name, name);
    // am.addAccountExplicitly(ac, null, userdata1);
    // } else {
    // am.setUserData(accounts[0], Name, name);
    // }
    // }
    //


    // 获得手机号
    public String getUserMobile() {
        String mob = "";
        accounts = am.getAccountsByType(getString(R.string.account_type));
        if (accounts.length > 0) {
            mob = am.getUserData(accounts[0], Mobile);
            if (mob == null) {
                mob = "";
            }
        }
        return mob;
    }

    public String getUserTicket() {
        String ret = "";
        accounts = am.getAccountsByType(getString(R.string.account_type));
        if (accounts.length > 0) {
            ret = am.getUserData(accounts[0], Ticket);
            if (ret == null) {
                ret = "";
            }
        }
        return ret;
    }

    public String getUserId() {
        String ret = "";
        accounts = am.getAccountsByType(getString(R.string.account_type));
        if (accounts.length > 0) {
            ret = am.getUserData(accounts[0], Userid);
            if (ret == null) {
                ret = "";
            }
        }
        return ret;
    }

    public String getUserName() {
        String ret = "";
        accounts = am.getAccountsByType(getString(R.string.account_type));
        if (accounts.length > 0) {
            ret = am.getUserData(accounts[0], UserName);
            if (ret == null) {
                ret = "";
            }
        }
        return ret;
    }


    public void logout() {
        accounts = am.getAccountsByType(getString(R.string.account_type));
        if (accounts.length > 0) {
            am.removeAccount(accounts[0], null, null);
//			new AddaliasTask(getUserId() + "sxl", "sxl_type");
        }
    }

    /**
     * 判断用户是否登录，未登录跳到登录界面
     */
    public boolean isLogin(final Context activity) {
        String token = getUserTicket();
        if (TextUtils.isEmpty(token)) {
            CallDialog islogin = new CallDialog((Activity) activity, "你尚未登录，是否登录？");
            islogin.setNegativeButtonListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    islogin.dismissDialog();
                }
            });
            return false;
        }
        return true;
    }
//	class AddaliasTask extends AsyncTask<Void, Void, Boolean> {
//
//		String alias;
//		String alias_type;
//
//		public AddaliasTask(String alias, String alias_type) {
//			super();
//			this.alias = alias;
//			this.alias_type = alias_type;
//		}
//
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			boolean result = false;
//			try {
//				result = mPushAgent.removeAlias(alias, alias_type);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			super.onPostExecute(result);
//		}
//
//	}

    public String getFirst_start_flag() {
        return first_start_flag;
    }

    public void setFirst_start_flag(String first_start_flag) {
        this.first_start_flag = first_start_flag;
        sysInitSharedPreferences.edit().putString("first_start_flag", this.first_start_flag).commit();
    }

    public RadioButton getBtn_test2() {
        return btn_test2;
    }

    public void setBtn_test2(RadioButton btn_test2) {
        this.btn_test2 = btn_test2;
    }

    public TabHost getTabHost() {
        return tabHost;
    }

    public void setTabHost(TabHost tabHost) {
        this.tabHost = tabHost;
    }

//	 public void setSearch_dao(SearchDao search_dao) {
//	 this.search_dao = search_dao;
//	 }


    public void setScreenWidth(int screenWidth) {
        MyApp.screenWidth = screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        MyApp.screenHeight = screenHeight;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getPhoto_path() {
        return Photo_path;
    }

    public void setPhoto_path(String photo_path) {
        Photo_path = photo_path;
        sysInitSharedPreferences.edit().putString("Photo_path", this.Photo_path).commit();
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
        sysInitSharedPreferences.edit().putString("city_id", this.city_id).commit();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        sysInitSharedPreferences.edit().putString("phone", this.phone).commit();
    }

    public String getMember_key() {
        return member_key;
    }

    public void setMember_key(String member_key) {
        this.member_key = member_key;
        sysInitSharedPreferences.edit().putString("member_key", this.member_key).commit();
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
        sysInitSharedPreferences.edit().putString("city_name", this.city_name).commit();
    }

    public boolean isCity_id_flag() {
        boolean city_id_flag = sysInitSharedPreferences.getBoolean("city_id_flag", true);  //  默认为true，表示 未开始录制
        return city_id_flag;
    }

    public void setCity_id_flag(boolean city_id_flag) {
        this.city_id_flag = city_id_flag;
        sysInitSharedPreferences.edit().putBoolean("city_id_flag", this.city_id_flag).commit();
    }

    /**
     * 用于区分是乘客还是车主的相关界面
     */
    //  0 表示乘客    ,1表示车主
    public int getStatus() {
        int status_of_is_driver = sysInitSharedPreferences.getInt("status_of_is_driver", 0);
        return status_of_is_driver;
    }

    //  0 表示乘客    ,1表示车主
    public void setStatus(int status) {
        sysInitSharedPreferences.edit().putInt("status_of_is_driver", status).commit();
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    /**
     * 获取系统初始化文件操作器
     *
     * @return
     */
    public SharedPreferences getSysInitSharedPreferences() {
        return sysInitSharedPreferences;
    }

    // 创建SD卡缓存目录
    private void createCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File f = new File(Constants.CACHE_DIR);
            if (f.exists()) {
                System.out.println("SD卡缓存目录:已存在!");
            } else {
                if (f.mkdirs()) {
                    System.out.println("SD卡缓存目录:" + f.getAbsolutePath() + "已创建!");
                } else {
                    System.out.println("SD卡缓存目录:创建失败!");
                }
            }
            File ff = new File(Constants.CACHE_IMAGE);
            if (ff.exists()) {
                System.out.println("SD卡照片缓存目录:已存在!");
            } else {
                if (ff.mkdirs()) {
                    System.out.println("SD卡照片缓存目录:" + ff.getAbsolutePath() + "已创建!");
                } else {
                    System.out.println("SD卡照片缓存目录:创建失败!");
                }
            }
            File fff = new File(Constants.CACHE_DIR_IMAGE);
            if (fff.exists()) {
                System.out.println("SD卡缓存目录:已存在!");
            } else {
                if (fff.mkdirs()) {
                    System.out.println("SD卡照片缓存目录:" + fff.getAbsolutePath() + "已创建!");
                } else {
                    System.out.println("SD卡照片缓存目录:创建失败!");
                }
            }

            File ffff = new File(Constants.CACHE_DIR_UPLOADING_IMG);
            if (ffff.exists()) {
                System.out.println("SD卡上传缓存目录:已存在!");
            } else {
                if (ffff.mkdirs()) {
                    System.out.println("SD卡上传缓存目录:" + ffff.getAbsolutePath() + "已创建!");
                } else {
                    System.out.println("SD卡上传缓存目录:创建失败!");
                }
            }
        } else {
            Toast.makeText(MyApp.this, "亲，您的SD不在了，可能有的功能不能用奥，赶快看看吧。", Toast.LENGTH_SHORT).show();
        }
    }


    private void initYoumeng() {
        PushAgent mPushAgent = PushAgent.getInstance(this);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                System.out.println("友盟推送 自定义消息的回调方法 dealWithCustomMessage ");

//                Toast.makeText(context, "友盟推送 自定义消息的回调方法 dealWithCustomMessage ", Toast.LENGTH_SHORT).show();
//                handler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        // 对自定义消息的处理方式，点击或者忽略
//                        boolean isClickOrDismissed = true;
//                        if (isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
            }

            /**
             * 自定义通知栏样式的回调方法   1.每当有通知送达时，均会回调getNotification方法，因此可以通过监听此方法来判断通知是否送达。
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
//                Toast.makeText(context, "友盟推送 自定义通知栏样式的回调方法 getNotification" + "UMessage=" + msg, Toast.LENGTH_SHORT).show();
//                System.out.println("友盟推送 自定义通知栏样式的回调方法 getNotification");

                switch (msg.builder_id) {
//                    case 1:
//                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//
//                        return_icon builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }

            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                super.handleMessage(context, uMessage);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);


        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         *
         *
         * 2.自定义通知打开动作，当用户点击了推送后，触发该回调
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                System.out.println("友盟推送 自定义行为的回调处理 dealWithCustomAction");
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String message = msg.custom;
                        try {
                            if (is_active) { // 说明系统中存在这个activity
//                                System.out.println("友盟推送 存在");
                            } else {
                                Intent intentFirst = new Intent(context, MainActivity.class);
                                intentFirst.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentFirst);
//                                System.out.println("友盟推送 不存在");

                            }
                            JSONObject jsonObject = new JSONObject(message);
                            int type = jsonObject.getInt("type");

                            if (type == 1) {
                                //系统消息
                                JSONObject json = new JSONObject(jsonObject.getString("data"));
                                int status = json.getInt("type");//0无跳转 1是url  2跳转包名
                                switch (status) {
                                    case 1:
                                        //webview展示链接
                                        String url = json.getString("data");
//                                    System.out.println("友盟推送 type = 1  " + url);

                                        Intent intent = new Intent(context, BrowseActivity.class);
                                        intent.putExtra("url", url);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        //跳转Activity
//                                    System.out.println("友盟推送 type = 2  ");
//						    		Intent intent = new Intent();
//							    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							    	startActivity(intent);
                                        break;
                                    case 3:
                                        //线路
                                        String id = json.getString("data");
                                        Intent intentLine = new Intent(context, LineDetailsActivity.class);
                                        intentLine.putExtra("id", id);
                                        intentLine.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intentLine);
                                        break;

                                }


                            } else if (type == 2) {
                                //订单消息

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                super.handleMessage(context, uMessage);
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("友盟推送", "MyApp onSuccess: " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d(s, "MyApp onSuccess: " + s1);
            }
        });
    }
}
