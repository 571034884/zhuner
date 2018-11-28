package com.aibabel.baselibrary.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程池管理
 *
 * @author zwy
 */
public class ThreadPoolManager {
    private ExecutorService service;
    private ThreadPoolExecutor pool;

    private ThreadPoolManager() {
        //获得可用的处理器个数
        int num = Runtime.getRuntime().availableProcessors();
        // 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
        service = Executors.newFixedThreadPool(num * 8);
        pool = (ThreadPoolExecutor) service;

    }

    private static final ThreadPoolManager manager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return manager;
    }

    public void addTask(Runnable runnable) {
        service.execute(runnable);
    }

    /**
     * 正在执行线程
     *
     * @return
     */
    public int getCarryNum() {
        return pool.getActiveCount();
    }

}
