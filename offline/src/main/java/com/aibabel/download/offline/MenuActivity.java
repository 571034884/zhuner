package com.aibabel.download.offline;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.download.offline.adapter.LanPagerAdapter;
import com.aibabel.download.offline.adapter.MyAdapter;
import com.aibabel.download.offline.adapter.ViewHolder;
import com.aibabel.download.offline.base.BaseActivity;
import com.aibabel.download.offline.bean.NeizhiList;
import com.aibabel.download.offline.fragment.InstalledFragment;
import com.aibabel.download.offline.fragment.ListdFragment;
import com.aibabel.download.offline.util.CommonUtils;
import com.aibabel.download.offline.util.FileSizeUtil;
import com.aibabel.download.offline.util.FileUtil;
import com.aibabel.download.offline.util.L;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MenuActivity extends BaseActivity {

private TabLayout tabLayout;
private ViewPager vp;
private List<Fragment> listFragment;
private List<String> titleList;


@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("=====================MainActivity onCreate");

        double  fileSize= FileSizeUtil.getFileOrFilesSize("/sdcard/download_offline/jqdl_ch.zip", 4);

        L.e("jqdl_ch======================================="+fileSize);

        }

        boolean isF = true;

@Override
protected void onResume() {
        L.e("=====================MainActivity onResume");
        setLan();


        if (isF) {
        isF = false;
        }
        super.onResume();
        }

@Override
public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        tabLayout.getTabAt(0).setText(getString(R.string.title_yaz));
        tabLayout.getTabAt(1).setText(getString(R.string.title_qbyy));
        }

@Override
protected void assignView() {
        setLan();
        tabLayout = findViewById(R.id.main_tablayout);
        vp = findViewById(R.id.main_viewpager);
        }

@Override
protected void initView() {
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(mContext, R.drawable.tablayout_center_line));
        linearLayout.setDividerPadding(15);
        }

@Override
protected void initListener() {

        }

@Override
protected int getLayoutId() {
        return R.layout.activity_main;
        }

@Override
protected void initData() {
        listFragment = new ArrayList() {{
        add(new InstalledFragment());
        add(new ListdFragment());
        }};
        titleList = new ArrayList() {{

        add(getString(R.string.title_yaz));
        add(getString(R.string.title_qbyy));
        }};
        LanPagerAdapter lanPagerAdapter = new LanPagerAdapter(getSupportFragmentManager(), listFragment, titleList);
        tabLayout.setTabsFromPagerAdapter(lanPagerAdapter);
        vp.setAdapter(lanPagerAdapter);
        tabLayout.setupWithViewPager(vp);

        }

@Override
protected void onStart() {
        L.e("=====================MainActivity onStart");
        setLan();
        super.onStart();
        }


        //国际化
        String[] lanArr = {"zh-CN", "zh-TW", "ja-JP", "ko-KR", "th-TH", "en-US"};

/**
 * 支持的国际化的语言
 *
 * @param lan
 * @return
 */
public String[] cantansLan(String lan) {
        for (int i = 0; i < lanArr.length; i++) {
        if (lanArr[i].indexOf(lan) != -1) {
        return lanArr[i].split("-");
        }
        }
        return "en-US".split("-");
        }


/**
 * 根据系统改变语言
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public void setLan() {
        try {
        String lan = Locale.getDefault().getLanguage();
        if (lan.indexOf("zh") != -1) {
        lan = "zh-" + Locale.getDefault().getCountry();
        }
        L.e("获取的系统语言》》》》》》" + lan);
        String[] newLan = cantansLan(lan);
        Resources resources = mContext.getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像
        Locale locale = new Locale(newLan[0], newLan[1]);
        config.setLocale(locale);
        resources.updateConfiguration(config, dm);

        } catch (Exception e) {
        Resources resources = mContext.getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像
        Locale locale = new Locale("en", "US");
        config.setLocale(locale);
        resources.updateConfiguration(config, dm);
        }

        }

@Override
protected void onDestroy() {
        L.e("MianActivity onDestroy========================");
        super.onDestroy();
        }


public void getJson() {
        NeizhiList neizhiList = new NeizhiList();
        List<NeizhiList.ListFileBean> listFileBeanList = new ArrayList<>();
        Map<String, String> yuyinMAp = new HashMap() {{
        put("ko_KR", "ko_KR.zip,197.06MB,韩语（Koresn）,0001");
        put("fr_FR", "fr_FR.zip,176.12MB,法语（French）,0001");
        put("ru_RU", "ru_RU.zip,191.36MB,俄语（Russian）,0001");

        }};
        for (String str : yuyinMAp.keySet()) {
        NeizhiList.ListFileBean bean=new NeizhiList.ListFileBean();
        bean.setId(str);
        String[] arr=yuyinMAp.get(str).split(",");
        bean.setFileName(arr[0]);
        bean.setFileSize(arr[1]);
        bean.setAsName(arr[2]);

        NeizhiList.ListFileBean.CopyPathBean oneBean=new NeizhiList.ListFileBean.CopyPathBean();
        oneBean.setZipPath("/sdcard/download_offline/"+arr[0]);
        oneBean.setUnZipPath("/sdcard/download_offline/"+str);

        String[] key=str.split("_");
        List<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean> list=new LinkedList<>();
        for (int i = 0; i < 3; i++) {
        NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean=new NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean();

        if (i == 0) {
        childFilesBean.setFileName("zh2" + key[0]);
        childFilesBean.setFromPath("/sdcard/download_offline/" + str + "/" + "zh2" + key[0]);
        childFilesBean.setToPath("/sdcard/NiuTransTransformer/");

        } else if (i == 1) {
        childFilesBean.setFileName(key[0] + "2zh");
        childFilesBean.setFromPath("/sdcard/download_offline/" + str + "/" + key[0] + "2zh");
        childFilesBean.setToPath("/sdcard/NiuTransTransformer/");
        } else if(i==2){
        childFilesBean.setFileName(key[0] +","+key[1]);
        childFilesBean.setFromPath("/sdcard/download_offline/" + str + "/" +key[0] +"-"+key[1]);
        childFilesBean.setToPath("/data/data/com.google.android.googlequicksearchbox/app_g3_models/");
        }
        switch (str) {
        case "ko_KR":
        bean.setLan_code("kor");
        break;
        case "ru_RU":
        bean.setLan_code("rus");
        break;
        case "fr_FR":
        bean.setLan_code("fr");
        break;
        }
        bean.setVersionCode(arr[3]);

        list.add(childFilesBean);

        }

        oneBean.setChildFiles(list);

        bean.setCopyPath(oneBean);
        listFileBeanList.add(bean);

        }


        //mdd
        Map<String, String> mddMAp = new HashMap() {{
        put("mdd_jp", "mdd_jp.zip,166MB,目的地-日本,0001");
        put("mdd_ko", "mdd_ko.zip,63.5MB,目的地-韩国,0001");
        put("mdd_sea", "mdd_sea.zip,378MB,目的地-东南亚,0001");
        put("mdd_europe_hot", "mdd_europe_hot.zip,745MB,目的地-欧洲热门,0001");
        put("mdd_north_europe", "mdd_north_europe.zip,33.6MB,目的地-北欧,0001");
        put("mdd_usa_ca", "mdd_usa_ca.zip,360MB,目的地-北美洲,0001");
        put("mdd_aus_new", "mdd_aus_new.zip,107MB,目的地-大洋洲,0001");

        }};


        for (String str : mddMAp.keySet()) {
        NeizhiList.ListFileBean bean=new NeizhiList.ListFileBean();
        bean.setId(str);
        String[] arr=mddMAp.get(str).split(",");
        bean.setFileName(arr[0]);
        bean.setFileSize(arr[1]);
        bean.setAsName(arr[2]);

        NeizhiList.ListFileBean.CopyPathBean oneBean=new NeizhiList.ListFileBean.CopyPathBean();
        oneBean.setZipPath("/sdcard/download_offline/"+arr[0]);
        oneBean.setUnZipPath("/sdcard/download_offline/"+str);

        NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean=new NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean();
        childFilesBean.setFileName(str);
        childFilesBean.setFromPath("/sdcard/download_offline/"+str);
        childFilesBean.setToPath("/sdcard/offline/mdd/");
        List<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean> list= new ArrayList<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean>();
        list.add(childFilesBean);
        oneBean.setChildFiles(list);
        bean.setCopyPath(oneBean);
        bean.setVersionCode(arr[3]);
        bean.setLan_code("mdd");
        listFileBeanList.add(bean);
        }


        //景区导览
        Map<String, String> jqdlMAp = new HashMap() {{

        put("jqdl_ru", "jqdl_ru.zip,503MB,景区导览-俄罗斯,0001");
        put("jqdl_fr", "jqdl_fr.zip,913MB,景区导览-法国,0001");
        put("jqdl_jp", "jqdl_jp.zip,928MB,景区导览-日本,0001");
        put("jqdl_th", "jqdl_th.zip,138MB,景区导览-泰国,0001");
        put("jqdl_it", "jqdl_it.zip,642MB,景区导览-意大利,0001");
        put("jqdl_ch", "jqdl_ch.zip,3.05GB,景区导览-中国大陆,0001");


        put("jqdl_aus", "jqdl_aus.zip,0,景区导览-澳大利亚,0001");
        put("jqdl_ko", "jqdl_ko.zip,0,景区导览-韩国,0001");
        put("jqdl_en", "jqdl_en.zip,0,景区导览-英国,0001");
        put("jqdl_usa", "jqdl_usa.zip,0,景区导览-美国,0001");

        }};


        for (String str : jqdlMAp.keySet()) {
        NeizhiList.ListFileBean bean=new NeizhiList.ListFileBean();
        bean.setId(str);
        String[] arr=jqdlMAp.get(str).split(",");
        bean.setFileName(arr[0]);
        bean.setFileSize(arr[1]);
        bean.setAsName(arr[2]);

        NeizhiList.ListFileBean.CopyPathBean oneBean=new NeizhiList.ListFileBean.CopyPathBean();
        oneBean.setZipPath("/sdcard/download_offline/"+arr[0]);
        oneBean.setUnZipPath("/sdcard/download_offline/"+str);

        NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean=new NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean();
        childFilesBean.setFileName(str);
        childFilesBean.setFromPath("/sdcard/download_offline/"+str);
        childFilesBean.setToPath("/sdcard/offline/jqdl/");
        List<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean> list= new ArrayList<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean>();
        list.add(childFilesBean);
        oneBean.setChildFiles(list);
        bean.setVersionCode(arr[3]);
        bean.setCopyPath(oneBean);
        bean.setLan_code("jqdl");
        listFileBeanList.add(bean);
        }

        neizhiList.setListFile(listFileBeanList);

        String  json= JSON.toJSONString(neizhiList);

        FileUtil.saveFile(json,"/sdcard/download_offline/list_json.txt");


        }
        }

