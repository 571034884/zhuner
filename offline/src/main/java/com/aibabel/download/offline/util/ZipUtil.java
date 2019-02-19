package com.aibabel.download.offline.util;

import android.annotation.TargetApi;
import android.os.Build;

import com.aibabel.download.offline.app.MyApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//import java.util.zip.ZipOutputStream;
//
public class ZipUtil {

   static String TAG="ZipUtil=====================";
    /**
     * 含子目录的文件压缩
     *
     * @throws Exception
     */
    // 第一个参数就是需要解压的文件，第二个就是解压的目录

    @TargetApi(Build.VERSION_CODES.N)
    public static boolean upZipFile(File zipFile, String folderPath) {
        long start = System.currentTimeMillis();
        ZipFile zfile = null;
        try {
            // 转码为GBK格式，支持中文
            Charset gbk = Charset.forName("GBK");
            zfile = new ZipFile(zipFile, gbk);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {

            if (!MyApplication.isFile) {
               return false;
            }
            ze = (ZipEntry) zList.nextElement();
            // 列举的压缩文件里面的各个文件，判断是否为目录
            if (ze.isDirectory()) {
                String dirstr = folderPath + ze.getName();
                L.e(TAG, "dirstr=" + dirstr);
                dirstr.trim();
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os = null;
            FileOutputStream fos = null;
            // ze.getName()会返回 script/start.script这样的，是为了返回实体的File
            File realFile = getRealFileName(folderPath, ze.getName());
            try {
                fos = new FileOutputStream(realFile);
            } catch (FileNotFoundException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            os = new BufferedOutputStream(fos);
            InputStream is = null;
            try {
                is = new BufferedInputStream(zfile.getInputStream(ze));
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            int readLen = 0;
            // 进行一些内容复制操作
            try {
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
        }
        try {
            zfile.close();
        } catch (IOException e) {
            L.e(TAG, e.getMessage());
            return false;
        }
        long end = System.currentTimeMillis();
        L.e("解压成功======================" + (end - start));
        return true;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        L.i(TAG, "baseDir=" + baseDir + "------absFileName="
                + absFileName);
        absFileName = absFileName.replace("\\", "/");
        L.i(TAG, "absFileName=" + absFileName);
        String[] dirs = absFileName.split("/");
        L.i(TAG, "dirs=" + dirs);
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                ret = new File(ret, substr);
            }

            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            ret = new File(ret, substr);
            return ret;
        } else {
            ret = new File(ret, absFileName);
        }
        return ret;
    }


    /**
     * 解压一个压缩文档 到指定位置
     *
     * @param zipFileString 压缩包的名字
     * @param outPathString 指定的路径
     * @throws Exception
     */
    public static boolean UnZipFolder(String zipFileString, String outPathString) {
        try {
            long start = System.currentTimeMillis();
            android.util.Log.v("XZip", "UnZipFolder(String, String)");
            java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
            java.util.zip.ZipEntry zipEntry;
            String szName = "";
            while ((zipEntry = inZip.getNextEntry()) != null) {
                if (!MyApplication.isFile) {
                    break;
                }
                szName = zipEntry.getName();
                if (zipEntry.isDirectory()) {

                    // get the folder name of the widget
                    szName = szName.substring(0, szName.length() - 1);
                    java.io.File folder = new java.io.File(outPathString + java.io.File.separator + szName);
                    folder.mkdirs();

                } else {

                    java.io.File file = new java.io.File(outPathString + java.io.File.separator + szName);
                    file.createNewFile();
                    // get the output stream of the file
                    java.io.FileOutputStream out = new java.io.FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024];
                    // read (len) bytes into buffer
                    while ((len = inZip.read(buffer)) != -1) {
                        // write (len) byte from buffer at the position 0
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                }
            }//end of while

            inZip.close();
            long end = System.currentTimeMillis();
            L.e("解压成功======================" + (end - start));
            return true;
        } catch (Exception e) {
            return false;
        }

    }//end of func


    public static boolean Unzip(String zipFile, String targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称

        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {

                try {
                    L.e("Unzip: ","="+ entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();

                    File entryFile = new File(targetDir + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }

                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
            zis.close();
            return true;
        } catch (Exception cwj) {
            cwj.printStackTrace();

            return false;
        }
    }




 /*   *//**
     * 含子目录的文件压缩
     *
     * @throws Exception
     *//*
    // 第一个参数就是需要解压的文件，第二个就是解压的目录
    @TargetApi(Build.VERSION_CODES.N)
    public static boolean upZipFile1(String zipFile, String folderPath) {
        ZipFile zfile = null;
        try {
            // 转码为GBK格式，支持中文
//            zfile = new ZipFile(zipFile);

            Charset gbk = Charset.forName("GBK");
            zfile = new ZipFile(zipFile, gbk);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            if (!MyApplication.isFile) {
                return false;
            }
            ze = (ZipEntry) zList.nextElement();
            // 列举的压缩文件里面的各个文件，判断是否为目录
            if (ze.isDirectory()) {
                String dirstr = folderPath + ze.getName();
                L.e(TAG, "dirstr=" + dirstr);
                dirstr.trim();
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os = null;
            FileOutputStream fos = null;
            // ze.getName()会返回 script/start.script这样的，是为了返回实体的File
            File realFile = getRealFileName(folderPath, ze.getName());
            try {
                fos = new FileOutputStream(realFile);
            } catch (FileNotFoundException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            os = new BufferedOutputStream(fos);
            InputStream is = null;
            try {
                is = new BufferedInputStream(zfile.getInputStream(ze));
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            int readLen = 0;
            // 进行一些内容复制操作
            try {
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
        }
        try {
            zfile.close();
        } catch (IOException e) {
            L.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

*/



   /* *//**
     * 含子目录的文件压缩
     *
     * @throws Exception
     *//*
    // 第一个参数就是需要解压的文件，第二个就是解压的目录
    @TargetApi(Build.VERSION_CODES.N)
    public static boolean upZipFile(String zipFile, String folderPath) {
        ZipFile zfile = null;
        try {
            // 转码为GBK格式，支持中文
//            zfile = new ZipFile(zipFile);

            Charset gbk = Charset.forName("GBK");
            zfile = new ZipFile(zipFile, gbk);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {

            if (!MyApplication.isFile) {
                L.e("TIZHI==============");
                break;
            }

//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                break;
//            }
            ze = (ZipEntry) zList.nextElement();
            // 列举的压缩文件里面的各个文件，判断是否为目录
            if (ze.isDirectory()) {
                String dirstr = folderPath + ze.getName();
                L.e(TAG, "dirstr=" + dirstr);
                dirstr.trim();
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os = null;
            FileOutputStream fos = null;
            // ze.getName()会返回 script/start.script这样的，是为了返回实体的File
            File realFile = getRealFileName(folderPath, ze.getName());
            try {
                fos = new FileOutputStream(realFile);
            } catch (FileNotFoundException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            os = new BufferedOutputStream(fos);
            InputStream is = null;
            try {
                is = new BufferedInputStream(zfile.getInputStream(ze));
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            int readLen = 0;
            // 进行一些内容复制操作
            try {
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
                return false;
            }
        }
        try {
            zfile.close();
        } catch (IOException e) {
            L.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }*/

}
