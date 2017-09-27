package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handongkeji.ui.BaseActivity;
import com.qunar.adapter.PlaneOrderAdapter;
import com.qunar.view.SlideRecyclerView;
import com.vlvxing.app.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 */

public class PlaneSubmitOrderActivity extends BaseActivity {
    @Bind(R.id.head_title)
    TextView headTitle;//标题
    @Bind(R.id.btn_back)
    ImageView ban_back;//返回键
    private Context mcontext;
    private String goCity ;
    private String arriveCity ;
    private String date ;
    private int flag = 0;//记录当前页面 改签0  退票1
    private SlideRecyclerView recyclerView;
    private ArrayList<HashMap<String,String>> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_submit_plane_order);
        ButterKnife.bind(this);
        mcontext = this;
//        goCity = getIntent().getStringExtra("goCity");//出发城市
//        arriveCity = getIntent().getStringExtra("arriveCity");//到达城市
//        date = getIntent().getStringExtra("date");//出发日期
        headTitle.setText("机票订单");
        recyclerView = (SlideRecyclerView) findViewById(R.id.id_item_remove_recyclerview);
//        mList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            mList.add(i + "");
//        }
        mData = new ArrayList<>();
        HashMap<String,String> hs = new HashMap<>();
        hs.put("state","已完成");
        hs.put("cityLeft","北京");
        hs.put("cityRight","深圳");
        hs.put("price","1120");
        mData.add(hs);
        mData.add(hs);
        mData.add(hs);

        final PlaneOrderAdapter adapter = new PlaneOrderAdapter(this, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(new SlideRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mcontext,PlaneCompletedOrderActivity.class);
                startActivity(intent);
//                Toast.makeText(mcontext, "** " + mData.get(position) + " **", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position);
            }
        });
    }




    @OnClick({R.id.return_lin, R.id.right_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_txt:
//				clickSave();
                break;

        }
    }

}
