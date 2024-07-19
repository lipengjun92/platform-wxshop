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
package com.platform.interceptor;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 权限(Token)验证
 *
 * @author 李鹏军
 */
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    private static final String[] IGNORE_TOKEN_URI = new String[]{"/search/seachList", "/cart/myCart", "/user/footprintList", "/search/history"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //支持跨域请求
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        String maAppId = request.getHeader("maAppId");
        request.setAttribute(Constant.MA_APPID, maAppId);

        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if (StringUtils.isBlank(token)) {
            if (Arrays.stream(IGNORE_TOKEN_URI).anyMatch(uri -> request.getRequestURI().contains(uri))) {
                return true;
            }
            throw new BusinessException(jwtUtils.getHeader() + "不能为空", HttpStatus.UNAUTHORIZED.value());
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            throw new BusinessException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(Constant.USER_KEY, claims.getSubject());

        return true;
    }
}
