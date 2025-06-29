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
import com.platform.modules.mall.entity.MallKeywordsEntity;
import com.platform.modules.mall.service.MallKeywordsService;
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
 * 关键词热搜表Controller
 *
 * @author 李鹏军
 * @since 2025-06-29 19:29:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/keywords")
@Tag(name = "MallKeywordsController|关键词热搜表")
public class MallKeywordsController extends AbstractController {

    private final MallKeywordsService mallKeywordsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:keywords:list")
    public RestResponse<List<MallKeywordsEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<MallKeywordsEntity> list = mallKeywordsService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 分页查询关键词热搜表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询关键词热搜表", description = "分页查询关键词热搜表")
    @GetMapping("/list")
    @RequiresPermissions("mall:keywords:list")
    public RestResponse<Page<MallKeywordsEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MallKeywordsEntity> page = mallKeywordsService.queryPage(params);

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
    @RequiresPermissions("mall:keywords:info")
    public RestResponse<MallKeywordsEntity> info(@PathVariable("id") Integer id) {
        MallKeywordsEntity mallKeywords = mallKeywordsService.getById(id);

        return RestResponse.ok(mallKeywords);
    }

    /**
     * 新增关键词热搜表
     *
     * @param mallKeywords mallKeywords
     * @return RestResponse
     */
    @Operation(summary = "新增关键词热搜表", description = "新增关键词热搜表")
    @SysLog("新增关键词热搜表")
    @PostMapping("/save")
    @RequiresPermissions("mall:keywords:save")
    public RestResponse<String> save(@RequestBody MallKeywordsEntity mallKeywords) {

        mallKeywordsService.add(mallKeywords);

        return RestResponse.ok();
    }

    /**
     * 修改关键词热搜表
     *
     * @param mallKeywords mallKeywords
     * @return RestResponse
     */
    @Operation(summary = "修改关键词热搜表", description = "修改关键词热搜表")
    @SysLog("修改关键词热搜表")
    @PostMapping("/update")
    @RequiresPermissions("mall:keywords:update")
    public RestResponse<String> update(@RequestBody MallKeywordsEntity mallKeywords) {

        mallKeywordsService.update(mallKeywords);

        return RestResponse.ok();
    }

    /**
     * 根据主键删除关键词热搜表
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除关键词热搜表", description = "删除关键词热搜表")
    @SysLog("删除关键词热搜表")
    @PostMapping("/delete")
    @RequiresPermissions("mall:keywords:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallKeywordsService.deleteBatch(ids);

        return RestResponse.ok();
    }
}
