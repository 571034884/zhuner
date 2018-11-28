package com.aibabel.baselibrary.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * 作者：SunSH on 2018/11/9 16:50
 * 功能：
 * 版本：1.0
 */
public class FileUtil {

    /**
     * 保存操作完成监听事件
     */
    interface SaveCompleteListener {
        void Success(String filePath);

        void failure(String error);
    }

    /**
     * 读取操作完成监听事件
     */
    interface GetCompleteListener {
        void Success(byte[] bytes);

        void failure(String error);
    }

    /**
     * 删除操作完成监听事件
     */
    interface DeleteCompleteListener {
        void Success();

        void failure(String error);
    }

    /**
     * 保存文件，默认覆盖
     *
     * @param filePath 文件路径
     * @param data     文件数据
     * @param listener 操作完成监听
     */
    public void save(String filePath, byte[] data, SaveCompleteListener listener) {
        save(filePath, data, false, listener);
    }

    /**
     * 保存文件，默认覆盖
     *
     * @param filePath 文件路径
     * @param data     文件数据
     * @param append   是否追加
     * @param listener 操作完成监听
     */
    public void save(String filePath, byte[] data, boolean append, SaveCompleteListener listener) {
        if (!createOrExistsDir(filePath)) {
            listener.failure("目录创建失败");
            return;
        }
        boolean success = FileIOUtils.writeFileFromBytesByStream(filePath, data, append);
        if (listener == null) return;
        if (success) listener.Success(filePath);
        else listener.failure("存储失败");
    }

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @param listener 读取完成监听
     */
    public void get(String filePath, GetCompleteListener listener) {
        get(getFileByPath(filePath), listener);
    }

    /**
     * 读取文件
     *
     * @param file     文件
     * @param listener 读取完成监听
     */
    public void get(File file, GetCompleteListener listener) {
        if (!isFileExists(file)) {
            listener.failure("文件不存在");
            return;
        }
        byte[] bytes = FileIOUtils.readFile2BytesByStream(file);
        if (listener == null) return;
        if (bytes != null) listener.Success(bytes);
        else listener.failure("读取过程出错");
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @param listener 删除完成监听
     */
    public void delete(String filePath, DeleteCompleteListener listener) {
        delete(getFileByPath(filePath), listener);
    }

    /**
     * 删除文件
     *
     * @param file     文件
     * @param listener 删除完成监听
     */
    public void delete(File file, DeleteCompleteListener listener) {
        if (!isFileExists(file)) {
            listener.failure("文件不存在");
            return;
        }
        byte[] bytes = FileIOUtils.readFile2BytesByStream(file);
        if (listener == null) return;
        if (bytes != null) listener.Success();
        else listener.failure("删除过程出错");
    }

    /**
     * 删除全部
     *
     * @param filePath
     */
    public void deleteAll(String filePath, DeleteCompleteListener listener) {
        if (deleteFilesInDir(filePath))
            listener.Success();
        else listener.failure("删除过程出错");
    }

    /**
     * 删除全部
     *
     * @param file
     */
    public void deleteAll(File file, DeleteCompleteListener listener) {
        if (deleteFilesInDir(file))
            listener.Success();
        else listener.failure("删除过程出错");
    }

    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    public static boolean deleteFilesInDir(File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(final String dirPath, final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 根据文件路径获取文件
     * Return the file by path.
     *
     * @param filePath The path of file.
     * @return the file
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath
     * @return
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file
     * @return
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在
     * Return whether the file exists.
     *
     * @param file The file.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * isWhitespace() 方法用于判断指定字符是否为空白字符，空白符包含：空格、tab键、换行符。
     *
     * @param s
     * @return
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
