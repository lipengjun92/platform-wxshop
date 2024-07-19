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
package com.platform.modules.gen.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.gen.dao.SysGeneratorDao;
import com.platform.modules.gen.entity.ColumnEntity;
import com.platform.modules.gen.entity.ResultMapEntity;
import com.platform.modules.gen.service.SysGeneratorService;
import com.platform.modules.gen.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author 李鹏军
 */
@Service("sysGeneratorService")
public class SysGeneratorServiceImpl extends ServiceImpl<SysGeneratorDao, ResultMapEntity> implements SysGeneratorService {
    @Value("${spring.datasource.dynamic.datasource.master.driverClassName}")
    private String driverClassName;

    @Override
    public Page<ResultMapEntity> queryPage(Map<String, Object> params) {
        Page<ResultMapEntity> page = new Query<ResultMapEntity>(params).getPage();

        params.put("driverClassName", driverClassName);
        return page.setRecords(baseMapper.queryPage(page, params));
    }

    @Override
    public ResultMapEntity queryTable(String tableName) {
        Map<String, Object> params = new HashMap<>(4);

        params.put("tableName", tableName);
        params.put("driverClassName", driverClassName);

        return baseMapper.queryTable(params);
    }

    @Override
    public List<ColumnEntity> queryColumns(String tableName) {
        Map<String, Object> params = new HashMap<>(4);

        params.put("tableName", tableName);
        params.put("driverClassName", driverClassName);

        return baseMapper.queryColumns(params);
    }

    @Override
    public byte[] generatorCode(String[] tableNames, String projectName, String packageName, String author) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            ResultMapEntity table = queryTable(tableName);
            //查询列信息
            List<ColumnEntity> columns = queryColumns(tableName);
            //生成代码
            String tablePrefix = tableName.split("_")[0];
            GenUtils.generatorCode(table, columns, zip, projectName, packageName, author, tablePrefix);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

}
