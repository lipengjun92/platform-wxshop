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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.platform.handler.JsonArrayTypeHandler;
import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板消息日志
 *
 * @author 李鹏军
 */
@Data
@TableName(value = "WX_TEMPLATE_MSG_LOG", autoResultMap = true)
public class TemplateMsgLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String logId;
    private String touser;
    private String templateId;
    @TableField(typeHandler = JsonArrayTypeHandler.class)
    private JSONArray data;
    private String url;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONObject miniprogram;
    private Date sendTime;
    private String sendResult;

    public TemplateMsgLogEntity() {
    }

    public TemplateMsgLogEntity(WxMpTemplateMessage msg, String sendResult) {
        this.touser = msg.getToUser();
        this.templateId = msg.getTemplateId();
        this.url = msg.getUrl();
        this.miniprogram = JSONObject.parseObject(JSON.toJSONString(msg.getMiniProgram()));
        this.data = JSONArray.parseArray(JSON.toJSONString(msg.getData()));
        this.sendTime = new Date();
        this.sendResult = sendResult;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
