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
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.entity.SysLogEntity;
import com.platform.modules.sys.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 系统日志
 *
 * @author 李鹏军
 */
@Tag(name = "SysLogController|系统日志")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    private final SysLogService sysLogService;

    /**
     * 列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "系统日志列表", description = "系统日志列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:log:list")
    public RestResponse<Page<SysLogEntity>> list(@RequestParam Map<String, Object> params) {
        Page<SysLogEntity> page = sysLogService.queryPage(params);

        return RestResponse.ok(page);
    }
}
