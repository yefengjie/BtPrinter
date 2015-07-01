package com.yefeng.night.btprinter.bt;

/**
 * Created by yefeng on 7/1/15.
 * github:yefengfreedom
 * <p/>
 * read bytes from bluetooth
 */
public class BtMsgReadEvent {

    public int bytes;
    public byte[] buffer;
    public String message;

    public BtMsgReadEvent(int bytes, byte[] buffer) {
        this.buffer = buffer;
        this.bytes = bytes;
        this.message = new String(buffer, 0, bytes);
    }
}