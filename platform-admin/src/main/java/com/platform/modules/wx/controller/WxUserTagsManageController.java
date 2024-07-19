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

import com.platform.common.utils.RestResponse;
import com.platform.modules.wx.form.WxUserBatchTaggingForm;
import com.platform.modules.wx.form.WxUserTagForm;
import com.platform.modules.wx.service.WxUserTagsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公众号用户标签
 *
 * @author 李鹏军
 */
@RestController
@RequestMapping("/manage/wxUserTags")
@RequiredArgsConstructor
@Tag(name = "WxUserTagsManageController|公众号用户标签")
public class WxUserTagsManageController {

    private final WxUserTagsService wxUserTagsService;

    /**
     * 查询用户标签
     */
    @GetMapping("/list")
    @RequiresPermissions("wx:wxuser:info")
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    public RestResponse<List<WxUserTag>> list() throws WxErrorException {
        List<WxUserTag> wxUserTags = wxUserTagsService.getWxTags();
        return RestResponse.ok(wxUserTags);
    }

    /**
     * 修改或新增标签
     */
    @PostMapping("/save")
    @RequiresPermissions("wx:wxuser:save")
    @Operation(summary = "修改或新增标签", description = "修改或新增标签")
    public RestResponse<String> save(@RequestBody WxUserTagForm form) throws WxErrorException {
        Long tagid = form.getId();
        if (tagid == null || tagid <= 0) {
            wxUserTagsService.creatTag(form.getName());
        } else {
            wxUserTagsService.updateTag(tagid, form.getName());
        }
        return RestResponse.ok();
    }

    /**
     * 删除标签
     */
    @PostMapping("/delete/{tagid}")
    @RequiresPermissions("wx:wxuser:save")
    @Operation(summary = "删除标签", description = "删除标签")
    public RestResponse<String> delete(@PathVariable("tagid") Long tagid) throws WxErrorException {
        if (tagid == null || tagid <= 0) {
            return RestResponse.fail("标签ID不得为空");
        }
        wxUserTagsService.deleteTag(tagid);
        return RestResponse.ok();
    }

    /**
     * 批量给用户打标签
     */
    @PostMapping("/batchTagging")
    @RequiresPermissions("wx:wxuser:save")
    @Operation(summary = "批量给用户打标签", description = "批量给用户打标签")
    public RestResponse<String> batchTagging(@RequestBody WxUserBatchTaggingForm form) throws WxErrorException {

        wxUserTagsService.batchTagging(form.getTagid(), form.getOpenidList());
        return RestResponse.ok();
    }

    /**
     * 批量移除用户标签
     */
    @PostMapping("/batchUnTagging")
    @RequiresPermissions("wx:wxuser:save")
    @Operation(summary = "批量移除用户标签", description = "批量移除用户标签")
    public RestResponse<String> batchUnTagging(@RequestBody WxUserBatchTaggingForm form) throws WxErrorException {
        wxUserTagsService.batchUnTagging(form.getTagid(), form.getOpenidList());
        return RestResponse.ok();
    }
}
