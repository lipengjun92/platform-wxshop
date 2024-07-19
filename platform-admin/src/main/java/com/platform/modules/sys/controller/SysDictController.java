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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.entity.SysDictGroupEntity;
import com.platform.modules.sys.service.SysDictGroupService;
import com.platform.modules.sys.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典Controller
 *
 * @author 李鹏军
 * @since 2019-01-15 11:42:20
 */
@Tag(name = "SysDictController|数据字典")
@RequiredArgsConstructor
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    private final SysDictService sysDictService;
    private final SysDictGroupService sysDictGroupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("sys:dict:list")
    public RestResponse<List<SysDictEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<SysDictEntity> list = sysDictService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public RestResponse<Page<SysDictEntity>> list(@RequestParam Map<String, Object> params) {
        Page<SysDictEntity> page = sysDictService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public RestResponse<SysDictEntity> info(@PathVariable("id") String id) {
        SysDictEntity sysDict = sysDictService.getById(id);

        return RestResponse.ok(sysDict);
    }

    /**
     * 保存
     *
     * @return RestResponse
     */
    @Operation(summary = "保存数据字典", description = "保存数据字典")
    @SysLog("保存数据字典")
    @PostMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public RestResponse<String> save(@RequestBody SysDictEntity sysDict) {
        ValidatorUtils.validateEntity(sysDict, AddGroup.class);
        sysDictService.add(sysDict);

        return RestResponse.ok();
    }

    /**
     * 修改
     *
     * @return RestResponse
     */
    @Operation(summary = "修改数据字典", description = "修改数据字典")
    @SysLog("修改数据字典")
    @PostMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public RestResponse<String> update(@RequestBody SysDictEntity sysDict) {
        ValidatorUtils.validateEntity(sysDict, UpdateGroup.class);
        sysDictService.update(sysDict);

        return RestResponse.ok();
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @Operation(summary = "删除数据字典", description = "删除数据字典")
    @SysLog("删除数据字典")
    @PostMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public RestResponse<String> delete(@RequestBody String[] ids) {
        sysDictService.deleteBatch(ids);

        return RestResponse.ok();
    }

    /**
     * 根据code查询数据字典
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "根据code查询数据字典", description = "根据code查询数据字典")
    @GetMapping("/queryByCode")
    public RestResponse<Map<String, Object>> queryByCode(@RequestParam Map<String, Object> params) {
        String code = (String) params.get("code");
        SysDictGroupEntity sysDictGroupEntity = sysDictGroupService.getOne(new QueryWrapper<SysDictGroupEntity>()
                .eq(StringUtils.isNotBlank(code), "code", code)
        );
        String type = Constant.BLANK;
        if (null != sysDictGroupEntity) {
            type = sysDictGroupEntity.getName();
        }

        List<SysDictEntity> list = sysDictService.queryByCode(params);
        Map<String, Object> map = new HashMap<>(4);
        map.put("list", list);
        map.put("type", type);
        return RestResponse.ok(map);
    }
}
