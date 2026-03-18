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
import com.platform.modules.mall.entity.MallCommentPictureEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品评论图片表
 *
 * @author 李鹏军
 * @since 2026-03-15 13:45:28
 */
public interface MallCommentPictureService extends IService<MallCommentPictureEntity> {
    List<MallCommentPictureEntity> queryAll(Map<String, Object> params);

    List<MallCommentPictureEntity> queryAllByWrapper(QueryWrapper<MallCommentPictureEntity> queryWrapper);

    Page<MallCommentPictureEntity> queryPage(Map<String, Object> params);

    boolean add(MallCommentPictureEntity mallCommentPicture);

    boolean update(MallCommentPictureEntity mallCommentPicture);

    boolean delete(Integer id);

    boolean deleteBatch(Integer[] ids);

    List<MallCommentPictureEntity> queryList(Map<String, Object> paramPicture);
}
