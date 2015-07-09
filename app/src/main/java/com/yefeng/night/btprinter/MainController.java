package com.yefeng.night.btprinter;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.text.TextUtils;

import com.yefeng.night.btprinter.print.PrintUtil;

/**
 * Created by yefeng on 7/9/15.
 * github:yefengfreedom
 */
public class MainController {

    public static void init(MainActivity activity) {
        if (null == activity.mAdapter) {
            activity.mAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (null == activity.mAdapter) {
            activity.txtPrinterTitle.setText("该设备没有蓝牙模块");
            activity.imgPrinter.setImageResource(R.drawable.ic_bluetooth_off);
            activity.mBtEnable = false;
            return;
        }
        if (!activity.mAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, MainActivity.OPEN_BLUETOOTH_REQUEST);
            activity.showToast("正在打开蓝牙");
            activity.txtPrinterTitle.setText("蓝牙未打开");
            activity.imgPrinter.setImageResource(R.drawable.ic_bluetooth_off);
            return;
        }
        String address = PrintUtil.getDefaultBluethoothDeviceAddress(activity.getApplicationContext());
        if (TextUtils.isEmpty(address)) {
            activity.txtPrinterTitle.setText("尚未绑定蓝牙设备");
            activity.imgPrinter.setImageResource(R.drawable.ic_bluetooth_off);
            return;
        }
        String name = PrintUtil.getDefaultBluetoothDeviceName(activity.getApplicationContext());
        activity.txtPrinterTitle.setText("已绑定蓝牙：" + name);
        activity.txtPrinterSummary.setText(address);
        activity.imgPrinter.setImageResource(R.drawable.ic_bluetooth_device_connected);
    }
}
