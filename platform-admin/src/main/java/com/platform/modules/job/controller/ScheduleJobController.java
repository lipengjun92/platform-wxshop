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
package com.platform.modules.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.modules.job.entity.ScheduleJobEntity;
import com.platform.modules.job.service.ScheduleJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务
 *
 * @author 李鹏军
 */
@Tag(name = "ScheduleJobController|定时任务")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

    private final ScheduleJobService scheduleJobService;

    /**
     * 分页查询定时任务
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询定时任务", description = "分页查询定时任务")
    @GetMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public RestResponse<Page<ScheduleJobEntity>> list(@RequestParam Map<String, Object> params) {
        Page<ScheduleJobEntity> page = scheduleJobService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 根据主键查询详情
     *
     * @param jobId 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public RestResponse<ScheduleJobEntity> info(@PathVariable("jobId") String jobId) {
        ScheduleJobEntity schedule = scheduleJobService.getById(jobId);

        return RestResponse.ok(schedule);
    }

    /**
     * 新增定时任务
     *
     * @param scheduleJob scheduleJob
     * @return RestResponse
     */
    @Operation(summary = "新增定时任务", description = "新增定时任务")
    @SysLog("新增定时任务")
    @PostMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public RestResponse<String> save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.add(scheduleJob);

        return RestResponse.ok();
    }

    /**
     * 修改定时任务
     *
     * @param scheduleJob scheduleJob
     * @return RestResponse
     */
    @Operation(summary = "修改定时任务", description = "修改定时任务")
    @SysLog("修改定时任务")
    @PostMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public RestResponse<String> update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return RestResponse.ok();
    }

    /**
     * 删除定时任务
     *
     * @param jobIds jobIds
     * @return RestResponse
     */
    @Operation(summary = "删除定时任务", description = "删除定时任务")
    @SysLog("删除定时任务")
    @PostMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public RestResponse<String> delete(@RequestBody String[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return RestResponse.ok();
    }

    /**
     * 立即执行任务
     *
     * @param jobIds jobIds
     * @return RestResponse
     */
    @Operation(summary = "立即执行任务", description = "立即执行任务")
    @SysLog("立即执行任务")
    @PostMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public RestResponse<String> run(@RequestBody String[] jobIds) {
        scheduleJobService.run(jobIds);

        return RestResponse.ok();
    }

    /**
     * 暂停定时任务
     *
     * @param jobIds jobIds
     * @return RestResponse
     */
    @Operation(summary = "暂停定时任务", description = "暂停定时任务")
    @SysLog("暂停定时任务")
    @PostMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public RestResponse<String> pause(@RequestBody String[] jobIds) {
        scheduleJobService.pause(jobIds);

        return RestResponse.ok();
    }

    /**
     * 恢复定时任务
     *
     * @param jobIds jobIds
     * @return RestResponse
     */
    @Operation(summary = "恢复定时任务", description = "恢复定时任务")
    @SysLog("恢复定时任务")
    @PostMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public RestResponse<String> resume(@RequestBody String[] jobIds) {
        scheduleJobService.resume(jobIds);

        return RestResponse.ok();
    }
}
