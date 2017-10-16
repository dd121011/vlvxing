package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 社区--所在位置
 */

public class ForumPublishLocaitonActivity extends BaseActivity {

    @Bind(R.id.list_view)
    ListView listView;

    private Context mContext;
    private ArrayList<HashMap<String,String>> mData;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forum_publish_location);
        ButterKnife.bind(this);
        mContext = this;
        mData = new ArrayList<HashMap<String,String>>();
        intent = getIntent();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("title","亦庄");
        hashMap.put("txt","通州区经海七路北口尖子班");
        mData.add(hashMap);
        mData.add(hashMap);
        LocationAdapter adapter = new LocationAdapter(mContext);
        listView.setAdapter(adapter);

        initOnItemClickClickListener();
    }

    private void initOnItemClickClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("result",mData.get(position).get("title"));//添加要返回给页面1的数据
                setResult(1, intent);// 设置回传数据。resultCode值是1，这个值在主窗口将用来区分回传数据的来源，以做不同的处理
                finish();// 关闭子窗口ChildActivity
            }
        });
    }



    @OnClick({R.id.return_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_img:
//                intent.putExtra("result","");//添加要返回给页面1的数据
//                setResult(1, intent);// 设置回传数据。resultCode值是1，这个值在主窗口将用来区分回传数据的来源，以做不同的处理
                finish();// 关闭子窗口ChildActivity
                break;
        }
    }

    public final class ViewHolder {
        public TextView title;
        public TextView txt;
    }

    //定位列表的数据源适配器
    class LocationAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public LocationAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.act_forum_publish_location_listview_item, null);
                holder.title = (TextView) convertView.findViewById(R.id.title_txt);
                holder.txt = (TextView) convertView.findViewById(R.id.txt);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(mData.get(position).get("title"));
            holder.txt.setText(mData.get(position).get("txt"));
            return convertView;
        }
    }
}
