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
import com.platform.modules.wx.entity.WxQrCodeEntity;
import com.platform.modules.wx.form.WxQrCodeForm;
import com.platform.modules.wx.service.WxQrCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 公众号带参二维码管理
 * https://github.com/Wechat-Group/WxJava/wiki/MP_二维码管理
 *
 * @author 李鹏军
 * @date 2020-06-17 13:56:51
 */
@Tag(name = "WxQrCodeManageController|公众号带参二维码")
@RequiredArgsConstructor
@RestController
@RequestMapping("/manage/wxQrCode")
public class WxQrCodeManageController {

    private final WxQrCodeService wxQrCodeService;

    /**
     * 创建带参二维码ticket
     */
    @Operation(summary = "创建带参二维码ticket", description = "创建带参二维码ticket")
    @PostMapping("/createTicket")
    @RequiresPermissions("wx:wxqrcode:save")
    public RestResponse<WxMpQrCodeTicket> createTicket(@RequestBody WxQrCodeForm form) throws WxErrorException {

        WxMpQrCodeTicket ticket = wxQrCodeService.createQrCode(form);
        return RestResponse.ok(ticket);
    }

    /**
     * 列表
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("wx:wxqrcode:list")
    public RestResponse<Page<WxQrCodeEntity>> list(@RequestParam Map<String, Object> params) {
        Page<WxQrCodeEntity> page = wxQrCodeService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 信息
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:wxqrcode:info")
    public RestResponse<WxQrCodeEntity> info(@PathVariable("id") String id) {
        WxQrCodeEntity wxQrCode = wxQrCodeService.getById(id);

        return RestResponse.ok(wxQrCode);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除公众号二维码")
    @PostMapping("/delete")
    @RequiresPermissions("wx:wxqrcode:delete")
    public RestResponse<String> delete(@RequestBody String[] ids) {
        wxQrCodeService.removeByIds(Arrays.asList(ids));

        return RestResponse.ok();
    }
}
