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
package com.platform.modules.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.modules.wx.TaskExcutor;
import com.platform.modules.wx.entity.MsgReplyRuleEntity;
import com.platform.modules.wx.entity.WxMsgEntity;
import com.platform.modules.wx.service.MsgReplyRuleService;
import com.platform.modules.wx.service.MsgReplyService;
import com.platform.modules.wx.service.WxMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 微信公众号消息处理
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Message_Ma nagement/Service_Center_messages.html#7
 * 参考WxJava客服消息文档：https://github.com/Wechat-Group/WxJava/wiki/MP_主动发送消息（客服消息）
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Slf4j
@Service("msgReplyService")
@RequiredArgsConstructor
public class MsgReplyServiceImpl implements MsgReplyService {
    private final MsgReplyRuleService msgReplyRuleService;
    private final WxMsgService wxMsgService;
    private final WxMpService wxMpService;

    /**
     * 根据规则配置通过微信客服消息接口自动回复消息
     *
     * @param exactMatch 是否精确匹配
     * @param toUser     用户openid
     * @param keywords   匹配关键词
     * @return 是否已自动回复，无匹配规则则不自动回复
     */
    @Override
    public boolean tryAutoReply(boolean exactMatch, String toUser, String keywords) {
        try {
            List<MsgReplyRuleEntity> rules = msgReplyRuleService.getMatchedRules(exactMatch, keywords);
            if (rules.isEmpty()) {
                return false;
            }
            long delay = 0;
            for (MsgReplyRuleEntity rule : rules) {
                TaskExcutor.schedule(() -> {
                    reply(toUser, rule.getReplyType(), rule.getReplyContent());
                }, delay, TimeUnit.MILLISECONDS);
                delay += 1000;
            }
            return true;
        } catch (Exception e) {
            log.error("自动回复出错：", e);
        }
        return false;
    }

    @Override
    public void reply(String toUser, String replyType, String replyContent) {
        try {
            if (WxConsts.KefuMsgType.TEXT.equals(replyType)) {
                this.replyText(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.IMAGE.equals(replyType)) {
                this.replyImage(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.VOICE.equals(replyType)) {
                this.replyVoice(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.VIDEO.equals(replyType)) {
                this.replyVideo(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.MUSIC.equals(replyType)) {
                this.replyMusic(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.NEWS.equals(replyType)) {
                this.replyNews(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.MPNEWS.equals(replyType)) {
                this.replyMpNews(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.WXCARD.equals(replyType)) {
                this.replyWxCard(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.MINIPROGRAMPAGE.equals(replyType)) {
                this.replyMiniProgram(toUser, replyContent);
            } else if (WxConsts.KefuMsgType.MSGMENU.equals(replyType)) {
                this.replyMsgMenu(toUser, replyContent);
            }
        } catch (Exception e) {
            log.error("自动回复出错：", e);
        }
    }

    @Override
    public void replyImage(String toUser, String mediaId) throws WxErrorException {
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.IMAGE().toUser(toUser).mediaId(mediaId).build());

        JSONObject json = new JSONObject().fluentPut("mediaId", mediaId);
        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.IMAGE, toUser, json));
    }

    @Override
    public void replyText(String toUser, String content) throws WxErrorException {
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.TEXT().toUser(toUser).content(content).build());

        JSONObject json = new JSONObject().fluentPut("content", content);
        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.TEXT, toUser, json));
    }

    @Override
    public void replyVoice(String toUser, String mediaId) throws WxErrorException {
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.VOICE().toUser(toUser).mediaId(mediaId).build());

        JSONObject json = new JSONObject().fluentPut("mediaId", mediaId);
        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.VOICE, toUser, json));
    }

    @Override
    public void replyVideo(String toUser, String mediaId) throws WxErrorException {
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.VIDEO().toUser(toUser).mediaId(mediaId).build());

        JSONObject json = new JSONObject().fluentPut("mediaId", mediaId);
        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.VIDEO, toUser, json));
    }

    @Override
    public void replyMusic(String toUser, String musicInfoJson) throws WxErrorException {
        JSONObject json = JSON.parseObject(musicInfoJson);
        wxMpService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MUSIC().toUser(toUser)
                        .musicUrl(json.getString("musicurl"))
                        .hqMusicUrl(json.getString("hqmusicurl"))
                        .title(json.getString("title"))
                        .description(json.getString("description"))
                        .thumbMediaId(json.getString("thumb_media_id"))
                        .build());

        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.IMAGE, toUser, json));
    }

    /**
     * 发送图文消息（点击跳转到外链） 图文消息条数限制在1条以内
     *
     * @param toUser
     * @param newsInfoJson
     * @throws WxErrorException
     */
    @Override
    public void replyNews(String toUser, String newsInfoJson) throws WxErrorException {
        WxMpKefuMessage.WxArticle wxArticle = JSON.parseObject(newsInfoJson, WxMpKefuMessage.WxArticle.class);
        List<WxMpKefuMessage.WxArticle> newsList = new ArrayList<WxMpKefuMessage.WxArticle>() {{
            add(wxArticle);
        }};
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.NEWS().toUser(toUser).articles(newsList).build());

        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.NEWS, toUser, JSON.parseObject(newsInfoJson)));
    }

    /**
     * 发送图文消息（点击跳转到图文消息页面） 图文消息条数限制在1条以内
     *
     * @param toUser
     * @param mediaId
     * @throws WxErrorException
     */
    @Override
    public void replyMpNews(String toUser, String mediaId) throws WxErrorException {
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(toUser).mediaId(mediaId).build());

        JSONObject json = new JSONObject().fluentPut("mediaId", mediaId);
        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.MPNEWS, toUser, json));
    }

    @Override
    public void replyWxCard(String toUser, String cardId) throws WxErrorException {
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.WXCARD().toUser(toUser).cardId(cardId).build());

        JSONObject json = new JSONObject().fluentPut("cardId", cardId);
        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.WXCARD, toUser, json));
    }

    @Override
    public void replyMiniProgram(String toUser, String miniProgramInfoJson) throws WxErrorException {
        JSONObject json = JSON.parseObject(miniProgramInfoJson);
        wxMpService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MINIPROGRAMPAGE()
                        .toUser(toUser)
                        .title(json.getString("title"))
                        .appId(json.getString("appid"))
                        .pagePath(json.getString("pagepath"))
                        .thumbMediaId(json.getString("thumb_media_id"))
                        .build());

        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.IMAGE, toUser, json));
    }

    @Override
    public void replyMsgMenu(String toUser, String msgMenusJson) throws WxErrorException {
        JSONObject json = JSON.parseObject(msgMenusJson);
        List<WxMpKefuMessage.MsgMenu> msgMenus = JSON.parseArray(json.getString("list"), WxMpKefuMessage.MsgMenu.class);
        wxMpService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MSGMENU()
                        .toUser(toUser)
                        .headContent(json.getString("head_content"))
                        .tailContent(json.getString("tail_content"))
                        .msgMenus(msgMenus).build());

        wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.IMAGE, toUser, json));
    }

}
