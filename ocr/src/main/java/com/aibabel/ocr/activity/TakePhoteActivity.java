package com.aibabel.ocr.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.ocr.MainActivity;
import com.aibabel.ocr.R;
import com.aibabel.ocr.adapter.CardPagerAdapter;
import com.aibabel.ocr.app.BaseApplication;
import com.aibabel.ocr.bean.ObjectResponseBean;
import com.aibabel.ocr.bean.ResponseBean;
import com.aibabel.ocr.bean.WordsResult;
import com.aibabel.ocr.gesture.GestureViewBinder;
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
import com.aibabel.ocr.widgets.OcrDisplayView;
import com.aibabel.ocr.widgets.ReferenceLine;
import com.aibabel.ocr.widgets.SingleLineZoomTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xiaoqi.libjpegcompress.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * @Class: TakePhoteActivity
 * @Description: 拍照界面测试用textview承载
 * @author: lling
 * @Date: 2018/10/25
 */
public class TakePhoteActivity extends BaseActivity implements CameraPreview.OnCameraStatusListener, CardPagerAdapter.onClickListener {
    private static final String TAG = "TakePhoteActivity";
    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final String PATH = Environment.getExternalStorageDirectory().toString() + "/AndroidMedia/";
    CameraPreview mCameraPreview;
    //    CropImageView mCropImageView;
    RelativeLayout mTakePhotoLayout;
    //    LinearLayout mCropperLayout;
    private TextView tv_orLan;
    private TextView tv_tranLan;
    private TextView tv_light;
    private RadioGroup rg_meau;
    private RadioButton rb_object;
    private RadioButton rb_menu;
    private RadioButton rb_article;
    private TextView tv_change;
    private TextView tv_back;
    private Guaguaka guaguaka;
    private OcrDisplayView ocrDisplayView;
    private LinearLayout root_layout;
    private ReferenceLine rl_line;

    private RelativeLayout rl_camera;
    private RelativeLayout rl_confirm;
    private RelativeLayout rl_meau;
    private ImageView iv_recamera;
    private ImageView iv_translation;
    private ImageView iv_camera;
    private ImageView dstView;
    private boolean isShow = false;//是否展示涂抹和翻译结果

    private String path; //拍照后保存到内存中的地址
    private String or_code;
    private String tr_code;
    private int rg_tag = 0;
    private boolean isOpenFlashMode = false;
    private Uri path_uri;
    private FocusImageView focusView;
    private double latitude;
    private double longitude;
    private FrameLayout fl_tran;
    private GestureViewBinder gestureViewBinder;
    private CustomProgress dialog;
    private int isDaubed = 0;//是否已经涂抹
    private String type = Constant.TYPE_MENU;//是否已经涂抹
    private long oldTime = 0;//

    private OrientationEventListener mOrEventListener;
    private EditText et_test4;
    private RadioGroup radioGroup;
    private View addr;
    private View b_addr;
    private View b_queding;
    private PopupWindow window;


    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private ViewPager mViewPager;
    private int requsetCode = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_phote);
        init();
        changeServerAddress();

//        屏幕按钮随屏幕旋转
        setOrientationChangeListener();


    }

    /**
     * 初始化控件
     */
    private void init() {
        root_layout = findViewById(R.id.root_layout);
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
        tv_change = findViewById(R.id.tv_change);
        tv_light = findViewById(R.id.tv_light);
        rg_meau = findViewById(R.id.rg_meau);
        rb_object = findViewById(R.id.rb_object);
        rb_menu = findViewById(R.id.rb_menu);
        rb_article = findViewById(R.id.rb_article);
        rl_line = findViewById(R.id.rl_line);
        dstView = findViewById(R.id.dstImage);
        guaguaka = findViewById(R.id.guaguaka);
        ocrDisplayView = findViewById(R.id.ocrDisplayView);
        mCameraPreview = findViewById(R.id.cameraPreview);
        mTakePhotoLayout = findViewById(R.id.take_photo_layout);
        focusView = findViewById(R.id.fv_focus);
        Point point = new Point((BaseApplication.screenW / 2) - 68, ((BaseApplication.screenH - 84) / 2) - 120);
        focusView.setPosition(point);
        mCameraPreview.setFocusView(focusView);
        tv_orLan.setOnClickListener(this);
        tv_tranLan.setOnClickListener(this);
        tv_light.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        iv_recamera.setOnClickListener(this);
        iv_translation.setOnClickListener(this);
        rb_object.setOnClickListener(this);
        rb_menu.setOnClickListener(this);
        rb_article.setOnClickListener(this);
        mCameraPreview.setOnCameraStatusListener(this);
        ocrDisplayView.setOnDownListener(new OcrDisplayView.OnDownListener() {
            @Override
            public void onDown(View view) {

            }
        });

        initRadio();
        getLocation();
        initGuaGuaKa();
        gestureViewBinder = GestureViewBinder.bind(TakePhoteActivity.this, mTakePhotoLayout, fl_tran);
        gestureViewBinder.setFullGroup(true);
        dialog = new CustomProgress(this, R.style.Custom_Progress);


        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(5);
    }

    private void initRadio() {
        selectArticleOrMeau(0);
        rg_meau.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_article:
                        selectArticleOrMeau(1);
                        break;
                    case R.id.rb_menu:
                        selectArticleOrMeau(0);
                        break;
                    case R.id.rb_object:
                        selectArticleOrMeau(2);
                        break;
                }
            }
        });

    }

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

    private void initGuaGuaKa() {
        guaguaka.setOnDownListener(new Guaguaka.OnTumoListener() {
            @Override
            public void onDown(View view) {

            }

            @Override
            public void onUp(View view) {
                isDaubed++;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;
                startOcr(BitmapFactory.decodeFile(path, options));
            }
        });
    }


    /**
     * 开始涂抹识别翻译（部分）
     *
     * @param bitmap
     */
    public void startOcr(final Bitmap bitmap) {
        //防止两次涂抹
        if (isDaubed > 1)
            return;
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                final RectF rectF = combineDaubArea(guaguaka.getRects());
                if (rectF == null) {
                    isDaubed = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TakePhoteActivity.this, getString(R.string.small), Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                float dis_w = rectF.right - rectF.left;
                float dis_h = rectF.bottom - rectF.top;

                if (dis_h < 60 && dis_w < 60) {
                    isDaubed = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TakePhoteActivity.this, getString(R.string.small), Toast.LENGTH_SHORT).show();
                        }
                    });

                    return;
                }

                String sbitmap = "";
                final Uri myUri = cropDaubed(rectF, bitmap);
                if (myUri == null) {
                    isDaubed = 0;
                    return;
                }

                final Bitmap bitmap1 = PictureUtil.getBitmapFromUri(myUri, TakePhoteActivity.this);
                sbitmap = PictureUtil.bitmapToString(bitmap1);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ocrDisplayView.clear();//清空识别翻译布局
                        guaguaka.clear();
                    }
                });

                try {
                    PictureUtil.saveBitmap(bitmap1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                });
                getData(sbitmap, rectF.left, rectF.top);
            }
        });

    }

    /**
     * 计算(合并)涂抹区域
     *
     * @param rects
     */
    private RectF combineDaubArea(List<RectF> rects) {
        if (rects == null || rects.size() == 0)
            return null;
        RectF rectF = rects.get(0);
        for (int i = 1; i < rects.size(); i++) {
            RectF item = rects.get(i);
            rectF.left = Math.min(rectF.left, item.left);
            rectF.top = Math.min(rectF.top, item.top);
            rectF.right = Math.max(rectF.right, item.right);
            rectF.bottom = Math.max(rectF.bottom, item.bottom);
        }
        //根据旋转来改变涂抹框坐标原点
        RectF newRectf = null;
        if (fangxiang == 1) newRectf = rectF;
        else if (fangxiang == 0)
            newRectf = new RectF(960 - rectF.bottom, rectF.left, 960 - rectF.top, rectF.right);
        else if (fangxiang == 2)
            newRectf = new RectF(rectF.top, 540 - rectF.right, 960 - rectF.bottom, 540 - rectF.left);
        else if (fangxiang == 3)
            newRectf = new RectF(540 - rectF.right, 960 - rectF.bottom, 540 - rectF.left, 960 - rectF.top);
        return newRectf;
    }

    /**
     * 裁剪涂抹后的图片
     */
    private Uri cropDaubed(RectF rectF, Bitmap bitmap) {
        final int imageSize = 3 * 1024 * 1024;//压缩后的图片大小
        Uri daubUri = null;
        System.out.println("cropDaubed-up3:left=" + rectF.left + " top=" + rectF.top + " width=" + rectF.width() + " bottom=" + rectF.height());
        System.out.println("cropDaubed-up4: width=" + bitmap.getWidth() + " height=" + bitmap.getHeight());
        try {
            //裁剪图片
//            final Bitmap daubBitmap = Bitmap.createBitmap(bitmap, (int) rectF.left * Constant.REQUIRE_W / BaseApplication.screenW, (int) rectF.top * Constant.REQUIRE_H / BaseApplication.screenH, (int) rectF.width() * Constant.REQUIRE_W / BaseApplication.screenW, (int) rectF.height() * Constant.REQUIRE_H / BaseApplication.screenH);
             Bitmap daubBitmap = null;
            if (fangxiang == 1 || fangxiang == 3)
                daubBitmap=Bitmap.createBitmap(bitmap, (int) rectF.left * 720 / 540, (int) rectF.top * 1280 / 960, (int) rectF.width() * 720 / 540, (int) rectF.height() * 1280 / 960);
            else if (fangxiang == 2 || fangxiang == 0)
                daubBitmap=Bitmap.createBitmap(bitmap, (int) rectF.left * 1280 / 960, (int) rectF.top * 720 / 540, (int) rectF.width() * 1280 / 960, (int) rectF.height() * 720 / 540);
//            System.out.println("cropDaubed:涂抹后的图片 width=" + daubBitmap.getWidth() + " height=" + daubBitmap.getHeight());
            File folder = new File(Environment.getExternalStorageDirectory() + "/ocr");
            if (!folder.exists())
                folder.mkdirs();
            File pictureFile = new File(folder, "aa_ocr.jpg");
            int options = 100;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            daubBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length > imageSize && options > 0) {
                baos.reset();
                options -= 5;
                daubBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//                System.out.println("cropDaubed:继续压缩 option=" + options + "  " + baos.toByteArray().length);
            }
            FileOutputStream fos = new FileOutputStream(pictureFile);
            baos.writeTo(fos);
            fos.close();
            daubUri = Uri.fromFile(pictureFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daubUri;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mCameraPreview.onActivityStart();
        mCameraPreview.start();
    }

    /**
     * 给语言赋值
     */
    @Override
    protected void onResume() {
        super.onResume();
        String or_code = SharePrefUtil.getString(this, Constant.LAN_OR_CODE, "en");
        String tr_code = SharePrefUtil.getString(this, Constant.LAN_TR_CODE, "ch_ch");
        tv_orLan.setText(LanguageUtils.getNameByCode(or_code, this));
        tv_tranLan.setText(LanguageUtils.getRightNameByCode(tr_code, this));
        sendInvalidBroadCast();
        mOrEventListener.enable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCameraPreview.onActivityStop();
        mCameraPreview.stop();
        sendEffectBroadCast();
        if (null != dialog && dialog.isShowing()) {
            dialog.cancle();
        }
        mOrEventListener.disable();
    }


    /**
     * 拍照
     */
    public void takePhoto() {
        if (mCameraPreview != null) {
            mCameraPreview.takePicture();
            focusView.setVisibility(View.GONE);
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
//        Log.i("TAG", "==onCameraStopped==");
        tv_light.setVisibility(View.INVISIBLE);

        isShow = true;

//        try {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ocrtemp.jpg";
        path_uri = Uri.parse(path);

//        ImageUtils.compress(BitmapFactory.decodeByteArray(data, 0, data.length), path, 150);
        ImageUtils.compress(PictureUtil.adjustPhotoRotation(BitmapFactory.decodeByteArray(data, 0, data.length), -90 * fangxiang + 90), path, 150);
//        try {
//            ExifInterface exifInterface = new ExifInterface(path);
//            if (fangxiang == 0)
//                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_270 + "");
//            else if (fangxiang == 2)
//                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_90 + "");
//            else if (fangxiang == 3)
//                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_180 + "");
//            else
//                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL + "");
//            exifInterface.saveAttributes();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Log.e(TAG, "onCameraStopped: " + PictureUtil.getExifOrientation(path) + "   " + fangxiang);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        guaguaka.setDstWidthAndHeight(BaseApplication.screenW, BaseApplication.screenH);
        guaguaka.setVisibility(View.VISIBLE);
        dstView.setVisibility(View.VISIBLE);
        rl_camera.setVisibility(View.GONE);
        focusView.setVisibility(View.GONE);
        Glide.with(this).load(PictureUtil.adjustPhotoRotation(BitmapFactory.decodeFile(path), 90 * fangxiang - 90)).into(dstView);
        mCameraPreview.setCanFocus(false);
        rl_confirm.setVisibility(View.VISIBLE);
        iv_recamera.setVisibility(View.VISIBLE);
        iv_translation.setVisibility(View.VISIBLE);
//        iv_translation.setClickable(true);
        rl_line.setVisibility(View.GONE);
        rl_meau.setVisibility(View.GONE);
    }


    /**
     * 存储图像并将信息添加入媒体数据库
     */
    private Uri insertImage(ContentResolver cr, String name, long dateTaken, String directory, String filename, Bitmap source, byte[] jpegData) {
        OutputStream outputStream = null;
        String filePath = directory + filename;
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(directory, filename);
            if (file.createNewFile()) {
                outputStream = new FileOutputStream(file);
                if (source != null) {
                    source.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } else {
                    outputStream.write(jpegData);
                }
            }
        } catch (IOException e) {
//            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable t) {
                }
            }
        }
        ContentValues values = new ContentValues(7);
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, filePath);
        return cr.insert(IMAGE_URI, values);
    }

    private void deleteImage() {
        PictureUtil.deleteFile(PATH);
        getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media.MIME_TYPE + "= \"image/jpeg\"", null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2222) {//跳转到文章翻译回调
            if (mCameraPreview.getVisibility() == View.GONE) {
                mCameraPreview.setVisibility(View.VISIBLE);
                mCameraPreview.start();
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.iv_recamera://重新拍照
                isTaken = false;
                reset();
                break;
            case R.id.iv_camera://拍照
                isTaken = true;
                iv_camera.setClickable(false);
                takePhoto();
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
                long currentTime = System.currentTimeMillis();
                if (currentTime - oldTime < 2000) {
                    oldTime = currentTime;
                    return;
                }
                oldTime = currentTime;
                dialog.show();
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                options.inDither = true;
//                Bitmap bm = BitmapFactory.decodeFile(path);
//                if (direction == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                    Matrix m = new Matrix();
//                    m.setRotate(-90, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
//                }
                getData(PictureUtil.imageToBase64(path), 0f, 0f);
//                getData(PictureUtil.bitmapToString(bm), 0f, 0f);
//                iv_translation.setClickable(false);
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
        } else {
            Intent intent = new Intent(this, SelectRightLanguageActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }

    /**
     * 发送请求识别和翻译的数据
     *
     * @param bitmap
     * @param downX
     * @param downY
     */
    public void getData(String bitmap, final float downX, final float downY) {
        String from = SharePrefUtil.getString(this, Constant.LAN_OR_CODE, "en");
        String to = SharePrefUtil.getString(this, Constant.LAN_TR_CODE, "ch_ch");
        if (!NetworkUtils.isAvailable(this)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isDaubed = 0;
                    Toast.makeText(TakePhoteActivity.this, R.string.no_net, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            return;
        }
        if (null == bitmap) {
            isDaubed = 0;
            return;
        }


        if (rg_tag != 2 && TextUtils.equals(from, to)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isDaubed = 0;
                    Toast.makeText(TakePhoteActivity.this, R.string.no_same, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            return;
        }

        if (rg_tag != 2 && (TextUtils.equals(from, "jpa") || TextUtils.equals(from, "jpa_v")) && TextUtils.equals("jpa", to)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isDaubed = 0;
                    Toast.makeText(TakePhoteActivity.this, R.string.no_same, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                guaguaka.setVisibility(View.GONE);
            }
        });

//        OkGo.<String>post(et_test4.getText().toString().equals("") ? ContentProviderUtil.getHost(this) : et_test4.getText().toString())
        OkGo.<String>post(ContentProviderUtil.getHost(this))
//        OkGo.<String>post("http://192.168.50.242:6001")
                .tag(this)
                .headers("X-Progress-ID", UUID.randomUUID().toString().toLowerCase())
                .params("from", from)
                .params("to", to)
                .params("image", bitmap)
                .params("id", DevUtils.getSN())
                .params("type", type)
                .params("location", StringUtils.getLocation(latitude, longitude))
                //object 参数，与上边相同的省略
                .params("type", "general")
//                .params("image", bitmap)
//                .params("id", DevUtils.getSN(this))
                .params("location", StringUtils.getLocation(latitude, longitude))
                .params("locale", LanguageUtils.getLang())
//                .params("turn", "3")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (rg_tag == 1) {//当前选中的是文章
                            showOcr();//显示翻译
                            toArtical(response.body(), downX, downY);
                        } else if (rg_tag == 0) {//当前选中的是菜单
                            showOcr();//显示翻译
                            toMenu(response.body(), downX, downY);
                        } else if (rg_tag == 2) {//当前选中的是物体识别
                            toObject(response.body(), downX, downY);
                        }
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
                        if (rg_tag != 2) showGuaguaka();
                        Toast.makeText(TakePhoteActivity.this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 展示涂抹布局
     */
    private void showGuaguaka() {
        isDaubed = 0;
        ocrDisplayView.clear();//清空翻译布局
        ocrDisplayView.setVisibility(View.GONE);
        mCameraPreview.stop();
        dstView.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.GONE);
        focusView.setVisibility(View.GONE);
        rl_confirm.setVisibility(View.VISIBLE);
        iv_recamera.setVisibility(View.VISIBLE);
        iv_translation.setVisibility(View.VISIBLE);
//        iv_translation.setClickable(true);
        rl_line.setVisibility(View.GONE);
        fl_tran.removeAllViews();
        fl_tran.setVisibility(View.GONE);
        tv_back.setVisibility(View.INVISIBLE);
        guaguaka.clear();
        guaguaka.setVisibility(View.VISIBLE);

    }

    /**
     * 展示翻译布局
     */
    private void showOcr() {
        gestureViewBinder.reset();
        ocrDisplayView.clear();//清空翻译布局
        guaguaka.clear();
        guaguaka.setVisibility(View.GONE);
        ocrDisplayView.setVisibility(View.VISIBLE);
        dstView.setVisibility(View.GONE);
        mCameraPreview.stop();
        mCameraPreview.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        focusView.setVisibility(View.GONE);
        rl_confirm.setVisibility(View.VISIBLE);
        iv_translation.setVisibility(View.GONE);
        rl_line.setVisibility(View.GONE);

    }


    private void reset() {

        mViewPager.setVisibility(View.GONE);

        isDaubed = 0;
        gestureViewBinder.reset();
        fl_tran.removeAllViews();
        fl_tran.setVisibility(View.GONE);
        focusView.setVisibility(View.VISIBLE);
        mCameraPreview.setCanFocus(true);
        tv_light.setVisibility(View.VISIBLE);
        rl_line.setVisibility(View.VISIBLE);
        rl_meau.setVisibility(View.VISIBLE);
        rl_camera.setVisibility(View.VISIBLE);
        rl_confirm.setVisibility(View.GONE);
        iv_recamera.setVisibility(View.GONE);
        iv_translation.setVisibility(View.GONE);
//        iv_translation.setClickable(true);
        iv_camera.setClickable(true);
        tv_back.setVisibility(View.INVISIBLE);
        dstView.setVisibility(View.GONE);
        guaguaka.clear();
        ocrDisplayView.clear();
        ocrDisplayView.setVisibility(View.GONE);
        OkGo.getInstance().cancelAll();

        mViewPager.setVisibility(View.GONE);

        if (isShow) {
            guaguaka.setVisibility(View.GONE);
            mCameraPreview.setVisibility(View.VISIBLE);
            mCameraPreview.start();
            isShow = false;
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
//                takePhoto();
                reset();
                Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                return;
            }
            if (bean.getResult().size() == 0) {
                reset();
                Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                return;

            }

            Log.e("path_uri", path_uri.toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("uri", path_uri.toString());
            intent.putExtra("result", result);
            intent.putExtra("x", x);
            intent.putExtra("y", y);
            startActivityForResult(intent, 1111);
        } else {
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
//            reset();
        }
        reset();
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
            ocrDisplayView.setBitmap(BitmapFactory.decodeFile(path));
            ocrDisplayView.setVisibility(View.GONE);
            fl_tran.removeAllViews();
            fl_tran.setVisibility(View.VISIBLE);
            Bitmap bm = PictureUtil.adjustPhotoRotation(BitmapFactory.decodeFile(path), 90 * fangxiang - 90);
            ImageView imageView = new ImageView(this);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            int asss = getResources().getColor(R.color.black_transparent);
            RequestOptions options = new RequestOptions().transform(new ColorFilterTransformation(asss));
            Glide.with(this)
                    .load(bm)
                    .apply(options)
                    .into(imageView);
            fl_tran.addView(imageView);

            FrameLayout frameLayout = new FrameLayout(this);
            FrameLayout.LayoutParams lll = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.setLayoutParams(lll);
            fl_tran.addView(frameLayout);
            fl_tran.setClipChildren(false);

            for (int i = 0; i < bean.getResult().size(); i++) {
                String words = bean.getResult().get(i).getTrans_words();
                WordsResult result = new WordsResult();
                result.setHeight(StringUtils.getRealHeight(bean.getResult().get(i).getLocation().getHeight()));
                result.setLeft(StringUtils.getRealWidth(bean.getResult().get(i).getLocation().getX()) + (int) downX);
                result.setTop(StringUtils.getRealHeight(bean.getResult().get(i).getLocation().getY()) + (int) downY);
                result.setWidth(StringUtils.getRealWidth(bean.getResult().get(i).getLocation().getWidth()));
                result.setWords(words);

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(result.getWidth(), FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(result.getLeft(), result.getTop() + 1, 0, 0);
                SingleLineZoomTextView textView = new SingleLineZoomTextView(this);
                if (TextUtils.equals("1_v", bean.getMode())) {//竖排文字
                    textView.setRotation(90);
                    textView.setPivotX(result.getWidth());
                    textView.setPivotY(0);
                    layoutParams = new FrameLayout.LayoutParams(result.getHeight(), FrameLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(result.getLeft(), result.getTop() + result.getWidth(), 0, 0);
                }
                textView.setLayoutParams(layoutParams);
                textView.setText(result.getWords());
                textView.setMaxWidth(DisplayUtil.getWidthInPx(this) - result.getLeft());
                textView.setPadding(0, 1, 0, 1);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextColor(getResources().getColor(R.color.white));
                frameLayout.addView(textView);

            }
            if (fangxiang == 0)
                startPropertyAnim(frameLayout, -90, StringUtils.getRealWidth(bm.getHeight() / 2), StringUtils.getRealHeight(bm.getHeight() / 2));
            else if (fangxiang == 2)
                startPropertyAnim(frameLayout, 90, StringUtils.getRealWidth(bm.getWidth() / 2), StringUtils.getRealHeight(bm.getWidth() / 2));
            else if (fangxiang == 3)
                startPropertyAnim(frameLayout, 180, StringUtils.getRealWidth(bm.getWidth() / 2), StringUtils.getRealHeight(bm.getHeight() / 2));
            else
                startPropertyAnim(frameLayout, 0, 0, 0);
        } else {
            Toast.makeText(this, bean.getError_message() + "", Toast.LENGTH_SHORT).show();
            reset();
        }

    }

    public void toObject(String response, float downX, float downY) {
        rl_confirm.setVisibility(View.GONE);//此处为临时办法
        mCameraPreview.stop();
        dialog.cancle();
        ObjectResponseBean bean = FastJsonUtil.changeJsonToBean(response, ObjectResponseBean.class);
        if (null != bean.getData() && bean.getData().size() > 0) {
            tv_back.setVisibility(View.VISIBLE);
            List<CardItem> cardItemList = new ArrayList<>();
            for (int i = 0; i < bean.getData().size(); i++) {
//                                cardItemList.add(new CardItem(bean.getData().get(i).getDetail(), bean.getData().get(i).getKeyword(),bean.getData().get(i).getImgUrl(),bean.getData().get(i).getAudioUrl()));
                cardItemList.add(new CardItem(bean.getData().get(i).getText(), bean.getData().get(i).getKeyword(), bean.getData().get(i).getPic(), bean.getData().get(i).getBaike()));
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


    /**
     * 发送广播拦截长右滑动事件
     */
    private void sendInvalidBroadCast() {
        Intent intent = new Intent();
        intent.setAction("com.aibabel.broadcast.fyttranslate");
        intent.putExtra("msg", "Scroll_Invalid");//发送广播
        sendBroadcast(intent);
    }

    /**
     * 发送广播解锁右滑动事件
     */
    private void sendEffectBroadCast() {
        Intent intent = new Intent();
        intent.setAction("com.aibabel.broadcast.fyttranslate");
        intent.putExtra("msg", "Scroll_TakeEffect");//发送广播
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        myOrientationEventListener.disable();
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
                        if (lastFangxiang == 0)
                            startPropertyAnim(iv_camera, 270f, 360f);
                        else if (lastFangxiang == 2)
                            startPropertyAnim(iv_camera, 90f, 0f);
                    }
                } else if ((rotation > 45) && (rotation <= 135)) {
                    if (direction == 1) {
                        Log.e(TAG, "onOrientationChanged: " + "竖屏变横屏4");
                        direction = 0;
                        fangxiang = 0;
                        Log.e(TAG, "getDushu: fangxiang form" + lastFangxiang + "to" + fangxiang);
                        if (lastFangxiang == 3)
                            startPropertyAnim(iv_camera, 180f, 270f);
                        else if (lastFangxiang == 1)
                            startPropertyAnim(iv_camera, 360f, 270f);
                    }
                } else if ((rotation > 135) && (rotation <= 225)) {
                    if (direction == 0) {
                        Log.e(TAG, "onOrientationChanged: " + "横屏变竖屏3");
                        direction = 1;
                        fangxiang = 3;
                        Log.e(TAG, "getDushu: fangxiang form" + lastFangxiang + "to" + fangxiang);
                        if (lastFangxiang == 2)
                            startPropertyAnim(iv_camera, 90f, 180f);
                        else if (lastFangxiang == 0)
                            startPropertyAnim(iv_camera, 270f, 180f);
                    }
                } else if ((rotation > 225) && (rotation <= 315)) {
                    if (direction == 1) {
                        Log.e(TAG, "onOrientationChanged: " + "竖屏变横屏2");
                        direction = 0;
                        fangxiang = 2;
                        Log.e(TAG, "getDushu: fangxiang form" + lastFangxiang + "to" + fangxiang);
                        if (lastFangxiang == 1)
                            startPropertyAnim(iv_camera, 0f, 90f);
                        else if (lastFangxiang == 3)
                            startPropertyAnim(iv_camera, 180f, 90f);
                    }
                }
            }
        };
    }

    // 动画实际执行
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

    // 动画实际执行
    private void startPropertyAnim(View view, float from, float to) {
        lastFangxiang = fangxiang;
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", from, to);
        // 动画的持续时间，执行多久？
        anim.setDuration(500);

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

    public void changeServerAddress() {
        addr = View.inflate(this, R.layout.layout_serveraddress, null);
        window = new PopupWindow(addr, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(false);
        radioGroup = addr.findViewById(R.id.rg_addr);
        et_test4 = addr.findViewById(R.id.et_test4);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_test1:
                        et_test4.setText("http://test_st_bj.ali.aibabel.cn");
                        break;
                    case R.id.rb_test2:
                        et_test4.setText("http://test_st_hk.ali.aibabel.cn");
                        break;
                    case R.id.rb_test3:
                        et_test4.setText("http://test_st_jp.ali.aibabel.cn");
                        break;
                    case R.id.rb_test4:
                        et_test4.setText("http://test_st_bj.aws.aibabel.cn");
                        break;
                    case R.id.rb_test5:
                        et_test4.setText("http://test_st_sg.aws.aibabel.cn");
                        break;
                    case R.id.rb_test6:
                        et_test4.setText("http://test_st_jp.aws.aibabel.cn");
                        break;
                    case R.id.rb_test7:
                        et_test4.setText(ContentProviderUtil.getHost(TakePhoteActivity.this));
                        break;
                }
            }
        });
        b_addr = findViewById(R.id.b_addr);
        b_queding = addr.findViewById(R.id.b_queding);
        b_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!window.isShowing())
                    showChangeAddr();

            }
        });
        b_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePrefUtil.saveString(TakePhoteActivity.this, "ceshifuwuqi", et_test4.getText().toString());
                window.dismiss();
            }
        });
    }

    public void showChangeAddr() {
        et_test4.setText(SharePrefUtil.getString(TakePhoteActivity.this, "ceshifuwuqi", ""));
        window.showAtLocation(root_layout, Gravity.CENTER, 0, 0);
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
}
