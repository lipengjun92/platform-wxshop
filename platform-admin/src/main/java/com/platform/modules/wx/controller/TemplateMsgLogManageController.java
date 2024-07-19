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
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.wx.entity.TemplateMsgLogEntity;
import com.platform.modules.wx.service.TemplateMsgLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 模版消息发送记录
 *
 * @author 李鹏军
 * @date 2019-11-12 18:30:15
 */
@Tag(name = "TemplateMsgLogManageController|模版消息发送记录")
@RequiredArgsConstructor
@RestController
@RequestMapping("/manage/templateMsgLog")
public class TemplateMsgLogManageController {
    private final TemplateMsgLogService templateMsgLogService;

    /**
     * 模板消息日志列表
     *
     * @param params params
     * @return RestResponse
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("wx:templatemsglog:list")
    public RestResponse<Page<TemplateMsgLogEntity>> list(@RequestParam Map<String, Object> params) {
        Page<TemplateMsgLogEntity> page = templateMsgLogService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 模板消息日志
     *
     * @param logId 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{logId}")
    @RequiresPermissions("wx:templatemsglog:info")
    public RestResponse<TemplateMsgLogEntity> info(@PathVariable("logId") String logId) {
        TemplateMsgLogEntity templateMsgLogEntity = templateMsgLogService.getById(logId);

        return RestResponse.ok(templateMsgLogEntity);
    }

    /**
     * 删除模板消息日志
     *
     * @param logIds logIds
     * @return RestResponse
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除模板消息日志")
    @PostMapping("/delete")
    @RequiresPermissions("wx:templatemsglog:delete")
    public RestResponse<String> delete(@RequestBody String[] logIds) {
        templateMsgLogService.removeByIds(Arrays.asList(logIds));

        return RestResponse.ok();
    }
}
