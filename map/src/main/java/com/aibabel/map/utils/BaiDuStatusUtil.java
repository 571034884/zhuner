package com.aibabel.map.utils;

import android.content.Context;

import com.aibabel.baselibrary.utils.ToastUtil;

/**
 *
 *
 * Created by fytworks on 2018/12/18.
 */

public class BaiDuStatusUtil {

    /**
     *
     *  国内
     *
     *  根据百度的错误码进行Toast
     *  驾车 0 1 2 7
     *  步行 0 1 2 7
     *  公交 0 1 2 1001 1002
     *
     * @param mContext
     * @param status
     */
    public static void getBaiDuStatusZh(Context mContext, int status){
        switch (status){
            case 0:
                //成功，不会出现，
                break;
            case 1:
                ToastUtil.showShort(mContext,"服务内部错误");
                break;
            case 2:
                ToastUtil.showShort(mContext,"参数无效");
                break;
            case 7:
                ToastUtil.showShort(mContext,"没有返回结果");
                break;
            case 1001:
                ToastUtil.showShort(mContext,"没有公交方案");
                break;
            case 1002:
                ToastUtil.showShort(mContext,"没有匹配的POI");
                break;
        }
    }

    /**
     *
     *  国外
     *
     *  根据百度的错误码进行Toast
     *  驾车 0 1 2 7 3001
     *  步行 0 1 2 2001 3001
     *  公交 0 1 2 1001 3001
     *
     * @param mContext
     * @param status
     */
    public static void getBaiDuStatusEn(Context mContext, int status){
        switch (status){
            case 0:
                //成功，不会出现，
                break;
            case 1:
                ToastUtil.showShort(mContext,"服务内部错误");
                break;
            case 2:
                ToastUtil.showShort(mContext,"参数无效");
                break;
            case 7:
                ToastUtil.showShort(mContext,"没有返回结果");
                break;
            case 1001:
                ToastUtil.showShort(mContext,"没有公交方案");
                break;
            case 2001:
                ToastUtil.showShort(mContext,"无步行路线");
                break;
            case 3001:
                ToastUtil.showShort(mContext,"暂不支持该出行方式");
                break;
        }
    }

}
