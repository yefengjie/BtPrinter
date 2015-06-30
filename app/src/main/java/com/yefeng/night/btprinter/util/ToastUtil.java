package com.yefeng.night.btprinter.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by yefeng on 6/2/15.
 * github:yefengfreedom
 */
public class ToastUtil {

    public static void showToast(Context context, String message) {
        if (null != context && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Context context, int message) {
        if (null != context && message != 0) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
