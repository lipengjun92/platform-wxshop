package com.platform.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 异常处理器
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年10月27日 下午10:16:19
 */
@Slf4j
@RestControllerAdvice(value = {"com.platform"})
public class RRExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return R.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }


    @ExceptionHandler(ApiRRException.class)
    public Object handleApiRRException(ApiRRException e) {
        HashMap result = new HashMap<>();
        result.put("errno", e.getErrno());
        result.put("errmsg", e.getErrmsg());
        return result;
    }
}
