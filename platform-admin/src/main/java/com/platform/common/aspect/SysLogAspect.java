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
package com.platform.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.HttpContextUtils;
import com.platform.common.utils.IpUtils;
import com.platform.modules.sys.entity.SysLogEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 *
 * @author 李鹏军
 */
@RequiredArgsConstructor
@Aspect
@Component
public class SysLogAspect {
    private final SysLogService sysLogService;

    @Pointcut("@annotation(com.platform.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + Constant.DOT + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONObject.toJSONString(args);
            sysLog.setParams(params);

            //获取request
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            //设置IP地址
            sysLog.setIp(IpUtils.getIpAddr(request));

            //用户名
            String userName = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUserName();
            sysLog.setUserName(userName);

            sysLog.setTime(time);
            //保存系统日志
            sysLogService.save(sysLog);
        } catch (Exception ignored) {

        }
    }
}
