package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/3 0003.
 * 定制游--选择城市
 */

public class SelectCityActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.select_edt)
    EditText selectEdt;
    @Bind(R.id.result_list)
    ListView resultList;
    @Bind(R.id.guonei_txt)
    TextView guoneiTxt;
    @Bind(R.id.guowai_txt)
    TextView guowaiTxt;
    @Bind(R.id.city_txt)
    TextView cityTxt;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.common_no_data_layout)
    LinearLayout commonNoDataLayout;
    private Intent intent;
    List<JSONObject> data_list = new ArrayList<JSONObject>();
    private resultAdapter adapter;
    private int type=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        intent = getIntent();
        headTitle.setText("选择地区");
        //设置第一次进入界面不弹出软键盘
        headTitle.setFocusable(true);
        headTitle.setFocusableInTouchMode(true);
        headTitle.requestFocus();
        guoneiTxt.setSelected(true);
        commonNoDataLayout.setVisibility(View.GONE);
        adapter = new resultAdapter(this, data_list);
        listView.setAdapter(adapter);
        selectEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    resultList.setVisibility(View.VISIBLE);
                    lin.setVisibility(View.GONE);
                    selectCity();
                } else {
                    resultList.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
        data_list.clear();
        String url = Constants.URL_CITYSALL;
        HashMap<String, String> params = new HashMap<String, String>();
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (null != json && !json.equals("")) {
                    JSONObject obj;
                    try {
                        obj = new JSONObject(json);
                        String status = obj.getString("status");
                        String message = obj.getString("message");
                        if (status.equals("1")) {
                            String datas = obj.getString("data");
                            JSONObject objects=new JSONObject(datas);
                            String guonei=objects.getString("guonei");
                            String guowai=objects.getString("foreign");
                            JSONArray array=null;
                            if (type==1){
                               array = new JSONArray(guonei);
                            }else if (type==2){
                               array = new JSONArray(guowai);
                            }
                            List<JSONObject> result = new ArrayList<JSONObject>();
                            if (array.length() <= 0) {
                                commonNoDataLayout.setVisibility(View.VISIBLE);
                                resultList.setVisibility(View.GONE);
                            } else {
                                commonNoDataLayout.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    data_list.add(object);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectCity() {
        String url = Constants.URL_CITYSELECT;
        HashMap<String, String> params = new HashMap<String, String>();
        String city = selectEdt.getText().toString().trim();
        params.put("areaName", city);
        RemoteDataHandler.asyncPost(url, params, this, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                String json = data.getJson();
                if (null != json && !json.equals("")) {
                    JSONObject obj;
                    try {
                        obj = new JSONObject(json);
                        String status = obj.getString("status");
                        String message = obj.getString("message");
                        if (status.equals("1")) {
                            String datas = obj.getString("data");
                            JSONArray array = new JSONArray(datas);
                            List<JSONObject> result = new ArrayList<JSONObject>();
                            if (array.length() <= 0) {
                                commonNoDataLayout.setVisibility(View.VISIBLE);
                                resultList.setVisibility(View.GONE);
                            } else {
                                commonNoDataLayout.setVisibility(View.GONE);
                                resultList.setVisibility(View.VISIBLE);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    result.add(object);
                                }
                                resultAdapter adapter = new resultAdapter(SelectCityActivity.this, result);
                                resultList.setAdapter(adapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    class resultAdapter extends BaseAdapter {
        Context mContext;
        List<JSONObject> list;

        public resultAdapter(Context mContext, List<JSONObject> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size() > 0 ? list.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.city_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            JSONObject obj = list.get(position);
            try {
                final String areaname = obj.getString("areaname");
                final String areaid = obj.getString("areaid");
                String areanamewithspell=obj.getString("areanamewithspell");
                holder.cityTxt.setText(areaname);
                holder.pinyinTxt.setText(areanamewithspell);
                holder.itemLin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("areaname", areaname);
                        intent.putExtra("areaid", areaid);
                        setResult(4, intent);
                        finish();

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return convertView;
        }


        class ViewHolder {
            @Bind(R.id.city_txt)
            TextView cityTxt;
            @Bind(R.id.pinyin_txt)
            TextView pinyinTxt;
            @Bind(R.id.item_lin)
            LinearLayout itemLin;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick({R.id.return_lin, R.id.exit_txt, R.id.guonei_txt, R.id.guowai_txt, R.id.city_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.exit_txt:
                resultList.setVisibility(View.GONE);
                lin.setVisibility(View.VISIBLE);
                break;
            case R.id.guonei_txt:
                guoneiTxt.setSelected(true);
                guowaiTxt.setSelected(false);
                type = 1;
                initData();
                break;
            case R.id.guowai_txt:
                guoneiTxt.setSelected(false);
                guowaiTxt.setSelected(true);
                type = 2;
                initData();
                break;
            case R.id.city_txt:
                break;
        }
    }
}
