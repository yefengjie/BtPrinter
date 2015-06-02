package com.yefeng.night.btprinter.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by yee on 11/21/13.
 *
 * @author yee
 */
public class NetworkUtil {

    /**
     * 检测网络状态
     *
     * @param activity activity
     * @return 网络是否链接上
     */
    public static boolean detect(Activity activity) {
        try {
            ConnectivityManager manager = null;
            try {
                manager = (ConnectivityManager) activity
                        .getApplicationContext().getSystemService(
                                Context.CONNECTIVITY_SERVICE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (manager == null) {
                return false;
            }
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo == null || !networkinfo.isAvailable()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            //默认网络是连上的.为什么这么做?因为连上是没有任何提示的.不连上才会提示
            return true;
        }
    }

    /**
     * 获取网络类型
     *
     * @param context context
     * @return net work type
     */
    public static String getNetWorkType(Context context) {
        String mNetWorkType = "network_unknown";
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = "network_wifi";
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context) ? "network_3g" : "network_2g")
                        : "network_wap";
            }
        } else {
            mNetWorkType = "network_invalid";
        }
        return mNetWorkType;
    }

    /**
     * 判断是否是FastMobileNetWork，将3G或者3G以上的网络称为快速网络
     *
     * @param context context
     * @return 是否是快速网络
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
}
