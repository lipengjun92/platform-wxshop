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
import com.platform.modules.sys.entity.SysCacheEntity;
import com.platform.modules.sys.service.SysCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统缓存管理
 *
 * @author 李鹏军
 */
@Tag(name = "SysCacheController|系统缓存")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/cache")
public class SysCacheController {
    private final SysCacheService sysCacheService;

    /**
     * 查询缓存
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查询缓存", description = "查询缓存")
    @RequiresPermissions("sys:cache:queryAll")
    @GetMapping("/queryAll")
    public RestResponse<List<SysCacheEntity>> queryAll(@RequestParam Map<String, String> params) {
        String type = params.get("type");
        if (Constant.STR_ONE.equals(type)) {
            //查询所有缓存
            params.put("pattern", "*");
        } else if (Constant.STR_TWO.equals(type)) {
            //查询session缓存
            params.put("pattern", Constant.CACHEABLE + "*");
        } else if (Constant.STR_THREE.equals(type)) {
            //查询系统缓存
            params.put("pattern", Constant.SYS_CACHE + "*");
        } else if (Constant.STR_FOUR.equals(type)) {
            //查询业务缓存
            params.put("pattern", Constant.MTM_CACHE + "*");
        }
        List<SysCacheEntity> list = sysCacheService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 删除cache
     *
     * @param keys keys
     * @return RestResponse
     */
    @Operation(summary = "删除redis缓存", description = "删除redis缓存")
    @SysLog("删除redis缓存")
    @RequiresPermissions("sys:cache:deleteCache")
    @PostMapping("/deleteCache")
    public RestResponse<String> deleteBatch(@RequestBody String[] keys) {
        sysCacheService.deleteBatch(keys);

        return RestResponse.ok();
    }
}
