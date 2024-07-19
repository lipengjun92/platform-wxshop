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
import com.platform.modules.wx.entity.WxMsgEntity;
import com.platform.modules.wx.form.WxMsgReplyForm;
import com.platform.modules.wx.service.MsgReplyService;
import com.platform.modules.wx.service.WxMsgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 微信消息
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Tag(name = "WxMsgManageController|微信消息")
@RequiredArgsConstructor
@RestController
@RequestMapping("/manage/wxMsg")
public class WxMsgManageController {
    private final WxMsgService wxMsgService;
    private final MsgReplyService msgReplyService;

    /**
     * 列表
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("wx:wxmsg:list")
    public RestResponse<Page<WxMsgEntity>> list(@RequestParam Map<String, Object> params) {
        Page<WxMsgEntity> page = wxMsgService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 信息
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:wxmsg:info")
    public RestResponse<WxMsgEntity> info(@PathVariable("id") Long id) {
        WxMsgEntity wxMsg = wxMsgService.getById(id);

        return RestResponse.ok(wxMsg);
    }

    /**
     * 回复
     */
    @Operation(summary = "回复公众号消息", description = "回复公众号消息")
    @SysLog("回复公众号消息")
    @PostMapping("/reply")
    @RequiresPermissions("wx:wxmsg:save")
    public RestResponse<String> reply(@RequestBody WxMsgReplyForm form) {

        msgReplyService.reply(form.getOpenid(), form.getReplyType(), form.getReplyContent());
        return RestResponse.ok();
    }

    /**
     * 删除
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除众号消息")
    @PostMapping("/delete")
    @RequiresPermissions("wx:wxmsg:delete")
    public RestResponse<String> delete(@RequestBody Long[] ids) {
        wxMsgService.removeByIds(Arrays.asList(ids));

        return RestResponse.ok();
    }
}
