package com.aibabel.map.utils;

import java.math.BigDecimal;

/**
 * Created by fytworks on 2018/11/21.
 */

public class MathUtil {

    /**
     * 除法 保留小数
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确小数
     * @return  值
     */
    public static double mathDivision(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
