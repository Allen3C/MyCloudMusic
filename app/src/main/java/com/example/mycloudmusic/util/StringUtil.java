package com.example.mycloudmusic.util;

import static com.example.mycloudmusic.util.Constant.REGEX_EMAIL;
import static com.example.mycloudmusic.util.Constant.REGEX_PHONE;

/**
 * 字符串相关工具类
 */
public class StringUtil {
    /**
     * 是否是手机号
     * @param value
     * @return
     */
    public static boolean isPhone(String value){
        return value.matches((REGEX_PHONE));
    }

    /**
     * 是否符合邮箱格式
     * @param value
     * @return
     */
    public static boolean isEmail(String value){
        return value.matches((REGEX_EMAIL));
    }

    /**
     * 是否符合密码格式
     * @param value
     * @return
     */
    public static boolean isPassword(String value) {
        return value.length() >= 6 && value.length() <= 15;
    }
}
