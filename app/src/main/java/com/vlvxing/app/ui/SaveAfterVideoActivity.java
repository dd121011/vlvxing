package com.vlvxing.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.CommonUtils;
import com.handongkeji.utils.StringUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.MyDialog;
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.model.RecordMapModel;
import com.vlvxing.app.model.RecordModel;
import com.vlvxing.app.model.TrackDetailModel;
import com.vlvxing.app.utils.CacheData;
import com.vlvxing.app.utils.DataUtils;
import com.vlvxing.app.utils.MyVideoThumbLoader;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 保存后
 */

public class SaveAfterVideoActivity extends BaseActivity {


    @Bind(R.id.bg_img)
    ImageView bgImg;
    @Bind(R.id.name_txt)
    TextView nameTxt;
    @Bind(R.id.time_txt)
    TextView timeTxt;
    @Bind(R.id.right_img)
    ImageView rightImg;
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.share_lin)
    LinearLayout shareLin;
    @Bind(R.id.rel)
    RelativeLayout rel;
    private String videoPath;
    String id, url;
    private String share_title, share_content, share_url;
    public static Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saveaftervideo_layout);
        ButterKnife.bind(this);
        headTitle.setVisibility(View.GONE);

        mContext = this;
//        share_url="https://www.baidu.com";
        showDialog("加载中...");
        // 获取图片的宽度
        // 获取图片的宽度
        int img_width = (MyApp.getScreenWidth() - CommonUtils.dip2px(this, 50));
        ViewGroup.LayoutParams params = rel.getLayoutParams();
        params.height = (img_width * 521) / 375; // 750:500
        rel.setLayoutParams(params);

        ViewGroup.LayoutParams params1 = bgImg.getLayoutParams();
        params1.height = (img_width * 209) / 375; // 750:500
        bgImg.setLayoutParams(params1);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        int type = intent.getIntExtra("type", 0);
        System.out.println("test 视频功能  SaveAfterVideoActivity  getIntent type="+type);
        System.out.println("test 视频功能  SaveAfterVideoActivity  getIntent id="+id);
        share_url = Constants.URL_VIDEOSHARE + "?pathInfoId=" + id;
        if (type == 1) {
            rightImg.setVisibility(View.GONE);
            List<RecordModel> list = CacheData.getRecentSubList("addmaker");
            int position = Integer.valueOf(id);
            RecordModel model = list.get(position);
            String Coverurl = model.getCoverUrl();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(Coverurl, new HashMap());
            Bitmap bitmap = mmr.getFrameAtTime();//获取第一帧图片
            bgImg.setImageBitmap(bitmap);
            nameTxt.setText(StringUtils.isStringNull(model.getPathName()) ? model.getAddress() : model.getPathName());
            url = model.getCoverUrl();
            videoPath = model.getVideoUrl();
            String time = DataUtils.format(model.getTime(), "yyyy-MM-dd HH:mm");
            timeTxt.setText(time);
        } else if (type == 0) {
            rightImg.setVisibility(View.VISIBLE);
            RecordMapModel.DataBean bean = intent.getParcelableExtra("data");
            String Coverurl = bean.getCoverurl();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(Coverurl, new HashMap());
            //获得第一帧图片
            Bitmap bitmap = mmr.getFrameAtTime();//获取第一帧图片
            bgImg.setImageBitmap(bitmap);
            nameTxt.setText(StringUtils.isStringNull(bean.getPathname()) ? bean.getAddress() : bean.getPathname());
            videoPath = bean.getVideourl();
            url = bean.getCoverurl();
            String time = DataUtils.format(bean.getCreatetime(), "yyyy-MM-dd HH:mm");
            timeTxt.setText(time);
            share_title = StringUtils.isStringNull(bean.getPathname()) ? "V旅行" : bean.getPathname();
            share_content = StringUtils.isStringNull(bean.getPathname()) ? bean.getAddress() : bean.getPathname();
        } else {
            rightImg.setVisibility(View.VISIBLE);
            TrackDetailModel.DataBean.PathinfosBean bean = intent.getParcelableExtra("data");
            String Coverurl = bean.getCoverurl();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(Coverurl, new HashMap());
            Bitmap bitmap = mmr.getFrameAtTime();//获取第一帧图片
            bgImg.setImageBitmap(bitmap);
            nameTxt.setText(StringUtils.isStringNull(bean.getPathname()) ? bean.getAddress() : bean.getPathname());
            videoPath = bean.getVideourl();
            url = bean.getCoverurl();
            String time = DataUtils.format(bean.getCreatetime(), "yyyy-MM-dd HH:mm");
            timeTxt.setText(time);
            share_title = StringUtils.isStringNull(bean.getPathname()) ? "V旅行" : bean.getPathname();
            share_content = StringUtils.isStringNull(bean.getPathname()) ? bean.getAddress() : bean.getPathname();
        }
        dismissDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
    }

    int count = 0;

    @OnClick({R.id.return_lin, R.id.right_img, R.id.video_img, R.id.share_txt, R.id.update_txt, R.id.delete_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_img: //点击分享
                if (count == 0) {
                    count++;
                    shareLin.setVisibility(View.VISIBLE);
                } else {
                    count = 0;
                    shareLin.setVisibility(View.GONE);
                }
                break;
            case R.id.share_txt: //分享
                shareLin.setVisibility(View.GONE);
                ShareDialog sdialog = new ShareDialog(this);
                sdialog.setmOnclickListener(new ShareDialog.ClickSureListener() {
                    @Override
                    public void onClick(int type) { //type 1QQ 2微博  3微信  4朋友圈
                        if (type == 1) {
                            umShare(SHARE_MEDIA.QQ);
                        } else if (type == 2) {
                            umShare(SHARE_MEDIA.SINA);
                        } else if (type == 3) {
                            umShare(SHARE_MEDIA.WEIXIN);
                        } else {
                            umShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        sdialog.dismissDialog();
                    }
                });
                break;
            case R.id.update_txt:  //编辑
                shareLin.setVisibility(View.GONE);
                startActivity(new Intent(this, AddVideoMakerActivity.class).putExtra("id", id).putExtra("url", url).putExtra("videoPath", videoPath).putExtra("type", 1));
                break;
            case R.id.delete_txt:  //删除
                shareLin.setVisibility(View.GONE);
                MyDialog dialog = new MyDialog(this, "确定删除当前标注点？");
                dialog.setNegativeButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickDelete(id);
                        dialog.dismissDialog();
                    }
                });
                break;
            case R.id.video_img: //播放视频
                Intent intent = new Intent(this, VideoPlayActivity.class);
                intent.putExtra("path", videoPath);
                startActivity(intent);
                break;
        }
    }

    /**
     * 友盟分享
     */
    private void umShare(SHARE_MEDIA share_media) {
//        ShareAction shareAction = new ShareAction(this);
//        shareAction.setPlatform(share_media).withMedia(new UMImage(this, R.mipmap.logos)).withTitle(share_title).withText(share_content).withTargetUrl(share_url).setCallback(umShareListener).share();
        ShareAction shareAction = new ShareAction(this);
        UMWeb  web = new UMWeb(share_url);
        web.setTitle(share_title);//标题
        web.setThumb(new UMImage(this, R.mipmap.logos));
        web.setDescription("V旅行");//描述
        shareAction.setPlatform(share_media).withMedia(web).withText(share_content).setCallback(umShareListener).share();

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
//            Log.d("plat", "platform" + platform);
//            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                Toast.makeText(SaveAfterVideoActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(SaveAfterVideoActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(SaveAfterVideoActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(SaveAfterVideoActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
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
                    ToastUtils.show(SaveAfterVideoActivity.this, "删除成功!");
                    dismissDialog();
                    finish();
                } else {
                    ToastUtils.show(SaveAfterVideoActivity.this, message);
                }
            }
        });
    }
}
