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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.mall.entity.MallOrderEntity;
import com.platform.modules.wx.entity.WxUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单服务接口。
 * <p>
 * 定义订单模块的标准业务能力，包括分页、查询、新增、修改、删除。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
public interface MallOrderService extends IService<MallOrderEntity> {

    /**
     * 查询订单列表。
     *
     * @param params 查询参数
     * @return 订单集合
     */
    List<MallOrderEntity> queryAll(Map<String, Object> params);

    /**
     * 按条件包装器查询订单列表。
     *
     * @param queryWrapper 条件包装器
     * @return 订单集合
     */
    List<MallOrderEntity> queryAllByWrapper(QueryWrapper<MallOrderEntity> queryWrapper);

    /**
     * 分页查询订单。
     *
     * @param params 查询参数
     * @return 分页数据
     */
    Page<MallOrderEntity> queryPage(Map<String, Object> params);

    /**
     * 新增订单。
     *
     * @param mallOrder 订单实体
     * @return 是否成功
     */
    boolean add(MallOrderEntity mallOrder);

    /**
     * 修改订单。
     *
     * @param mallOrder 订单实体
     * @return 是否成功
     */
    boolean update(MallOrderEntity mallOrder);

    /**
     * 根据主键删除订单。
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean delete(Integer id);

    /**
     * 批量删除订单。
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    boolean deleteBatch(Integer[] ids);

    Map<String, Object> submit(JSONObject payload, WxUserEntity loginUser);

    List<MallOrderEntity> queryList(Map<String, Object> params);
}
