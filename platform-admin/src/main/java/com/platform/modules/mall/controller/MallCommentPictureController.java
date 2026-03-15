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
import com.platform.modules.mall.entity.MallCommentPictureEntity;
import com.platform.modules.mall.service.MallCommentPictureService;
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
 * 评论图片Controller
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2026-03-15 13:48:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("mall/commentpicture")
@Tag(name = "MallCommentPictureController|评论图片")
public class MallCommentPictureController extends AbstractController {

    private final MallCommentPictureService mallCommentPictureService;

    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("mall:commentpicture:list")
    public RestResponse<List<MallCommentPictureEntity>> queryAll(@RequestParam Map<String, Object> params) {
        return RestResponse.ok(mallCommentPictureService.queryAll(params));
    }

    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("mall:commentpicture:list")
    public RestResponse<Page<MallCommentPictureEntity>> list(@RequestParam Map<String, Object> params) {
        return RestResponse.ok(mallCommentPictureService.queryPage(params));
    }

    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "主键", example = "1", required = true)})
    @GetMapping("/info/{id}")
    @RequiresPermissions("mall:commentpicture:info")
    public RestResponse<MallCommentPictureEntity> info(@PathVariable("id") Integer id) {
        return RestResponse.ok(mallCommentPictureService.getById(id));
    }

    @Operation(summary = "新增", description = "新增")
    @SysLog("新增评论图片")
    @PostMapping("/save")
    @RequiresPermissions("mall:commentpicture:save")
    public RestResponse<String> save(@RequestBody MallCommentPictureEntity mallCommentPicture) {
        mallCommentPictureService.add(mallCommentPicture);
        return RestResponse.ok();
    }

    @Operation(summary = "修改", description = "修改")
    @SysLog("修改评论图片")
    @PostMapping("/update")
    @RequiresPermissions("mall:commentpicture:update")
    public RestResponse<String> update(@RequestBody MallCommentPictureEntity mallCommentPicture) {
        mallCommentPictureService.update(mallCommentPicture);
        return RestResponse.ok();
    }

    @Operation(summary = "删除", description = "删除")
    @SysLog("删除评论图片")
    @PostMapping("/delete")
    @RequiresPermissions("mall:commentpicture:delete")
    public RestResponse<String> delete(@RequestBody Integer[] ids) {
        mallCommentPictureService.deleteBatch(ids);
        return RestResponse.ok();
    }
}
