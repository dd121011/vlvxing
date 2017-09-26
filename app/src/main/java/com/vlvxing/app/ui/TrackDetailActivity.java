package com.vlvxing.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.CommonUtils;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.NoScrollListView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.MyDialog;
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.model.ProCustomModel;
import com.vlvxing.app.model.TrackDetailModel;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.MyVideoThumbLoader;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 轨迹详情
 */

public class TrackDetailActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.right_img)
    ImageView rightImg;
    @Bind(R.id.name_txt)
    TextView nameTxt;
    @Bind(R.id.time_txt)
    TextView timeTxt;
    @Bind(R.id.address_txt)
    TextView addressTxt;
    @Bind(R.id.content_txt)
    TextView contentTxt;
    @Bind(R.id.list_view)
    NoScrollListView listView;
    @Bind(R.id.share_lin)
    LinearLayout shareLin;
    @Bind(R.id.update_txt)
    TextView updateTxt;
    private String id; //轨迹id
    List<TrackDetailModel.DataBean.PathinfosBean> data_list = new ArrayList<>();
    myAdapter adapter;
    private String share_title,share_content, share_url;
    private boolean isFirst=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackdetail_layout);
        ButterKnife.bind(this);
//        share_url="https://www.baidu.com";
        headTitle.setVisibility(View.GONE);
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(R.mipmap.more);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        share_url = Constants.URL_LINESSHARE + "?travelRoadId=" + id;
        adapter = new myAdapter(this, data_list);
        listView.setAdapter(adapter);
        updateTxt.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (data_list.size()>0){
            data_list.clear();
        }
        if (isFirst) {
            showDialog("加载中...");
            isFirst=false;
        }
        String url = Constants.URL_GETTRAROAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("travelRoadId", id);
        RemoteDataHandler.asyncTokenPost(url, this, true, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                TrackDetailModel model = gson.fromJson(json, TrackDetailModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    dismissDialog();
                    TrackDetailModel.DataBean bean = model.getData();
                    share_title=StringUtils.isStringNull(bean.getTravelroadtitle())?"V旅行":bean.getTravelroadtitle();
                    nameTxt.setText(bean.getTravelroadtitle());
                    String time = DataUtils.format(bean.getCreatetime(), "yyyy/MM/dd");
                    timeTxt.setText(time);
                    addressTxt.setText(bean.getStartarea());
                    contentTxt.setText(StringUtils.isStringNull(bean.getDescription())?"":"描述："+bean.getDescription());
                    share_content=StringUtils.isStringNull(bean.getDescription())?bean.getStartarea():bean.getDescription();
                    List<TrackDetailModel.DataBean.PathinfosBean> lines = bean.getPathinfos();
                    data_list.addAll(lines);
                    if (data_list.size() <= 0) {
                        listView.setVisibility(View.GONE);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    class myAdapter extends BaseAdapter {
        Context mContext;
        List<TrackDetailModel.DataBean.PathinfosBean> list;

        public myAdapter(Context mContext, List<TrackDetailModel.DataBean.PathinfosBean> list) {
            this.mContext = mContext;
            this.list = list;
        }
        public void removeItems(String id){
            for (int j=0;j<list.size();j++){
                TrackDetailModel.DataBean.PathinfosBean bean = list.get(j);
                if(id.equals(bean.getPathinfoid())){
                    list.remove(bean);
                }
            }
            if (list.size()==0){
                listView.setVisibility(View.GONE);
            }
            notifyDataSetChanged();
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
                        R.layout.trackdetail_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // 获取图片的宽度
            int img_width = MyApp.getInstance().getScreenWidth()- CommonUtils.dip2px(mContext, 24);
            ViewGroup.LayoutParams params = holder.bgImg.getLayoutParams();
            params.height = (img_width * 140) / 351; // 750:500
            holder.bgImg.setLayoutParams(params);
            TrackDetailModel.DataBean.PathinfosBean bean = list.get(position);
           holder.nameTxt.setText(bean.getPathname());
            String time = DataUtils.format(bean.getCreatetime(), "yyyy-MM-dd HH:mm");
            holder.timeTxt.setText(time);
            String pathinfoid = bean.getPathinfoid();
            holder.deleteLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MyDialog dialog = new MyDialog(mContext, "确认删除?");
                    dialog.setNegativeButtonListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismissDialog();
                            clickDelete(pathinfoid);
                        }
                    });
                }
            });

            String type = bean.getType();
            if ("0".equals(type)) {  //0图片，1视频
                holder.bgImg.setVisibility(View.VISIBLE);
                holder.videoRel.setVisibility(View.GONE);
                String head = bean.getPicurl();
                if (!StringUtils.isStringNull(head)) {
                    Glide.with(mContext).load(head).into(holder.bgImg);
                }
            }else{
                holder.bgImg.setVisibility(View.GONE);
                holder.videoRel.setVisibility(View.VISIBLE);
                Bitmap videoThumb2 = MyVideoThumbLoader.getVideoThumb2(bean.getVideourl()); //获取视频封面图
                holder.Img.setImageBitmap(videoThumb2); //将视频封面图设置到控件上
            }

            holder.bgImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, SaveAfterActivity.class).putExtra("id", pathinfoid).putExtra("data",bean).putExtra("type",2)); //图片详情
                }
            });
            holder.videoRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, SaveAfterVideoActivity.class).putExtra("id", pathinfoid).putExtra("data",bean).putExtra("type",2)); //视频详情
                }
            });
            return convertView;
        }


         class ViewHolder {
            @Bind(R.id.name_txt)
            TextView nameTxt;
            @Bind(R.id.delete_lin)
            LinearLayout deleteLin;
            @Bind(R.id.time_txt)
            TextView timeTxt;
            @Bind(R.id.bg_img)
            ImageView bgImg;
             @Bind(R.id.video_rel)
             RelativeLayout videoRel;
             @Bind(R.id.img)
             ImageView Img;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private void clickDelete(String id) {
        showDialog("");
        String url = Constants.URL_DELETEPATHINFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("pathInfoId", id);
        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
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
                    ToastUtils.show(TrackDetailActivity.this, "删除成功!");
                    adapter.removeItems(id);
                    dismissDialog();
                } else {
                    ToastUtils.show(TrackDetailActivity.this, message);
                }
            }
        });
    }

    int count=0;
    @OnClick({R.id.return_lin, R.id.right_img,R.id.share_txt,R.id.delete_txt,R.id.address_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_img: //点击分享
                if (count==0) {
                    count++;
                    shareLin.setVisibility(View.VISIBLE);
                }else{
                    count=0;
                    shareLin.setVisibility(View.GONE);
                }
                break;
            case R.id.share_txt: //分享
                shareLin.setVisibility(View.GONE);
                ShareDialog sdialog=new ShareDialog(this);
                sdialog.setmOnclickListener(new ShareDialog.ClickSureListener() {
                    @Override
                    public void onClick(int type) { //type 1QQ 2微博  3微信  4朋友圈
                        if (type==1){
                            umShare(SHARE_MEDIA.QQ);
                        }else if (type==2){
                            umShare(SHARE_MEDIA.SINA);
                        }else if (type==3){
                            umShare(SHARE_MEDIA.WEIXIN);
                        }else {
                            umShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        sdialog.dismissDialog();
                    }
                });
                break;
            case R.id.delete_txt:  //删除轨迹
                shareLin.setVisibility(View.GONE);
                final MyDialog dialog = new MyDialog(this, "确认删除?");
                dialog.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismissDialog();
                        deleteRoute();
                    }
                });
                break;
            case R.id.address_txt:
                startActivity(new Intent(this,TrackActivity.class).putExtra("id",id)); //轨迹线路
                break;
        }
    }

    /**
     * 友盟分享
     */
    private void umShare(SHARE_MEDIA share_media) {
        ShareAction shareAction = new ShareAction(this);
        shareAction.setPlatform(share_media).withMedia(new UMImage(this, R.mipmap.logos)).withTitle(share_title).withText(share_content).withTargetUrl(share_url).setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(TrackDetailActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TrackDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(TrackDetailActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(TrackDetailActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }
    /**
     * 删除轨迹
     * */
    private void deleteRoute() {
        showDialog("");
        String url = Constants.URL_DELETETRAROAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", myApp.getUserTicket());
        params.put("travelRoadId", id);
        RemoteDataHandler.asyncTokenPost(url, this, false, params, new RemoteDataHandler.Callback() {
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
                    ToastUtils.show(TrackDetailActivity.this, "删除成功!");
                    dismissDialog();
                    finish();
                } else {
                    ToastUtils.show(TrackDetailActivity.this, message);
                }
            }
        });
    }
}
