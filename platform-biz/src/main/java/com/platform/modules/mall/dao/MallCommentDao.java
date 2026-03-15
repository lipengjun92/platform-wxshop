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
import com.platform.modules.mall.entity.MallCommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品评论表Dao
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2026-03-15 13:46:31
 */
@Mapper
public interface MallCommentDao extends BaseMapper<MallCommentEntity> {
    List<MallCommentEntity> queryAll(@Param("params") Map<String, Object> params);

    List<MallCommentEntity> selectMallCommentPage(Page<MallCommentEntity> page, @Param("params") Map<String, Object> params);
}
