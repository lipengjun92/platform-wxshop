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
package com.platform.modules.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.job.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务
 *
 * @author 李鹏军
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

    /**
     * 获取分页数据
     *
     * @param params 查询参数
     * @return Page
     */
    Page<ScheduleJobEntity> queryPage(Map<String, Object> params);

    /**
     * 新增定时任务
     *
     * @param scheduleJob scheduleJob
     */
    void add(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     *
     * @param scheduleJob scheduleJob
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     *
     * @param jobIds jobIds
     */
    void deleteBatch(String[] jobIds);

    /**
     * 批量更新定时任务状态
     *
     * @param jobIds jobIds
     * @param status status
     */
    void updateBatch(String[] jobIds, int status);

    /**
     * 立即执行
     *
     * @param jobIds jobIds
     */
    void run(String[] jobIds);

    /**
     * 暂停运行
     *
     * @param jobIds jobIds
     */
    void pause(String[] jobIds);

    /**
     * 恢复运行
     *
     * @param jobIds jobIds
     */
    void resume(String[] jobIds);
}
