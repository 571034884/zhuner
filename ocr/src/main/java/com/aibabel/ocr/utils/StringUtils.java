package com.aibabel.ocr.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.ocr.MainActivity;
import com.aibabel.ocr.app.BaseApplication;
import com.aibabel.ocr.bean.LanBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class StringUtils {

    public static boolean hasChild(LanBean bean) {
        boolean isNull = false;
        if (null != bean.getChild() && bean.getChild().size() > 0) {
            isNull = true;
        }
        return isNull;
    }

    /**
     * 通过给定的regex来截取字符串
     *
     * @param str
     * @param regex
     * @return 返回字符串数组
     */
    public static String[] splitByRegex(String str, String regex) {

        String[] array = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            array = str.split(regex);
        } catch (Exception e) {
            return null;
        }


        return array;
    }


    public static int getRealWidth(int width) {
        int realW = BaseApplication.screenW;
        if (BaseApplication.screenW == 540) {
            realW = width * BaseApplication.screenW / Constant.REQUIRE_W;
        } else if (BaseApplication.screenW == 480) {
            realW = width * 480 / (480 * 720 / 540);
        }
        return realW;
    }


    /**
     * 获取真实高度
     * @param height
     * @return
     */
    public static int getRealHeight(int height) {
        int realH = BaseApplication.screenH;
        if (BaseApplication.screenW == 540) {
            realH = height * BaseApplication.screenH / Constant.REQUIRE_H;
        } else if (BaseApplication.screenW == 480) {
            realH = height * 480 / (480 * 720 / 540);
        }
        if(realH<0){
            realH=0;
        }
        return realH;
    }


    /**
     * 将定位获取的经纬度转化成字符串参数
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public static String getLocation(double latitude, double longitude) {
        String location = "";
        StringBuilder builder = new StringBuilder();
        if (latitude != 0.0 && longitude != 0.0 && latitude != 4.9E-324 && longitude != 4.9E-324) {
            location = (builder.append(latitude).append(",").append(longitude)).toString();
        }

        return location;
    }

    public static int boubleFormatInt(Double dou) {
        DecimalFormat df = new DecimalFormat("######0"); //四舍五入转换成整数
        return Integer.parseInt(df.format(dou));
    }

    public static void main(String args[]) {
//        String str="การรู้จาอักขระด้วยแสง หรือมักเรียกอย่างย่อ\nว่า โอซีอาร์คือกระบวนการทางกลไกหรือทาง\nอิเล็กทรอนิกส์เพอแปลภาพของข้อความจากการ\nเขียนหรือจากการพิมพ์ไปเป็นข้อความทีสามารถ\nแก้ไขได้โดยเครืองคอมพิวเตอร์ การจับภาพอาจ\nทําโดยเครืองสแกนเนอร์ กล้องดิจิทัล";
//        String [] array = splitByRegex(str,"\n");
//        System.out.println(array.length);
    }

    /**
     * 解决宽度超出屏幕问题
     * @param left
     * @param width
     * @param context
     * @return
     */
    public static int isExceed(int left, int width, Context context){
        int realWidth = width;
        int exceed = left+width;
        if(exceed>=DisplayUtil.getScreenWidth(context)){
            realWidth = width-(exceed-DisplayUtil.getScreenWidth(context))-3;
        }
        Log.e("realWidth","realWidth:"+realWidth+"---left:"+left+"-----+width:"+width);
        return realWidth;

    }
}
