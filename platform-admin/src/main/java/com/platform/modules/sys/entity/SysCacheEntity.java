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
package com.platform.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * redis缓存信息
 *
 * @author 李鹏军
 */
@Data
public class SysCacheEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    private String cacheKey;
    /**
     * value
     */
    private String value;
    /**
     * 剩余过期时间，单位秒
     */
    private Long seconds;
}
