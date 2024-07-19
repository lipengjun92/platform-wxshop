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
package com.platform.modules.oss.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.oss.dao.SysOssDao;
import com.platform.modules.oss.entity.SysOssEntity;
import com.platform.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李鹏军
 */
@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public Page<SysOssEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.CREATE_DATE");
        params.put("asc", false);
        Page<SysOssEntity> page = new Query<SysOssEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysOssPage(page, params));
    }
}
