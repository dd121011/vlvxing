package com.qunar.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wjjunjjun.wang on 2017/7/25.
 */
public class Md5Util {

//    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static String encode(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String md5Result = null;
        if(null == data){
            return md5Result;
        }

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte b[] = md.digest();
            System.out.println("去哪儿网 MD5加密之后 :" + b.length);
            int i;
            StringBuffer sb = new StringBuffer("");
            for(int offset = 0; offset < b.length; offset ++){
                i = b[offset];
                if(i< 0){
                    i += 256;
                }
                if(i < 16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            System.out.println("去哪儿网 MD5加密之后并转化32位小写 :" + sb.toString());
            md5Result = sb.toString();
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }
        return md5Result;
    }

}
