package com.handongkeji.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2016/10/13.
 */
public class StringUtils {

    public static boolean isStringNull(String str){
        return TextUtils.isEmpty(str) || "null".equals(str)||str==null;
    }

    public static String parseStr(String str){
        if (isStringNull(str)) {
            return "";
        }
        return str;
    }
}
