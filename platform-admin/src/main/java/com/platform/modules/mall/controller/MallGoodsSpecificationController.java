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
import com.platform.modules.mall.entity.MallGoodsSpecificationEntity;
import com.platform.modules.mall.service.MallGoodsSpecificationService;
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
 * 商品对应规格表值表Controller
 *
 * @author 李鹏军
 * @since 2026-02-24 13:15:28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/goodsspecification")
@Tag(name = "MallGoodsSpecificationController|商品对应规格表值表")
public class MallGoodsSpecificationController extends AbstractController {

    private final MallGoodsSpecificationService mallGoodsSpecificationService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:goodsspecification:list")
    public RestResponse<List<MallGoodsSpecificationEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<MallGoodsSpecificationEntity> list = mallGoodsSpecificationService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 分页查询商品对应规格表值表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询商品对应规格表值表", description = "分页查询商品对应规格表值表")
    @GetMapping("/list")
    @RequiresPermissions("mall:goodsspecification:list")
    public RestResponse<Page<MallGoodsSpecificationEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MallGoodsSpecificationEntity> page = mallGoodsSpecificationService.queryPage(params);

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
    @RequiresPermissions("mall:goodsspecification:info")
    public RestResponse<MallGoodsSpecificationEntity> info(@PathVariable("id") Integer id) {
        MallGoodsSpecificationEntity mallGoodsSpecification = mallGoodsSpecificationService.getById(id);

        return RestResponse.ok(mallGoodsSpecification);
    }

    /**
     * 新增商品对应规格表值表
     *
     * @param mallGoodsSpecification mallGoodsSpecification
     * @return RestResponse
     */
    @Operation(summary = "新增商品对应规格表值表", description = "新增商品对应规格表值表")
    @SysLog("新增商品对应规格表值表")
    @PostMapping("/save")
    @RequiresPermissions("mall:goodsspecification:save")
    public RestResponse<String> save(@RequestBody MallGoodsSpecificationEntity mallGoodsSpecification) {

        mallGoodsSpecificationService.add(mallGoodsSpecification);

        return RestResponse.ok();
    }

    /**
     * 修改商品对应规格表值表
     *
     * @param mallGoodsSpecification mallGoodsSpecification
     * @return RestResponse
     */
    @Operation(summary = "修改商品对应规格表值表", description = "修改商品对应规格表值表")
    @SysLog("修改商品对应规格表值表")
    @PostMapping("/update")
    @RequiresPermissions("mall:goodsspecification:update")
    public RestResponse<String> update(@RequestBody MallGoodsSpecificationEntity mallGoodsSpecification) {

        mallGoodsSpecificationService.update(mallGoodsSpecification);

        return RestResponse.ok();
    }

    /**
     * 根据主键删除商品对应规格表值表
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除商品对应规格表值表", description = "删除商品对应规格表值表")
    @SysLog("删除商品对应规格表值表")
    @PostMapping("/delete")
    @RequiresPermissions("mall:goodsspecification:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallGoodsSpecificationService.deleteBatch(ids);

        return RestResponse.ok();
    }
}
