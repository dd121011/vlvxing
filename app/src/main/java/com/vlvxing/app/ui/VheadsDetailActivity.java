package com.vlvxing.app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.ui.BaseActivity;
import com.handongkeji.utils.FileUtil;
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
import com.vlvxing.app.model.VheadsModel;
import com.vlvxing.app.utils.MyWebViewClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/18 0018.
 * V头条详情
 */

public class VheadsDetailActivity extends BaseActivity {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.right_img)
    ImageView rightImg;
    private String id;
    private String share_title, share_url,share_content,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vheadsdetail_layout);
        ButterKnife.bind(this);
        rightImg.setVisibility(View.VISIBLE);
        showDialog("加载中...");
        headTitle.setText("详情");
        Intent intent = getIntent();
        VheadsModel.DataBean bean = intent.getParcelableExtra("bean");
        id = bean.getVheadid(); //头条id
        share_title = bean.getHeadname();
        share_content="看世界，V旅行";
        content=bean.getDescription();
        share_url = Constants.URL_VHEADSSHARE + "?vHeadId=" + id; //微头条详情
//        String url="http://www.baidu.com";
//        webview.getSettings().setAllowFileAccess(true);
//        webview.getSettings().setJavaScriptEnabled(true);
//
//        webview.getSettings().setSupportZoom(true);
//        // 设置出现缩放工具
//        webview.getSettings().setBuiltInZoomControls(true);
//        //扩大比例的缩放
//        webview.getSettings().setUseWideViewPort(true);
////        //	自适应屏幕
//        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webview.getSettings().setLoadWithOverviewMode(true);
//        webview.loadUrl(share_url);
//        WebSettings mWebSettings = webview.getSettings();
//        mWebSettings.setBuiltInZoomControls(true);
//        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webview.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                dismissDialog();
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                showDialog("加载中...");
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

        initDatas();
    }

    private void initDatas() {
//        showDialog("加载中...");
//        String url = Constants.URL_VHEADSSHARE;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("vHeadId", id);
//        RemoteDataHandler.asyncPost(url, params, this, true, new RemoteDataHandler.Callback() {
//
//            @Override
//            public void dataLoaded(ResponseData data) {
//                String text = data.getJson();
                String text=content;
                int width = (int) (MyApp.getScreenWidth() * 0.32f - 30f);
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
                    File file = new File(Constants.ENVIROMENT_DIR_CACHE + "style0.html");
                    FileUtil.writeFile(file, text);
                    webview.getSettings().setDefaultTextEncodingName("UTF-8");
                    webview.getSettings().setJavaScriptEnabled(true);
                    webview.loadUrl("file://" + file.getPath());
                    WebSettings settings = webview.getSettings();
                    settings.setJavaScriptEnabled(true);
                    webview.setWebViewClient(new MyWebViewClient(webview));
                    dismissDialog();
                }
//            }
//        });

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
//                Toast.makeText(VheadsDetailActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(VheadsDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(VheadsDetailActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(VheadsDetailActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }

    @OnClick({R.id.return_lin, R.id.right_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_lin:
                finish();
                break;
            case R.id.right_img: //点击分享
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
        }
    }
}
