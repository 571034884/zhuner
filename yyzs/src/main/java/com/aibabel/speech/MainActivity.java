package com.aibabel.speech;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;


import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.utils.ServerKeyUtils;
import com.aibabel.speech.util.CommonUtils;
import com.aibabel.speech.util.HostUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.aibabel.speech.activity.ViewSpotDetailActivity;
import com.aibabel.speech.adapter.MyAdapter;
import com.aibabel.speech.adapter.MyAdapter1;
import com.aibabel.speech.adapter.ViewHolder;
import com.aibabel.speech.app.BaseApplication;
import com.aibabel.speech.audio.AudioRecordUtil;
import com.aibabel.speech.baidu.BdMap;
import com.aibabel.speech.baidu.activity.LocationDemo;
import com.aibabel.speech.baidu.activity.RoutePlanDemo;
import com.aibabel.speech.base.BaseActivity;
import com.aibabel.speech.bean.ByteDataBean;
import com.aibabel.speech.bean.JsonDataBean;
import com.aibabel.speech.bean.ViewspotBean;
import com.aibabel.speech.presenter.BindDataPresenter;
import com.aibabel.speech.properites.Constants;
import com.aibabel.speech.tcp.SocketManger;
import com.aibabel.speech.util.L;
import com.aibabel.speech.util.NetUtil;
import com.aibabel.speech.util.SystemUtil;
import com.aibabel.speech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity implements AudioRecordUtil.OnDealwithListener, SocketManger.OnReceiveListener, TextToSpeech.OnInitListener {

    //保持话轮所带参数
    /**
     * appid: 9197699
     * ak: P9oYB0NzbHaoiFe3MmccBgYz
     * sk: RfM4FAyVNLmXra1rnaeEBpAGw7nMPyFu
     */

    //录音机

    AudioRecordUtil util = AudioRecordUtil.getInstance();

    //tts语音
    public TextToSpeech tts;

    //
    private LinearLayout main_ll;


    //listview
    public ListView listView;
    public MyAdapter myAdapter;
    public List<String> mDataList;
    Map<String, Integer> mapLayoutId;


    public WebView webView;
    //底部动画
    public static FrameLayout frame_amin;
    public static AnimationDrawable frameAnim;


    public MapView mMapView;
    private BaiduMap mBaiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker = null;

    //绑定数据的业务类
    private BindDataPresenter bdp;

    //    public static WaveView wv;
    private EditText editText;
    private TextView myTv;
    private LinearLayout my_ll;

    private int count = 0;


    private Timer timerTTS;
    private TimerTask timerTaskTSS;
    private boolean isFirst = false;

    private Callback.Cancelable post;

    private boolean isNospeak = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    //初始化成功的话，设置语音，这里我将它设置为中文
                    if (status == TextToSpeech.SUCCESS) {
                        tts.setLanguage(Locale.CHINA);
                    }
                }
            });
            StatisticsManager.getInstance(this).addEventAidl(2000);

        } catch (Exception e) {

        }
        bdp = new BindDataPresenter(this);
        //选择服务器
//        changeUrl();
        try {
            String res = getIntent().getStringExtra("isFirst");
            getIntent().putExtra("isFirst", "false");
            if (res.equals("true")) {
                isFirst = true;
                Log.e("onCreate", "diyici---------------" + res);
            }
        } catch (Exception e) {
        }

        if (BaseApplication.tishi_num == 1) {
            BaseApplication.tishi_num = 0;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakShiyong("请长按语音秘书键和准儿对话");
                }
            }, 500);

        }


    }

    @Override
    protected void onRestart() {
        L.e("onRestart=====================================");
//       changeUrl();
        super.onRestart();
    }


    //去定位获取一次   语音识别和意图识别的  服务器地址
    public void changeUrl() {
        String url = HostUtil.getServerHost("pa");
        if (!url.equals("")) {
            Constants.getInstance().setBaseUrl(url);
            L.e("----------------url:" + Constants.getInstance().getBaseUrl());
        }

        String shibieUrl = HostUtil.getServerHost("function");
        if (!shibieUrl.equals("")) {
            try {
                String[] arr = shibieUrl.split(":");
                SocketManger.getInstance().setIP(arr[0]);
                SocketManger.getInstance().setPORT(Integer.valueOf(arr[1]));
                if (!arr[0].equals(SocketManger.getInstance().getIP())) {
                    SocketManger.getInstance().reInitConfig(arr[0], Integer.valueOf(arr[1]));
                }

            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            String res = intent.getStringExtra("isFirst");

            intent.putExtra("isFirst", "false");
            if (res.equals("true")) {
                isFirst = true;
                Log.e("onNewIntent", "diyici---------------" + res);
            }

        } catch (Exception e) {
        }
        StatisticsManager.getInstance(this).addEventAidl(2000);

    }

    @Override
    protected void assignView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        main_ll = findViewById(R.id.main_ll);
        listView = findViewById(R.id.main_listview);
        frame_amin = findViewById(R.id.main_framelayout);

        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_huxi);
        frame_amin.setBackgroundDrawable(frameAnim);
        frameAnim.start();


    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initListener() {

        SocketManger.getInstance().initConfig();
        SocketManger.getInstance().setOnReceiveListener(this);
        AudioRecordUtil.getInstance().setOnDealwithListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        BdMap.getInstance().init(getApplicationContext());

        //listview子项布局
        mDataList = new ArrayList<>();
        mapLayoutId = new HashMap<>();
        mapLayoutId.put("txt", R.layout.item_result_txt);
        mapLayoutId.put("my", R.layout.item_my_speech);
        mapLayoutId.put("train", R.layout.item_result_train);
        mapLayoutId.put("music", R.layout.item_result_music);
        mapLayoutId.put("map", R.layout.item_result_map);
        mapLayoutId.put("poi", R.layout.item_result_search_list);
        mapLayoutId.put("select", R.layout.item_select_setout);
        mapLayoutId.put("viewspot", R.layout.item_viewspot_list);
        mapLayoutId.put("count", R.layout.item_result_count);
        mapLayoutId.put("rate", R.layout.item_result_exchange_rate);


    }

    ////////////////////////////////////////////监听回调////////////////////////////////////////////////////////

    /**
     * 录音机发送回调
     *
     * @param msgBytes
     */
    @Override
    public void dealwithEachOne(byte[] msgBytes) {

        //正在录音  未压缩的数据回调

    }

    @Override
    public void dealwithSpxInEachOne(byte[] speexBytes) {

        SocketManger.getInstance().sendMessage(new ByteDataBean(52, speexBytes));


    }

    @Override
    public void dealwithComplete() {
        SocketManger.getInstance().sendMessage(new JsonDataBean(53, ""));


    }

//    @Override
//    public void dealwithAll(List<byte[]> list) {
//
//    }
//    @Override
//    public void dealwithFile(File file) {
//
//    }

    /*数据成功和失败*/
    @Override
    public void onSuccess(int flag, String json) {

        L.e("-------------" + flag);
        String res = "";
        if (flag == 55) {
            playAnim(4);
            stopTimerTTS();
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(json);
            res = jsonObject.getString("info");

            SocketManger.getInstance().disconnect();
            String id = jsonObject.getString("id");
            if (!("" + count).equals(id)) {
                return;
            }
            if (post != null) {
                post.cancel();
            }
            bdp.cancelPost();
            again(res);

        } else if (flag == -1) {
            stopTimerTTS();
            speakNo();
        }
    }

    @Override
    public void onError(int flag) {

        HostUtil.sendErrorServer(ServerKeyUtils.serverKeySpeechFunctionError);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.CHINA);
        }
    }

    @Override
    protected void onPause() {
        L.e("onPause----------------");
        if (timerTTS != null) {
            timerTTS.cancel();
        }
        if (tts != null) {
            isNospeak = false;
            tts.setPitch(0);
            tts.stop();
        }

        util.stopRecord();


        if (mMapView != null) {
            mMapView.onPause();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNospeak = true;
        L.e("onResume------------------");
        if (mMapView != null) {
            mMapView.onResume();
        }
        if (isFirst) {
            ping();
        }
    }

    @Override
    protected void onStop() {
        L.e("onStop------------------");
        SocketManger.getInstance().disconnect();

        if (timerTTS != null) {
            timerTTS.cancel();
        }
        if (tts != null) {
            isNospeak = false;
            tts.setPitch(0);
            tts.stop();
        }

        util.stopRecord();


        if (bdp != null) {
            bdp.onStop();
        }
        L.e("onstop");
        super.onStop();

    }

    @Override
    protected void onStart() {


        super.onStart();
    }


    /**
     * Map数据绑定在子布局上
     *
     * @param t
     * @param holder
     */

    public void bindMapItem(String t, final ViewHolder holder) {
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(t);
        if (jsonObject.getString("code").equals("005_3")) {
            com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");
            final com.alibaba.fastjson.JSONObject body = data.getJSONObject("body");
            String answer = data.getString("answer");
            ((TextView) holder.getView(R.id.item_result_map_tv)).setText(answer);
            speakTTS(answer);
            ((TextView) holder.getView(R.id.item_result_map_to)).setText(body.getString("user_nav_to"));
            ((TextView) holder.getView(R.id.item_result_map_form)).setText("出发点:" + body.getString("user_nav_from"));

            mMapView = (MapView) holder.getView(R.id.item_result_map_mmap);
            mBaiduMap = mMapView.getMap();
            mBaiduMap.setMyLocationEnabled(true);

            BdMap.getInstance().setSearchListern(new BdMap.SearchListern() {
                @Override
                public void returnSearch(GeoCodeResult result) {

                    LatLng ll = result.getLocation();
                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(7.0f)
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(0).latitude(ll.latitude)
                            .longitude(ll.longitude).build();
                    L.e("--------------" + ll.latitude + "--" + ll.longitude);

                    mBaiduMap.setMyLocationData(locData);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);

                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                    View child = mMapView.getChildAt(1);
                    if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
                        child.setVisibility(View.INVISIBLE);
                    }
                    //隐藏地图上比例尺   禁止所有触摸事件
                    mMapView.showScaleControl(false);
                    mMapView.showZoomControls(false);
                    mBaiduMap.getUiSettings().setAllGesturesEnabled(false);
                    mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                }
            });


            BdMap.getInstance().getmSearch().geocode(new GeoCodeOption()
                    .city(BdMap.getInstance().getCity(mContext))
                    .address(body.getString("user_nav_to")));
            ((RelativeLayout) holder.getView(R.id.item_result_map_rl)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RoutePlanDemo.class);
                    intent.putExtra("f", body.getString("user_nav_from"));
                    intent.putExtra("t", body.getString("user_nav_to"));
                    startActivity(intent);
                }
            });
            ((TextView) holder.getView(R.id.item_result_map_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RoutePlanDemo.class);
                    intent.putExtra("f", body.getString("user_nav_from"));
                    intent.putExtra("t", body.getString("user_nav_to"));
                    startActivity(intent);
                }
            });

        } else if (jsonObject.getString("code").equals("005_2")) {
            com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");
            final com.alibaba.fastjson.JSONObject body = data.getJSONObject("body");
            String answer = data.getString("answer");
            ((TextView) holder.getView(R.id.item_result_map_tv)).setText(answer);
            speakTTS(answer);
            ((TextView) holder.getView(R.id.item_result_map_to)).setText(body.getString("user_nav_to"));
            ((TextView) holder.getView(R.id.item_result_map_form)).setText("出发点:" + BdMap.getInstance().getmAddress());

            mMapView = (MapView) holder.getView(R.id.item_result_map_mmap);
            mBaiduMap = mMapView.getMap();
            mBaiduMap.setMyLocationEnabled(true);


            LatLng ll = new LatLng(BdMap.getInstance().getLat(mContext), BdMap.getInstance().getLong(mContext));
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(7.0f)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(ll.latitude)
                    .longitude(ll.longitude).build();
            L.e("--------------" + ll.latitude + "--" + ll.longitude);

            mBaiduMap.setMyLocationData(locData);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);

            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            View child = mMapView.getChildAt(1);
            if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
                child.setVisibility(View.INVISIBLE);
            }
            //隐藏地图上比例尺   禁止所有触摸事件
            mMapView.showScaleControl(false);
            mMapView.showZoomControls(false);
            mBaiduMap.getUiSettings().setAllGesturesEnabled(false);
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;


            BdMap.getInstance().getmSearch().geocode(new GeoCodeOption()
                    .city(BdMap.getInstance().getCity(mContext))
                    .address(body.getString("user_nav_to")));

            mMapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RoutePlanDemo.class);
                    intent.putExtra("f", BdMap.getInstance().getmAddress());
                    intent.putExtra("t", body.getString("user_nav_to"));
                    startActivity(intent);
                }
            });
            ((TextView) holder.getView(R.id.item_result_map_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RoutePlanDemo.class);
                    intent.putExtra("f", BdMap.getInstance().getmAddress());
                    intent.putExtra("t", body.getString("user_nav_to"));
                    startActivity(intent);
                }
            });

        } else if (jsonObject.getString("code").equals("005_0")) {

            com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");

            String answer = data.getString("answer");
            ((TextView) holder.getView(R.id.item_result_map_tv)).setText(answer);
            speakTTS(answer);
            ((TextView) holder.getView(R.id.item_result_map_to)).setText("正在获取当前位置");
            ((TextView) holder.getView(R.id.item_result_map_form)).setText("当前");
            ((TextView) holder.getView(R.id.item_result_map_btn)).setText("当前位置");
            mMapView = (MapView) holder.getView(R.id.item_result_map_mmap);
            mBaiduMap = mMapView.getMap();
            mBaiduMap.setMyLocationEnabled(true);

            LatLng ll = new LatLng(BdMap.getInstance().getLat(mContext),
                    BdMap.getInstance().getLong(mContext));


            mBaiduMap.setMyLocationData(BdMap.getInstance().getLocData());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);

            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            View child = mMapView.getChildAt(1);
            if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
                child.setVisibility(View.INVISIBLE);
            }
            //隐藏地图上比例尺   禁止所有触摸事件
            mMapView.showScaleControl(false);
            mMapView.showZoomControls(false);
            mBaiduMap.getUiSettings().setAllGesturesEnabled(false);
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
            mMapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LocationDemo.class);
                    startActivity(intent);
                }
            });

            ((TextView) holder.getView(R.id.item_result_map_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LocationDemo.class);
                    startActivity(intent);
                }
            });
        } else if (jsonObject.getString("code").equals("005")) {

            com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");

            String answer = data.getString("answer");
            ((TextView) holder.getView(R.id.item_result_map_tv)).setText(answer);
            speakTTS(answer);
            ((TextView) holder.getView(R.id.item_result_map_to)).setText("正在获取当前位置");
            ((TextView) holder.getView(R.id.item_result_map_form)).setText("当前");
            ((TextView) holder.getView(R.id.item_result_map_btn)).setText("当前位置");
            mMapView = (MapView) holder.getView(R.id.item_result_map_mmap);
            mBaiduMap = mMapView.getMap();
            mBaiduMap.setMyLocationEnabled(true);

            LatLng ll = new LatLng(BdMap.getInstance().getLat(mContext),
                    BdMap.getInstance().getLong(mContext));
            ;

            mBaiduMap.setMyLocationData(BdMap.getInstance().getLocData());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);

            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            View child = mMapView.getChildAt(1);
            if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
                child.setVisibility(View.INVISIBLE);
            }
            //隐藏地图上比例尺   禁止所有触摸事件
            mMapView.showScaleControl(false);
            mMapView.showZoomControls(false);
            mBaiduMap.getUiSettings().setAllGesturesEnabled(false);
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
            mMapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LocationDemo.class);
                    startActivity(intent);
                }
            });

            ((TextView) holder.getView(R.id.item_result_map_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LocationDemo.class);
                    startActivity(intent);
                }
            });
        }

    }


    /**
     * 景点介绍
     *
     * @param t
     * @param holder
     */
    public void bindViewSpot(String t, ViewHolder holder) {
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(t);
        com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");
        ((TextView) holder.getView(R.id.item_viewspot_list_answer)).setText(data.getString("answer"));
        speakTTS(data.getString("answer"));
        String type = data.getString("type");
        if (type.equals("spot_list")) {
            List<ViewspotBean> list = new ArrayList<>();
            JSONArray body = data.getJSONArray("body");
            for (int i = 0; i < body.size(); i++) {
                ViewspotBean bean = new ViewspotBean();
                bean.setName(body.getJSONObject(i).getString("name"));
                bean.setRegion(body.getJSONObject(i).getString("region"));
                bean.setAddress(body.getJSONObject(i).getString("address"));
                bean.setSpot(body.getJSONObject(i).getString("spot"));
                bean.setDescription(body.getJSONObject(i).getString("description"));
                bean.setTips(body.getJSONObject(i).getString("tips"));
                bean.setFeatured(body.getJSONObject(i).getString("featured"));
                list.add(bean);

            }
            ListView listView = holder.getView(R.id.item_viewspot_list_listview);
            listView.setAdapter(new MyAdapter1<ViewspotBean>(mContext, list, R.layout.item_child_viewspot_list) {
                @Override
                public void convert(ViewHolder holder, final ViewspotBean viewspotBean) {
                    ((TextView) holder.getView(R.id.item_child_viewspot_list_tv)).setText(viewspotBean.getName() + "    (" + viewspotBean.getRegion() + ")");
                    ((LinearLayout) holder.getView(R.id.item_child_viewspot_list_ll)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ViewSpotDetailActivity.class);
                            intent.putExtra("bean", JSON.toJSONString(viewspotBean));
                            startActivity(intent);

                        }
                    });
                }
            });
            Utility.setListViewHeightBasedOnChildren(listView);

            if (list.size() > 0) {
                ((TextView) holder.getView(R.id.item_viewspot_list_loading)).setVisibility(View.GONE);
            } else {
                ((TextView) holder.getView(R.id.item_viewspot_list_loading)).setText("没有搜索结果");
            }

        } else {

            List<ViewspotBean> list = new ArrayList<>();
            JSONArray body = data.getJSONArray("body");
            for (int i = 0; i < body.size(); i++) {
                ViewspotBean bean = new ViewspotBean();
                bean.setName(body.getJSONObject(i).getString("name"));
                bean.setRegion(body.getJSONObject(i).getString("region"));
                bean.setAddress(body.getJSONObject(i).getString("address"));
                bean.setSpot(body.getJSONObject(i).getString("spot"));
                bean.setDescription(body.getJSONObject(i).getString("description"));
                bean.setTips(body.getJSONObject(i).getString("tips"));
                bean.setFeatured(body.getJSONObject(i).getString("featured"));
                list.add(bean);


            }
            ListView listView = holder.getView(R.id.item_viewspot_list_listview);
            listView.setAdapter(new MyAdapter1<ViewspotBean>(mContext, list, R.layout.item_child_viewspot_list) {
                @Override
                public void convert(ViewHolder holder, final ViewspotBean viewspotBean) {
                    ((TextView) holder.getView(R.id.item_child_viewspot_list_tv)).setText(viewspotBean.getName() + "    (" + viewspotBean.getRegion() + ")");
                    ((LinearLayout) holder.getView(R.id.item_child_viewspot_list_ll)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ViewSpotDetailActivity.class);
                            intent.putExtra("bean", JSON.toJSONString(viewspotBean));
                            startActivity(intent);

                        }
                    });
                }
            });
            Utility.setListViewHeightBasedOnChildren(listView);

            if (list.size() > 0) {
                ((TextView) holder.getView(R.id.item_viewspot_list_loading)).setVisibility(View.GONE);
            } else {
                ((TextView) holder.getView(R.id.item_viewspot_list_loading)).setText("没有搜索结果");
            }

        }


    }


    /**
     * 选择出发位置
     *
     * @param t
     * @param holder
     */
    public void bindSelectOutsetItem(String t, ViewHolder holder) {
        com.alibaba.fastjson.JSONObject bean = JSON.parseObject(t);
        com.alibaba.fastjson.JSONObject beanChild = bean.getJSONObject("data");
        JSONArray body = beanChild.getJSONArray("body");
        L.e(body.toString());
        String answer = beanChild.getString("answer");
        ((TextView) holder.getView(R.id.item_result_select_setout_tv)).setText(answer);

        speakTTS(answer);

        final TextView my = ((TextView) holder.getView(R.id.item_result_select_setout_my));
        my.setText(body.getString(0));
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataList.clear();
                mDataList.add(my.getText().toString());
                myAdapter.fresh(mDataList);
                again(my.getText().toString());

            }
        });
        final TextView other = ((TextView) holder.getView(R.id.item_result_select_setout_other));
        other.setText(body.getString(1));
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataList.clear();
                mDataList.add(other.getText().toString());
                myAdapter.fresh(mDataList);
                again(other.getText().toString());
            }
        });


    }


    /**
     * @param res
     */
    public void shibie(String res) {

        RequestParams rp = new RequestParams(Constants.getInstance().getIntentionUrl());
        rp.addBodyParameter("id", BaseApplication.user_id);
        if (res.lastIndexOf("。") != -1) {
            res = res.substring(0, res.length() - 1);
        }
        rp.addBodyParameter("text", res);
        rp.addBodyParameter("location", BdMap.getInstance().getLocation(mContext));
        rp.addBodyParameter("city", BdMap.getInstance().getCity(mContext));
        rp.addBodyParameter("country", BdMap.getInstance().getCountry(mContext));
        String isEnd = BaseApplication.is_dialog_end;
        rp.addBodyParameter("is_dialog_end", isEnd);
        BaseApplication.is_dialog_end = null;

        L.e("发送is_dialog_end：" + isEnd);

        x.http().post(rp, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                myAdapter.loadOne(result);
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                L.e("json--------------------------------" + jsonObject.getString("code") + ":" + jsonObject.getString("data"));


                if (jsonObject.containsKey("bot_session")) {
                    BaseApplication.bot_session = jsonObject.getString("bot_session");
                }

                if (jsonObject.containsKey("is_dialog_end")) {
                    BaseApplication.is_dialog_end = jsonObject.getString("is_dialog_end");
                    L.e("接收is_dialog_end--------" + BaseApplication.is_dialog_end);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e("onError----------" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (tts != null) {
            tts.shutdown();
        }

        if (webView != null) {
            webView.removeAllViews();
            webView = null;
        }
        if (BdMap.getInstance() != null) {
            BdMap.getInstance().destroy();

        }
        if (bdp != null) {
            bdp.onDestory();
        }
        if (SocketManger.getInstance() != null) {

            SocketManger.getInstance().disconnect();
        }
        if (mMapView != null) {
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
        }

    }


    private boolean shortPress = false;

    /**
     * 录音键 长按 时的 操作 。
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == 133) {
            changeUrl();
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            if (!SystemUtil.isScreenLocked(mContext)) {
                L.e("是黑屏" + SystemUtil.isScreenLocked(mContext));
                return true;
            }
            shortPress = false;
            ping();
            // TODO: 2018/5/24
            L.e("长按");
            playAnim(2);
            SocketManger.getInstance().connect();
            //停止正在播放的音乐
            stopTimerTTS();

            count++;
            hideInput();
            mDataList.clear();
            listView.setAdapter(null);
            editText = null;
            tts.stop();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", count + "");
                jsonObject.put("f", "ch_ch");
                jsonObject.put("t", "en");
                jsonObject.put("audio", "mp3");
                jsonObject.put("speed", "speed");
                jsonObject.put("gender", "m");
                jsonObject.put("dev", BaseApplication.user_id);
                jsonObject.put("flag", "1");
                jsonObject.put("count", "0");

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            changeUrl();
            SocketManger.getInstance().sendMessage(new JsonDataBean(51, jsonObject.toString()));
            util.startRecord(true);
            return true;
        }


        return false;
    }

    /**
     * 录音键 按下 时的操作
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        stopTimerTTS();
        if (keyCode == 133) {

            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                event.startTracking();
                if (event.getRepeatCount() == 0) {
                    shortPress = true;
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 录音键 抬起的 事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 133) {
            if (!SystemUtil.isScreenLocked(mContext)) {
                L.e("是黑屏" + SystemUtil.isScreenLocked(mContext));
                return true;
            }
            L.e("抬起");
            if (!NetUtil.isNetworkConnected(mContext)) {
                speakError("当前无网络!!!");
                util.stopRecord();
                // TODO: 2018/11/23
//                wv.setChange(0);

                return true;
            }
            startTTimerTTS();
            if (shortPress) {
//                Toast.makeText(this, "shortPress", Toast.LENGTH_LONG).show();
            } else {
                //// TODO: 2018/5/2

                playAnim(3);
                SocketManger.getInstance().setResponseTimeout(3000);
                util.stopRecord();


                mDataList.clear();
                listView.setAdapter(null);

            }
            shortPress = false;
            return true;
        }


        return super.onKeyUp(keyCode, event);
    }


    public void again(String res) {
        editText = null;
        mDataList.clear();
        mDataList.add(res);
        listView.setAdapter(null);
        listView.setAdapter(myAdapter = new MyAdapter(mContext, mDataList, mapLayoutId) {
            @Override
            public void convert(final ViewHolder holder, String t) {
                try {
                    if (t.trim().equals("") && t != null) {
                        bdp.bindTxtItem("", holder, tts);
                        return;
                    }

                    if (t.indexOf("{") != -1) {
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(t);
                        String code = jsonObject.getString("code");
                        if (code.equals("001")) {

                            bindViewSpot(t, holder);

                        } else if (code.equals("001_1")) {
                            bindViewSpot(t, holder);

                        } else if (code.equals("002")) {

                            bdp.bindTxtItem(t, holder, tts);

                        } else if (code.equals("003")) {

                            bdp.bindTxtItem(t, holder, tts);

                        } else if (code.equals("003_0")) {

                            bdp.bindSearchListItem(t, holder);

                        } else if (code.equals("003_1")) {

                            bdp.bindSearchListItem(t, holder);

                        } else if (code.equals("004")) {

                            bdp.bindTxtItem(t, holder, tts);


                        } else if (code.equals("005")) {
                            //百度地图
                            bindMapItem(t, holder);


                        } else if (code.equals("006")) {

                            bdp.bindTxtItem(t, holder, tts);

                        } else if (code.equals("007")) {

                            webView = (WebView) holder.getView(R.id.item_result_train_webview);
                            bdp.bindTrainItem(t, holder, tts, webView);


                        } else if (code.equals("008")) {

                            bdp.bindExchangeRateItem(t, holder, tts);


                        } else if (code.equals("009")) {
                            webView = (WebView) holder.getView(R.id.item_result_train_webview);
                            bdp.bindTrainItem(t, holder, tts, webView);

                        } else if (code.equals("010")) {

                            bdp.bindTxtItem(t, holder, tts);


                        } else if (code.equals("011")) {
                            //相声戏曲
//                            bindMusicItem(t, holder);

                        } else if (code.equals("012")) {
                            bdp.bindTxtItem(t, holder, tts);


                        } else if (code.equals("013")) {
                            webView = (WebView) holder.getView(R.id.item_result_train_webview);
                            bdp.bindTrainItem(t, holder, tts, webView);

                        } else if (code.equals("014")) {
                            bdp.bindCountItem(t, holder, tts);


                        } else if (code.equals("015")) {
                            //

                        } else if (code.equals("016")) {
                            //音乐
//                            bindMusicItem(t, holder);

                        } else if (code.equals("005_3")) {
                            bindMapItem(t, holder);

                        } else if (code.equals("005_0")) {
                            bindMapItem(t, holder);

                        } else if (code.equals("005_2")) {
                            bindMapItem(t, holder);

                        } else if (code.equals("666")) {
                            bindSelectOutsetItem(t, holder);

                        } else if (code.equals("222")) {
                            bdp.bindTxtItem(t, holder, tts);

                        } else if (code.equals("111")) {

                            bdp.bindTxtItem(t, holder, tts);

                        } else {

                            bdp.bindTxtItem(t, holder, tts);

                        }
                    } else {

                        myTv = ((TextView) holder.getView(R.id.item_my_speech_tv));
                        my_ll = ((LinearLayout) holder.getView(R.id.item_my_speech_ll));
                        editText = ((EditText) holder.getView(R.id.item_my_speech_et));
                        myTv.setText(t);
                        my_ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editText.setText(myTv.getText().toString());
                                editText.setVisibility(View.VISIBLE);
                                myTv.setVisibility(View.GONE);

                            }
                        });


                        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_SEARCH) {


                                    count++;
                                    tts.stop();

                                    again(editText.getText().toString());
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                                    return true;
                                }
                                return false;
                            }
                        });

                    }

                } catch (Exception e) {
                    speakNo();
                }
            }
        });


        if (res.trim().equals("")) {

            speakNo();

//            tts.speak("没有准儿听懂", TextToSpeech.QUEUE_ADD, null);


        } else {

            L.e("---------------------" + Constants.getInstance().getIntentionUrl());
            RequestParams rp = new RequestParams(Constants.getInstance().getIntentionUrl());


            rp.addBodyParameter("id", BaseApplication.user_id);
            if (res.lastIndexOf("。") != -1) {
                res = res.substring(0, res.length() - 1);
            }
            rp.addBodyParameter("text", res);
            rp.addBodyParameter("location", BdMap.getInstance().getLocation(mContext));
            rp.addBodyParameter("city", BdMap.getInstance().getCity(mContext));
            rp.addBodyParameter("country", BdMap.getInstance().getCountry(mContext));
            rp.addBodyParameter("bot_session", BaseApplication.bot_session);
            String isEnd = BaseApplication.is_dialog_end;
            rp.addBodyParameter("is_dialog_end", isEnd);
            L.e("发送is_dialog_end：" + isEnd);
            BaseApplication.is_dialog_end = null;


            post = x.http().post(rp, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {

                    myAdapter.loadOne(result);
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                    L.e("json--------------------------------" + jsonObject.getString("code") + ":" + jsonObject.getString("data"));
                    if (jsonObject.containsKey("bot_session")) {
                        BaseApplication.bot_session = jsonObject.getString("bot_session");
                    }
                    if (jsonObject.containsKey("is_dialog_end")) {
                        BaseApplication.is_dialog_end = jsonObject.getString("is_dialog_end");
                        L.e("接收is_dialog_end--------" + BaseApplication.is_dialog_end);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    L.e("onError----------" + ex.getMessage());
                    HostUtil.sendErrorServer(ServerKeyUtils.serverKeySpeechPaError);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        L.e("onWindowFocusChanged");
        if (isFirst) {

            L.e("onWindowFocusChanged====================进入");
            if (!SystemUtil.isScreenLocked(mContext)) {
                L.e("是黑屏" + SystemUtil.isScreenLocked(mContext));
                return;
            }
            isFirst = false;
            try {
                ping();
                SocketManger.getInstance().connect();
                BaseApplication.bot_session = "";

                mDataList.clear();
                listView.setAdapter(null);
                tts.stop();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", count + "");
                    jsonObject.put("f", "ch_ch");
                    jsonObject.put("t", "en");
                    jsonObject.put("audio", "mp3");
                    jsonObject.put("speed", "speed");
                    jsonObject.put("gender", "m");
                    jsonObject.put("dev", BaseApplication.user_id);
                    jsonObject.put("flag", "1");
                    jsonObject.put("count", "0");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SocketManger.getInstance().sendMessage(new JsonDataBean(51, jsonObject.toString()));
                util.startRecord(true);
                playAnim(2);
                CommonUtils.rexiufu(mContext);
            } catch (Exception e) {
                L.e("第一次错误");
            }
        }
    }

    /**
     * 音量计算
     *
     * @param buffer
     * @return
     */
    private double calculateVolume(byte[] buffer) {

        double sumVolume = 0.0;

        double avgVolume = 0.0;

        double volume = 0.0;

        for (int i = 0; i < buffer.length; i += 2) {

            int v1 = buffer[i] & 0xFF;

            int v2 = buffer[i + 1] & 0xFF;

            int temp = v1 + (v2 << 8);// 小端

            if (temp >= 0x8000) {

                temp = 0xffff - temp;
            }
            sumVolume += Math.abs(temp);
        }
        avgVolume = sumVolume / buffer.length / 2;
        volume = Math.log10(1 + avgVolume) * 20;
        return volume;

    }

    public void speakNo() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                listView.setAdapter(null);
                mDataList.clear();
//
                mDataList.add("准儿没有听明白，可以再说一次吗?");
                listView.setAdapter(new MyAdapter1<String>(mContext, mDataList, R.layout.item_result_txt) {
                    @Override
                    public void convert(ViewHolder holder, String t) {
                        ((TextView) holder.getView(R.id.item_result_txt_tv)).setText(t);
                        speakTTS(t);
                        playAnim(1);
                    }
                });
            }
        });

    }

    public void speakError(final String res) {

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                listView.setAdapter(null);
                mDataList.clear();
                mDataList.add(res);
                listView.setAdapter(new MyAdapter1<String>(mContext, mDataList, R.layout.item_result_txt) {
                    @Override
                    public void convert(ViewHolder holder, String t) {
                        ((TextView) holder.getView(R.id.item_result_txt_tv)).setText(t);
                        if (isNospeak) {
                            speakTTS(t);
                        }

                    }
                });

            }
        });

    }


    public void speakShiyong(final String res) {

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                listView.setAdapter(null);
                mDataList.clear();
                mDataList.add(res);
                listView.setAdapter(new MyAdapter1<String>(mContext, mDataList, R.layout.item_result_txt) {
                    @Override
                    public void convert(final ViewHolder holder, final String t) {

                        try {
                            tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    //初始化成功的话，设置语音，这里我将它设置为中文
                                    if (status == TextToSpeech.SUCCESS) {
                                        tts.setLanguage(Locale.CHINA);
                                        ((TextView) holder.getView(R.id.item_result_txt_tv)).setText(t);
                                        speakTTS(t);
                                    }
                                }
                            });

                        } catch (Exception e) {

                        }


                    }
                });

            }
        });

    }

    public void stopTimerTTS() {
        try {
            timerTTS.cancel();

        } catch (Exception e) {

        }
    }

    public void startTTimerTTS() {
        try {
            if (timerTTS != null) {
                timerTTS.cancel();
            }

            timerTTS = new Timer();
            timerTaskTSS = new TimerTask() {
                @Override
                public void run() {
                    speakNo();
                }
            };
            timerTTS.schedule(timerTaskTSS, 3000);
            Intent intent = new Intent(mContext, MainActivity.class);


        } catch (Exception e) {

        }
    }

    public void ping() {
        RequestParams requestParams = new RequestParams(Constants.getInstance().getIntentionUrl());
//        RequestParams requestParams = new RequestParams("http://192.168.5.242:3389");
        final long start = System.currentTimeMillis();
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                L.e("pin通-------------");
                long end = System.currentTimeMillis();
                if ((end - start) > 30 * 1000) {

                    speakError("当前网络不佳!!!");
                    playAnim(1);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e("pin失败--------------" + ex.getMessage());
                long end = System.currentTimeMillis();
                L.e("--------" + (end - start));
                if (ex.getMessage().equals("timeout")) {
                    speakError("当前网络不佳!!!");
                    playAnim(1);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (hideKeyboard()) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean hideKeyboard() {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive(editText)) {
                L.e("隐藏键盘");
                //让某个控件获取焦点

                // TODO: 2018/11/23  把焦点放到动画控件上面
//                wv. requestFocusFromTouch();

                imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                myTv.setText(editText.getText().toString());
                editText.setVisibility(View.GONE);
                myTv.setVisibility(View.VISIBLE);
                return true;
            }
        }


        return false;
    }


    public void hideInput() {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive(editText)) {

                // TODO: 2018/11/23  把焦点放到动画控件上面
//                wv. requestFocusFromTouch();
                imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    public void speakTTS(String answer) {
        tts.setPitch(1.0f);
        tts.speak(answer, TextToSpeech.QUEUE_ADD, null);
    }


    /**
     * 语音动画  切换
     *
     * @param index
     */
    public void playAnim(int index) {
        switch (index) {
            case 1:
                frameAnim.stop();
                frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_huxi);
                frame_amin.setBackgroundDrawable(frameAnim);
                frameAnim.start();
                break;
            case 2:
                frameAnim.stop();
                frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_shuru);
                frame_amin.setBackgroundDrawable(frameAnim);
                frameAnim.start();
                break;
            case 3:
                frameAnim.stop();
                frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_dengdai);
                frame_amin.setBackgroundDrawable(frameAnim);
                frameAnim.start();
                break;
            case 4:
                frameAnim.stop();
                frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_end);
                frame_amin.setBackgroundDrawable(frameAnim);
                frameAnim.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frameAnim.stop();
                        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_huxi);
                        frame_amin.setBackgroundDrawable(frameAnim);
                        frameAnim.start();
                    }
                }, 30 * 80);

                break;
        }
    }
}
