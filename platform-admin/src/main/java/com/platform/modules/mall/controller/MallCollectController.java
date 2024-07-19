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
import com.platform.modules.mall.entity.MallCollectEntity;
import com.platform.modules.mall.service.MallCollectService;
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
 * @since 2024-07-19 20:53:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/collect")
@Tag(name = "MallCollectController|")
public class MallCollectController extends AbstractController {

    private final MallCollectService mallCollectService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:collect:list")
    public RestResponse<List<MallCollectEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<MallCollectEntity> list = mallCollectService.queryAll(params);

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
    @RequiresPermissions("mall:collect:list")
    public RestResponse<Page<MallCollectEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MallCollectEntity> page = mallCollectService.queryPage(params);

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
    @RequiresPermissions("mall:collect:info")
    public RestResponse<MallCollectEntity> info(@PathVariable("id") Integer id) {
        MallCollectEntity mallCollect = mallCollectService.getById(id);

        return RestResponse.ok(mallCollect);
    }

    /**
     * 新增
     *
     * @param mallCollect mallCollect
     * @return RestResponse
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("新增")
    @PostMapping("/save")
    @RequiresPermissions("mall:collect:save")
    public RestResponse<String> save(@RequestBody MallCollectEntity mallCollect) {

        mallCollectService.add(mallCollect);

        return RestResponse.ok();
    }

    /**
     * 修改
     *
     * @param mallCollect mallCollect
     * @return RestResponse
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改")
    @PostMapping("/update")
    @RequiresPermissions("mall:collect:update")
    public RestResponse<String> update(@RequestBody MallCollectEntity mallCollect) {

        mallCollectService.update(mallCollect);

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
    @RequiresPermissions("mall:collect:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallCollectService.deleteBatch(ids);

        return RestResponse.ok();
    }
}
