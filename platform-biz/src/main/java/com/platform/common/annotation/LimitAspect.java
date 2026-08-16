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
 *  Neither the name of the openwtai.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.common.annotation;

import com.google.common.util.concurrent.RateLimiter;
import com.platform.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 限流
 *
 * @author 李鹏军
 */
@Slf4j
@Component
@Scope
@Aspect
public class LimitAspect {
    /**
     * 每秒只发出5个令牌，此处是单进程服务的限流,内部采用令牌捅算法实现
     */
    private static RateLimiter rateLimiter = RateLimiter.create(5.0);

    /**
     * Service层切点  限流
     */
    @Pointcut("@annotation(com.platform.common.annotation.ServiceLimit)")
    public void serviceAspect() {

    }

    @Around("serviceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!rateLimiter.tryAcquire()) {
            log.warn("请求被限流, 方法: {}", joinPoint.getSignature().toShortString());
            throw new BusinessException("请求过于频繁，请稍后再试", 429);
        }
        return joinPoint.proceed();
    }
}
