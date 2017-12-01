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

import java.util.HashMap;
import java.util.List;

/**
 * 购买飞机票筛选按钮对应的弹出框中 机场落地对应列表的适配器
 */

public class DialogFourAdapter extends BaseAdapter {
    private Context context;
    private List<PlaneBottonDialogThreeModel> bottomDialogThreeData;//填充数据
    private HashMap<String, Boolean> hsResult;

    public DialogFourAdapter(Context context, List<PlaneBottonDialogThreeModel> bottomDialogThreeData, HashMap<String, Boolean> hsResult) {
        this.context = context;
        this.bottomDialogThreeData = bottomDialogThreeData;
        this.hsResult = hsResult;
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

        if (hsResult.size() != 0) {
            if (hsResult.containsKey(bottomDialogThreeData.get(position).getTitle())) {
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
        } else {
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

    public class DialogViewHolder {
        public TextView title;
        public TextView price;
        public CheckBox check;
        public TextView mark;
        public LinearLayout threeLin;
    }


}
