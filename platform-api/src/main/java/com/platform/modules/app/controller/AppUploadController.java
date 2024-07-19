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
package com.platform.modules.app.controller;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.oss.cloud.UploadFactory;
import com.platform.modules.oss.entity.SysOssEntity;
import com.platform.modules.oss.service.SysOssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 作者: @author Lipengjun <br>
 * 时间: 2017-09-08 13:20<br>
 * 描述: AppUploadController <br>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/upload")
@Tag(name = "AppUploadController|上传管理控制器")
public class AppUploadController {

    private final SysOssService sysOssService;

    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @return RestResponse
     */
    @IgnoreAuth
    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传文件，form表单提交",
            parameters = {@Parameter(name = "file", description = "文件流对象", required = true, schema = @Schema(type = "object", format = "binary"))}
    )
    public RestResponse<String> upload(@RequestParam("file") MultipartFile file) {
        if (null == file || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(Constant.DOT));

        String url;
        try {
            url = Objects.requireNonNull(UploadFactory.build()).uploadSuffix(file.getBytes(), suffix);
        } catch (IOException e) {
            return RestResponse.fail("上传文件失败");
        }

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        sysOssService.save(ossEntity);

        return RestResponse.ok("操作成功", url);
    }

    /**
     * 上传多个文件
     *
     * @param files MultipartFile[]
     * @return RestResponse
     */
    @IgnoreAuth
    @PostMapping("/uploadFiles")
    @Operation(summary = "上传多个文件", description = "上传多个文件，form表单提交",
            parameters = {@Parameter(name = "files", description = "文件流对象,接收数组格式", required = true)}
    )
    @ArraySchema(arraySchema = @Schema(type = "object", format = "binary"))
    public RestResponse<List<String>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        if (null == files || files.length == 0) {
            throw new BusinessException("上传文件不能为空");
        }
        List<String> urls = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(Constant.DOT));
                //上传文件
                String url = Objects.requireNonNull(UploadFactory.build()).uploadSuffix(file.getBytes(), suffix);
                urls.add(url);
                //保存文件信息
                SysOssEntity ossEntity = new SysOssEntity();
                ossEntity.setUrl(url);
                ossEntity.setCreateDate(new Date());
                sysOssService.save(ossEntity);
            }
        } catch (IOException e) {
            return RestResponse.fail("上传文件失败");
        }
        return RestResponse.ok(urls);
    }
}
