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
package com.platform.modules.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallSpecificationEntity;
import com.platform.modules.mall.service.MallSpecificationService;
import com.platform.modules.sys.controller.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 规格表Controller
 *
 * @author 李鹏军
 * @since 2026-02-24 10:10:05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/specification")
@Tag(name = "MallSpecificationController|规格表")
public class MallSpecificationController extends AbstractController {

    private final MallSpecificationService mallSpecificationService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:specification:list")
    public RestResponse<List<MallSpecificationEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<MallSpecificationEntity> list = mallSpecificationService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 分页查询规格表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询规格表", description = "分页查询规格表")
    @GetMapping("/list")
    @RequiresPermissions("mall:specification:list")
    public RestResponse<Page<MallSpecificationEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MallSpecificationEntity> page = mallSpecificationService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "主键", example = "1", required = true)}
    )
    @GetMapping("/info/{id}")
    @RequiresPermissions("mall:specification:info")
    public RestResponse<MallSpecificationEntity> info(@PathVariable("id") Integer id) {
        MallSpecificationEntity mallSpecification = mallSpecificationService.getById(id);

        return RestResponse.ok(mallSpecification);
    }

    /**
     * 新增规格表
     *
     * @param mallSpecification mallSpecification
     * @return RestResponse
     */
    @Operation(summary = "新增规格表", description = "新增规格表")
    @SysLog("新增规格表")
    @PostMapping("/save")
    @RequiresPermissions("mall:specification:save")
    public RestResponse<String> save(@RequestBody MallSpecificationEntity mallSpecification) {

        mallSpecificationService.add(mallSpecification);

        return RestResponse.ok();
    }

    /**
     * 修改规格表
     *
     * @param mallSpecification mallSpecification
     * @return RestResponse
     */
    @Operation(summary = "修改规格表", description = "修改规格表")
    @SysLog("修改规格表")
    @PostMapping("/update")
    @RequiresPermissions("mall:specification:update")
    public RestResponse<String> update(@RequestBody MallSpecificationEntity mallSpecification) {

        mallSpecificationService.update(mallSpecification);

        return RestResponse.ok();
    }

    /**
     * 根据主键删除规格表
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除规格表", description = "删除规格表")
    @SysLog("删除规格表")
    @PostMapping("/delete")
    @RequiresPermissions("mall:specification:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallSpecificationService.deleteBatch(ids);

        return RestResponse.ok();
    }
}
