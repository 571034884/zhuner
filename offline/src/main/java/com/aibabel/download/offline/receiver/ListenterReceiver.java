package com.aibabel.download.offline.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.aibabel.download.offline.R;
import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.util.FileUtil;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.T;

import java.io.File;
import java.util.List;

public class ListenterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        String key=intent.getStringExtra("key");
        String[] res=key.split(";");
        MyApplication.dbHelper.updateStatusId(res[0],"1");
        try {

            for (int i = 0; i < res.length; i++) {
                if (i == 1) {
                    FileUtil.deleteDirectory("/sdcard/download_offline/" + res[i]);

                } else if (i==2) {

                    FileUtil.deleteFile("/sdcard/download_offline/" + res[i]);
                    File file=new File("/sdcard/download_offline/");
                    if (  file.isDirectory()) {
                        File[] fileArr=file.listFiles();
                        for (int j = 0; j < fileArr.length; j++) {
                            String name= fileArr[j].getName();
                            if (name.lastIndexOf(res[0])!=-1) {
                                if (fileArr[j].isDirectory()) {
                                    FileUtil.deleteDirectory("/sdcard/download_offline/" + name);
                                } else {
                                    FileUtil.deleteFile("/sdcard/download_offline/" + name);

                                }
                            }

                        }

                    }
                }

            }


            if (MyApplication.uiHandler!=null) {
                Message message=MyApplication.uiHandler.obtainMessage();
                message.what=200;
                message.obj=res[0];
                MyApplication.uiHandler.sendMessage(message);
            }

            if (MyApplication.offlineNoticeUIHandler!=null) {
                MyApplication.offlineNoticeUIHandler.sendEmptyMessage(100);
            }




        } catch (Exception e) {

        }finally {
            MyApplication.dbHelper.updateStatusId(res[0],"1");
            T.show(context,context.getString(R.string.anzhuangchengong)+res[3]+"，"+context.getString(R.string.ms_chongqi),1000);
            L.e("================ListenterReceiver:"+res[0]);
        }



//        T.show(context,"安装成功"+key,1000);
//        L.e("================ListenterReceiver:y"+key);


    }
}
