package com.yefeng.night.btprinter.bt;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by yefeng on 6/2/15.
 * github:yefengfreedom
 */
public class BtUtil {
    /**
     * 判断蓝牙是否打开
     *
     * @return boolean
     */
    public static boolean isOpen(BluetoothAdapter adapter) {
        if (null != adapter) {
            return adapter.isEnabled();
        }
        return false;
    }

    /**
     * 搜索蓝牙设备
     */
    public static void searchDevices(BluetoothAdapter adapter) {
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        if (null != adapter) {
            adapter.startDiscovery();
        }
    }

    /**
     * 取消搜索蓝牙设备
     */
    public static void cancelDiscovery(BluetoothAdapter adapter) {
        if (null != adapter) {
            adapter.cancelDiscovery();
        }
    }

}
