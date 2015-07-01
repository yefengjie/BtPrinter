package com.yefeng.night.btprinter;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yefeng.night.btprinter.bt.BluetoothActivity;
import com.yefeng.night.btprinter.bt.BtUtil;
import com.yefeng.night.btprinter.print.PrintQueue;
import com.yefeng.night.btprinter.print.PrintUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by yefeng on 6/1/15.
 * github:yefengfreedom
 */
@EActivity(R.layout.activity_bond_bt)
public class BondBtActivity extends BluetoothActivity {

    private static final int OPEN_BLUETOOTH_REQUEST = 100;
    @ViewById
    ImageView imgBondIcon;
    @ViewById
    TextView txtBondTitle;
    @ViewById
    TextView txtBondSummary;
    @ViewById
    LinearLayout llBondSearch;
    @ViewById
    ListView listBondDevice;
    private BtDeviceAdapter deviceAdapter;
    private BluetoothAdapter bluetoothAdapter;

    @AfterViews
    void initViews() {
        if (null == deviceAdapter) {
            deviceAdapter = new BtDeviceAdapter(getApplicationContext(), null);
        }
        listBondDevice.setAdapter(deviceAdapter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        searchDeviceOrOpenBluetooth();
    }

    private void init() {
        if (null != bluetoothAdapter) {
            Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();
            if (null != deviceSet) {
                deviceAdapter.addDevices(new ArrayList<BluetoothDevice>(deviceSet));
            }
        }
        if (!BtUtil.isOpen(bluetoothAdapter)) {
            txtBondTitle.setText("未连接蓝牙打印机");
            txtBondSummary.setText("系统蓝牙已关闭,点击开启");
            imgBondIcon.setImageResource(R.drawable.ic_bluetooth_off);
        } else {
            if (!PrintUtil.isBondPrinter(this, bluetoothAdapter)) {
                //未绑定蓝牙打印机器
                txtBondTitle.setText("未连接蓝牙打印机");
                txtBondSummary.setText("点击后搜索蓝牙打印机");
                imgBondIcon.setImageResource(R.drawable.ic_bluetooth_off);
            } else {
                //已绑定蓝牙设备
                txtBondTitle.setText(getPrinterName() + "已连接");
                String blueAddress = PrintUtil.getDefaultBluethoothDeviceAddress(this);
                if (TextUtils.isEmpty(blueAddress)) {
                    blueAddress = "点击后搜索蓝牙打印机";
                }
                txtBondSummary.setText(blueAddress);
                imgBondIcon.setImageResource(R.drawable.ic_bluetooth_device_connected);
            }
        }
    }

    /**
     * search device, if bluetooth is closed, open it
     */
    private void searchDeviceOrOpenBluetooth() {
        if (!BtUtil.isOpen(bluetoothAdapter)) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, OPEN_BLUETOOTH_REQUEST);
        } else {
            BtUtil.searchDevices(bluetoothAdapter);
        }
    }

    private String getPrinterName() {
        String dName = PrintUtil.getDefaultBluetoothDeviceName(this);
        if (TextUtils.isEmpty(dName)) {
            dName = "未知设备";
        }
        return dName;
    }

    @Override
    protected void onStop() {
        super.onStop();
        BtUtil.cancelDiscovery(bluetoothAdapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deviceAdapter = null;
        bluetoothAdapter = null;
    }

    @Click(R.id.ll_bond_search)
    void searchDevice() {
        searchDeviceOrOpenBluetooth();
    }

    @ItemClick(R.id.list_bond_device)
    void bondDevice(final int position) {
        if (null == deviceAdapter) {
            return;
        }
        final BluetoothDevice bluetoothDevice = deviceAdapter.getItem(position);
        if (null == bluetoothDevice) {
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("绑定" + getPrinterName(bluetoothDevice.getName()) + "?")
                .setMessage("点击确认绑定蓝牙设备")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            BtUtil.cancelDiscovery(bluetoothAdapter);
                            PrintUtil.setDefaultBluetoothDeviceAddress(getApplicationContext(), bluetoothDevice.getAddress());
                            PrintUtil.setDefaultBluetoothDeviceName(getApplicationContext(), bluetoothDevice.getName());
                            if (null != deviceAdapter) {
                                deviceAdapter.setConnectedDeviceAddress(bluetoothDevice.getAddress());
                            }
                            if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                                init();
                                goPrinterSetting();
                            } else {
                                Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                                createBondMethod.invoke(bluetoothDevice);
                            }
                            PrintQueue.getQueue(getApplicationContext()).disconnect();
                            String name = bluetoothDevice.getName();
                        } catch (Exception e) {
                            e.printStackTrace();
                            PrintUtil.setDefaultBluetoothDeviceAddress(getApplicationContext(), "");
                            PrintUtil.setDefaultBluetoothDeviceName(getApplicationContext(), "");
                            showToast("蓝牙绑定失败,请重试");
                        }
                    }
                })
                .create()
                .show();
    }

    private String getPrinterName(String dName) {
        if (TextUtils.isEmpty(dName)) {
            dName = "未知设备";
        }
        return dName;
    }

    /**
     * go printer setting activity
     */
    private void goPrinterSetting() {
        Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void btStartDiscovery(Intent intent) {
        txtBondTitle.setText("正在搜索蓝牙设备…");
        txtBondSummary.setText("");
    }

    @Override
    public void btFinishDiscovery(Intent intent) {
        txtBondTitle.setText("搜索完成");
        txtBondSummary.setText("点击重新搜索");
    }

    @Override
    public void btStatusChanged(Intent intent) {
        init();
    }

    @Override
    public void btFoundDevice(Intent intent) {
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if (null != deviceAdapter && device != null) {
            deviceAdapter.addDevices(device);
        }
    }

    @Override
    public void btBondStatusChange(Intent intent) {
        init();
        if (PrintUtil.isBondPrinter(getApplicationContext(), bluetoothAdapter)) {
            goPrinterSetting();
        }
    }

    @Override
    public void btPairingRequest(Intent intent) {
        showToast("正在绑定打印机");
    }
}
