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
import java.util.List;


public class MainActivity extends BaseActivity {
    private ListView listView;
    private MyAdapter adapter=null;

    List menuList=new ArrayList(){{
        add("yyfy_"+MyApplication.mContext.getString(R.string.yuyinfanyi));
        add("jqdl_"+MyApplication.mContext.getString(R.string.jingqudaolan));
        add("mdd_"+MyApplication.mContext.getString(R.string.mudidi));
        add("bdzy_"+MyApplication.mContext.getString(R.string.bendiziyuna));
    }};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        L.e("是debug======================="+ CommonUtils.isApkInDebug(mContext));
        URL_API.getHost();
        if (savedInstanceState!=null) {
            //内存不足是  被回收的处理
            L.e("被回收处理===================================");
            MyApplication.dbHelper.shutdownUPdateDB();

        }





        //检查是否有预装
        try {

            if (SDCardUtils.isExists("/sdcard/download_offline/list_json.txt")) {

                String json= FileUtil.readTxtFile("/sdcard/download_offline/list_json.txt");
                NeizhiList neizhiList= JSON.parseObject(json,NeizhiList.class);

                List<NeizhiList.ListFileBean> listFileBeans=neizhiList.getListFile();

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
                            contentValues.put("from_path",JSON.toJSONString(listFileBeans.get(i).getCopyPath()));
                            contentValues.put("to_path", "--");
                            contentValues.put("lan_name", listFileBeans.get(i).getAsName());
                            contentValues.put("lan_code", listFileBeans.get(i).getLan_code());
                            contentValues.put("down_start_time",System.currentTimeMillis());
                            contentValues.put("copy_start_time",0);
                            contentValues.put("uninstall_start_time",0);
                            contentValues.put("need_again_unzip","false");
                            contentValues.put("down_zip_filename",listFileBeans.get(i).getFileName());

                            contentValues.put("version_code",listFileBeans.get(i).getVersionCode());


                            if (MyApplication.dbHelper.queryID(listFileBeans.get(i).getId())) {
                                contentValues.remove("Id");
                                MyApplication.dbHelper.updateAll(listFileBeans.get(i).getFileName(),contentValues);
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
                        contentValues.put("from_path",JSON.toJSONString(listFileBeans.get(i).getCopyPath()));
                        contentValues.put("to_path", "--");
                        contentValues.put("lan_name", listFileBeans.get(i).getAsName());
                        contentValues.put("lan_code", listFileBeans.get(i).getLan_code());
                        contentValues.put("down_start_time",System.currentTimeMillis());
                        contentValues.put("copy_start_time",0);
                        contentValues.put("uninstall_start_time",0);
                        contentValues.put("need_again_unzip","false");
                        contentValues.put("down_zip_filename",listFileBeans.get(i).getFileName());

                        contentValues.put("version_code",listFileBeans.get(i).getVersionCode());


                        if (MyApplication.dbHelper.queryID(listFileBeans.get(i).getId())) {
                            contentValues.remove("Id");
                            MyApplication.dbHelper.updateAll(listFileBeans.get(i).getFileName(),contentValues);
                        } else {
                            MyApplication.dbHelper.insert(contentValues);
                        }
                    }


                }

                FileUtil.deleteFile("/sdcard/download_offline/list_json.txt");

            }


            L.e("MainActivity   onCreate====================");
            L.e("存储大小："+FileUtil.getInternalMemorySize(mContext),"================可用："+FileUtil.getAvailableInternalMemorySize(mContext));



        } catch (Exception e) {

        }




    }

    @Override
    protected void onResume() {
        LanUtil.setLan(this);

        if (CommonUtils.getDeviceInfo().equals("PM")) {
            if (menuList.size()!=2) {
                menuList=new ArrayList(){{
                    add("yyfy_"+MyApplication.mContext.getString(R.string.yuyinfanyi));

                }};
                if (adapter!=null) {
                    adapter.fresh(menuList);
                }
            }
            //租赁版不显示目的地
        }else if(Build.DISPLAY.substring(9,10).equals("L")){
            menuList=new ArrayList(){{
                add("yyfy_"+MyApplication.mContext.getString(R.string.yuyinfanyi));
                add("jqdl_"+MyApplication.mContext.getString(R.string.jingqudaolan));
                add("bdzy_"+MyApplication.mContext.getString(R.string.bendiziyuna));
            }};
            if (adapter!=null) {
                adapter.fresh(menuList);
            }
        }
        else if (DeviceUtils.getSystem() == DeviceUtils.System.FLY_TAIWAN){

            menuList.clear();
            menuList.add("yyfy_"+MyApplication.mContext.getString(R.string.yuyinfanyi));
            menuList.add("jqdl_"+MyApplication.mContext.getString(R.string.jingqudaolan));
            if (adapter!=null) {
                adapter.fresh(menuList);
            }
        }
        else{
            if (CommonUtils.getLocal(mContext).equals("zh_CN")) {
                if (menuList.size()<4) {
                    menuList=new ArrayList(){{
                        add("yyfy_"+MyApplication.mContext.getString(R.string.yuyinfanyi));
                        add("jqdl_"+MyApplication.mContext.getString(R.string.jingqudaolan));
                        add("mdd_"+MyApplication.mContext.getString(R.string.mudidi));
                        add("bdzy_"+MyApplication.mContext.getString(R.string.bendiziyuna));
                    }};
                    if (adapter!=null) {
                        adapter.fresh(menuList);
                    }
                }
            }else{
                if (menuList.size()!=2) {
                    menuList=new ArrayList(){{
                        add("yyfy_"+MyApplication.mContext.getString(R.string.yuyinfanyi));
                        add("bdzy_"+MyApplication.mContext.getString(R.string.bendiziyuna));
                    }};
                    if (adapter!=null) {
                        adapter.fresh(menuList);
                    }
                }

            }

        }

        super.onResume();
    }

    @Override
    protected void assignView() {
        listView=findViewById(R.id.menu_listview);
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

        listView.setAdapter(adapter=new MyAdapter<String>(mContext,menuList,R.layout.item_menu) {
            @Override
            public void convert(ViewHolder holder, String s) {

                final String[] arr=s.split("_");
                ImageView ivIcon=(ImageView) holder.getView(R.id.menu_img);
                ImageView ivJinru=(ImageView) holder.getView(R.id.menu_jinru);
                TextView tvName=(TextView) holder.getView(R.id.menu_name);

                RelativeLayout rl=(RelativeLayout) holder.getView(R.id.menu_rl);


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
                            if(arr[0].equals("yyfy")){
                                entenid = "download.offline_main1";
                            }else if(arr[0].equals("jqdl")){
                                entenid = "download.offline_main2";
                            }else if(arr[0].equals("bdzy")){
                                entenid = "download.offline_main3";
                            }
                            addStatisticsEvent(entenid, null);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /**####  end-hjs-addStatisticsEvent  ##**/

                        Intent intent=new Intent();
                        intent.putExtra("key",arr[0]);
                        intent.putExtra("name",arr[1]);
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


}