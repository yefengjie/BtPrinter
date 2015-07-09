package com.yefeng.night.btprinter.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by yefeng on 6/15/15.
 * github:yefengfreedom
 */
public class ImageUtils {

    /**
     * 按照指定宽度缩放图片
     *
     * @param bitmap 原始图片
     * @param width  指定宽度
     * @return 缩放后的图片
     */
    public static Bitmap zoomBitmapByWidth(Bitmap bitmap, int width) {
        if (width <= 0 || null == bitmap) {
            return bitmap;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float scale = (float) width / (float) w;
        if (scale <= 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        try {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return bitmap;
        }
        return bitmap;
    }

}
