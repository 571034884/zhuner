package com.aibabel.sos.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.sos.R;
import com.aibabel.sos.adapter.CommomRecyclerAdapter;
import com.aibabel.sos.adapter.CommonRecyclerViewHolder;
import com.aibabel.sos.app.BaseActivity;
import com.aibabel.sos.bean.SosBean;
import com.aibabel.sos.custom.BackgroundDarkPopupWindow;
import com.aibabel.sos.utils.CommonUtils;
import com.aibabel.sos.utils.SosDbUtil;
import com.aibabel.sos.utils.WeizhiUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InformationItemActivity extends BaseActivity {

    //    @BindView(R.id.tv_city)
//    TextView tvCity;
    //    @BindView(R.id.sv)
//    TextView sv;
//    @BindView(R.id.iv_shijie)
//    ImageView ivShijie;
//    @BindView(R.id.ll_1)
//    LinearLayout ll1;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    String currentChengshi;

    Context context;
    @BindView(R.id.tv_left1)
    TextView tvLeft1;
    @BindView(R.id.iv_right1)
    ImageView ivRight1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //    @BindView(R.id.cl_root)
    View clRoot;
    private List<SosBean> informationBeans = new ArrayList<>();
    private List<SosBean> popList = new ArrayList<>();
    //    private ArrayList<String> strings = new ArrayList<>();
    private CommomRecyclerAdapter popAdapter;
    private CommomRecyclerAdapter rvAdapter;
    private View popu;
    private RecyclerView rvPopu;
    private BackgroundDarkPopupWindow popupWindow_city;
    private String tv_city;
    private String currentGuojia;

    private static final Uri CONTENT_URI_WY = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");

    @Override
    public int initLayout() {
        return R.layout.activity_information_item;
    }

    @Override
    public void init() {
        context = this;
        initTitle();
//        View view = findViewById(R.id.i_title);
        clRoot = findViewById(R.id.i_title);
        initData();
        initAdapter();
        initPopupwindow();

//        tvCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindowShow();
//                initAdapter_popu();
//            }
//        });
    }

    public void initTitle() {
        tvLeft1.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight1.setVisibility(View.VISIBLE);

        setDrawableRight(tvLeft1, R.mipmap.xiugaichengshixiala);
        tvLeft1.setCompoundDrawablePadding(4);
        tvTitle.setHint(getResources().getString(R.string.morenxianshi));
        tvTitle.setHintTextColor(getResources().getColor(R.color.gray66));
        tvLeft1.setTextColor(getResources().getColor(R.color.cfe5000));
        ivRight1.setImageResource(R.mipmap.shijie);

    }

    /**
     * 设置textview 的drawable属性
     *
     * @param attention
     * @param drawableId
     */
    private void setDrawableRight(TextView attention, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        attention.setCompoundDrawables(null, null, drawable, null);
    }


    private void initAdapter_popu() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //设置布局管理器
        rvPopu.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        popAdapter = new CommomRecyclerAdapter<SosBean>(this, popList, R.layout.rv_popu_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                tvLeft1.setText(popList.get(postion).getCs());
                currentChengshi = popList.get(postion).getCs();

                informationBeans = SosDbUtil.getLingshiguan(currentChengshi);
                rvAdapter.updateData(informationBeans);
                popupWindow_city.dismiss();
            }

        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, SosBean sosBean, int position) {
                TextView tv_popu_city = holder.getView(R.id.tv_popu_city);

                tv_popu_city.setText(sosBean.getCs());

                if (sosBean.getCs().equals(currentChengshi)) {
                    tv_popu_city.setBackgroundResource(R.drawable.popu_background_true);
                    tv_popu_city.setTextColor(getResources().getColor(R.color.red));
                }
            }
        };
        rvPopu.setAdapter(popAdapter);
    }

    /**
     * 初始化修改我方语言的popupwindow
     */
    private void initPopupwindow() {
        popu = View.inflate(InformationItemActivity.this, R.layout.popu_type, null);
        rvPopu = popu.findViewById(R.id.rv_popu);
        popupWindow_city = new BackgroundDarkPopupWindow(popu, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow_city.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_city.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow_city.setFocusable(true);//点击空白处关闭

    }


    /**
     * 底部弹出popupWindow
     */
    private void popupWindowShow() {

        popupWindow_city.setDarkStyle(-1);
        popupWindow_city.setDarkColor(Color.parseColor("#a0000000"));
        popupWindow_city.resetDarkPosition();
        popupWindow_city.darkBelow(clRoot);
        popupWindow_city.showAsDropDown(clRoot, clRoot.getRight() / 2, 0);
    }

    private void initAdapter() {
//        tvPurchase.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvList.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        rvAdapter = new CommomRecyclerAdapter<SosBean>(this, informationBeans, R.layout.information_recy_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {

            }

        }, null) {


            @Override
            public void convert(CommonRecyclerViewHolder holder, final SosBean sosBean, int position) {
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_country_number = holder.getView(R.id.tv_country_number);
                TextView tv_consulate_telephone = holder.getView(R.id.tv_consulate_telephone);
                TextView tv_consulate_address = holder.getView(R.id.tv_consulate_address);
                TextView tv_alarm_telephone = holder.getView(R.id.tv_alarm_telephone);
                TextView tv_emergency_telephone = holder.getView(R.id.tv_emergency_telephone);

                String lingshiguanxinxi = getResources().getString(R.string.lingshiguanxinxi);
                int begin, end;
                switch (CommonUtils.getLocalLanguage()) {
                    case "zh_CN":
                    case "zh_TW":
                        begin = lingshiguanxinxi.indexOf("[");
                        end = lingshiguanxinxi.indexOf("]") + 1;
                        tv_name.setText(lingshiguanxinxi.replace(lingshiguanxinxi.substring(begin, end), sosBean.getCs()));
                        break;
                    case "en":
                        tv_name.setText(lingshiguanxinxi + " " + sosBean.getCs());
                        break;
                    case "ja":
                        begin = lingshiguanxinxi.indexOf("[");
                        end = lingshiguanxinxi.indexOf("]") + 1;
                        tv_name.setText(lingshiguanxinxi.replace(lingshiguanxinxi.substring(begin, end), sosBean.getCs()));
                        break;
                    case "ko":
                        begin = lingshiguanxinxi.indexOf("[");
                        end = lingshiguanxinxi.indexOf("]") + 1;
                        tv_name.setText(lingshiguanxinxi.replace(lingshiguanxinxi.substring(begin, end), sosBean.getCs()));
                        break;
                }
//                        tv_name.setText("中国驻"+sosBean.getGj() + sosBean.getCs()+"领事馆信息");
                tv_country_number.setText(sosBean.getGjqh() + "");
                tv_consulate_telephone.setText(sosBean.getLsgdh());
                tv_consulate_address.setText(sosBean.getLsgdz());
                tv_alarm_telephone.setText(sosBean.getBjdh());
                tv_emergency_telephone.setText(sosBean.getJjdh());
                String display = Build.DISPLAY;
                final String version = display.substring(9, 10);
                tv_consulate_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String zb = WeizhiUtil.getInfo(mContext, WeizhiUtil.CONTENT_URI_WY, "latitude");
                        Log.e("init: ", "接收到定位服务的cursor" + zb);
                        String dw = WeizhiUtil.getInfo(mContext, WeizhiUtil.CONTENT_URI_WY, "country");
                        if (!TextUtils.equals(dw, "中国") && !TextUtils.equals(dw, "")&& !TextUtils.equals(zb, "") && !TextUtils.equals(zb, null))
                            turnToGoogleMap(zb, sosBean.getZb());
//                        try {
//                            if (TextUtils.equals(version, "S")) {//S售卖版 L租赁
//                                Cursor cursor = getContentResolver().query(CONTENT_URI_WY, null, null, null, null);
//                                cursor.moveToFirst();
//                                String zb = cursor.getString(cursor.getColumnIndex("latitude")) + "," + cursor.getString(cursor.getColumnIndex("longitude"));
//                                cursor.close();
//                                Log.e("init: ", "接收到定位服务的cursor" + zb);
//                                if (!TextUtils.equals(zb, "") && !TextUtils.equals(zb, null))
//                                    turnToGoogleMap(zb, sosBean.getZb());
//                            }
//                        } catch (Exception e) {
//                            Log.e("init: ", "没有接收到定位服务的cursor");
//                            e.printStackTrace();
//                        }
                    }
                });

            }
        };
        rvList.setAdapter(rvAdapter);
    }

    private void initData() {
        currentChengshi = getIntent().getStringExtra("cs");
        currentGuojia = getIntent().getStringExtra("gj");
        popList = SosDbUtil.getChengshi(currentGuojia);
        if (currentChengshi == null) {
            currentChengshi = currentGuojia;
            informationBeans = SosDbUtil.getLingshiguanInGuojia(currentGuojia);
        } else {
            informationBeans = SosDbUtil.getLingshiguan(currentChengshi);
        }
        tvLeft1.setText(currentChengshi);

//        for (int i = 0; i < 5; i++) {
//            informationBeans.add(new InformationBean("韩国济州岛中国领事馆信息", "82", "10-6576-8838", "济州特别自治道济州市厅舍路1条10号", "112", "119"));
//        }

//        for (int i = 0; i < 2; i++) {
//            strings.add("吴清华" + i);
//            strings.add("孙舒恒" + i);
//            strings.add("张文颖" + i);
//            strings.add("张月" + i);
//            strings.add("李梅" + i);
//            strings.add("李琪" + i);
//        }
    }

//    @OnClick({R.id.sv, R.id.iv_shijie})
//    public void onViewClicked(View view) {
//        Intent intent = null;
//        switch (view.getId()) {
//            case R.id.sv:
//                intent = new Intent(InformationItemActivity.this, SousuoActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.iv_shijie:
//                intent = new Intent(InformationItemActivity.this, WorldCountryActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }

    public void turnToGoogleMap(String from, String to) {
        if (isAvilible(context, "com.google.android.apps.maps")) {
//            Uri gmmIntentUri = Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr= 30.6739968716,103.9602246880 &daddr=30.6798861599,103.9739656448&hl=zh");
            Uri gmmIntentUri = Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr= " + from + " &daddr=" + to + "&hl=zh");
//            "google.navigation:q="
//                    + mLatitude + "," + mLongitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "您尚未安装谷歌地图", Toast.LENGTH_LONG)
                    .show();
        }

    }

    /*
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    @OnClick({R.id.tv_left1, R.id.iv_right1, R.id.tv_title})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_left1:
                popupWindowShow();
                initAdapter_popu();
                break;
            case R.id.tv_title:
                intent = new Intent(InformationItemActivity.this, SousuoActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_right1:
                intent = new Intent(InformationItemActivity.this, WorldCountryActivity.class);
                startActivity(intent);
                break;
        }
    }

}
