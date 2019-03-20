package com.aibabel.menu.util;

import android.util.Log;

import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.baselibrary.utils.ServerKeyUtils;
import com.aibabel.menu.bean.Domain;
import com.aibabel.menu.bean.ServerBean;
import com.tencent.mmkv.MMKV;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by fytworks on 2019/3/16.
 */

public class ServerUtils {

    /**
     * 获取当前时区
     * @return  1国内服务器，0国外服务器
     */
    public static int getTimerType(){
        try{
            String timerID = TimeZone.getDefault().getID();

            if (timerID.equals("Asia/Shanghai")){
                Log.e("SERVICE_FUWU","时区:"+1);
                return 1;
            }else{
                Log.e("SERVICE_FUWU","时区:"+0);
                return 0;
            }
        }catch (Exception e){
            Log.e("SERVICE_FUWU","获取时区报错");
        }
        return 0;
    }
    public static MMKV mk = null;
    public static void saveAllServer(ServerBean serverBean){
        mk = MMKV.defaultMMKV();
        DataManager.getInstance().setSaveString("_com_aibabel_alliedclock_joner",getSerVerInfo(serverBean.default_com_aibabel_alliedclock_joner, ServerKeyUtils.serverKeyAlliedClockJoner));
        DataManager.getInstance().setSaveString("_com_aibabel_chat_joner",getSerVerInfo(serverBean.default_com_aibabel_chat_joner,ServerKeyUtils.serverKeyChatJoner));
        DataManager.getInstance().setSaveString("_com_aibabel_coupon",getSerVerInfo(serverBean.default_com_aibabel_coupon,ServerKeyUtils.serverKeyAibabelCoupon));
        DataManager.getInstance().setSaveString("_com_aibabel_currencyconversion_joner",getSerVerInfo(serverBean.default_com_aibabel_currencyconversion_joner,ServerKeyUtils.serverKeyCurrencyconVersionJoner));
        DataManager.getInstance().setSaveString("_com_aibabel_currencyconversion_joner",getSerVerInfo(serverBean.default_com_aibabel_ddot,ServerKeyUtils.serverKeyAibabelDDot));
        DataManager.getInstance().setSaveString("_com_aibabel_desktop",getSerVerInfo(serverBean.default_com_aibabel_desktop,ServerKeyUtils.serverKeyAibabelDesktop));
        DataManager.getInstance().setSaveString("_com_aibabel_dictionary_joner",getSerVerInfo(serverBean.default_com_aibabel_dictionary_joner,ServerKeyUtils.serverKeyDictionaryJoner));
        DataManager.getInstance().setSaveString("_com_aibabel_enterandexit",getSerVerInfo(serverBean.default_com_aibabel_enterandexit,ServerKeyUtils.serverKeyEnterAndexit));
        DataManager.getInstance().setSaveString("_com_aibabel_food",getSerVerInfo(serverBean.default_com_aibabel_food,ServerKeyUtils.serverKeyAibabelFood));
        DataManager.getInstance().setSaveString("_com_aibabel_locationservice_joner",getSerVerInfo(serverBean.default_com_aibabel_locationservice_joner,ServerKeyUtils.serverKeyLocationService));
        DataManager.getInstance().setSaveString("_com_aibabel_map",getSerVerInfo(serverBean.default_com_aibabel_map,ServerKeyUtils.serverKeyAibabelMap));
        DataManager.getInstance().setSaveString("_com_aibabel_ocr_camera",getSerVerInfo(serverBean.default_com_aibabel_ocr_camera,ServerKeyUtils.serverKeyOcrCamera));
        DataManager.getInstance().setSaveString("_com_aibabel_ocr_ocr",getSerVerInfo(serverBean.default_com_aibabel_ocr_ocr,ServerKeyUtils.serverKeyOcrOcr));
        DataManager.getInstance().setSaveString("_com_aibabel_ocrobject_object",getSerVerInfo(serverBean.default_com_aibabel_ocrobject_object,ServerKeyUtils.serverKeyOcrObject));
        DataManager.getInstance().setSaveString("_com_aibabel_play",getSerVerInfo(serverBean.default_com_aibabel_play,ServerKeyUtils.serverKeyAibabelPlay));
        DataManager.getInstance().setSaveString("_com_aibabel_speech_function",getSerVerInfo(serverBean.default_com_aibabel_speech_function,ServerKeyUtils.serverKeySpeechFunction));
        DataManager.getInstance().setSaveString("_com_aibabel_speech_pa",getSerVerInfo(serverBean.default_com_aibabel_speech_pa,ServerKeyUtils.serverKeySpeechPa));
        DataManager.getInstance().setSaveString("_com_aibabel_surfinternet_joner",getSerVerInfo(serverBean.default_com_aibabel_surfinternet_joner,ServerKeyUtils.serverKeySurfinternet));
        DataManager.getInstance().setSaveString("_com_aibabel_surfinternet_pay",getSerVerInfo(serverBean.default_com_aibabel_surfinternet_pay,ServerKeyUtils.serverKeySurfinternetPay));
        DataManager.getInstance().setSaveString("_com_aibabel_translate_function",getSerVerInfo(serverBean.default_com_aibabel_translate_function,ServerKeyUtils.serverKeyTranslateFunction));
        DataManager.getInstance().setSaveString("_com_aibabel_travel_joner",getSerVerInfo(serverBean.default_com_aibabel_travel_joner,ServerKeyUtils.serverKeyTravelJoner));
        DataManager.getInstance().setSaveString("_com_aibabel_traveladvisory_joner",getSerVerInfo(serverBean.default_com_aibabel_traveladvisory_joner,ServerKeyUtils.serverKeyTravelAdvisory));
        DataManager.getInstance().setSaveString("_com_aibabel_weather_joner",getSerVerInfo(serverBean.default_com_aibabel_weather_joner,ServerKeyUtils.serverKeyEeatherJoner));
    }

    /**
     * 对服务器列表进行整合
     * @param infoVer
     * @return
     */
    public static String getSerVerInfo(List<Domain> infoVer, String key){
        String infos = "";
        for (int i = 0; i < infoVer.size() ; i ++){
            infos += infoVer.get(i).domain+",";
        }
        //直接进行存储
        saveDefaultService(infos.substring(0,infos.length() - 1),key);
        return infos.substring(0,infos.length() - 1);
    }

    /**
     * 对服务器进行初始化 存储
     * 以当前时区进行存储
     * [0] 国外服务器
     * [1] 国内服务器
     * [2] 国内备用服务器
     * ...
     * @param infos
     * @param key
     */
    public static void saveDefaultService(String infos, String key) {
        String[] infosServer = infos.split(",");
        if (mk != null){
            mk.encode(key,infosServer[ServerUtils.getTimerType()]);
        }
        Log.e("SERVICE_FUWU","当前APP存储的域名MMKV--:"+key+":"+mk.decodeString(key));
    }

}
