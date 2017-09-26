package com.vlvxing.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handongkeji.handler.RemoteDataHandler;
import com.handongkeji.modle.ResponseData;
import com.handongkeji.utils.StringUtils;
import com.qunar.service.RequestService;

import com.vlvxing.app.R;
import com.vlvxing.app.common.Constants;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 论坛
 */

public class ForumFragment extends Fragment {

    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.right_img)
    ImageView rightImg;
    @Bind(R.id.return_lin)
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ButterKnife.bind(this, view);
        headTitle.setText("论坛");
        rightImg.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);

        try {
            String params = URLEncoder.encode("{\"dpt\":\"SYX\",\"arr\":\"PEK\",\"date\":\"2017-09-22\",\"ex_track\":\"youxuan\"}", "utf-8");
            String result = RequestService.getSign("flight.national.supply.sl.searchflight","{\"dpt\":\"SYX\",\"arr\":\"PEK\",\"date\":\"2017-09-22\",\"ex_track\":\"youxuan\"}","1411442340");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        getInfo();
        return view;
    }
    private void getInfo(){
        String url = Constants.QUNAR_BASE_URL;
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put();
        RemoteDataHandler.asyncPost(url, params, getActivity(), false, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) throws JSONException {
                String json = data.getJson();
                if (StringUtils.isStringNull(json)){
                    return;
                }
//                Toast.makeText(getActivity(),"接口访问成功", Toast.LENGTH_SHORT).show();
            }
        }

        );
    }
}
