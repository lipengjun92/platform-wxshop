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
package com.platform.modules.wx.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;

import java.util.List;

/**
 * @author 李鹏军
 */
public interface WxUserTagsService {
    /**
     * 获取公众号用户标签
     *
     * @return
     * @throws WxErrorException
     */
    List<WxUserTag> getWxTags() throws WxErrorException;

    /**
     * 创建标签
     *
     * @param name 标签名称
     * @throws WxErrorException
     */
    void creatTag(String name) throws WxErrorException;

    /**
     * 修改标签
     *
     * @param tagid 标签ID
     * @param name  标签名称
     * @throws WxErrorException
     */
    void updateTag(Long tagid, String name) throws WxErrorException;

    /**
     * 删除标签
     *
     * @param tagid 标签ID
     * @throws WxErrorException
     */
    void deleteTag(Long tagid) throws WxErrorException;

    /**
     * 批量给用户打标签
     *
     * @param tagid      标签ID
     * @param openidList 用户openid列表
     * @throws WxErrorException
     */
    void batchTagging(Long tagid, String[] openidList) throws WxErrorException;

    /**
     * 批量取消用户标签
     *
     * @param tagid      标签ID
     * @param openidList 用户openid列表
     * @throws WxErrorException
     */
    void batchUnTagging(Long tagid, String[] openidList) throws WxErrorException;

    /**
     * 为用户绑定标签
     *
     * @param tagid
     * @param openid
     * @throws WxErrorException
     */
    void tagging(Long tagid, String openid) throws WxErrorException;

    /**
     * 取消用户所绑定的某一个标签
     *
     * @param tagid
     * @param openid
     * @throws WxErrorException
     */
    void untagging(Long tagid, String openid) throws WxErrorException;
}
