package com.aibabel.ocr.widgets;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/3/29
 * <p>
 * 描述: 自定义相机
 * <p>
 * =====================================================================
 */

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.ocr.activity.TakePhoteActivity;
import com.aibabel.ocr.app.BaseApplication;
import com.aibabel.ocr.utils.Constant;
import com.aibabel.ocr.utils.DisplayUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, AutoFocusCallback, SensorControler.CameraFocusListener,
        IActivityLifiCycle {
    private static final String TAG = "CameraPreview";
    private String isOpenFlashMode = Camera.Parameters.FLASH_MODE_OFF;//是否开启闪光灯 默认关闭闪光灯
    private int viewWidth = 0;
    private int viewHeight = 0;
    private SensorControler mSensorControler;


    /**
     * 监听接口
     */
    private OnCameraStatusListener listener;

    private SurfaceHolder holder;
    private Camera camera;
    private Camera.Parameters parameters;
    //    private FocusView mFocusView;
    private FocusImageView mFocusView;
    private boolean isCanFocus = true;
//
//    private int mDisplayOrientation;
//    private int mLayoutOrientation;
//    private CameraOrientationListener mOrientationListener;
    /**
     * 当前屏幕旋转角度
     */
    private int mOrientation = 0;
//    private Context context;

    /**
     * 屏幕方向1竖，2横
     */
    public int direction = 1;
    public boolean vertical = true;
    private OrientationEventListener mOrEventListener;

    private Context mContext;

    // Preview类的构造方法
    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 获得SurfaceHolder对象
        holder = getHolder();
        //设置焦点对焦
        setFocusable(true);
        // 指定用于捕捉拍照事件的SurfaceHolder.Callback对象
        holder.addCallback(this);
        // 设置SurfaceHolder对象的类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //初始化传感器，便于自动对焦
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        setOnTouchListener(onTouchListener);

    }

    // 在surface创建时激发
    public void surfaceCreated(SurfaceHolder holder) {
        if (!DisplayUtil.checkCameraHardware(getContext())) {
            Toast.makeText(getContext(), "Failed to open camera！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 获得Camera对象
        camera = getCameraInstance();
        try {
            // 设置用于显示拍照摄像的SurfaceHolder对象
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
            // 释放手机摄像头
            camera.release();
            camera = null;
        }
        updateCameraParameters();
        if (camera != null) {
            camera.startPreview();
        }

    }

    // 在surface销毁时激发
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "==surfaceDestroyed==");
        // 释放手机摄像头
        camera.release();
        camera = null;
    }

    // 在surface的大小发生改变时激发
    public void surfaceChanged(final SurfaceHolder holder, int format, int w, int h) {
        // stop preview before making changes
//        try {
//            camera.stopPreview();
//        } catch (Exception e) {
//            // ignore: tried to stop a non-existent preview
//        }
//        // set preview size and make any resize, rotate or
//        // reformatting changes here
////        updateCameraParameters();
//        // start preview with new settings
//        try {
//            camera.setPreviewDisplay(holder);
//            camera.startPreview();
//
//        } catch (Exception e) {
//            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
//        }


        // 实现自动对焦
        camera.autoFocus(this);

    }


/*    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            //横屏
            camera.setDisplayOrientation(0);
        }else{
            //竖屏
            camera.setDisplayOrientation(90);
        }



    }*/

    long time;
    /**
     * 点击显示焦点区域
     */
    OnTouchListener onTouchListener = new OnTouchListener() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    // 手指离开屏幕
                case MotionEvent.ACTION_UP:
                    //设置聚焦
                    time = System.currentTimeMillis();
                    Point point = new Point((int) event.getRawX(), (int) event.getRawY() - 84);
                    onCameraFocus(point, false);
                    break;
            }

            return true;
        }
    };


    /**
     * 相机对焦
     *
     * @param point
     * @param needDelay 是否需要延时
     */
    public void onCameraFocus(final Point point, boolean needDelay) {
        long delayDuration = needDelay ? 300 : 0;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mSensorControler.isFocusLocked() && isCanFocus) {
                    if (doFocusByManual(point, CameraPreview.this)) {
                        mSensorControler.lockFocus();
                        mFocusView.startFocus(point);

                        //播放对焦音效
//                        if(mFocusSoundPrepared) {
//                            mSoundPool.play(mFocusSoundId, 1.0f, 0.5f, 1, 0, 1.0f);
//                        }
                    }
                }
            }
        }, delayDuration);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    /**
     * 手动聚焦
     *
     * @param point 触屏坐标
     */
    protected boolean doFocusByManual(Point point, Camera.AutoFocusCallback callback) {
        if (camera == null) {
            return false;
        }
        camera.cancelAutoFocus();
        Camera.Parameters parameters = null;
        try {
            parameters = camera.getParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        if (parameters.getMaxNumFocusAreas() <= 0) {
            return focus(callback);
        }


        int[] location = new int[2];
        RelativeLayout relativeLayout = (RelativeLayout) getParent();
        relativeLayout.getLocationOnScreen(location);

        Rect focusRect = DisplayUtil.calculateTapArea(mFocusView.getWidth(),
                mFocusView.getHeight(), 1f, point.x, point.y,
                location[0], location[0] + relativeLayout.getWidth(), location[1],
                location[1] + relativeLayout.getHeight());
        try {
            parameters = camera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);


            if (parameters.getMaxNumFocusAreas() > 0) {
                List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
                focusAreas.add(new Camera.Area(focusRect, 1000));

                parameters.setFocusAreas(focusAreas);
            }

            if (parameters.getMaxNumMeteringAreas() > 0) {
                List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                Rect areaRect1 = new Rect(-100, -100, 100, 100);    // specify an area in center of image
                meteringAreas.add(new Camera.Area(areaRect1, 600)); // set weight to 60%
                Rect areaRect2 = new Rect(800, -1000, 1000, -800);  // specify an area in upper right of image
                meteringAreas.add(new Camera.Area(areaRect2, 400)); // set weight to 40%
                parameters.setMeteringAreas(meteringAreas);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }


        return focus(callback);
    }


    private boolean focus(Camera.AutoFocusCallback callback) {
        try {

            camera.autoFocus(callback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 设置聚焦的图片
     *
     * @param focusView
     */

    public void setFocusView(FocusImageView focusView) {
        this.mFocusView = focusView;
    }


    /**
     * 获取摄像头实例
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera c = null;
        try {
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras(); // get cameras number

            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        c = Camera.open(camIdx);   //打开后置摄像头
                    } catch (RuntimeException e) {
                        Toast.makeText(getContext(), "Failed to open camera", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (c == null) {
                c = Camera.open(0); // attempt to get a Camera instance
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Failed to open camera", Toast.LENGTH_SHORT).show();
        }
        return c;
    }

    private void updateCameraParameters() {
        //重置对焦计数
        mSensorControler.restFoucs();
        if (camera != null) {
            Camera.Parameters p = camera.getParameters();
            setParameters(p);
            try {
                camera.setParameters(p);
            } catch (Exception e) {
                Camera.Size previewSize = findBestPreviewSize(p);
                p.setPreviewSize(previewSize.width, previewSize.height);
                p.setPictureSize(previewSize.width, previewSize.height);
                camera.setParameters(p);
            }
        }
    }

    /**
     * @param p
     */
    private void setParameters(Camera.Parameters p) {
        List<String> focusModes = p.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        //重置对焦计数
        mSensorControler.restFoucs();
        long time = new Date().getTime();
        p.setGpsTimestamp(time);
        // 设置照片格式
        List<Camera.Size> list = p.getSupportedPictureSizes();
//        for(Camera.Size size :list){
//            Log.d("uuuuuu", size.width+"X"+size.height);
//        }
        p.setPictureFormat(ImageFormat.JPEG);
        p.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        Camera.Size previewSize = findPreviewSizeByScreen(p);
//        p.setPreviewSize(previewSize.width, previewSize.height);
//        p.setPictureSize(previewSize.width, previewSize.height);
        if (BaseApplication.screenW == 540) {
            p.setPreviewSize((960 - 84) * 720 / 540, 720);
            p.setPictureSize((960 - 84) * 720 / 540, 720);
        } else if (BaseApplication.screenW == 480) {
            p.setPreviewSize(((800 - (84 * 480 / 540)) * 720 / 540), 480 * 720 / 540);
            p.setPictureSize(((800 - (84 * 480 / 540)) * 720 / 540), 480 * 720 / 540);
        }
//        p.setPreviewSize(requireW, requireH);
//        p.setPictureSize(requireW, requireH);
        Log.d(TAG, "图片宽度：" + previewSize.width + " 高度：" + previewSize.height);
        Log.d(TAG, p.getMaxZoom() + "");

        p.setFlashMode(getIsOpenFlashMode());
        p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
            p.setRotation(90);
        }

    }

    // 进行拍照，并将拍摄的照片传入PictureCallback接口的onPictureTaken方法
    public void takePicture() {
        if (camera != null) {
            try {
                mSensorControler.lockFocus();
                camera.takePicture(null, null, pictureCallback);
//                mSensorControler.unlockFocus();
            } catch (Exception exception) {
                exception.printStackTrace();
                try {
                    camera.startPreview();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //创建一个PictureCallback对象，并实现其中的onPictureTaken方法
    private PictureCallback pictureCallback = new PictureCallback() {

        // 该方法用于处理拍摄后的照片数据
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 停止照片拍摄
            try {
                camera.stopPreview();
            } catch (Exception e) {
            }
            // 调用结束事件
            if (null != listener) {
                listener.onCameraStopped(data);
            }
        }
    };

    // 设置监听事件
    public void setOnCameraStatusListener(OnCameraStatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        Map<String, String> map = new HashMap<>();
        map.put("对焦时长", System.currentTimeMillis() - time + "");
        //聚焦之后根据结果修改图片
        if (success) {
            map.put("是否成功", "成功");
            mFocusView.onFocusSuccess();
        } else {
            //聚焦失败显示的图片，由于未找到合适的资源，这里仍显示同一张图片
            mFocusView.onFocusFailed();
            map.put("是否成功", "失败");
        }
        StatisticsManager.getInstance(mContext).addEventAidl( "对焦", map);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //一秒之后才能再次对焦
                mSensorControler.unlockFocus();
            }
        }, 500);
    }

    public void start() {
        try {
            if (mSensorControler.isFocusLocked()) {
                mSensorControler.unlockFocus();
            }
            if (camera != null) {
                camera.startPreview();
                int screenWidth = BaseApplication.screenW;
                int screenHeight = BaseApplication.screenH;
                Point point = new Point(screenWidth / 2, (screenHeight / 2) - 84);
                onCameraFocus(point, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (camera != null) {
                camera.startPreview();
                int screenWidth = BaseApplication.screenW;
                int screenHeight = BaseApplication.screenH;
                Point point = new Point(screenWidth / 2, (screenHeight / 2) - 84);
                onCameraFocus(point, false);
            }
        }

    }

    public void stop() {
        try {
            if (camera != null) {
                camera.stopPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @param mIsOpenFlashMode
     * @Description: 设置开启闪光灯(重新预览)
     */
    public void setIsOpenFlashMode(boolean mIsOpenFlashMode) {
        if (mIsOpenFlashMode) {
            this.isOpenFlashMode = Camera.Parameters.FLASH_MODE_ON;
        } else {
            this.isOpenFlashMode = Camera.Parameters.FLASH_MODE_OFF;
        }
        setCameraPreView();//重新预览相机
    }

    /**
     * 获取闪光灯当前模式
     */
    public String getIsOpenFlashMode() {
        return isOpenFlashMode;
    }


    public void setCameraPreView() {
        if (camera != null) {
            camera.stopPreview();
            updateCameraParameters();
            try {
                camera.setPreviewDisplay(getHolder());
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(">>>>>", "相机初始化失败!");
        }
    }

    @Override
    public void onFocus() {
        int screenWidth = BaseApplication.screenW;
        int screenHeight = BaseApplication.screenH;
        Point point = new Point(screenWidth / 2, (screenHeight / 2) - 84);
        onCameraFocus(point, false);
    }

    @Override
    public void onActivityStart() {
        synchronized (this) {
            mSensorControler.onActivityStart();
        }
    }

    @Override
    public void onActivityStop() {
        mSensorControler.onActivityStop();
    }

    /**
     * 相机拍照监听接口
     */
    public interface OnCameraStatusListener {
        // 相机拍照结束事件
        void onCameraStopped(byte[] data);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        viewWidth = MeasureSpec.getSize(widthSpec);
        viewHeight = MeasureSpec.getSize(heightSpec);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
    }

    /**
     * 将预览大小设置为屏幕大小
     *
     * @param parameters
     * @return
     */
    private Camera.Size findPreviewSizeByScreen(Camera.Parameters parameters) {
        if (viewWidth != 0 && viewHeight != 0) {
            return camera.new Size(Math.max(viewWidth, viewHeight), Math.min(viewWidth, viewHeight));
        } else {
            return camera.new Size(DisplayUtil.getScreenWH(getContext()).heightPixels, DisplayUtil.getScreenWH(getContext()).widthPixels);
        }
    }

    /**
     * 找到最合适的显示分辨率 （防止预览图像变形）
     *
     * @param parameters
     * @return
     */
    private Camera.Size findBestPreviewSize(Camera.Parameters parameters) {

        // 系统支持的所有预览分辨率
        String previewSizeValueString = null;
        previewSizeValueString = parameters.get("preview-size-values");

        if (previewSizeValueString == null) {
            previewSizeValueString = parameters.get("preview-size-value");
        }

        if (previewSizeValueString == null) { // 有些手机例如m9获取不到支持的预览大小 就直接返回屏幕大小
            return camera.new Size(DisplayUtil.getScreenWH(getContext()).widthPixels,
                    DisplayUtil.getScreenWH(getContext()).heightPixels);
        }
        float bestX = 0;
        float bestY = 0;

        float tmpRadio = 0;
        float viewRadio = 0;

        if (viewWidth != 0 && viewHeight != 0) {
            viewRadio = Math.min((float) viewWidth, (float) viewHeight)
                    / Math.max((float) viewWidth, (float) viewHeight);
        }

        String[] COMMA_PATTERN = previewSizeValueString.split(",");
        for (String prewsizeString : COMMA_PATTERN) {
            prewsizeString = prewsizeString.trim();

            int dimPosition = prewsizeString.indexOf('x');
            if (dimPosition == -1) {
                continue;
            }

            float newX = 0;
            float newY = 0;

            try {
                newX = Float.parseFloat(prewsizeString.substring(0, dimPosition));
                newY = Float.parseFloat(prewsizeString.substring(dimPosition + 1));
            } catch (NumberFormatException e) {
                continue;
            }

            float radio = Math.min(newX, newY) / Math.max(newX, newY);
            if (tmpRadio == 0) {
                tmpRadio = radio;
                bestX = newX;
                bestY = newY;
            } else if (tmpRadio != 0 && (Math.abs(radio - viewRadio)) < (Math.abs(tmpRadio - viewRadio))) {
                tmpRadio = radio;
                bestX = newX;
                bestY = newY;
            }
        }

        if (bestX > 0 && bestY > 0) {
            return camera.new Size((int) bestX, (int) bestY);
        }
        return null;
    }


    public void setCanFocus(boolean canFocus) {
        isCanFocus = canFocus;
    }


    /**
     * Determine the current display orientation and rotate the camera preview
     * accordingly
     */
//    private void determineDisplayOrientation() {
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        Camera.getCameraInfo(0, cameraInfo);
//
//        int rotation = context.getWindowManager().getDefaultDisplay().getRotation();
//        int degrees = 0;
//
//        switch (rotation) {
//            case Surface.ROTATION_0: {
//                degrees = 0;
//                break;
//            }
//            case Surface.ROTATION_90: {
//                degrees = 90;
//                break;
//            }
//            case Surface.ROTATION_180: {
//                degrees = 180;
//                break;
//            }
//            case Surface.ROTATION_270: {
//                degrees = 270;
//                break;
//            }
//        }
//
//        int displayOrientation;
//
//        // Camera direction
//        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//            // Orientation is angle of rotation when facing the camera for
//            // the camera image to match the natural orientation of the device
//            displayOrientation = (cameraInfo.orientation + degrees) % 360;
//            displayOrientation = (360 - displayOrientation) % 360;
//        } else {
//            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
//        }
//
//        mDisplayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
//        mLayoutOrientation = degrees;
//
//        camera.setDisplayOrientation(displayOrientation);
//
//        Log.i(TAG, "displayOrientation:" + displayOrientation);
//    }


//
//
//    /**
//     * When orientation changes, onOrientationChanged(int) of the listener will be called
//     */
//    private class CameraOrientationListener extends OrientationEventListener {
//
//        private int mCurrentNormalizedOrientation;
//        private int mRememberedNormalOrientation;
//
//        public CameraOrientationListener(Context context) {
//            super(context, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//
//        @Override
//        public void onOrientationChanged(int orientation) {
//            if (orientation != ORIENTATION_UNKNOWN) {
//                mCurrentNormalizedOrientation = normalize(orientation);
//            }
//        }
//
//        private int normalize(int degrees) {
//            if (degrees > 315 || degrees <= 45) {
//                return 0;
//            }
//
//            if (degrees > 45 && degrees <= 135) {
//                return 90;
//            }
//
//            if (degrees > 135 && degrees <= 225) {
//                return 180;
//            }
//
//            if (degrees > 225 && degrees <= 315) {
//                return 270;
//            }
//
//            throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
//        }
//
//        public void rememberOrientation() {
//            mRememberedNormalOrientation = mCurrentNormalizedOrientation;
//        }
//
//        public int getRememberedNormalOrientation() {
//            return mRememberedNormalOrientation;
//        }
//    }


}
