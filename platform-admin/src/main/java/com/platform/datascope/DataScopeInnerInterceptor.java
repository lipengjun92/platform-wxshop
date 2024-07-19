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
package com.platform.datascope;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.platform.common.utils.Constant;
import com.platform.common.utils.ShiroUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.entity.SysUserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 数据权限
 *
 * @author 李鹏军
 * @date 2021-05-18 13:30:56
 */
@Data
@NoArgsConstructor
public class DataScopeInnerInterceptor implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 不是SELECT操作直接返回
        if (!SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
            return;
        }
        StringBuilder filterSql = new StringBuilder(boundSql.getSql());
        //参数中DataScope类型的参数
        DataScope dataScope = findDataScopeObject(parameter);
        if (dataScope != null) {
            SysUserEntity user = ShiroUtils.getUserEntity();
            if (null != user) {
                //系统管理员，拥有最高权限，不进行权限过滤
                if (!Constant.SUPER_ADMIN.equals(user.getUserId())) {
                    String userAlias = dataScope.getUserAlias();
                    String orgAlias = dataScope.getOrgAlias();
                    String alias = dataScope.getOrgNos();
                    boolean self = dataScope.getSelf();

                    if (StringUtils.isNotBlank(alias)) {
                        filterSql.append(" and (").append(orgAlias).append(" in (").append(alias).append(")");
                        if (self) {
                            filterSql.append(" or ").append(userAlias).append("='").append(user.getUserId()).append("' ");
                        }
                        filterSql.append(" ) ");
                    } else if (self) {
                        filterSql.append(" and ").append(userAlias).append("='").append(user.getUserId()).append("' ");
                    }
                }
                PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
                List<ParameterMapping> mappings = mpBoundSql.parameterMappings();
                mpBoundSql.sql(filterSql.toString());
                mpBoundSql.parameterMappings(mappings);
            }
        }
    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    private DataScope findDataScopeObject(Object parameterObj) {
        if (parameterObj instanceof DataScope) {
            return (DataScope) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof DataScope) {
                    return (DataScope) val;
                } else {
                    if (val instanceof Map) {
                        for (Object v : ((Map<?, ?>) val).values()) {
                            if (v instanceof DataScope) {
                                return (DataScope) v;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
