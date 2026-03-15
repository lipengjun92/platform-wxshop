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
import com.platform.modules.mall.entity.MallOrderGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单商品明细数据访问接口。
 * <p>
 * 提供订单商品查询与分页查询能力，支撑后台订单商品管理。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
@Mapper
public interface MallOrderGoodsDao extends BaseMapper<MallOrderGoodsEntity> {

    /**
     * 查询订单商品明细列表。
     *
     * @param params 查询参数
     * @return 订单商品明细集合
     */
    List<MallOrderGoodsEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 分页查询订单商品明细。
     *
     * @param page   分页对象
     * @param params 查询参数
     * @return 订单商品明细集合
     */
    List<MallOrderGoodsEntity> selectMallOrderGoodsPage(Page<MallOrderGoodsEntity> page, @Param("params") Map<String, Object> params);
}
