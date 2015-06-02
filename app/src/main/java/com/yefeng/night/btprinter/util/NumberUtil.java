package com.yefeng.night.btprinter.util;

/**
 * Created by yee on 11/21/13.
 *
 * @author yee
 */
public class NumberUtil {

    /**
     * format double data
     *
     * @param d double
     * @return string
     */
    public static String formatDouble(Double d) {
        if (0 == d) {
            return "0";
        } else if (d % 1 == 0) {
            return new java.text.DecimalFormat("0").format(d);
        } else {
            return new java.text.DecimalFormat("0.0").format(d);
        }
    }

    /**
     * format float data
     *
     * @param f float
     * @return string
     */
    public static String formatFloat(float f) {
        if (0 == f) {
            return "0";
        } else if (f % 1 == 0) {
            return new java.text.DecimalFormat("0").format(f);
        } else {
            return new java.text.DecimalFormat("0.0").format(f);
        }
    }

    /**
     * format double data
     *
     * @param d double
     * @return string
     */
    public static String formatDoubleKeepFourDecimal(Double d) {
        if (0 == d) {
            return "0";
        } else if (d % 1 == 0) {
            return new java.text.DecimalFormat("0").format(d);
        } else {
            return new java.text.DecimalFormat("0.0000").format(d);
        }
    }

}
