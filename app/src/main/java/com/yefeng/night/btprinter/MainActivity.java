package com.yefeng.night.btprinter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yefeng.night.btprinter.bt.BluetoothActivity;
import com.yefeng.night.btprinter.print.PrintMsgEvent;
import com.yefeng.night.btprinter.print.PrintUtil;
import com.yefeng.night.btprinter.print.PrinterMsgType;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by yefeng on 6/2/15.
 * github:yefengfreedom
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BluetoothActivity {

    @ViewById(R.id.img_printer_setting_icon)
    ImageView imgPrinter;
    @ViewById(R.id.txt_printer_setting_title)
    TextView txtPrinterTitle;
    @ViewById(R.id.txt_printer_setting_summary)
    TextView txtPrinterSummary;
    @ViewById(R.id.ll_printer_setting_change_device)
    LinearLayout llPrinterChangeDevice;
    @ViewById(R.id.btn_printer_setting_test)
    Button btnTest;

    private boolean mBtEnable = true;

    private static final int OPEN_BLUETOOTH_REQUEST = 100;

    /**
     * bluetooth adapter
     */
    private BluetoothAdapter mAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_BLUETOOTH_REQUEST && resultCode == Activity.RESULT_OK) {
            init();
        } else if (requestCode == OPEN_BLUETOOTH_REQUEST && resultCode == Activity.RESULT_CANCELED) {
            showToast("您已拒绝使用蓝牙");
            finish();
        }
    }

    void init() {
        if (null == mAdapter) {
            mAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (null == mAdapter) {
            txtPrinterTitle.setText("该设备没有蓝牙模块");
            imgPrinter.setImageResource(R.drawable.ic_bluetooth_off);
            mBtEnable = false;
            return;
        }
        if (!mAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, OPEN_BLUETOOTH_REQUEST);
            showToast("正在打开蓝牙");
            txtPrinterTitle.setText("蓝牙未打开");
            imgPrinter.setImageResource(R.drawable.ic_bluetooth_off);
            return;
        }
        String address = PrintUtil.getDefaultBluethoothDeviceAddress(getApplicationContext());
        if (TextUtils.isEmpty(address)) {
            txtPrinterTitle.setText("尚未绑定蓝牙设备");
            imgPrinter.setImageResource(R.drawable.ic_bluetooth_off);
            return;
        }
        String name = PrintUtil.getDefaultBluetoothDeviceName(getApplicationContext());
        txtPrinterTitle.setText("已绑定蓝牙：" + name);
        txtPrinterSummary.setText(address);
        imgPrinter.setImageResource(R.drawable.ic_bluetooth_device_connected);
    }

    @Override
    public void btStatusChanged(Intent intent) {
        super.btStatusChanged(intent);
        init();
    }

    @Click(R.id.ll_printer_setting_change_device)
    void bondPrinter() {
        if (mBtEnable) {
            startActivity(new Intent(getApplicationContext(), BondBtActivity_.class));
            finish();
        }
    }

    @Click(R.id.btn_printer_setting_test)
    void testPrinter() {
        startService(new Intent(getApplicationContext(), BtService.class));
    }

    /**
     * handle printer message
     *
     * @param event print msg event
     */
    public void onEventMainThread(PrintMsgEvent event) {
        if (event.type == PrinterMsgType.MESSAGE_TOAST) {
            showToast(event.msg);
        }
    }
}
