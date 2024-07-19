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
package com.platform.common.exception;

import com.platform.common.utils.RestResponse;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author 李鹏军
 */
@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return restResponse
     */
    @ExceptionHandler(BusinessException.class)
    public RestResponse<String> handleBusinessException(BusinessException e) {
        return RestResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * 参数格式化异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NumberFormatException.class)
    public RestResponse<String> handleNumberFormatException(NumberFormatException e) {
        log.error(e.getMessage(), e);
        return RestResponse.fail("请求参数有误！");
    }

    /**
     * 路径不存在异常
     *
     * @param e 路径不存在异常
     * @return restResponse
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResponse<String> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return RestResponse.fail(HttpStatus.NOT_FOUND.value(), "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public RestResponse<String> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return RestResponse.fail("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public RestResponse<String> handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return RestResponse.fail("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public RestResponse<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return RestResponse.fail(e.toString());
    }

    @ExceptionHandler(WxErrorException.class)
    public RestResponse<String> handleWxErrorException(WxErrorException e) {
        log.error(e.getMessage(), e);

        return RestResponse.fail(e.getError().getErrorCode(), e.getError().getErrorMsg());
    }
}
