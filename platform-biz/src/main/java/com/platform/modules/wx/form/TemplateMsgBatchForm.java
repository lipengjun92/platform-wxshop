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

import com.alibaba.fastjson.JSON;
import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 批量发送模板消息表单
 * 通过用户筛选条件（一般使用标签筛选），将消息发送给数据库中所有符合筛选条件的用户
 * 若所有筛选条件都为空，则表示发送给所有用户
 *
 * @author 李鹏军
 */
@Data
public class TemplateMsgBatchForm {
    @NotNull(message = "需用户筛选条件参数")
    Map<String, Object> wxUserFilterParams;
    @NotEmpty(message = "模板ID不得为空")
    private String templateId;
    private String url;
    private WxMpTemplateMessage.MiniProgram miniprogram;
    @NotEmpty(message = "消息模板数据不得为空")
    private List<WxMpTemplateData> data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
