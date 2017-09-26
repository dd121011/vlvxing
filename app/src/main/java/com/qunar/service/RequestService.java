package com.qunar.service;

import com.qunar.utils.Md5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
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

//    public String doRequest(String tag, String params) {
//        String result = null;
//        try {
//            Map<String, String> paramsMap = buildParamMap(tag, params);
//            String url = buildRequsetUrl(paramsMap);
//            System.out.println("request url:" + url);
//            result = HttpClientUtil.doGet(url);
//        } catch (Exception e) {
//            logger.error("do request error", e);
//        }
//        return result;
//    }

//    private Map<String, String> buildParamMap(String tag, String params) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        Map<String, String> paramMap = Maps.newHashMap();
//        String createTime = String.valueOf(new Date().getTime());
//        String sign = getSign(tag, params, createTime);
//        paramMap.put("sign", sign);
//        paramMap.put("createTime", createTime);
//        paramMap.put("token", TOKEN);
//        paramMap.put("tag", tag);
//        paramMap.put("params", URLEncoder.encode(params, "utf-8"));
//        paramMap.put("debug", "true");
//        return paramMap;
//    }

//    private String buildRequsetUrl(Map<String, String> paramsMap) {
//        return BASE_URL + mapJoiner.join(paramsMap);
//    }

    public static String getSign(String tag, String params, String createTime) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuffer requestParams = new StringBuffer();
        requestParams.append("createTime="+createTime);
        requestParams.append("key="+KEY);
//        requestParams.append("params="+params);
        requestParams.append("tag="+tag);
        requestParams.append("token="+TOKEN);
        String sign = Md5Util.encode(requestParams.toString());

        System.out.println("before md5 str:" + sign);
//        logger.info("before md5 str={}, after md5 str={}", beforeMd5Str, sign);
        return sign;
    }


//    private String linkStr(List<String> requestParams) {
//        StringBuilder sb = new StringBuilder();
//        for (String param : requestParams) {
//            sb.append(param);
//        }
//        return sb.toString();
//    }

}
