package com.vlvxing.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.vlvxing.app.R;
import com.vlvxing.app.common.MyApp;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class DataPickerDialog {

    Context context;
    Dialog ad;
    DatePicker picker;
    public DataPickerDialog(final Context context) {
        this.context = context;
        ad = new Dialog(context, R.style.AlertDialogStyle);
        Window window = ad.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM); //让Dialog位于底部，默认居中
        ad.show(); //显示Dialog
        ad.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        // 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        window.setContentView(R.layout.datepicker_layout);
        View root = window.findViewById(R.id.root);
        picker= (DatePicker) window.findViewById(R.id.date_picker);
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.width = (int) (MyApp.getScreenWidth()); //设置Dialog的宽度
//		params.width=ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        root.setLayoutParams(params);
        //获取当前的日期
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        picker.setDate(year, month+1); //月份是从0-11
        picker.setMode(DPMode.SINGLE); //默认多选 模式设置为单选
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                String result=date;
                String[] split = result.split("-");
                int size = split.length;
                if (size > 0) {
                    String year = split[0];
                    String month = split[1];
                    if (month.length()==1){
                        month="0"+month;
                    }
                    String day = split[2];
                    if (day.length()==1){
                        day="0"+day;
                    }

                    result=year+"-"+month+"-"+day;
                }
                if (mIOnItemClickListener != null) {
                    mIOnItemClickListener.onItemClick(result);
                }
                ad.dismiss();
            }
        });

//        picker.setMode(DPMode.MULTIPLE); //默认多选 模式设置为单选
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//        String[] split = result.split("-");
//        int size = split.length;
//        if (size > 0) {
//            String year = split[0];
//            String month = split[1];
//            if (month.length()==1){
//                month="0"+month;
//            }
//            String day = split[2];
//            if (day.length()==1){
//                day="0"+day;
//            }
//
//            result=year+"-"+month+"-"+day;
//        }
//                ToastUtils.show(context, result);
//                if (mIOnItemClickListener != null) {
//                    mIOnItemClickListener.onItemClick(result);
//                }
//                ad.dismiss();
//            }
//        });

    }


    /**
     * 该接口用于把Dialog中listview item点击的相关数据传递给Activity
     * */
    public interface IOnItemClickListener {
        public void onItemClick(String result);
    }

    private IOnItemClickListener mIOnItemClickListener;

    public IOnItemClickListener getmIOnItemClickListener() {
        return mIOnItemClickListener;
    }

    public void setmIOnItemClickListener(
            IOnItemClickListener mIOnItemClickListener) {
        this.mIOnItemClickListener = mIOnItemClickListener;
    }
}
