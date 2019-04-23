package com.aibabel.statisticalserver;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.aibabel.aidlaar.StatisticsManager;

import java.util.Map;

public class SimpleStatisticsActivity extends AppCompatActivity {

    public String TAG = this.getClass().getSimpleName();

    private long inTime;
    private long outTime;
    private int interactionTimes = 0;
    private String pathParams = "";//
    public boolean repairEnable = false; //热修复请求标志
    public int repairCount = 1; //热修复请求次数
    private Map<String, String> postMap;

    public String getPathParams() {
        return pathParams;
    }

    public void setPathParams(String pathParams) {
        this.pathParams = pathParams;
    }

    //交互次数增长
    public void interactionTimesGrowth() {
        interactionTimes++;
    }

    @Override
    protected void onResume() {
        super.onResume();
        inTime = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        outTime = System.currentTimeMillis();
        interactionTimes = 0;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interactionTimesGrowth();
//                System.out.println("Activity---dispatchTouchEvent---DOWN" + interactionTimes);
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
}
