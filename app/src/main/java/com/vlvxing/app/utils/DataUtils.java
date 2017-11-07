package com.vlvxing.app.utils;

import com.handongkeji.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * 判断当前日期是星期几
     *
     * @param  pTime     设置的需要判断的时间  //格式如2012-09-08
     *
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getWeek(String pTime) {

        String Week = "周";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);
        switch(weekIndex){
            case 1 :
                Week += "天";
                break;
            case 2 :
                Week += "一";
                break;
            case 3 :
                Week += "二";
                break;
            case 4 :
                Week += "三";
                break;
            case 5 :
                Week += "四";
                break;
            case 6 :
                Week += "五";
                break;
            case 7 :
                Week += "六";
                break;


        }
        return Week;
    }
}
