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

/**
 * 公众号消息处理
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7
 * WxJava客服消息文档：https://github.com/Wechat-Group/WxJava/wiki/MP_主动发送消息（客服消息）
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
public interface MsgReplyService {
    /**
     * 根据规则配置通过微信客服消息接口自动回复消息
     *
     * @param exactMatch 是否精确匹配
     * @param toUser     用户openid
     * @param keywords   匹配关键词
     * @return 是否已自动回复，无匹配规则则不自动回复
     */
    boolean tryAutoReply(boolean exactMatch, String toUser, String keywords);

    /**
     * 回复公众号消息
     *
     * @param toUser
     * @param replyType
     * @param replyContent
     */
    void reply(String toUser, String replyType, String replyContent);

    /**
     * 回复文字消息
     *
     * @param toUser
     * @param replyContent
     * @throws WxErrorException
     */
    void replyText(String toUser, String replyContent) throws WxErrorException;

    /**
     * 回复图片消息
     *
     * @param toUser
     * @param mediaId
     * @throws WxErrorException
     */
    void replyImage(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复录音消息
     *
     * @param toUser
     * @param mediaId
     * @throws WxErrorException
     */
    void replyVoice(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复视频消息
     *
     * @param toUser
     * @param mediaId
     * @throws WxErrorException
     */
    void replyVideo(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复音乐消息
     *
     * @param toUser
     * @param mediaId
     * @throws WxErrorException
     */
    void replyMusic(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复图文消息（点击跳转到外链）
     * 图文消息条数限制在1条以内
     *
     * @param toUser
     * @param newsInfoJson
     * @throws WxErrorException
     */
    void replyNews(String toUser, String newsInfoJson) throws WxErrorException;

    /**
     * 回复公众号文章消息（点击跳转到图文消息页面）
     * 图文消息条数限制在1条以内
     *
     * @param toUser
     * @param mediaId
     * @throws WxErrorException
     */
    void replyMpNews(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复卡券消息
     *
     * @param toUser
     * @param cardId
     * @throws WxErrorException
     */
    void replyWxCard(String toUser, String cardId) throws WxErrorException;

    /**
     * 回复小程序消息
     *
     * @param toUser
     * @param miniProgramInfoJson
     * @throws WxErrorException
     */
    void replyMiniProgram(String toUser, String miniProgramInfoJson) throws WxErrorException;

    /**
     * 回复菜单消息
     *
     * @param toUser
     * @param msgMenusJson
     * @throws WxErrorException
     */
    void replyMsgMenu(String toUser, String msgMenusJson) throws WxErrorException;
}
