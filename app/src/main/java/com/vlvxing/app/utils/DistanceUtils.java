package com.vlvxing.app.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.handongkeji.utils.StringUtils;
import com.vlvxing.app.common.MyApp;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 根据用户指定的两个坐标点，计算这两个点的实际地理距离
 */

public class DistanceUtils {
    public static String getDistance(double pathlat,double pathlng){
        // 根据用户指定的两个坐标点，计算这两个点的实际地理距离
        String l= MyApp.getInstance().getLat();
        String ln=MyApp.getInstance().getLng();
        double lat = Double.valueOf(l);
        double lng = Double.valueOf(ln);
        LatLng p1 = new LatLng(lat, lng);
        LatLng p2 = new LatLng(pathlat, pathlng);
        //计算p1、p2两点之间的直线距离，单位：米
        double distance = DistanceUtil.getDistance(p1, p2);
        distance = distance * 0.001; //米转换成千米
        //四舍五入，保留两位小数
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
       String d= nf.format(distance);
        return d;
    }

}
