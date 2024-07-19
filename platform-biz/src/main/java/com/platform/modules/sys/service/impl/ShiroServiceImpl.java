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
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.modules.sys.dao.SysMenuDao;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.dao.SysUserTokenDao;
import com.platform.modules.sys.entity.SysMenuEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.entity.SysUserTokenEntity;
import com.platform.modules.sys.service.ShiroService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 李鹏军
 */
@RequiredArgsConstructor
@Service("shiroService")
public class ShiroServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements ShiroService {

    private final SysMenuDao sysMenuDao;

    private final SysUserDao sysUserDao;

    @Override
    public Set<String> getUserPermissions(String userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN.equals(userId)) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(new QueryWrapper<SysMenuEntity>().select("PERMS"));
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                if (null != menu) {
                    permsList.add(menu.getPerms());
                }
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return baseMapper.selectOne(new QueryWrapper<SysUserTokenEntity>().eq("TOKEN", token));
    }

    @Override
    public SysUserEntity queryUser(String userId) {
        return sysUserDao.selectById(userId);
    }
}
