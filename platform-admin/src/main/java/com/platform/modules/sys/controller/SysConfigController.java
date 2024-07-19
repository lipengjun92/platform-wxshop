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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.modules.sys.entity.SysConfigEntity;
import com.platform.modules.sys.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author 李鹏军
 */
@Tag(name = "SysConfigController|系统配置")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {

    private final SysConfigService sysConfigService;

    /**
     * 所有系统配置列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "所有系统配置列表", description = "所有系统配置列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:config:list")
    public RestResponse<Page<SysConfigEntity>> list(@RequestParam Map<String, Object> params) {
        Page<SysConfigEntity> page = sysConfigService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * status=0系统配置列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "status=0系统配置列表", description = "status=0系统配置列表")
    @GetMapping("/queryKeyValues")
    @RequiresPermissions("sys:config:list")
    public RestResponse<List<Map<String, Object>>> queryKeyValues(@RequestParam Map<String, Object> params) {
        List<Map<String, Object>> maps = sysConfigService.listMaps(new QueryWrapper<SysConfigEntity>().select("PARAM_KEY", "PARAM_VALUE").eq("STATUS", 0));

        return RestResponse.ok(maps);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public RestResponse<SysConfigEntity> info(@PathVariable("id") String id) {
        SysConfigEntity config = sysConfigService.getById(id);

        return RestResponse.ok(config);
    }

    /**
     * 保存系统配置
     *
     * @param config config
     * @return RestResponse
     */
    @Operation(summary = "保存系统配置", description = "保存系统配置")
    @SysLog("保存系统配置")
    @PostMapping("/save")
    @RequiresPermissions("sys:config:save")
    public RestResponse<String> save(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.add(config);

        return RestResponse.ok();
    }

    /**
     * 修改系统配置
     *
     * @param config config
     * @return RestResponse
     */
    @Operation(summary = "修改系统配置", description = "修改系统配置")
    @SysLog("修改系统配置")
    @PostMapping("/update")
    @RequiresPermissions("sys:config:update")
    public RestResponse<String> update(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.update(config);

        return RestResponse.ok();
    }

    /**
     * 删除系统配置
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除系统配置", description = "删除系统配置")
    @SysLog("删除系统配置")
    @PostMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public RestResponse<String> delete(@RequestBody String[] ids) {
        sysConfigService.deleteBatch(ids);

        return RestResponse.ok();
    }

    /**
     * 根据key查询value
     *
     * @param key 主键
     * @return RestResponse
     */
    @Operation(summary = "根据key查询value", description = "根据key查询value")
    @GetMapping("/getConfigValue")
    @RequiresPermissions("sys:config:getConfigValue")
    public RestResponse<String> getConfigValue(String key) {
        String value = sysConfigService.getValue(key);

        return RestResponse.ok("操作成功", value);
    }

    /**
     * 保存
     *
     * @param config config
     * @return RestResponse
     */
    @Operation(summary = "修改系统配置", description = "修改系统配置")
    @SysLog("修改系统配置")
    @PostMapping("/saveConfigValue")
    @RequiresPermissions("sys:config:saveConfigValue")
    public RestResponse<String> saveConfigValue(@RequestBody SysConfigEntity config) {
        sysConfigService.updateValueByKey(config.getParamKey(), config.getParamValue());
        return RestResponse.ok();
    }
}
