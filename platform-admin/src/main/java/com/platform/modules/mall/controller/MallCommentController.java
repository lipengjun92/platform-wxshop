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
import com.platform.modules.mall.entity.MallCommentEntity;
import com.platform.modules.mall.service.MallCommentService;
import com.platform.modules.sys.controller.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论Controller
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2026-03-15 13:48:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/comment")
@Tag(name = "MallCommentController|评论")
public class MallCommentController extends AbstractController {

    private final MallCommentService mallCommentService;

    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:comment:list")
    public RestResponse<List<MallCommentEntity>> queryAll(@RequestParam Map<String, Object> params) {
        return RestResponse.ok(mallCommentService.queryAll(params));
    }

    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("mall:comment:list")
    public RestResponse<Page<MallCommentEntity>> list(@RequestParam Map<String, Object> params) {
        return RestResponse.ok(mallCommentService.queryPage(params));
    }

    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "主键", example = "1", required = true)})
    @GetMapping("/info/{id}")
    @RequiresPermissions("mall:comment:info")
    public RestResponse<MallCommentEntity> info(@PathVariable("id") Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        List<MallCommentEntity> list = mallCommentService.queryAll(params);
        if (list.isEmpty()) {
            return RestResponse.ok(mallCommentService.getById(id));
        }
        return RestResponse.ok(list.get(0));
    }

    @Operation(summary = "新增", description = "新增")
    @SysLog("新增评论")
    @PostMapping("/save")
    @RequiresPermissions("mall:comment:save")
    public RestResponse<String> save(@RequestBody MallCommentEntity mallComment) {
        mallCommentService.add(mallComment);
        return RestResponse.ok();
    }

    @Operation(summary = "修改", description = "修改")
    @SysLog("修改评论")
    @PostMapping("/update")
    @RequiresPermissions("mall:comment:update")
    public RestResponse<String> update(@RequestBody MallCommentEntity mallComment) {
        mallCommentService.update(mallComment);
        return RestResponse.ok();
    }

    @Operation(summary = "删除", description = "删除")
    @SysLog("删除评论")
    @PostMapping("/delete")
    @RequiresPermissions("mall:comment:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallCommentService.deleteBatch(ids);
        return RestResponse.ok();
    }
}
