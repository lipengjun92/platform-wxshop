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
package com.platform.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * 对象操作工具类, 继承org.apache.commons.lang.ObjectUtils类
 *
 * @author 李鹏军
 */
public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {

    private static Log logger = LogFactory.getLog(ObjectUtils.class);

    /**
     * 注解到对象复制，只复制能匹配上的方法。
     *
     * @param annotation annotation
     * @param object     object
     */
    public static void annotationToObject(Object annotation, Object object) {
        if (annotation != null) {
            Class<?> annotationClass = annotation.getClass();
            Class<?> objectClass = object.getClass();
            for (Method m : objectClass.getMethods()) {
                if (StringUtils.startsWith(m.getName(), "set")) {
                    try {
                        String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
                        Object obj = annotationClass.getMethod(s).invoke(annotation);
                        if (obj != null && !"".equals(obj.toString())) {
                            if (object == null) {
                                object = objectClass.newInstance();
                            }
                            m.invoke(object, obj);
                        }
                    } catch (Exception e) {
                        // 忽略所有设置失败方法
                    }
                }
            }
        }
    }

    /**
     * 序列化对象
     *
     * @param object object
     * @return byte[]
     */
    public static byte[] serialize(Object object) {
        try {
            if (object != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * 反序列化对象
     *
     * @param bytes bytes
     * @return Object
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais;
        try {
            if (bytes != null && bytes.length > 0) {
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
}
