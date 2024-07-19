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

import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.bean.WxNetCheckResult;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号菜单管理
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html
 * WxJava开发文档：https://github.com/Wechat-Group/WxJava/wiki/MP_自定义菜单管理
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Tag(name = "WxMenuManageController|微信公众号菜单")
@RestController
@RequestMapping("/manage/wxMenu")
@RequiredArgsConstructor
public class WxMenuManageController {
    private final WxMpService wxMpService;

    /**
     * 获取公众号菜单
     */
    @Operation(summary = "获取公众号菜单", description = "获取公众号菜单")
    @GetMapping("/getMenu")
    public RestResponse<WxMpMenu> getMenu() throws WxErrorException {
        WxMpMenu wxMpMenu = wxMpService.getMenuService().menuGet();
        return RestResponse.ok(wxMpMenu);
    }

    /**
     * 创建、更新菜单
     */
    @Operation(summary = "发布公众号菜单", description = "发布公众号菜单")
    @SysLog("发布公众号菜单")
    @PostMapping("/updateMenu")
    @RequiresPermissions("wx:menu:save")
    public RestResponse<String> updateMenu(@RequestBody WxMenu wxMenu) throws WxErrorException {
        wxMpService.getMenuService().menuCreate(wxMenu);
        return RestResponse.ok();
    }

    /**
     * 网络检测
     * 为了帮助开发者排查回调连接失败的问题，提供这个网络检测的API。它可以对开发者URL做域名解析，然后对所有IP进行一次ping操作，得到丢包率和耗时。
     *
     * @param action 执行的检测动作，允许的值：dns（做域名解析）、ping（做ping检测）、all（dns和ping都做）
     * @return RestResponse
     */
    @Operation(summary = "网络检测", description = "网络检测")
    @SysLog("网络检测")
    @GetMapping("/netCheck")
    public RestResponse<WxNetCheckResult> netCheck(String action) {
        WxNetCheckResult result;
        try {
            result = wxMpService.netCheck(action, "DEFAULT");
        } catch (WxErrorException e) {
            return RestResponse.fail(e.getMessage());
        }
        return RestResponse.ok(result);
    }
}
