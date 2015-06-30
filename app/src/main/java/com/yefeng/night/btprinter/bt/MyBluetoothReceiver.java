package com.yefeng.night.btprinter.bt;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.yefeng.night.btprinter.print.PrintQueue;

import de.greenrobot.event.EventBus;

/**
 * Created by yefeng on 6/2/15.
 * github:yefengfreedom
 * <p>
 * bluetooth receiver
 */
public class MyBluetoothReceiver extends BroadcastReceiver {
    public MyBluetoothReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        try {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                EventBus.getDefault().post(new BtMsgEvent(BtMsgType.BLUETOOTH_STATUS_CHANGE, intent));
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                if (state == BluetoothAdapter.STATE_ON) {
                    PrintQueue.getQueue(context).tryConnect();
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}
