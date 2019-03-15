package com.aibabel.traveladvisory.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.MyApplication;
import com.aibabel.traveladvisory.utils.MyImageGetter;
import com.aibabel.traveladvisory.utils.ToastUtil;

import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

public class HtmlActivity extends BaseActivity {

    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;
    @BindView(R.id.tv_palce)
    TextView tvPalce;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    public int getLayout(Bundle savedInstanceState){
        return R.layout.activity_html;
    }

    @Override
    public void init() {
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        tvPalce.setText(getIntent().getStringExtra("place"));
        if (!TextUtils.equals("", getIntent().getStringExtra("html"))) {
//            final Spanned html = Html.fromHtml(getIntent().getStringExtra("html"),imgGetter, null);
//            runOnUiThread(new Runnable(){
//                public void run(){
//                    tvContent.setText(html);
//                }
//            });
//            tvContent.setText(Html.fromHtml(getIntent().getStringExtra("html")));
            tvContent.setText(Html.fromHtml(getIntent().getStringExtra("html"), new MyImageGetter(this, tvContent), null));
        } else {
            ToastUtil.show(this, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
        }
    }

    @OnClick(R.id.iv_fanhui)
    public void onViewClicked() {
        finish();
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "");  //获取网路图片
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    };
}
