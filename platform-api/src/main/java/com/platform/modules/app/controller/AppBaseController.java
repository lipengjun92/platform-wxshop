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
package com.platform.modules.app.controller;

import com.platform.common.utils.Constant;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制类
 *
 * @author Lipengjun
 * @since 2016年9月2日
 */
public class AppBaseController {
    /**
     * 得到request对象
     */
    @Autowired
    protected HttpServletRequest request;

    protected Object getAttribute(String param) {
        return request.getAttribute(param);
    }

    /**
     * 请求头里的 maAppId
     */
    protected String getMaAppId() {
        return ObjectUtils.toString(getAttribute(Constant.MA_APPID), null);
    }

    /**
     * 获取请求方IP
     *
     * @return 客户端Ip
     */
    protected String getClientIp() {
        String xff = request.getHeader("x-forwarded-for");
        if (xff == null) {
            return request.getRemoteAddr();
        }
        return xff;
    }

    /**
     * initBinder 初始化绑定 <br>
     * 这里处理了1种类型<br>
     * 1、字符串自动 trim 去掉前后空格 <br>
     *
     * @param binder  WebDataBinder 要注册的binder
     * @param request 前端请求
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // 字符串自动Trim
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }
}
