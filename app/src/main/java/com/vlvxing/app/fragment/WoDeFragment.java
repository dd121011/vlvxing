package com.vlvxing.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.AnimateFirstDisplayListener;
import com.handongkeji.utils.StringUtils;
import com.handongkeji.widget.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.common.ShareDialog;
import com.vlvxing.app.ui.LineDetailsActivity;
import com.vlvxing.app.ui.LoginActivity;
import com.vlvxing.app.ui.MyCollectActivity;
import com.vlvxing.app.ui.MyOrderActivity;
import com.vlvxing.app.ui.MyProCustomActivity;
import com.vlvxing.app.ui.RegisterActivity;
import com.vlvxing.app.ui.SettingActivity;
import com.vlvxing.app.ui.TrackDetailActivity;
import com.vlvxing.app.ui.UserInfoActivity;
import com.vlvxing.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/13 0013.
 * 我的Fragment
 */

public class WoDeFragment extends Fragment {

    Context mcontext;
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.return_lin)
    LinearLayout returnLin;
    @Bind(R.id.right_img)
    ImageView rightImg;
    @Bind(R.id.head_img)
    RoundImageView headImg;
    @Bind(R.id.name_txt)
    TextView nameTxt;
    @Bind(R.id.sex_img)
    ImageView sexImg;
    @Bind(R.id.info_lin)
    LinearLayout infoLin;
    @Bind(R.id.nologin_lin)
    LinearLayout nologinLin;
    private String token;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    private String share_title,share_content, share_url;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mcontext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wode_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        getUserInfo();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        token = MyApp.getInstance().getUserTicket();
        if (StringUtils.isStringNull(token)) { //判断是否登录  未登录
            nologinLin.setVisibility(View.VISIBLE);
            infoLin.setVisibility(View.GONE);
        }else{
            nologinLin.setVisibility(View.GONE);
            infoLin.setVisibility(View.VISIBLE);
        }
        getUserInfo();
    }

    private void init() {
        headTitle.setText("我的");
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(R.mipmap.set_red);
        returnLin.setVisibility(View.GONE);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageForEmptyUri(R.mipmap.touxiang_moren)
                .showImageOnFail(R.mipmap.touxiang_moren)
                .cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        token = MyApp.getInstance().getUserTicket();
        if (StringUtils.isStringNull(token)) { //判断是否登录  未登录
             nologinLin.setVisibility(View.VISIBLE);
            infoLin.setVisibility(View.GONE);
        }else{
            nologinLin.setVisibility(View.GONE);
            infoLin.setVisibility(View.VISIBLE);
        }
    }

    private void getUserInfo() {
        String token=MyApp.getInstance().getUserTicket();
        if (StringUtils.isStringNull(token)){
            return;
        }
        String url= Constants.URL_GETUSERINFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        RemoteDataHandler.asyncPost(url, params, mcontext, false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json=data.getJson();
                if (StringUtils.isStringNull(json)){
                    return;
                }
                JSONObject object=new JSONObject(json);
                String status = object.getString("status");
                String message = object.getString("message");
                if ("1".equals(status)){
                    JSONObject obj = object.getJSONObject("data");
                    String userpic = obj.getString("userpic");
                    if (!StringUtils.isStringNull(userpic)) { // 加载图片
                        ImageLoader
                                .getInstance().displayImage(userpic,
                                headImg, options,
                                animateFirstListener);
                    } else {
                        headImg.setImageResource(R.mipmap.touxiang_moren);
                    }
                    String usernick = obj.getString("usernick");
                    nameTxt.setText(usernick);
                    String usersex = obj.getString("usersex"); //1男，2女
                    if ("1".equals(usersex)){
                        sexImg.setImageResource(R.mipmap.man_img);
                    }else if ("2".equals(usersex)){
                        sexImg.setImageResource(R.mipmap.woman_img);
                    }
                }else {
                    ToastUtils.show(mcontext,message);
                }
            }
        });
    }

    int count=0;
    @OnClick({R.id.set_img,R.id.right_img,R.id.info_rel, R.id.login_txt, R.id.regist_txt, R.id.rel1, R.id.rel2, R.id.rel3, R.id.rel4, R.id.rel5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_img:
                //判断是否登录
                boolean flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    mcontext.startActivity(new Intent(mcontext, SettingActivity.class));
                }
                break;
            case R.id.right_img:
                //判断是否登录
                 flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    mcontext.startActivity(new Intent(mcontext, SettingActivity.class));
                }
                break;
            case R.id.info_rel:  //个人信息
                String token = MyApp.getInstance().getUserTicket();
                if (StringUtils.isStringNull(token)) {
                    mcontext.startActivity(new Intent(mcontext, LoginActivity.class));
                } else {
                    mcontext.startActivity(new Intent(mcontext, UserInfoActivity.class));
                }
                break;
            case R.id.login_txt:
                mcontext.startActivity(new Intent(mcontext,LoginActivity.class));
                break;
            case R.id.regist_txt:
                mcontext.startActivity(new Intent(mcontext,RegisterActivity.class));
                break;
            case R.id.rel1:  //个人信息
                //判断是否登录
                 flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    mcontext.startActivity(new Intent(mcontext, UserInfoActivity.class));
                }
                break;
            case R.id.rel2:  //我的订单
                flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    startActivity(new Intent(mcontext, MyOrderActivity.class));
                }
                break;
            case R.id.rel3:  //我的定制
                flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    startActivity(new Intent(mcontext, MyProCustomActivity.class));
                }
                break;
            case R.id.rel4:  //我的收藏
                flag = MyApp.getInstance().isLogin(mcontext);
                if (flag) {
                    startActivity(new Intent(mcontext, MyCollectActivity.class));
                }
                break;
            case R.id.rel5:  //分享
                //判断是否登录
                 flag = MyApp.getInstance().isLogin(mcontext);
                if (!flag) {
                    return;
                }
                share_url = Constants.URL_APPSHARE;
                share_title="V旅行";
                share_content="看世界，V旅行";
                ShareDialog sdialog=new ShareDialog(mcontext);
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
        }
    }

    /**
     * 友盟分享
     */
    private void umShare(SHARE_MEDIA share_media) {
        ShareAction shareAction = new ShareAction((Activity) mcontext);
        shareAction.setPlatform(share_media).withMedia(new UMImage(mcontext, R.mipmap.logos)).withTitle(share_title).withText(share_title).withTargetUrl(share_url).setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mcontext, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mcontext, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mcontext, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mcontext, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(mcontext).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }
}
