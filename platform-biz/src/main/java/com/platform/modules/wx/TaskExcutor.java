/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.wx;

import java.util.concurrent.*;

/**
 * 系统线程池，系统各种任务使用同一个线程池，以防止创建过多线程池
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
public class TaskExcutor {
    private TaskExcutor() {
    }

    /**
     * 使用静态内部类实现单例懒加载
     */
    private static class ExcutorHolder {
        /**
         * 线程池
         * corePoolSize=5 核心线程数
         * maximumPoolSize=30 最大线程数
         * keepAliveTime=10，unit=TimeUnit.SECOND 线程最大空闲时间为10秒
         * workQueue=new SynchronousQueue<Runnable>() 链表队列
         * handler=new ThreadPoolExecutor.CallerRunsPolicy()
         */
        private static final ExecutorService EXCUTOR = new ThreadPoolExecutor(
                5, 30, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 使用静态内部类实现单例懒加载
     */
    private static class SchedulerHolder {
        private static final ScheduledExecutorService SCHEDULER =
                Executors.newScheduledThreadPool(5);
    }

    /**
     * 将任务提交到系统线程池中执行
     * 1.如果线程数未达核心线程，创建核心线程
     * 2.已达核心线程数，添加到任务队列
     * 3.核心线程已满、队列已满，创建新空闲线程
     * 4.核心线程已满、队列已满、无法创建新空闲线程，执行拒绝策略
     * 本工具类拒绝策略使用内置ThreadPoolExecutor.CallerRunsPolicy，即让添加任务的主线程来执行任务，这样主线程被占用无法继续添加任务，相当于线程池全满后添加任务的线程会被阻塞
     *
     * @param task
     * @return
     */
    public static Future<?> submit(Runnable task) {
        return ExcutorHolder.EXCUTOR.submit(task);
    }

    /**
     * 将定时任务添加到系统线程池
     *
     * @param task
     * @param delay
     * @param unit
     * @return
     */
    public static ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return SchedulerHolder.SCHEDULER.schedule(task, delay, unit);
    }
}
