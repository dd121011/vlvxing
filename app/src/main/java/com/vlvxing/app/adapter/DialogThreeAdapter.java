package com.vlvxing.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vlvxing.app.R;
import com.vlvxing.app.model.PlaneBottonDialogThreeModel;
import com.vlvxing.app.model.PlaneScreenBottomListViewFirstModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 购买飞机票筛选按钮对应的弹出框中 航空公司对应列表的适配器
 */

public class DialogThreeAdapter extends BaseAdapter {
    private Context context;
    private List<PlaneBottonDialogThreeModel> bottomDialogThreeData;//填充数据
    private List<PlaneBottonDialogThreeModel> bottomDialogThreeLastData;//记录用户上次点击的数据集合 为true的是点击过的
    private boolean isChecked = false;//为true的时候--是被点击过的   false--未被点击过的
    private boolean isClicked = false;
    private HashMap<String,String> hsThreeData ;


    public DialogThreeAdapter(Context context, List<PlaneBottonDialogThreeModel> bottomDialogThreeData,HashMap<String,String> hsThreeData) {
        this.context = context;
        this.bottomDialogThreeData = bottomDialogThreeData;
        this.hsThreeData = hsThreeData;

    }

    @Override
    public int getCount() {
        return bottomDialogThreeData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return bottomDialogThreeData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public boolean hasStableIds() {
        //getCheckedItemIds()方法要求此处返回为真
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DialogViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.plane_bottom_three_listview_layout, null);
            viewHolder = new DialogViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.check = (CheckBox) convertView.findViewById(R.id.check);
            viewHolder.mark = (TextView) convertView.findViewById(R.id.mark);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DialogViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(bottomDialogThreeData.get(position).getTitle());
        if(hsThreeData.size()!=0){
            if(hsThreeData.containsKey(bottomDialogThreeData.get(position).getCode())){
                viewHolder.title.setTextColor(Color.parseColor("#ff6600"));
                viewHolder.price.setTextColor(Color.parseColor("#ff6600"));
                viewHolder.mark.setTextColor(Color.parseColor("#ff6600"));
                viewHolder.check.setChecked(true);
            }else{
                viewHolder.title.setTextColor(Color.parseColor("#000000"));
                viewHolder.price.setTextColor(Color.parseColor("#000000"));
                viewHolder.mark.setTextColor(Color.parseColor("#000000"));
                viewHolder.check.setChecked(false);
            }
        }else{
            if (bottomDialogThreeData.get(position).isBo()) {
                viewHolder.title.setTextColor(Color.parseColor("#ff6600"));
                viewHolder.price.setTextColor(Color.parseColor("#ff6600"));
                viewHolder.mark.setTextColor(Color.parseColor("#ff6600"));
                viewHolder.check.setChecked(true);
            } else {
                viewHolder.title.setTextColor(Color.parseColor("#000000"));
                viewHolder.price.setTextColor(Color.parseColor("#000000"));
                viewHolder.mark.setTextColor(Color.parseColor("#000000"));
                viewHolder.check.setChecked(false);
            }
        }


        return convertView;
    }

//    public interface OnClickListener {
//
//        void setOnChange(boolean b);
//
//    }
//
//    public void setOnChange(boolean b) {
//            isClicked = b;
//    }

    public class DialogViewHolder {
        public TextView title;
        public TextView price;
        public CheckBox check;
        public TextView mark;
        public LinearLayout threeLin;
    }


}
