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
package com.platform.modules.wx.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信消息
 * <p>
 * 使用mybatis-plus可以提高应用程序操作数据库的效率，让开发人员专注于业务逻辑实现。在使用mybatis-plus操作json字段的要点主要有:
 * 在需要处理的字段上使用@TableField(typeHandler = JacksonTypeHandler.class)，同时实体开启@TableName(autoResultMap = true)
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Data
@TableName(value = "WX_MSG", autoResultMap = true)
public class WxMsgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 微信用户ID
     */
    private String openid;
    /**
     * 消息方向
     */
    private byte inOut;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 消息详情
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONObject detail;
    /**
     * 创建时间
     */
    private Date createTime;

    public static class WxMsgInOut {
        static final byte IN = 0;
        static final byte OUT = 1;
    }

    public WxMsgEntity() {
    }

    public WxMsgEntity(WxMpXmlMessage wxMessage) {
        this.openid = wxMessage.getFromUser();
        this.inOut = WxMsgInOut.IN;
        this.msgType = wxMessage.getMsgType();
        this.detail = new JSONObject();
        Long createTime = wxMessage.getCreateTime();
        this.createTime = createTime == null ? new Date() : new Date(createTime * 1000);
        if (WxConsts.XmlMsgType.TEXT.equals(this.msgType)) {
            this.detail.put("content", wxMessage.getContent());
        } else if (WxConsts.XmlMsgType.IMAGE.equals(this.msgType)) {
            this.detail.put("picUrl", wxMessage.getPicUrl());
            this.detail.put("mediaId", wxMessage.getMediaId());
        } else if (WxConsts.XmlMsgType.VOICE.equals(this.msgType)) {
            this.detail.put("format", wxMessage.getFormat());
            this.detail.put("mediaId", wxMessage.getMediaId());
        } else if (WxConsts.XmlMsgType.VIDEO.equals(this.msgType) ||
                WxConsts.XmlMsgType.SHORTVIDEO.equals(this.msgType)) {
            this.detail.put("thumbMediaId", wxMessage.getThumbMediaId());
            this.detail.put("mediaId", wxMessage.getMediaId());
        } else if (WxConsts.XmlMsgType.LOCATION.equals(this.msgType)) {
            this.detail.put("locationX", wxMessage.getLocationX());
            this.detail.put("locationY", wxMessage.getLocationY());
            this.detail.put("scale", wxMessage.getScale());
            this.detail.put("label", wxMessage.getLabel());
        } else if (WxConsts.XmlMsgType.LINK.equals(this.msgType)) {
            this.detail.put("title", wxMessage.getTitle());
            this.detail.put("description", wxMessage.getDescription());
            this.detail.put("url", wxMessage.getUrl());
        } else if (WxConsts.XmlMsgType.EVENT.equals(this.msgType)) {
            this.detail.put("event", wxMessage.getEvent());
            this.detail.put("eventKey", wxMessage.getEventKey());
        }
    }

    public static WxMsgEntity buildOutMsg(String msgType, String openid, JSONObject detail) {
        WxMsgEntity wxMsg = new WxMsgEntity();
        wxMsg.msgType = msgType;
        wxMsg.openid = openid;
        wxMsg.detail = detail;
        wxMsg.createTime = new Date();
        wxMsg.inOut = WxMsgInOut.OUT;
        return wxMsg;
    }
}
