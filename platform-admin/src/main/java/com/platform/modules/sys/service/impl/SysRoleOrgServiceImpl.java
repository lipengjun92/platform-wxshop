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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.modules.sys.dao.SysRoleOrgDao;
import com.platform.modules.sys.entity.SysRoleOrgEntity;
import com.platform.modules.sys.service.SysRoleOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色与机构对应关系
 *
 * @author lipengjun
 * @since 2017年9月18日 上午9:18:38
 */
@Service("sysRoleOrgService")
public class SysRoleOrgServiceImpl extends ServiceImpl<SysRoleOrgDao, SysRoleOrgEntity> implements SysRoleOrgService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(String roleId, List<String> orgNoList) {
        //先删除角色与菜单关系
        baseMapper.deleteByRoleId(roleId);

        if (null == orgNoList || orgNoList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        List<SysRoleOrgEntity> list = new ArrayList<>(orgNoList.size());
        for (String orgNo : orgNoList) {
            SysRoleOrgEntity sysRoleMenuEntity = new SysRoleOrgEntity();
            sysRoleMenuEntity.setOrgNo(orgNo);
            sysRoleMenuEntity.setRoleId(roleId);

            list.add(sysRoleMenuEntity);
        }
        this.saveBatch(list);
    }

    @Override
    public List<String> queryOrgNoList(String roleId) {
        return baseMapper.queryOrgNoList(roleId);
    }

    @Override
    public String queryOrgNoListByUserId(String userId) {
        List<String> roleOrglist = baseMapper.queryOrgNoListByUserId(userId);
        StringBuilder roleStr = new StringBuilder();
        String alias = Constant.BLANK;
        if (roleOrglist != null && !roleOrglist.isEmpty()) {
            for (String roleId : roleOrglist) {
                roleStr.append(Constant.COMMA);
                roleStr.append("'");
                roleStr.append(roleId);
                roleStr.append("'");
            }
            alias = roleStr.toString().substring(1, roleStr.length());
        }
        return alias;
    }

    @Override
    public int deleteBatch(String[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
