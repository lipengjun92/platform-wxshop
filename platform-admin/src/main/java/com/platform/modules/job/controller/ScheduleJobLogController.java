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
import com.platform.common.utils.RestResponse;
import com.platform.modules.job.entity.ScheduleJobLogEntity;
import com.platform.modules.job.service.ScheduleJobLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author 李鹏军
 */
@Tag(name = "ScheduleJobLogController|定时任务日志")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {

    private final ScheduleJobLogService scheduleJobLogService;

    /**
     * 分页查询定时任务日志
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询定时任务日志", description = "分页查询定时任务日志")
    @GetMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public RestResponse<Page<ScheduleJobLogEntity>> list(@RequestParam Map<String, Object> params) {
        Page<ScheduleJobLogEntity> page = scheduleJobLogService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 根据主键查询详情
     *
     * @param logId logId
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "logId", description = "logId", required = true)}
    )
    @GetMapping("/info/{logId}")
    public RestResponse<ScheduleJobLogEntity> info(@PathVariable("logId") String logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);

        return RestResponse.ok(log);
    }
}
