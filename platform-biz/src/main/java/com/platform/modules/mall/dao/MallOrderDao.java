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
package com.platform.modules.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.mall.entity.MallAttributeCategoryEntity;
import com.platform.modules.mall.entity.MallOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单数据访问接口。
 * <p>
 * 提供订单列表查询和分页查询能力，供 Service 层调用。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
@Mapper
public interface MallOrderDao extends BaseMapper<MallOrderEntity> {

    /**
     * 查询订单列表。
     *
     * @param params 查询参数
     * @return 订单集合
     */
    List<MallOrderEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 分页查询订单列表。
     *
     * @param page   分页对象
     * @param params 查询参数
     * @return 订单集合
     */
    List<MallOrderEntity> selectMallOrderPage(Page<MallOrderEntity> page, @Param("params") Map<String, Object> params);

    List<MallOrderEntity> queryList(Map<String, Object> param);
}
