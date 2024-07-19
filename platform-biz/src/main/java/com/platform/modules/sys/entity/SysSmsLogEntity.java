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
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信发送日志实体
 *
 * @author 李鹏军
 * @since 2019-02-12 09:51:15
 */
@Data
@TableName("SYS_SMS_LOG")
public class SysSmsLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 模板ID
     */
    private String templateId;
    /**
     * 验证码
     */
    private String code;
    /**
     * 手机号码。多个以英文逗号隔开
     */
    private String mobile;
    /**
     * 发送时间
     */
    private Date stime;
    /**
     * 用户签名
     */
    private String sign;
    /**
     * 0成功 1失败
     */
    private Integer sendStatus;
    /**
     * 发送编号
     */
    private String sendId;
    /**
     * 成功提交数
     */
    private Integer successNum;
    /**
     * 返回消息
     */
    private String returnMsg;
}
