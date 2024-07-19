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
import com.platform.modules.wx.form.MaterialFileDeleteForm;
import com.platform.modules.wx.service.WxAssetsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.material.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 微信公众号素材管理
 * 参考官方文档：https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/New_temporary_materials.html
 * 参考WxJava开发文档：https://github.com/Wechat-Group/WxJava/wiki/MP_永久素材管理
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Tag(name = "WxAssetsManageController|微信公众号素材")
@RequiredArgsConstructor
@RestController
@RequestMapping("/manage/wxAssets")
public class WxAssetsManageController {
    private final WxAssetsService wxAssetsService;

    /**
     * 获取素材总数
     *
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "获取素材总数", description = "获取素材总数")
    @GetMapping("/materialCount")
    public RestResponse<WxMpMaterialCountResult> materialCount() throws WxErrorException {
        WxMpMaterialCountResult res = wxAssetsService.materialCount();
        return RestResponse.ok(res);
    }

    /**
     * 获取素材总数
     *
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "获取素材", description = "获取素材")
    @GetMapping("/materialNewsInfo")
    public RestResponse<WxMpMaterialNews> materialNewsInfo(String mediaId) throws WxErrorException {
        WxMpMaterialNews res = wxAssetsService.materialNewsInfo(mediaId);
        return RestResponse.ok(res);
    }


    /**
     * 根据类别分页获取非图文素材列表
     *
     * @param type type
     * @param page page
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "根据类别分页获取非图文素材列表", description = "根据类别分页获取非图文素材列表")
    @GetMapping("/materialFileBatchGet")
    @RequiresPermissions("wx:wxassets:list")
    public RestResponse<WxMpMaterialFileBatchGetResult> materialFileBatchGet(@RequestParam(defaultValue = "image") String type,
                                                                             @RequestParam(defaultValue = "1") int page) throws WxErrorException {
        WxMpMaterialFileBatchGetResult res = wxAssetsService.materialFileBatchGet(type, page);
        return RestResponse.ok(res);
    }

    /**
     * 分页获取图文素材列表
     *
     * @param page page
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "分页获取图文素材列表", description = "分页获取图文素材列表")
    @GetMapping("/materialNewsBatchGet")
    @RequiresPermissions("wx:wxassets:list")
    public RestResponse<WxMpMaterialNewsBatchGetResult> materialNewsBatchGet(@RequestParam(defaultValue = "1") int page) throws WxErrorException {
        WxMpMaterialNewsBatchGetResult res = wxAssetsService.materialNewsBatchGet(page);
        return RestResponse.ok(res);
    }

    /**
     * 添加多媒体永久素材
     *
     * @param file      file
     * @param fileName  fileName
     * @param mediaType mediaType
     * @return RestResponse
     * @throws WxErrorException .
     * @throws IOException      .
     */
    @Operation(summary = "添加多媒体永久素材", description = "添加多媒体永久素材")
    @PostMapping("/materialFileUpload")
    @RequiresPermissions("wx:wxassets:save")
    public RestResponse<WxMpMaterialUploadResult> materialFileUpload(MultipartFile file, String fileName, String mediaType) throws WxErrorException, IOException {
        if (file == null) {
            return RestResponse.fail("文件不得为空");
        }

        WxMpMaterialUploadResult res = wxAssetsService.materialFileUpload(mediaType, fileName, file);
        return RestResponse.ok(res);
    }

    /**
     * 删除素材
     *
     * @param form form
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "删除素材", description = "删除素材")
    @SysLog("删除素材")
    @PostMapping("/materialDelete")
    @RequiresPermissions("wx:wxassets:delete")
    public RestResponse<Boolean> materialDelete(@RequestBody MaterialFileDeleteForm form) throws WxErrorException {
        boolean res = wxAssetsService.materialDelete(form.getMediaId());
        return RestResponse.ok(res);
    }
}
