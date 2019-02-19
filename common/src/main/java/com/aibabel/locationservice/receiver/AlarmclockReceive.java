package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.locationservice.alarm.AlarmManagerUtil;
import com.aibabel.locationservice.bean.LocationBean;
import com.aibabel.locationservice.bean.ResultBean;
import com.aibabel.locationservice.map.MapUtils;
import com.aibabel.locationservice.utils.CommonUtils;
import com.aibabel.locationservice.utils.Constants;
import com.aibabel.locationservice.utils.ContentProviderUtil;
import com.aibabel.locationservice.utils.FastJsonUtil;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2018/11/9
 *
 * @Desc：定时广播，接受定时广播启动请求服务器。20181024过时了
 *==========================================================================================
 */
public class AlarmclockReceive extends BroadcastReceiver {
    private String TAG = AlarmclockReceive.class.getSimpleName().toString();

    private LocationBean locationBean = new LocationBean();
    private MyLocationListener myListener = new MyLocationListener();
    public LocationClient mLocationClient = null;
    //业务参数
    private double latitude;
    private double longitude;
    private String addr;
    private String country;
    private String province;
    private String city;
    private String district;
    private String street;
    private String locationDescribe;
    private boolean isRegisterReceiver = false;
    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        int id = intent.getIntExtra("id", 0);

        Log.e("AlarmclockReceive" + " id=" + id, "闹钟响了，可以发送请求了");

        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis, intent);
        }


        if (TextUtils.equals(intent.getAction(), Constants.ACTION_ALARM)) {
            //执行一次请求
            mLocationClient = MapUtils.getLocationClient(context, 0);
            // //注册监听函数
            mLocationClient.registerLocationListener(myListener);
            //调用LocationClient的start()方法，便可发起定位请求
            mLocationClient.start();
        }


    }


    /**
     * 注册监听
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度和地址相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            //获取详细地址信息
            addr = location.getAddrStr();
            //获取国家
            country = location.getCountry();
            //获取省份
            province = location.getProvince();
            //获取城市
            city = location.getCity();
            //获取区县
            district = location.getDistrict();
            //获取街道信息
            street = location.getStreet();
            //获取位置描述信息
            locationDescribe = location.getLocationDescribe();
//            Toast.makeText(LocationService.this, "latitude："+latitude+" longitude:"+longitude, Toast.LENGTH_SHORT).show();


            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            locationBean.setAddr(addr);
            locationBean.setCity(city);
            locationBean.setCountry(country);
            locationBean.setDistrict(district);
            locationBean.setLatitude(latitude);
            locationBean.setLocationDescribe(locationDescribe);
            locationBean.setLongitude(longitude);
            locationBean.setStreet(street);
            locationBean.setProvince(province);
//            Log.d(TAG, country+province+city+district+street);
//            Log.d(TAG, addr);
            if (null != context)
                getInformation(context);
//            Log.d(TAG, "latitude：" + latitude + " longitude:" + longitude);

        }
    }

    /**
     * 获取推送通知
     */
    public void getInformation(final Context context) {
        if (!CommonUtils.isAvailable(context)) {
            return;
        }
//        latitude = 35.698432;
//        longitude = 139.771639;
        String HOST = ContentProviderUtil.getHost(context);
        String url = HOST + "/v1/poi?lat=" + latitude + "&lng=" + longitude + "&r=" + Constants.RADIUS + "&sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocal();
        Log.e(TAG, url);
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body());
                        ResultBean bean = FastJsonUtil.changeJsonToBean(response.body().toString(), ResultBean.class);
                        if (null != bean && TextUtils.equals(bean.getData().getApk(), Constants.TRAVEL)) {
                            Intent broadcastIntent = new Intent(Constants.ACTION_TRAVEL);
                            Bundle bundle = new Bundle();
                            bundle.putString("data", FastJsonUtil.changListToString(bean.getData().getInfo()));
                            broadcastIntent.putExtras(bundle);
                            context.sendBroadcast(broadcastIntent);
//                            Log.d(TAG, "发广播了");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        Log.d(TAG, "AlarmclockReceive网络请求失败了！1");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mLocationClient.stop();
                        mLocationClient.unRegisterLocationListener(myListener);
//                        Log.e("1111111","-------------------");
                    }
                });
    }


    private void sendLocationBroadcast(LocationBean bean, Context context) {
        Intent broadcastIntent = new Intent(Constants.ACTION_LOCATION);
        Bundle bundle = new Bundle();
        bundle.putString("location", FastJsonUtil.changObjectToString(bean));
        broadcastIntent.putExtras(bundle);
        context.sendBroadcast(broadcastIntent);
    }

    /**
     * 根据apk类型选择广播
     *
     * @param apk
     * @return
     */
    private String selectBroadcastByType(String apk) {
        String action = "";
        if (TextUtils.equals(apk, "weather")) {
            action = Constants.ACTION_WEATHER;
        } else if (TextUtils.equals(apk, Constants.TRAVEL)) {
            action = Constants.ACTION_TRAVEL;
        } else if (TextUtils.equals(apk, "location")) {
            action = Constants.ACTION_LOCATION;
        } else if (TextUtils.equals(apk, "advisory")) {
            action = Constants.ACTION_TRAVEL_ADVISORY;
        }
        return action;
    }
}
