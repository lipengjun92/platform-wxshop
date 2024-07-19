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
package com.platform.handler;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JSONArray处理类
 *
 * @author 李鹏军
 * @date 2019-11-12 18:30:15
 */
public class JsonArrayTypeHandler extends BaseTypeHandler<JSONArray> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONArray array, JdbcType jdbcType) throws SQLException {
        ps.setString(i, array.toJSONString());
    }

    @Override
    public JSONArray getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return JSONArray.parseArray(resultSet.getString(columnName));
    }

    @Override
    public JSONArray getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return JSONArray.parseArray(resultSet.getString(columnIndex));
    }

    @Override
    public JSONArray getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return JSONArray.parseArray(callableStatement.getString(columnIndex));
    }
}
