package com.aibabel.download.offline.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.download.offline.DownLoadListActivity;
import com.aibabel.download.offline.MainActivity;
import com.aibabel.download.offline.MenuActivity;
import com.aibabel.download.offline.R;
import com.aibabel.download.offline.adapter.MyAdapter;
import com.aibabel.download.offline.adapter.ViewHolder;
import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.bean.DownViewBean;
import com.aibabel.download.offline.bean.NeizhiList;
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
import com.aibabel.download.offline.util.UrlUtil;
import com.aibabel.download.offline.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.daimajia.swipe.SwipeLayout;
import com.jiangyy.easydialog.OtherDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 下载列表已安装的
 */
public class InstalledFragment extends Fragment {
    private ListView listView;
    private SmartRefreshLayout refreshLayout;
    ProgressDialog.Builder mBuilder;

    private String id = "";
    //操作文件的服务
    Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_installed, null);
        initView(view);
        MyApplication.offlineNoticeUIHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:

                        getOfflineInstallList();
                        break;
                    case 500:
                        //删除完成  回调
                        if (!((String)msg.obj).equals("")) {
                            T.show(getActivity(), MyApplication.mContext.getString(R.string.xiezaichenggong) + MyApplication.dbHelper.queryFiled((String)msg.obj, "lan_name"), 500);
                        }
                        getOfflineInstallList();
                        break;
                    case -500:
                        //删除失败  回调
                        getOfflineInstallList();
                        break;
                }
            }
        };

        getOfflineInstallList();
        return view;
    }


    @Override
    public void onResume() {
        L.e("InstalledFragment onresume==========================");
        try {


        } catch (Exception e) {

        }
        super.onResume();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    public void initView(View view) {
        listView = view.findViewById(R.id.frag_install_listview);
        refreshLayout = view.findViewById(R.id.frag_install_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getOfflineInstallList();
                refreshLayout.finishRefresh(true);
            }
        });
        refreshLayout.setDisableContentWhenLoading(false);//是否在加载的时候禁止列表的操作

    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {

        super.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    public void getOfflineInstallList() {

        if (mBuilder != null) {
            mBuilder.dismiss();
        }

        Cursor cursor = MyApplication.dbHelper.queryCursor();
        final List<Offline_database> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            //遍历Cursor对象，取出数据
            do {
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String lan_code = cursor.getString(cursor.getColumnIndex("lan_code"));
                String local_lan = CommonUtils.getLocal(getContext());
                if (status.equals("1") || status.equals("13") || status.equals("14")) {
                    if(cursor.getString(cursor.getColumnIndex("down_url")).contains("http")){

                        if (local_lan.equals("zh_CN")) {
                            String id = cursor.getString(cursor.getColumnIndex("Id"));
                            String name = cursor.getString(cursor.getColumnIndex("lan_name"));
                            String size = cursor.getString(cursor.getColumnIndex("size"));
                            String copyPath = cursor.getString(cursor.getColumnIndex("from_path"));
                            list.add(new Offline_database(id, name, size, status, copyPath));
                        } else if (!lan_code.equals("mdd") && !lan_code.equals("jqdl")) {

                            String id = cursor.getString(cursor.getColumnIndex("Id"));
                            String name = cursor.getString(cursor.getColumnIndex("lan_name"));
                            String size = cursor.getString(cursor.getColumnIndex("size"));
                            String copyPath = cursor.getString(cursor.getColumnIndex("from_path"));
                            list.add(new Offline_database(id, name, size, status, copyPath));
                        }

                    }

                }


            } while (cursor.moveToNext());


        }
        DownLoadListActivity base = ((DownLoadListActivity) getActivity());
        //本地数据分组   当前类型
        if(base == null)return;
        //本地数据分组   当前类型
        String currId = base.key;
        final List<Offline_database> newList = new ArrayList<>();

        for (int j = 0; j < list.size(); j++) {


            if (currId.equals("yyfy")) {
                //语音翻译
                if (!list.get(j).getId().contains("jqdl") && !list.get(j).getId().contains("mdd")) {

                    newList.add(list.get(j));

                }
            } else if (currId.equals("jqdl")) {
                //景区导览
                if (list.get(j).getId().contains("jqdl")) {
                    newList.add(list.get(j));

                }
            } else if (currId.equals("mdd")) {
                //目的地
                if (list.get(j).getId().contains("mdd")) {

                    newList.add(list.get(j));

                }

            }


        }


        listView.setAdapter(new MyAdapter<Offline_database>(getActivity(), newList, R.layout.item_perload) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void convert(ViewHolder holder, final Offline_database dataBean) {
                final TextView tv_name = ((TextView) holder.getView(R.id.item_preload_name));
                TextView tv_size = ((TextView) holder.getView(R.id.item_preload_size));
                final TextView tv_tishi = ((TextView) holder.getView(R.id.item_preload_tishi));
                RelativeLayout rl = ((RelativeLayout) holder.getView(R.id.item_preload_rl));
                SwipeLayout parent_rl = ((SwipeLayout) holder.getView(R.id.item_preload_parent_swipeLayout));

                ImageView imageView = ((ImageView) holder.getView(R.id.item_preload_shibai_iv));

                RelativeLayout top_rl = ((RelativeLayout) holder.getView(R.id.item_preload_top_rl));
                TextView tv_title = ((TextView) holder.getView(R.id.item_preload_title));
                TextView tv_del = ((TextView) holder.getView(R.id.item_perload_delect1));


                parent_rl.close();
                switch (dataBean.getId()) {
                    case "yyfy":
                        tv_title.setText(MyApplication.mContext.getString(R.string.yuyinfanyi));

                        parent_rl.setVisibility(View.GONE);
                        top_rl.setVisibility(View.VISIBLE);
                        return;
                    case "jqdl":
                        if(MyApplication.ifshowjqdl) {
                            tv_title.setText(MyApplication.mContext.getString(R.string.jingqudaolan));

                            parent_rl.setVisibility(View.GONE);
                            top_rl.setVisibility(View.VISIBLE);
                        }
                        return;
                    case "mdd":
                        tv_title.setText(MyApplication.mContext.getString(R.string.mudidi));

                        parent_rl.setVisibility(View.GONE);
                        top_rl.setVisibility(View.VISIBLE);

                        return;
                }

                //禁用滑动删除
                parent_rl.setSwipeEnabled(false);


                imageView.setVisibility(View.GONE);
                tv_name.setText(dataBean.getName());
                tv_size.setText(dataBean.getSize());
                switch (dataBean.getStatus()) {
                    case "1":
                        tv_tishi.setText(MyApplication.mContext.getString(R.string.xiezai));
                        break;


                    case "13":
                        tv_size.setText(dataBean.getSize());
                        imageView.setVisibility(View.GONE);
                        tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaixiezai));


                        long uninstallTime = MyApplication.dbHelper.queryFiledLong(dataBean.getId(), "uninstall_start_time");
                        if (uninstallTime > 0 && System.currentTimeMillis() - uninstallTime > 30 * 60 * 1000) {
                            MyApplication.dbHelper.updateStatusId(dataBean.getId(), "14");
                            tv_size.setText(dataBean.getSize());
                            imageView.setVisibility(View.VISIBLE);
                            tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));

                        }
                        break;
                    case "14":
                        tv_size.setText(dataBean.getSize());
                        imageView.setVisibility(View.VISIBLE);
                        tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));


                        break;
                    default:
                        tv_tishi.setVisibility(View.GONE);
                        break;
                }


                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //已安装
                        if (ThreadPoolManager.getInstance().getCarryNum() >= 2) {
                            T.show(getContext(), MyApplication.mContext.getString(R.string.zhengzaizhixing), 500);
                            return;

                        }

                        String status = MyApplication.dbHelper.queryStatus(dataBean.getId());
                        String installTime = "";
                        switch (status) {
                            case "1":
                                new OtherDialog.Builder(getContext())
                                        .setGravity(Gravity.CENTER)
                                        .setContentView(R.layout.dialog_tishi)
                                        .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.xiezai))
                                        .setText(R.id.dialog_desc, MyApplication.mContext.getString(R.string.xiezai_content))
                                        .setCancelable(false)
                                        .setOnClickListener(R.id.dialog_tv_sure, MyApplication.mContext.getString(R.string.queding), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                mBuilder = new ProgressDialog.Builder(getActivity());
                                                mBuilder.setCanceledOnTouchOutside(false)
                                                        .showProgress(true)
                                                        .setTitle(MyApplication.mContext.getString(R.string.xiezaitishi))
                                                        .setDesc(MyApplication.mContext.getString(R.string.xiezaitishi_content))
                                                        .showProgress(false)
                                                        .show();
                                                id = dataBean.getId();
                                                deleteFile(dataBean, tv_tishi,"13");


                                            }
                                        }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();
                                break;

                            case "13":
                                T.show(getActivity(), MyApplication.mContext.getString(R.string.zhengzaixiezai), 500);
                                break;
                            case "14":
                                new OtherDialog.Builder(getContext())
                                        .setGravity(Gravity.CENTER)
                                        .setContentView(R.layout.dialog_tishi)
                                        .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.xiezai))
                                        .setText(R.id.dialog_desc, MyApplication.mContext.getString(R.string.xiezai_content))
                                        .setCancelable(false)
                                        .setOnClickListener(R.id.dialog_tv_sure, MyApplication.mContext.getString(R.string.queding), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                mBuilder = new ProgressDialog.Builder(getActivity());
                                                mBuilder.setCanceledOnTouchOutside(false)
                                                        .showProgress(true)
                                                        .setTitle(MyApplication.mContext.getString(R.string.xiezaitishi))
                                                        .setDesc(MyApplication.mContext.getString(R.string.xiezaitishi_content))
                                                        .showProgress(false)
                                                        .show();
                                                id = dataBean.getId();
                                                deleteFile(dataBean, tv_tishi,"13");


                                            }
                                        }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();
                                break;

                        }


                    }

                });
            }

        });
    }

    /**
     * 解压 拷贝文件
     *
     * @param bean
     */
    public void unzipCopy(final String id, final NeizhiList.ListFileBean.CopyPathBean bean, final TextView tv) {
        MyApplication.dbHelper.updateFiled(id, "copy_start_time", System.currentTimeMillis() + "");
        MyApplication.dbHelper.updateStatusId(id, "11");

        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {

                try {


                    if (id.equals("jqdl_ch")) {
                        //景区导览 中国大陆解压
                        L.e("解压=================" + id);
                        NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = bean.getChildFiles().get(0);
                        if (ZipUtil.UnZipFolder(bean.getZipPath(), childFilesBean.getToPath())) {

                            if (FileUtil.deleteFile(bean.getZipPath())) {
                                MyApplication.dbHelper.updateStatusId(id, "1");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mBuilder != null) {
                                            mBuilder.dismiss();
                                        }
                                        getOfflineInstallList();
                                        T.show(getContext(), "安装成功：" + MyApplication.dbHelper.queryFiled(id, "lan_name"), 500);
                                    }
                                });


                            } else {
                                L.e("拷贝失败==================" + id);
                                updateUiDb(id, tv);
                            }
                        } else {
                            L.e("解压失败=================" + id);
                            updateUiDb(id, tv);

                        }
                    } else {
                        if (id.indexOf("mdd") == -1 && id.indexOf("jqdl") == -1) {

                            if (ZipUtil.upZipFile(new File(bean.getZipPath()), SDCardUtils.getSDCardPath() + "/download_offline/")) {

                                String filename = id + ".zip";
                                filename = filename.substring(0, filename.indexOf("."));
                                L.e("=================================" + filename);
                                String[] str = filename.split("_");
                                if (FileUtil.copyFolder("/sdcard/download_offline/" + filename + "/" + str[0] + "2zh", "/sdcard/NiuTransTransformer/" + str[0] + "2zh")) {
                                    L.e("copy diyiwnjian==================" + str[0]);
                                    if (FileUtil.copyFolder("/sdcard/download_offline/" + filename + "/zh2" + str[0], "/sdcard/NiuTransTransformer/zh2" + str[0])) {

                                        Intent intent = new Intent("com.aibabel.download.offline.COPY_DATA");
                                        //copy标
                                        intent.putExtra("command", "copy");
                                        intent.putExtra("key", filename + ";" + filename + ";" + id + ".zip;" + MyApplication.dbHelper.queryFiled(id, "lan_name"));
                                        intent.putExtra("fromPath", "/sdcard/download_offline/" + filename + "/" + str[0] + "-" + str[1]);
                                        intent.putExtra("toPath", "/data/data/com.google.android.googlequicksearchbox/app_g3_models");
                                        intent.putExtra("fileName", "app_g3_models");
                                        intent.putExtra("packageName", "com.google.android.googlequicksearchbox");
                                        try {
                                            getActivity().sendBroadcast(intent);
                                        } catch (Exception e) {
                                            L.e("kaobei =========================" + e.getMessage());
                                        }

                                    }
                                }

                            }


                        } else {
                            NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = bean.getChildFiles().get(0);
                            if (ZipUtil.upZipFile(new File(bean.getZipPath()), childFilesBean.getToPath())) {
                                L.e("解压=================" + id);

                                if (FileUtil.deleteFile(bean.getZipPath())) {
                                    MyApplication.dbHelper.updateStatusId(id, "1");
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mBuilder != null) {
                                                mBuilder.dismiss();
                                            }
                                            getOfflineInstallList();
                                            T.show(getContext(), "安装成功：" + MyApplication.dbHelper.queryFiled(id, "lan_name"), 500);
                                        }
                                    });


                                } else {
                                    L.e("拷贝失败==================" + id);
                                    MyApplication.dbHelper.updateStatusId(id, "12");
                                    updateUiDb(id, tv);
                                }
                            } else {
                                L.e("解压失败=================" + id);
                                MyApplication.dbHelper.updateStatusId(id, "12");
                                updateUiDb(id, tv);
                            }
                        }
                    }


                } catch (Exception e) {

                    L.e("文件执行失败==============" + e.getMessage());
                    MyApplication.dbHelper.updateStatusId(id, "12");
                    e.printStackTrace();
                }


            }
        });


    }




    /**
     *   删除
     * @param dataBean
     * @param tv_tishi
     * @param delType   13是卸载
     */
    public void deleteFile(final Offline_database dataBean, final TextView tv_tishi, String delType) {

        intent = new Intent(MyApplication.mContext, UnZipInstallService.class);
        intent.putExtra("comm","del");
        intent.putExtra("frag","install");
        intent.putExtra("bean",JSON.toJSONString(dataBean));
        L.e("bean======================"+JSON.toJSONString(dataBean));
        intent.putExtra("delType",delType);
        getActivity().startService(intent);

        tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaixiezai));
        tv_tishi.setVisibility(View.VISIBLE);
        tv_tishi.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);


    }


    public void updateUiDb(final String id, final TextView textView) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyApplication.dbHelper.updateStatusId(id, "12");
                if (mBuilder != null) {
                    mBuilder.dismiss();
                }
                textView.setText(MyApplication.mContext.getString(R.string.chongshi));
                textView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onDestroy() {

        L.e("preloadfragment  ondestroy============================");

        try {
            getActivity().stopService(intent);
        } catch (Exception e) {
            L.e("unbindService==================="+e.getMessage());
        }

        MyApplication.dbHelper.shutdownUPdateDB();

        super.onDestroy();
    }
}