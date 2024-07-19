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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.AbstractAssert;
import com.platform.common.validator.ValidatorUtils;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.form.PasswordForm;
import com.platform.modules.sys.service.SysUserRoleService;
import com.platform.modules.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author 李鹏军
 */
@Tag(name = "SysUserController|系统用户")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "查看所有列表", description = "查看所有列表")
    @GetMapping("/queryAll")
    @RequiresPermissions("sys:dict:list")
    public RestResponse<List<SysUserEntity>> queryAll(@RequestParam Map<String, Object> params) {
        List<SysUserEntity> list = sysUserService.queryAll(params);

        return RestResponse.ok(list);
    }

    /**
     * 所有用户列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public RestResponse<Page<SysUserEntity>> list(@RequestParam Map<String, Object> params) {

        //如需数据权限，在参数中添加DataScope
        params.put("dataScope", getDataScope());

        Page<SysUserEntity> page = sysUserService.queryPage(params);

        return RestResponse.ok(page);
    }

    /**
     * 获取登录的用户信息
     *
     * @return RestResponse
     */
    @Operation(summary = "获取登录的用户信息", description = "获取登录的用户信息")
    @GetMapping("/info")
    public RestResponse<SysUserEntity> info() {
        return RestResponse.ok(getUser());
    }

    /**
     * 修改登录用户密码
     *
     * @param form form
     * @return RestResponse
     */
    @Operation(summary = "修改密码", description = "修改密码")
    @SysLog("修改密码")
    @PostMapping("/password")
    public RestResponse<String> password(@RequestBody PasswordForm form) {
        AbstractAssert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return RestResponse.fail("原密码不正确");
        }

        return RestResponse.ok();
    }

    /**
     * 根据主键查询详情
     *
     * @param userId 主键
     * @return RestResponse
     */
    @Operation(summary = "根据主键查询详情", description = "根据主键查询详情")
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public RestResponse<SysUserEntity> info(@PathVariable("userId") String userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return RestResponse.ok(user);
    }

    /**
     * 保存用户
     *
     * @param user user
     * @return RestResponse
     */
    @Operation(summary = "新增", description = "新增")
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public RestResponse<String> save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        user.setCreateUserId(getUserId());
        user.setCreateUserOrgNo(getOrgNo());
        sysUserService.add(user, params);

        return RestResponse.ok();
    }

    /**
     * 修改用户
     *
     * @param user user
     * @return RestResponse
     */
    @Operation(summary = "修改", description = "修改")
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public RestResponse<String> update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        user.setCreateUserId(getUserId());
        user.setCreateUserOrgNo(getOrgNo());
        sysUserService.update(user, params);

        return RestResponse.ok();
    }

    /**
     * 删除用户
     *
     * @param userIds userIds
     * @return RestResponse
     */
    @Operation(summary = "删除", description = "删除")
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public RestResponse<String> delete(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, Constant.SUPER_ADMIN)) {
            return RestResponse.fail("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return RestResponse.fail("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return RestResponse.ok();
    }

    /**
     * 重置密码
     *
     * @param userIds userIds
     * @return RestResponse
     */
    @Operation(summary = "重置密码", description = "重置密码")
    @SysLog("重置密码")
    @PostMapping("/resetPw")
    @RequiresPermissions("sys:user:resetPw")
    public RestResponse<String> resetPw(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, Constant.SUPER_ADMIN)) {
            return RestResponse.fail("系统管理员不能重置");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return RestResponse.fail("当前用户不能重置");
        }

        sysUserService.resetPw(userIds);

        return RestResponse.ok();
    }
}
