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
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.entity.SysUserTokenEntity;
import com.platform.modules.sys.service.SysUserTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统用户TokenController
 *
 * @author 李鹏军
 * @since 2019-02-01 11:12:49
 */
@Tag(name = "SysUserTokenController|登录用户")
@RequiredArgsConstructor
@RestController
@RequestMapping("sys/usertoken")
public class SysUserTokenController {
    private final SysUserTokenService sysUserTokenService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("sys:usertoken:list")
    public RestResponse<Page<SysUserTokenEntity>> list(@RequestParam Map<String, Object> params) {
        Page<SysUserTokenEntity> page = sysUserTokenService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 批量下线用户(删除用户token记录)
     *
     * @param userIds userIds
     * @return RestResponse
     */
    @Operation(summary = "批量下线用户", description = "批量下线用户")
    @SysLog("批量下线用户")
    @PostMapping("/offline")
    @RequiresPermissions("sys:usertoken:offline")
    public RestResponse<String> offline(@RequestBody String[] userIds) {
        sysUserTokenService.offlineBatch(userIds);
        return RestResponse.ok();
    }
}
