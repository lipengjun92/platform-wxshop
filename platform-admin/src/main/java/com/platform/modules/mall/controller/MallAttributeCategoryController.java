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
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.mall.entity.MallAttributeCategoryEntity;
import com.platform.modules.mall.service.MallAttributeCategoryService;
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
 * Controller
 *
 * @author 李鹏军
 * @since 2025-06-29 18:07:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/attributecategory")
@Tag(name = "MallAttributeCategoryController|")
public class MallAttributeCategoryController extends AbstractController {

    private final MallAttributeCategoryService mallAttributeCategoryService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:attributecategory:list")
    public RestResponse<List<MallAttributeCategoryEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<MallAttributeCategoryEntity> list = mallAttributeCategoryService.queryAll(params);

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
    @RequiresPermissions("mall:attributecategory:list")
    public RestResponse<Page<MallAttributeCategoryEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MallAttributeCategoryEntity> page = mallAttributeCategoryService.queryPage(params);

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
    @RequiresPermissions("mall:attributecategory:info")
    public RestResponse<MallAttributeCategoryEntity> info(@PathVariable("id") Integer id) {
        MallAttributeCategoryEntity mallAttributeCategory = mallAttributeCategoryService.getById(id);

        return RestResponse.ok(mallAttributeCategory);
    }

    /**
     * 新增
     *
     * @param mallAttributeCategory mallAttributeCategory
     * @return RestResponse
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("新增")
    @PostMapping("/save")
    @RequiresPermissions("mall:attributecategory:save")
    public RestResponse<String> save(@RequestBody MallAttributeCategoryEntity mallAttributeCategory) {

        mallAttributeCategoryService.add(mallAttributeCategory);

        return RestResponse.ok();
    }

    /**
     * 修改
     *
     * @param mallAttributeCategory mallAttributeCategory
     * @return RestResponse
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改")
    @PostMapping("/update")
    @RequiresPermissions("mall:attributecategory:update")
    public RestResponse<String> update(@RequestBody MallAttributeCategoryEntity mallAttributeCategory) {

        mallAttributeCategoryService.update(mallAttributeCategory);

        return RestResponse.ok();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除")
    @PostMapping("/delete")
    @RequiresPermissions("mall:attributecategory:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallAttributeCategoryService.deleteBatch(ids);

        return RestResponse.ok();
    }
}
