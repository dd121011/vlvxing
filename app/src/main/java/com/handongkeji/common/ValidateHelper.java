package com.handongkeji.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  验证工具类
 */
public class ValidateHelper {

    // 手机号
    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern p = Pattern.compile("^0?(13[0-9]|15[012356789]|18[0123456789]|14[57]|177|17[0-9])[0-9]{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();

    }

    // 固定电话
    public static boolean isPhoneValid(String phoneNumber) {
        Pattern p = Pattern.compile("^0\\d{2,3}\\d{5,9}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    // 银行卡
    public static boolean isBankCardValid(String phoneNumber) {
        Pattern p = Pattern.compile("^(\\d{16}|\\d{19})$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();

    }


    // 网址
    public static boolean isWebUrlValid(String webUrl) {
        Pattern p = Pattern.compile("^((https|http|ftp|rtsp|mms)?://)" + "?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*\'()-]+.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$");

        // Pattern p =
        // Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
        // Pattern p =
        // Pattern.compile("^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",
        // Pattern.CASE_INSENSITIVE);

        Matcher m = p.matcher(webUrl);
        return m.matches();
    }

    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 校验身份证
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        if (null==idCard || "".equals(idCard)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");//复杂匹配
        Matcher m = p.matcher(idCard);
        return m.matches();
    }
}
