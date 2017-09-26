package com.vlvxing.app.utils;

/**
 * 工具标签
 * 应用常量实体类
 *
 * @author wangguan
 */
public class GlobalTools {
    public static String LIMIT = "10";
    /**
     * 默认标签
     */
    public static final String DEFAULT_TAG = "ZLW";
    /**
     * 是否是第一进入app
     */
    public static final String IS_FIRST_START = "isFirstStart";

    /**
     * true 是开启 <br>
     * false 是关闭<br>
     */
    public static final boolean DEFAULT_DEBUG = false;

    public static final String ISPUSH = "is_push";

    public static final String WEIXIN_PAY = "weixin_pay";

    public static int Comment_Length = 1000;
    /**
     * 第三方登录使用的类型,qq,weixin,weibo
     */
    public static int TPOS_WEIXING = 2;
    public static int TPOS_QQ = 3;
    public static int TPOS_WEIBO = 4;

    /**
     * 我的个人页面滚动监听
     */
    public static String ACTION_MY_COMMENT_SEND = "com.my.comment.send";
    public static String ACTION_MY_REPLY_CLICK = "com.my.reply.click";

    /**
     * 加载方向
     */
    public interface LoadDirect {
        int Refresh = 0;
        int More = 1;
    }

    public static String HAVE_NEWS = "com.my.news";


}
