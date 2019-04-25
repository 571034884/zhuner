package com.aibabel.baselibrary.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fytworks
 * 用于对数据进行持久化保存的操作。
 * 把数据以文件形式进行保存
 * 恢复出厂设置 不会被清除。
 */

public class FilesUtil {
    public static final String mFileName ="menu.jks";
    /**
     * 保存操作完成监听事件
     */
    public interface SaveCompleteListener {
        void Success(String body);

        void failure(String error);
    }

    public static void saveToFile(String txt,SaveCompleteListener listener){


        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File e = new File(Environment.getExternalStorageDirectory() + "/fyt");
                if(!e.exists()) {
                    e.mkdir();
                }
                FileOutputStream mFileOutputStream = new FileOutputStream(e + "/" + mFileName);
                mFileOutputStream.write(txt.getBytes());
                mFileOutputStream.close();
                listener.Success("存储成功");
            }catch (IOException var) {
                var.printStackTrace();
                listener.failure("存储失败");
            }
        }
    }

    /**
     * 读取文件
     * @return  读取到的文件
     */
    public static boolean readToFile(){
        StringBuilder sb = new StringBuilder("");
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {

                File e = new File(Environment.getExternalStorageDirectory() + "/fyt");
                if(!e.exists()) {
                    return false;
                }
                FileInputStream is = new FileInputStream(e+"/"+mFileName);
                byte[] buffer = new byte[1024];
                int len = is.read(buffer);
                while(len > 0){
                    sb.append(new String(buffer,0,len));
                    //继续将数据放到buffer中
                    len = is.read(buffer);
                }
                //关闭输入流
                is.close();
                return Boolean.parseBoolean(sb.toString().trim());
            } catch (Exception e1) {
                e1.printStackTrace();
                return false;
            }

        }
        return false;
    }

}
