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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * 自动回复规则
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Data
@TableName("WX_MSG_REPLY_RULE")
public class MsgReplyRuleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private String ruleId;
    private String appid;
    @NotEmpty(message = "规则名称不得为空")
    private String ruleName;
    @NotEmpty(message = "匹配的关键词、事件等不能为空")
    private String matchValue;
    private boolean exactMatch;
    @NotEmpty(message = "回复类型不得为空")
    private String replyType;
    @NotEmpty(message = "回复内容不得为空")
    private String replyContent;
    @TableField(value = "`STATUS`")
    private boolean status;
    @TableField(value = "`DESC`")
    private String desc;
    private Time effectTimeStart;
    private Time effectTimeEnd;
    private int priority;
    private Date updateTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
