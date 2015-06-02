package com.yefeng.night.btprinter.bt;

import android.content.Intent;

/**
 * Created by yefeng on 6/1/15.
 * github:yefengfreedom
 */
public class BtMsgEvent {

    public int type;
    public Intent intent;

    public BtMsgEvent(int type, Intent intent) {
        this.type = type;
        this.intent = intent;
    }
}
