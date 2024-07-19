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
package com.platform.modules.wx.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 图文素材文章实体.
 *
 * @author 李鹏军
 * @since 2022-08-22 19:20:47
 */
@Data
public class DraftArticles implements Serializable {
    private Integer index;

    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前54个字。
     */
    private String digest;
    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源 "上传图文消息内的图片获取URL"接口获取。外部图片url将被过滤。
     */
    private String content;
    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    private String contentSourceUrl;
    /**
     * 图文消息的封面图片素材id（必须是永久MediaID）
     */
    private String thumbMediaId;
    /**
     * 是否显示封面，0为false，即不显示，1为true，即显示(默认)
     */
    private Integer showCoverPic;
    /**
     * 是否打开评论，0不打开(默认)，1打开
     */
    private Integer needOpenComment;
    /**
     * 是否粉丝才可评论，0所有人可评论(默认)，1粉丝才可评论
     */
    private Integer onlyFansCanComment;
    /**
     * 草稿的临时链接,点击图文消息跳转链接
     */
    private String url;
    /**
     * 图文消息的封面url
     */
    private String thumbUrl;
}
