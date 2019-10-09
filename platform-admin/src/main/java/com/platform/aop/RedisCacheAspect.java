package com.platform.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.RedisCache;
import com.platform.cache.J2CacheUtils;
import com.platform.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 李鹏军
 * @email 939961241@qq.com
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * @date 2019年2月18日 下午15:54:23
 */
@Aspect
@Component
public class RedisCacheAspect {

    @Pointcut("@annotation(com.platform.annotation.RedisCache)")
    public void webAspect() {
    }

    @SuppressWarnings("unchecked")
    @Around("webAspect()")
    public Object redisCache(ProceedingJoinPoint pjp) throws Throwable {
        //得到类名、方法名和参数
        String redisResult;
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        //得到被代理的方法
        Signature signature = pjp.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException();
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = pjp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //得到被代理的方法上的注解
        String cacheKey = method.getAnnotation(RedisCache.class).cacheKey();
        boolean destine = method.getAnnotation(RedisCache.class).destine();

        String key = cacheKey;
        if (!destine) {
            //根据类名，方法名和参数生成key
            key = StringUtils.genKey(cacheKey, className, methodName);
        }

        Object result = null;
        if (!J2CacheUtils.exists(key)) {
            //缓存不存在，则调用原方法，并将结果放入缓存中
            result = pjp.proceed(args);
            redisResult = JSON.toJSONString(result);
            J2CacheUtils.put(key, redisResult);
        } else {
            //缓存命中
            redisResult = JSONObject.toJSON(J2CacheUtils.get(key)).toString();
            //得到被代理方法的返回值类型
            Class returnType = method.getReturnType();
            result = JSON.parseObject(redisResult, returnType);
        }
        return result;
    }
}
