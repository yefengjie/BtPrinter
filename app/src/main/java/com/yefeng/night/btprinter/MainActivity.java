package com.yefeng.night.btprinter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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

    static final int OPEN_BLUETOOTH_REQUEST = 100;
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
    boolean mBtEnable = true;
    /**
     * bluetooth adapter
     */
    BluetoothAdapter mAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        MainController.init(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_BLUETOOTH_REQUEST && resultCode == Activity.RESULT_OK) {
            MainController.init(this);
        } else if (requestCode == OPEN_BLUETOOTH_REQUEST && resultCode == Activity.RESULT_CANCELED) {
            showToast("您已拒绝使用蓝牙");
            finish();
        }
    }

    @Override
    public void btStatusChanged(Intent intent) {
        super.btStatusChanged(intent);
        MainController.init(this);
    }

    @Click(R.id.ll_printer_setting_change_device)
    void bondPrinter() {
        if (mBtEnable) {
            startActivity(new Intent(getApplicationContext(), BondBtActivity_.class));
            finish();
        }
    }

    @Click(R.id.btn_printer_setting_test)
    void printTest() {
        showToast("打印测试...");
        Intent intent = new Intent(getApplicationContext(), BtService.class);
        intent.setAction(PrintUtil.ACTION_PRINT_TEST);
        startService(intent);
    }

    @Click(R.id.btn_printer_setting_test_bitmap)
    void printBitmap() {
        showToast("打印图片中...");
        Intent intent = new Intent(getApplicationContext(), BtService.class);
        intent.setAction(PrintUtil.ACTION_PRINT_BITMAP);
        startService(intent);
    }

    @Click(R.id.btn_printer_setting_test_draw_picture)
    void printDrawPicture() {
        startActivity(new Intent(getApplicationContext(), PaintActivity_.class));
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
