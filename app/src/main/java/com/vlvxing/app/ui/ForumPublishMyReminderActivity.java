package com.vlvxing.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.handongkeji.ui.BaseActivity;
import com.vlvxing.app.R;
import com.vlvxing.app.adapter.ForumPublishMyCommentAdapter;
import com.vlvxing.app.adapter.MyRemindAdapter;
import com.vlvxing.app.widget.ForumRecyclerView;
import com.vlvxing.app.widget.MyReminderRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 社区--关注提醒
 */

public class ForumPublishMyReminderActivity extends BaseActivity {

    private Context context;
    @Bind(R.id.id_item_remove_recyclerview)
    MyReminderRecyclerView recyclerView;
    private ArrayList<HashMap<String,String>> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forum_publish_my_comment);
        ButterKnife.bind(this);
        context = this;
        mData = new ArrayList<>();
        HashMap<String,String> hs = new HashMap<>();
        hs.put("name","测试");
        hs.put("date","2017-9-18");
        mData.add(hs);
        mData.add(hs);
        mData.add(hs);

        MyRemindAdapter adapter = new MyRemindAdapter(context,mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(new MyReminderRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context,PlaneCompletedOrderActivity.class);
//                startActivity(intent);
//                Toast.makeText(mcontext, "** " + mData.get(position) + " **", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position);
            }


        });

    }



    @OnClick({R.id.return_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_img:
                finish();
                break;

        }
    }

}
