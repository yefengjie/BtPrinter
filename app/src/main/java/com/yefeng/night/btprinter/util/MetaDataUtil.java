package com.yefeng.night.btprinter.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by yee on 11/13/13.
 *
 * @author yee
 */
public class MetaDataUtil {

    public static String getMetaData(Context context, String key) {
        ApplicationInfo ai = null;
        try {
            ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != ai) {
            if (null == ai.metaData) {
                return "";
            }
            Object value = ai.metaData.get(key);
            if (null != value) {
                return value.toString();
            }
        }
        return "";
    }
}
