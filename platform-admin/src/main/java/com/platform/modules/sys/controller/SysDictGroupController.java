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
import com.platform.common.validator.ValidatorUtils;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.sys.entity.SysDictGroupEntity;
import com.platform.modules.sys.service.SysDictGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据字典分组Controller
 *
 * @author 李鹏军
 * @since 2019-01-15 11:42:20
 */
@Tag(name = "SysDictGroupController|数据字典分组")
@RequiredArgsConstructor
@RestController
@RequestMapping("sys/dictgroup")
public class SysDictGroupController {

    private final SysDictGroupService sysDictGroupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("sys:dictgroup:list")
    public RestResponse<List<SysDictGroupEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<SysDictGroupEntity> list = sysDictGroupService.queryAll(params);

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
    @RequiresPermissions("sys:dictgroup:list")
    public RestResponse<Page<SysDictGroupEntity>> list(@RequestParam Map<String, Object> params) {
        Page<SysDictGroupEntity> page = sysDictGroupService.queryPage(params);

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
    @RequiresPermissions("sys:dictgroup:info")
    public RestResponse<SysDictGroupEntity> info(@PathVariable("id") String id) {
        SysDictGroupEntity sysDictGroup = sysDictGroupService.getById(id);

        return RestResponse.ok(sysDictGroup);
    }

    /**
     * 保存数据字典分组
     *
     * @param sysDictGroup sysDictGroup
     * @return RestResponse
     */
    @Operation(summary = "保存数据字典分组", description = "保存数据字典分组")
    @SysLog("保存数据字典分组")
    @PostMapping("/save")
    @RequiresPermissions("sys:dictgroup:save")
    public RestResponse<String> save(@RequestBody SysDictGroupEntity sysDictGroup) {
        ValidatorUtils.validateEntity(sysDictGroup, AddGroup.class);
        sysDictGroupService.add(sysDictGroup);

        return RestResponse.ok();
    }

    /**
     * 修改数据字典分组
     *
     * @param sysDictGroup sysDictGroup
     * @return RestResponse
     */
    @Operation(summary = "修改数据字典分组", description = "修改数据字典分组")
    @SysLog("修改数据字典分组")
    @PostMapping("/update")
    @RequiresPermissions("sys:dictgroup:update")
    public RestResponse<String> update(@RequestBody SysDictGroupEntity sysDictGroup) {
        ValidatorUtils.validateEntity(sysDictGroup, UpdateGroup.class);
        sysDictGroupService.update(sysDictGroup);

        return RestResponse.ok();
    }

    /**
     * 删除数据字典分组
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除数据字典分组", description = "删除数据字典分组")
    @SysLog("删除数据字典分组")
    @PostMapping("/delete")
    @RequiresPermissions("sys:dictgroup:delete")
    public RestResponse<String> delete(@RequestBody String[] ids) {
        sysDictGroupService.deleteBatch(ids);

        return RestResponse.ok();
    }
}
