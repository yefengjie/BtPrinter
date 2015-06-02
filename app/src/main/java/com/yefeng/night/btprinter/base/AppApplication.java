package com.yefeng.night.btprinter.base;

import android.app.Application;

/**
 * Created by yee on 11/1/13.
 * <p/>
 */
public class AppApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AppInfo.init(getApplicationContext());
    }

}
