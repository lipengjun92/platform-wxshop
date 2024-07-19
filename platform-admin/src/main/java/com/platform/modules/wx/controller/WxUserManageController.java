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
package com.platform.modules.wx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.utils.RestResponse;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author 李鹏军
 */
@Tag(name = "WxUserManageController|公众号粉丝")
@RequiredArgsConstructor
@RestController
@RequestMapping("/manage/wxUser")
public class WxUserManageController {
    private final WxUserService userService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("wx:wxuser:list")
    @Operation(summary = "分页查询", description = "分页查询")
    public RestResponse<Page<WxUserEntity>> list(@RequestParam Map<String, Object> params) {
        Page<WxUserEntity> page = userService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 列表
     */
    @PostMapping("/listByIds")
    @RequiresPermissions("wx:wxuser:list")
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    public RestResponse<List<WxUserEntity>> listByIds(@RequestBody String[] openids) {
        List<WxUserEntity> users = userService.listByIds(Arrays.asList(openids));
        return RestResponse.ok(users);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{openid}")
    @RequiresPermissions("wx:wxuser:info")
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    public RestResponse<WxUserEntity> info(@PathVariable("openid") String openid) {
        WxUserEntity wxUser = userService.getById(openid);

        return RestResponse.ok(wxUser);
    }
}
