package com.yefeng.night.btprinter.base;

import android.app.Activity;
import android.os.Bundle;


import com.yefeng.night.btprinter.util.ToastUtil;

import de.greenrobot.event.EventBus;


/**
 * Created by night on 5/27/15.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(String msg) {
        showToast(msg);
    }

    public void showToast(String message) {
        ToastUtil.showToast(this, message);
    }

    public void showToast(int message) {
        ToastUtil.showToast(this, message);
    }
}
