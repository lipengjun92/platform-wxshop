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
package com.platform.modules.gen.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.xss.XssHttpServletRequestWrapper;
import com.platform.modules.gen.entity.ResultMapEntity;
import com.platform.modules.gen.service.SysGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @since 2017年1月3日 下午6:35:28
 */
@Tag(name = "SysGeneratorController|代码生成")
@RequiredArgsConstructor
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {

    private final SysGeneratorService sysGeneratorService;

    /**
     * 分页查询所有表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询所有表", description = "分页查询所有表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("sys:generator:list")
    public RestResponse<Page<ResultMapEntity>> list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Page<ResultMapEntity> page = sysGeneratorService.queryPage(params);
        return RestResponse.ok(page);
    }

    /**
     * 生成代码
     *
     * @param request  request
     * @param response response
     */
    @Operation(summary = "生成代码", description = "生成代码")
    @SysLog("生成代码")
    @GetMapping("/code")
    @RequiresPermissions("sys:generator:code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取表名，不进行xss过滤
        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
        String tables = orgRequest.getParameter("tables");
        String projectName = orgRequest.getParameter("projectName");
        String packageName = orgRequest.getParameter("packageName");
        String author = orgRequest.getParameter("author");
        String[] tableNames = tables.split(Constant.COMMA);

        byte[] data = sysGeneratorService.generatorCode(tableNames, projectName, packageName, author);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"AutoCode" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + ".zip\"");
        response.addHeader("Content-Length", Constant.BLANK + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
