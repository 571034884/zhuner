package com.aibabel.download.offline.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.widget.TextView;

import com.aibabel.download.offline.R;
import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.bean.NeizhiList;
import com.aibabel.download.offline.bean.Offline_database;
import com.aibabel.download.offline.util.FileUtil;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.SDCardUtils;
import com.aibabel.download.offline.util.T;
import com.aibabel.download.offline.util.ThreadPoolManager;
import com.aibabel.download.offline.util.ZipUtil;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.List;

public class UnZipInstallService extends Service {
    public UnZipInstallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

       return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            String comm = intent.getStringExtra("comm");

            String id = "";
            String filename = "";
            String delType = "";
            String bean = "";
            acquireWakeLock();

            switch (comm) {
                case "unzip":
                    id = intent.getStringExtra("id");
                    filename = intent.getStringExtra("filename");
                    unZipAndInstall(id, filename);
                    break;
                case "copy":
                    id = intent.getStringExtra("id");
                    unzipCopy(id, JSON.parseObject(intent.getStringExtra("copyBean"), NeizhiList.ListFileBean.CopyPathBean.class));


                    break;
                case "del":

                    delType = intent.getStringExtra("delType");
                    bean = intent.getStringExtra("bean");
                    deleteFile(JSON.parseObject(bean, Offline_database.class), delType, intent.getStringExtra("frag"));

                    break;

            }

        }


        return super.onStartCommand(intent, flags, startId);

    }

    /**
     * 解压 安装
     * @param id
     * @param fileName
     */
    public  void  unZipAndInstall(String id, final String fileName) {
        MyApplication.isFile=true;
        final String finalKey = id;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String lan_code = MyApplication.dbHelper.queryFiled(finalKey, "lan_code");
                String down_filename_path = SDCardUtils.getSDCardPath() + "/download_offline/" +fileName;
                if (!lan_code.equals("mdd") && !lan_code.equals("jqdl")) {
                    //语音翻译  不同的拷贝方式
                    if (ZipUtil.upZipFile(new File(down_filename_path), SDCardUtils.getSDCardPath() + "/download_offline/")) {
                        String filename = fileName.replace("utf-8' '", "");
                        filename = filename.substring(0, filename.indexOf("."));
                        L.e("=================================" + filename);
                        String[] str = filename.split("_");
                        if (FileUtil.copyFolder("/sdcard/download_offline/" + filename + "/" + str[0] + "2zh", "/sdcard/NiuTransTransformer/" + str[0] + "2zh")) {
                            L.e("copy diyiwnjian==================" + str[0]);
                            if (FileUtil.copyFolder("/sdcard/download_offline/" + filename + "/zh2" + str[0], "/sdcard/NiuTransTransformer/zh2" + str[0])) {

                                Intent intent = new Intent("com.aibabel.download.offline.COPY_DATA");
                                //copy标
                                intent.putExtra("command", "copy");
                                intent.putExtra("key", filename + ";" + filename + ";" + fileName + ";" +MyApplication.dbHelper.queryFiled(finalKey, "lan_name"));
                                intent.putExtra("fromPath", "/sdcard/download_offline/" + filename + "/" + str[0] + "-" + str[1]);
                                intent.putExtra("toPath", "/data/data/com.google.android.googlequicksearchbox/app_g3_models");
                                intent.putExtra("fileName", "app_g3_models");
                                intent.putExtra("packageName", "com.google.android.googlequicksearchbox");
                                try {
                                    MyApplication.mContext.sendBroadcast(intent);
                                } catch (Exception e) {
                                    L.e("kaobei =========================" + e.getMessage());
                                }

                            }
                        }

                    }

                } else {
                    //mdd  jqdl
                    try {

                        NeizhiList.ListFileBean.CopyPathBean bean = JSON.parseObject(MyApplication.dbHelper.queryFiled(finalKey, "from_path"), NeizhiList.ListFileBean.CopyPathBean.class);
                        if (finalKey.equals("jqdl_ch")) {
                            //景区导览 中国大陆解压
                            NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = bean.getChildFiles().get(0);
                            if (ZipUtil.UnZipFolder(down_filename_path, childFilesBean.getToPath())) {
                                L.e("解压=================" + finalKey);


                                if (FileUtil.deleteFile(down_filename_path)) {
                                    //安装成功
                                    MyApplication.dbHelper.updateStatusId(finalKey, "1");
                                    if (MyApplication.uiHandler!=null) {
                                        Message message=MyApplication.uiHandler.obtainMessage();
                                        message.what=400;
                                        message.obj=finalKey;
                                        MyApplication.uiHandler.sendMessage(message);
                                    }



                                } else {
                                    L.e("拷贝失败==================" + finalKey);

                                }
                            } else {
                                L.e("解压失败=================" + finalKey);


                            }
                        } else {
                            NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = bean.getChildFiles().get(0);
                            if (ZipUtil.upZipFile(new File(down_filename_path), childFilesBean.getToPath())) {
                                L.e("解压=================" + finalKey);


                                if (FileUtil.deleteFile(down_filename_path)) {
                                    //安装成功
                                    MyApplication.dbHelper.updateStatusId(finalKey, "1");
                                    if (MyApplication.uiHandler!=null) {
                                        Message message=MyApplication.uiHandler.obtainMessage();
                                        message.what=400;
                                        message.obj=finalKey;
                                        MyApplication.uiHandler.sendMessage(message);
                                    }


                                } else {
                                    L.e("拷贝失败==================" + finalKey);

                                }
                            } else {
                                L.e("解压失败=================" + finalKey);

                            }
                        }


                    } catch (Exception e) {

                        L.e("文件执行失败==============" + e.getMessage());

                        e.printStackTrace();
                    }finally {
                        releaseWakeLock();
                    }

                }
                releaseWakeLock();

            }
        }).start();

    }



    /**
     * 预装解压 拷贝文件
     * @param bean
     */
    public void unzipCopy(final String id, final NeizhiList.ListFileBean.CopyPathBean bean){
        MyApplication.isFile=true;
        MyApplication.dbHelper.updateFiled(id,"copy_start_time",System.currentTimeMillis()+"");
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

                            if (FileUtil.deleteFile(bean.getZipPath())){
                                //安装成功
                                MyApplication.dbHelper.updateStatusId(id, "1");
                                if (MyApplication.uiHandler!=null) {
                                    Message message=MyApplication.uiHandler.obtainMessage();
                                    message.what=400;
                                    message.obj=id;
                                    MyApplication.uiHandler.sendMessage(message);
                                }





                            } else {
                                L.e("拷贝失败=================="+id);
//                                updateUiDb(id,tv);
                            }
                        } else {
                            L.e("解压失败=================" + id);
//                            updateUiDb(id,tv);

                        }
                    } else {
                        if (id.indexOf("mdd")==-1&&id.indexOf("jqdl")==-1) {

                            if (ZipUtil.upZipFile(new File(bean.getZipPath()), SDCardUtils.getSDCardPath() + "/download_offline/")) {

                                String filename = id+".zip";
                                filename = filename.substring(0, filename.indexOf("."));
                                L.e("=================================" + filename);
                                String[] str = filename.split("_");
                                if (FileUtil.copyFolder("/sdcard/download_offline/" + filename + "/" + str[0] + "2zh", "/sdcard/NiuTransTransformer/" + str[0] + "2zh")) {
                                    L.e("copy diyiwnjian==================" + str[0]);
                                    if (FileUtil.copyFolder("/sdcard/download_offline/" + filename + "/zh2" + str[0], "/sdcard/NiuTransTransformer/zh2" + str[0])) {

                                        Intent intent = new Intent("com.aibabel.download.offline.COPY_DATA");
                                        //copy标
                                        intent.putExtra("command", "copy");
                                        intent.putExtra("key", filename + ";" + filename + ";" +id + ".zip;" + MyApplication.dbHelper.queryFiled(id,"lan_name"));
                                        intent.putExtra("fromPath", "/sdcard/download_offline/" + filename + "/" + str[0] + "-" + str[1]);
                                        intent.putExtra("toPath", "/data/data/com.google.android.googlequicksearchbox/app_g3_models");
                                        intent.putExtra("fileName", "app_g3_models");
                                        intent.putExtra("packageName", "com.google.android.googlequicksearchbox");
                                        try {
                                            MyApplication.mContext.sendBroadcast(intent);
                                        } catch (Exception e) {
                                            L.e("kaobei =========================" + e.getMessage());
                                        }

                                    }
                                }

                            }


                        }else {
                            NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean childFilesBean = bean.getChildFiles().get(0);
                            if (ZipUtil.upZipFile(new File(bean.getZipPath()), childFilesBean.getToPath())) {
                                L.e("解压=================" + id);

                                if (FileUtil.deleteFile(bean.getZipPath())) {
                                    //安装成功
                                    MyApplication.dbHelper.updateStatusId(id, "1");
                                    if (MyApplication.uiHandler!=null) {
                                        Message message=MyApplication.uiHandler.obtainMessage();
                                        message.what=400;
                                        message.obj=id;
                                        MyApplication.uiHandler.sendMessage(message);
                                    }





                                } else {
                                    L.e("拷贝失败==================" + id);
                                    MyApplication.dbHelper.updateStatusId(id, "12");
//                                    updateUiDb(id, tv);
                                }
                            } else {
                                L.e("解压失败=================" + id);
                                MyApplication.dbHelper.updateStatusId(id, "12");
//                                updateUiDb(id, tv);
                            }
                        }
                    }


                } catch (Exception e) {

                    L.e("文件执行失败=============="+e.getMessage());
                    MyApplication.dbHelper.updateStatusId(id, "12");
                    e.printStackTrace();
                }finally {
                    releaseWakeLock();
                }


            }
        });




    }


    /**
     *   删除
     * @param dataBean
     * @param delType   13是卸载
     */
    public void deleteFile(final Offline_database dataBean, final String delType, final String frag) {
        MyApplication.isFile=true;

        try {
//            tv_tishi.setText("正在卸载");
//            tv_tishi.setVisibility(View.VISIBLE);
//            tv_tishi.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);


            MyApplication.dbHelper.updateFiled(dataBean.getId(), "uninstall_start_time", System.currentTimeMillis() + "");
            if(delType.equals("13")){
                MyApplication.dbHelper.updateUninstallStatusId(dataBean.getId(), delType);
            }



            if (MyApplication.dbHelper.queryFiled(dataBean.getId(), "lan_code").equals("mdd") || MyApplication.dbHelper.queryFiled(dataBean.getId(), "lan_code").equals("jqdl")) {
                //景区导览和目的地  文件的删除
                NeizhiList.ListFileBean.CopyPathBean bean = JSON.parseObject(dataBean.getCopyPath(), NeizhiList.ListFileBean.CopyPathBean.class);
                final List<NeizhiList.ListFileBean.CopyPathBean.ChildFilesBean> listChild = bean.getChildFiles();
//

                final boolean[] isdel = {true};
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < listChild.size(); i++) {
                            if (MyApplication.isFile) {
                                isdel[0] = FileUtil.deleteDirectory(listChild.get(i).getToPath() + "/" + dataBean.getId());
                            } else {
                                break;
                            }

                        }
                        File file = new File("/sdcard/download_offline/");
                        if (file.isDirectory()) {
                            File[] fileArr = file.listFiles();
                            for (int j = 0; j < fileArr.length; j++) {
                                if (MyApplication.isFile) {
                                    String name = fileArr[j].getName();
                                    if (name.lastIndexOf(dataBean.getId()) != -1) {
                                        if (fileArr[j].isDirectory()) {
                                            isdel[0] = FileUtil.deleteDirectory("/sdcard/download_offline/" + name);
                                        } else {
                                            isdel[0] = FileUtil.deleteFile("/sdcard/download_offline/" + name);

                                        }
                                    }
                                } else {
                                    break;
                                }


                            }
                        }

                        if (isdel[0]) {
                            //jqdl  mdd删除成功
                            MyApplication.dbHelper.updateUninstallStatusId(dataBean.getId(), "-1");
                            if (frag.equals("list")) {
                                if (MyApplication.uiHandler != null) {
                                    Message message = MyApplication.uiHandler.obtainMessage();
                                    message.what = 500;
                                    message.obj = dataBean.getId();
                                    MyApplication.uiHandler.sendMessage(message);
                                }
                            } else if (frag.equals("install")) {

                                if (MyApplication.uiHandler != null) {
                                    Message message = MyApplication.offlineNoticeUIHandler.obtainMessage();
                                    message.what = 500;
                                    message.obj = dataBean.getId();
                                    MyApplication.offlineNoticeUIHandler.sendMessage(message);
                                }
                            }


                        } else {
                            //jqdl  mdd删除失败
                            if (frag.equals("list")) {
                                if (MyApplication.uiHandler != null) {
                                    Message message = MyApplication.uiHandler.obtainMessage();
                                    message.what = -500;
                                    message.obj = dataBean.getId();
                                    MyApplication.uiHandler.sendMessage(message);
                                }
                            } else if (frag.equals("install")) {

                                if (MyApplication.uiHandler != null) {
                                    Message message = MyApplication.offlineNoticeUIHandler.obtainMessage();
                                    message.what = -500;
                                    message.obj = dataBean.getId();
                                    MyApplication.offlineNoticeUIHandler.sendMessage(message);
                                }
                            }

                            if(delType.equals("13")) {
                                MyApplication.dbHelper.updateUninstallStatusId(dataBean.getId(), "14");
                            }

                            releaseWakeLock();
                            L.e("删除失败===================================MyApplication isfile:"+MyApplication.isFile);
                        }

                    }

                });


            } else {
                //语音文件的 删除
                String[] res = dataBean.getId().split("_");
                FileUtil.deleteDirectory("/sdcard/NiuTransTransformer/" + res[0] + "2zh");
                FileUtil.deleteDirectory("/sdcard/NiuTransTransformer/zh2" + res[0]);
                MyApplication.dbHelper.updateUninstallStatusId(dataBean.getId(), "-1");
                if (frag.equals("list")) {
                    if (MyApplication.uiHandler != null) {
                        Message message = MyApplication.uiHandler.obtainMessage();
                        message.what = 500;
                        message.obj = dataBean.getId();
                        MyApplication.uiHandler.sendMessage(message);
                    }
                } else if (frag.equals("install")) {

                    if (MyApplication.uiHandler != null) {
                        Message message = MyApplication.offlineNoticeUIHandler.obtainMessage();
                        message.what = 500;
                        message.obj = dataBean.getId();
                        MyApplication.offlineNoticeUIHandler.sendMessage(message);
                    }
                }
//                T.show(getActivity(), MyApplication.mContext.getString(R.string.xiezaichenggong) + dataBean.getName(), 500);
//                getOfflineInstallList();
            }
        } catch (Exception e) {
            if(delType.equals("13")){
                MyApplication.dbHelper.updateUninstallStatusId(dataBean.getId(), "14");

                //jqdl  mdd删除失败
                if (frag.equals("list")) {
                    if (MyApplication.uiHandler != null) {
                        Message message = MyApplication.uiHandler.obtainMessage();
                        message.what = -500;
                        message.obj = dataBean.getId();
                        MyApplication.uiHandler.sendMessage(message);
                    }
                } else if (frag.equals("install")) {

                    if (MyApplication.uiHandler != null) {
                        Message message = MyApplication.offlineNoticeUIHandler.obtainMessage();
                        message.what = -500;
                        message.obj = dataBean.getId();
                        MyApplication.offlineNoticeUIHandler.sendMessage(message);
                    }
                }
            }
        }finally {
            releaseWakeLock();

            MyApplication.dbHelper.updateUninstallStatusId(dataBean.getId(), "-1");
            if (frag.equals("list")) {
                if (MyApplication.uiHandler != null) {
                    Message message = MyApplication.uiHandler.obtainMessage();
                    message.what = 500;
                    message.obj = dataBean.getId();
                    MyApplication.uiHandler.sendMessage(message);
                }
            } else if (frag.equals("install")) {

                if (MyApplication.uiHandler != null) {
                    Message message = MyApplication.offlineNoticeUIHandler.obtainMessage();
                    message.what = 500;
                    message.obj = dataBean.getId();
                    MyApplication.offlineNoticeUIHandler.sendMessage(message);
                }
            }

            L.e("  finally===================================MyApplication isfile:"+MyApplication.isFile);
        }


    }

    PowerManager.WakeLock wakeLock = null;
    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    private void acquireWakeLock()
    {
        try {
            if (null == wakeLock) {
                PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
                wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "PostLocationService");
                if (null != wakeLock) {
                    wakeLock.acquire();
                }
            }
        } catch (Exception e) {
            L.e("释放设备电源锁====="+e.getMessage());
        }
    }
    //释放设备电源锁
    private void releaseWakeLock()
    {
        try {
            if (null != wakeLock) {
                wakeLock.release();
                wakeLock = null;
            }
        } catch (Exception e) {
            L.e("获取电源锁====="+e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        MyApplication.isFile=false;
        releaseWakeLock();
        super.onDestroy();

    }
}
