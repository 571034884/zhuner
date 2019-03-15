package com.aibabel.ocr.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aibabel.ocr.R;
import com.aibabel.ocr.bean.ResponseBean;
import com.aibabel.ocr.bean.WordsResult;
import com.aibabel.ocr.utils.DisplayUtil;
import com.aibabel.ocr.utils.FastJsonUtil;
import com.aibabel.ocr.utils.StringUtils;
import com.aibabel.ocr.widgets.SingleLineZoomTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

public class TakePhotoResultActivity extends BaseActivity {

//    @BindView(R.id.fl_tran)
    FrameLayout flTran;
    private int screenOritation;
    private String result;
    private float downX;
    private float downY;
    private String filePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        setRequestedOrientation(screenOritation);
        setContentView(R.layout.activity_take_photo_result);
        ButterKnife.bind(this);

        flTran = findViewById(R.id.fl_tran);

        toMenu(filePath, result, downX, downY);
    }

    public void getIntentData() {
        screenOritation = getIntent().getIntExtra("screenOritation", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        filePath = getIntent().getStringExtra("filePath");
        result = getIntent().getStringExtra("result");
        downX = getIntent().getFloatExtra("downX", 0f);
        downY = getIntent().getFloatExtra("downY", 0f);
    }

    /**
     * 识别翻译菜单
     *
     * @param response
     * @param downX
     * @param downY
     */
    private void toMenu(String path, String response, float downX, float downY) {
        ResponseBean bean = null;
        try {
            bean = FastJsonUtil.changeJsonToBean(response, ResponseBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == bean || null == bean.getResult()) {
//            reset();
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        if (bean.getResult().size() == 0) {
//            reset();
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
            return;

        }

        if (null != bean && null != bean.getResult() && bean.getResult().size() > 0) {
            flTran.removeAllViews();
            flTran.setVisibility(View.VISIBLE);
            SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    flTran.setBackground(resource);
                }
            };
            int asss = getResources().getColor(R.color.black_transparent);
            RequestOptions options = new RequestOptions().transform(new ColorFilterTransformation(asss));
            Bitmap bm = BitmapFactory.decodeFile(path);
            if (screenOritation==0){
                Matrix m = new Matrix();
                m.setRotate(-90, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            }
            Glide.with(this)
                    .load(bm)
                    .apply(options)
                    .into(simpleTarget);

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
                flTran.addView(textView);

            }

        } else {
            Toast.makeText(this, bean.getError_message() + "", Toast.LENGTH_SHORT).show();
//            reset();
        }

    }
}
