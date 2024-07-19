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

import javax.validation.constraints.NotEmpty;

/**
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Data
public class WxMsgReplyForm {
    @NotEmpty(message = "用户信息不得为空")
    private String openid;
    @NotEmpty(message = "回复类型不得为空")
    private String replyType;
    @NotEmpty(message = "回复内容不得为空")
    private String replyContent;
}
