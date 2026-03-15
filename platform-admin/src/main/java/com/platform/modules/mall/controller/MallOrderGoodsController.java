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
import com.platform.modules.mall.entity.MallOrderGoodsEntity;
import com.platform.modules.mall.service.MallOrderGoodsService;
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
 * 订单商品明细管理控制器。
 * <p>
 * 提供后台订单商品明细的查询、分页、详情、增删改接口。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/ordergoods")
@Tag(name = "MallOrderGoodsController|订单商品")
public class MallOrderGoodsController extends AbstractController {

    private final MallOrderGoodsService mallOrderGoodsService;

    /**
     * 查询订单商品明细列表。
     *
     * @param params 查询参数
     * @return 订单商品明细列表
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:ordergoods:list")
    public RestResponse<List<MallOrderGoodsEntity>> queryAll(@RequestParam Map<String, Object> params) {
        return RestResponse.ok(mallOrderGoodsService.queryAll(params));
    }

    /**
     * 分页查询订单商品明细。
     *
     * @param params 查询参数
     * @return 分页数据
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("mall:ordergoods:list")
    public RestResponse<Page<MallOrderGoodsEntity>> list(@RequestParam Map<String, Object> params) {
        return RestResponse.ok(mallOrderGoodsService.queryPage(params));
    }

    /**
     * 根据主键查询订单商品明细详情。
     *
     * @param id 主键
     * @return 订单商品明细详情
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "主键", example = "1", required = true)})
    @GetMapping("/info/{id}")
    @RequiresPermissions("mall:ordergoods:info")
    public RestResponse<MallOrderGoodsEntity> info(@PathVariable("id") Integer id) {
        return RestResponse.ok(mallOrderGoodsService.getById(id));
    }

    /**
     * 新增订单商品明细。
     *
     * @param mallOrderGoods 订单商品明细实体
     * @return 处理结果
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("新增订单商品明细")
    @PostMapping("/save")
    @RequiresPermissions("mall:ordergoods:save")
    public RestResponse<String> save(@RequestBody MallOrderGoodsEntity mallOrderGoods) {
        mallOrderGoodsService.add(mallOrderGoods);
        return RestResponse.ok();
    }

    /**
     * 修改订单商品明细。
     *
     * @param mallOrderGoods 订单商品明细实体
     * @return 处理结果
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改订单商品明细")
    @PostMapping("/update")
    @RequiresPermissions("mall:ordergoods:update")
    public RestResponse<String> update(@RequestBody MallOrderGoodsEntity mallOrderGoods) {
        mallOrderGoodsService.update(mallOrderGoods);
        return RestResponse.ok();
    }

    /**
     * 删除订单商品明细。
     *
     * @param ids 主键数组
     * @return 处理结果
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除订单商品明细")
    @PostMapping("/delete")
    @RequiresPermissions("mall:ordergoods:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallOrderGoodsService.deleteBatch(ids);
        return RestResponse.ok();
    }
}
