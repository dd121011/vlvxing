package com.vlvxing.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.MyGridView;
import com.handongkeji.widget.MyListView;
import com.qunar.service.RequestService;

import com.vlvxing.app.R;
import com.vlvxing.app.adapter.DialogFourAdapter;
import com.vlvxing.app.adapter.DialogThreeAdapter;
import com.vlvxing.app.adapter.ImageGridViewAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.ui.ForumPublishActivity;
import com.vlvxing.app.ui.ForumPublishMyMessageActivity;
import com.vlvxing.app.ui.PlaneSearchActivity;
import com.vlvxing.app.ui.TopicDetailsActivity;
import com.vlvxing.app.utils.ToastUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 社区
 */

public class ForumFragment extends Fragment {

//    @Bind(R.id.head_title)
//    TextView headTitle;
//    @Bind(R.id.right_img)
//    ImageView rightImg;
//    @Bind(R.id.return_lin)
//    LinearLayout linearLayout;
    @Bind(R.id.top_radio_group)
    RadioGroup topRadioGroup;
    @Bind(R.id.first_img)
    ImageView firstImg;
    @Bind(R.id.second_img)
    ImageView secondImg;
    @Bind(R.id.three_img)
    ImageView threeImg;
    private Context context;
    @Bind(R.id.list_view)
    MyListView forumListview;
    @Bind(R.id.common_swiperefresh)
    SwipeRefreshLayout swipeRefresh;

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private boolean isRefreshing = true;
    private boolean isLoadMore;
    private int currentPage = 1;
    private int pageSize = 5;



    @Bind(R.id.num_txt)
    TextView numTxt;
    @Bind(R.id.havemsg_rel)
    RelativeLayout havemsgRel;
    @Bind(R.id.nomsg_img)
    ImageView nomsgImg;
    private Dialog checkedDialog;
    private List<HashMap<String,Object>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
//        headTitle.setText("社区");
//        rightImg.setVisibility(View.INVISIBLE);
//        linearLayout.setVisibility(View.INVISIBLE);

        String paramsStr = "{\"arr\":\"SHA\",\"dpt\":\"PEK\",\"date\":\"2017-10-15\",\"ex_track\":\"youxuan\"}";
//        String url = RequestService.doRequest(Constants.QUNAR_SEARCHFLIGHT, paramsStr);
//      getInfo(url, null);
        checkedDialog = new Dialog(context, R.style.BottomDialog);
        list = getListViewData();


        initView();
        setOnItemClickListener();
//        if (!StringUtils.isStringNull(userpic)) { // 加载图片
//            ImageLoader
//                    .getInstance().displayImage(userpic,
//                    headImg, options,
//                    animateFirstListener);
//        } else {
//            headImg.setImageResource(R.mipmap.touxiang_moren);
//        }
        initTopRadioButtonChecked();
        return view;
    }


    private void initView(){
        ForumAdapter adapter = new ForumAdapter(context);
        forumListview.setAdapter(adapter);

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isRefreshing) return;
                isRefreshing = true;
                currentPage = 1;
                swipeRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListViewData();
                    }
                }, 1000);
            }
        };

        swipeRefresh.setOnRefreshListener(onRefreshListener);
        swipeRefresh.setColorSchemeResources(R.color.color_ea5413);

        forumListview.setLoadDataListener(new MyListView.LoadDataListener() {
            @Override
            public void onLoadMore() {
                if (isLoadMore) return;
                isLoadMore = true;
                currentPage++;
                getListViewData();
            }
        });
        onRefresh();
    }

    private void onRefresh() {
        swipeRefresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }


    private void setOnItemClickListener(){
        forumListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "item被点击了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TopicDetailsActivity.class);
                startActivity(intent);

            }
        });
    }



    //顶部导航  新鲜、关注、附近
    private void initTopRadioButtonChecked(){
        topRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.first_rabtn:
                        //新鲜
                        firstImg.setVisibility(View.VISIBLE);
                        secondImg.setVisibility(View.INVISIBLE);
                        threeImg.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.second_rabtn:
                        //关注
                        firstImg.setVisibility(View.INVISIBLE);
                        secondImg.setVisibility(View.VISIBLE);
                        threeImg.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.three_rabtn:
                        //附近
                        firstImg.setVisibility(View.INVISIBLE);
                        secondImg.setVisibility(View.INVISIBLE);
                        threeImg.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
    @OnClick({R.id.add_experience,R.id.nomsg_img})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.add_experience:
                if (!checkedDialog.isShowing()){
                    showDialog();
                }
                //添加心得
                break;
            case R.id.nomsg_img:
                //我的消息
                Intent intent = new Intent(context, ForumPublishMyMessageActivity.class);
                startActivity(intent);
                break;
        }
    }

    //右上角未读消息接口数据
    private void getNoreadmsg() {
        if (StringUtils.isStringNull(MyApp.getInstance().getUserTicket())){
            return;
        }
        String url = Constants.URL_MSGCENUM;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApp.getInstance().getUserTicket());
        RemoteDataHandler.asyncTokenPost(url, context, false, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                JSONObject obj = new JSONObject(json);
                String status = obj.getString("status");
                String message = obj.getString("message");
                if (status.equals("1")) {
                    String num = obj.getString("data");
                    if ("0".equals(num)) {
                        havemsgRel.setVisibility(View.GONE);
                        nomsgImg.setVisibility(View.VISIBLE);
                    } else {
                        havemsgRel.setVisibility(View.VISIBLE);
                        nomsgImg.setVisibility(View.GONE);
                        numTxt.setText(num);
                    }

                } else {
                    ToastUtils.show(context, message);
                }
            }
        });
    }



    private void getInfo(String url1, String params) {
        String url = url1;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put();
        RemoteDataHandler.asyncPost(url, null, getActivity(), false, new RemoteDataHandler.Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) throws JSONException {
                        String json = data.getJson();
                        if (StringUtils.isStringNull(json)) {
                            Toast.makeText(getActivity(), "接口访问失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getActivity(), "接口访问成功", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }



    //机场落地 起飞机场 的数据源
    private List<HashMap<String,Object>> getListViewData() {
        list  = new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> map = new HashMap<>();
        LinkedList<Integer> pictures = new LinkedList<>();
        pictures.add(R.drawable.loading);
        pictures.add(R.drawable.loading);
        pictures.add(R.drawable.loading);
        map.put("list",pictures);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        return list;
    }


    public final class ViewHolder {
        public GridView gridView;
        public ImageView headImg;
    }

    //贴吧列表的数据源适配器
    class ForumAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ForumAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
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
                convertView = mInflater.inflate(R.layout.forum_img_gridview, null);
                holder.gridView = (MyGridView) convertView.findViewById(R.id.img_group);
                holder.headImg = (ImageView)convertView.findViewById(R.id.head_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            LinkedList<Integer> pictures = (LinkedList)list.get(position).get("list");
            if (pictures.size()>=3&&pictures.size()!=4) holder.gridView.setNumColumns(3);
            if (pictures.size()==2||pictures.size()==4) holder.gridView.setNumColumns(2);
            if (pictures.size()==1) holder.gridView.setNumColumns(1);

            //绑定自定义的adapter
            ImageGridViewAdapter imgadapter = new ImageGridViewAdapter(context,pictures);
            holder.gridView.setAdapter(imgadapter);
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getActivity(), "grid item被点击了", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }
    private void showDialog() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_forum_publish, null);
        Button photoBtn = (Button)contentView.findViewById(R.id.photo_btn);
        Button videoBtn = (Button)contentView.findViewById(R.id.video_btn);
        Button cancelBtn = (Button)contentView.findViewById(R.id.cancel_btn);
        checkedDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.8);
        contentView.setLayoutParams(layoutParams);
        checkedDialog.getWindow().setGravity(Gravity.BOTTOM);
        checkedDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        checkedDialog.show();
        //图片
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedDialog.dismiss();
                Intent intent = new Intent(context, ForumPublishActivity.class);
                startActivity(intent);
            }
        });
        //视频
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedDialog.dismiss();
            }
        });
        //取消
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedDialog.dismiss();
            }
        });



    }

}
