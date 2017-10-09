package com.qunar.service;

//import com.google.common.base.Joiner;
//import com.qunar.utils.HttpClientUtil;
import com.qunar.utils.Md5Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjjunjjun.wang on 2017/7/25.
 */
public class RequestService {

//    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    private static final String KEY = "0748cebf8cf346cf64070333c7fed08f";   //从开发者平台获取
    private static final String TOKEN = "109cfbc62855bdc711a68c77c3e6bd97"; //从开发者平台获取
    private static final String BASE_URL = "http://qae.qunar.com/api/router?";


//    private Joiner.MapJoiner mapJoiner = Joiner.on("&").withKeyValueSeparator("=");

    public static String doRequest(String tag, String params) {
        String result = null;
        try {
            Map<String, String> paramsMap = buildParamMap(tag, params);
            String url = buildRequsetUrl(paramsMap);
            System.out.println("去哪儿网机票接口测试:"+url);
//            System.out.println("request url:" + url);
//            result = HttpClientUtil.doGet(url);
        } catch (Exception e) {
//            logger.error("do request error", e);
        }
        return result;
    }

    public static Map<String, String> buildParamMap(String tag, String params) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> paramMap = new HashMap<String, String>();
        String createTime = String.valueOf(new Date().getTime());
        String sign = getSign(tag, params, createTime);
        paramMap.put("sign", sign);
        paramMap.put("createTime", createTime);
        paramMap.put("token", TOKEN);
        paramMap.put("tag", tag);
        paramMap.put("params", URLEncoder.encode(params, "utf-8"));
        paramMap.put("debug", "true");
        return paramMap;
    }

    public static String buildRequsetUrl(Map<String, String> paramsMap) {
        String params = "";
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            params += entry.getKey()+"=" + entry.getValue()+"&";
        }
        return BASE_URL + params;
    }

    public static String getSign(String tag, String params, String createTime) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<String> requestParams = new ArrayList<String>();
//        {"arr":"SHA","createTime":"1411442340","dpt":"PEK","date":"2015-12-21","ex_track":"youxuan", "tag":"flight.national.supply.sl.searchflight","token":"109cfbc62855bdc711a68c77c3e6bd97"}
        requestParams.add("tag=" + tag);
        requestParams.add("params=" + params);
        requestParams.add("key=" + KEY);
        requestParams.add("token=" + TOKEN);
        requestParams.add("createTime=" + createTime);
        Collections.sort(requestParams);
        String beforeMd5Str = linkStr(requestParams);
        String sign = Md5Util.encode(beforeMd5Str);
        System.out.println("before md5 str:" + beforeMd5Str);
        return sign;
    }

    public static String linkStr(List<String> requestParams) {
        StringBuilder sb = new StringBuilder();
        for (String param : requestParams) {
            sb.append(param);
        }
        return sb.toString();
    }

}
