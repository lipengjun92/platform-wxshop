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
import com.platform.modules.wx.entity.MsgTemplateEntity;
import com.platform.modules.wx.form.TemplateMsgBatchForm;
import com.platform.modules.wx.service.MsgTemplateService;
import com.platform.modules.wx.service.TemplateMsgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 消息模板
 *
 * @author 李鹏军
 * @date 2019-11-12 18:30:15
 */
@Tag(name = "MsgTemplateManageController|消息模板")
@RequiredArgsConstructor
@RestController
@RequestMapping("/manage/msgTemplate")
public class MsgTemplateManageController {
    private final MsgTemplateService msgTemplateService;
    private final TemplateMsgService templateMsgService;

    /**
     * 消息模板列表
     *
     * @param params params
     * @return RestResponse
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("wx:msgtemplate:list")
    public RestResponse<Page<MsgTemplateEntity>> list(@RequestParam Map<String, Object> params) {
        Page<MsgTemplateEntity> page = msgTemplateService.queryPage(params);

        return RestResponse.ok(page);
    }


    /**
     * 消息模板信息
     *
     * @param id 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:msgtemplate:info")
    public RestResponse<MsgTemplateEntity> info(@PathVariable("id") String id) {
        MsgTemplateEntity msgTemplateEntity = msgTemplateService.getById(id);

        return RestResponse.ok(msgTemplateEntity);
    }

    /**
     * 消息模板信息
     *
     * @param name 名称
     * @return RestResponse
     */
    @Operation(summary = "消息模板信息", description = "消息模板信息")
    @GetMapping("/getByName")
    @RequiresPermissions("wx:msgtemplate:info")
    public RestResponse<MsgTemplateEntity> getByName(String name) {
        MsgTemplateEntity msgTemplateEntity = msgTemplateService.selectByName(name);

        return RestResponse.ok(msgTemplateEntity);
    }

    /**
     * 新增消息模板
     *
     * @param msgTemplateEntity 消息模板
     * @return RestResponse
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("新增消息模板")
    @PostMapping("/save")
    @RequiresPermissions("wx:msgtemplate:save")
    public RestResponse<String> save(@RequestBody MsgTemplateEntity msgTemplateEntity) {
        msgTemplateService.save(msgTemplateEntity);

        return RestResponse.ok();
    }

    /**
     * 修改消息模板
     *
     * @param msgTemplateEntity 消息模板
     * @return RestResponse
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改消息模板")
    @PostMapping("/update")
    @RequiresPermissions("wx:msgtemplate:update")
    public RestResponse<String> update(@RequestBody MsgTemplateEntity msgTemplateEntity) {
        msgTemplateEntity.setUpdateTime(new Date());
        msgTemplateService.updateById(msgTemplateEntity);

        return RestResponse.ok();
    }

    /**
     * 删除消息模板
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除消息模板")
    @PostMapping("/delete")
    @RequiresPermissions("wx:msgtemplate:delete")
    public RestResponse<String> delete(@RequestBody String[] ids) {
        msgTemplateService.removeByIds(Arrays.asList(ids));

        return RestResponse.ok();
    }

    /**
     * 同步公众号模板
     *
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "同步公众号模板", description = "同步公众号模板")
    @SysLog("同步公众号模板")
    @PostMapping("/syncWxTemplate")
    @RequiresPermissions("wx:msgtemplate:save")
    public RestResponse<String> syncWxTemplate() throws WxErrorException {
        msgTemplateService.syncWxTemplate();
        return RestResponse.ok();
    }

    /**
     * 批量向用户发送模板消息
     *
     * @param form 批量发送模板消息表单
     * @return RestResponse
     */
    @Operation(summary = "批量向用户发送模板消息", description = "批量向用户发送模板消息")
    @SysLog("批量向用户发送模板消息")
    @PostMapping("/sendMsgBatch")
    @RequiresPermissions("wx:msgtemplate:save")
    public RestResponse<String> sendMsgBatch(@RequestBody TemplateMsgBatchForm form) {
        templateMsgService.sendMsgBatch(form);
        return RestResponse.ok();
    }
}
