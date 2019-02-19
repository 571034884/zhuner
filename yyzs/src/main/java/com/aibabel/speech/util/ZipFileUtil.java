package com.aibabel.speech.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ZipFileUtil {


    private static final int BUFF_SIZE = 1024 * 1024;


    public static void zipFiles(Collection<File> fileList, File zipFile) throws IOException {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
                zipFile)));

        for (File file : fileList) {
            zipFile(file, zipout, "");
        }

        zipout.close();
    }


    public static void zipFiles1(File fileList, File zipFile) throws IOException {

        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        zipout.setLevel(9);
        zipFile(fileList, zipout, "");
        zipout.close();
    }


    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件（夹）
     * @param zipout   压缩的目的文件
     * @param rootpath 压缩的文件路径
     * @throws FileNotFoundException 找不到文件时抛出
     * @throws IOException           当压缩过程出错时抛出
     */
    private static void zipFile(File resFile, ZipOutputStream zipout, String rootpath)
            throws FileNotFoundException, IOException {
        rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator)
                + resFile.getName();
        rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipout, rootpath);
            }
        } else {
            byte buffer[] = new byte[2048];
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile));
            zipout.putNextEntry(new ZipEntry(rootpath));
            int realLength;
            while ((realLength = in.read(buffer)) != -1) {
                zipout.write(buffer, 0, realLength);
            }
            in.close();
            zipout.flush();
            zipout.closeEntry();
        }
    }


    public static byte[] getFileToByte(File file) {

        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[1024];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }

            by = bytestream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     //L.e("图片大小", "getFileToByte: " + by.length);
        return by;
    }


    // 把Bitmap 转成 Byte
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static File saveBitmapFile(Bitmap bitmap, String path) {
        File file = new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void zip(String filepath, String zipFilePath) {
        File file = new File(filepath);
        File zipFile = new File(zipFilePath);
        try {
            InputStream input = new FileInputStream(file);
            ZipOutputStream zipOut = null;
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.setLevel(0);
            zipOut.setMethod(ZipOutputStream.STORED);
            try {
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                int temp = 0;
                while ((temp = input.read()) != -1) {
                    zipOut.write(temp);
                }
                input.close();
                zipOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap ratio(Bitmap image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 957.6f;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = 720f;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 2;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }


    public static void gzipFile(File input, File out) {
        try {

            int number;

            //打开需压缩文件作为文件输入流

            FileInputStream fin = new FileInputStream(input);

            //建立压缩文件输出流

            FileOutputStream fout = new FileOutputStream(out);

            //建立GZIP压缩输出流

            GZIPOutputStream gzout = new GZIPOutputStream(fout);

            //设定读入缓冲区尺寸

            byte[] buf = new byte[1024];

            while ((number = fin.read(buf)) != -1)

                gzout.write(buf, 0, number);

            gzout.close();

            fout.close();

            fin.close();

        } catch (IOException e)

        {
        }
    }


    /**
     * 根据byte数组生成文件
     *
     * @para成文件用到的byte数组
     */
    public  static void createFileWithByte(List<byte[]> list, String fileName) {
        // TODO Auto-generated method stub
        /**
         * 创建File对象，其中包含文件所在的目录以及文件的命名
         */
        File file = new File(fileName);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象

        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象

         //L.e("11111111111111111","--------------------create file");
            // 往文件所在的缓冲输出流中写byte数据
            for (int i = 0; i < list.size(); i++) {
                outputStream.write(list.get(i));
             //L.e("-------------------<",i+">----------------");
            }

            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            outputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
         //L.e("2222222222222222","--------------------create file"+e.getMessage());
        } finally {
            // 关闭创建的流对象


            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}