package com.vlvxing.app.utils;

import com.handongkeji.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/3.
 */
public class DataUtils {
    public static String parseString(String str){
        long t = StringUtils.isStringNull(str)?0:Long.valueOf(str);
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(t));
        return time;
    }

    public static String format(String source ,String format){
        long t = StringUtils.isStringNull(source)?0:Long.valueOf(source);
        return new SimpleDateFormat(format).format(new Date(t));
    }
}
