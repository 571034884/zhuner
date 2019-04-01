package com.aibabel.download.offline;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.download.offline.adapter.MyAdapter;
import com.aibabel.download.offline.adapter.ViewHolder;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.base.BaseActivity;
import com.aibabel.download.offline.bean.NeizhiList;
import com.aibabel.download.offline.bean.Offline_database;
import com.aibabel.download.offline.util.CommonUtils;
import com.aibabel.download.offline.util.FileUtil;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.LanUtil;
import com.aibabel.download.offline.util.SDCardUtils;
import com.aibabel.download.offline.util.URL_API;
import com.alibaba.fastjson.JSON;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {
    private ListView listView;
    private MyAdapter adapter = null;

    List menuList = new ArrayList() {{
        add("yyfy_" + MyApplication.mContext.getString(R.string.yuyinfanyi));
        if (MyApplication.ifshowjqdl)
            add("jqdl_" + MyApplication.mContext.getString(R.string.jingqudaolan));
        add("mdd_" + MyApplication.mContext.getString(R.string.mudidi));
        add("bdzy_" + MyApplication.mContext.getString(R.string.bendiziyuna));
    }};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        L.e("是debug=======================" + CommonUtils.isApkInDebug(mContext));
        URL_API.getHost();
        if (savedInstanceState != null) {
            //内存不足是  被回收的处理
            L.e("被回收处理===================================");
            MyApplication.dbHelper.shutdownUPdateDB();

        }
        //getJson();

        //检查是否有预装
        try {
            Log.e("hjs", "list_json.txt=" + SDCardUtils.isExists("/sdcard/download_offline/list_json.txt"));
            if (SDCardUtils.isExists("/sdcard/download_offline/list_json.txt")) {
                String json = FileUtil.readTxtFile("/sdcard/download_offline/list_json.txt");
                NeizhiList neizhiList = JSON.parseObject(json, NeizhiList.class);

                List<NeizhiList.ListFileBean> listFileBeans = neizhiList.getListFile();

                for (int i = 0; i < listFileBeans.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    //租赁版目的地预装不显示
                    if (Build.DISPLAY.substring(9, 10).equals("L")) {

                        if (!listFileBeans.get(i).getId().contains("mdd")) {
                            contentValues.put("Id", listFileBeans.get(i).getId());
                            contentValues.put("name", listFileBeans.get(i).getFileName());
                            contentValues.put("size", listFileBeans.get(i).getFileSize());
                            contentValues.put("progress", "0%");
                            if (listFileBeans.get(i).getFileSize().equals("0")) {
                                contentValues.put("status", "99");
                            } else {
                                contentValues.put("status", "10");
                            }
                            contentValues.put("down_url", "");
                            contentValues.put("from_path", JSON.toJSONString(listFileBeans.get(i).getCopyPath()));
                            contentValues.put("to_path", "--");
                            contentValues.put("lan_name", listFileBeans.get(i).getAsName());
                            contentValues.put("lan_code", listFileBeans.get(i).getLan_code());
                            contentValues.put("down_start_time", System.currentTimeMillis());
                            contentValues.put("copy_start_time", 0);
                            contentValues.put("uninstall_start_time", 0);
                            contentValues.put("need_again_unzip", "false");
                            contentValues.put("down_zip_filename", listFileBeans.get(i).getFileName());

                            contentValues.put("version_code", listFileBeans.get(i).getVersionCode());


                            if (MyApplication.dbHelper.queryID(listFileBeans.get(i).getId())) {
                                contentValues.remove("Id");
                                MyApplication.dbHelper.updateAll(listFileBeans.get(i).getFileName(), contentValues);
                            } else {
                                MyApplication.dbHelper.insert(contentValues);
                            }
                        }

                    } else {
                        contentValues.put("Id", listFileBeans.get(i).getId());
                        contentValues.put("name", listFileBeans.get(i).getFileName());
                        contentValues.put("size", listFileBeans.get(i).getFileSize());
                        contentValues.put("progress", "0%");
                        if (listFileBeans.get(i).getFileSize().equals("0")) {
                            contentValues.put("status", "99");
                        } else {
                            contentValues.put("status", "10");
                        }
                        contentValues.put("down_url", "");
                        contentValues.put("from_path", JSON.toJSONString(listFileBeans.get(i).getCopyPath()));
                        contentValues.put("to_path", "--");
                        contentValues.put("lan_name", listFileBeans.get(i).getAsName());
                        contentValues.put("lan_code", listFileBeans.get(i).getLan_code());
                        contentValues.put("down_start_time", System.currentTimeMillis());
                        contentValues.put("copy_start_time", 0);
                        contentValues.put("uninstall_start_time", 0);
                        contentValues.put("need_again_unzip", "false");
                        contentValues.put("down_zip_filename", listFileBeans.get(i).getFileName());

                        contentValues.put("version_code", listFileBeans.get(i).getVersionCode());


                        if (MyApplication.dbHelper.queryID(listFileBeans.get(i).getId())) {
                            contentValues.remove("Id");
                            MyApplication.dbHelper.updateAll(listFileBeans.get(i).getFileName(), contentValues);
                        } else {
                            MyApplication.dbHelper.insert(contentValues);
                        }
                    }


                }

                FileUtil.deleteFile("/sdcard/download_offline/list_json.txt");

            }


            L.e("MainActivity   onCreate====================");
            L.e("存储大小：" + FileUtil.getInternalMemorySize(mContext), "================可用：" + FileUtil.getAvailableInternalMemorySize(mContext));


        } catch (Exception e) {

        }

    }

    @Override
    protected void onResume() {
        LanUtil.setLan(this);

        if (CommonUtils.getDeviceInfo().equals("PM")) {
            if (menuList.size() != 2) {
                menuList = new ArrayList() {{
                    add("yyfy_" + MyApplication.mContext.getString(R.string.yuyinfanyi));

                }};
                if (adapter != null) {
                    adapter.fresh(menuList);
                }
            }
            //租赁版不显示目的地
        } else if (Build.DISPLAY.substring(9, 10).equals("L")) {
            menuList = new ArrayList() {{
                add("yyfy_" + MyApplication.mContext.getString(R.string.yuyinfanyi));
                // TODO: 2019/3/30  租赁版，新版景区导览暂时不显示景区导览，具体时间待定

                if (MyApplication.ifshowjqdl) {
                    add("jqdl_" + MyApplication.mContext.getString(R.string.jingqudaolan));
                }
                add("bdzy_" + MyApplication.mContext.getString(R.string.bendiziyuna));
            }};
            if (adapter != null) {
                adapter.fresh(menuList);
            }
        } else if (DeviceUtils.getSystem() == DeviceUtils.System.FLY_TAIWAN) {

            menuList.clear();
            menuList.add("yyfy_" + MyApplication.mContext.getString(R.string.yuyinfanyi));
            menuList.add("jqdl_" + MyApplication.mContext.getString(R.string.jingqudaolan));
            if (adapter != null) {
                adapter.fresh(menuList);
            }
        } else {
            if (CommonUtils.getLocal(mContext).equals("zh_CN")) {
                if (menuList.size() < 4) {
                    menuList = new ArrayList() {{
                        add("yyfy_" + MyApplication.mContext.getString(R.string.yuyinfanyi));
                        add("jqdl_" + MyApplication.mContext.getString(R.string.jingqudaolan));
                        add("mdd_" + MyApplication.mContext.getString(R.string.mudidi));
                        add("bdzy_" + MyApplication.mContext.getString(R.string.bendiziyuna));
                    }};
                    if (adapter != null) {
                        adapter.fresh(menuList);
                    }
                }
            } else {
                if (menuList.size() != 2) {
                    menuList = new ArrayList() {{
                        add("yyfy_" + MyApplication.mContext.getString(R.string.yuyinfanyi));
                        add("bdzy_" + MyApplication.mContext.getString(R.string.bendiziyuna));
                    }};
                    if (adapter != null) {
                        adapter.fresh(menuList);
                    }
                }

            }

        }

        super.onResume();
    }

    @Override
    protected void assignView() {
        listView = findViewById(R.id.menu_listview);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void initData() {

        listView.setAdapter(adapter = new MyAdapter<String>(mContext, menuList, R.layout.item_menu) {
            @Override
            public void convert(ViewHolder holder, String s) {

                final String[] arr = s.split("_");
                ImageView ivIcon = (ImageView) holder.getView(R.id.menu_img);
                ImageView ivJinru = (ImageView) holder.getView(R.id.menu_jinru);
                TextView tvName = (TextView) holder.getView(R.id.menu_name);

                RelativeLayout rl = (RelativeLayout) holder.getView(R.id.menu_rl);


                switch (arr[0]) {
                    case "yyfy":
                        setPathParams("语音翻译");
                        ivIcon.setImageResource(R.mipmap.yyfy);
                        break;
                    case "jqdl":
                        ivIcon.setImageResource(R.mipmap.jqdl);
                        setPathParams("景区导览");
                        break;
                    case "mdd":
                        ivIcon.setImageResource(R.mipmap.mdd);
                        setPathParams("目的地");
                        break;
                    case "bdzy":
                        ivIcon.setImageResource(R.mipmap.bendiziyuan);
                        setPathParams("本地资源");

                        break;
                }

                tvName.setText(arr[1]);
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /**####  start-hjs-addStatisticsEvent   ##**/
                        try {
                            String entenid = "";
                            if (arr[0].equals("yyfy")) {
                                entenid = "download.offline_main1";
                            } else if (arr[0].equals("jqdl")) {
                                entenid = "download.offline_main2";
                            } else if (arr[0].equals("bdzy")) {
                                entenid = "download.offline_main3";
                            }
                            addStatisticsEvent(entenid, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /**####  end-hjs-addStatisticsEvent  ##**/

                        Intent intent = new Intent();
                        intent.putExtra("key", arr[0]);
                        intent.putExtra("name", arr[1]);
                        if (arr[0].equals("bdzy")) {
                            intent.setClass(mContext, LocalResourceActivity.class);
                        } else {
                            intent.setClass(mContext, DownLoadListActivity.class);
                        }
                        startActivity(intent);
                    }
                });
            }
        });

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
            NeizhiList.ListFileBean bean = new NeizhiList.ListFileBean();
            bean.setId(str);
            String[] arr = yuyinMAp.get(str).split(",");
            bean.setFileName(arr[0]);
            bean.setFileSize(arr[1]);
            bean.setAsName(arr[2]);

            NeizhiList.ListFileBean.CopyPathBean oneBean = new NeizhiList.ListFileBean.CopyPathBean();
            oneBean.setZipPath("/sdcard/download_offline/" + arr[0]);
            oneBean.setUnZipPath("/sdcard/download_offline/" + str);

            String[] key = str.split("_");
            List<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean> list = new LinkedList<>();
            for (int i = 0; i < 3; i++) {
                NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = new NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean();

                if (i == 0) {
                    childFilesBean.setFileName("zh2" + key[0]);
                    childFilesBean.setFromPath("/sdcard/download_offline/" + str + "/" + "zh2" + key[0]);
                    childFilesBean.setToPath("/sdcard/NiuTransTransformer/");

                } else if (i == 1) {
                    childFilesBean.setFileName(key[0] + "2zh");
                    childFilesBean.setFromPath("/sdcard/download_offline/" + str + "/" + key[0] + "2zh");
                    childFilesBean.setToPath("/sdcard/NiuTransTransformer/");
                } else if (i == 2) {
                    childFilesBean.setFileName(key[0] + "," + key[1]);
                    childFilesBean.setFromPath("/sdcard/download_offline/" + str + "/" + key[0] + "-" + key[1]);
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
            NeizhiList.ListFileBean bean = new NeizhiList.ListFileBean();
            bean.setId(str);
            String[] arr = mddMAp.get(str).split(",");
            bean.setFileName(arr[0]);
            bean.setFileSize(arr[1]);
            bean.setAsName(arr[2]);

            NeizhiList.ListFileBean.CopyPathBean oneBean = new NeizhiList.ListFileBean.CopyPathBean();
            oneBean.setZipPath("/sdcard/download_offline/" + arr[0]);
            oneBean.setUnZipPath("/sdcard/download_offline/" + str);

            NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = new NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean();
            childFilesBean.setFileName(str);
            childFilesBean.setFromPath("/sdcard/download_offline/" + str);
            childFilesBean.setToPath("/sdcard/offline/mdd/");
            List<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean> list = new ArrayList<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean>();
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
            NeizhiList.ListFileBean bean = new NeizhiList.ListFileBean();
            bean.setId(str);
            String[] arr = jqdlMAp.get(str).split(",");
            bean.setFileName(arr[0]);
            bean.setFileSize(arr[1]);
            bean.setAsName(arr[2]);

            Log.e("hjs", "arr[0]==" + arr[0]);

            NeizhiList.ListFileBean.CopyPathBean oneBean = new NeizhiList.ListFileBean.CopyPathBean();
            oneBean.setZipPath("/sdcard/download_offline/" + arr[0]);
            oneBean.setUnZipPath("/sdcard/download_offline/" + str);

            NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = new NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean();
            childFilesBean.setFileName(str);
            childFilesBean.setFromPath("/sdcard/download_offline/" + str);
            childFilesBean.setToPath("/sdcard/offline/jqdl/");
            List<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean> list = new ArrayList<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean>();
            list.add(childFilesBean);
            oneBean.setChildFiles(list);
            bean.setVersionCode(arr[3]);
            bean.setCopyPath(oneBean);
            bean.setLan_code("jqdl");
            listFileBeanList.add(bean);
        }

        neizhiList.setListFile(listFileBeanList);

        String json = JSON.toJSONString(neizhiList);

        FileUtil.saveFile(json, "/sdcard/download_offline/list_json.txt");
    }
}