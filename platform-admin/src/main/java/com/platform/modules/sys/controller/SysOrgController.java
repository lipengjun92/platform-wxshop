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
package com.platform.modules.sys.controller;

import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.modules.sys.entity.SysOrgEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysOrgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 组织机构Controller
 *
 * @author 李鹏军
 * @since 2019-01-21 11:29:22
 */
@Tag(name = "SysOrgController|组织机构")
@RequiredArgsConstructor
@RestController
@RequestMapping("sys/org")
public class SysOrgController extends AbstractController {

    private final SysOrgService sysOrgService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("sys:org:list")
    public RestResponse<List<SysOrgEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<SysOrgEntity> list = sysOrgService.queryAll(params);
        list.sort(Comparator.comparing(SysOrgEntity::getSort));

        return RestResponse.ok(list);
    }

    /**
     * 根据主键查询详情
     *
     * @param orgNo 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{orgNo}")
    @RequiresPermissions("sys:org:info")
    public RestResponse<SysOrgEntity> info(@PathVariable("orgNo") String orgNo) {
        SysOrgEntity sysOrg = sysOrgService.getById(orgNo);

        return RestResponse.ok(sysOrg);
    }

    /**
     * 保存
     *
     * @param sysOrg sysOrg
     * @return RestResponse
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("保存机构")
    @PostMapping("/save")
    @RequiresPermissions("sys:org:save")
    public RestResponse<String> save(@RequestBody SysOrgEntity sysOrg) {
        ValidatorUtils.validateEntity(sysOrg);

        SysUserEntity user = getUser();
        sysOrg.setCreateUserId(user.getUserId());
        sysOrgService.add(sysOrg);
        return RestResponse.ok();
    }

    /**
     * 修改
     *
     * @param sysOrg sysOrg
     * @return RestResponse
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改机构")
    @PostMapping("/update")
    @RequiresPermissions("sys:org:update")
    public RestResponse<String> update(@RequestBody SysOrgEntity sysOrg) {
        ValidatorUtils.validateEntity(sysOrg);
        sysOrgService.update(sysOrg);
        return RestResponse.ok();
    }

    /**
     * 删除
     *
     * @param orgNo 机构编码
     * @return RestResponse
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除机构")
    @PostMapping("/delete")
    @RequiresPermissions("sys:org:delete")
    public RestResponse<String> delete(@RequestBody String orgNo) {
        orgNo = orgNo.replaceAll("\"", Constant.BLANK);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryListByOrgNo(orgNo);
        if (sysOrgEntities.size() > 0) {
            return RestResponse.fail("请先删除子机构");
        } else {
            sysOrgService.delete(orgNo);
        }
        return RestResponse.ok();
    }
}
