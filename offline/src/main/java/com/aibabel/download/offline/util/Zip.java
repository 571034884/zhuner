package com.aibabel.download.offline.util;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * 该类实现文件夹压缩成zip文件和zip文件解压
 * @author Administrator
 *
 */
public class Zip {
    private ZipInputStream zipIn;      //解压Zip
    private ZipOutputStream zipOut;     //压缩Zip
    private ZipEntry zipEntry;
    private static int bufSize;    //size of bytes
    private byte[] buf;
    private int readedBytes;

    public Zip() {
        this(1024);
    }

    public Zip(int bufSize) {
        this.bufSize = bufSize;
        this.buf = new byte[this.bufSize];
    }

    //压缩文件夹内的文件
    public void doZip(String zipDirectory) {//zipDirectoryPath:需要压缩的文件夹名
        File file;
        File zipDir;

        zipDir = new File(zipDirectory);
        String zipFileName = zipDirectory + ".zip";//压缩后生成的zip文件名

        try {
            this.zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
            handleDir(zipDir, this.zipOut);
            this.zipOut.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    //由doZip调用,递归完成目录文件读取
    private void handleDir(File dir, ZipOutputStream zipOut) throws Exception {
        FileInputStream fileIn;
        File[] files;

        files = dir.listFiles();
        if (files.length == 0) {//如果目录为空,则单独创建之.
            //ZipEntry的isDirectory()方法中,目录以"/"结尾.
            this.zipOut.putNextEntry(new ZipEntry(dir.toString() + "/"));
            this.zipOut.closeEntry();
        } else {//如果目录不为空,则分别处理目录和文件.
            for (File fileName : files) {

                if (fileName.isDirectory()) {
                    handleDir(fileName, this.zipOut);
                } else {
                    fileIn = new FileInputStream(fileName);
                    String name = dir.getName();
                    //生成的压缩包存放在原目录下
                    this.zipOut.putNextEntry(new ZipEntry(name + "/" + fileName.getName().toString()));

                    //此方法存放在该项目目录下
                    //this.zipOut.putNextEntry(new ZipEntry(fileName.toString()));
                    while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
                        this.zipOut.write(this.buf, 0, this.readedBytes);
                    }
                    this.zipOut.closeEntry();
                }
            }
        }
    }

    //解压指定zip文件
    public  void unZip(String unZipfileName) {//unZipfileName需要解压的zip文件名
        FileOutputStream fileOut;
        File file;
        String f = unZipfileName.substring(0, unZipfileName.length() - 4);
        File ff = new File(f);
        try {
            this.zipIn = new ZipInputStream(new
                    BufferedInputStream(new FileInputStream(unZipfileName)));
            while ((this.zipEntry = this.zipIn.getNextEntry()) != null) {
                file = new File(this.zipEntry.getName());
                if (this.zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    //如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    if (!ff.exists()) {
                        ff.mkdir();
                    }
                    fileOut = new FileOutputStream(f + "/" + file.getName());

                    //fileOut = new FileOutputStream(file); 此方法存放到该项目目录下
                    while ((this.readedBytes = this.zipIn.read(this.buf)) > 0) {
                        fileOut.write(this.buf, 0, this.readedBytes);
                    }
                    fileOut.close();
                }

                this.zipIn.closeEntry();
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();

            L.e("解压失败---------------"+ioe.getMessage());
        }
    }

    //设置缓冲区大小
    public void setBufSize(int bufSize) {
        this.bufSize = bufSize;
    }



    //第一个参数就是需要解压的文件，第二个就是解压的目录 public static boolean upZipFileDir(File zipFile, String folderPath) { ZipFile zfile= null; try { //转码为GBK格式，支持中文 zfile = new ZipFile(zipFile,"GBK"); } catch (IOException e) { e.printStackTrace(); return false; } Enumeration zList=zfile.getEntries(); ZipEntry ze=null; byte[] buf=new byte[1024]; while(zList.hasMoreElements()){ ze=(ZipEntry)zList.nextElement(); //列举的压缩文件里面的各个文件，判断是否为目录 if(ze.isDirectory()){ String dirstr = folderPath + ze.getName(); dirstr.trim(); File f=new File(dirstr); f.mkdir(); continue; } OutputStream os= null; FileOutputStream fos = null; // ze.getName()会返回 script/start.script这样的，是为了返回实体的File File realFile = getRealFileName(folderPath, ze.getName()); try { fos = new FileOutputStream(realFile); } catch (FileNotFoundException e) { e.printStackTrace(); return false; } os = new BufferedOutputStream(fos); InputStream is= null; try { is = new BufferedInputStream(zfile.getInputStream(ze)); } catch (IOException e) { e.printStackTrace(); return false; } int readLen=0; //进行一些内容复制操作 try { while ((readLen=is.read(buf, 0, 1024))!=-1) { os.write(buf, 0, readLen); } } catch (IOException e) { e.printStackTrace(); return false; } try { is.close(); os.close(); } catch (IOException e) { e.printStackTrace(); return false; } } try { zfile.close(); } catch (IOException e) { e.printStackTrace(); return false; } return true; } /** * 给定根目录，返回一个相对路径所对应的实际文件名. * @param baseDir 指定根目录 * @param absFileName 相对路径名，来自于ZipEntry中的name * @return java.io.File 实际的文件 */ public static File getRealFileName(String baseDir, String absFileName){ String[] dirs=absFileName.split("/"); File ret = new File(baseDir); String substr = null; if(dirs.length>1){ for (int i = 0; i < dirs.length-1;i++) { substr = dirs[i]; ret=new File(ret, substr); } if(!ret.exists()) ret.mkdirs(); substr = dirs[dirs.length-1]; ret=new File(ret, substr); return ret; }else{ ret = new File(ret,absFileName); } return ret; }



}
