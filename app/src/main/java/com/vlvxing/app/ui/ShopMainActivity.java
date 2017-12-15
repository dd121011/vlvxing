package com.vlvxing.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.FileUtil;
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
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.model.NearModel;
import com.vlvxing.app.model.ShopMainModel;
import com.vlvxing.app.utils.CallUtils;
import com.vlvxing.app.utils.MyWebViewClient;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/9 0009.
 * 店铺主页
 */

public class ShopMainActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.right_img)
    ImageView rightImg;
    @Bind(R.id.car_img)
    ImageView carImg;
    @Bind(R.id.name_txt)
    TextView nameTxt;
    @Bind(R.id.content_txt)
    TextView contentTxt;
    @Bind(R.id.webview)
    WebView webview;
    private String id;
    private String tel;
    private String share_title,share_content, share_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopmain_layout);
        ButterKnife.bind(this);
        headTitle.setText("店铺主页");
        rightImg.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
        id=intent.getStringExtra("id"); //店铺id
        share_url = Constants.URL_SHOPDESHARE + "?businessId=" + id;

        initData();

    }

    private void initData() {
        showDialog("加载中...");
        String url = Constants.URL_SHOPDETAILS;
        HashMap<String, String> params = new HashMap<>();
        params.put("businessId", id);
        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)) {
                    return;
                }
                Gson gson = new Gson();
                ShopMainModel model = gson.fromJson(json, ShopMainModel.class);
                String status = model.getStatus();
                if ("1".equals(status)) {
                    ShopMainModel.DataBean bean = model.getData();
                    String picurl=bean.getBusinesspic();
                    if (!StringUtils.isStringNull(picurl)) {
                        Glide.with(ShopMainActivity.this).load(picurl).into(carImg);
                    }
                    nameTxt.setText(bean.getBusinessname());
                    share_title=bean.getBusinessname();
                    share_content=StringUtils.isStringNull(bean.getDescraption())?"看世界，V旅行":bean.getDescraption();
                    contentTxt.setText(bean.getDescraption());
                    tel = bean.getTel();
                   String url= bean.getBusinessproject();
                    List<ShopMainModel.DataBean.ProjectsBean> projects = bean.getProjects();
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < projects.size(); i++) {
                        ShopMainModel.DataBean.ProjectsBean bean1 = projects.get(i);
                         String descraption = bean1.getDescraption();
                         sb.append(descraption);
                    }
                    loadWeb(sb.toString());
                } else {
                    ToastUtils.show(ShopMainActivity.this, model.getMessage());
                }
                dismissDialog();
            }
        });
    }

    private void loadWeb(String data) {
        String text = data;
        int width = (int) (MyApp.getScreenWidth());
        if (text != null) {
            String[] splitd = text.split("img");
            StringBuffer sbd = new StringBuffer();
            for (int i = 0; i < splitd.length; i++) {
                if (i < splitd.length - 1) {
                    sbd.append(splitd[i] + "img name='cellImage' ");
                } else {
                    sbd.append(splitd[i]);
                }
            }
            text = sbd.toString();
            text += "<script> var imgs = document.getElementsByName(\"cellImage\");var width ="
                    + width
                    + ";for(var i=0;i<imgs.length;i++){var img = imgs[i];var iWidth = img.offsetWidth;var iHeight = img.offsetHeight;var height = iHeight * width / iWidth;img.style.width = width + 'px';img.style.height = height + 'px';} </script>";
            Log.e("TAG", "text3" + text);
            File file = new File(Constants.ENVIROMENT_DIR_CACHE + "style.html");
            FileUtil.writeFile(file, text);
            webview.getSettings().setDefaultTextEncodingName("UTF-8");
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new MyWebViewClient(webview));
            webview.loadUrl("file://" + file.getPath());
        }
 }

    int count=0;
    @OnClick({R.id.return_lin, R.id.right_img, R.id.call_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_img: //分享
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
            case R.id.call_rel:  //打电话
                CallUtils.call(this,tel);
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
//                Toast.makeText(ShopMainActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(ShopMainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(ShopMainActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(ShopMainActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }

}
