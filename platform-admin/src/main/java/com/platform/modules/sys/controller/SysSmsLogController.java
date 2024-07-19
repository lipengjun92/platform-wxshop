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

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.entity.SmsConfig;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.sys.service.SysSmsLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 短信发送日志Controller
 *
 * @author 李鹏军
 * @since 2019-02-12 09:51:15
 */
@Tag(name = "SysSmsLogController|短信发送日志")
@RequiredArgsConstructor
@RestController
@RequestMapping("sys/smslog")
public class SysSmsLogController extends AbstractController {

    private final SysSmsLogService sysSmsLogService;
    private final SysConfigService sysConfigService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("sys:smslog:list")
    public RestResponse<List<SysSmsLogEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<SysSmsLogEntity> list = sysSmsLogService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("sys:smslog:list")
    public RestResponse<Page<SysSmsLogEntity>> list(@RequestParam Map<String, Object> params) {
        Page<SysSmsLogEntity> page = sysSmsLogService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:smslog:info")
    public RestResponse<SysSmsLogEntity> info(@PathVariable("id") String id) {
        SysSmsLogEntity sysSmsLog = sysSmsLogService.getById(id);

        return RestResponse.ok(sysSmsLog);
    }

    /**
     * 保存
     *
     * @param sysSmsLog sysSmsLog
     * @return RestResponse
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("保存短信发送记录")
    @PostMapping("/save")
    @RequiresPermissions("sys:smslog:save")
    public RestResponse<String> save(@RequestBody SysSmsLogEntity sysSmsLog) {

        sysSmsLogService.add(sysSmsLog);

        return RestResponse.ok();
    }

    /**
     * 修改
     *
     * @param sysSmsLog sysSmsLog
     * @return RestResponse
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改短信发送记录")
    @PostMapping("/update")
    @RequiresPermissions("sys:smslog:update")
    public RestResponse<String> update(@RequestBody SysSmsLogEntity sysSmsLog) {

        sysSmsLogService.update(sysSmsLog);

        return RestResponse.ok();
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除短信发送记录")
    @PostMapping("/delete")
    @RequiresPermissions("sys:smslog:delete")
    public RestResponse<String> delete(@RequestBody String[] ids) {
        sysSmsLogService.deleteBatch(ids);

        return RestResponse.ok();
    }

    /**
     * 短信配置信息
     *
     * @return RestResponse
     */
    @Operation(summary = "短信配置信息", description = "短信配置信息")
    @GetMapping("/config")
    @RequiresPermissions("sys:smslog:config")
    public RestResponse<SmsConfig> config() {
        SmsConfig config = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);

        return RestResponse.ok(config);
    }

    /**
     * 保存短信配置信息
     *
     * @param config config
     * @return RestResponse
     */
    @Operation(summary = "保存短信配置信息", description = "保存短信配置信息")
    @SysLog("保存短信配置信息")
    @RequiresPermissions("sys:smslog:config")
    @PostMapping("/saveConfig")
    public RestResponse<String> saveConfig(@RequestBody SmsConfig config) {
        sysConfigService.updateValueByKey(Constant.SMS_CONFIG_KEY, JSON.toJSONString(config));
        return RestResponse.ok();
    }
}
