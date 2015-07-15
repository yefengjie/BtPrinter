package com.yefeng.night.btprinter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Button;

import com.yefeng.night.btprinter.base.BaseActivity;
import com.yefeng.night.btprinter.print.PrintMsgEvent;
import com.yefeng.night.btprinter.print.PrintPic;
import com.yefeng.night.btprinter.print.PrintUtil;
import com.yefeng.night.btprinter.print.PrinterMsgType;
import com.yefeng.night.btprinter.util.ImageUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by yefeng on 7/9/15.
 * github:yefengfreedom
 * <p/>
 * 打印机器型号:WP-T630
 */
@EActivity(R.layout.activity_paint)
public class PaintActivity extends BaseActivity {

    @ViewById(R.id.dv_paint)
    DrawingView mPaintView;
    @ViewById(R.id.btn_paint_finish)
    Button mBtnFinish;

    @Click(R.id.btn_paint_clear)
    void clearPainting() {
        if (null != mPaintView) {
            mPaintView.clearPainting();
        }
    }

    @Click(R.id.btn_paint_finish)
    void finishAndPrint() {
        Bitmap bitmap = mPaintView.getBitmap();
        if (null == bitmap) {
            showToast("出错了:绘制图形获取失败");
            return;
        }
        Log.e("PaintActivity", "before bitmap :" + bitmap.getWidth() + "--" + bitmap.getHeight());
        bitmap = ImageUtils.zoomBitmapByWidth(bitmap, 200);
        Log.e("PaintActivity", "zoom bitmap :" + bitmap.getWidth() + "--" + bitmap.getHeight());
        PrintPic.getInstance().init(bitmap);
        if (bitmap.isRecycled()) {
            bitmap = null;
        } else {
            bitmap.recycle();
            bitmap = null;
        }
        showToast("图片打印中...");
        Intent intent = new Intent(getApplicationContext(), BtService.class);
        intent.setAction(PrintUtil.ACTION_PRINT_PAINTING);
        startService(intent);
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
