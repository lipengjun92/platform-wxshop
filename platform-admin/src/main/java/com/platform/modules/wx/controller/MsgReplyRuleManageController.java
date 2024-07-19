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
package com.platform.modules.wx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.modules.wx.entity.MsgReplyRuleEntity;
import com.platform.modules.wx.service.MsgReplyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 自动回复规则
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Tag(name = "MsgReplyRuleManageController|自动回复规则")
@RequiredArgsConstructor
@RestController
@RequestMapping("/manage/msgReplyRule")
public class MsgReplyRuleManageController {
    private final MsgReplyRuleService msgReplyRuleService;

    /**
     * 列表
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("wx:msgreplyrule:list")
    public RestResponse<Page<MsgReplyRuleEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MsgReplyRuleEntity> page = msgReplyRuleService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 信息
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{ruleId}")
    @RequiresPermissions("wx:msgreplyrule:info")
    public RestResponse<MsgReplyRuleEntity> info(@PathVariable("ruleId") String ruleId) {
        MsgReplyRuleEntity msgReplyRule = msgReplyRuleService.getById(ruleId);

        return RestResponse.ok(msgReplyRule);
    }

    /**
     * 保存
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("新增公众号回复规则")
    @PostMapping("/save")
    @RequiresPermissions("wx:msgreplyrule:save")
    public RestResponse<String> save(@RequestBody MsgReplyRuleEntity msgReplyRule) {
        ValidatorUtils.validateEntity(msgReplyRule);
        msgReplyRuleService.save(msgReplyRule);

        return RestResponse.ok();
    }

    /**
     * 修改
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改公众号回复规则")
    @PostMapping("/update")
    @RequiresPermissions("wx:msgreplyrule:update")
    public RestResponse<String> update(@RequestBody MsgReplyRuleEntity msgReplyRule) {
        ValidatorUtils.validateEntity(msgReplyRule);
        msgReplyRuleService.updateById(msgReplyRule);

        return RestResponse.ok();
    }

    /**
     * 删除
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除公众号回复规则")
    @PostMapping("/delete")
    @RequiresPermissions("wx:msgreplyrule:delete")
    public RestResponse<String> delete(@RequestBody String[] ruleIds) {
        msgReplyRuleService.removeByIds(Arrays.asList(ruleIds));

        return RestResponse.ok();
    }
}
