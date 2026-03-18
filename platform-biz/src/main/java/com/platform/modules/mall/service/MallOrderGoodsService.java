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
package com.platform.modules.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.mall.entity.MallOrderGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单商品明细服务接口。
 * <p>
 * 定义订单商品模块的业务方法，包括查询、分页以及标准增删改。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
public interface MallOrderGoodsService extends IService<MallOrderGoodsEntity> {

    /**
     * 查询订单商品明细列表。
     *
     * @param params 查询参数
     * @return 订单商品明细集合
     */
    List<MallOrderGoodsEntity> queryAll(Map<String, Object> params);

    /**
     * 按条件包装器查询订单商品明细列表。
     *
     * @param queryWrapper 条件包装器
     * @return 订单商品明细集合
     */
    List<MallOrderGoodsEntity> queryAllByWrapper(QueryWrapper<MallOrderGoodsEntity> queryWrapper);

    /**
     * 分页查询订单商品明细。
     *
     * @param params 查询参数
     * @return 分页数据
     */
    Page<MallOrderGoodsEntity> queryPage(Map<String, Object> params);

    /**
     * 新增订单商品明细。
     *
     * @param mallOrderGoods 订单商品明细实体
     * @return 是否成功
     */
    boolean add(MallOrderGoodsEntity mallOrderGoods);

    /**
     * 修改订单商品明细。
     *
     * @param mallOrderGoods 订单商品明细实体
     * @return 是否成功
     */
    boolean update(MallOrderGoodsEntity mallOrderGoods);

    /**
     * 根据主键删除订单商品明细。
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean delete(Integer id);

    /**
     * 批量删除订单商品明细。
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    boolean deleteBatch(Integer[] ids);

    List<MallOrderGoodsEntity> queryList(Map<String, Object> orderGoodsParam);
}
