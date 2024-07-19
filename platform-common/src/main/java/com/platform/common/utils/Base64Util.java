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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 作者: @author Lipengjun <br>
 * 时间: 2017-08-11 16:17<br>
 * 描述: Base64 <br>
 */
public class Base64Util {
    /**
     * 加密
     *
     * @param s s
     * @return String
     */
    public static String encode(String s) {
        if (StringUtils.isBlank(s)) {
            return "";
        }
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        return encoder.encodeToString(b);
    }

    /**
     * 解密
     *
     * @param s s
     * @return String
     */
    public static String decode(String s) {
        if (StringUtils.isBlank(s)) {
            return "";
        }
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(s), StandardCharsets.UTF_8);
    }
}
