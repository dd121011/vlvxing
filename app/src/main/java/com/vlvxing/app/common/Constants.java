/**
 * ClassName: Constants.java
 * created on 2013-1-24
 * Copyrights 2013-1-24 hjgang All rights reserved.
 * site: http://t.qq.com/hjgang2012
 * email: hjgang@yahoo.cn
 */
package com.vlvxing.app.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author chaiqingshan
 * @category 全局变量
 * @日期 2015-11-24
 * @时间 下午2:05:25
 * @年份 2015
 */
public final class Constants {
    /**
     * 去哪儿网
     */
    public static final String QUNAR_KEY = "0748cebf8cf346cf64070333c7fed08f";   //从开发者平台获取
    public static final String QUNAR_TOKEN = "109cfbc62855bdc711a68c77c3e6bd97"; //从开发者平台获取
//    public static final String QUNAR_BASE_URL = "http://60.205.213.239:81/ticket/";
    public static final String QUNAR_BASE_URL = "http://app.mtvlx.cn/ticket/";
    public static final String QUNAR_SEARCHFLIGHT = "flight.national.supply.sl.searchflight";//航班搜索接口
    /**
     * 系统初始化配置文件名
     */
    public static final String SYSTEM_INIT_FILE_NAME = "handongkeji.android.sysini";
    public static final String FLAG = "com.handongkeji.android";

    /**
     * 判断是否从MainActivity进入登录
     */
    public static final String FIRST_LOGIN = "first_login";
    public static final String FIRST_LOGIN_FLAG = "first_login_flag";

    // regist/userinfo.json?
    /**
     * 微信支付Appid
     */
    public static final String APP_ID = "wxd3cb391989fe67f1";
    /**
     * 微信支付Appkey
     */
    public static final String APP_KEY = "88888888888888888888888888888888";

    /**
     * QQ ID
     */
    public static final String QQ_ID = "1106153781";
    /**
     * QQ KEY
     */
    public static final String QQ_KEY = "53AkGv7rAsG5OMli";
    /**
     * 微信 ID
     */
    public static final String WX_ID = "wxd3cb391989fe67f1";
    public static final String WX_KEY = "88888888888888888888888888888888";
//    public static final String WX_KEY = "226c687e3cf3f69ed0ee44a54cc1aef8";
    /**
     * 微信 KEY
     */
//    public static final String WX_SECRET = "226c687e3cf3f69ed0ee44a54cc1aef8";

    /**
     * 用于标识请求照相功能的activity结果码
     */
    public static final int RESULT_CODE_CAMERA = 1;
    /**
     * 用来标识请求相册gallery的activity结果码
     */
    public static final int RESULT_CODE_PHOTO_PICKED = 2;
    public static final int RESULT_CODE_PHOTO_CUT = 3;
    /**
     * 用于识别帖子界面ListView
     */
    public static final int LIST_TOP = 1001;
    public static final int LIST_BOTTOM = 1002;
    /**
     * 图片类型
     */
    public static final String IMAGE_UNSPECIFIED = "image/*";
    /**
     * 本地缓存目录
     */
    public static String CACHE_DIR;
    /**
     * 图片缓存目录
     */
    public static String CACHE_DIR_IMAGE;
    /**
     * 待上传图片缓存目录
     */
    public static String CACHE_DIR_UPLOADING_IMG;
    /**
     * 图片目录
     */
    public static String CACHE_IMAGE;
    /**
     * 图片名称
     */
    public static final String PHOTO_PATH = "handongkeji_android_photo";
    /**
     * 语音缓存目录
     */
    public static String CACHE_VOICE;

    public static void init(Context context){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            CACHE_DIR = context.getExternalCacheDir().getAbsolutePath();
        }else{
            CACHE_DIR = context.getCacheDir().getAbsolutePath();
        }
        File file = new File(CACHE_DIR,"image");
        file.mkdirs();
        CACHE_IMAGE = file.getAbsolutePath();
        CACHE_DIR_IMAGE = CACHE_IMAGE;
        file = new File(CACHE_DIR,"temp");
        file.mkdirs();
        CACHE_DIR_UPLOADING_IMG = file.getAbsolutePath();
        file = new File(CACHE_DIR,"voice");
        file.mkdirs();
        CACHE_VOICE = file.getAbsolutePath();

        file = new File(CACHE_DIR,"html");
        file.mkdirs();
        ENVIROMENT_DIR_CACHE = file.getAbsolutePath();
    }

//    static {
//        if (Environment.MEDIA_MOUNTED.equals(Environment
//                .getExternalStorageState())) {
//            CACHE_DIR = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath() + "/handongkeji.android/";
//        } else {
//            CACHE_DIR = Environment.getRootDirectory().getAbsolutePath()
//                    + "/handongkeji.android/";
//        }
//
//        CACHE_IMAGE = CACHE_DIR + "/image";
//        CACHE_DIR_IMAGE = CACHE_DIR + "/pic";
//        CACHE_DIR_UPLOADING_IMG = CACHE_DIR + "/uploading_img";
//        CACHE_VOICE = CACHE_DIR + "/voice/";
//    }

    public static String ENVIROMENT_DIR_CACHE = CACHE_DIR + "/cache/";

    private Constants() {
    }

    /**
     * 数据库版本号
     */
    public static final int DB_VERSION = 1;
    /**
     * 数据库名
     */
    public static final String DB_NAME = "android.db";
    /**
     * 与服务器端连接的协议名
     */
    public static final String PROTOCOL = "http://";
    /**
     * 服务器IP
     */
    public static final String HOST = "handongkeji.com";
    public static final String HOST2 = "handongkeji.com";

    /**
     * 服务器端口号
     */
    public static final String PORT = ":8090/lvyoushejiao/";
    public static final String PORT2 = ":8090/";
    /**
     * 应用上下文完整路径
     *
     */
    public static final String URL_PLANE_PATH = "http://app.mtvlx.cn/demo/";
//	public static final String URL_CONTEXTPATH = PROTOCOL + HOST2 + PORT;
//	public static final String URL_CONTEXTPATH = "http://192.168.1.68/lvyoushejiao/";
    public static final String URL_CONTEXTPATH = "http://app.mtvlx.cn/lvyoushejiao/";  //客户的
//    public static final String URL_CONTEXTPATH = "http://192.168.1.21/lvyoushejiao/";

    /**
     * 应用图片完整路径c
     */
    public static final String URL_CONTEXTPATH_PIC = PROTOCOL + HOST2 + PORT2;
    /**
     * 应用图片完整路径2
     *
     */
    public static final String URL_CONTEXTPATH_PIC2 = PROTOCOL + HOST + PORT2;

    /**
     * 获取广告
     **/
    public static final String URL_GET_AD = URL_CONTEXTPATH
            + "sysAd/getAdList.json";

    /**
     * 获取用户信息
     **/
    public static final String URL_GET_USERINFO = URL_CONTEXTPATH
            + "mbuser/getUserInfo.json";
    /**
     * 上传图片接口
     **/
    public static final String URL_UPLOAD = URL_CONTEXTPATH
            + "tool/uploadAPI.json";
    /**
     * 上传语音接口
     */
    public static final String URL_UPLOADAUDIO = URL_CONTEXTPATH
            + "tool/uploadAudio.json";

    /**
     * 3.1.4登录
     */
    public static final String URL_SIGN_IN = URL_CONTEXTPATH+"MbUserController/login.json";

    /**
     * 注册
     */
    public static final String URL_REGISTER = URL_CONTEXTPATH+"MbUserController/regSetPassword.json";
    /**
     * 第三方登录MbUserController/loginByOpenNew.json
     **/
    public static final String URL_LOGIN_OPEN = URL_CONTEXTPATH+ "MbUserController/loginByOpenNew.json";
    /**
     * 第三方注册
     */
    public static final String URL_REGISTER_OPEN = URL_CONTEXTPATH+"MbUserController/registByOpen.json";
    /**
     * 3.1.1	注册获取手机验证码
     */
    public static final String URL_GETVERIFYCODE = URL_CONTEXTPATH + "mbUserPer/getCode.json";

    /**
     * 3.1.21	登录获取手机验证码
     */
    public static final String URL_VERIFYCODE_SIGNIN = URL_CONTEXTPATH+"mbUserPer/code.json";
    /**
     * 验证验证码
     */
    public static final String URL_REG_GETCODE = URL_CONTEXTPATH + "mbUserPer/checkCode.json";
    /**
     * 版本更新autoUpdate/getVersion.json
     */
    public static final String URL_VERSIONUPDATE = URL_CONTEXTPATH + "autoUpdate/getVersion.json?configtype=1";
    /**
     * 推送
     */
    public static final String URL_MESSAGEPUSH= URL_CONTEXTPATH+"mbuser/auth/messagePush.json";
    /**
     * 关于我们
     */
    public static final String URL_ABOUTWE= URL_CONTEXTPATH+"SysTextController/auth/aboutUs.json";
    /**
     * 忘记密码
     */
    public static final String URL_FORGETPWD= URL_CONTEXTPATH+"MbUserController/forgotPassword.json";
    /**
     * 意见反馈
     */
    public static final String URL_FEEDBACK= URL_CONTEXTPATH+"MbUserProposalController/auth/feedBack.json";
    /**
     * 改变用户消息推送状态
     */
    public static final String URL_TOGGLE_MESSAGE = URL_CONTEXTPATH+"MbUserController/isUserPush.json";
    /**
     * 查询用户消息推送状态
     */
    public static final String URL_TOGGLE_PUSHSTATUS = URL_CONTEXTPATH+"MbUserController/userPushStatus.json";
    /**
     * 更换手机号
     */
    public static final String URL_UPDATEPHONE= URL_CONTEXTPATH+"MbUserController/updateUserMobile.json";
    /**
     * 获取用户信息
     */
    public static final String URL_GETUSERINFO= URL_CONTEXTPATH+"MbUserController/auth/getUserInfo.json";
    /**
     * 保存用户信息
     */
    public static final String URL_SAVEUSERINFO= URL_CONTEXTPATH+"MbUserController/auth/changeSetting.json";
    /**
     * 首页新闻头条
     */
    public static final String URL_VHEADS= URL_CONTEXTPATH+"ProVHeadController/getVHeads.json";
    /**
     * 首页轮播图
     */
    public static final String URL_SYSAD= URL_CONTEXTPATH+"SysAdController/getSlideShow.json";
    /**
     * 首页热门目的地
     */
    public static final String URL_HOTAREA= URL_CONTEXTPATH+"ProHotAreaController/getHotArea.json";
    /**
     * 首页热门推荐、当季游玩
     */
    public static final String URL_RECOMMEND= URL_CONTEXTPATH+"ProRecommendController/getProRecommend.json";
    /**
     * 关于我们
     **/
    public static final String URL_ABOUT = URL_CONTEXTPATH + "systext/getinfo.json";
    /**
     * 路线详情
     **/
    public static final String URL_LINEDETAILS = URL_CONTEXTPATH + "ProProductController/productDetails.html";
    /**
     * 评论列表ProEvaluateController/productEvaluates.html
     **/
    public static final String URL_REMARKLIST = URL_CONTEXTPATH + "ProEvaluateController/productEvaluates.html";
    /**
     * 城市列表
     **/
    public static final String URL_CITYLIST = URL_CONTEXTPATH + "/sysArea/getTheCity.json";

    /**
     * 机票城市列表 Zophar
     **/
    public static final String PLANE_CITYLIST  =  "/getTheCity";
    /**
     * 城市搜索 Zophar
     */
    public static String PLANE_CITYSELECT = "/sousuodiqu";

    /**
     * 定制游中的目的地（包括国内外市）sysArea/getAllTheCity.json
     */
    public static String URL_CITYSALL=URL_CONTEXTPATH+"sysArea/getAllTheCity.json";
    /**
     * 城市搜索
     */
    public static String URL_CITYSELECT=URL_CONTEXTPATH+"sysArea/sousuodiqu.json";
    /**
     * 热门景点
     */
    public static final String URL_HOTSCIENCE= URL_CONTEXTPATH+"ProSpotsController/getProSpots.html";
    /**
     * 国内外商品列表
     */
    public static final String URL_PRODUCTLIST1= URL_CONTEXTPATH+"ProProductController/productList.html";
    /**
     * 附近时获取商品列表  或者   附近中的自驾游和周末游
     */
    public static final String URL_PRODUCTLIST2= URL_CONTEXTPATH+"ProNearController/nearProductList.html";
    /**
     * 附近景点用车
     */
    public static final String URL_NEARCAR= URL_CONTEXTPATH+"ProNearController/getNearCarInfoList.html";
    /**
     * 附近农家院
     */
    public static final String URL_FARMYARD= URL_CONTEXTPATH+"ProNearController/farmyardProductList.html";
    /**
     * 定制游保存
     */
    public static final String URL_SAVEPROCUSTOM= URL_CONTEXTPATH+"ProCustomController/auth/saveProCustom.json";
    /**
     * 我的定制
     */
    public static final String URL_PROCUSTOM= URL_CONTEXTPATH+"ProCustomController/auth/getProCustom.json";
    /**
     * 我的定制删除
     */
    public static final String URL_DELETEPROCUSTOM= URL_CONTEXTPATH+"ProCustomController/auth/deleteProCustom.json";
    /**
     * 点击收藏
     */
    public static final String URL_COLLECT= URL_CONTEXTPATH+"ProCollectController/auth/saveProCollect.json";
    /**
     * 取消收藏
     */
    public static final String URL_CANCLECOLLECT= URL_CONTEXTPATH+"ProCollectController/auth/deleteProCollect.json";
    /**
     * 是否收藏
     */
    public static final String URL_ISCOLLECT= URL_CONTEXTPATH+"ProCollectController/auth/isProCollect.json";
    /**
     * 保存订单
     */
    public static final String URL_SAVEORDER= URL_CONTEXTPATH+"OrderInfoController/auth/placeAnOrder.html";
    /**
     * 通过城市名称获取areaId
     */
    public static final String URL_GETAREAID= URL_CONTEXTPATH+"sysArea/getAreaIdByAreaName.json";
    /**
     * 我的订单
     */
    public static final String URL_MYORDER= URL_CONTEXTPATH+"OrderInfoController/auth/getOrderList.html";
    /**
     * 取消订单
     */
    public static final String URL_DELETEORDER= URL_CONTEXTPATH+"OrderInfoController/auth/deleteOrder.html";
    /**
     * 保存评价
     */
    public static final String URL_SAVEREMARK= URL_CONTEXTPATH+"ProEvaluateController/auth/saveEvaluate.html";
    /**
     * 我的收藏
     */
    public static final String URL_MYCOLLECT= URL_CONTEXTPATH+"ProCollectController/auth/getProCollect.json";
    /**
     * 点击进入消息的接口
     */
    public static final String URL_MSGCENTER= URL_CONTEXTPATH+"sysMsg/auth/getSysMsgCountByMsgstatus.json";
    /**
     * 消息列表
     */
    public static final String URL_MSGCELIST= URL_CONTEXTPATH+"sysMsg/auth/getsysmsglist.json";
    /**
     * 未读消息数量统计
     */
    public static final String URL_MSGCENUM= URL_CONTEXTPATH+"sysMsg/auth/getSysMsgCount.json";
    /**
     * 点击查看消息（更改消息状态）
     */
    public static final String URL_READMSG= URL_CONTEXTPATH+"sysMsg/readSysMsgById.json";
    /**
     *获取附近中的商家列表（1km以内）
     */
    public static final String URL_NEARBUS= URL_CONTEXTPATH+"BusBusinessController/nearBusBusiness.json";
    /**
     *商家的详细信息
     */
    public static final String URL_SHOPDETAILS= URL_CONTEXTPATH+"BusBusinessController/getBusBusinessDetail.json";
    /**
     *保存记录（单个图片或视频）
     */
    public static final String URL_SAVETRAVEL= URL_CONTEXTPATH+"TraRoadController/auth/saveTravelPathInfo.html";
    /**
     *编辑旅途点
     */
    public static final String URL_UPDATETRAVEL= URL_CONTEXTPATH+"TraRoadController/auth/updatePathinfo.html";
    /**
     *保存记录（有轨迹）
     */
    public static final String URL_SAVETRAVELROAD= URL_CONTEXTPATH+"TraRoadController/auth/saveTravelRoad.html";
    /**
     * 3.4.3 支付宝通知 回调地址（支付宝）alipay/getnotify.json
     */
    public static final String URL_ALIPAY = URL_CONTEXTPATH+"aliPay/getnotify.html";
    /**
     * 订单支付状态--支付宝
     */
    public static final String URL_ORDER_STATUS = URL_CONTEXTPATH+"aliPay/orderstatus.json";

    /**
     * Zophar 支付宝回调
     */
//    public static final String URL_PLANE_ALIPAY = URL_PLANE_PATH+"aliPay/flyforderstatus";
    /**
     * Zophar 机票状态
     */
    public static final String URL_PLANE_ORDER_STATUS = URL_PLANE_PATH+"aliPay/flyforderstatus";

    /**
     * 微信下单
     */
    public static final String URL_WXORDER = URL_CONTEXTPATH+"wxpay/auth/placeOrder.json";
    /**
     * 旅途首页（按周围四个角的范围）
     */
    public static final String URL_TRAVELROAD = URL_CONTEXTPATH+"TraRoadController/auth/TravelRoadList.html";
    /**
     * 通过点获得旅途
     */
    public static final String URL_GETTRAROAD = URL_CONTEXTPATH+"TraRoadController/auth/getTraRoad.html";
    /**
     * 删除旅途首页中的点
     */
    public static final String URL_DELETEPATHINFOS = URL_CONTEXTPATH+"TraRoadController/auth/deleteTraPathinfoList.html";
    /**
     * 删除旅途（需要将途中的点也删除）
     */
    public static final String URL_DELETETRAROAD = URL_CONTEXTPATH+"TraRoadController/auth/deleteTraRoad.html";
    /**
     * 删除旅途点
     */
    public static final String URL_DELETEPATHINFO = URL_CONTEXTPATH+"TraRoadController/auth/deletePathinfo.html";
    /**
     * 订单详情
     */
    public static final String URL_ORDERDETAIL = URL_CONTEXTPATH+"OrderInfoController/auth/getOrderInfo.html";
    /**
     * App整体分享
     */
    public static final String URL_APPSHARE= URL_CONTEXTPATH+"shareurl/appshare.json";
    /**
     * 旅途中的点的分享（照片）
     */
    public static final String URL_PHOTOSSHARE= URL_CONTEXTPATH+"shareurl/zhaopianshare.json";
    /**
     * 旅途中的点的分享（视频）
     */
    public static final String URL_VIDEOSHARE= URL_CONTEXTPATH+"shareurl/shipingshare.json";
    /**
     * 商品详情分享页（用车，农家院，周末等）
     */
    public static final String URL_SHOPDETAILSHARE= URL_CONTEXTPATH+"shareurl/farmstayscheduleshare.json";
    /**
     * 商品店铺详情分享页
     */
    public static final String URL_SHOPDESHARE= URL_CONTEXTPATH+"shareurl/businessinfoshare.json";
    /**
     * 个人线路分享详情页
     */
    public static final String URL_LINESSHARE= URL_CONTEXTPATH+"shareurl/xianludetailshare.json";
    /**
     * 分享微头条--微头条详情
     */
    public static final String URL_VHEADSSHARE= URL_CONTEXTPATH+"shareurl/vtopshare.json";
    /**
     * 地图标注点分享
     */
    public static final String URL_ROADSHARE= URL_CONTEXTPATH+"shareurl/auth/travelRoadShare.json";

    /**
     * 删除消息
     */
    public static final String URL_DELETE_MSG = URL_CONTEXTPATH+"sysMsg/deleteSysMsgInfo.json";
}
