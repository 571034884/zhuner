package com.aibabel.ocr;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aibabel.ocr.activity.BaseActivity;
import com.aibabel.ocr.adapter.ViewPagerAdapter;
import com.aibabel.ocr.bean.ResponseBean;
import com.aibabel.ocr.utils.DisplayUtil;
import com.aibabel.ocr.utils.FastJsonUtil;
import com.aibabel.ocr.utils.StringUtils;
import com.aibabel.ocr.widgets.MyRelativeLayout;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back;
    private ImageView iv_image;
    private MyRelativeLayout rl_image;
    private ScrollView sv_image;
    float height = 0f;
    ViewPager viewPager;

    private LinearLayout ll_dots;
    private List<TextView> dots;
    private LinearLayout.LayoutParams lp_dots_normal, lp_dots_focused;
    private int oldPosition = 0;
    private int currentPosition = 0;

    private ViewPagerAdapter adapter;
    private List<View> listViews = null;
    private List<ResponseBean.ResultBean> list;
    private String result;
    private float x, y;
    private ResponseBean bean;
    private String mode;
    private int screenWidth, screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        height = DisplayUtil.getScreenHeight(MainActivity.this);
        init();
    }


    /**
     * 初始化数据
     */
    private void init() {
        tv_back = findViewById(R.id.tv_back);
        rl_image = findViewById(R.id.rl_image);
        iv_image = findViewById(R.id.iv_image);
        sv_image = findViewById(R.id.sv_image);
        viewPager = findViewById(R.id.viewpager);
        ll_dots = findViewById(R.id.ll_dots);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        String path = getIntent().getStringExtra("path");
        Log.e("path", path+"");
        if (TextUtils.isEmpty(result)) {
            result = getIntent().getStringExtra("result");
            x = getIntent().getFloatExtra("x", 0);
            if(x<0)x=0;
            y = getIntent().getFloatExtra("y", 0);
            bean = FastJsonUtil.changeJsonToBean(result, ResponseBean.class);
            mode = bean.getMode();
            list = bean.getResult();
        }
        Glide.with(this).load(BitmapFactory.decodeFile(path)).into(iv_image);
        tv_back.setOnClickListener(this);
        listViews = new ArrayList<View>();
        dots = new ArrayList<TextView>();
        ll_dots.removeAllViews();
        dots.removeAll(dots);

        //默认进入页面将第一个点设置为选中状态focus
        for (int i = 0; i < (list.size() > 5 ? 5 : list.size()); i++) {
            TextView v_dot = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_for_ocr_viewpager_point, null);
            LinearLayout.LayoutParams docParams;
            if (i == 0) {
                docParams = new LinearLayout.LayoutParams(20, 20);
                v_dot.setBackgroundResource(R.drawable.dot_focused);
                v_dot.setTextSize(10);
                v_dot.setText("1");
                int left =(int) (StringUtils.getRealWidth(list.get(i).getLocation().getX()) + x);
                if(left<0)left=0;
                int top = (int) (StringUtils.getRealHeight(list.get(i).getLocation().getY()) + y + currentPosition * 0.2);
                int width = StringUtils.getRealWidth(list.get(0).getLocation().getWidth());
                int realWidth = StringUtils.isExceed(left,width,MainActivity.this);
                int height = StringUtils.getRealWidth(list.get(0).getLocation().getHeight());

                rl_image.drawReat(left,top,realWidth,height);
//                rl_image.drawReat((int) (StringUtils.getRealWidth(list.get(i).getLocation().getX()) + x), (int) (StringUtils.getRealHeight(list.get(i).getLocation().getY()) + y + i * 0.2), StringUtils.getRealWidth(list.get(0).getLocation().getWidth()), StringUtils.getRealWidth(list.get(0).getLocation().getHeight()));
            } else {
                docParams = new LinearLayout.LayoutParams(15, 15);
                v_dot.setBackgroundResource(R.drawable.dot_normal);
            }
            docParams.setMargins(5, 0, 5, 0);
            v_dot.setLayoutParams(docParams);
            ll_dots.addView(v_dot);
            dots.add(v_dot);
        }
        if (TextUtils.equals(mode, "2")) {
            rl_image.setVisibility(View.GONE);
        }
        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_viewpage, null);
            TextView tv_original = view.findViewById(R.id.tv_original);
            tv_original.setText(list.get(i).getWords());
            TextView tv_translation = view.findViewById(R.id.tv_translation);
            tv_translation.setText(list.get(i).getTrans_words());
            listViews.add(view);

            //设置上方圆形flag
            TextView circleFlag = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_for_ocr_each_part, null);
            circleFlag.setText(i + 1 + "");
            circleFlag.setTag(i);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = (screenWidth - StringUtils.getRealWidth(list.get(i).getLocation().getX()) + x) > 30 ? (int) (StringUtils.getRealWidth(list.get(i).getLocation().getX()) + x) : screenWidth - 30;
            lp.topMargin = (screenHeight - StringUtils.getRealHeight(list.get(i).getLocation().getY())) + y > 30 ? (int) (StringUtils.getRealHeight(list.get(i).getLocation().getY()) + y + i * 0.2) : screenHeight - 30;
            circleFlag.setLayoutParams(lp);
            rl_image.addView(circleFlag);

        }

        //将第一个flag放置到层级的最顶层
        TextView textView = (TextView) rl_image.getChildAt(0);
        textView.setBackgroundResource(R.drawable.circle_shape_focus);
        rl_image.removeViewAt(0);
        rl_image.addView(textView);

        //将第一个滚动到视野中
        int yPoint = list.get(0).getLocation().getY();
        int areaHeight = screenHeight - 400;
        int bitmapHeight = rl_image.getHeight();
        if (yPoint > areaHeight / 2 && (bitmapHeight - yPoint > areaHeight / 2)) {
            sv_image.scrollTo(0, yPoint - areaHeight / 2);
        }

        adapter = new ViewPagerAdapter(listViews);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new PageChangeListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                setResult(2222);
                this.finish();
                break;
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            Logger.d(arg1 * height);

            int yPoint = (int) (list.get(arg0).getLocation().getY() + y);
            int areaHeight = screenHeight - 400;
            int bitmapHeight = rl_image.getHeight();
//            Log.e("yPoint", yPoint + "");
//            Log.e("areaHeight", areaHeight + "");
//            Log.e("bitmapHeight", bitmapHeight + "");
            if (yPoint > areaHeight / 2 && (bitmapHeight - yPoint > areaHeight / 2)) {
                sv_image.scrollTo(0, yPoint - areaHeight / 2);
            } else {
                sv_image.scrollTo(0, yPoint - areaHeight);
            }


        }

        @Override
        public void onPageSelected(int arg0) {
//            index = arg0;
            //当识别的部分超过5个时候，只用5个点显示
            if (list.size() > 5) {
                if (arg0 < 2) {
                    currentPosition = arg0;
                } else if (list.size() - arg0 > 2) {
                    currentPosition = 2;
                } else {
                    currentPosition = 5 - (list.size() - arg0);
                }
            } else {
                currentPosition = arg0 % list.size();
            }

//            currentPosition = arg0 % list.size();

            //将对应的位置背景变为focus形式
            lp_dots_normal = (LinearLayout.LayoutParams) (dots.get(oldPosition)).getLayoutParams();
            lp_dots_focused = (LinearLayout.LayoutParams) (dots.get(currentPosition)).getLayoutParams();
//            rl_image.drawReat((int) (StringUtils.getRealWidth(list.get(arg0).getLocation().getX()) + x), (int) (StringUtils.getRealHeight(list.get(arg0).getLocation().getY()) + y + currentPosition * 0.2), StringUtils.getRealWidth(list.get(arg0).getLocation().getWidth()), StringUtils.getRealWidth(list.get(arg0).getLocation().getHeight()));
            int left =(int) (StringUtils.getRealWidth(list.get(arg0).getLocation().getX()) + x);
            if(left<0)left=0;
            int top = (int) (StringUtils.getRealHeight(list.get(arg0).getLocation().getY()) + y + currentPosition * 0.2);
            int width = StringUtils.getRealWidth(list.get(arg0).getLocation().getWidth());
            int realWidth = StringUtils.isExceed(left,width,MainActivity.this);
            int height = StringUtils.getRealWidth(list.get(arg0).getLocation().getHeight());

            rl_image.drawReat(left,top, realWidth, height);

            lp_dots_normal.width = 15;
            lp_dots_normal.height = 15;
            lp_dots_focused.width = 25;
            lp_dots_focused.height = 25;

            dots.get(oldPosition).setText("");
            dots.get(currentPosition).setText(arg0 + 1 + "");
            dots.get(currentPosition).setTextSize(10);
            dots.get(oldPosition).setLayoutParams(lp_dots_normal);
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(currentPosition).setLayoutParams(lp_dots_focused);
            dots.get(currentPosition).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = currentPosition;

            //将要被划出位置对应的上方的flagview，重置为原来的位置
            TextView theLastOne = (TextView) rl_image.getChildAt(rl_image.getChildCount() - 1);
            theLastOne.setBackgroundResource(R.drawable.circle_shape);
            rl_image.removeViewAt(rl_image.getChildCount() - 1);
            rl_image.addView(theLastOne, (Integer) theLastOne.getTag());//index参数表示该位置的下一个，0表示relativeLayout本身

            //将要被划入位置对应的上方的view职位层级的最上层
            TextView textView = (TextView) rl_image.getChildAt(arg0);
            textView.setBackgroundResource(R.drawable.circle_shape_focus);
            rl_image.removeViewAt(arg0);
            rl_image.addView(textView);
        }

    }


}
