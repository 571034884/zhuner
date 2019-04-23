package com.aibabel.baselibrary.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.bean.RepaireBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.impl.KillSelfListener;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.taobao.sophix.SophixManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/11/13 17:26
 * 功能：
 * 版本：1.0
 */
public abstract class BaseActivity extends StatisticsBaseActivity  {
    private static List<BaseActivity> activitiesList;
    static {
        activitiesList=  Collections.synchronizedList(new ArrayList<>());
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {

            }
        });
    }



    protected boolean killedToBackground=true;
    public String TAG = this.getClass().getSimpleName();
    public Unbinder mUnbinder;
    public Context mContext;
    private boolean isCurrentPhysicalButtonsExitEnable = true;
    public boolean repairEnable = false; //热修复请求标志
    public int repairCount = 1; //热修复请求次数

    private long inTime;
    private long outTime;
    private int interactionTimes = 0;
    private String pathParams = "";

    private FragmentListener fragmentListener;

    public static final int ADD = 1;
    public static final int REPLACE = 2;

    private boolean stateFlag = false;//状态栏是否显示标志 默认不显示


    public boolean isStateFlag() {
        return stateFlag;
    }

    /**
     * 设置状态来状态
     *
     * @param stateFlag
     */
    public void setStateFlag(boolean stateFlag) {
        this.stateFlag = stateFlag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("life==","onCreate"+getClass().getName());
        mContext = this;
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setNavigationBarVisibility();

        int getlayout = getLayout(savedInstanceState);

        Log.e("hjs","BaseAcitivyt="+getlayout);
        if(getlayout>0) {
            setContentView(getlayout);
            mUnbinder = ButterKnife.bind(this);
            init();
        }

        //mUnbinder = ButterKnife.bind(this);



        if (repairEnable && CommonUtils.getRequestHotRepaireEnable(repairCount)) {
            Map<String, String> map = new HashMap<>();
            map.put("app", getPackageName());
            OkGoUtil.<RepaireBean>get(false, "/v1/jonersystem/", "GetAppNew", map, RepaireBean
                    .class, new BaseCallback<RepaireBean>() {
                @Override
                public void onSuccess(String method, RepaireBean model, String resoureJson) {
                    if (model.getData().isIsNew()) {
                        int count = SharePrefUtil.getInt(BaseApplication.mApplication,
                                "requestCount", 0);
                        SharePrefUtil.saveLong(BaseApplication.mApplication, "requestDay", new
                                Date().getTime());
                        SharePrefUtil.saveInt(BaseApplication.mApplication, "requestCount",
                                ++count);
                        Log.e(TAG, "onSuccess: " + "今天第" + count + "次请求热修复");
                        SophixManager.getInstance().queryAndLoadNewPatch();
                    }
                }

                @Override
                public void onError(String method, String message, String resoureJson) {
                    Log.e(TAG, "onError: " + message);
                }

                @Override
                public void onFinsh(String method) {

                }
            });
        }

       activitiesList.add(this);
    }

    //交互次数增长
    public void interactionTimesGrowth() {
        interactionTimes++;
    }

    public String getPathParams() {
        return pathParams;
    }

    public void setPathParams(String pathParams) {
        this.pathParams = pathParams;
    }

    /**
     * 设置当前页面是否响应物理按键回到桌面
     *
     * @return
     */
    public boolean isCurrentPhysicalButtonsExitEnable() {
        return isCurrentPhysicalButtonsExitEnable;
    }

    public void setCurrentPhysicalButtonsExitEnable(boolean currentPhysicalButtonsExitEnable) {
        isCurrentPhysicalButtonsExitEnable = currentPhysicalButtonsExitEnable;
    }
    public void setKilledToBackground(boolean killedToBackground) {
        this.killedToBackground = killedToBackground;
    }
    /**
     * 设置当前页面是否需要请求更新
     *
     * @param hotRepairEnable 能否请求热修复，默认请求次数
     */
    public void setHotRepairEnable(boolean hotRepairEnable) {
        setHotRepairEnable(hotRepairEnable, repairCount);
    }

    /**
     * 设置当前页面是否需要请求更新
     *
     * @param hotRepairEnable 能否请求热修复
     * @param hotRepairCount  最大每天请求次数
     */
    public void setHotRepairEnable(boolean hotRepairEnable, int hotRepairCount) {
        repairEnable = hotRepairEnable;
        repairCount = hotRepairCount;
    }

    /**
     * 获取布局
     *
     * @param savedInstanceState
     * @return R.layout.xxxxLayout
     */
    public abstract int getLayout(Bundle savedInstanceState);

    /**
     * 初始化其他内容
     */
    public abstract void init();

    @Override
    protected void onResume() {

        Log.e("life==","onResume"+getClass().getName());
        super.onResume();
        inTime = System.currentTimeMillis();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        outTime = System.currentTimeMillis();
        interactionTimes = 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("life==","onStop"+getClass().getName());


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        try {
            activitiesList.remove(this);
            if(mUnbinder!=null)mUnbinder.unbind();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置导航栏显示状态
     */
    public void setNavigationBarVisibility() {
        int flag = 0;
        if (!isStateFlag()) {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                    .LayoutParams.FLAG_FULLSCREEN);// 设置全屏
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT); // 底部导航栏颜色也可以由系统设置
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(mContext, cls));
    }

    public void startActivityForResult(Class cls, int requestCode) {
        startActivityForResult(new Intent(mContext, cls), requestCode);
    }

    public void setFragmentListener(FragmentListener listener) {
        this.fragmentListener = listener;
    }

    /**
     * 布局中Fragment的ID，没有Fragment可以不做更改
     */
    public interface FragmentListener {
        int getFragmentViewId();

    }

    /**
     * 初始化fragment
     */
    protected void initFragment(BaseFragment fragment, int way) {
        if (fragmentListener == null) {
            Logger.e("未设置fragmentListener");
            return;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (way == ADD) {
                transaction.add(fragmentListener.getFragmentViewId(), fragment, fragment.getClass
                        ().getSimpleName());
            } else {
                transaction.replace(fragmentListener.getFragmentViewId(), fragment, fragment
                        .getClass().getSimpleName())
                        .addToBackStack(fragment.getClass().getSimpleName());//添加回退栈
            }
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 初始化fragment:add方式
     */
    protected void addFragment(BaseFragment fragment) {
        initFragment(fragment, ADD);
    }

    /**
     * 初始化fragment:replace方式
     */
    protected void replaceFragment(BaseFragment fragment) {
        initFragment(fragment, REPLACE);
    }

    /**
     * 移除fragment,集合replace方式，返回使用
     */
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 展示目标fragment
     *
     * @param fragment
     */
    public void showFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    /**
     * 隐藏目标fragment
     *
     * @param fragment
     */
    public void hideFragment(BaseFragment fragment) {
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    /**
     * 获取已经存在的fragment
     *
     * @param fragment
     * @return
     */
    public BaseFragment getExistingFragment(BaseFragment fragment) {
        return (BaseFragment) getSupportFragmentManager().findFragmentByTag(fragment.getClass()
                .getSimpleName());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (killedToBackground){
           switch (keyCode) {
               case 133:
                   exitAPP();
               case 134:
                  if (!getPackageName().equals("com.aibabel.ocr")){
                      exitAPP();
                  }

//                   android.os.Process.killProcess(android.os.Process.myPid());
           }
       }

        return super.onKeyDown(keyCode, event);
    }
   private void exitAPP(){
       onPause();
       onStop();
       Iterator<BaseActivity> iterator=activitiesList.iterator();
       while (iterator.hasNext()){
           BaseActivity baseActivity=iterator.next();
           baseActivity.finish();
           iterator.remove();
       }

       activitiesList.clear();
       System.exit(0);
   }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interactionTimesGrowth();
                System.out.println("Activity---dispatchTouchEvent---DOWN" + interactionTimes);
                break;
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("Activity---dispatchTouchEvent---MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                System.out.println("Activity---dispatchTouchEvent---UP");
//                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }



//    /**
//     * 添加自定义事件
//     * @param eventId 自定义事件id
//     * @param parameters   自定义事件参数
//     * @param hardwareButton
//     */
//    protected void addStatisticsEvent(String eventId, HashMap<String, Serializable> parameters, boolean hardwareButton){
//
//    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Activity---onTouchEvent---DOWN");
                break;
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("Activity---onTouchEvent---MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                System.out.println("Activity---onTouchEvent---UP");
//                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


}
