package com.vlvxing.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.FileUtil;
import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;
import com.vlvxing.app.common.MyApp;
import com.vlvxing.app.utils.MyWebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/4/13 0013.
 * 产品特色Fragment
 */

public class FeaturesFragment extends Fragment {
    Context mcontext;
    @Bind(R.id.webview)
    WebView webview;

    String data;

//        public static FeaturesFragment getInstance(String data) {
//        Bundle bundle = new Bundle();
//        bundle.putString("data", data);
//        FeaturesFragment instance = new FeaturesFragment();
//        instance.setArguments(bundle);
//        return instance;
//    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mcontext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.features_fragment, container, false);
        ButterKnife.bind(this, view);
        initDatas();
        return view;
    }

    private void initDatas() {
        Bundle bundle = getArguments();
        data = bundle.getString("data");
        String text = data;
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
            File file = new File(Constants.ENVIROMENT_DIR_CACHE + "style.html");
            FileUtil.writeFile(file, text);
            webview.getSettings().setDefaultTextEncodingName("UTF-8");
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new MyWebViewClient(webview));
            webview.loadUrl("file://" + file.getPath());

        }

//        String url = Constants.URL_ABOUT;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("textflag",  "");
//        params.put("type", "");
//        RemoteDataHandler.asyncPost(url, params, mcontext, true, new RemoteDataHandler.Callback() {
//
//            @Override
//            public void dataLoaded(ResponseData data) {
//                String json = data.getJson();
//                if (null != json && !json.equals("")) {
//                    try {
//                        JSONObject obj = new JSONObject(json);
//                        Log.e("TAG", "obj" + obj);
//                        String status = obj.getString("status");
//                        if ("1".equals(status)) {
//                            String text = null;
//                            JSONArray array = obj.getJSONArray("data");
//                            for (int i = 0; i < array.length(); i++) {
//                                JSONObject readme = array.getJSONObject(i);
//                                text = readme.getString("text");
//                                Log.e("TAG", "text1" + text);
//                            }
//                            int width = (int) (MyApp.getScreenWidth() * 0.32f - 30f);
//                            if (text != null) {
//                                String[] splitd = text.split("img");
//                                StringBuffer sbd = new StringBuffer();
//                                for (int i = 0; i < splitd.length; i++) {
//                                    if (i < splitd.length - 1) {
//                                        sbd.append(splitd[i] + "img name='cellImage' ");
//                                    } else {
//                                        sbd.append(splitd[i]);
//                                    }
//                                }
//                                text = sbd.toString();
//                                text += "<script> var imgs = document.getElementsByName(\"cellImage\");var width ="
//                                        + width
//                                        + ";for(var i=0;i<imgs.length;i++){var img = imgs[i];var iWidth = img.offsetWidth;var iHeight = img.offsetHeight;var height = iHeight * width / iWidth;img.style.width = width + 'px';img.style.height = height + 'px';} </script>";
//                                Log.e("TAG", "text3" + text);
//                                File file = new File(Constants.ENVIROMENT_DIR_CACHE + "style.html");
//                                FileUtil.writeFile(file, text);
//                                webview.getSettings().setDefaultTextEncodingName("UTF-8");
//                                webview.getSettings().setJavaScriptEnabled(true);
//                                webview.loadUrl("file://" + file.getPath());
//                            }
//                        }
//                    } catch (Exception exc) {
//                        exc.printStackTrace();
//                    }
//
//                }
//            }
//
//        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ;
    }
}
