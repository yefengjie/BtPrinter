package com.yefeng.night.btprinter.util;

/**
 * Created by yee on 2/25/14.
 * @author yee
 */
public class TextUtil {

    public static boolean isEmpty(String s) {
        if (android.text.TextUtils.isEmpty(s)) {
            return true;
        }
        return "null".equals(s);
    }

}
