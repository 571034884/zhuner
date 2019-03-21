package com.aibabel.ocr.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.ocr.BuildConfig;
import com.aibabel.ocr.MainActivity;
import com.aibabel.ocr.R;
import com.aibabel.ocr.adapter.CardPagerAdapter;
import com.aibabel.ocr.app.BaseApplication;
import com.aibabel.ocr.bean.ObjectResponseBean;
import com.aibabel.ocr.bean.ResponseBean;
import com.aibabel.ocr.bean.WordsResult;
import com.aibabel.ocr.gesture.GestureViewBinder;
import com.aibabel.ocr.utils.CommonUtils;
import com.aibabel.ocr.utils.Constant;
import com.aibabel.ocr.utils.ContentProviderUtil;
import com.aibabel.ocr.utils.DevUtils;
import com.aibabel.ocr.utils.DisplayUtil;
import com.aibabel.ocr.utils.FastJsonUtil;
import com.aibabel.ocr.utils.LanguageUtils;
import com.aibabel.ocr.utils.NetworkUtils;
import com.aibabel.ocr.utils.PictureUtil;
import com.aibabel.ocr.utils.ShadowTransformer;
import com.aibabel.ocr.utils.SharePrefUtil;
import com.aibabel.ocr.utils.StringUtils;
import com.aibabel.ocr.utils.ThreadPoolManager;
import com.aibabel.ocr.widgets.CameraPreview;
import com.aibabel.ocr.widgets.CardItem;
import com.aibabel.ocr.widgets.CustomProgress;
import com.aibabel.ocr.widgets.FocusImageView;
import com.aibabel.ocr.widgets.Guaguaka;
import com.aibabel.ocr.widgets.ReferenceLine;
import com.aibabel.ocr.widgets.SingleLineZoomTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.taobao.sophix.SophixManager;
import com.xiaoqi.libjpegcompress.ImageUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * @Class: TakePhoteActivity
 * @Description: 拍照界面
 * @Date: 2018/10/25
 */
public class TakePhoteActivity extends BaseActivity implements CameraPreview.OnCameraStatusListener, CardPagerAdapter.onClickListener {
    private static final String TAG = "TakePhoteActivity";
    private String pathName = PictureUtil.PATH + "ocrtemp.jpg"; //拍照后保存到内存中的地址

    private int status = Constant.PREVIEW;
    private int status_compress;
    CameraPreview mCameraPreview;
    RelativeLayout mTakePhotoLayout;
    private TextView tv_orLan;
    private TextView tv_tranLan;
    private TextView tv_light;
    private RadioGroup rg_meau;
    private RadioButton rb_object;
    private RadioButton rb_menu;
    private RadioButton rb_article;
    private TextView tv_change;
    private TextView tv_back;
    private TextView tvFanhui;
    private Guaguaka guaguaka;
    private LinearLayout root_layout;
    private ReferenceLine rl_line;
    private TextView tvHint;

    private RelativeLayout rl_camera;
    private RelativeLayout rl_confirm;
    private RelativeLayout rl_meau;
    private ImageView iv_recamera;
    private ImageView iv_translation;
    private ImageView iv_camera;
    private ImageView iv_guagua;


    /*引导页*/
    private Handler mHandler = new Handler();
    private View popu1;
    private TextView tv_I_kown1;
    private TextView tv_I_kown2;
    private TextView tv_I_kown3;
    private TextView tv_I_kown4;
    private TextView tv_I_kown5;
    private PopupWindow popupWindow;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private RelativeLayout rl4;

    private int rg_tag = 0;
    private boolean isOpenFlashMode = false;
    private FocusImageView fv_focus;
    private double latitude;
    private double longitude;
    private FrameLayout fl_tran;
    private GestureViewBinder gestureViewBinder;
    private CustomProgress dialog;
    private int isDaubed = 0;//是否已经涂抹
    private String type = Constant.TYPE_MENU;//是否已经涂抹

    private OrientationEventListener mOrEventListener;


    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private ViewPager mViewPager;
    private int requsetCode = 1111;
    private Bitmap bitmapData;//相机拍照返回的图片


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_phote);
        setPathParams(Constant.LAN_OR + " " + Constant.LAN_TR);
        init();
        String guangbi = getIntent().getStringExtra("from");
        if (guangbi != null && guangbi.equals("food")) {
            tvFanhui.setVisibility(View.VISIBLE);
        } else {
            tvFanhui.setVisibility(View.GONE);
        }


//        屏幕按钮随屏幕旋转
//        setOrientationChangeListener();

        rexiufu();

    }

    /**
     * 初始化控件
     */
    public void init() {
        root_layout = findViewById(R.id.root_layout);
        tvHint = findViewById(R.id.hint);
        fl_tran = findViewById(R.id.fl_tran);
        rl_camera = findViewById(R.id.rl_camera);
        rl_confirm = findViewById(R.id.rl_confirm);
        rl_meau = findViewById(R.id.rl_meau);
        iv_recamera = findViewById(R.id.iv_recamera);
        iv_translation = findViewById(R.id.iv_translation);
        iv_camera = findViewById(R.id.iv_camera);
        tv_orLan = findViewById(R.id.tv_orLan);
        tv_orLan = findViewById(R.id.tv_orLan);
        tv_tranLan = findViewById(R.id.tv_tranLan);
        tv_back = findViewById(R.id.tv_back);
        tvFanhui = findViewById(R.id.tvFanhui);
        tv_change = findViewById(R.id.tv_change);
        tv_light = findViewById(R.id.tv_light);
        rg_meau = findViewById(R.id.rg_meau);
        rb_object = findViewById(R.id.rb_object);
        rb_menu = findViewById(R.id.rb_menu);
        rb_article = findViewById(R.id.rb_article);
        rl_line = findViewById(R.id.rl_line);
        iv_guagua = findViewById(R.id.dstImage);
        guaguaka = findViewById(R.id.guaguaka);
        mCameraPreview = findViewById(R.id.cameraPreview);
        mTakePhotoLayout = findViewById(R.id.take_photo_layout);
        //引导页
        popu1 = CommonUtils.getVersion(this);
        tv_I_kown1 = popu1.findViewById(R.id.tv_I_kown1);
        tv_I_kown2 = popu1.findViewById(R.id.tv_I_kown2);
        tv_I_kown3 = popu1.findViewById(R.id.tv_I_kown3);
        tv_I_kown4 = popu1.findViewById(R.id.tv_I_kown4);
        rl1 = popu1.findViewById(R.id.rl1);
        rl2 = popu1.findViewById(R.id.rl2);
        rl3 = popu1.findViewById(R.id.rl3);
        rl4 = popu1.findViewById(R.id.rl4);
        rl1.setVisibility(View.VISIBLE);
        //聚焦
        fv_focus = findViewById(R.id.fv_focus);
        Point point = new Point((BaseApplication.screenW / 2) - 68, ((BaseApplication.screenH - 84) / 2) - 120);
        fv_focus.setPosition(point);
        mCameraPreview.setFocusView(fv_focus);
        tv_orLan.setOnClickListener(this);
        tv_tranLan.setOnClickListener(this);
        tv_light.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tvFanhui.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        iv_recamera.setOnClickListener(this);
        iv_translation.setOnClickListener(this);
        rb_object.setOnClickListener(this);
        rb_menu.setOnClickListener(this);
        rb_article.setOnClickListener(this);
        mCameraPreview.setOnCameraStatusListener(this);
        tv_I_kown1.setOnClickListener(this);
        tv_I_kown2.setOnClickListener(this);
        tv_I_kown3.setOnClickListener(this);
        tv_I_kown4.setOnClickListener(this);

        initRadio();
        getLocation();
        initGuaGuaKa();
        PictureUtil.isFolderExists();
        status = Constant.PREVIEW;
        gestureViewBinder = GestureViewBinder.bind(TakePhoteActivity.this, mTakePhotoLayout, fl_tran);
        gestureViewBinder.setFullGroup(true);
        dialog = new CustomProgress(this, R.style.Custom_Progress);


        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(5);
    }


    /**
     * 判定是否为第一次执行，如果是第一次则显示引导，否则不显示
     */
    private Runnable mRunnable = new Runnable() {
        public void run() {
            // 弹出PopupWindow的具体代码
            if (SharePrefUtil.getBoolean(TakePhoteActivity.this, "isfirstTime", true)) {
                popupWindowShow(popu1);
                SharePrefUtil.saveBoolean(TakePhoteActivity.this, "isfirstTime", false);
            }
        }
    };


    /**
     * 底部弹出popupWindow
     */
    private void popupWindowShow(View popu) {
        popupWindow = new PopupWindow(popu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(root_layout, Gravity.CENTER, 0, 0);
    }


    /**
     * 初始化选择
     */
    private void initRadio() {
        selectArticleOrMeau(0);
        rg_meau.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_article:
                        tvHint.setVisibility(View.VISIBLE);
                        selectArticleOrMeau(1);
                        iv_camera.setClickable(true);
                        break;
                    case R.id.rb_menu:
                        tvHint.setVisibility(View.GONE);
                        selectArticleOrMeau(0);
                        iv_camera.setClickable(true);
                        break;
                    case R.id.rb_object:
                        tvHint.setVisibility(View.GONE);
                        selectArticleOrMeau(2);
                        iv_camera.setClickable(true);
                        break;
                }
            }
        });

    }

    /**
     * 选择模式
     *
     * @param tag
     */
    private void selectArticleOrMeau(int tag) {
        final Drawable drawable = getDrawable(R.mipmap.ic_meau_select);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (tag) {
            case 0:
                rg_tag = 0;
                type = Constant.TYPE_MENU;
                rb_article.setCompoundDrawables(null, null, null, null);
                rb_menu.setCompoundDrawables(null, null, null, drawable);
                rb_object.setCompoundDrawables(null, null, null, null);
                break;
            case 1:
                rg_tag = 1;
                type = Constant.TYPE_ARTICAL;
                rb_article.setCompoundDrawables(null, null, null, drawable);
                rb_menu.setCompoundDrawables(null, null, null, null);
                rb_object.setCompoundDrawables(null, null, null, null);
                break;
            case 2:
                rg_tag = 2;
                type = Constant.TYPE_OBJECT;
                rb_article.setCompoundDrawables(null, null, null, null);
                rb_menu.setCompoundDrawables(null, null, null, null);
                rb_object.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    /**
     * 获取定位信息
     */
    private void getLocation() {
        Cursor cursor = getContentResolver().query(Constant.CONTENT_URI, null, null, null, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                cursor.getString(cursor.getColumnIndex("city"));
                int latId = cursor.getColumnIndex("latitude");
                int lonId = cursor.getColumnIndex("longitude");
                latitude = cursor.getDouble(latId);
                longitude = cursor.getDouble(lonId);
            } else {

            }
        } finally {
            if (null != cursor)
                cursor.close();
        }

    }

    /**
     * 初始化刮刮卡
     */
    private void initGuaGuaKa() {
        guaguaka.setOnDownListener(new Guaguaka.OnTumoListener() {
            @Override
            public void onDown(View view) {
                guaguaka.changeFront();
            }

            @Override
            public void onUp(View view) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.RGB_565;
//                options.inDither = true;
//                startOcr(BitmapFactory.decodeFile(pathName, options));
                isDaubed++;
                dialog.show();
                startOcr();
            }
        });
    }


    /**
     * 开始涂抹识别翻译（部分）
     */
    public void startOcr() {
        //防止两次涂抹
        if (isDaubed > 1)
            return;
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                final RectF rectF = PictureUtil.combineDaubArea(guaguaka.getRects(), fangxiang);
                if (rectF == null) {
                    isDaubed = 0;
                    toast(getString(R.string.small));
                    dialog.dismiss();
                    return;
                }
                float dis_w = rectF.right - rectF.left;
                float dis_h = rectF.bottom - rectF.top;
                if (dis_h < 60 && dis_w < 60) {
                    isDaubed = 0;
                    toast(getString(R.string.small));
                    dialog.dismiss();
                    return;
                }

//                String bitmap = PictureUtil.bitmapToString(PictureUtil.cropDaubed(rectF, BitmapFactory.decodeFile(pathName)));
                final String path = PictureUtil.cropDaubed(rectF, bitmapData, fangxiang);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        guaguaka.clear();
                        getData(false, path, rectF.left, rectF.top);
                    }
                });

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mCameraPreview.onActivityStart();
        mCameraPreview.start();

        try {
            Map<String, Double> map = ContentProviderUtil.getLocation();
            latitude = map.get("latitude");
            longitude = map.get("longitude");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //延时启动handler
//        mHandler.postDelayed(mRunnable, 500);
    }

    /**
     * 给语言赋值
     */
    @Override
    protected void onResume() {
        super.onResume();
        //如果是不是翻译状态，mCameraPreview，主要解决fly版本变红的问题
        Log.e("status",status+"-----------------------------------");
        if (status == Constant.PREVIEW){
            mCameraPreview.setVisibility(View.GONE);
            mCameraPreview.setVisibility(View.VISIBLE);
        }

        //设置语言
        String or_code = SharePrefUtil.getString(this, Constant.LAN_OR_CODE, "en");
        String tr_code = SharePrefUtil.getString(this, Constant.LAN_TR_CODE, "ch_ch");
        String from = LanguageUtils.getNameByCode(or_code, this);
        String to = LanguageUtils.getRightNameByCode(tr_code, this);

        tv_orLan.setText(from + "");
        tv_tranLan.setText(to + "");
        SharePrefUtil.saveString(this, Constant.LAN_OR, from);
        SharePrefUtil.saveString(this, Constant.LAN_TR, to);
//        mOrEventListener.enable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCameraPreview.onActivityStop();
        mCameraPreview.stop();
        if (null != dialog && dialog.isShowing()) {
            dialog.cancle();
        }
//        mOrEventListener.disable();
    }


    /**
     * 拍照
     */
    public void takePhoto() {
        if (mCameraPreview != null) {
            mCameraPreview.takePicture();
            fv_focus.setVisibility(View.GONE);
        }
    }


    /**
     * 拍照成功后回调
     * 存储图片并显示截图界面
     *
     * @param data
     */
    @Override
    public void onCameraStopped(byte[] data) {
        // 创建图像
        bitmapData = BitmapFactory.decodeByteArray(data, 0, data.length);
        Glide.with(this).load(bitmapData).into(iv_guagua);
//        Glide.with(this).load(PictureUtil.adjustPhotoRotation(bitmapData, 90 * fangxiang - 90)).into(iv_guagua);
        tv_light.setVisibility(View.INVISIBLE);
        guaguaka.setDstWidthAndHeight(BaseApplication.screenW, BaseApplication.screenH);
        mCameraPreview.setCanFocus(false);

        //显示刮刮卡
        showGuaguaka();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_I_kown1:
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_I_kown2:
                rl2.setVisibility(View.GONE);
                rl3.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_I_kown3:
                rl3.setVisibility(View.GONE);
                rl4.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_I_kown4:
                popupWindow.dismiss();
                break;
            case R.id.tv_orLan://原始的语言
                selectLanguage(1);
                break;
            case R.id.tv_tranLan://选择目标语言
                selectLanguage(2);
                break;
            case R.id.tv_change://对换语言
                changeLanguage();
                break;
            case R.id.tv_back://返回涂抹界面
                if (rg_tag != 2)
                    showGuaguaka();
                else reset();
                break;
            case R.id.tvFanhui://返回涂抹界面
                finish();
                break;
            case R.id.iv_recamera://重新拍照
                isTaken = false;
                reset();
                break;
            case R.id.iv_camera://拍照
                isTaken = true;
                iv_camera.setClickable(false);
                takePhoto();
                Map<String, String> map = new HashMap<>();
                StatisticsManager.getInstance(TakePhoteActivity.this).addEventAidl(1405, map);
                break;
            case R.id.rb_object:
                selectArticleOrMeau(2);
                break;
            case R.id.rb_menu:
                selectArticleOrMeau(0);
                break;
            case R.id.rb_article:
                selectArticleOrMeau(1);
                break;
            case R.id.iv_translation://识别翻译确定
                if (CommonUtils.isFastClick()) {
                    dialog.show();
                    getData(true, pathName, 0f, 0f);
                }
                break;
            case R.id.tv_light://开启关闭闪光灯
                setFlashMode();
                break;
            default:
                break;
        }
    }

    /**
     * 开启关闭闪光灯
     */
    private void setFlashMode() {
        if (isOpenFlashMode) {
            isOpenFlashMode = false;
            tv_light.setBackground(getDrawable(R.mipmap.ic_flashclose));
        } else {
            isOpenFlashMode = true;
            tv_light.setBackground(getDrawable(R.mipmap.ic_flashlight));
        }
        mCameraPreview.setIsOpenFlashMode(isOpenFlashMode);
    }

    /**
     * 将原始语言和目标语言对换
     */
    private void changeLanguage() {
        String trLan = SharePrefUtil.getString(this, Constant.LAN_OR, "英语");
        String trLanCode = SharePrefUtil.getString(this, Constant.LAN_OR_CODE, "en");
        String orLan = SharePrefUtil.getString(this, Constant.LAN_TR, "中文");
        String orLanCode = SharePrefUtil.getString(this, Constant.LAN_TR_CODE, "ch_ch");

        if (TextUtils.equals(trLanCode, "jpa") || TextUtils.equals(trLanCode, "jpa_v")) {
            trLan = LanguageUtils.getRightLanByCode(this, trLanCode);
            trLanCode = "jpa";

        }
        if (TextUtils.equals(orLanCode, "jpa")) {
            orLan = LanguageUtils.getLanByCode(this, "jpa");
            orLanCode = "jpa";
        }


        tv_orLan.setText(orLan);
        tv_tranLan.setText(trLan);
        SharePrefUtil.saveString(this, Constant.LAN_TR, trLan);
        SharePrefUtil.saveString(this, Constant.LAN_OR, orLan);
        SharePrefUtil.saveString(this, Constant.LAN_OR_CODE, orLanCode);
        SharePrefUtil.saveString(this, Constant.LAN_TR_CODE, trLanCode);

    }

    /**
     * 修改语言
     *
     * @param type
     */
    private void selectLanguage(int type) {
        if (type == 1) {
            Intent intent = new Intent(this, SelectLanguageActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            Map<String, String> map = new HashMap<>();
            StatisticsManager.getInstance(this).addEventAidl(1400, map);
        } else {
            Intent intent = new Intent(this, SelectRightLanguageActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            Map<String, String> map = new HashMap<>();
            StatisticsManager.getInstance(this).addEventAidl(1401, map);
        }
    }

    /**
     * 发送请求识别和翻译的数据
     *
     * @param path
     * @param downX
     * @param downY
     */
    public void getData(final boolean isFull, String path, final float downX, final float downY) {
        String from = SharePrefUtil.getString(this, Constant.LAN_OR_CODE, "en");
        String to = SharePrefUtil.getString(this, Constant.LAN_TR_CODE, "ch_ch");

        if (status_compress == 0) {
            ImageUtils.compress(bitmapData, pathName, 200, 80);
            status_compress = 1;
        }

        //判定网络是否可用
        if (!NetworkUtils.isAvailable(this)) {
            isDaubed = 0;
            toast(R.string.no_net);
            dialog.dismiss();
            return;
        }

        //判定拍照是否成功
        if (TextUtils.isEmpty(path)) {
            isDaubed = 0;
            toast(getString(R.string.retry));
            dialog.dismiss();
            return;
        }

        //判定拍照语言是否一样
        if (rg_tag != 2 && TextUtils.equals(from, to)) {
            isDaubed = 0;
            toast(R.string.no_same);
            dialog.cancel();
            return;
        }
        //判定拍照语言是否一样日语横纵向
        if (rg_tag != 2 && (TextUtils.equals(from, "jpa") || TextUtils.equals(from, "jpa_v")) && TextUtils.equals("jpa", to)) {
            isDaubed = 0;
            toast(R.string.no_same);
            dialog.cancel();
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                guaguaka.setVisibility(View.GONE);
            }
        });
        String image = PictureUtil.bitmapToString(path);
        final long beginTime = System.currentTimeMillis();
        OkGo.<String>post(rg_tag == 2 ? ContentProviderUtil.getServerHost() + "/v1/object" : ContentProviderUtil.getServerHost() + "/v1/ocr")
                .tag(this)
                .headers("X-Progress-ID", UUID.randomUUID().toString().toLowerCase())
                .params("from", from)
                .params("to", to)
                .params("image", image)
                .params("id", CommonUtils.getSN())
                .params("type", type)
                .params("location", StringUtils.getLocation(latitude, longitude))
                .params("type", "general")
                .params("locale", LanguageUtils.getLang())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (rg_tag == 1) {//当前选中的是文章
                            showGuaguaka();
                            toArtical(response.body(), downX, downY);
                        } else if (rg_tag == 0) {//当前选中的是菜单
                            showOcr();//显示翻译
                            toMenu(response.body(), downX, downY);
                        } else if (rg_tag == 2) {//当前选中的是物体识别
                            toObject(response.body(), downX, downY);
                        }
                        Map<String, String> map = new HashMap<>();
                        map.put("p1", "成功");
                        map.put("p2", (System.currentTimeMillis() - beginTime) + "");
                        map.put("p3", Constant.LAN_OR);
                        map.put("p4", Constant.LAN_TR);
                        map.put("p5", rg_tag + "");
                        StatisticsManager.getInstance(TakePhoteActivity.this).addEventAidl(isFull ? 1403 : 1402, map);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dialog.cancel();
                        isDaubed = 0;
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        reset();
                        Map<String, String> map = new HashMap<>();
                        map.put("p1", "失败");
                        map.put("p2", (System.currentTimeMillis() - beginTime) + "");
                        map.put("p3", Constant.LAN_OR);
                        map.put("p4", Constant.LAN_TR);
                        map.put("p5", rg_tag + "");
                        StatisticsManager.getInstance(TakePhoteActivity.this).addEventAidl(isFull ? 1403 : 1402, map);
                        if (rg_tag != 2) showGuaguaka();
                        Toast.makeText(TakePhoteActivity.this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                        //切换服务器
                        ContentProviderUtil.sendErrorServer();
                    }
                });
    }


    /**
     * 展示涂抹布局
     */
    private void showGuaguaka() {
        isDaubed = 0;
        //修改状态值
        status = Constant.IMAGED;
        mCameraPreview.stop();
        mCameraPreview.setVisibility(View.GONE);
        fv_focus.setVisibility(View.GONE);
        rl_camera.setVisibility(View.GONE);
        rl_confirm.setVisibility(View.VISIBLE);
        iv_recamera.setVisibility(View.VISIBLE);
        iv_translation.setVisibility(View.VISIBLE);
        rl_line.setVisibility(View.GONE);
        rl_meau.setVisibility(View.GONE);
        fl_tran.removeAllViews();
        fl_tran.setVisibility(View.GONE);
        tv_back.setVisibility(View.INVISIBLE);
        iv_guagua.setVisibility(View.VISIBLE);
        guaguaka.clear();
        guaguaka.setVisibility(View.VISIBLE);
        tvHint.setVisibility(View.GONE);

    }

    /**
     * 展示翻译布局
     */
    private void showOcr() {
        gestureViewBinder.reset();
        guaguaka.clear();
        guaguaka.setVisibility(View.GONE);
        iv_guagua.setVisibility(View.GONE);
        mCameraPreview.stop();
        mCameraPreview.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        fv_focus.setVisibility(View.GONE);
        rl_confirm.setVisibility(View.VISIBLE);
        iv_translation.setVisibility(View.GONE);
        rl_line.setVisibility(View.GONE);
        status = Constant.TRANSLATE;
        tvHint.setVisibility(View.GONE);
    }


    /**
     * 重置
     */
    private void reset() {
        isDaubed = 0;
        gestureViewBinder.reset();
        fl_tran.removeAllViews();
        fl_tran.setVisibility(View.GONE);
        fv_focus.setVisibility(View.VISIBLE);
        mCameraPreview.setCanFocus(true);
        tv_light.setVisibility(View.VISIBLE);
        rl_line.setVisibility(View.VISIBLE);
        rl_meau.setVisibility(View.VISIBLE);
        rl_camera.setVisibility(View.VISIBLE);
        rl_confirm.setVisibility(View.GONE);
        iv_recamera.setVisibility(View.GONE);
        iv_translation.setVisibility(View.GONE);
        iv_camera.setClickable(true);
        tv_back.setVisibility(View.INVISIBLE);
        iv_guagua.setVisibility(View.GONE);
        guaguaka.clear();
        status = Constant.PREVIEW;
        OkGo.getInstance().cancelAll();
        guaguaka.setVisibility(View.GONE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mCameraPreview.start();
        mViewPager.setVisibility(View.GONE);
        status_compress = 0;
        if (rg_tag == 1) {
            tvHint.setVisibility(View.VISIBLE);
        } else {
            tvHint.setVisibility(View.GONE);
        }
    }


    /**
     * 跳转到识别翻译文章页面
     *
     * @param result 识别翻译返回的结果
     * @param x      部分翻译时x坐标（部分翻译）
     * @param y      部分翻译时y坐标（部分翻译）
     */
    private void toArtical(String result, float x, float y) {

        if (!TextUtils.isEmpty(result)) {
            ResponseBean bean = null;
            try {
                bean = FastJsonUtil.changeJsonToBean(result, ResponseBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (null == bean || null == bean.getResult()) {
                reset();
                Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                return;
            }
            if (bean.getResult().size() == 0) {
                reset();
                Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                return;
            }


            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("path", pathName);
            intent.putExtra("result", result);
            intent.putExtra("x", x);
            intent.putExtra("y", y);
            startActivityForResult(intent, 1111);
        } else {
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 识别翻译菜单
     *
     * @param response
     * @param downX
     * @param downY
     */
    private void toMenu(String response, float downX, float downY) {
        ResponseBean bean = null;
        try {
            bean = FastJsonUtil.changeJsonToBean(response, ResponseBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == bean || null == bean.getResult()) {
            reset();
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        if (bean.getResult().size() == 0) {
            reset();
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
            return;

        }

        if (null != bean && null != bean.getResult() && bean.getResult().size() > 0) {
            fl_tran.removeAllViews();
            fl_tran.setVisibility(View.VISIBLE);
            Bitmap bm = PictureUtil.adjustPhotoRotation(BitmapFactory.decodeFile(pathName), 90 * fangxiang - 90);
            ImageView imageView = new ImageView(this);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            int asss = getResources().getColor(R.color.black_transparent);
            RequestOptions options = new RequestOptions().transform(new ColorFilterTransformation(asss));

            Glide.with(this).load(bitmapData).apply(options).into(imageView);
            fl_tran.addView(imageView);

            FrameLayout frameLayout = new FrameLayout(this);
            FrameLayout.LayoutParams lll = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.setLayoutParams(lll);
            fl_tran.addView(frameLayout);

            for (int i = 0; i < bean.getResult().size(); i++) {
                String words = bean.getResult().get(i).getTrans_words();
                WordsResult result = new WordsResult();
                result.setHeight(StringUtils.getRealHeight(bean.getResult().get(i).getLocation().getHeight()));
                result.setLeft(StringUtils.getRealWidth(bean.getResult().get(i).getLocation().getX()) + (int) downX);
                result.setTop(StringUtils.getRealHeight(bean.getResult().get(i).getLocation().getY()) + (int) downY);
                result.setWidth(StringUtils.getRealWidth(bean.getResult().get(i).getLocation().getWidth()));
                result.setWords(words);

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(result.getWidth(), FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins((result.getLeft() > 1) ? result.getLeft() : 2, result.getTop() + 1, 0, 0);
                SingleLineZoomTextView textView = new SingleLineZoomTextView(this);
                if (TextUtils.equals("1_v", bean.getMode())) {//竖排文字
                    textView.setRotation(90);
                    textView.setPivotX(result.getWidth());
                    textView.setPivotY(0);
                    layoutParams = new FrameLayout.LayoutParams(result.getHeight(), FrameLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins((result.getLeft() > 1) ? result.getLeft() : 2, result.getTop() + result.getWidth(), 0, 0);
                }
                textView.setLayoutParams(layoutParams);
                textView.setText(result.getWords());
                textView.setMaxWidth(DisplayUtil.getWidthInPx(this) - result.getLeft());
                textView.setPadding(0, 1, 0, 1);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextColor(getResources().getColor(R.color.white));
                frameLayout.addView(textView);

            }
//            if (fangxiang == 0)
//                startPropertyAnim(frameLayout, -90, StringUtils.getRealWidth(bm.getHeight() / 2), StringUtils.getRealHeight(bm.getHeight() / 2));
//            else if (fangxiang == 2)
//                startPropertyAnim(frameLayout, 90, StringUtils.getRealWidth(bm.getWidth() / 2), StringUtils.getRealHeight(bm.getWidth() / 2));
//            else if (fangxiang == 3)
//                startPropertyAnim(frameLayout, 180, StringUtils.getRealWidth(bm.getWidth() / 2), StringUtils.getRealHeight(bm.getHeight() / 2));
//            else
//                startPropertyAnim(frameLayout, 0, 0, 0);
        } else {
            Toast.makeText(this, bean.getError_message() + "", Toast.LENGTH_SHORT).show();
            reset();
        }

    }

//    /**
//     * 识别翻译菜单
//     *
//     * @param response
//     * @param downX
//     * @param downY
//     */
//    private void toMenu(String response, float downX, float downY) {
//        ResponseBean bean = null;
//        try {
//            bean = FastJsonUtil.changeJsonToBean(response, ResponseBean.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (null == bean || null == bean.getResult()) {
//            reset();
//            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (bean.getResult().size() == 0) {
//            reset();
//            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//
//        if (null != bean && null != bean.getResult() && bean.getResult().size() > 0) {
//
//            fl_tran.removeAllViews();
//            fl_tran.setVisibility(View.VISIBLE);
//            Bitmap bm = PictureUtil.adjustPhotoRotation(BitmapFactory.decodeFile(pathName), 90 * fangxiang - 90);
//            ImageView imageView = new ImageView(this);
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//            imageView.setLayoutParams(lp);
//            int asss = getResources().getColor(R.color.black_transparent);
//            RequestOptions options = new RequestOptions().transform(new ColorFilterTransformation(asss));
//
//            Glide.with(this).load(bitmapData).apply(options).into(imageView);
//            fl_tran.addView(imageView);
//
//            FrameLayout frameLayout = new FrameLayout(this);
//            FrameLayout.LayoutParams lll = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//            frameLayout.setLayoutParams(lll);
//            fl_tran.addView(frameLayout);
//
//            for (int i = 0; i < bean.getResult().size(); i++) {
//                String words = bean.getResult().get(i).getTrans_words();
//                WordsResult result = new WordsResult();
//                result.setHeight(StringUtils.getRealHeight(bean.getResult().get(i).getLocation().getHeight()));
//                result.setLeft(StringUtils.getRealWidth(bean.getResult().get(i).getLocation().getX()) + (int) downX);
//                result.setTop(StringUtils.getRealHeight(bean.getResult().get(i).getLocation().getY()) + (int) downY);
//                result.setWidth(StringUtils.getRealWidth(bean.getResult().get(i).getLocation().getWidth()));
//                result.setWords(words);
//
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(result.getWidth(), FrameLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins((result.getLeft() > 1) ? result.getLeft() : 2, result.getTop() + 1, 0, 0);
//                SingleLineZoomTextView textView = new SingleLineZoomTextView(this);
//                if (TextUtils.equals("1_v", bean.getMode())) {//竖排文字
//                    textView.setRotation(90);
//                    textView.setPivotX(result.getWidth());
//                    textView.setPivotY(0);
//                    layoutParams = new FrameLayout.LayoutParams(result.getHeight(), FrameLayout.LayoutParams.WRAP_CONTENT);
//                    layoutParams.setMargins((result.getLeft() > 1) ? result.getLeft() : 2, result.getTop() + result.getWidth(), 0, 0);
//                }
//                textView.setLayoutParams(layoutParams);
//                textView.setText(result.getWords());
//                textView.setMaxWidth(DisplayUtil.getWidthInPx(this) - result.getLeft()-2);
//                textView.setPadding(0, 1, 0, 1);
//                textView.setGravity(Gravity.CENTER_VERTICAL);
//                textView.setTextColor(getResources().getColor(R.color.white));
//                frameLayout.addView(textView);
//
//            }
//            if (fangxiang == 0)
//                startPropertyAnim(frameLayout, -90, StringUtils.getRealWidth(bm.getHeight() / 2), StringUtils.getRealHeight(bm.getHeight
//                        () / 2));
//            else if (fangxiang == 2)
//                startPropertyAnim(frameLayout, 90, StringUtils.getRealWidth(bm.getWidth() / 2), StringUtils.getRealHeight(bm.getWidth() /
//                        2));
//            else if (fangxiang == 3)
//                startPropertyAnim(frameLayout, 180, StringUtils.getRealWidth(bm.getWidth() / 2), StringUtils.getRealHeight(bm.getHeight()
//                        / 2));
//            else
//                startPropertyAnim(frameLayout, 0, 0, 0);
//
//        } else {
//            Toast.makeText(this, bean.getError_message() + "", Toast.LENGTH_SHORT).show();
//            reset();
//        }
//
//    }

    /**
     * 跳转到物体识别
     *
     * @param response
     * @param downX
     * @param downY
     */
    public void toObject(String response, float downX, float downY) {
        rl_confirm.setVisibility(View.GONE);//此处为临时办法
        mCameraPreview.stop();
        dialog.cancle();
        ObjectResponseBean bean = FastJsonUtil.changeJsonToBean(response, ObjectResponseBean.class);
        if (null != bean.getData() && bean.getData().size() > 0) {
            tv_back.setVisibility(View.VISIBLE);
            List<CardItem> cardItemList = new ArrayList<>();
            for (int i = 0; i < bean.getData().size(); i++) {
                cardItemList.add(new CardItem(bean.getData().get(i).getText(), bean.getData().get(i).getKeyword(), bean.getData().get(i)
                        .getPic(), bean.getData().get(i).getBaike()));
            }
            mCardAdapter = new CardPagerAdapter(cardItemList, TakePhoteActivity.this);
            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
            mViewPager.setAdapter(mCardAdapter);
            mCardAdapter.setOnItemClickListener(TakePhoteActivity.this);
            mCardShadowTransformer.enableScaling(true);
            mViewPager.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(TakePhoteActivity.this, R.string.error_msg, Toast.LENGTH_SHORT).show();
            reset();
        }
        Log.d(TAG, response.toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isTaken = false;

    /**
     * 当前屏幕旋转角度
     */
//    private int mOrientation = 0;
//    private Context context;

    /**
     * 屏幕方向0横，1竖
     */
    public int direction = 1;
    public boolean vertical = true;

    /**
     * 0左，1 上，2右，3下
     */
    public int lastFangxiang = 1;
    public int fangxiang = 1;

    /**
     * 启动屏幕朝向改变监听函数 用于在屏幕横竖屏切换时改变保存的图片的方向
     */
    private void setOrientationChangeListener() {
        mOrEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (isTaken) return;
                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
                    if (direction == 0) {
                        Log.e(TAG, "onOrientationChanged: " + "横屏变竖屏1");
                        direction = 1;
                        fangxiang = 1;
                        Log.e(TAG, "getDushu: fangxiang form" + lastFangxiang + "to" + fangxiang);
                        if (lastFangxiang == 0) {
                            startPropertyAnim(iv_camera, 270f, 360f, 500);
                            startPropertyAnim(tvHint, 270f, 360f);
                        } else if (lastFangxiang == 2) {
                            startPropertyAnim(iv_camera, 90f, 0f, 500);
                            startPropertyAnim(tvHint, 90f, 0f);
                        }
                        iv_camera.setClickable(true);
                        tvHint.setVisibility(View.GONE);
                    }
                } else if ((rotation > 45) && (rotation <= 135)) {
                    if (direction == 1) {
                        Log.e(TAG, "onOrientationChanged: " + "竖屏变横屏4");
                        direction = 0;
                        fangxiang = 0;
                        Log.e(TAG, "getDushu: fangxiang form" + lastFangxiang + "to" + fangxiang);
                        if (lastFangxiang == 3) {
                            startPropertyAnim(iv_camera, 180f, 270f, 500);
                            startPropertyAnim(tvHint, 180f, 270f);
                        } else if (lastFangxiang == 1) {
                            startPropertyAnim(iv_camera, 360f, 270f, 500);
                            startPropertyAnim(tvHint, 360f, 270f);
                        }
                        iv_camera.setClickable(true);
                        if (rg_tag == 1) {
                            tvHint.setVisibility(View.VISIBLE);
                            iv_camera.setClickable(false);
                        }
                    }
                } else if ((rotation > 135) && (rotation <= 225)) {
                    if (direction == 0) {
                        Log.e(TAG, "onOrientationChanged: " + "横屏变竖屏3");
                        direction = 1;
                        fangxiang = 3;
                        Log.e(TAG, "getDushu: fangxiang form" + lastFangxiang + "to" + fangxiang);
                        if (lastFangxiang == 2) {
                            startPropertyAnim(iv_camera, 90f, 180f, 500);
                            startPropertyAnim(tvHint, 90f, 180f);
                        } else if (lastFangxiang == 0) {
                            startPropertyAnim(iv_camera, 270f, 180f, 500);
                            startPropertyAnim(tvHint, 270f, 180f);
                        }
                        iv_camera.setClickable(true);
                        if (rg_tag == 1) {
                            tvHint.setVisibility(View.VISIBLE);
                            iv_camera.setClickable(false);
                        }
                    }
                } else if ((rotation > 225) && (rotation <= 315)) {
                    if (direction == 1) {
                        Log.e(TAG, "onOrientationChanged: " + "竖屏变横屏2");
                        direction = 0;
                        fangxiang = 2;
                        Log.e(TAG, "getDushu: fangxiang form" + lastFangxiang + "to" + fangxiang);
                        if (lastFangxiang == 1) {
                            startPropertyAnim(iv_camera, 0f, 90f, 500);
                            startPropertyAnim(tvHint, 0f, 90f);
                        } else if (lastFangxiang == 3) {
                            startPropertyAnim(iv_camera, 180f, 90f, 500);
                            startPropertyAnim(tvHint, 180f, 90f);
                        }
                        iv_camera.setClickable(true);
                        if (rg_tag == 1) {
                            tvHint.setVisibility(View.VISIBLE);
                            iv_camera.setClickable(false);
                        }
                    }
                }
            }
        };
    }

    //  保存的图片动画实际执行
    private void startPropertyAnim(View view, float mOrientation, int x, int y) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", 0f, mOrientation);
        view.setPivotX(x);
        view.setPivotY(y);
        // 动画的持续时间，执行多久？
        anim.setDuration(0);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // 正式开始启动执行动画
        anim.start();
    }

    //相机图标转
    private void startPropertyAnim(View view, float from, float to) {
        startPropertyAnim(view, from, to, 0);
    }

    // 动画实际执行
    private void startPropertyAnim(View view, float from, float to, long time) {
        lastFangxiang = fangxiang;
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", from, to);
        // 动画的持续时间，执行多久？
        anim.setDuration(time);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // 正式开始启动执行动画
        anim.start();
    }


    @Override
    public void onItemClick(CardItem item) {
        Intent intent = new Intent(TakePhoteActivity.this, DetailActivity.class);
        intent.putExtra("title", item.getmTextResource());
        intent.putExtra("detail", item.getmTitleResource());
        intent.putExtra("url", item.getmUrlResource());
        intent.putExtra("audioUrl", item.getmAudioUrl());
        startActivityForResult(intent, requsetCode);
        rl_confirm.setVisibility(View.GONE);
    }

    public void rexiufu() {
        String latitude = "111";
        String longitude = "111";
//        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
//        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
        String url = "http://abroad.api.joner.aibabel.cn:7001" + "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocalLanguage() + "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;

        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    Log.e("success:", "=================" + isNew + "=================");
                                } else {
                                    Log.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });
    }
}
