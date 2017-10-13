package com.vlvxing.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.selecity.PlaneSelestorCityActivity;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qunar.service.RequestService;

import com.vlvxing.app.R;
import com.vlvxing.app.adapter.ImageGridViewAdapter;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.lib.CalendarSelectorActivity;
import com.vlvxing.app.model.PlaneBottonDialogThreeModel;
import com.vlvxing.app.ui.PlaneOrderActivity;
import com.vlvxing.app.ui.PlaneSearchActivity;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

    @Bind(R.id.forum_listview)
    ListView forumListview;

    private List<HashMap<String,Object>> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
//        headTitle.setText("社区");
//        rightImg.setVisibility(View.INVISIBLE);
//        linearLayout.setVisibility(View.INVISIBLE);

        String paramsStr = "{\"arr\":\"SHA\",\"dpt\":\"PEK\",\"date\":\"2017-10-15\",\"ex_track\":\"youxuan\"}";
        String url = RequestService.doRequest(Constants.QUNAR_SEARCHFLIGHT, paramsStr);
//        getInfo(url, null);

        list = getListViewData();
        ForumAdapter adapter = new ForumAdapter(context);
        forumListview.setAdapter(adapter);
//        if (!StringUtils.isStringNull(userpic)) { // 加载图片
//            ImageLoader
//                    .getInstance().displayImage(userpic,
//                    headImg, options,
//                    animateFirstListener);
//        } else {
//            headImg.setImageResource(R.mipmap.touxiang_moren);
//        }
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
        return view;
    }

    @OnClick({R.id.add_experience})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.add_experience:
                //添加心得
                break;

        }
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
        List<HashMap<String,Object>> list  = new ArrayList<HashMap<String,Object>>();
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
                holder.gridView = (GridView) convertView.findViewById(R.id.img_group);
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

            return convertView;
        }
    }

}
