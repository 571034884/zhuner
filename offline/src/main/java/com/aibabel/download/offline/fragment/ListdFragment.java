package com.aibabel.download.offline.fragment;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.download.offline.R;
import com.aibabel.download.offline.adapter.MyAdapter;
import com.aibabel.download.offline.adapter.ViewHolder;
import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.bean.DownViewBean;
import com.aibabel.download.offline.bean.NeizhiList;
import com.aibabel.download.offline.bean.OfflineList;
import com.aibabel.download.offline.bean.Offline_database;
import com.aibabel.download.offline.dailog.ProgressDialog;
import com.aibabel.download.offline.service.UnZipInstallService;
import com.aibabel.download.offline.util.CommonUtils;
import com.aibabel.download.offline.util.FileUtil;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.LanUtil;
import com.aibabel.download.offline.util.SDCardUtils;
import com.aibabel.download.offline.util.T;
import com.aibabel.download.offline.util.ThreadPoolManager;
import com.aibabel.download.offline.util.URL_API;
import com.aibabel.download.offline.util.UrlUtil;
import com.aibabel.download.offline.util.Zip;
import com.aibabel.download.offline.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.jiangyy.easydialog.OtherDialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络资源  下载列表
 */
public class ListdFragment extends Fragment {


    private ListView listView;
    private SmartRefreshLayout refreshLayout;
    private List<String> list;
    private Map<String, DownViewBean> map_child;
    private MyAdapter myAdapter;
    private List<OfflineList.DataBeanX.DataBean> listData;
    Zip zip;
    long start, end;
    int dialog_title,dialog_desc ,dialog_desc1;
    private String curTag="";
    private Map<String,BaseDownloadTask>  downMap=new HashMap<>();

    //进度提示框
    ProgressDialog.Builder mBuilder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        L.e("ListdFragment onCreateView========================");


        initView(view);
        zip = new Zip();
        MyApplication.uiHandler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                switch (msg.what) {
                    case 200:
                        //系统拷贝完成通知
                        getOfflineList();

                        break;
                    case 300:
                        L.e("网络变化==================");
                        if (CommonUtils.isAvailable(MyApplication.mContext)) {
                            if (downloadTask != null) {
                                L.e("网络变化有网==================11111111111");
                                if (downloadTask.reuse()) {
                                    downloadTask.start();
                                }
                            }
                        } else {
                            if (mBuilder!=null) {
                                mBuilder.dismiss();
                                myAdapter.fresh(listData);
                            }
                        }


                        break;
                    case 400:
                        //解压安装完成  回调
                        getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (MyApplication.offlineNoticeUIHandler != null) {
                                                MyApplication.offlineNoticeUIHandler.sendEmptyMessage(100);
                                            }
                                            getOfflineList();
                                            if (!((String)msg.obj).equals("")) {
                                                T.show(getContext(), MyApplication.mContext.getString(R.string.anzhuangchengong)+ MyApplication.dbHelper.queryFiled((String)msg.obj, "lan_name"), 500);

                                            }

                                        }


                                    });
                        try {
                            if (mBuilder!=null) {
                                mBuilder.dismiss();
                            }
                            getActivity().stopService(intent);
                        } catch (Exception e) {

                        }

                        break;
                    case -400:
                        //解压安装失败  回调

                        break;
                    case 500:
                        //删除完成  回调
                        if (mBuilder!=null) {
                            mBuilder.dismiss();
                        }
                        if (!((String)msg.obj).equals("")) {
                            T.show(getActivity(), MyApplication.mContext.getString(R.string.xiezaichenggong) + MyApplication.dbHelper.queryFiled((String)msg.obj, "lan_name"), 500);
                        }
                        getOfflineList();
                        break;
                    case -500:
                        //删除失败  回调
                        if (mBuilder!=null) {
                            mBuilder.dismiss();
                        }
                        if (!((String)msg.obj).equals("")) {
                            T.show(getActivity(), MyApplication.mContext.getString(R.string.xiezaichenggong) + MyApplication.dbHelper.queryFiled((String)msg.obj, "lan_name"), 500);
                        }
                        getOfflineList();
                        break;
                }
            }
        };
        MyApplication.FileHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {


                switch (msg.what) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 100:
                        //下载完成


                        break;
                    case -100:

                        break;


                }


            }
        };
        return view;
    }


    public void initView(View view) {
        listView = view.findViewById(R.id.frag_list_listview);
        refreshLayout = view.findViewById(R.id.frag_list_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                getOfflineList();

            }
        });
        refreshLayout.setDisableContentWhenLoading(false);//是否在加载的时候禁止列表的操作
        map_child = new HashMap<>();
        getOfflineList();
        URL_API.getHost();
        RequestParams rp = new RequestParams(URL_API.getList_file());
        x.http().get(rp, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                OfflineList bean = JSON.parseObject(result, OfflineList.class);
                List<OfflineList.DataBeanX.DataBean> list = bean.getData().getData();

                for (int i = 0; i < list.size(); i++) {
                    L.e(listData.get(i).getId() + "==============" + MyApplication.dbHelper.queryID(listData.get(i).getId()));
                    if (!MyApplication.dbHelper.queryID(listData.get(i).getId())) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("Id", listData.get(i).getId());
                        contentValues.put("name", listData.get(i).getName());
                        contentValues.put("size", listData.get(i).getSize());
                        contentValues.put("progress", "0%");
                        contentValues.put("status", "99");
                        contentValues.put("down_url", "");
                        contentValues.put("from_path", listData.get(i).getCopy_path());
                        contentValues.put("to_path", "--");
                        contentValues.put("lan_name", listData.get(i).getLan_name());
                        contentValues.put("lan_code", listData.get(i).getLan_code());
                        contentValues.put("down_start_time", System.currentTimeMillis());
                        contentValues.put("copy_start_time", 0);
                        contentValues.put("uninstall_start_time", 0);
                        contentValues.put("need_again_unzip", "false");
                        contentValues.put("down_zip_filename", "");

                        contentValues.put("version_code", listData.get(i).getLan_code());

                        MyApplication.dbHelper.insert(contentValues);
                    }

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });


    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onResume() {
        L.e("ListFragment onresume==========================");

        try {
             //解决下载变安装的   国际化的问题
            if (MyApplication.isDialog_install==1) {
                mBuilder.setTitle(MyApplication.mContext.getString(R.string.anzhaungtishi))
                        .setDesc(MyApplication.mContext.getString(R.string.anzhaungtishi_content1)+MyApplication.mContext.getString(R.string.anzhaungtishi_content2));
                map_child.get(downloadTask.getTag()).getTv_status().setText(MyApplication.mContext.getString(R.string.zhengzaianzhuang));
                L.e("isDialog_install======="+MyApplication.mContext.getString(R.string.anzhaungtishi)+"======"+
                        MyApplication.mContext.getString(R.string.anzhaungtishi_content1)+"========="+MyApplication.mContext.getString(R.string.anzhaungtishi_content2)
                );
            }

            if (downloadTask !=null) {
                String tag=(String)downloadTask.getTag();
                if (!tag.equals("")) {
                    String status=MyApplication.dbHelper.queryStatus(tag);
                    switch (status) {
                        case "2":
                            long copyTime = MyApplication.dbHelper.queryFiledLong(tag, "down_start_time");
                            if (copyTime > 0 && System.currentTimeMillis() - copyTime > 12*60 * 60 * 1000) {

                                if (mBuilder!=null) {
                                    mBuilder.dismiss();
                                }
                            }
                            break;
                        case "3":
                             copyTime = MyApplication.dbHelper.queryFiledLong(tag, "copy_start_time");
                            if (copyTime > 0 && System.currentTimeMillis() - copyTime > 40 * 60 * 1000) {

                                if (mBuilder!=null) {
                                    mBuilder.dismiss();
                                }
                            }
                            break;
                    }

                }

            }
        } catch (Exception e){

        }
        super.onResume();

    }

    @Override
    public void onDestroy() {
        L.e("ListFragment ondestroy===================");

        try {
            getActivity().stopService(intent);
        } catch (Exception e) {
            L.e("unbindService==================="+e.getMessage());
        }

        if (mBuilder!=null) {
            mBuilder.dismiss();
        }

        try {
            Cursor cursor= MyApplication.dbHelper.queryCursor();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String status=cursor.getString( cursor.getColumnIndex("status"));
                    if(status.equals("2")){
                        MyApplication.dbHelper.updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "5");

                    } else  if (status.equals("3")) {
                        MyApplication.dbHelper.updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "5");
                        L.e("关机二次解压："+cursor.getString(cursor.getColumnIndex("Id")));
                        MyApplication.dbHelper.updateFiled(cursor.getString(cursor.getColumnIndex("Id")), "need_again_unzip", "true");

                    } else if (status.equals("11")) {
                        MyApplication.dbHelper.updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "12");
                        L.e("关机正在安装变为失败："+cursor.getString(cursor.getColumnIndex("Id")));
                    } else if (status.equals("13")) {

                        MyApplication.dbHelper.updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "14");
                        L.e("关机正在卸载变为失败："+cursor.getString(cursor.getColumnIndex("Id")));

                    }

                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception e) {

        }

        super.onDestroy();
    }


    //滑动删除控件  map
    Map<String,SwipeLayout> swipeLayoutMap=new HashMap<>();

    public void getOfflineList() {
        if (mBuilder!=null) {
            mBuilder.dismiss();
        }
        swipeLayoutMap.clear();
        listView.setAdapter(null);
        L.e("type=================="+getArguments().getString("key")+"====================="+URL_API.getList_file()+"&type="+((String)getArguments().getString("key")));
        RequestParams rp = new RequestParams(URL_API.getList_file()+"&type="+((String)getArguments().getString("key")));
//        RequestParams rp = new RequestParams(URL_API.getList_file()+"&type="+((String)getArguments().getString("key")+"&status=false"));

        x.http().get(rp, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                final OfflineList bean = JSON.parseObject(result, OfflineList.class);
                listData = bean.getData().getData();

                L.e("listData111111111============"+listData.toString());


                Cursor cursor = MyApplication.dbHelper.queryCursor();

                if (cursor.moveToFirst()) {
                    //遍历Cursor对象，取出数据
                    do {
                        String id = cursor.getString(cursor.getColumnIndex("Id"));
                        String status = cursor.getString(cursor.getColumnIndex("status"));
                        String prog = cursor.getString(cursor.getColumnIndex("progress"));

                        for (int i = 0; i < listData.size(); i++) {

                            if (!MyApplication.dbHelper.queryID(listData.get(i).getId())) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("Id", listData.get(i).getId());
                                contentValues.put("name", listData.get(i).getName());
                                contentValues.put("size", listData.get(i).getSize());
                                contentValues.put("progress", "0%");
                                contentValues.put("status", "99");
                                contentValues.put("down_url", "");
                                contentValues.put("from_path", listData.get(i).getCopy_path());
                                contentValues.put("to_path", "--");
                                contentValues.put("lan_name", listData.get(i).getLan_name());
                                contentValues.put("lan_code", listData.get(i).getLan_code());
                                contentValues.put("down_start_time", System.currentTimeMillis());
                                contentValues.put("copy_start_time", 0);
                                contentValues.put("uninstall_start_time", 0);
                                contentValues.put("need_again_unzip", "false");
                                contentValues.put("down_zip_filename", "");

                                contentValues.put("version_code", listData.get(i).getLan_code());

                                MyApplication.dbHelper.insert(contentValues);
                            }
                            if (isShow(listData.get(i).getId(), id, status)) {

                                listData.remove(i);
                            }

                        }
                    } while (cursor.moveToNext());


                }

                L.e("listData22222222============"+listData.toString());


                if (myAdapter == null) {
                    listView.setAdapter(new MyAdapter<OfflineList.DataBeanX.DataBean>(getActivity(), listData, R.layout.item_download) {
                        @Override
                        public void convert(ViewHolder holder, final OfflineList.DataBeanX.DataBean dataBean) {


                            final TextView tv_title = ((TextView) holder.getView(R.id.item_dl_title));
                            TextView tv_desc = ((TextView) holder.getView(R.id.item_dl_size));


                            final TextView tv_tishi = ((TextView) holder.getView(R.id.item_dl_tishi));
                            final ImageView iv_img = ((ImageView) holder.getView(R.id.item_dl_shibai_iv));
                            final TextView tv_id = ((TextView) holder.getView(R.id.item_dl_key));
                            tv_id.setText(dataBean.getId());
                            RelativeLayout rl = ((RelativeLayout) holder.getView(R.id.item_dl_rl));
                            final DownViewBean viewBean = new DownViewBean(dataBean.getId(),"","",dataBean.getLan_name(),rl,tv_title,tv_desc,tv_id,iv_img,tv_tishi);


                            tv_title.setText(dataBean.getLan_name());
                            tv_desc.setText( dataBean.getSize());
                            tv_tishi.setText(MyApplication.mContext.getString(R.string.xiazai));
                            map_child.put(dataBean.getId(), viewBean);

                            TextView tv_del=((TextView) holder.getView(R.id.item_dl_delect1));
                            SwipeLayout swipeLayout=((SwipeLayout) holder.getView(R.id.item_dl_swipeLayout));
                            swipeLayoutMap.put(dataBean.getId(),swipeLayout);
                            swipeLayout.setSwipeEnabled(false);

                            swipeLayout.addSwipeListener(new SimpleSwipeListener(){
                                @Override
                                public void onOpen(SwipeLayout layout) {
                                    L.e("kai ====================");
                                    for (String key : swipeLayoutMap.keySet()) {
                                        swipeLayoutMap.get(key).close();
                                    }

                                    layout.open();
                                    super.onOpen(layout);
                                }
                            });

                            tv_del.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new OtherDialog.Builder(getContext())
                                            .setGravity(Gravity.CENTER)
                                            .setContentView(R.layout.dialog_tishi)
                                            .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.shanchu))
                                            .setText(R.id.dialog_desc,MyApplication.mContext.getString(R.string.shanchu_content))
                                            .setCancelable(false)
                                            .setOnClickListener(R.id.dialog_tv_sure, MyApplication.mContext.getString(R.string.queding), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    mBuilder = new ProgressDialog.Builder(getActivity());
                                                    mBuilder.setCanceledOnTouchOutside(false)
                                                            .showProgress(true)
                                                            .setTitle(MyApplication.mContext.getString(R.string.shanchu_tishi))
                                                            .setDesc(MyApplication.mContext.getString(R.string.shanchu_tishi_content))
                                                            .showProgress(false)
                                                            .show();


                                                    deleteFile(new Offline_database(dataBean.getId(),"","","",dataBean.getCopy_path()),tv_tishi,"");



                                                }
                                            }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();


                                }
                            });




                            final String status = MyApplication.dbHelper.queryStatus(dataBean.getId());
                            if (status != null && !status.equals("") && MyApplication.dbHelper.queryID(dataBean.getId())) {
                                switch (status) {
                                    case "2":
                                        //正在下载



//                                        prog.setProgress(Integer.valueOf(MyApplication.dbHelper.queryProgess(dataBean.getId()).replace("%","")));
                                        tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaixiazai));

                                        long downstartTime = MyApplication.dbHelper.queryFiledLong(dataBean.getId(), "down_start_time");
                                        if (downstartTime > 0 && System.currentTimeMillis() - downstartTime > 12 * 60 * 60 * 1000) {
                                            //12小时后  算作下载失败

                                            tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));
                                            MyApplication.dbHelper.updateStatusId(dataBean.getId(), "5");
                                        }
//
//                                        iv_img.setVisibility(View.VISIBLE);


                                        break;
                                    case "3":
                                        //  正在安装
//                                       prog.setProgress(100);
                                        tv_tishi.setText(getString(R.string.zhengzaianzhuang));


                                        long copyTime = MyApplication.dbHelper.queryFiledLong(dataBean.getId(), "copy_start_time");
                                            if (copyTime > 0 && System.currentTimeMillis() - copyTime > 40 * 60 * 1000) {
                                                //后期做重新解压拷贝

                                                tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));
                                                MyApplication.dbHelper.updateStatusId(dataBean.getId(), "5");


                                            }
//                                        String needAgainUnzip = MyApplication.dbHelper.queryFiled(dataBean.getId(), "need_again_unzip");
//
//                                        //关机后的二次解压
//                                        MyApplication.dbHelper.updateFiled(dataBean.getId(), "need_again_unzip", "false");
//
//                                        final String zipFilename = MyApplication.dbHelper.queryFiled(dataBean.getId(), "down_zip_filename");
//                                        final String down_filename_path = SDCardUtils.getSDCardPath() + "/download_offline/" + zipFilename;
//                                        double fileSize = 0;
//                                        if (dataBean.getId().equals("jqdl_ch")) {
//                                            fileSize = FileSizeUtil.getFileOrFilesSize(down_filename_path, 4);
//                                            L.e("jqdl_ch=======================================" + fileSize);
//                                        } else {
//                                            fileSize = FileSizeUtil.getFileOrFilesSize(down_filename_path, 3);
//                                            L.e("other=======================================" + fileSize);
//                                        }
//                                        String size = MyApplication.dbHelper.queryFiled(dataBean.getId(), "size").replace("MB", "").replace("GB", "");
//                                        L.e("size==================================" + size);
//
//
//                                        if (fileSize >= Double.valueOf(size)) {
//
//                                        } else {
//                                            //后期做重新解压拷贝
////                                            prog.setProgress(Integer.valueOf(MyApplication.dbHelper.queryProgess(dataBean.getId()).replace("%","")));
//                                            tv_tishi.setText(getString(R.string.ms_xzsb));
//                                            MyApplication.dbHelper.updateStatusId(dataBean.getId(), "5");
//
//
//                                            return;
//                                        }
//
//                                        if (needAgainUnzip.equals("true")) {
//                                            if (!zipFilename.equals("")) {
//                                                unZipAndInstall(dataBean.getId(),dataBean.getName());
//
//                                            }
//
//
//                                        } else {
//
//                                            long copyTime = MyApplication.dbHelper.queryFiledLong(dataBean.getId(), "copy_start_time");
//                                            if (copyTime > 0 && System.currentTimeMillis() - copyTime > 40 * 60 * 1000) {
//                                                //后期做重新解压拷贝
////                                                prog.setProgress(0);
//                                                tv_tishi.setText(getString(R.string.ms_xzsb));
//                                                MyApplication.dbHelper.updateStatusId(dataBean.getId(), "5");
//
//
//                                            }
//                                        }
//
//
//                                        iv_img.setVisibility(View.VISIBLE);


                                        break;
                                    case "4":
                                        // 暂停
//                                        prog.setProgress(Integer.valueOf(MyApplication.dbHelper.queryProgess(dataBean.getId()).replace("%","")));
                                        tv_tishi.setText(getString(R.string.ms_zt));
                                        iv_img.setVisibility(View.VISIBLE);

                                        break;
                                    case "5":
                                        //下载失败
                                      swipeLayout.setSwipeEnabled(true);
//                                        prog.setProgress(Integer.valueOf(MyApplication.dbHelper.queryProgess(dataBean.getId()).replace("%","")));
                                        tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));
                                        iv_img.setVisibility(View.VISIBLE);

                                        break;
                                    default:
                                        iv_img.setVisibility(View.GONE);
//                                        prog.setProgress(0);


                                        break;

                                }
                            }

                            //下载点击事件
                            rl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (MyApplication.dbHelper.queryID(dataBean.getId())) {
//                                    T.show(getActivity(),"==================xizaizhuangtai>>>>"+MyApplication.dbHelper.queryStatus(dataBean.getId()),500);
                                        L.e("==================xizaizhuangtai>>>>" + MyApplication.dbHelper.queryStatus(dataBean.getId()));
                                        switch (MyApplication.dbHelper.queryStatus(dataBean.getId())) {
                                            case "2":
                                                T.show(getActivity(), tv_tishi.getText().toString(), 500);
                                                return;

                                            case "3":
                                                T.show(getActivity(), tv_tishi.getText().toString(), 500);
                                                return;
                                            case "4":
//
                                                tv_tishi.setText(getString(R.string.zhengzaixiazai));
                                                MyApplication.dbHelper.updateStatusId(dataBean.getId(), "2");


                                                return;
                                            case "5":

                                                String needAgainUnzip = MyApplication.dbHelper.queryFiled(dataBean.getId(), "need_again_unzip");

                                                if (needAgainUnzip.equals("true")&&new File("/sdcard/download_offline/"+dataBean.getName()+".zip").exists()) {
                                                    //直接做安装解压
                                                    MyApplication.dbHelper.updateFiled(dataBean.getId(), "need_again_unzip","false");

                                                    mBuilder = new ProgressDialog.Builder(getActivity());
                                                    mBuilder.setCanceledOnTouchOutside(false)
                                                            .showProgress(true)
                                                            .setTitle(MyApplication.mContext.getString(R.string.anzhaungtishi))
                                                            .setDesc(MyApplication.mContext.getString(R.string.anzhaungtishi_content1)+MyApplication.mContext.getString(R.string.anzhaungtishi_content2))
                                                            .showProgress(true)
                                                            .show();
                                                    mBuilder.setProgress(100);

                                                  unZipAndInstall(dataBean.getId(),dataBean.getName());


                                                } else {
                                                    //连接没失效续传  否则会重下

                                                        downloadFile(dataBean,status,tv_id.getText().toString());


                                                }

                                             return;

                                        }

                                    }

                                //下载
                                    downloadFile(dataBean,status,tv_id.getText().toString());
                                    StatisticsManager.getInstance(mContext).addEventAidl("下载"+dataBean.getName());



                                }
                            });

                        }
                    });

                } else {
                    myAdapter.fresh(listData);
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listView.setAdapter(null);
                refreshLayout.finishRefresh(true);
                if (CommonUtils.isAvailable(MyApplication.mContext)) {
                    T.show(MyApplication.mContext, MyApplication.mContext.getString(R.string.ts_sxsb), 500);
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                refreshLayout.finishRefresh(true);
            }
        });
    }


    public boolean isShow(String currId, String id, String status) {
        if (currId.equals(id) && status != null && status.equals("1")) {
            return true;
        } else if (currId.equals(id) && status != null && status.equals("10")) {
            return true;
        } else if (currId.equals(id) && status != null && status.equals("11")) {
            return true;
        } else if (currId.equals(id) && status != null && status.equals("12")) {
            return true;
        } else if (currId.equals(id) && status != null && status.equals("13")) {
            return true;
        } else if (currId.equals(id) && status != null && status.equals("14")) {
            return true;
        }

        return false;
    }



    //下载任务
    public BaseDownloadTask downloadTask;
    /**
     * 下载
     * @param dataBean
     */
    public void downloadFile(final OfflineList.DataBeanX.DataBean dataBean, final String status, final String  tv_id) {
        MyApplication.isDialog_install=0;
        if (!CommonUtils.isAvailable(MyApplication.mContext)) {
            T.show(MyApplication.mContext,MyApplication.mContext.getString(R.string.wuwangluo),500);
            return;

        }

        String sdSize=FileUtil.getAvailableInternalMemorySize(MyApplication.mContext);
        if (sdSize.endsWith("GB")) {
            long sum=FileUtil.sizeFormatString2Num(sdSize);
            long a=1024l*1024l*1024l*2l;
            long num=sum - a;
            long fileSize=(long)(FileUtil.sizeFormatString2Num(dataBean.getSize())* 2.5);
            if (num<fileSize) {
                T.show(MyApplication.mContext,MyApplication.mContext.getString(R.string.kongjianbuzu),500);
                return;
            }

        } else {
            T.show(MyApplication.mContext,MyApplication.mContext.getString(R.string.kongjianbuzu),500);
            return;
        }
        new OtherDialog.Builder(getContext())
                .setGravity(Gravity.CENTER)
                .setContentView(R.layout.dialog_tishi)
                .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.xiazaitishi))
                .setText(R.id.dialog_desc, MyApplication.mContext.getString(R.string.wenjiandaxiao)+dataBean.getSize()+MyApplication.mContext.getString(R.string.xiazaitishi_wifi))
                .setCancelable(false)
                .setOnClickListener(R.id.dialog_tv_sure, MyApplication.mContext.getString(R.string.queding), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mBuilder = new ProgressDialog.Builder(getActivity());
                        mBuilder.setCanceledOnTouchOutside(false)
                                .showProgress(true)
                                .setTitle(MyApplication.mContext.getString(R.string.xiazaitishi))
                                .setDesc(MyApplication.mContext.getString(R.string.xiazaitishi_content))
                                .showProgress(true)
                                .show();
                        mBuilder.setProgress(0);

                        RequestParams requestParams = new RequestParams(URL_API.get_download_url);
                        requestParams.addQueryStringParameter("fileName", dataBean.getName());
                        requestParams.addQueryStringParameter("sn", CommonUtils.getSN());

                        if (status.equals("-1")) {
                            requestParams.addBodyParameter("newUrl", "true");
                        }

                        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                final JSONObject jsonObject = JSON.parseObject(result);


                                map_child.get(dataBean.getId()).getImg().setVisibility(View.GONE);
                                map_child.get(dataBean.getId()).getTv_status().setText(getString(R.string.zhengzaixiazai));
                                map_child.get(dataBean.getId()).getImg().setVisibility(View.GONE);
                                map_child.get(dataBean.getId()).getTv_status().setVisibility(View.VISIBLE);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("Id", dataBean.getId());
                                contentValues.put("name", dataBean.getName());
                                contentValues.put("size", dataBean.getSize());
                                contentValues.put("progress", "0%");
                                contentValues.put("status", "2");
                                contentValues.put("down_url", UrlUtil.decode(jsonObject.getString("data")));
                                contentValues.put("from_path", dataBean.getCopy_path());
                                contentValues.put("to_path", "--");
                                contentValues.put("lan_name", dataBean.getLan_name());
                                contentValues.put("lan_code", dataBean.getLan_code());
                                contentValues.put("down_start_time", System.currentTimeMillis());
                                contentValues.put("copy_start_time", 0);
                                contentValues.put("uninstall_start_time", 0);
                                contentValues.put("need_again_unzip", "false");
                                contentValues.put("down_zip_filename", "");
                                if (MyApplication.dbHelper.queryID(dataBean.getId())) {
                                    contentValues.remove("Id");
                                    MyApplication.dbHelper.updateAll(dataBean.getId(), contentValues);
                                } else {
                                    MyApplication.dbHelper.insert(contentValues);
                                }


                                 downloadTask = FileDownloader.getImpl().create(UrlUtil.decode(jsonObject.getString("data")))
                                        .setPath("/sdcard/download_offline/" + dataBean.getName())
                                        .setTag(dataBean.getId());

                                if (downloadTask.getTag().equals("jqdl_ch")) {
                                    //2G以上中国大陆
                                    downloadTask.setListener(new FileDownloadLargeFileListener() {
                                        @Override
                                        protected void completed(BaseDownloadTask task) {
                                            L.e("completed=================" + (String) task.getTag());
                                            String tag = (String) task.getTag();
                                            if (MyApplication.dbHelper.queryID(tag)) {

                                                MyApplication.dbHelper.updateStatusId(tag, "3");
                                                MyApplication.dbHelper.updateFiled(tag, "copy_start_time", System.currentTimeMillis() + "");
                                                MyApplication.dbHelper.updateProgressId(tag, "100%");
                                                if (tag.equals(tv_id)) {
                                                    mBuilder.setProgress(100);

                                                    if (map_child.containsKey(tag)) {
                                                        map_child.get(tag).getImg().setVisibility(View.GONE);
                                                        map_child.get(tag).getTv_status().setVisibility(View.VISIBLE);
                                                        map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.zhengzaianzhuang));
                                                    }

                                                }
                                                mBuilder.setTitle(MyApplication.mContext.getString(R.string.anzhaungtishi))
                                                        .setDesc(MyApplication.mContext.getString(R.string.anzhaungtishi_content1)+MyApplication.mContext.getString(R.string.anzhaungtishi_content2));
                                                dialog_title=R.string.anzhaungtishi;
                                                dialog_desc=R.string.anzhaungtishi_content1;
                                                dialog_desc1=R.string.anzhaungtishi_content2;
                                                MyApplication.isDialog_install=1;

                                                downloadTask = null;
                                                unZipAndInstall(tag, dataBean.getName());
                                            }
                                        }

                                        @Override
                                        protected void error(BaseDownloadTask task, Throwable e) {
                                            L.e("FileDownloadLargeFileListener error=================" + e.getMessage());
                                            String tag = (String) task.getTag();
                                            MyApplication.dbHelper.updateStatusId((String) task.getTag(), "5");
                                            map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.chongshi));
                                            if (mBuilder != null) {
                                                mBuilder.dismiss();
                                            }
                                        }

                                        @Override
                                        protected void warn(BaseDownloadTask task) {
                                            L.e("warn=================" + task);
                                            String tag = (String) task.getTag();

                                            map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.dengdai));
                                        }

                                        @Override
                                        protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                                            L.e("pending=================" + soFarBytes);
                                        }

                                        @Override
                                        protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                                            L.e("progress=================" + soFarBytes + "======" + totalBytes + "============" + (float) soFarBytes / totalBytes);
                                            String tag = (String) task.getTag();
                                            MyApplication.dbHelper.updateProgressId(tag, (int) (((float) soFarBytes / totalBytes) * 100) + "%");

                                            if (isAdded()) {

                                                if (map_child.containsKey(tag)) {
                                                    map_child.get(tag).getImg().setVisibility(View.GONE);

                                                    map_child.get(tag).getTv_status().setVisibility(View.VISIBLE);

                                                } else {
                                                    return;
                                                }
                                                String prog = "";


                                                if ((int) (((float) soFarBytes / totalBytes) * 100) > 100) {
                                                    prog = "0%";
                                                    MyApplication.dbHelper.updateProgressId(tag, prog);


                                                    mBuilder.setProgress(0);
                                                    map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.xiezaichenggong));
                                                    L.e("当前下载进度=====================>>>>" + prog);
                                                } else {
                                                    prog = (int) (((float) soFarBytes / totalBytes) * 100) + "%";
                                                    MyApplication.dbHelper.updateProgressId(tag, prog);
                                                    mBuilder.setProgress((int) (((float) soFarBytes / totalBytes) * 100));
                                                    map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.zhengzaixiazai));
                                                    L.e("当前下载进度=====================>>>>" + prog);
                                                }
                                            }
                                        }

                                        @Override
                                        protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                                            L.e("paused=================" + soFarBytes);
                                            try {
                                                String tag = (String) task.getTag();
                                                MyApplication.dbHelper.updateStatusId((String) task.getTag(), "5");
                                                map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.ms_zt));
                                            } catch (Exception e) {

                                            }

                                        }
                                    });

                                } else {
                                    downloadTask.setListener(new FileDownloadListener() {
                                        @Override
                                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                                            L.e("pending=================" + soFarBytes);
                                        }

                                        @Override
                                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                            L.e("progress=================" + soFarBytes+"======"+totalBytes+"============"+(float)soFarBytes / totalBytes);
                                            String tag =(String) task.getTag();
                                            MyApplication.dbHelper.updateProgressId(tag, (int)(((float)soFarBytes / totalBytes) * 100)+"%");

                                            if (isAdded()) {

                                                if (map_child.containsKey(tag)) {
                                                    map_child.get(tag).getImg().setVisibility(View.GONE);

                                                    map_child.get(tag).getTv_status().setVisibility(View.VISIBLE);

                                                } else {
                                                    return;
                                                }
                                                String prog = "";


                                                if ((int)(((float)soFarBytes / totalBytes) * 100) > 100) {
                                                    prog = "0%";
                                                    MyApplication.dbHelper.updateProgressId(tag, prog);


                                                    mBuilder.setProgress(0);
                                                    map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.xiezaichenggong));
                                                    L.e("当前下载进度=====================>>>>" + prog);
                                                } else {
                                                    prog = (int)(((float)soFarBytes / totalBytes) * 100) + "%";
                                                    MyApplication.dbHelper.updateProgressId(tag, prog);
                                                    mBuilder.setProgress((int)(((float)soFarBytes / totalBytes) * 100));
                                                    map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.zhengzaixiazai));
                                                    L.e("当前下载进度=====================>>>>" + prog);
                                                }
                                            }
                                        }

                                        @Override
                                        protected void completed(BaseDownloadTask task) {
                                            L.e("completed=================" + (String)task.getTag());
                                            String tag=(String)task.getTag();
                                            if ( MyApplication.dbHelper.queryID(tag)) {

                                                MyApplication.dbHelper.updateStatusId(tag, "3");
                                                MyApplication.dbHelper.updateFiled(tag, "copy_start_time", System.currentTimeMillis() + "");
                                                MyApplication.dbHelper.updateProgressId(tag, "100%");
                                                if (tag.equals(tv_id)) {
                                                    mBuilder.setProgress(100);

                                                    if (map_child.containsKey(tag)) {
                                                        map_child.get(tag).getImg().setVisibility(View.GONE);
                                                        map_child.get(tag).getTv_status().setVisibility(View.VISIBLE);
                                                        map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.zhengzaianzhuang));
                                                    }

                                                }
                                                mBuilder .setTitle(MyApplication.mContext.getString(R.string.anzhaungtishi))
                                                        .setDesc(MyApplication.mContext.getString(R.string.anzhaungtishi_content1)+MyApplication.mContext.getString(R.string.anzhaungtishi_content2));
                                                MyApplication.isDialog_install=1;
                                                downloadTask =null;
                                                unZipAndInstall(tag,dataBean.getName());
                                            }
                                        }

                                        @Override
                                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                            L.e("paused=================" + soFarBytes);
                                            try {
                                                String tag=(String)task.getTag();
                                                MyApplication.dbHelper.updateStatusId((String)task.getTag(),"5");
                                                map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.chongshi));
                                                map_child.get(tag).getImg().setVisibility(View.VISIBLE);
                                                if (mBuilder!=null) {
                                                    mBuilder.dismiss();
                                                }
                                            } catch (Exception e) {
                                            }

                                        }

                                        @Override
                                        protected void error(BaseDownloadTask task, Throwable e) {
                                            L.e("error=================" + e.getMessage());
                                            String tag=(String)task.getTag();
                                            MyApplication.dbHelper.updateStatusId((String)task.getTag(),"5");
                                            map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.chongshi));
                                            map_child.get(tag).getImg().setVisibility(View.VISIBLE);
                                            if (mBuilder!=null) {
                                                mBuilder.dismiss();
                                            }
                                        }

                                        @Override
                                        protected void warn(BaseDownloadTask task) {
                                            L.e("warn=================" + task);
                                            String tag=(String)task.getTag();

                                            map_child.get(tag).getTv_status().setText(MyApplication.mContext.getString(R.string.dengdai));
                                        }
                                    });
                                }


                                downloadTask.start();
                                downloadTask.setAutoRetryTimes(5);
                                downMap.put(dataBean.getId(), downloadTask);


                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {

                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });



                    }
                }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();




    }


    /**
     * 解压 安装
     * @param id
     * @param fileName
     */
    public  void  unZipAndInstall(String id, final String fileName) {

            intent = new Intent(MyApplication.mContext, UnZipInstallService.class);
            intent.putExtra("comm","unzip");
            intent.putExtra("id",id);
            intent.putExtra("frag","list");
            intent.putExtra("filename",fileName);
            getActivity().startService(intent);



    }
    Intent intent;


    /**
     *   删除
     * @param dataBean
     * @param tv_tishi
     * @param delType   13是卸载
     */
    public void deleteFile(final Offline_database dataBean, final TextView tv_tishi, String delType) {

        intent = new Intent(MyApplication.mContext, UnZipInstallService.class);
        intent.putExtra("comm","del");
        intent.putExtra("bean",JSON.toJSONString(dataBean));
        intent.putExtra("frag","list");
        L.e("bean======================"+JSON.toJSONString(dataBean));
        intent.putExtra("delType",delType);
        getActivity().startService(intent);

        tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaixiezai));
        tv_tishi.setVisibility(View.VISIBLE);
        tv_tishi.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);



    }







}
