package com.yefeng.night.btprinter.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yee on 12/5/13.
 */
public class KeyboardUtil {

    public static void closeKeyboard(Activity activity) {
        InputMethodManager mInputMethodManager;
        mInputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void openKeyboard(final Activity activity) {
//        //得到InputMethodManager的实例
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        //如果关闭
//        if (!imm.isActive()) {
//            //开启软键盘，关闭方法相同，这个方法是切换开启与关闭状态的
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 500);  //在0.5秒后打开
    }
}
