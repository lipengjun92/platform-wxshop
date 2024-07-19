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
package com.platform.modules.job.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.platform.common.utils.SpringContextUtils;
import com.platform.modules.job.entity.ScheduleJobEntity;
import com.platform.modules.job.entity.ScheduleJobLogEntity;
import com.platform.modules.job.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 定时任务
 *
 * @author 李鹏军
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

    private static final ExecutorService THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), NAMED_THREAD_FACTORY);

    @Override
    protected void executeInternal(JobExecutionContext context) {
        ScheduleJobEntity scheduleJob = new ScheduleJobEntity();

        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY), scheduleJob);
        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        ScheduleJobLogEntity logEntity = new ScheduleJobLogEntity();
        logEntity.setJobId(scheduleJob.getJobId());
        logEntity.setBeanName(scheduleJob.getBeanName());
        logEntity.setMethodName(scheduleJob.getMethodName());
        logEntity.setParams(scheduleJob.getParams());
        logEntity.setCreateTime(new Date());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            log.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
                    scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = THREAD_POOL_EXECUTOR.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            logEntity.setTimes((int) times);
            //任务状态    0：成功    1：失败
            logEntity.setStatus(0);

            log.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            logEntity.setTimes((int) times);

            //任务状态    0：成功    1：失败
            logEntity.setStatus(1);
            logEntity.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(logEntity);
        }
    }
}
