package com.yefeng.night.btprinter;

import android.app.IntentService;
import android.content.Intent;

import com.yefeng.night.btprinter.print.GPrinterCommand;
import com.yefeng.night.btprinter.print.PrintQueue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by yefeng on 6/2/15.
 * github:yefengfreedom
 * <p/>
 * print ticket service
 */
public class BtService extends IntentService {

    public BtService() {
        super("BtService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BtService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        testPrinter();
    }

    void testPrinter() {
        try {
            ArrayList<byte[]> bytes = new ArrayList<byte[]>();
            String message = "蓝牙打印测试\n蓝牙打印测试\n蓝牙打印测试\n\n";
            bytes.add(message.getBytes("gbk"));
            bytes.add(GPrinterCommand.print);
            bytes.add(GPrinterCommand.print);
            bytes.add(GPrinterCommand.print);
            PrintQueue.getQueue(getApplicationContext()).add(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}