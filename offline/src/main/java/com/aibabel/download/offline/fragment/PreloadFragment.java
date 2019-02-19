package com.aibabel.download.offline.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.download.offline.MenuActivity;
import com.aibabel.download.offline.R;
import com.aibabel.download.offline.adapter.MyAdapter;
import com.aibabel.download.offline.adapter.ViewHolder;
import com.aibabel.download.offline.app.MyApplication;
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
import com.aibabel.download.offline.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.jiangyy.easydialog.LoadingDialog;
import com.jiangyy.easydialog.OtherDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内置资源的  列表
 */
public class PreloadFragment extends Fragment {
    private ListView listView;
    private SmartRefreshLayout refreshLayout;
    ProgressDialog.Builder mBuilder;

     private String id="";

     //操作文件的服务
     Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_installed,null);
        initView(view);
        //修正韩语名字显示问题
        MyApplication.dbHelper.replaceKO();

        MyApplication.uiHandler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                switch (msg.what) {
                    case 200:
                        //系统拷贝完成通知
                        getOfflineInstallList();

                        break;

                    case 400:
                        //解压安装完成  回调
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (MyApplication.offlineNoticeUIHandler != null) {
                                    MyApplication.offlineNoticeUIHandler.sendEmptyMessage(100);
                                }
                                getOfflineInstallList();
                                if (!((String)msg.obj).equals("")) {
                                    T.show(getContext(), MyApplication.mContext.getString(R.string.anzhuangchengong) + MyApplication.dbHelper.queryFiled((String)msg.obj, "lan_name"), 500);

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
                        if (mBuilder!=null) {
                            mBuilder.dismiss();

                        }

                        break;
                    case 500:
                        //删除完成  回调
                        if (!((String)msg.obj).equals("")) {
                            T.show(getActivity(), MyApplication.mContext.getString(R.string.xiezaichenggong) + MyApplication.dbHelper.queryFiled((String)msg.obj, "lan_name"), 500);
                        }
                        if (mBuilder!=null) {
                            mBuilder.dismiss();
                        }
                        getOfflineInstallList();
                        break;
                    case -500:
                        //删除失败  回调
                        if (!((String)msg.obj).equals("")) {
                            T.show(getActivity(), MyApplication.mContext.getString(R.string.xiezaichenggong) + MyApplication.dbHelper.queryFiled((String)msg.obj, "lan_name"), 500);
                        }
                        if (mBuilder!=null) {
                            mBuilder.dismiss();
                        }
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



                if (!id.equals("")) {
                    String status=MyApplication.dbHelper.queryStatus(id);
                    switch (status) {

                        case "11":
                            long  copyTime = MyApplication.dbHelper.queryFiledLong(id, "copy_start_time");
                            if (copyTime > 0 && System.currentTimeMillis() - copyTime > 40 * 60 * 1000) {

                                if (mBuilder!=null) {
                                    mBuilder.dismiss();
                                }
                            }
                            break;
                    }

                }



        } catch (Exception e) {

        }
        super.onResume();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    public void initView(View view) {
        listView=view.findViewById(R.id.frag_install_listview);
        refreshLayout=view.findViewById(R.id.frag_install_refresh);
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

    Map<String,SwipeLayout> swipeLayoutMap=new HashMap<>();

    public void getOfflineInstallList() {
        if (mBuilder!=null) {
            mBuilder.dismiss();
        }
        swipeLayoutMap.clear();
        Cursor cursor= MyApplication.dbHelper.queryCursor();
        final List<Offline_database>  list=new ArrayList<>();
        if(cursor.moveToFirst()){
            //遍历Cursor对象，取出数据
            do{
                String status= cursor.getString(cursor.getColumnIndex("status"));
                String lan_code=cursor.getString(cursor.getColumnIndex("lan_code"));
                String local_lan= CommonUtils.getLocal(getContext());
                if (status.equals("10")||status.equals("11")||status.equals("12")) {
                    if (local_lan.equals("zh_CN")) {
                        String id = cursor.getString(cursor.getColumnIndex("Id"));
                        String name = cursor.getString(cursor.getColumnIndex("lan_name"));
                        String size = cursor.getString(cursor.getColumnIndex("size"));
                        String copyPath = cursor.getString(cursor.getColumnIndex("from_path"));
                        list.add(new Offline_database(id, name, size, status, copyPath));
                    } else if(!lan_code.equals("mdd")&&!lan_code.equals("jqdl")) {

                        String id = cursor.getString(cursor.getColumnIndex("Id"));
                        String name = cursor.getString(cursor.getColumnIndex("lan_name"));
                        String size = cursor.getString(cursor.getColumnIndex("size"));
                        String copyPath = cursor.getString(cursor.getColumnIndex("from_path"));
                        list.add(new Offline_database(id, name, size, status, copyPath));
                    }

                }


            }while(cursor.moveToNext());


        }



        //本地数据排序分组
        final List<Offline_database>  newList=new ArrayList<>();
        int first=0;
        for (int i = 0; i < 3; i++) {
            first=0;
            first++;
            for (int j = 0; j < list.size(); j++)
            {

                switch (i) {
                    case 0:
                        //语音翻译
                        if (!list.get(j).getId().contains("jqdl")&&!list.get(j).getId().contains("mdd")) {
                            if (first == 1) {
                                first=0;
                                newList.add(new Offline_database("yyfy", "语音翻译", "", "", ""));
                                newList.add(list.get(j));


                            } else {
                                newList.add(list.get(j));
                            }
                        }
                        break;
                    case 1:
                        //景区导览
                        if (list.get(j).getId().contains("jqdl")) {
                            if (first == 1) {
                                first=0;
                                newList.add(new Offline_database("jqdl", "景区导览", "", "", ""));
                                newList.add(list.get(j));


                            } else {
                                newList.add(list.get(j));
                            }
                        }
                        break;
                    case 2:
                        //目的地
                        if(!Build.DISPLAY.substring(9,10).equals("L")) {
                            if (list.get(j).getId().contains("mdd")) {
                                if (first == 1) {
                                    first = 0;
                                    newList.add(new Offline_database("mdd", "目的地", "", "", ""));
                                    newList.add(list.get(j));


                                } else {
                                    newList.add(list.get(j));
                                }
                            }
                        }
                        break;
                }

            }
        }

        listView.setAdapter(new MyAdapter<Offline_database>(getActivity(),newList,R.layout.item_perload) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void convert(ViewHolder holder, final Offline_database dataBean) {
                final TextView tv_name = ((TextView) holder.getView(R.id.item_preload_name));
                TextView tv_size = ((TextView) holder.getView(R.id.item_preload_size));
                final TextView tv_tishi = ((TextView) holder.getView(R.id.item_preload_tishi));
                RelativeLayout rl = ((RelativeLayout) holder.getView(R.id.item_preload_rl));
                final SwipeLayout parent_rl = ((SwipeLayout) holder.getView(R.id.item_preload_parent_swipeLayout));

                swipeLayoutMap.put(dataBean.getId(),parent_rl);


                ImageView imageView=((ImageView) holder.getView(R.id.item_preload_shibai_iv));

                RelativeLayout top_rl= ((RelativeLayout) holder.getView(R.id.item_preload_top_rl));
                TextView tv_title = ((TextView) holder.getView(R.id.item_preload_title));
                TextView tv_del= ((TextView) holder.getView(R.id.item_perload_delect1));


                parent_rl.close();
                switch (dataBean.getId()) {
                    case "yyfy":
                        tv_title.setText(MyApplication.mContext.getString(R.string.yuyinfanyi));

                        parent_rl.setVisibility(View.GONE);
                        top_rl.setVisibility(View.VISIBLE);
                        return;
                    case "jqdl":
                        tv_title.setText(MyApplication.mContext.getString(R.string.jingqudaolan));

                        parent_rl.setVisibility(View.GONE);
                        top_rl.setVisibility(View.VISIBLE);

                        return;
                    case "mdd":
                        tv_title.setText(MyApplication.mContext.getString(R.string.mudidi));

                        parent_rl.setVisibility(View.GONE);
                        top_rl.setVisibility(View.VISIBLE);

                        return;
                }




            parent_rl.addSwipeListener(new SimpleSwipeListener(){
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



                //删除资源
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
                                        id=dataBean.getId();
                                        deleteFile(dataBean,tv_tishi,"");



                                    }
                                }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();
                    }
                });

                imageView.setVisibility(View.GONE);
                tv_name.setText(dataBean.getName());
                tv_size.setText(dataBean.getSize());
                switch (dataBean.getStatus()) {

                    case "10":

                        tv_tishi.setText(MyApplication.mContext.getString(R.string.anzhuang));
                        imageView.setVisibility(View.GONE);
                        tv_tishi.setVisibility(View.VISIBLE);
                        break;
                    case "11":
                        tv_size.setText( dataBean.getSize());
                        imageView.setVisibility(View.GONE);
                        tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaianzhuang));
                        tv_tishi.setVisibility(View.VISIBLE);
                        long copyTime = MyApplication.dbHelper.queryFiledLong(dataBean.getId(), "copy_start_time");
                        if (copyTime > 0 && System.currentTimeMillis() - copyTime > 40 * 60 * 1000) {
                            MyApplication.dbHelper.updateStatusId(dataBean.getId(),"12");
                            tv_size.setText(dataBean.getSize() );
                            imageView.setVisibility(View.VISIBLE);
                            tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));


                        }
                        break;
                    case "12":
                        tv_size.setText( dataBean.getSize());
                        imageView.setVisibility(View.VISIBLE);
                        tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));


                        break;
                    case "13":
                        tv_size.setText(dataBean.getSize());
                        imageView.setVisibility(View.GONE);
                        tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaixiezai));


                        long uninstallTime = MyApplication.dbHelper.queryFiledLong(dataBean.getId(), "uninstall_start_time");
                        if (uninstallTime > 0 && System.currentTimeMillis() - uninstallTime > 30 * 60 * 1000) {
                            MyApplication.dbHelper.updateStatusId(dataBean.getId(),"14");
                            tv_size.setText(dataBean.getSize());
                            imageView.setVisibility(View.VISIBLE);
                            tv_tishi.setText(MyApplication.mContext.getString(R.string.chongshi));

                        }
                        break;
                    case "14":
                        tv_size.setText(dataBean.getSize() );
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
                        if (ThreadPoolManager.getInstance().getCarryNum()>=2) {
                            T.show(getContext(),MyApplication.mContext.getString(R.string.zhengzaizhixing),500);
                            return;

                        }

                        String status=MyApplication.dbHelper.queryStatus(dataBean.getId());
                        String installTime="";
                        switch (status) {
                            case "1":
                                new OtherDialog.Builder(getContext())
                                        .setGravity(Gravity.CENTER)
                                        .setContentView(R.layout.dialog_tishi)
                                        .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.xiezai))
                                        .setText(R.id.dialog_desc,"")
                                        .setCancelable(false)
                                        .setOnClickListener(R.id.dialog_tv_sure, MyApplication.mContext.getString(R.string.queding), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                mBuilder = new ProgressDialog.Builder(getActivity());
                                                mBuilder.setCanceledOnTouchOutside(false)
                                                        .showProgress(true)
                                                        .setTitle(MyApplication.mContext.getString(R.string.xiezaitishi))
                                                        .setDesc(MyApplication.mContext.getString(R.string.shanchu_tishi_content))
                                                        .showProgress(false)
                                                        .show();
                                                id=dataBean.getId();
                                              deleteFile(dataBean,tv_tishi,"13");



                                            }
                                        }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();
                                break;
                            case "10":
                                //预装
                                StatisticsManager.getInstance(mContext).addEventAidl("本地资源安装"+dataBean.getName());
                                if(dataBean.getId().equals("jqdl_ch")){
                                    installTime="（文件过大，预计安装时间20分钟）";
                                }else {

                                    installTime="";
                                }


                                final String finalInstallTime = installTime;
                                new OtherDialog.Builder(getContext())
                                        .setGravity(Gravity.CENTER)
                                        .setContentView(R.layout.dialog_tishi)
                                        .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.anzhuang))
                                        .setText(R.id.dialog_desc,MyApplication.mContext.getString(R.string.wenjiandaxiao)+dataBean.getSize())
                                        .setCancelable(false)
                                        .setOnClickListener(R.id.dialog_tv_sure, MyApplication.mContext.getString(R.string.queding), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                 mBuilder = new ProgressDialog.Builder(getActivity());
                                                 mBuilder.setCanceledOnTouchOutside(false)
                                                         .showProgress(true)
                                                         .setTitle(MyApplication.mContext.getString(R.string.anzhaungtishi))
                                                         .setDesc(MyApplication.mContext.getString(R.string.anzhaungtishi_content1)+ finalInstallTime +MyApplication.mContext.getString(R.string.anzhaungtishi_content2))
                                                         .showProgress(false)
                                                         .show();
                                                 id=dataBean.getId();
                                                L.e("10=============="+dataBean.getCopyPath());
                                                NeizhiList.ListFileBean.CopyPathBean bean=JSON.parseObject(dataBean.getCopyPath(),NeizhiList.ListFileBean.CopyPathBean.class);
                                                L.e("bean.getZipPath()================="+bean.getZipPath());
                                                tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaianzhuang));

                                                unzipCopy(dataBean.getId(),bean);



                                            }
                                        }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();
                                break;
                            case "11":
                                T.show(getActivity(),MyApplication.mContext.getString(R.string.zhengzaianzhuang),500);
                                break;
                            case "12":

                                if(dataBean.getId().equals("jqdl_ch")){
                                    installTime="（文件过大，预计安装时间20分钟）";
                                }else {

                                    installTime="";
                                }

                                final String finalInstallTime1 = installTime;
                                new OtherDialog.Builder(getContext())
                                        .setGravity(Gravity.CENTER)
                                        .setContentView(R.layout.dialog_tishi)
                                        .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.anzhuang))
                                        .setText(R.id.dialog_desc,MyApplication.mContext.getString(R.string.wenjiandaxiao)+dataBean.getSize())
                                        .setCancelable(false)
                                        .setOnClickListener(R.id.dialog_tv_sure, MyApplication.mContext.getString(R.string.queding), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                mBuilder = new ProgressDialog.Builder(getActivity());
                                                mBuilder.setCanceledOnTouchOutside(false)
                                                        .showProgress(true)
                                                        .setTitle(MyApplication.mContext.getString(R.string.anzhaungtishi))
                                                        .setDesc(MyApplication.mContext.getString(R.string.anzhaungtishi_content1)+ finalInstallTime1 +MyApplication.mContext.getString(R.string.anzhaungtishi_content2))
                                                        .showProgress(false)
                                                        .show();
                                                id=dataBean.getId();
                                                L.e("10=============="+dataBean.getCopyPath());
                                                NeizhiList.ListFileBean.CopyPathBean bean=JSON.parseObject(dataBean.getCopyPath(),NeizhiList.ListFileBean.CopyPathBean.class);
                                                L.e("bean.getZipPath()================="+bean.getZipPath());
                                                tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaianzhuang));

                                                unzipCopy(dataBean.getId(),bean);



                                            }
                                        }).setOnClickListener(R.id.dialog_tv_cancel, MyApplication.mContext.getString(R.string.quxiao), null).show();
                                break;
                            case "13":
                                T.show(getActivity(),MyApplication.mContext.getString(R.string.zhengzaixiezai),500);
                                break;
                            case "14":
                                new OtherDialog.Builder(getContext())
                                        .setGravity(Gravity.CENTER)
                                        .setContentView(R.layout.dialog_tishi)
                                        .setText(R.id.dialog_title, MyApplication.mContext.getString(R.string.xiezai))
                                        .setText(R.id.dialog_desc,"")
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
                                                id=dataBean.getId();
                                                deleteFile(dataBean,tv_tishi,"13");



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
     * @param bean
     */
    public void unzipCopy(final String id, final NeizhiList.ListFileBean.CopyPathBean bean){

        intent = new Intent(MyApplication.mContext, UnZipInstallService.class);
        intent.putExtra("comm","copy");
        intent.putExtra("id",id);
        intent.putExtra("copyBean",JSON.toJSONString(bean));
        intent.putExtra("frag","list");
        getActivity().startService(intent);


    }




    public void updateUiDb(final String id, final TextView textView) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyApplication.dbHelper.updateStatusId(id, "12");
                if (mBuilder!=null) {
                    mBuilder.dismiss();
                }
                textView.setText(MyApplication.mContext.getString(R.string.chongshi));
                textView.setVisibility(View.VISIBLE);
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
        intent.putExtra("frag","list");
        intent.putExtra("bean",JSON.toJSONString(dataBean));
        L.e("bean======================"+JSON.toJSONString(dataBean));
        intent.putExtra("delType",delType);
        getActivity().startService(intent);

        tv_tishi.setText(MyApplication.mContext.getString(R.string.zhengzaixiezai));
        tv_tishi.setVisibility(View.VISIBLE);
        tv_tishi.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);


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