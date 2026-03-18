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

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.mall.entity.MallRelatedGoodsEntity;

/**
 * 商品关联Service接口
 *
 * @author 李鹏军
 * @since 2026-03-15 21:55:00
 */
public interface MallRelatedGoodsService extends IService<MallRelatedGoodsEntity> {

    /**
     * 新增
     *
     * @param mallRelatedGoods 商品关联实体
     * @return 新增结果
     */
    boolean add(MallRelatedGoodsEntity mallRelatedGoods);

    /**
     * 根据主键更新
     *
     * @param mallRelatedGoods 商品关联实体
     * @return 更新结果
     */
    boolean update(MallRelatedGoodsEntity mallRelatedGoods);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(Integer[] ids);
}
