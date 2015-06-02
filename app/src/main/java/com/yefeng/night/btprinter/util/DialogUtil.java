package com.yefeng.night.btprinter.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * Created by yee on 3/24/14.
 * dialog util
 */
public class DialogUtil {

    /**
     * show a dialog with a simple confirm button
     *
     * @param activity activity
     */
    public static void showInfoDialog(Activity activity, String title, String message) {
        if (null == activity) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);
        if (!TextUtils.isEmpty(title)) {
            alertDialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            alertDialog.setMessage(message);
        }
        alertDialog.show();
    }

    /**
     * show a dialog with a simple confirm button
     *
     * @param activity activity
     */
    public static void showInfoDialog(Activity activity, int title, int message) {
        if (null == activity) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);
        if (-1 != title) {
            alertDialog.setTitle(title);
        }
        if (-1 != message) {
            alertDialog.setMessage(activity.getResources().getString(message));
        }
        alertDialog.show();
    }

    /**
     * show a dialog with a simple confirm button
     *
     * @param activity activity
     */
    public static void showInfoDialog(Activity activity, String title, String message, String button, DialogInterface.OnClickListener onClickListener) {
        if (null == activity) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);
        if (!TextUtils.isEmpty(title)) {
            alertDialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            alertDialog.setMessage(message);
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, button, onClickListener);
        alertDialog.show();
    }

}
