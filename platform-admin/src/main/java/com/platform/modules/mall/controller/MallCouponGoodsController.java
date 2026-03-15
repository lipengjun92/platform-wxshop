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
import com.platform.modules.mall.entity.MallCouponGoodsEntity;
import com.platform.modules.mall.service.MallCouponGoodsService;
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
 * 优惠券关联商品Controller
 *
 * @author 李鹏军
 * @since 2026-02-24 10:20:41
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/coupongoods")
@Tag(name = "MallCouponGoodsController|优惠券关联商品")
public class MallCouponGoodsController extends AbstractController {

    private final MallCouponGoodsService mallCouponGoodsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:coupongoods:list")
    public RestResponse<List<MallCouponGoodsEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<MallCouponGoodsEntity> list = mallCouponGoodsService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 分页查询优惠券关联商品
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询优惠券关联商品", description = "分页查询优惠券关联商品")
    @GetMapping("/list")
    @RequiresPermissions("mall:coupongoods:list")
    public RestResponse<Page<MallCouponGoodsEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MallCouponGoodsEntity> page = mallCouponGoodsService.queryPage(params);

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
    @RequiresPermissions("mall:coupongoods:info")
    public RestResponse<MallCouponGoodsEntity> info(@PathVariable("id") Integer id) {
        MallCouponGoodsEntity mallCouponGoods = mallCouponGoodsService.getById(id);

        return RestResponse.ok(mallCouponGoods);
    }

    /**
     * 新增优惠券关联商品
     *
     * @param mallCouponGoods mallCouponGoods
     * @return RestResponse
     */
    @Operation(summary = "新增优惠券关联商品", description = "新增优惠券关联商品")
    @SysLog("新增优惠券关联商品")
    @PostMapping("/save")
    @RequiresPermissions("mall:coupongoods:save")
    public RestResponse<String> save(@RequestBody MallCouponGoodsEntity mallCouponGoods) {

        mallCouponGoodsService.add(mallCouponGoods);

        return RestResponse.ok();
    }

    /**
     * 修改优惠券关联商品
     *
     * @param mallCouponGoods mallCouponGoods
     * @return RestResponse
     */
    @Operation(summary = "修改优惠券关联商品", description = "修改优惠券关联商品")
    @SysLog("修改优惠券关联商品")
    @PostMapping("/update")
    @RequiresPermissions("mall:coupongoods:update")
    public RestResponse<String> update(@RequestBody MallCouponGoodsEntity mallCouponGoods) {

        mallCouponGoodsService.update(mallCouponGoods);

        return RestResponse.ok();
    }

    /**
     * 根据主键删除优惠券关联商品
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除优惠券关联商品", description = "删除优惠券关联商品")
    @SysLog("删除优惠券关联商品")
    @PostMapping("/delete")
    @RequiresPermissions("mall:coupongoods:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallCouponGoodsService.deleteBatch(ids);

        return RestResponse.ok();
    }
}
